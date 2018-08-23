package com.uiho.module_palm.ui.activity;

import android.os.Bundle;

import com.uiho.module_palm.R;
import com.uiho.sgmw.common.base.BaseActivity;


/**
 * 作者：uiho_mac
 * 时间：2018/5/10
 * 描述：应用通知
 * 版本：1.0
 * 修订历史
 */
public class NotificationActivity extends BaseActivity {

    @Override
    protected int getLayout() {
        return R.layout.activity_notification;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        showBack = true;
        setTopTitle("通知提醒");
    }

}
