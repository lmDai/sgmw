package com.uiho.sgmw.common.base;

/**
 * 作者：uiho_mac
 * 时间：2018/8/6
 * 描述：
 * 版本：1.0
 * 修订历史：
 */
public class BaseApi {
    public final static String BASE_HOST = "http://testapp.sgmw.cqbaowei.cn:8686/";
    public final static String VERSION_HOST = "http://app.uiho.com/sgmwapp/";
    public final static String OTHER_HOST = "http://222.217.61.58:10000/sgmw-ve-api/";

    public enum HostType {
        BASE_TYPE,
        VERSION_TYPE,
        OTHER_TYPE
    }

    private static String sHost;

    public static void host(HostType hostType, boolean isDebug) {
        if (isDebug) {
            switch (hostType) {
                case BASE_TYPE:
                    sHost = BASE_HOST;//测试环境
                    break;
                case VERSION_TYPE:
                    sHost = VERSION_HOST;//军哥
                    break;
                case OTHER_TYPE:
                    sHost = OTHER_HOST;//陈小兵
                    break;
                default:
                    sHost = BASE_HOST;//测试环境
                    break;
            }
        } else {
            sHost = BASE_HOST;
        }
    }

    public static String getBaseUrl() {
        return sHost == null ? BASE_HOST : sHost;
    }
}
