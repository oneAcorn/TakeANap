package com.su.hang.nap.bean;

import com.su.hang.nap.base.BaseBean;

public class ParameterBean extends BaseBean {
    private long timeStart;
    private long timeEnd;
    private String tip;
    private int intervalMinutes;
    private int speakTimes;
    private int vibratorTime;

    public long getTimeStart() {
        return timeStart;
    }

    public void setTimeStart(long timeStart) {
        this.timeStart = timeStart;
    }

    public long getTimeEnd() {
        return timeEnd;
    }

    public int getSpeakTimes() {
        return speakTimes;
    }

    public void setSpeakTimes(int speakTimes) {
        this.speakTimes = speakTimes;
    }

    public void setTimeEnd(long timeEnd) {
        this.timeEnd = timeEnd;
    }

    public String getTip() {
        return tip;
    }

    public void setTip(String tip) {
        this.tip = tip;
    }

    public int getIntervalMinutes() {
        return intervalMinutes;
    }

    public void setIntervalMinutes(int intervalMinutes) {
        this.intervalMinutes = intervalMinutes;
    }

    public int getVibratorTime() {
        return vibratorTime;
    }

    public void setVibratorTime(int vibratorTime) {
        this.vibratorTime = vibratorTime;
    }
}
