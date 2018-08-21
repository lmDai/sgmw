package com.uiho.sgmw.module_login;

import com.uiho.sgmw.common.base.BaseApi;
import com.uiho.sgmw.common.base.BaseApplication;

/**
 * 作者：uiho_mac
 * 时间：2018/8/6
 * 描述：
 * 版本：1.0
 * 修订历史：
 */
public class MainApplication extends BaseApplication {
    @Override
    public void onCreate() {
        super.onCreate();
        BaseApi.host(BaseApi.HostType.BASE_TYPE, true);//初始化api环境
    }
}
