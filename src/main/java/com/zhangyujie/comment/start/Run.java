package com.zhangyujie.comment.start;

import com.zhangyujie.comment.rpc.RpcServerRunner;
import com.zhangyujie.comment.service.WeiboCommentCrawlerRunner;
import com.zhangyujie.comment.rpc.RpcClientRunner;
import com.zhangyujie.global.env.GlobalEnv;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Run {
    public static void main(String[] args) {
        System.out.println("微博评论爬虫启动了。。。");
        ExecutorService es = Executors.newCachedThreadPool();
        // 加载接收微博ID的线程
        es.submit(new RpcServerRunner());
        // 加载采集微博评论的线程
        es.submit(GlobalEnv.CTX.getBean("weiboCommentCrawlerRunner", WeiboCommentCrawlerRunner.class));
        // 加载发送用户ID的线程
        es.submit(new RpcClientRunner());
        // https://m.weibo.cn/p/index?containerid=102803
    }
}
