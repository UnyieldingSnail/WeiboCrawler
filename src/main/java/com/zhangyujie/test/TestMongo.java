package com.zhangyujie.test;

import com.zhangyujie.global.env.GlobalEnv;
import com.zhangyujie.poms.dao.mongo.TestDao;
import com.zhangyujie.poms.entity.MongoTest;

public class TestMongo {
    public static void main(String[] args) {
        TestDao dao = GlobalEnv.CTX.getBean("testDao", TestDao.class);
        dao.save(new MongoTest("id", "aha", "bame", 15));
    }
}
