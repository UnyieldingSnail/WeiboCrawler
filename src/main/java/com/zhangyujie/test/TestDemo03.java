package com.zhangyujie.test;

import com.zhangyujie.content.env.ContentEnv;

public class TestDemo03 implements Runnable{
    public void run() {
        try {
            System.out.println(ContentEnv.WEIBOIDS.take());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
