package com.zhangyujie.specialWeibo.start;

import com.zhangyujie.global.env.GlobalEnv;
import com.zhangyujie.specialWeibo.rpc.RpcClientRunner;
import com.zhangyujie.specialWeibo.service.WeiboSpeechCrawlerRunner;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Run {
    public static void main(String[] args) {
        System.out.println("微博列表爬虫启动了。。。");
        ExecutorService es = Executors.newCachedThreadPool();
        // 加载采集微博列表的线程
        es.submit(GlobalEnv.CTX.getBean("weiboSpeechCrawlerRunner", WeiboSpeechCrawlerRunner.class));
        // 加载发送微博ID的线程
        es.submit(new RpcClientRunner());
    }
}
