package com.uiho.module_palm.ui.activity;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.location.Location;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.amap.api.maps.AMap;
import com.amap.api.maps.CameraUpdate;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.MapView;
import com.amap.api.maps.UiSettings;
import com.amap.api.maps.model.BitmapDescriptor;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.CameraPosition;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.LatLngBounds;
import com.amap.api.maps.model.Marker;
import com.amap.api.maps.model.MarkerOptions;
import com.amap.api.maps.model.MyLocationStyle;
import com.amap.api.maps.model.Polygon;
import com.amap.api.maps.model.PolygonOptions;
import com.uiho.module_palm.R;
import com.uiho.module_palm.R2;
import com.uiho.module_palm.base.BasePalmActivity;
import com.uiho.module_palm.contract.PositionContract;
import com.uiho.module_palm.presenter.PositionPresenter;
import com.uiho.sgmw.common.Constants;
import com.uiho.sgmw.common.base.RouterPath;
import com.uiho.sgmw.common.model.CarSecurityFenceModel;
import com.uiho.sgmw.common.model.CarStatusModel;
import com.uiho.sgmw.common.mvp_senior.annotaions.CreatePresenterAnnotation;
import com.uiho.sgmw.common.utils.AESUtil;

import java.util.List;

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
public class PositionActivity extends BasePalmActivity<PositionContract.View, PositionPresenter> implements PositionContract.View {
    @BindView(R2.id.map_view)
    MapView mapView;
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
    private AMap aMap;
    private String vin;
    private String token;
    private LatLng latLngPostion;
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
        mapView.onCreate(savedInstanceState);
        //初始化地图控制器对象
        if (aMap == null) {
            aMap = mapView.getMap();
        }
        initMap();
    }

    /**
     * 地图中心点
     */
    public void setLocation(LatLng latLng) {
        if (latLng != null) {
            CameraUpdate mCameraUpdate = CameraUpdateFactory.newCameraPosition(new CameraPosition(new LatLng(latLng.latitude, latLng.longitude), 18, 0, 0));
            aMap.moveCamera(mCameraUpdate);
        }
    }

    public void moveToFence() {
        if (polygon != null) {
            List<LatLng> points = polygon.getPoints();
            if (points != null) {
                LatLngBounds.Builder b = LatLngBounds.builder();
                for (int i = 0; i < points.size(); i++) {
                    b.include(points.get(i));
                }
                LatLngBounds bounds = b.build();
                aMap.animateCamera(CameraUpdateFactory.newLatLngBounds(bounds, 100));
            }
        }
    }

    /**
     * 初始化地图
     */
    private void initMap() {
        aMap.setOnMyLocationChangeListener(new AMap.OnMyLocationChangeListener() {
            @Override
            public void onMyLocationChange(Location location) {
                latLngPostion = new LatLng(location.getLatitude(), location.getLongitude());
            }
        });
        MyLocationStyle myLocationStyle = new MyLocationStyle();//初始化定位蓝点样式类
        myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_LOCATION_ROTATE_NO_CENTER);//连续定位、且将视角移动到地图中心点，定位点依照设备方向旋转，并且会跟随设备移动。（1秒1次定位）如果不设置myLocationType，默认也会执行此种模式。
        BitmapDescriptor bitmapDescriptor = BitmapDescriptorFactory.fromResource(R.drawable.ic_position);
        myLocationStyle.myLocationIcon(bitmapDescriptor);//设置定位蓝点的icon图标方法，需要用到BitmapDescriptor类对象作为参数。
        myLocationStyle.interval(2000); //设置连续定位模式下的定位间隔，只在连续定位模式下生效，单次定位模式下不会生效。单位为毫秒。
        myLocationStyle.strokeColor(Color.argb(0, 0, 0, 0));// 设置圆形的边框颜色
        myLocationStyle.radiusFillColor(Color.argb(0, 0, 0, 0));// 设置圆形的填充颜色
        myLocationStyle.strokeWidth(0);//设置定位蓝点精度圈的边框宽度的方法。
        aMap.setMyLocationStyle(myLocationStyle);//设置定位蓝点的Style
        aMap.setMyLocationEnabled(true);// 设置为true表示启动显示定位蓝点，false表示隐藏定位蓝点并不进行定位，默认是false。
        UiSettings mUiSettings = aMap.getUiSettings();
        mUiSettings.setZoomControlsEnabled(false);  //显示隐藏 缩放地图
        mUiSettings.setRotateGesturesEnabled(false);
        mUiSettings.setLogoBottomMargin(-50);//隐藏logo
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
        if (aMap == null) {
            aMap = mapView.getMap();
        }
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
            setLocation(latLngPostion);
        } else if (i == R.id.ib_car) {
            setLocation(latLngCar);
        } else if (i == R.id.ib_fence) {
            moveToFence();
        }
    }
}
