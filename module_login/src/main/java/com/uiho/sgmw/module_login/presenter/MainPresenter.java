package com.uiho.sgmw.module_login.presenter;

import com.uiho.sgmw.common.https.observers_extension.ProgressObserver;
import com.uiho.sgmw.common.https.scheduler.RxSchedulers;
import com.uiho.sgmw.common.model.UpdateModel;
import com.uiho.sgmw.module_login.base.MainModule;
import com.uiho.sgmw.module_login.contract.MainContract;

import java.io.File;
import java.io.IOException;

import io.reactivex.Observer;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;
import okio.BufferedSource;

import static com.uiho.sgmw.common.utils.SystemUtils.unSubscribe;
import static com.uiho.sgmw.common.utils.SystemUtils.writeFile;

/**
 * 作者：uiho_mac
 * 时间：2018/8/7
 * 描述：
 * 版本：1.0
 * 修订历史：
 */
public class MainPresenter extends MainContract.Presenter {

    @Override
    public void checkedAppVersion() {
        MainModule.createAppVerSionRetrofit().checkVersion()
                .compose(RxSchedulers.observableIO2Main(getView()))
                .compose(RxSchedulers.handVersionResult())
                .subscribe(new ProgressObserver<UpdateModel>(this, false) {
                    @Override
                    public void onSuccess(UpdateModel result) {
                        getView().showUpdateDialog(result);//返回登录信息
                    }
                });
    }

    @Override
    public void updateApp(String url, String apkPath, CompositeDisposable cd) {
        MainModule.createDownLoadRetrofit().updateApp(url)
                .map(new Function<ResponseBody, BufferedSource>() {
                    @Override
                    public BufferedSource apply(ResponseBody responseBody) throws Exception {
                        return responseBody.source();
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .subscribe(new Observer<BufferedSource>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        cd.add(d);
                    }

                    @Override
                    public void onNext(BufferedSource bufferedSource) {
                        try {
                            writeFile(bufferedSource, new File(apkPath));
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        unSubscribe(cd);
                    }

                    @Override
                    public void onComplete() {
                        //安装apk
                        unSubscribe(cd);
                    }
                });
    }
}
