package com.uiho.sgmw.common.model;

import java.util.List;

/**
 * 作者：uiho_mac
 * 时间：2018/5/19
 * 描述：行驶轨迹
 * 版本：1.0
 * 修订历史：
 */

public class TripTrackModel {

    /**
     * vin : LK6ADCE20GB001287
     * beginTime :  2018-04-16 12:00:02
     * endTime :  2018-04-16 13:00:02
     * gpsArray : [{"collectTime":" 2018-04-16 12:00:02","longitudeGD":11.11,"latitudeGD":11.11},{"collectTime":" 2018-04-16 12:00:02","longitudeGD":11.11,"latitudeGD":11.11}]
     */

    private String vin;
    private String beginTime;
    private String endTime;
    private List<GpsArrayBean> gpsArray;

    public String getVin() {
        return vin;
    }

    public void setVin(String vin) {
        this.vin = vin;
    }

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

    public List<GpsArrayBean> getGpsArray() {
        return gpsArray;
    }

    public void setGpsArray(List<GpsArrayBean> gpsArray) {
        this.gpsArray = gpsArray;
    }

    public static class GpsArrayBean {
        /**
         * collectTime :  2018-04-16 12:00:02
         * longitudeGD : 11.11
         * latitudeGD : 11.11
         */

        private String collectTime;
        private double longitudeGD;
        private double latitudeGD;

        public String getCollectTime() {
            return collectTime;
        }

        public void setCollectTime(String collectTime) {
            this.collectTime = collectTime;
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
    }
}
