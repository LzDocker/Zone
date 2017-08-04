package com.dcoker.zone.entity;

import java.util.List;

public class articleCommentRepy {

	List<CommentReply> commentRepyList;
	
	public List<CommentReply> getCommentRepyList() {
		return commentRepyList;
	}

	public void setCommentRepyList(List<CommentReply> commentRepyList) {
		this.commentRepyList = commentRepyList;
	}

	public Article getArticle() {
		return article;
	}

	public void setArticle(Article article) {
		this.article = article;
	}

	Article article;

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	User user;


}
