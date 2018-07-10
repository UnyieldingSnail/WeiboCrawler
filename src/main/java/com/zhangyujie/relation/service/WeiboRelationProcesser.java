package com.zhangyujie.relation.service;

import com.zhangyujie.relation.env.RelationEnv;
import com.zhangyujie.repost.env.RepostEnv;
import org.openqa.selenium.By;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.springframework.stereotype.Component;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.selector.JsonPathSelector;

import java.util.*;

@Component("weiboRelationProcesser")
public class WeiboRelationProcesser implements PageProcessor {

    private Site site = Site.me().setRetryTimes(3).setSleepTime(100).setTimeOut(3000);

    public void process(Page page) {
        if (page.getUrl().regex("https://m\\.weibo\\.cn/api/container/getIndex\\?containerid=231051_-_followers_-_[0-9]+_-_1042015&page=[0-9]+").match()) {
            // 用户关注列表
            collectRelations(page);
            System.out.println("collectRelations......");
        } else {
            page.setSkip(true);
        }
    }

    // 采集用户关系 https://m.weibo.cn/p/index?containerid=231051_-_followers_-_6349839494_-_1042015
    private void collectRelations(Page page) {
        page.putField("type", "relation");
        // 检查是否成功返回了页面
        String ok = new JsonPathSelector("$.ok").select(page.getRawText()).trim();
        if (!"1".equals(ok)) {
            page.setSkip(true);
            try {
                String userId = RelationEnv.USERIDS.take().toString();
                page.addTargetRequest("https://m.weibo.cn/api/container/getIndex?containerid=231051_-_followers_-_" + userId + "_-_1042015&page=1" + "&random="+ Math.random());
                System.out.println("relation端剩余用户ID" + RelationEnv.USERIDS.size());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        } else {
            String userId = page.getUrl().regex("(?<=_-_)[0-9]+(?=_-_)").get();
            page.putField("userId", userId);
            page.putField("follow", new JsonPathSelector("$.data.cards[*].card_group[*].user.id").selectList(page.getRawText()));

            int n = Integer.parseInt(page.getUrl().regex("(?<=&page=)[0-9]+").get());
            page.addTargetRequest("https://m.weibo.cn/api/container/getIndex?containerid=231051_-_followers_-_" + userId + "_-_1042015&page=" + (n + 1) + "&random="+ Math.random());
        }
        System.out.println("relation段请求池：" + page.getTargetRequests().size());
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public Site getSite() {
        return site;
    }
}
