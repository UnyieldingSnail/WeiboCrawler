package com.zhangyujie.repost.service;

import com.zhangyujie.repost.env.RepostEnv;
import org.apache.commons.collections.CollectionUtils;
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

@Component("weiboRepostProcesser")
public class WeiboRepostProcesser implements PageProcessor {

    private Site site = Site.me().setRetryTimes(3).setSleepTime(1000).setTimeOut(30000);

    public void process(Page page) {
        if (page.getUrl().regex("https://m\\.weibo\\.cn/api/statuses/repostTimeline\\?id=[0-9]+&page=[0-9]+").match()) {
            collectRepost(page);
            System.out.println("collectRepost......");
        } else {
            page.setSkip(true);
        }
    }

    // 采集转发者id
    private void collectRepost(Page page) {
        page.putField("type", "repost");
        // 检查是否成功返回了页面
        String ok = new JsonPathSelector("$.ok").select(page.getRawText()).trim();
        if (!"1".equals(ok)) {
            page.setSkip(true);
            try {
                String weiboId = RepostEnv.WEIBOIDS.take().toString();
                page.addTargetRequest("https://m.weibo.cn/api/statuses/repostTimeline?id=" + weiboId + "&page=1" + "&random="+ Math.random());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        } else {
            String weiboId = page.getUrl().regex("(?<=id=)[0-9]+(?=&)").get();
            List<String> repostIds = new JsonPathSelector(".data.data[*].user.id").selectList(page.getRawText());
            page.putField("weiboId", weiboId);
            page.putField("repostIds", repostIds);
            page.putField("repostTexts", new JsonPathSelector(".data.data[*].raw_text").selectList(page.getRawText()));
            page.putField("createdAt", new JsonPathSelector(".data.data[*].created_at").selectList(page.getRawText()));

            int n = Integer.parseInt(page.getUrl().regex("(?<=&page=)[0-9]+").get());
            page.addTargetRequest("https://m.weibo.cn/api/statuses/repostTimeline?id=" + weiboId + "&page=" + (n + 1) + "&random="+ Math.random());
            RepostEnv.USERIDS.addAll(repostIds);
        }
        System.out.println("repost段请求池：" + page.getTargetRequests().size());
        try {
            Thread.sleep(300);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public Site getSite() {
        return site;
    }
}
