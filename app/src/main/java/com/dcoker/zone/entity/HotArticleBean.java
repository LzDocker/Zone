package com.dcoker.zone.entity;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Mr.Zhang on 2017/8/3.
 */

public class HotArticleBean implements Serializable {



    private boolean state;
    private Data data;
    private String error;
    public void setState(boolean state) {
        this.state = state;
    }
    public boolean getState() {
        return state;
    }

    public void setData(Data data) {
        this.data = data;
    }
    public Data getData() {
        return data;
    }

    public void setError(String error) {
        this.error = error;
    }
    public String getError() {
        return error;
    }


    public class Data {

        private int state;

        private String stateInfo;
        private Article article;

        private List<Article> articleList;
        private List<CommentReply> commentReplyList;
        private List<articleCommentRepy> articleCommentReplyList;


        public int getState() {
            return state;
        }

        public void setState(int state) {
            this.state = state;
        }

        public String getStateInfo() {
            return stateInfo;
        }

        public void setStateInfo(String stateInfo) {
            this.stateInfo = stateInfo;
        }

        public Article getArticle() {
            return article;
        }

        public void setArticle(Article article) {
            this.article = article;
        }

        public List<Article> getArticleList() {
            return articleList;
        }

        public void setArticleList(List<Article> articleList) {
            this.articleList = articleList;
        }

        public List<CommentReply> getCommentReplyList() {
            return commentReplyList;
        }

        public void setCommentReplyList(List<CommentReply> commentReplyList) {
            this.commentReplyList = commentReplyList;
        }

        public List<articleCommentRepy> getArticleCommentReplyList() {
            return articleCommentReplyList;
        }

        public void setArticleCommentReplyList(List<articleCommentRepy> articleCommentReplyList) {
            this.articleCommentReplyList = articleCommentReplyList;
        }
    }



}
