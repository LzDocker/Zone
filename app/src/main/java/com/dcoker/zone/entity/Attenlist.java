package com.dcoker.zone.entity;

/**
 * Created by Mr.Zhang on 2017/7/24.
 */

public class Attenlist {

    private User user;
    private Article article;
    private Comment comment;
    public void setUser(User user) {
        this.user = user;
    }
    public User getUser() {
        return user;
    }

    public void setArticle(Article article) {
        this.article = article;
    }
    public Article getArticle() {
        return article;
    }

    public void setComment(Comment comment) {
        this.comment = comment;
    }
    public Comment getComment() {
        return comment;
    }




}
