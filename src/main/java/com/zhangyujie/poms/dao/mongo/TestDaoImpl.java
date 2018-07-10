package com.zhangyujie.poms.dao.mongo;

import javax.annotation.Resource;

import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

import com.mongodb.WriteResult;
import com.zhangyujie.poms.entity.MongoTest;

@Repository("testDao")
public class TestDaoImpl implements TestDao{
	
	@Resource(name="mongoTemplate")
	private MongoOperations mo;
	
	public String save(MongoTest test) {
		mo.insert(test);
		return test.getId();
	}

	public int update(MongoTest test) {
		Query query = new Query();
		query.addCriteria(Criteria.where("id").is(test.getId()).and("name").is(test.getName()));
		Update update = new Update();
		update.set("age", test.getAge());
		WriteResult result = mo.updateMulti(query, update, MongoTest.class);
		return result.getN();
	}

	public MongoTest get(String name) {
		Query query = new Query();
		query.addCriteria(Criteria.where("name").is(name));
		return mo.findOne(query, MongoTest.class);
	}

	public void remove(String id) {
		mo.remove(Query.query(Criteria.where("id").is(id)), MongoTest.class);
	}

}
