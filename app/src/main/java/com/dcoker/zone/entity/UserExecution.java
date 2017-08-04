package com.dcoker.zone.entity;

/**
 * Created by Mr.Zhang on 2017/7/24.
 */

public class UserExecution {



    private int state;
    private String stateinfo;
    private User user;
    public void setState(int state) {
        this.state = state;
    }
    public int getState() {
        return state;
    }

    public void setStateinfo(String stateinfo) {
        this.stateinfo = stateinfo;
    }
    public String getStateinfo() {
        return stateinfo;
    }

    public void setUser(User user) {
        this.user = user;
    }
    public User getUser() {
        return user;
    }

}
