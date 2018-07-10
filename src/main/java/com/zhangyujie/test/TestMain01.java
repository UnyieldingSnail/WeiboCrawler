package com.zhangyujie.test;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class TestMain01 {
    public static void main(String[] args) {
//        ExecutorService service = Executors.newCachedThreadPool();
//        service.submit(new TestDemo02());
        // '2017-06-27 00:00:00'
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        System.out.println(sdf.format(new Date(15)));
    }
}
