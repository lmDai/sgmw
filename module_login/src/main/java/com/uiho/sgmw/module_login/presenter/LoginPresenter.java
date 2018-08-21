package com.uiho.sgmw.module_login.presenter;

import com.uiho.sgmw.common.https.observers_extension.ProgressObserver;
import com.uiho.sgmw.common.https.scheduler.RxSchedulers;
import com.uiho.sgmw.module_login.base.MainModule;
import com.uiho.sgmw.module_login.contract.LoginContract;

/**
 * 作者：uiho_mac
 * 时间：2018/8/7
 * 描述：
 * 版本：1.0
 * 修订历史：
 */
public class LoginPresenter extends LoginContract.Presenter {
    @Override
    public void login(String mobile, String password) {
        MainModule.createRetrofit().login(mobile, password)
                .compose(RxSchedulers.observableIO2Main(getView()))
                .compose(RxSchedulers.hanResult())
                .subscribe(new ProgressObserver<String>(this) {
                    @Override
                    public void onSuccess(String result) {
                        getView().loginResult(result);//返回登录信息
                    }
                });
    }
}
