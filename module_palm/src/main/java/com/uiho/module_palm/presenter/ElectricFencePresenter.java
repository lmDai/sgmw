package com.uiho.module_palm.presenter;

import android.text.TextUtils;

import com.uiho.module_palm.base.PalmModule;
import com.uiho.module_palm.contract.ElectricFenceContract;
import com.uiho.sgmw.common.Constants;
import com.uiho.sgmw.common.https.observers_extension.ProgressObserver;
import com.uiho.sgmw.common.https.scheduler.RxSchedulers;
import com.uiho.sgmw.common.model.CarSecurityFenceModel;
import com.uiho.sgmw.common.model.CarStatusModel;
import com.uiho.sgmw.common.utils.AESUtil;
import com.uiho.sgmw.common.utils.GsonUtils;

/**
 * 作者：uiho_mac
 * 时间：2018/8/8
 * 描述：电子围栏
 * 版本：1.0
 * 修订历史：
 */
public class ElectricFencePresenter extends ElectricFenceContract.Presenter {
    //车辆状态查询
    @Override
    public void getCarStatus(String vin, String token) {
        PalmModule.createrRetofit().getCarStatus(vin, token)
                .compose(RxSchedulers.observableIO2Main(getView()))
                .compose(RxSchedulers.hanResult())
                .subscribe(new ProgressObserver<String>(this, true) {
                    @Override
                    public void onSuccess(String result) {
                        if (!TextUtils.isEmpty(result)) {
                            CarStatusModel model = GsonUtils.GsonToBean(AESUtil.decrypt(result, Constants.CAR_STATUS_KEY), CarStatusModel.class);
                            getView().setStatus(model);
                        }
                    }
                });
    }

    //电子围栏查询
    @Override
    public void getCarSecurityFence(String vin, String token) {
        PalmModule.createrRetofit().getCarSecurityFence(vin, token)
                .compose(RxSchedulers.observableIO2Main(getView()))
                .compose(RxSchedulers.hanResult())
                .subscribe(new ProgressObserver<String>(this, true) {
                    @Override
                    public void onSuccess(String result) {
                        if (!TextUtils.isEmpty(result)) {
                            CarSecurityFenceModel model = GsonUtils.GsonToBean(AESUtil.decrypt(result, Constants.ELEC_FANCE_SEARCH_KEY), CarSecurityFenceModel.class);
                            getView().setCarSecurityFence(model);
                        }
                    }
                });
    }

    //电子围栏重设
    @Override
    public void carSecurityFenceReset(String fenceData, String token) {
        PalmModule.createrRetofit().carSecurityFenceReset(fenceData, token)
                .compose(RxSchedulers.observableIO2Main(getView()))
                .compose(RxSchedulers.hanResult())
                .subscribe(new ProgressObserver<String>(this, true) {
                    @Override
                    public void onSuccess(String result) {
                        if (!TextUtils.isEmpty(result)) {
                            CarSecurityFenceModel model = GsonUtils.GsonToBean(AESUtil.decrypt(result, Constants.ELEC_FANCE_SEARCH_KEY), CarSecurityFenceModel.class);
                            getView().setCarSecurityFence(model);
                        }
                    }
                });
    }

    //电子围栏设置
    @Override
    public void carSecurityFenceSet(String fenceData, String token, String vin) {
        PalmModule.createrRetofit().carSecurityFenceSet(fenceData, token, vin)
                .compose(RxSchedulers.observableIO2Main(getView()))
                .compose(RxSchedulers.hanResult())
                .subscribe(new ProgressObserver<String>(this, true) {
                    @Override
                    public void onSuccess(String result) {
                        if (!TextUtils.isEmpty(result)) {
                            CarSecurityFenceModel model = GsonUtils.GsonToBean(AESUtil.decrypt(result, Constants.ELEC_FANCE_SEARCH_KEY), CarSecurityFenceModel.class);
                            getView().setCarSecurityFence(model);
                        }
                    }
                });
    }
}
