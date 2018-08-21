package com.uiho.module_palm.contract;

import com.uiho.sgmw.common.base.BasePresenter;
import com.uiho.sgmw.common.base.IBaseView;
import com.uiho.sgmw.common.model.BurglarAlarmModel;
import com.uiho.sgmw.common.model.CarInfoModel;
import com.uiho.sgmw.common.model.CarSecurityFenceModel;
import com.uiho.sgmw.common.model.CarStatusModel;
import com.uiho.sgmw.common.model.UserModel;

/**
 * 作者：uiho_mac
 * 时间：2018/8/8
 * 描述：
 * 版本：1.0
 * 修订历史：
 */
public interface PalmTopContract {
    interface View extends IBaseView {
        void setStatus(CarStatusModel model);

        void setBurglarAlarm(BurglarAlarmModel model);

        void setUserInfo(UserModel userInfo);

        void setCarSecurityFence(CarSecurityFenceModel model);

        void setCarInfo(CarInfoModel model);
    }

    abstract class Presenter extends BasePresenter<View> {
        public abstract void getCarStatus(String vin, String token);

        public abstract void burglarAlarm(String vin, String token);

        public abstract void setBurglarAlarm(String vins, Integer alarmState, String token);

        public abstract void getUserInfo(String phone, String token);//获取个人信息

        public abstract void getCarSecurityFence(String vin, String token);

        public abstract void getCarInfo(String vin, String token);//获取车辆信息
    }
}
