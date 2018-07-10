package com.zhangyujie.content.start;

import com.zhangyujie.content.service.WeiboContentCrawlerRunner;
import com.zhangyujie.content.rpc.RpcServerRunner;
import com.zhangyujie.global.env.GlobalEnv;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Run {
    public static void main(String[] args) {
        System.out.println("微博内容爬虫启动了。。。");
        ExecutorService es = Executors.newCachedThreadPool();
        // 加载接收微博ID的线程
        es.submit(new RpcServerRunner());
        // 加载采集微博内容的线程
        WeiboContentCrawlerRunner runner = GlobalEnv.CTX.getBean("weiboContentCrawlerRunner", WeiboContentCrawlerRunner.class);
        es.submit(runner);
    }
}
