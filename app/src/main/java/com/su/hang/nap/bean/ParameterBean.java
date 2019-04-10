package com.su.hang.nap.bean;

import com.su.hang.nap.base.BaseBean;

public class ParameterBean extends BaseBean {
    private String time;
    private String tip;
    private int vibratorTime;

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getTip() {
        return tip;
    }

    public void setTip(String tip) {
        this.tip = tip;
    }

    public int getVibratorTime() {
        return vibratorTime;
    }

    public void setVibratorTime(int vibratorTime) {
        this.vibratorTime = vibratorTime;
    }
}
