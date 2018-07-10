package com.zhangyujie.content.service;

import com.zhangyujie.content.env.ContentEnv;
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

@Component("weiboContentProcesser")
public class WeiboContentProcesser implements PageProcessor {

    private Site site = Site.me().setRetryTimes(3).setSleepTime(1000).setTimeOut(5000);

    public void process(Page page) {
        if (page.getUrl().regex("https://m\\.weibo\\.cn/statuses/extend\\?id=[0-9]+").match()) {
            //如果是微博详细信息页面，则采集微博内容
            collectContents(page);
            System.out.println("collectContents......");
        } else {
            page.setSkip(true);
        }
    }

    //采集微博内容
    private void collectContents(Page page) {
        page.putField("type", "content");
        // 检查是否成功返回了页面
        String ok = new JsonPathSelector("$.ok").select(page.getRawText()).trim();
        if (!"1".equals(ok)) {
            System.out.println("下载失败");
            page.setSkip(true);
        } else {
            String weiboId = page.getUrl().regex("(?<=id=)[0-9]+").get();
            page.putField("weiboId", weiboId);
            page.putField("content", new JsonPathSelector("$.data.longTextContent").selectList(page.getRawText()));
        }
        try {
            String weiboId = ContentEnv.WEIBOIDS.take().toString();
            page.addTargetRequest("https://m.weibo.cn/statuses/extend?id=" + weiboId  + "&random="+ Math.random());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("content段请求池：" + page.getTargetRequests().size());
    }

    public Site getSite() {
        return site;
    }
}
