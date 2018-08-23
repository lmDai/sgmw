package com.uiho.sgmw.common.model;

/**
 * 作者：uiho_mac
 * 时间：2018/7/19
 * 描述：
 * 版本：1.0
 * 修订历史：
 */
public class ParkRequestModel {

    public ParkRequestModel(double longitude, double latitude) {
        this.longitude = longitude;
        this.latitude = latitude;
    }

    /**
     * longitude : 109.370552
     * latitude : 24.330149
     */

    private double longitude;
    private double latitude;

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }
}
