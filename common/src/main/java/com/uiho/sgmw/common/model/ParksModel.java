package com.uiho.sgmw.common.model;

/**
 * 作者：uiho_mac
 * 时间：2018/7/17
 * 描述：停车位
 * 版本：1.0
 * 修订历史：
 */
public class ParksModel {
    /**
     * address : null
     * latitude : 24.330149
     * name : 光伏
     * id : 1
     * used : 1
     * totalCount : 20
     * longitude : 109.370552
     */

    private String address;
    private double latitude;
    private String name;
    private String id;
    private int used;
    private int totalCount;
    private double longitude;
    private boolean isSelect;
    /**
     * address : null
     * distance : 0.27399253679253116
     * latitude : 24.310481
     * longitude : 109.435879
     */

    private double distance;

    public boolean isSelect() {
        return isSelect;
    }

    public void setSelect(boolean select) {
        isSelect = select;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getUsed() {
        return used;
    }

    public void setUsed(int used) {
        this.used = used;
    }

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }
}
