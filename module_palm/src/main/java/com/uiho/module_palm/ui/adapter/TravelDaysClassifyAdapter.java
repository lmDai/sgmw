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

public class TravelDaysClassifyAdapter extends BaseQuickAdapter<CarTripDaysModel.InfoArrayBean, BaseViewHolder> {
    public TravelDaysClassifyAdapter(@Nullable List<CarTripDaysModel.InfoArrayBean> data) {
        super(R.layout.adapter_travel_days, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, CarTripDaysModel.InfoArrayBean item) {
        helper.setText(R.id.txt_travel_day, item.getTravel_day())
                .setText(R.id.txt_drive_count, item.getDriveCount() + "次")
                .setText(R.id.txt_drive_mile, item.getDriveMile() + "km")
                .setText(R.id.txt_drive_cost_power, item.getDriveCostPowerKWH() + "°");
    }
}
