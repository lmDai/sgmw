package com.uiho.sgmw.module_login.ui.activity;

import android.annotation.SuppressLint;
import android.os.Bundle;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.uiho.sgmw.common.Constants;
import com.uiho.sgmw.common.base.BaseMvpActivity;
import com.uiho.sgmw.common.base.RouterPath;
import com.uiho.sgmw.common.mvp_senior.annotaions.CreatePresenterAnnotation;
import com.uiho.sgmw.common.utils.ARouterUtils;
import com.uiho.sgmw.common.utils.EventUtil;
import com.uiho.sgmw.common.utils.SPUtils;
import com.uiho.sgmw.common.utils.StringUtils;
import com.uiho.sgmw.common.utils.Utils;
import com.uiho.sgmw.module_login.R;
import com.uiho.sgmw.module_login.contract.SplashContract;
import com.uiho.sgmw.module_login.presenter.SplashPresenter;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;

/**
 * 作者：uiho_mac
 * 时间：2018/8/6
 * 描述：启动页面
 * 版本：1.0
 * 修订历史：
 */
@SuppressLint("Registered")
@CreatePresenterAnnotation(SplashPresenter.class)
@Route(path = RouterPath.SPLASH_ACTIVITY)
public class SplashActivity extends BaseMvpActivity<SplashContract.View, SplashPresenter> implements SplashContract.View {
    private Disposable disposable;

    @Override
    protected int getLayout() {
        return 0;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        disposable = Observable.timer(1000, TimeUnit.MILLISECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(aLong -> {
                    String token = (String) SPUtils.getParam(mContext, Constants.TOKEN, "");
                    if (!StringUtils.isEmpty(token)) {
                        getMvpPresenter().verifyLogin(token);
                    } else {
                        ARouterUtils.goPage(RouterPath.MAIN_ACTIVITY);
                        finish();
                    }
                });
    }

    @Override
    public void verifyLogin(boolean isLogin) {
        if (isLogin) {
            ARouterUtils.goPage(RouterPath.MAIN_ACTIVITY);
            finish();
        } else {
            EventUtil.showToast(mContext, Utils.getString(R.string.No_Login_Exception));
            ARouterUtils.goPage(RouterPath.LOGIN_ACTIVITY);
            finish();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (!disposable.isDisposed()) {
            disposable.dispose();
        }
    }
}
