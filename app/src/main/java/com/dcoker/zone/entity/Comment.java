package com.dcoker.zone.entity;

import java.io.Serializable;

/**
 * Created by Mr.Zhang on 2017/7/24.
 */

public class Comment implements Serializable {

    /**
     *  評論相關
     *
     */

    private int id;
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public int getAuid() {
        return auid;
    }
    public void setAuid(int auid) {
        this.auid = auid;
    }
    public int getArtid() {
        return artid;
    }
    public void setArtid(int artid) {
        this.artid = artid;
    }
    public String getDescption() {
        return descption;
    }
    public void setDescption(String descption) {
        this.descption = descption;
    }
    private int auid;
    private int artid;
    private String descption;
    private String createtime;
    public String getCreatetime() {
        return createtime;
    }
    public void setCreatetime(String createtime) {
        this.createtime = createtime;
    }
}
