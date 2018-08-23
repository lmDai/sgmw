package com.uiho.sgmw.common.model;

import java.util.List;

/**
 * 作者：uiho_mac
 * 时间：2018/7/26
 * 描述：
 * 版本：1.0
 * 修订历史：
 */
public class ResponseContent {

    private List<ParksModel> infoArray;

    public List<ParksModel> getInfoArray() {
        return infoArray;
    }

    public void setInfoArray(List<ParksModel> infoArray) {
        this.infoArray = infoArray;
    }
}
