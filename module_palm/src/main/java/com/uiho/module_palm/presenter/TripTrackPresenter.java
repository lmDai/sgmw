package com.uiho.module_palm.presenter;

import android.text.TextUtils;

import com.uiho.module_palm.base.PalmModule;
import com.uiho.module_palm.contract.TripTrackContract;
import com.uiho.sgmw.common.Constants;
import com.uiho.sgmw.common.https.observers_extension.ProgressObserver;
import com.uiho.sgmw.common.https.scheduler.RxSchedulers;
import com.uiho.sgmw.common.model.TripTrackModel;
import com.uiho.sgmw.common.utils.AESUtil;
import com.uiho.sgmw.common.utils.GsonUtils;

/**
 * 作者：uiho_mac
 * 时间：2018/8/8
 * 描述：
 * 版本：1.0
 * 修订历史：
 */
public class TripTrackPresenter extends TripTrackContract.Presenter {
    @Override
    public void getCarTripTrack(String vin, String token, String beginTime, String endTime) {
        PalmModule.createrRetofit().getCarTripTrack(vin, token, beginTime, endTime)
                .compose(RxSchedulers.observableIO2Main(getView()))
                .compose(RxSchedulers.hanResult())
                .subscribe(new ProgressObserver<String>(this) {
                    @Override
                    public void onSuccess(String result) {
                        if (!TextUtils.isEmpty(result)) {
                            TripTrackModel model = GsonUtils.GsonToBean(AESUtil.decrypt(result, Constants.TRACK_KEY), TripTrackModel.class);
                            getView().setTripTrack(model);
                        }
                    }
                });
    }
}
