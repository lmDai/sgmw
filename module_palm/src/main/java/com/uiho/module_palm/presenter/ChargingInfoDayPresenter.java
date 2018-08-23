package com.uiho.module_palm.presenter;

import android.text.TextUtils;

import com.uiho.module_palm.base.PalmModule;
import com.uiho.module_palm.contract.ChargingInfoDayContract;
import com.uiho.sgmw.common.Constants;
import com.uiho.sgmw.common.https.observers_extension.ProgressObserver;
import com.uiho.sgmw.common.https.scheduler.RxSchedulers;
import com.uiho.sgmw.common.model.ChargingDayModel;
import com.uiho.sgmw.common.utils.AESUtil;
import com.uiho.sgmw.common.utils.GsonUtils;

/**
 * 作者：uiho_mac
 * 时间：2018/8/8
 * 描述：
 * 版本：1.0
 * 修订历史：
 */
public class ChargingInfoDayPresenter extends ChargingInfoDayContract.Presenter {

    @Override
    public void getCarTripDayCharge(String vin, String chargeDate, String token) {
        PalmModule.createrRetofit().getCarTripDayCharge(vin, chargeDate.replace("/", "-"), token)
                .compose(RxSchedulers.observableIO2Main(getView()))
                .compose(RxSchedulers.hanResult())
                .subscribe(new ProgressObserver<String>(this) {
                    @Override
                    public void onSuccess(String result) {
                        if (!TextUtils.isEmpty(result)) {
                            ChargingDayModel model = GsonUtils.GsonToBean(AESUtil.decrypt(result, Constants.DAY_ELEC_KEY), ChargingDayModel.class);
                            getView().setCarChargeDay(model);
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
