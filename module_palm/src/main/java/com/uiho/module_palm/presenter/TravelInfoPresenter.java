package com.uiho.module_palm.presenter;

import android.text.TextUtils;

import com.uiho.module_palm.base.PalmModule;
import com.uiho.module_palm.contract.TravelInfoContract;
import com.uiho.sgmw.common.Constants;
import com.uiho.sgmw.common.https.observers_extension.ProgressObserver;
import com.uiho.sgmw.common.https.scheduler.RxSchedulers;
import com.uiho.sgmw.common.model.CarStatusModel;
import com.uiho.sgmw.common.utils.AESUtil;
import com.uiho.sgmw.common.utils.GsonUtils;

/**
 * 作者：uiho_mac
 * 时间：2018/8/8
 * 描述：
 * 版本：1.0
 * 修订历史：
 */
public class TravelInfoPresenter extends TravelInfoContract.Presenter {
    /**
     * 车辆状态查询
     *
     * @param vin   车架号
     * @param token token
     */
    @Override
    public void getCarStatus(String vin, String token) {
        PalmModule.createrRetofit().getCarStatus(vin, token)
                .compose(RxSchedulers.observableIO2Main(getView()))
                .compose(RxSchedulers.hanResult())
                .subscribe(new ProgressObserver<String>(this) {
                    @Override
                    public void onSuccess(String result) {
                        if (!TextUtils.isEmpty(result)) {
                            CarStatusModel model = GsonUtils.GsonToBean(AESUtil.decrypt(result, Constants.CAR_STATUS_KEY), CarStatusModel.class);
                            getView().setStatus(model);
                        }
                    }
                });
    }
}
