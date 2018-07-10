package com.zhangyujie.relation.service;

import com.zhangyujie.global.env.GlobalEnv;
import com.zhangyujie.poms.service.WeiboCrawler;
import com.zhangyujie.poms.util.MongoPipeline;
import com.zhangyujie.relation.env.RelationEnv;
import org.springframework.stereotype.Component;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.downloader.HttpClientDownloader;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.proxy.Proxy;
import us.codecraft.webmagic.proxy.SimpleProxyProvider;

import javax.annotation.Resource;
@Component("weiboRelationCrawlerRunner")
public class WeiboRelationCrawlerRunner implements WeiboCrawler, Runnable{
    @Resource(name = "weiboRelationProcesser")
    private PageProcessor processer;
    @Resource(name = "mongoPipeline")
    private MongoPipeline pipeline;

    public void run() {
//		processer.login();
        try {
            CharSequence userId = RelationEnv.USERIDS.take();
            System.out.println("《开始采集关系》");
            Spider.create(processer).addUrl("https://m.weibo.cn/api/container/getIndex?containerid=231051_-_followers_-_" + userId + "_-_1042015&page=1").addPipeline(pipeline).run();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
