package com.uiho.module_palm.contract;

import com.uiho.sgmw.common.base.BasePresenter;
import com.uiho.sgmw.common.base.IBaseView;
import com.uiho.sgmw.common.model.CarTripDayModel;
import com.uiho.sgmw.common.model.CarTripDaysModel;

/**
 * 作者：uiho_mac
 * 时间：2018/8/8
 * 描述：
 * 版本：1.0
 * 修订历史：
 */
public interface TravelInfoClassifyContract {
    interface View extends IBaseView {
        void setCarTripDay(CarTripDayModel model);

        void setCarTripDays(CarTripDaysModel model);

        void showError(String msg);
    }

    abstract class Presenter extends BasePresenter<View> {
        public abstract void getCarTripDay(String vin, String driveDate, String token);

        public abstract void getCarTripDays(String vin, String beginDate, String endDate, String token);

        public abstract void getCarTripMonth(String vin, String beginDate, String endDate, String token);
    }
}
