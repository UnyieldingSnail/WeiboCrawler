package com.zhangyujie.specialWeibo.service;

import com.zhangyujie.specialWeibo.env.SpeechEnv;
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

@Component("weiboSpeechProcesser")
public class WeiboSpeechProcesser implements PageProcessor {

    private Site site = Site.me().setRetryTimes(3).setSleepTime(100).setTimeOut(3000);
    //存储cookie信息
    private Set<Cookie> cookies;
    private int pageNum = 1;

    public void process(Page page) {
        if (page.getUrl().regex("https://m\\.weibo\\.cn/api/container/getIndex\\?uid=[0-9]+&type=uid&value=[0-9]+&containerid=[0-9]+(&page=[0-9]+)?").match()) {
            //如果是列表页面，则采集微博信息并获取微博详细内容链接
            collectSpeeches(page);
        } else {
            page.setSkip(true);
        }
    }
    // https://m.weibo.cn/u/2286908003?uid=2286908003&luicode=10000011&lfid=100103type%3D1%26q%3D%E4%BA%BA%E6%B0%91%E7%BD%91&featurecode=20000320
    private void collectSpeeches(Page page) {
        page.putField("type", "speech");
        // 检查是否成功返回了页面
        String ok = new JsonPathSelector("$.ok").select(page.getRawText()).trim();
        List<String> ids = new JsonPathSelector(".data.cards[*].mblog.id").selectList(page.getRawText());
        System.out.println("ids:" + ids.size());
        System.out.println(page.getUrl());
        System.out.println(ids);
        if ("1".equals(ok) && CollectionUtils.isNotEmpty(ids) && pageNum < 40) {
            for (String id : ids) {
                SpeechEnv.WEIBOIDS.offer(id);
            }
            List<String> userIds = new JsonPathSelector("$..cards[*].mblog.user.id").selectList(page.getRawText());
            page.putField("weiboId", ids);
            page.putField("userId", userIds);
            page.putField("userName", new JsonPathSelector("$..cards[*].mblog.user.screen_name").selectList(page.getRawText()));
            page.putField("userGender", new JsonPathSelector("$..cards[*].mblog.user.gender").selectList(page.getRawText()));
            page.putField("text", new JsonPathSelector("$..cards[*].mblog.text").selectList(page.getRawText()));
            page.putField("createdAt", new JsonPathSelector("$..cards[*].mblog.created_at").selectList(page.getRawText()));
            page.putField("attitudesCount", new JsonPathSelector("$..cards[*].mblog.attitudes_count").selectList(page.getRawText()));
            page.putField("commentsCount", new JsonPathSelector("$..cards[*].mblog.comments_count").selectList(page.getRawText()));
            page.putField("repostsCount", new JsonPathSelector("$..cards[*].mblog.reposts_count").selectList(page.getRawText()));
//            if (pageNum > 1) {
//                return;
//            }
            String url = page.getUrl().toString().replaceFirst("&page=[0-9]+&random=[0-9]+", "&page=" + (pageNum++) + "&random=" + System.currentTimeMillis());
            page.addTargetRequest(url);
            for (String userId : new HashSet<>(userIds)) {
                SpeechEnv.USERIDS.offer(userId);
            }
            try {
                Thread.sleep(10000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        } else {
            page.setSkip(true);
        }
    }

    //使用 selenium 来模拟用户的登录获取cookie信息
    public void login() {
        WebDriver driver = new ChromeDriver();
        driver.get("https://passport.weibo.cn/signin/login");

        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        driver.findElement(By.id("loginName")).clear();

        //填用户名
        driver.findElement(By.id("loginName")).sendKeys("15545544769");

        driver.findElement(By.id("loginPassword")).clear();
        //填密码
        driver.findElement(By.id("loginPassword")).sendKeys("123210");

        //模拟点击登录按钮
        // driver.findElement(By.cssSelector(".W_login_form .login_btn a")).click();
        Actions action = new Actions(driver);
        action.click(driver.findElement(By.id("loginAction")));

        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        //获取cookie信息
        cookies = driver.manage().getCookies();
        driver.close();

        //将获取到的cookie信息添加到webmagic中
        for (Cookie cookie : cookies) {
            site.addCookie(cookie.getName().toString(), cookie.getValue().toString());
            System.out.println(cookie.getName());
        }
    }

    public Site getSite() {
        return site;
    }
}
