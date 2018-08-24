package com.uiho.module_palm.ui.activity;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.amap.api.maps.CameraUpdate;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.CameraPosition;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.Marker;
import com.amap.api.maps.model.MarkerOptions;
import com.amap.api.maps.model.Polygon;
import com.amap.api.maps.model.PolygonOptions;
import com.uiho.module_palm.R;
import com.uiho.module_palm.R2;
import com.uiho.module_palm.contract.PositionContract;
import com.uiho.module_palm.presenter.PositionPresenter;
import com.uiho.sgmw.common.Constants;
import com.uiho.sgmw.common.base.BaseMapActivity;
import com.uiho.sgmw.common.base.RouterPath;
import com.uiho.sgmw.common.model.CarSecurityFenceModel;
import com.uiho.sgmw.common.model.CarStatusModel;
import com.uiho.sgmw.common.mvp_senior.annotaions.CreatePresenterAnnotation;
import com.uiho.sgmw.common.utils.AESUtil;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 作者：uiho_mac
 * 时间：2018/8/22
 * 描述：位置信息
 * 版本：1.0
 * 修订历史：
 */
@SuppressLint("Registered")
@CreatePresenterAnnotation(PositionPresenter.class)
@Route(path = RouterPath.PALM_POSITION)
public class PositionActivity extends BaseMapActivity<PositionContract.View, PositionPresenter> implements PositionContract.View {
    @BindView(R2.id.txt_total_mileage)
    TextView txtTotalMileage;
    @BindView(R2.id.txt_xh_mile)
    TextView txtXhMile;
    @BindView(R2.id.txt_power)
    TextView txtPower;
    @BindView(R2.id.ib_refresh)
    ImageButton ibRefresh;
    @BindView(R2.id.ib_location)
    ImageButton ibLocation;
    @BindView(R2.id.ib_car)
    ImageButton ibCar;
    @BindView(R2.id.ib_fence)
    ImageButton ibFence;
    @BindView(R2.id.txt_car_time)
    TextView txtCarTime;
    private String vin;
    private String token;
    private LatLng latLngCar;
    private Polygon polygon;
    private Marker marker;

    @Override
    public void setCarSecurityFence(CarSecurityFenceModel model) {
        if (polygon != null) {
            polygon.remove();
        }
        PolygonOptions polygonOptions = new PolygonOptions();
        // 添加 多边形的每个顶点（顺序添加）
        polygonOptions.add(
                new LatLng(model.getUpLeftLatitude(), model.getUpLeftLongitude()),
                new LatLng(model.getUpRightLatitude(), model.getUpRightLongitude()),
                new LatLng(model.getDownRightLatitude(), model.getDownRightLongitude()),
                new LatLng(model.getDownLeftLatitude(), model.getDownLeftLongitude())
        );
        polygonOptions.strokeWidth(1) // 多边形的边框
                .strokeColor(Color.RED) // 边框颜色
                .fillColor(Color.parseColor("#3FFF0000"));   // 多边形的填充色
        // 添加一个多边形
        polygon = aMap.addPolygon(polygonOptions);
    }

    @Override
    public void setStatus(CarStatusModel status) {
        txtTotalMileage.setText(status.getTotalMileage() + "km");
        txtPower.setText(status.getCurrentPowerPercent() + "%");
        txtXhMile.setText(status.getEnduranceMileage() + "km");
        txtCarTime.setText(status.getGpsCollectionTime());
        setMarker(status.getLongitudeGD(), status.getLatitudeGD(), status.getOrientation());
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_position;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        setTopTitle("位置");
        token = getIntent().getStringExtra("token");
        vin = getIntent().getStringExtra("vin");
        mMapView = findViewById(R.id.map_view);
        super.initView(savedInstanceState);
    }

    @Override
    protected void initEvent() {
        if (!TextUtils.isEmpty(vin)) {
            getMvpPresenter().getCarSecurityFence(AESUtil.encrypt(vin, Constants.ELEC_FANCE_SEARCH_KEY), token);
            getMvpPresenter().getCarStatus(AESUtil.encrypt(vin, Constants.CAR_STATUS_KEY), token);
        }
    }

    /**
     * 设置车位置
     *
     * @param longitude
     * @param latitude
     */
    public void setMarker(Double longitude, Double latitude, double orientation) {
        latLngCar = new LatLng(latitude, longitude);
        MarkerOptions markerOption = new MarkerOptions().icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_car_position))
                .position(latLngCar)
                .draggable(true);
        if (marker != null) {
            marker.remove();
        }
        marker = aMap.addMarker(markerOption);
        marker.setRotateAngle(360 - (float) orientation);//设置车辆旋转角度
        if (latitude == 0 && longitude == 0) {
            return;
        }
        CameraUpdate mCameraUpdate = CameraUpdateFactory.newCameraPosition(new CameraPosition(new LatLng(latitude, longitude), 18, 0, 0));
        aMap.moveCamera(mCameraUpdate);
    }

    @OnClick({R2.id.ib_refresh, R2.id.ib_location, R2.id.ib_car, R2.id.ib_fence})
    public void onViewClicked(View view) {
        int i = view.getId();
        if (i == R.id.ib_refresh) {
            initEvent();
        } else if (i == R.id.ib_location) {
            setLocation(myLatLng);
        } else if (i == R.id.ib_car) {
            setLocation(latLngCar);
        } else if (i == R.id.ib_fence) {
            moveToFence(polygon);
        }
    }
}
