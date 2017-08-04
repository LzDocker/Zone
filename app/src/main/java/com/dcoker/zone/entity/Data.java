package com.dcoker.zone.entity;

import java.util.List;

/**
 * Created by Mr.Zhang on 2017/7/24.
 */

public class Data {




    private int state;
    private String stateInfo;
    private List<Attenlist> attenList;
    public void setState(int state) {
        this.state = state;
    }
    public int getState() {
        return state;
    }

    public void setStateInfo(String stateInfo) {
        this.stateInfo = stateInfo;
    }
    public String getStateInfo() {
        return stateInfo;
    }

    public void setAttenList(List<Attenlist> attenList) {
        this.attenList = attenList;
    }
    public List<Attenlist> getAttenList() {
        return attenList;
    }

}
