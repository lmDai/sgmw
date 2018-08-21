package com.uiho.sgmw.common.model;

/**
 * 下载内容
 */
public class DownloadModel {
    private long total;
    private long bytesReaded;

    public DownloadModel(long total, long bytesReaded) {
        this.total = total;
        this.bytesReaded = bytesReaded;
    }

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }

    public long getBytesReaded() {
        return bytesReaded;
    }

    public void setBytesReaded(long bytesReaded) {
        this.bytesReaded = bytesReaded;
    }
}
