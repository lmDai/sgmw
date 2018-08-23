package com.uiho.module_palm.ui.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.uiho.module_palm.R;
import com.uiho.sgmw.common.model.CarTripDaysModel;

import java.util.List;

/**
 * 行程信息按日
 * Created by uiho_mac on 2018/2/6.
 */

public class ChargingMonthAdapter extends BaseQuickAdapter<CarTripDaysModel.InfoArrayBean, BaseViewHolder> {
    public ChargingMonthAdapter(@Nullable List<CarTripDaysModel.InfoArrayBean> data) {
        super(R.layout.adapter_charging_days, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, CarTripDaysModel.InfoArrayBean item) {
        helper.setText(R.id.txt_travel_day, item.getTravel_month())
                .setText(R.id.txt_charge_count, item.getChargeCount() + "次")
                .setText(R.id.txt_charge_power, item.getChargePowerKWH() + "°")
                .setText(R.id.txt_drive_cost_power, item.getDriveCostPowerKWH() + "°");
    }
}
