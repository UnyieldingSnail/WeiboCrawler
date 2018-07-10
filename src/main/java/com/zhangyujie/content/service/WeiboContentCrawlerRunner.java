package com.zhangyujie.content.service;

import com.zhangyujie.content.env.ContentEnv;
import com.zhangyujie.poms.service.WeiboCrawler;
import com.zhangyujie.poms.util.MongoPipeline;
import org.springframework.stereotype.Component;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.processor.PageProcessor;

import javax.annotation.Resource;
@Component("weiboContentCrawlerRunner")
public class WeiboContentCrawlerRunner implements WeiboCrawler, Runnable{
    @Resource(name = "weiboContentProcesser")
    private PageProcessor processer;
    @Resource(name = "mongoPipeline")
    private MongoPipeline pipeline;

    public void run() {
//		processer.login();
        try {
            CharSequence weiboId = ContentEnv.WEIBOIDS.take();
            System.out.println("《开始采集内容》");
            Spider.create(processer).addUrl("https://m.weibo.cn/statuses/extend?id=" + weiboId.toString()).addPipeline(pipeline).run();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public PageProcessor getProcesser() {
        return processer;
    }

    public void setProcesser(PageProcessor processer) {
        this.processer = processer;
    }

    public MongoPipeline getPipeline() {
        return pipeline;
    }

    public void setPipeline(MongoPipeline pipeline) {
        this.pipeline = pipeline;
    }
}
