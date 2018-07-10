package com.zhangyujie.poms.entity;

import java.io.Serializable;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection="test")
public class MongoTest implements Serializable{
	
	@Id
	private String id;
	private String sid;
	private String name;
	private Integer age;
	
	@PersistenceConstructor
	public MongoTest(String id, String sid, String name, Integer age) {
		super();
		this.id = id;
		this.sid = sid;
		this.name = name;
		this.age = age;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getSid() {
		return sid;
	}

	public void setSid(String sid) {
		this.sid = sid;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getAge() {
		return age;
	}

	public void setAge(Integer age) {
		this.age = age;
	}

	@Override
	public String toString() {
		return "MongoTest [id=" + id + ", sid=" + sid + ", name=" + name + ", age=" + age + "]";
	}
	
}
