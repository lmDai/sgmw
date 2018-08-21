package com.uiho.sgmw.common.base;

/**
 * 作者：uiho_mac
 * 时间：2018/8/6
 * 描述：
 * 版本：1.0
 * 修订历史：
 */
public class BaseResponse<T> {
    /**
     * code : 200
     * message : success
     * result : {"password":"e10adc3949ba59abbe56e057f20f883e","phoneNum":"13000000000","state":0,"token":"9e0be2aa-03cb-47ba-a97b-a6471c606957","userId":1,"xm":"zhangsan"}
     * success : true
     * timestamp : 2018-05-08 13:38:00
     */

    private int code;
    private String message;
    private T result;
    private boolean success;
    private String timestamp;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getResult() {
        return result;
    }

    public void setResult(T result) {
        this.result = result;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public <T> Optional<T> transform() {
        return new Optional(result);
    }
}
