package com.uiho.sgmw.common.model;

import java.util.Arrays;

/**
 * Created by dell1 on 2017/5/28.
 */
public class EchartsBarBean {

    public String type;
    public String title;
    public int maxValue;
    public int minValue;
    public String imageUrl;
    public String[] times;
    public String[] steps;
    public String unit;

    @Override
    public String toString() {
        return "EchartsBarBean{" +
                "type='" + type + '\'' +
                ", title='" + title + '\'' +
                ", maxValue=" + maxValue +
                ", minValue=" + minValue +
                ", imageUrl='" + imageUrl + '\'' +
                ", times=" + Arrays.toString(times) +
                ", steps=" + Arrays.toString(steps) +
                ", unit='" + unit + '\'' +
                '}';
    }
}
