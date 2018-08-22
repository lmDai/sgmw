package com.uiho.sgmw.common.https.observers_extension;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;

import com.uiho.sgmw.common.R;
import com.uiho.sgmw.common.https.exception.ApiException;
import com.uiho.sgmw.common.https.exception.ExceptionUtil;
import com.uiho.sgmw.common.utils.EventUtil;
import com.uiho.sgmw.common.utils.SystemUtils;
import com.uiho.sgmw.common.utils.Utils;

import java.lang.ref.WeakReference;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * 作者：uiho_mac
 * 时间：2018/8/6
 * 描述：请求服务器，返回的数据做同一处理
 * 版本：1.0
 * 修订历史：
 */
public abstract class BaseObserver<T> implements Observer<T> {
    private static final String TAG = "BaseObserver";
    public WeakReference<Context> contextWeakReference;

    @Override
    public void onSubscribe(Disposable d) {
        if (contextWeakReference != null && contextWeakReference.get() != null && !SystemUtils.isNetworkConnected()) {
            EventUtil.showToast(contextWeakReference.get(), Utils.getString(R.string.NO_NET_CONNECTED));
            onComplete();
        }
    }

    @Override
    public final void onNext(T result) {
        onSuccess(result);
    }

    @Override
    public void onError(@NonNull Throwable e) {
        String msg;
        int code = ExceptionUtil.exceptionHandler(e);
        Log.d(TAG, "错误信息---" + e.getMessage() + "-----错误码: " + code);
        if (e instanceof ApiException) {
            msg = e.getMessage();
        } else {
            msg = ExceptionUtil.getMsg(code);
        }
        onFailure(e, code, msg);
    }

    @Override
    public void onComplete() {

    }

    public abstract void onSuccess(T result);

    public void onFailure(Throwable e, int code, String errorMsg) {
        EventUtil.showToast(Utils.getContext(), errorMsg);
//        if (code == 401) {
//            if (e instanceof ApiException) {
//                ViewManager.getInstance().finishAllActivity();
//                ARouter.getInstance().build(RouterPath.LOGIN_ACTIVITY).navigation();
//            }
//        }
    }
}
