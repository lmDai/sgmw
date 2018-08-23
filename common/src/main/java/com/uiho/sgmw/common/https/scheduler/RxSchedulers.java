package com.uiho.sgmw.common.https.scheduler;

import android.text.TextUtils;

import com.uiho.sgmw.common.base.BaseEnCodeResponse;
import com.uiho.sgmw.common.base.BaseResponse;
import com.uiho.sgmw.common.base.IBaseView;
import com.uiho.sgmw.common.https.exception.ApiException;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

/**
 * 作者：uiho_mac
 * 时间：2018/8/6
 * 描述：
 * 版本：1.0
 * 修订历史：
 */
public class RxSchedulers {
    public static <T> ObservableTransformer<T, T> observableIO2Main(IBaseView iBaseView) {
        return upstream -> upstream.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(iBaseView.bindLifeycle());
    }

    public static <T> ObservableTransformer<BaseResponse<T>, T> hanResult() {
        return upstream -> upstream.flatMap((Function<BaseResponse<T>, ObservableSource<T>>) tBaseResponse -> {
            if (tBaseResponse.isSuccess()) {
                return createData(tBaseResponse.getResult());
            } else if (!TextUtils.isEmpty(tBaseResponse.getMessage())) {
                return Observable.error(new ApiException(tBaseResponse.getMessage(), tBaseResponse.getCode()));
            } else {
                return Observable.error(new ApiException("*" + "服务器返回error", 10000));
            }
        });
    }
    /**
     * 请求结果处理
     */
    public static <T> ObservableTransformer<BaseEnCodeResponse<T>, T> handResultList() {
        return upstream -> upstream.flatMap(new Function<BaseEnCodeResponse<T>, ObservableSource<T>>() {
            @Override
            public ObservableSource<T> apply(BaseEnCodeResponse<T> tBaseResponse) throws Exception {
                if (tBaseResponse.getRspHead().getRspCode().equals("00")) {
                    return createData(tBaseResponse.getRspBody().getRspContent());
                } else {
                    return Observable.error(new ApiException("*" + "服务器返回error", 10000));
                }
            }
        });
    }

    public static <T> ObservableTransformer<T, T> handVersionResult() {
        return upstream -> upstream.flatMap((Function<T, ObservableSource<T>>) tBaseResponse -> {
            if (tBaseResponse != null) {
                return createData(tBaseResponse);
            } else {
                return Observable.error(new ApiException("*" + "服务器返回error", 10000));
            }
        });
    }

    /**
     * 生成Observable
     *
     * @param <T>
     * @return
     */
    private static <T> Observable<T> createData(final T t) {
        return Observable.create(subscriber -> {
            try {
                subscriber.onNext(t);
                subscriber.onComplete();
            } catch (Exception e) {
                subscriber.onError(e);
            }
        });
    }
}