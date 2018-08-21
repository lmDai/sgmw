package com.uiho.module_palm.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;

import com.uiho.module_palm.R;
import com.uiho.sgmw.common.base.BaseMvpActivity;
import com.uiho.sgmw.common.base.BasePresenter;
import com.uiho.sgmw.common.base.IBaseView;
import com.uiho.sgmw.common.utils.StatusBarCompatUtils;

/**
 * 作者：uiho_mac
 * 时间：2018/8/8
 * 描述：掌上宝骏基类
 * 版本：1.0
 * 修订历史：
 */
public abstract class BasePalmActivity<V extends IBaseView, P extends BasePresenter<V>> extends BaseMvpActivity<V, P> {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StatusBarCompatUtils.setColor(mContext, ContextCompat.getColor(mContext, R.color.colorPrimaryDark));
    }
}
