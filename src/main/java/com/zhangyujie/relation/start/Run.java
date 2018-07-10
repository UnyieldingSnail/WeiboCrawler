package com.zhangyujie.relation.start;

import com.zhangyujie.global.env.GlobalEnv;
import com.zhangyujie.relation.service.WeiboRelationCrawlerRunner;
import com.zhangyujie.relation.rpc.RpcServerRunner;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Run {
    public static void main(String[] args) {
        System.out.println("关注关系爬虫启动了。。。");
        ExecutorService es = Executors.newCachedThreadPool();
        // 加载接收用户ID的线程
        es.submit(new RpcServerRunner());
        // 加载采集转发者的线程
        es.submit(GlobalEnv.CTX.getBean("weiboRelationCrawlerRunner", WeiboRelationCrawlerRunner.class));
    }
}
