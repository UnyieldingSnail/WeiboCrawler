package com.zhangyujie.comment.service;

import com.zhangyujie.comment.env.CommentEnv;
import com.zhangyujie.content.env.ContentEnv;
import com.zhangyujie.poms.entity.Comment;
import org.openqa.selenium.By;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.springframework.stereotype.Component;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.selector.JsonPathSelector;

import java.util.*;

@Component("weiboCommentProcesser")
public class WeiboCommentProcesser implements PageProcessor {

    private Site site = Site.me().setRetryTimes(3).setSleepTime(1000).setTimeOut(5000);

    public void process(Page page) {
        if (page.getUrl().regex("https://m\\.weibo\\.cn/api/comments/show\\?id=[0-9]+&page=[0-9]+").match()) {
            //微博评论页面
            collectComments(page);
            System.out.println("collectComments......");
        } else {
            page.setSkip(true);
        }
    }

    // 采集评论信息
    private void collectComments(Page page) {
        page.putField("type", "comment");
        // 检查是否成功返回了页面
        String ok = new JsonPathSelector("$.ok").select(page.getRawText()).trim();
        if (!"1".equals(ok)) {
            System.out.println("comment 页面返回为空");
            page.setSkip(true);
            try {
                String weiboId = CommentEnv.WEIBOIDS.take().toString();
                page.addTargetRequest("https://m.weibo.cn/api/comments/show?id=" + weiboId + "&page=1" + "&random="+ Math.random());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        } else {
            String weiboId = page.getUrl().regex("(?<=id=)[0-9]+").get();
            List<String> userIds = new JsonPathSelector("$.data.data[*].user.id").selectList(page.getRawText());

            page.putField("weiboId", weiboId);
            page.putField("commentId", new JsonPathSelector("$.data.data[*].id").selectList(page.getRawText()));
            page.putField("userId", userIds);
            page.putField("userName", new JsonPathSelector("$.data.data[*].user.screen_name").selectList(page.getRawText()));
            page.putField("text", new JsonPathSelector("$.data.data[*].text").selectList(page.getRawText()));
            page.putField("createdAt", new JsonPathSelector("$.data.data[*].created_at").selectList(page.getRawText()));

            int n = Integer.parseInt(page.getUrl().regex("(?<=&page=)[0-9]+").get());
            page.addTargetRequest("https://m.weibo.cn/api/comments/show?id=" + weiboId + "&page=" + (n + 1) + "&random="+ Math.random());
            CommentEnv.USERIDS.addAll(userIds);
        }
        System.out.println("comment段请求池：" + page.getTargetRequests().size());
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
