package com.uiho.sgmw.common.https.exception;

/**
 * 作者：uiho_mac
 * 时间：2018/3/1
 * 描述：服务器运行异常
 * 版本：1.0
 * 修订历史：新建
 */
public class ServerException extends RuntimeException {
    private String responseMessage;
    private int code;

    public String getResponseMessage() {
        return responseMessage;
    }

    public void setResponseMessage(String responseMessage) {
        this.responseMessage = responseMessage;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public ServerException(String responseMessage, int code) {
        this.responseMessage = responseMessage;
        this.code = code;
    }

    public ServerException(String responseMessage) {
        super(handleError(responseMessage));
    }

    private static String handleError(String responseMessage) {
        if (responseMessage == null || responseMessage.isEmpty()) {
            return "Server out:null";
        }
        return responseMessage;
    }
}
