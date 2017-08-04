package com.dcoker.zone.entity;

import java.io.Serializable;

/**
 * Created by Mr.Zhang on 2017/7/24.
 */

public class User implements Serializable {


    private int id;
    private int articles;
    private String uuid;
    private String uname;
    private String upwd;
    private int utype;
    private String phone;
    private String uicon;
    private String descption;
    private int atten;
    private int attend;
    private int likes;
    private String createtime;
    private String updatetime;
    public void setId(int id) {
        this.id = id;
    }
    public int getId() {
        return id;
    }

    public void setArticles(int articles) {
        this.articles = articles;
    }
    public int getArticles() {
        return articles;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }
    public String getUuid() {
        return uuid;
    }

    public void setUname(String uname) {
        this.uname = uname;
    }
    public String getUname() {
        return uname;
    }

    public void setUpwd(String upwd) {
        this.upwd = upwd;
    }
    public String getUpwd() {
        return upwd;
    }

    public void setUtype(int utype) {
        this.utype = utype;
    }
    public int getUtype() {
        return utype;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
    public String getPhone() {
        return phone;
    }

    public void setUicon(String uicon) {
        this.uicon = uicon;
    }
    public String getUicon() {
        return uicon;
    }

    public void setDescption(String descption) {
        this.descption = descption;
    }
    public String getDescption() {
        return descption;
    }

    public void setAtten(int atten) {
        this.atten = atten;
    }
    public int getAtten() {
        return atten;
    }

    public void setAttend(int attend) {
        this.attend = attend;
    }
    public int getAttend() {
        return attend;
    }

    public void setLikes(int likes) {
        this.likes = likes;
    }
    public int getLikes() {
        return likes;
    }

    public void setCreatetime(String createtime) {
        this.createtime = createtime;
    }
    public String getCreatetime() {
        return createtime;
    }

    public void setUpdatetime(String updatetime) {
        this.updatetime = updatetime;
    }
    public String getUpdatetime() {
        return updatetime;
    }


}
