package com.uiho.sgmw.common.model;

/**
 * 作者：uiho_mac
 * 时间：2018/5/11
 * 描述：防盗警报查询
 * 版本：1.0
 * 修订历史：
 */

public class BurglarAlarmModel {

    private int alarmState;
    private String vin;

    public int getAlarmState() {
        return alarmState;
    }

    public void setAlarmState(int alarmState) {
        this.alarmState = alarmState;
    }

    public String getVin() {
        return vin;
    }

    public void setVin(String vin) {
        this.vin = vin;
    }
}
