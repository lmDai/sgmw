package com.uiho.sgmw.common.model;

/**
 * 作者：uiho_mac
 * 时间：2018/5/19
 * 描述：电子围栏
 * 版本：1.0
 * 修订历史：
 */

public class CarSecurityFenceModel {


    /**
     * vin : LK6ADCE20GB001287
     * fenceState : 1
     * upLeftLatitude : 1.1
     * upLeftLongitude : 1.1
     * upRightLatitude : 1.1
     * upRightLongitude : 1.1
     * downLeftLatitude : 1.1
     * downLeftLongitude : 1.1
     * downRightLatitude : 1.1
     * downRightLongitude : 1.1
     */

    private String vin;
    private int fenceState;
    private Double upLeftLatitude;
    private Double upLeftLongitude;
    private Double upRightLatitude;
    private Double upRightLongitude;
    private Double downLeftLatitude;
    private Double downLeftLongitude;
    private Double downRightLatitude;
    private Double downRightLongitude;
    private String fenceGuid;

    public String getVin() {
        return vin;
    }

    public void setVin(String vin) {
        this.vin = vin;
    }

    public int getFenceState() {
        return fenceState;
    }

    public void setFenceState(int fenceState) {
        this.fenceState = fenceState;
    }

    public Double getUpLeftLatitude() {
        return upLeftLatitude;
    }

    public void setUpLeftLatitude(Double upLeftLatitude) {
        this.upLeftLatitude = upLeftLatitude;
    }

    public Double getUpLeftLongitude() {
        return upLeftLongitude;
    }

    public void setUpLeftLongitude(Double upLeftLongitude) {
        this.upLeftLongitude = upLeftLongitude;
    }

    public Double getUpRightLatitude() {
        return upRightLatitude;
    }

    public void setUpRightLatitude(Double upRightLatitude) {
        this.upRightLatitude = upRightLatitude;
    }

    public Double getUpRightLongitude() {
        return upRightLongitude;
    }

    public void setUpRightLongitude(Double upRightLongitude) {
        this.upRightLongitude = upRightLongitude;
    }

    public Double getDownLeftLatitude() {
        return downLeftLatitude;
    }

    public void setDownLeftLatitude(Double downLeftLatitude) {
        this.downLeftLatitude = downLeftLatitude;
    }

    public Double getDownLeftLongitude() {
        return downLeftLongitude;
    }

    public void setDownLeftLongitude(Double downLeftLongitude) {
        this.downLeftLongitude = downLeftLongitude;
    }

    public Double getDownRightLatitude() {
        return downRightLatitude;
    }

    public void setDownRightLatitude(Double downRightLatitude) {
        this.downRightLatitude = downRightLatitude;
    }

    public Double getDownRightLongitude() {
        return downRightLongitude;
    }

    public void setDownRightLongitude(Double downRightLongitude) {
        this.downRightLongitude = downRightLongitude;
    }

    public String getFenceGuid() {
        return fenceGuid;
    }

    public void setFenceGuid(String fenceGuid) {
        this.fenceGuid = fenceGuid;
    }
}
