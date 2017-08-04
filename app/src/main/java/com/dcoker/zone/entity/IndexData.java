package com.dcoker.zone.entity;

/**
 * Created by Mr.Zhang on 2017/7/24.
 */

public class IndexData {


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

}
