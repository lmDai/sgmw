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

public class CarTripDaysModel implements Serializable {


    /**
     * driveDate : 2018-04-16
     * vin : LK6ADCE20GB001287
     * infoArray : [{"beginTime":"2018-04-16 12:02:02","endTime":"2018-04-16 12:02:02","driveCostTime":0,"beginPowerPercent":"80%","endPowerPercent":"70%","costPowerKWH":0.99,"driveMile":0,"maxSpeed":0,"avgSpeed":0},{"beginTime":"2018-04-16 12:02:02","endTime":"2018-04-16 12:02:02","driveCostTime":0,"beginPowerPercent":"80%","endPowerPercent":"70%","costPowerKWH":0.99,"driveMile":0,"maxSpeed":0,"avgSpeed":0}]
     */

    private String beginDate;
    private String endDate;
    private String vin;
    private List<InfoArrayBean> infoArray;

    public String getBeginDate() {
        return beginDate;
    }

    public void setBeginDate(String beginDate) {
        this.beginDate = beginDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
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

    public static class InfoArrayBean implements Serializable {
        private String travel_month;
        private String travel_day;
        private String driveCount;
        private String driveMile;
        private String driveCostPowerKWH;
        private String chargeCount;
        private String chargePowerKWH;

        public String getTravel_month() {
            return travel_month;
        }

        public void setTravel_month(String travel_month) {
            this.travel_month = travel_month;
        }

        public String getTravel_day() {
            return travel_day;
        }

        public void setTravel_day(String travel_day) {
            this.travel_day = travel_day;
        }

        public String getDriveCount() {
            return driveCount;
        }

        public void setDriveCount(String driveCount) {
            this.driveCount = driveCount;
        }

        public String getDriveMile() {
            return driveMile;
        }

        public void setDriveMile(String driveMile) {
            this.driveMile = driveMile;
        }

        public String getDriveCostPowerKWH() {
            return driveCostPowerKWH;
        }

        public void setDriveCostPowerKWH(String driveCostPowerKWH) {
            this.driveCostPowerKWH = driveCostPowerKWH;
        }

        public String getChargeCount() {
            return chargeCount;
        }

        public void setChargeCount(String chargeCount) {
            this.chargeCount = chargeCount;
        }

        public String getChargePowerKWH() {
            return chargePowerKWH;
        }

        public void setChargePowerKWH(String chargePowerKWH) {
            this.chargePowerKWH = chargePowerKWH;
        }
    }
}
