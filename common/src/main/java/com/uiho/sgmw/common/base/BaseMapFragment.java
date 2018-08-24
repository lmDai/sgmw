package com.uiho.sgmw.common.base;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.view.View;

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
import com.amap.api.maps.model.MyLocationStyle;
import com.amap.api.maps.model.Polygon;
import com.tbruyelle.rxpermissions2.RxPermissions;
import com.uiho.sgmw.common.R;
import com.uiho.sgmw.common.utils.DialogUtils;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;

import static com.uiho.sgmw.common.Constants.REQUEST_LOCATION;

/**
 * 作者：uiho_mac
 * 时间：2018/8/24
 * 描述：
 * 版本：1.0
 * 修订历史：
 */
public abstract class BaseMapFragment<V extends IBaseView, P extends BasePresenter<V>> extends BaseMvpFragment<V, P> {
    protected MapView mMapView;
    protected AMap aMap;
    protected LatLng myLatLng;

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mMapView.onCreate(savedInstanceState);
        aMap = mMapView.getMap();
        requestPermissions();//请求权限
        onDefaultLocation();
    }

    @SuppressLint("CheckResult")
    private void requestPermissions() {
        RxPermissions rxPermissions = new RxPermissions(mActivity);
        rxPermissions
                .request(Manifest.permission.ACCESS_COARSE_LOCATION)//多个权限用","隔开
                .observeOn(AndroidSchedulers.mainThread(), false, 100)
                .subscribe(aBoolean -> {
                    if (aBoolean) {
                        onDefaultLocation();
                    } else {
                        DialogUtils.showPromptDialog(mContext, "检测到位置信息权限被禁止并设置了不再提醒,请您在设置-应用管理-权限管理中手动开启权限",
                                "温馨提示", "去开启", "取消", confirm -> {
                                    if (confirm) {
                                        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                                        Uri uri = Uri.fromParts("package", mActivity.getPackageName(), null);
                                        intent.setData(uri);
                                        startActivityForResult(intent, REQUEST_LOCATION);
                                    }
                                });
                    }
                });
    }

    /**
     * 获取定位
     */
    protected void onDefaultLocation() {
        aMap.setOnMyLocationChangeListener(new AMap.OnMyLocationChangeListener() {
            @Override
            public void onMyLocationChange(Location location) {
                myLatLng = new LatLng(location.getLatitude(), location.getLongitude());
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
        aMap.moveCamera(CameraUpdateFactory.zoomTo(15));//设置地图默认显示缩放层级
    }

    /**
     * 地图中心点
     */
    protected void setLocation(LatLng latLng) {
        if (latLng != null) {
            CameraUpdate mCameraUpdate = CameraUpdateFactory.newCameraPosition(new CameraPosition(new LatLng(latLng.latitude, latLng.longitude), aMap.getCameraPosition().zoom, 0, 0));
            aMap.moveCamera(mCameraUpdate);
        }
    }

    /**
     * 移动到围栏
     */
    protected void moveToFence(Polygon polygon) {
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

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_LOCATION) {
            requestPermissions();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mMapView != null) {
            mMapView.onDestroy();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (mMapView != null) {
            mMapView.onResume();
        }
    }
}
