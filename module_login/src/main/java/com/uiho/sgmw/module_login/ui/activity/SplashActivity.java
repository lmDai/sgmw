package com.uiho.sgmw.module_login.ui.activity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.uiho.sgmw.common.Constants;
import com.uiho.sgmw.common.base.BaseMvpActivity;
import com.uiho.sgmw.common.base.RouterPath;
import com.uiho.sgmw.common.mvp_senior.annotaions.CreatePresenterAnnotation;
import com.uiho.sgmw.common.utils.EventUtil;
import com.uiho.sgmw.common.utils.SPUtils;
import com.uiho.sgmw.common.utils.StringUtils;
import com.uiho.sgmw.common.utils.Utils;
import com.uiho.sgmw.module_login.R;
import com.uiho.sgmw.module_login.contract.SplashContract;
import com.uiho.sgmw.module_login.presenter.SplashPresenter;

/**
 * 作者：uiho_mac
 * 时间：2018/8/6
 * 描述：
 * 版本：1.0
 * 修订历史：
 */
@SuppressLint("Registered")
@CreatePresenterAnnotation(SplashPresenter.class)
@Route(path = RouterPath.SPLASH_ACTIVITY)
public class SplashActivity extends BaseMvpActivity<SplashContract.View, SplashPresenter> implements SplashContract.View {
    private SplashHandler splashHandler;
    private Handler handler;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        showSplash();
    }

    @Override
    protected int getLayout() {
        return 0;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {

    }

    public void showSplash() {
        handler = new Handler();
        splashHandler = new SplashHandler();
    }

    @Override
    public void verifyLogin(boolean isLogin) {
        if (isLogin) {
            ARouter.getInstance().build(RouterPath.MAIN_ACTIVITY).withTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right).navigation();
            finish();
        } else {
            EventUtil.showToast(mContext, Utils.getString(R.string.No_Login_Exception));
            ARouter.getInstance().build(RouterPath.LOGIN_ACTIVITY).withTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right).navigation();
            finish();
        }
    }

    private class SplashHandler implements Runnable {
        @Override
        public void run() {
            String token = (String) SPUtils.getParam(mContext, Constants.TOKEN, "");
            if (!StringUtils.isEmpty(token)) {
                getMvpPresenter().verifyLogin(token);
            } else {
                ARouter.getInstance().build(RouterPath.LOGIN_ACTIVITY).withTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right).navigation();
                finish();
            }
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (handler != null && splashHandler != null) {
            handler.removeCallbacks(splashHandler);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (handler != null && splashHandler != null) {
            handler.postDelayed(splashHandler, 1000);
        }
    }
}
