package com.uiho.sgmw.common.widget.dialog;

import android.app.Dialog;
import android.content.Context;

import com.uiho.sgmw.common.R;
import com.uiho.sgmw.common.widget.NumberProgressBar;

/**
 * 作者：uiho_mac
 * 时间：2018/7/19
 * 描述：更新下载框
 * 版本：1.0
 * 修订历史：
 */
public class AppUpdateProgressDialog extends Dialog {

    private NumberProgressBar numberProgressBar;

    public AppUpdateProgressDialog(Context context) {
        super(context, R.style.Custom_Progress);
        initLayout();
    }

    public AppUpdateProgressDialog(Context context, int theme) {
        super(context, R.style.Custom_Progress);
        initLayout();
    }

    private void initLayout() {
        this.setContentView(R.layout.update_progress_layout);
        getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        numberProgressBar = (NumberProgressBar) findViewById(R.id.number_progress);
        this.setCanceledOnTouchOutside(false);//点击dialog背景部分不消失
//        this.setCancelable(false);//dialog出现时，点击back键不消失
    }

    public void setProgress(int mProgress) {
        numberProgressBar.setProgress(mProgress);
    }
}