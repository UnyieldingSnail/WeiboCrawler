package com.zhangyujie.repost.service;

import com.zhangyujie.poms.service.WeiboCrawler;
import com.zhangyujie.poms.util.MongoPipeline;
import com.zhangyujie.repost.env.RepostEnv;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.processor.PageProcessor;

import javax.annotation.Resource;
@Component("weiboRepostCrawlerRunner")
public class WeiboRepostCrawlerRunner implements WeiboCrawler, Runnable{
    @Resource(name = "weiboRepostProcesser")
    private PageProcessor processer;
    @Resource(name = "mongoPipeline")
    private MongoPipeline pipeline;

    public void run() {
//		processer.login();
        try {
            CharSequence weiboId = RepostEnv.WEIBOIDS.take();
            System.out.println("《开始采集转发》");
            Spider.create(processer).addUrl("https://m.weibo.cn/api/statuses/repostTimeline?id=" + weiboId + "&page=1").addPipeline(pipeline).run();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
