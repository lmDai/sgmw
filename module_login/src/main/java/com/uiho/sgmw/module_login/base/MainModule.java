package com.uiho.sgmw.module_login.base;

import com.uiho.sgmw.common.base.BaseApi;
import com.uiho.sgmw.common.https.RetrofitManager;

/**
 * 作者：uiho_mac
 * 时间：2018/8/6
 * 描述：
 * 版本：1.0
 * 修订历史：
 */
public class MainModule {
    public static MainService createRetrofit() {
        return RetrofitManager.getInstance().getRetrofitService(MainService.class);
    }

    public static MainService createAppVerSionRetrofit() {
        return RetrofitManager.getInstance().getRetrofitServiceByUrl(BaseApi.VERSION_HOST, MainService.class);
    }
    public static MainService createDownLoadRetrofit() {
        return RetrofitManager.getInstance().getRetrofitDownLoad(BaseApi.VERSION_HOST, MainService.class);
    }
}
