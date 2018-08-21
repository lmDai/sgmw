package com.uiho.sgmw.module_login.presenter;

import com.uiho.sgmw.common.https.observers_extension.ProgressObserver;
import com.uiho.sgmw.common.https.scheduler.RxSchedulers;
import com.uiho.sgmw.module_login.base.MainModule;
import com.uiho.sgmw.module_login.contract.SplashContract;

/**
 * 作者：uiho_mac
 * 时间：2018/8/6
 * 描述：
 * 版本：1.0
 * 修订历史：
 */
public class SplashPresenter extends SplashContract.Presenter {
    @Override
    public void verifyLogin(String token) {
        MainModule.createRetrofit().verifyLogin(token)
                .compose(RxSchedulers.observableIO2Main(getView()))
                .compose(RxSchedulers.hanResult())
                .subscribe(new ProgressObserver<Boolean>(this) {
                    @Override
                    public void onSuccess(Boolean result) {
                        getView().verifyLogin(result);//结果返回
                    }
                });
    }
}
