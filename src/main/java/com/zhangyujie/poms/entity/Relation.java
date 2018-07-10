package com.zhangyujie.poms.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document(collection="relation")
public class Relation {
    @Id
    private String userId;
    private List<String> follows;

    @PersistenceConstructor
    public Relation(String userId, List<String> follows) {
        this.userId = userId;
        this.follows = follows;
    }


    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public List<String> getFollows() {
        return follows;
    }

    public void setFollows(List<String> follows) {
        this.follows = follows;
    }

    @Override
    public String toString() {
        return "Relation{" +
                "userId='" + userId + '\'' +
                ", follows=" + follows +
                '}';
    }
}
