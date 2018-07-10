package com.zhangyujie.test;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class TestMain {
    public static void main(String[] args) {
        ExecutorService service = Executors.newCachedThreadPool();
        service.submit(new TestDemo01());
        service.submit(new TestDemo03());
    }
}
