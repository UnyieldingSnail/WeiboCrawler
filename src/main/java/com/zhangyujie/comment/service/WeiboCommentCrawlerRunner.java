package com.zhangyujie.comment.service;

import com.zhangyujie.comment.env.CommentEnv;
import com.zhangyujie.content.env.ContentEnv;
import com.zhangyujie.poms.entity.Comment;
import com.zhangyujie.poms.service.WeiboCrawler;
import com.zhangyujie.poms.util.MongoPipeline;
import org.springframework.stereotype.Component;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.processor.PageProcessor;

import javax.annotation.Resource;
@Component("weiboCommentCrawlerRunner")
public class WeiboCommentCrawlerRunner implements WeiboCrawler, Runnable{
    @Resource(name = "weiboCommentProcesser")
    private PageProcessor processer;
    @Resource(name = "mongoPipeline")
    private MongoPipeline pipeline;

    public void run() {
//		processer.login();
        try {
            CharSequence weiboId = CommentEnv.WEIBOIDS.take();
            System.out.println("《开始采集评论》");
            Spider.create(processer).addUrl("https://m.weibo.cn/api/comments/show?id=" + weiboId + "&page=1").addPipeline(pipeline).run();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
