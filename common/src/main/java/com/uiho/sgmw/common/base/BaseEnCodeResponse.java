package com.uiho.sgmw.common.base;

/**
 * 作者：uiho_mac
 * 时间：2018/7/26
 * 描述：
 * 版本：1.0
 * 修订历史：
 */
public class BaseEnCodeResponse<T>{

    /**
     * rspBody : {"rspContent":"mfSW+yi97gTr20T50tniCN3KYjjlimruhiOEoaaAMOIrINsAvXTFb9oPgnv5idWSqG4lS2q3QSRn07LEsn54LMugAaSscpB7q5cYRpcEsgSCVg8y8oXOb9nEEI+2xCwdCYHd0geT3G8lGtxyPGmy+mO42lAnJXG6DUh5AAK7SJJJ/3xE/dnEKRS2VJavNuhUSNyZYWjSjmW7m1I9c3Jshl1JZM7sAvYYCRrk7FtTflgC0M3dGVSBe/otR/j0UQxDP7ZjYNb6h65iggV9gkWUEhTHwdeb53wCK9p4lvqBJvoWUYROBkIDg2Rgj7quicIyprOEFK57zl8p4zRGMpcpMURkAzxqeX2nUYOZepeumES+P6XScwREV0DlFGWF8KDEA/sHtmPV/RB1Dwsei7gO9t1P241O+s3/DpKm0G0psG8="}
     * rspHead : {"rspCode":"00","rspMsg":"SUCCESS","rspTime":"2018-07-26 15:06:52"}
     */

    private RspBodyBean rspBody;
    private RspHeadBean rspHead;

    public RspBodyBean getRspBody() {
        return rspBody;
    }

    public void setRspBody(RspBodyBean rspBody) {
        this.rspBody = rspBody;
    }

    public RspHeadBean getRspHead() {
        return rspHead;
    }

    public void setRspHead(RspHeadBean rspHead) {
        this.rspHead = rspHead;
    }

    public class RspBodyBean {
        /**
         * rspContent : mfSW+yi97gTr20T50tniCN3KYjjlimruhiOEoaaAMOIrINsAvXTFb9oPgnv5idWSqG4lS2q3QSRn07LEsn54LMugAaSscpB7q5cYRpcEsgSCVg8y8oXOb9nEEI+2xCwdCYHd0geT3G8lGtxyPGmy+mO42lAnJXG6DUh5AAK7SJJJ/3xE/dnEKRS2VJavNuhUSNyZYWjSjmW7m1I9c3Jshl1JZM7sAvYYCRrk7FtTflgC0M3dGVSBe/otR/j0UQxDP7ZjYNb6h65iggV9gkWUEhTHwdeb53wCK9p4lvqBJvoWUYROBkIDg2Rgj7quicIyprOEFK57zl8p4zRGMpcpMURkAzxqeX2nUYOZepeumES+P6XScwREV0DlFGWF8KDEA/sHtmPV/RB1Dwsei7gO9t1P241O+s3/DpKm0G0psG8=
         */

        private T rspContent;

        public T getRspContent() {
            return rspContent;
        }

        public void setRspContent(T rspContent) {
            this.rspContent = rspContent;
        }
    }

    public static class RspHeadBean {
        /**
         * rspCode : 00
         * rspMsg : SUCCESS
         * rspTime : 2018-07-26 15:06:52
         */

        private String rspCode;
        private String rspMsg;
        private String rspTime;

        public String getRspCode() {
            return rspCode;
        }

        public void setRspCode(String rspCode) {
            this.rspCode = rspCode;
        }

        public String getRspMsg() {
            return rspMsg;
        }

        public void setRspMsg(String rspMsg) {
            this.rspMsg = rspMsg;
        }

        public String getRspTime() {
            return rspTime;
        }

        public void setRspTime(String rspTime) {
            this.rspTime = rspTime;
        }
    }
}
