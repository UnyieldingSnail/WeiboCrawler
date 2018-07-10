package com.zhangyujie.poms.dao.mongo;

import com.zhangyujie.poms.entity.MongoTest;

public interface TestDao {
	public String save(MongoTest test);
	public int update(MongoTest test);
	public MongoTest get(String name);
    public void remove(String id);
}
