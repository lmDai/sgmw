package com.uiho.module_palm.presenter;

import android.text.TextUtils;

import com.uiho.module_palm.base.PalmModule;
import com.uiho.module_palm.contract.PalmTopContract;
import com.uiho.sgmw.common.Constants;
import com.uiho.sgmw.common.https.observers_extension.ProgressObserver;
import com.uiho.sgmw.common.https.scheduler.RxSchedulers;
import com.uiho.sgmw.common.model.BurglarAlarmModel;
import com.uiho.sgmw.common.model.CarInfoModel;
import com.uiho.sgmw.common.model.CarSecurityFenceModel;
import com.uiho.sgmw.common.model.CarStatusModel;
import com.uiho.sgmw.common.model.UserModel;
import com.uiho.sgmw.common.utils.AESUtil;
import com.uiho.sgmw.common.utils.GsonUtils;

/**
 * 作者：uiho_mac
 * 时间：2018/8/8
 * 描述：
 * 版本：1.0
 * 修订历史：
 */
public class PalmTopPresenter extends PalmTopContract.Presenter {
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

    /**
     * 防盗警报状态查询
     *
     * @param vin
     * @param token
     */
    @Override
    public void burglarAlarm(String vin, String token) {
        PalmModule.createrRetofit().burglarAlarm(vin, token)
                .compose(RxSchedulers.observableIO2Main(getView()))
                .compose(RxSchedulers.hanResult())
                .subscribe(new ProgressObserver<String>(this) {
                    @Override
                    public void onSuccess(String result) {
                        if (!TextUtils.isEmpty(result)) {
                            BurglarAlarmModel model = GsonUtils.GsonToBean(AESUtil.decrypt(result, Constants.WARN_SEARCH_KEY), BurglarAlarmModel.class);
                            getView().setBurglarAlarm(model);
                        }
                    }
                });
    }

    /**
     * 防盗警报开关设置
     *
     * @param vins
     * @param alarmState
     * @param token
     */
    @Override
    public void setBurglarAlarm(String vins, Integer alarmState, String token) {
        PalmModule.createrRetofit().setBurglarAlarm(vins, alarmState, token)
                .compose(RxSchedulers.observableIO2Main(getView()))
                .compose(RxSchedulers.hanResult())
                .subscribe(new ProgressObserver<String>(this) {
                    @Override
                    public void onSuccess(String result) {
                        if (!TextUtils.isEmpty(result)) {
                            BurglarAlarmModel model = GsonUtils.GsonToBean(AESUtil.decrypt(result, Constants.WARN_SETTING_KEY), BurglarAlarmModel.class);
                            getView().setBurglarAlarm(model);
                        }
                    }
                });
    }

    /**
     * 用户个人信息
     *
     * @param phone
     * @param token
     */
    @Override
    public void getUserInfo(String phone, String token) {
        PalmModule.createrRetofit().getUserInfo(phone, token)
                .compose(RxSchedulers.observableIO2Main(getView()))
                .compose(RxSchedulers.hanResult())
                .subscribe(new ProgressObserver<String>(this) {
                    @Override
                    public void onSuccess(String result) {
                        if (!TextUtils.isEmpty(result)) {
                            UserModel model = GsonUtils.GsonToBean(AESUtil.decrypt(result, Constants.USER_KEY), UserModel.class);
                            getView().setUserInfo(model);
                        }
                    }
                });
    }

    /**
     * 电子围栏查询
     *
     * @param vin
     * @param token
     */
    @Override
    public void getCarSecurityFence(String vin, String token) {
        PalmModule.createrRetofit().getCarSecurityFence(vin, token)
                .compose(RxSchedulers.observableIO2Main(getView()))
                .compose(RxSchedulers.hanResult())
                .subscribe(new ProgressObserver<String>(this) {
                    @Override
                    public void onSuccess(String result) {
                        if (!TextUtils.isEmpty(result)) {
                            CarSecurityFenceModel model = GsonUtils.GsonToBean(AESUtil.decrypt(result, Constants.ELEC_FANCE_SEARCH_KEY), CarSecurityFenceModel.class);
                            getView().setCarSecurityFence(model);
                        }
                    }
                });
    }

    /**
     * 车辆信息查询
     *
     * @param vin
     * @param token
     */
    @Override
    public void getCarInfo(String vin, String token) {
        PalmModule.createrRetofit().getCarInfo(vin, token)
                .compose(RxSchedulers.observableIO2Main(getView()))
                .compose(RxSchedulers.hanResult())
                .subscribe(new ProgressObserver<String>(this) {
                    @Override
                    public void onSuccess(String result) {
                        if (!TextUtils.isEmpty(result)) {
                            CarInfoModel model = GsonUtils.GsonToBean(AESUtil.decrypt(result, Constants.CAR_KEY), CarInfoModel.class);
                            getView().setCarInfo(model);
                        }
                    }
                });
    }
}
