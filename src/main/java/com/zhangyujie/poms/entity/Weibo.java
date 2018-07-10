package com.zhangyujie.poms.entity;

import java.io.Serializable;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "weibo")
public class Weibo implements Serializable {
    @Id
    private String weiboId;
    private String userId;
    private String userName;
    private String userGender;
    private String text;
    private String createdAt;
    private Integer attitudesCount;
    private Integer commentsCount;
    private List<Comment> comments;
    private Integer repostsCount;
    private List<String> repostIds;

    @PersistenceConstructor
    public Weibo(String weiboId, String userId, String userName, String userGender, String text, String createdAt, Integer attitudesCount, Integer commentsCount, List<Comment> comments, Integer repostsCount, List<String> repostIds) {
        this.weiboId = weiboId;
        this.userId = userId;
        this.userName = userName;
        this.userGender = userGender;
        this.text = text;
        this.createdAt = createdAt;
        this.attitudesCount = attitudesCount;
        this.commentsCount = commentsCount;
        this.comments = comments;
        this.repostsCount = repostsCount;
        this.repostIds = repostIds;
    }

    public String getWeiboId() {
        return weiboId;
    }

    public void setWeiboId(String weiboId) {
        this.weiboId = weiboId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserGender() {
        return userGender;
    }

    public void setUserGender(String userGender) {
        this.userGender = userGender;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public Integer getAttitudesCount() {
        return attitudesCount;
    }

    public void setAttitudesCount(Integer attitudesCount) {
        this.attitudesCount = attitudesCount;
    }

    public Integer getCommentsCount() {
        return commentsCount;
    }

    public void setCommentsCount(Integer commentsCount) {
        this.commentsCount = commentsCount;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    public Integer getRepostsCount() {
        return repostsCount;
    }

    public void setRepostsCount(Integer repostsCount) {
        this.repostsCount = repostsCount;
    }

    public List<String> getRepostIds() {
        return repostIds;
    }

    public void setRepostIds(List<String> repostIds) {
        this.repostIds = repostIds;
    }

    @Override
    public String toString() {
        return "Weibo{" +
                "weiboId='" + weiboId + '\'' +
                ", userId='" + userId + '\'' +
                ", userName='" + userName + '\'' +
                ", userGender='" + userGender + '\'' +
                ", text='" + text + '\'' +
                ", createdAt='" + createdAt + '\'' +
                ", attitudesCount=" + attitudesCount +
                ", commentsCount=" + commentsCount +
                ", comments=" + comments +
                ", repostsCount=" + repostsCount +
                ", repostIds=" + repostIds +
                '}';
    }
}
