package com.dcoker.zone.entity;

import java.io.Serializable;

/**
 * Created by Mr.Zhang on 2017/7/24.
 */

public class Article implements Serializable {

    private int id;
    private int uid;
    private String title;
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    @Override
    public String toString() {
        return "Article [id=" + id + ", uid=" + uid + ", atype=" + atype + ", descption=" + descption + ", picture="
                + picture + ", likce=" + likces + ", comment=" + comment + ", group=" + grouptag + ", createtime="
                + createtime + ", updatime=" + updatime + "]";
    }
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public int getUid() {
        return uid;
    }
    public void setUid(int uid) {
        this.uid = uid;
    }
    public int getAtype() {
        return atype;
    }
    public void setAtype(int atype) {
        this.atype = atype;
    }
    public String getDescption() {
        return descption;
    }
    public void setDescption(String descption) {
        this.descption = descption;
    }
    public String getPicture() {
        return picture;
    }
    public void setPicture(String picture) {
        this.picture = picture;
    }
    public int getLikces() {
        return likces;
    }
    public void setLikces(int likces) {
        this.likces = likces;
    }
    public int getComment() {
        return comment;
    }
    public void setComment(int comment) {
        this.comment = comment;
    }
    public String getGrouptag() {
        return grouptag;
    }
    public void setGrouptag(String grouptag) {
        this.grouptag = grouptag;
    }
    public String getCreatetime() {
        return createtime;
    }
    public void setCreatetime(String createtime) {
        this.createtime = createtime;
    }
    public String getUpdatime() {
        return updatime;
    }
    public void setUpdatime(String updatime) {
        this.updatime = updatime;
    }
    private int atype;
    private String descption;
    private String picture;

    public Boolean getAttenState() {
        return attenState;
    }

    public void setAttenState(Boolean attenState) {
        this.attenState = attenState;
    }

    public Boolean getLikeState() {
        return likeState;
    }

    public void setLikeState(Boolean likeState) {
        this.likeState = likeState;
    }

    private int likces;
    private int comment;
    private String grouptag;
    private String createtime;
    private String updatime;

    private Boolean attenState;
    private Boolean likeState;


}