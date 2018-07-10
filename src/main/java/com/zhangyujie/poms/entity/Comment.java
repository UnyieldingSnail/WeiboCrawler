package com.zhangyujie.poms.entity;

import java.io.Serializable;

public class Comment implements Serializable {
	
	private String commentId;
	private String userId;
	private String userName;
	private String text;
	private String createdAt;
	
	public Comment(String userId, String userName, String commentId, String text, String createdAt) {
		super();
		this.userId = userId;
		this.userName = userName;
		this.commentId = commentId;
		this.text = text;
		this.createdAt = createdAt;
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
	public String getCommentId() {
		return commentId;
	}
	public void setCommentId(String commentId) {
		this.commentId = commentId;
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
	@Override
	public String toString() {
		return "Comment [userId=" + userId + ", userName=" + userName + ", commentId=" + commentId
				+ ", text=" + text + ", createdAt=" + createdAt + "]";
	}
	
}
