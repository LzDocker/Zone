package com.dcoker.zone.entity;

/**
 * Created by Mr.Zhang on 2017/7/25.
 */

public class commenResult {


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

    }


}
