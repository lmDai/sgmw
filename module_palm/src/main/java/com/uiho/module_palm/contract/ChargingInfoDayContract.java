package com.uiho.module_palm.contract;

import com.uiho.sgmw.common.base.BasePresenter;
import com.uiho.sgmw.common.base.IBaseView;
import com.uiho.sgmw.common.model.ChargingDayModel;

/**
 * 作者：uiho_mac
 * 时间：2018/8/8
 * 描述：
 * 版本：1.0
 * 修订历史：
 */
public interface ChargingInfoDayContract {
    interface View extends IBaseView {
        void setCarChargeDay(ChargingDayModel model);

        void showError(String msg);
    }

    abstract class Presenter extends BasePresenter<View> {
        public abstract void getCarTripDayCharge(String vin, String chargeDate, String token);
    }
}
