package com.uiho.module_palm.base;

import com.uiho.sgmw.common.base.BaseApi;
import com.uiho.sgmw.common.https.RetrofitManager;

/**
 * 作者：uiho_mac
 * 时间：2018/8/8
 * 描述：
 * 版本：1.0
 * 修订历史：
 */
public class PalmModule {
    public static PalmService createrRetofit(){
        return RetrofitManager.getInstance().getRetrofitService(PalmService.class);
    }
    public static PalmService createAppVerSionRetrofit() {
        return RetrofitManager.getInstance().getRetrofitServiceByUrl(BaseApi.OTHER_HOST, PalmService.class);
    }
}
