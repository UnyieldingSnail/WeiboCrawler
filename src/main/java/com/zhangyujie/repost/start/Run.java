package com.zhangyujie.repost.start;

import com.zhangyujie.global.env.GlobalEnv;
import com.zhangyujie.repost.rpc.RpcClientRunner;
import com.zhangyujie.repost.rpc.RpcServerRunner;
import com.zhangyujie.repost.service.WeiboRepostCrawlerRunner;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Run {
    public static void main(String[] args) {
        System.out.println("微博转发爬虫启动了。。。");
        ExecutorService es = Executors.newCachedThreadPool();
        // 加载接收微博ID的线程
        es.submit(new RpcServerRunner());
        // 加载采集转发者的线程
        es.submit(GlobalEnv.CTX.getBean("weiboRepostCrawlerRunner", WeiboRepostCrawlerRunner.class));
        es.submit(new RpcClientRunner());
    }
}
