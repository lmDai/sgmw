package com.uiho.module_palm.ui.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.uiho.module_palm.R;
import com.uiho.sgmw.common.model.CarTripDayModel;
import com.uiho.sgmw.common.utils.DateUtils;

import java.util.List;

/**
 * 作者：uiho_mac
 * 时间：2018/5/29
 * 描述：
 * 版本：1.0
 * 修订历史：
 */

public class MyViewPagerAdapter extends PagerAdapter {

    private Context mContext;
    private List<CarTripDayModel.InfoArrayBean> infoArray;
    private LayoutInflater inflater;

    public MyViewPagerAdapter(Context mContext, List<CarTripDayModel.InfoArrayBean> infoArray) {
        this.mContext = mContext;
        this.infoArray = infoArray;
        inflater = LayoutInflater.from(mContext);
    }

    @Override
    public int getCount() {
        return infoArray == null ? 0 : infoArray.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View view = inflater.inflate(R.layout.adapter_path, null, false);
        CarTripDayModel.InfoArrayBean bean = infoArray.get(position);
        TextView txtDate = (TextView) view.findViewById(R.id.txt_date);
        TextView txtStartElec = (TextView) view.findViewById(R.id.txt_start_elec);
        TextView txtEndElec = (TextView) view.findViewById(R.id.txt_end_elec);
        TextView txtUseElec = (TextView) view.findViewById(R.id.txt_use_elec);
        TextView txtTripMile = (TextView) view.findViewById(R.id.txt_trip_mile);
        TextView txtSpeed = (TextView) view.findViewById(R.id.txt_speed);
        TextView txtMaxSpeed = (TextView) view.findViewById(R.id.txt_max_speed);
        txtDate.setText(
                DateUtils.formatterDate(DateUtils.yyyyMMddHHmmss, DateUtils.MMddHHmm, bean.getBeginTime()) + " - " +
                        DateUtils.formatterDate(DateUtils.yyyyMMddHHmmss, DateUtils.MMddHHmm, bean.getEndTime())
        );
        txtStartElec.setText("开始电量：" + bean.getBeginPowerPercent() + "%");
        txtEndElec.setText("结束电量：" + bean.getEndPowerPercent() + "%");
        txtUseElec.setText("消耗电度：" + bean.getCostPowerKWH() + "°");
        txtMaxSpeed.setText("最高速度：" + bean.getMaxSpeed() + "km/h");
        txtSpeed.setText("平均速度：" + bean.getAvgSpeed() + "km/h");
        txtTripMile.setText("行驶里程：" + bean.getDriveMile() + "km");
        container.addView(view);
        return view;
    }

    @Override
    public int getItemPosition(Object object) {
        return POSITION_NONE;
    }
}
