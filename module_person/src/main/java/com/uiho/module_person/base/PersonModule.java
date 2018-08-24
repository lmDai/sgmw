package com.uiho.module_person.base;

import com.uiho.sgmw.common.https.RetrofitManager;

/**
 * 作者：uiho_mac
 * 时间：2018/8/24
 * 描述：
 * 版本：1.0
 * 修订历史：
 */
public class PersonModule {
    public static PersonService createrRetofit() {
        return RetrofitManager.getInstance().getRetrofitService(PersonService.class);
    }
}
