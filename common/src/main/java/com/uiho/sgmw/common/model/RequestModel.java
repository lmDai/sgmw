package com.uiho.sgmw.common.model;

/**
 * 作者：uiho_mac
 * 时间：2018/7/19
 * 描述：
 * 版本：1.0
 * 修订历史：
 */
public class RequestModel {

    /**
     * reqHead : {"authKey":"35B6EF141D3242469711943F4C769DB2","encryptMode":"AES","reqGUID":"reqGUIDValue","reqSign":"reqSignValue"}
     * reqBody : {"reqContent":"myReqBodyAES_BASE64Value"}
     */

    private ReqHeadBean reqHead;
    private ReqBodyBean reqBody;

    public ReqHeadBean getReqHead() {
        return reqHead;
    }

    public void setReqHead(ReqHeadBean reqHead) {
        this.reqHead = reqHead;
    }

    public ReqBodyBean getReqBody() {
        return reqBody;
    }

    public void setReqBody(ReqBodyBean reqBody) {
        this.reqBody = reqBody;
    }

    public static class ReqHeadBean {
        /**
         * authKey : 35B6EF141D3242469711943F4C769DB2
         * encryptMode : AES
         * reqGUID : reqGUIDValue
         * reqSign : reqSignValue
         */

        private String authKey;
        private String encryptMode;
        private String reqGUID;
        private String reqSign;

        public String getAuthKey() {
            return authKey;
        }

        public void setAuthKey(String authKey) {
            this.authKey = authKey;
        }

        public String getEncryptMode() {
            return encryptMode;
        }

        public void setEncryptMode(String encryptMode) {
            this.encryptMode = encryptMode;
        }

        public String getReqGUID() {
            return reqGUID;
        }

        public void setReqGUID(String reqGUID) {
            this.reqGUID = reqGUID;
        }

        public String getReqSign() {
            return reqSign;
        }

        public void setReqSign(String reqSign) {
            this.reqSign = reqSign;
        }

        public ReqHeadBean(String authKey, String encryptMode, String reqGUID, String reqSign) {
            this.authKey = authKey;
            this.encryptMode = encryptMode;
            this.reqGUID = reqGUID;
            this.reqSign = reqSign;
        }
    }

    public static class ReqBodyBean {

        public ReqBodyBean(String reqContent) {
            this.reqContent = reqContent;
        }

        /**
         * reqContent : myReqBodyAES_BASE64Value
         */


        private String reqContent;

        public String getReqContent() {
            return reqContent;
        }

        public void setReqContent(String reqContent) {
            this.reqContent = reqContent;
        }
    }
}
