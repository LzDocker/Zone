package com.dcoker.zone.entity;

/**
 * Created by Mr.Zhang on 2017/7/24.
 */

public class Result {

    private Boolean state;

    public Boolean getState() {
        return state;
    }

    public void setState(Boolean state) {
        this.state = state;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public UserExecution getData() {
        return data;
    }

    public void setData(UserExecution data) {
        this.data = data;
    }

    private String error;
    private UserExecution data;


}
