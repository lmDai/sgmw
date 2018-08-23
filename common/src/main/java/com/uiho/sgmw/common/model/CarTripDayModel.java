package com.uiho.sgmw.common.model;

import java.io.Serializable;
import java.util.List;

/**
 * 作者：uiho_mac
 * 时间：2018/5/18
 * 描述：
 * 版本：1.0
 * 修订历史：
 */

public class CarTripDayModel implements  Serializable{


    /**
     * driveDate : 2018-04-16
     * vin : LK6ADCE20GB001287
     * infoArray : [{"beginTime":"2018-04-16 12:02:02","endTime":"2018-04-16 12:02:02","driveCostTime":0,"beginPowerPercent":"80%","endPowerPercent":"70%","costPowerKWH":0.99,"driveMile":0,"maxSpeed":0,"avgSpeed":0},{"beginTime":"2018-04-16 12:02:02","endTime":"2018-04-16 12:02:02","driveCostTime":0,"beginPowerPercent":"80%","endPowerPercent":"70%","costPowerKWH":0.99,"driveMile":0,"maxSpeed":0,"avgSpeed":0}]
     */

    private String driveDate;
    private String vin;
    private List<InfoArrayBean> infoArray;

    public String getDriveDate() {
        return driveDate;
    }

    public void setDriveDate(String driveDate) {
        this.driveDate = driveDate;
    }

    public String getVin() {
        return vin;
    }

    public void setVin(String vin) {
        this.vin = vin;
    }

    public List<InfoArrayBean> getInfoArray() {
        return infoArray;
    }

    public void setInfoArray(List<InfoArrayBean> infoArray) {
        this.infoArray = infoArray;
    }

    public static class InfoArrayBean implements Serializable{
        /**
         * beginTime : 2018-04-16 12:02:02
         * endTime : 2018-04-16 12:02:02
         * driveCostTime : 0
         * beginPowerPercent : 80%
         * endPowerPercent : 70%
         * costPowerKWH : 0.99
         * driveMile : 0
         * maxSpeed : 0
         * avgSpeed : 0
         */

        private String beginTime;
        private String endTime;
        private int driveCostTime;
        private String beginPowerPercent;
        private String endPowerPercent;
        private double costPowerKWH;
        private int driveMile;
        private int maxSpeed;
        private int avgSpeed;

        public String getBeginTime() {
            return beginTime;
        }

        public void setBeginTime(String beginTime) {
            this.beginTime = beginTime;
        }

        public String getEndTime() {
            return endTime;
        }

        public void setEndTime(String endTime) {
            this.endTime = endTime;
        }

        public int getDriveCostTime() {
            return driveCostTime;
        }

        public void setDriveCostTime(int driveCostTime) {
            this.driveCostTime = driveCostTime;
        }

        public String getBeginPowerPercent() {
            return beginPowerPercent;
        }

        public void setBeginPowerPercent(String beginPowerPercent) {
            this.beginPowerPercent = beginPowerPercent;
        }

        public String getEndPowerPercent() {
            return endPowerPercent;
        }

        public void setEndPowerPercent(String endPowerPercent) {
            this.endPowerPercent = endPowerPercent;
        }

        public double getCostPowerKWH() {
            return costPowerKWH;
        }

        public void setCostPowerKWH(double costPowerKWH) {
            this.costPowerKWH = costPowerKWH;
        }

        public int getDriveMile() {
            return driveMile;
        }

        public void setDriveMile(int driveMile) {
            this.driveMile = driveMile;
        }

        public int getMaxSpeed() {
            return maxSpeed;
        }

        public void setMaxSpeed(int maxSpeed) {
            this.maxSpeed = maxSpeed;
        }

        public int getAvgSpeed() {
            return avgSpeed;
        }

        public void setAvgSpeed(int avgSpeed) {
            this.avgSpeed = avgSpeed;
        }
    }
}
