package com.uiho.module_palm.presenter;

import android.text.TextUtils;

import com.uiho.module_palm.base.PalmModule;
import com.uiho.module_palm.contract.TravelInfoClassifyContract;
import com.uiho.sgmw.common.Constants;
import com.uiho.sgmw.common.https.observers_extension.ProgressObserver;
import com.uiho.sgmw.common.https.scheduler.RxSchedulers;
import com.uiho.sgmw.common.model.CarTripDayModel;
import com.uiho.sgmw.common.model.CarTripDaysModel;
import com.uiho.sgmw.common.utils.AESUtil;
import com.uiho.sgmw.common.utils.GsonUtils;

/**
 * 作者：uiho_mac
 * 时间：2018/8/8
 * 描述：
 * 版本：1.0
 * 修订历史：
 */
public class TravelInfoClassifyPresenter extends TravelInfoClassifyContract.Presenter {
    //按日查询
    @Override
    public void getCarTripDay(String vin, String driveDate, String token) {
        PalmModule.createrRetofit().getCarTripDay(vin, driveDate.replace("/", "-"), token)
                .compose(RxSchedulers.observableIO2Main(getView()))
                .compose(RxSchedulers.hanResult())
                .subscribe(new ProgressObserver<String>(this) {
                    @Override
                    public void onSuccess(String result) {
                        if (!TextUtils.isEmpty(result)) {
                            CarTripDayModel model = GsonUtils.GsonToBean(AESUtil.decrypt(result, Constants.DAY_TRAVEL_KEY), CarTripDayModel.class);
                            getView().setCarTripDay(model);
                        }
                    }

                    @Override
                    public void onFailure(Throwable e, int code, String errorMsg) {
                        super.onFailure(e, code, errorMsg);
                        getView().showError(errorMsg);
                    }
                });
    }

    //按日期段查询
    @Override
    public void getCarTripDays(String vin, String beginDate, String endDate, String token) {
        PalmModule.createrRetofit().getCarTripDays(vin, beginDate.replace("/", "-"), endDate.replace("/", "-"), token)
                .compose(RxSchedulers.observableIO2Main(getView()))
                .compose(RxSchedulers.hanResult())
                .subscribe(new ProgressObserver<String>(this) {
                    @Override
                    public void onSuccess(String result) {
                        if (!TextUtils.isEmpty(result)) {
                            CarTripDaysModel model = GsonUtils.GsonToBean(AESUtil.decrypt(result, Constants.DAYS_TRIP_KEY), CarTripDaysModel.class);
                            getView().setCarTripDays(model);
                        }
                    }

                    @Override
                    public void onFailure(Throwable e, int code, String errorMsg) {
                        super.onFailure(e, code, errorMsg);
                        getView().showError(errorMsg);
                    }
                });
    }

    @Override
    public void getCarTripMonth(String vin, String beginDate, String endDate, String token) {
        PalmModule.createrRetofit().getCarTripMonth(vin, beginDate, endDate, token)
                .compose(RxSchedulers.observableIO2Main(getView()))
                .compose(RxSchedulers.hanResult())
                .subscribe(new ProgressObserver<String>(this) {
                    @Override
                    public void onSuccess(String result) {
                        if (!TextUtils.isEmpty(result)) {
                            CarTripDaysModel model = GsonUtils.GsonToBean(AESUtil.decrypt(result, Constants.MONTHS_TRIP_KEY), CarTripDaysModel.class);
                            getView().setCarTripDays(model);
                        }
                    }

                    @Override
                    public void onFailure(Throwable e, int code, String errorMsg) {
                        super.onFailure(e, code, errorMsg);
                        getView().showError(errorMsg);
                    }
                });
    }
}
