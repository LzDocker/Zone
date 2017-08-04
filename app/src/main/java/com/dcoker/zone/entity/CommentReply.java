package com.dcoker.zone.entity;

import java.io.Serializable;
import java.util.List;

public class CommentReply implements Serializable{

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	private Comment comment;
	private List<Reply> repList;
	public Comment getComment() {
		return comment;
	}
	public void setComment(Comment comment) {
		this.comment = comment;
	}
	public List<Reply> getRepList() {
		return repList;
	}
	public void setRepList(List<Reply> repList) {
		this.repList = repList;
	}

	private User user;

	public List<User> getReplyUserList() {
		return replyUserList;
	}

	public void setReplyUserList(List<User> replyUserList) {
		this.replyUserList = replyUserList;
	}

	private List<User> replyUserList;
}
