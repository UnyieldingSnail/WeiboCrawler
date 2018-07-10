package com.zhangyujie.specialWeibo.service;

import com.zhangyujie.poms.service.WeiboCrawler;
import com.zhangyujie.poms.util.MongoPipeline;
import org.springframework.stereotype.Component;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.downloader.HttpClientDownloader;
import us.codecraft.webmagic.processor.PageProcessor;

import javax.annotation.Resource;

@Component("weiboSpeechCrawlerRunner")
public class WeiboSpeechCrawlerRunner implements WeiboCrawler, Runnable{
    @Resource(name = "weiboSpeechProcesser")
    private PageProcessor processer;
    @Resource(name = "mongoPipeline")
    private MongoPipeline pipeline;

    public void run() {
//		processer.login();
        HttpClientDownloader downloader = new HttpClientDownloader();
//        downloader.setProxyProvider(SimpleProxyProvider.from(GlobalEnv.PROXY));
//        Spider.create(processer).addUrl("https://m.weibo.cn/api/container/getIndex?containerid=102803").setDownloader(downloader).addPipeline(pipeline).run();
        String startUrl = "https://m.weibo.cn/api/container/getIndex?uid=2286908003&type=uid&value=2286908003&containerid=1076032286908003&page=0&random=" + System.currentTimeMillis();
        Spider.create(processer).addUrl(startUrl).addPipeline(pipeline).run();
    }

}
