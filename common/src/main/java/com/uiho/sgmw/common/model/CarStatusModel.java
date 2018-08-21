package com.uiho.sgmw.common.model;

import java.io.Serializable;

/**
 * 作者：uiho_mac
 * 时间：2018/5/11
 * 描述：车辆状态
 * 版本：1.0
 * 修订历史：
 */

public class CarStatusModel implements Serializable {
    /**
     * vin : L4AD5XC5EA2XD5777
     * leftFrontDoor : 1
     * rightFrontDoor : 1
     * backDoor : 0
     * leftTurnLight : 1
     * rightTurnLight : 1
     * positionLight : 0
     * network : 27
     * drivingState : 0
     * totalMileage : 1000
     * currentPower : 12
     * enduranceMileage : 80
     * longitudeGD : 11.11
     * latitudeGD : 11.11
     * orientation : 2.2
     * canCollectionTime : 2018-05-11 05:05:05
     * gpsCollectionTime : 2018-05-11 05:05:05
     */

    private String vin;
    private String leftFrontDoor;
    private String rightFrontDoor;
    private String backDoor;
    private String leftTurnLight;
    private String rightTurnLight;
    private String positionLight;
    private String network;
    private String drivingState;
    private String totalMileage;
    private String currentPowerPercent;
    private String enduranceMileage;
    private double longitudeGD;
    private double latitudeGD;
    private double orientation;
    private String canCollectionTime;
    private String gpsCollectionTime;
    private String time;

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getVin() {
        return vin;
    }

    public void setVin(String vin) {
        this.vin = vin;
    }

    public String getLeftFrontDoor() {
        return leftFrontDoor;
    }

    public void setLeftFrontDoor(String leftFrontDoor) {
        this.leftFrontDoor = leftFrontDoor;
    }

    public String getRightFrontDoor() {
        return rightFrontDoor;
    }

    public void setRightFrontDoor(String rightFrontDoor) {
        this.rightFrontDoor = rightFrontDoor;
    }

    public String getBackDoor() {
        return backDoor;
    }

    public void setBackDoor(String backDoor) {
        this.backDoor = backDoor;
    }

    public String getLeftTurnLight() {
        return leftTurnLight;
    }

    public void setLeftTurnLight(String leftTurnLight) {
        this.leftTurnLight = leftTurnLight;
    }

    public String getRightTurnLight() {
        return rightTurnLight;
    }

    public void setRightTurnLight(String rightTurnLight) {
        this.rightTurnLight = rightTurnLight;
    }

    public String getPositionLight() {
        return positionLight;
    }

    public void setPositionLight(String positionLight) {
        this.positionLight = positionLight;
    }

    public String getNetwork() {
        return network;
    }

    public void setNetwork(String network) {
        this.network = network;
    }

    public String getDrivingState() {
        return drivingState;
    }

    public void setDrivingState(String drivingState) {
        this.drivingState = drivingState;
    }

    public String getTotalMileage() {
        return totalMileage;
    }

    public void setTotalMileage(String totalMileage) {
        this.totalMileage = totalMileage;
    }

    public String getCurrentPowerPercent() {
        return currentPowerPercent;
    }

    public void setCurrentPowerPercent(String currentPowerPercent) {
        this.currentPowerPercent = currentPowerPercent;
    }

    public String getEnduranceMileage() {
        return enduranceMileage;
    }

    public void setEnduranceMileage(String enduranceMileage) {
        this.enduranceMileage = enduranceMileage;
    }

    public double getLongitudeGD() {
        return longitudeGD;
    }

    public void setLongitudeGD(double longitudeGD) {
        this.longitudeGD = longitudeGD;
    }

    public double getLatitudeGD() {
        return latitudeGD;
    }

    public void setLatitudeGD(double latitudeGD) {
        this.latitudeGD = latitudeGD;
    }

    public double getOrientation() {
        return orientation;
    }

    public void setOrientation(double orientation) {
        this.orientation = orientation;
    }

    public String getCanCollectionTime() {
        return canCollectionTime;
    }

    public void setCanCollectionTime(String canCollectionTime) {
        this.canCollectionTime = canCollectionTime;
    }

    public String getGpsCollectionTime() {
        return gpsCollectionTime;
    }

    public void setGpsCollectionTime(String gpsCollectionTime) {
        this.gpsCollectionTime = gpsCollectionTime;
    }
}
