package com.uiho.sgmw.common.model;

/**
 * 作者：uiho_mac
 * 时间：2018/8/7
 * 描述：用户信息
 * 版本：1.0
 * 修订历史：
 */
public class UserModel {
    private String phoneNum;
    private String xm;
    private int sex;
    private String avatar;
    private String vins;
    private String token;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getPhoneNum() {
        return phoneNum;
    }

    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }

    public String getXm() {
        return xm;
    }

    public void setXm(String xm) {
        this.xm = xm;
    }

    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getVins() {
        return vins;
    }

    public void setVins(String vins) {
        this.vins = vins;
    }
}
