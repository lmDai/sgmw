package com.uiho.sgmw.common.model;

import java.io.Serializable;
import java.util.List;

/**
 * 作者：uiho_mac
 * 时间：2018/5/20
 * 描述：
 * 版本：1.0
 * 修订历史：
 */

public class ChargingDayModel implements  Serializable{

    /**
     * chargeDate : 2017-06-18
     * vin : LK6ADCE20GB001287
     * infoArray : [{"beginTime":"2018-04-16 12:02:02","endTime":"2018-04-16 14:02:02","chargeCostTime":0,"beginPowerPercent":"80%","endPowerPercent":"70%","chargePowerKWH":0.99},{"beginTime":"2018-04-16 12:02:02","endTime":"2018-04-16 14:02:02","chargeCostTime ":0,"beginPowerPercent":"80%","endPowerPercent":"70%","chargePowerKWH":0.99}]
     */

    private String chargeDate;
    private String vin;
    private List<InfoArrayBean> infoArray;

    public String getChargeDate() {
        return chargeDate;
    }

    public void setChargeDate(String chargeDate) {
        this.chargeDate = chargeDate;
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
        /**
         * beginTime : 2018-04-16 12:02:02
         * endTime : 2018-04-16 14:02:02
         * chargeCostTime : 0
         * beginPowerPercent : 80%
         * endPowerPercent : 70%
         * chargePowerKWH : 0.99
         * chargeCostTime  : 0
         */

        private String beginTime;
        private String endTime;
        private String beginPowerPercent;
        private String endPowerPercent;
        private double chargePowerKWH;
        private int chargeCostTime;

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

        public double getChargePowerKWH() {
            return chargePowerKWH;
        }

        public void setChargePowerKWH(double chargePowerKWH) {
            this.chargePowerKWH = chargePowerKWH;
        }

        public int getChargeCostTime() {
            return chargeCostTime;
        }

        public void setChargeCostTime(int chargeCostTime) {
            this.chargeCostTime = chargeCostTime;
        }
    }
}
