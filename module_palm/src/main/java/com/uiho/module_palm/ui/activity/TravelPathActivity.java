package com.uiho.module_palm.ui.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.util.Pair;
import android.view.View;
import android.widget.ImageView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.amap.api.maps.AMap;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.MapView;
import com.amap.api.maps.UiSettings;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.LatLngBounds;
import com.amap.api.maps.model.MarkerOptions;
import com.amap.api.maps.model.PolylineOptions;
import com.amap.api.maps.utils.SpatialRelationUtil;
import com.amap.api.maps.utils.overlay.SmoothMoveMarker;
import com.uiho.module_palm.R;
import com.uiho.module_palm.R2;
import com.uiho.module_palm.base.BasePalmActivity;
import com.uiho.module_palm.contract.TripTrackContract;
import com.uiho.module_palm.presenter.TripTrackPresenter;
import com.uiho.module_palm.ui.adapter.MyViewPagerAdapter;
import com.uiho.sgmw.common.Constants;
import com.uiho.sgmw.common.base.RouterPath;
import com.uiho.sgmw.common.model.CarTripDayModel;
import com.uiho.sgmw.common.model.TripTrackModel;
import com.uiho.sgmw.common.mvp_senior.annotaions.CreatePresenterAnnotation;
import com.uiho.sgmw.common.utils.AESUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 作者：uiho_mac
 * 时间：2018/8/23
 * 描述：
 * 版本：1.0
 * 修订历史：
 */
@CreatePresenterAnnotation(TripTrackPresenter.class)
@Route(path = RouterPath.PALM_TRAVEL_PATH) // 路由地址，必须注明
public class TravelPathActivity extends BasePalmActivity<TripTrackContract.View, TripTrackPresenter> implements TripTrackContract.View {

    @BindView(R2.id.map_view)
    MapView mMapView;
    @BindView(R2.id.view_pager)
    ViewPager viewPager;
    @BindView(R2.id.img_pre)
    ImageView imgPre;
    @BindView(R2.id.img_next)
    ImageView imgNext;
    private AMap aMap;
    private MarkerOptions markerOption;
    private String vin, token;
    private MyViewPagerAdapter adapter;
    private List<CarTripDayModel.InfoArrayBean> infoArrayBeen;
    private int curPosition = 0;

    @Override
    protected void initView(Bundle savedInstanceState) {
        setTopTitle("行驶轨迹");
        mMapView.onCreate(savedInstanceState);
        Bundle bundle = getIntent().getBundleExtra("bundle");
        if (bundle != null) {
            vin = bundle.getString("vin");
            token = bundle.getString("token");
            curPosition = bundle.getInt("position");
            infoArrayBeen = (List<CarTripDayModel.InfoArrayBean>) bundle.getSerializable("details");
            adapter = new MyViewPagerAdapter(mContext, infoArrayBeen);
            viewPager.setAdapter(adapter);
            imgPre.setVisibility(curPosition == 0 ? View.GONE : View.VISIBLE);
            imgNext.setVisibility(curPosition == (adapter.getCount() - 1) ? View.GONE : View.VISIBLE);
        }
        //初始化地图控制器对象
        if (aMap == null) {
            aMap = mMapView.getMap();
        }
        initMap();

    }

    /**
     * 初始化地图
     */
    private void initMap() {
        UiSettings mUiSettings = aMap.getUiSettings();
        mUiSettings.setZoomControlsEnabled(false);  //显示隐藏 缩放地图
        mUiSettings.setLogoBottomMargin(-50);//隐藏logo
        mUiSettings.setRotateGesturesEnabled(false);
    }


    /**
     * 绘制两个坐标点之间的线段,从以前位置到现在位置
     */
    private void setUpMap(List<LatLng> points) {
        // 绘制一个大地曲线
        aMap.addPolyline((new PolylineOptions())
                .addAll(points)
                .geodesic(true).color(Color.RED));
        LatLngBounds.Builder b = LatLngBounds.builder();
        for (int i = 0; i < points.size(); i++) {
            b.include(points.get(i));
        }
        LatLngBounds bounds = b.build();
        aMap.animateCamera(CameraUpdateFactory.newLatLngBounds(bounds, 100));
        SmoothMoveMarker smoothMarker = new SmoothMoveMarker(aMap);
// 设置滑动的图标
        smoothMarker.setDescriptor(BitmapDescriptorFactory.fromResource(R.drawable.ic_car_position));
        LatLng drivePoint = points.get(0);
        Pair<Integer, LatLng> pair = SpatialRelationUtil.calShortestDistancePoint(points, drivePoint);
        points.set(pair.first, drivePoint);
        List<LatLng> subList = points.subList(pair.first, points.size());
// 设置滑动的轨迹左边点
        smoothMarker.setPoints(subList);
// 设置滑动的总时间
        smoothMarker.setTotalDuration(40);
// 开始滑动
        smoothMarker.startSmoothMove();
    }

    @Override
    protected void initEvent() {
        getMvpPresenter().getCarTripTrack(AESUtil.encrypt(vin, Constants.TRACK_KEY), token, infoArrayBeen.get(curPosition).getBeginTime(), infoArrayBeen.get(curPosition).getEndTime());
        viewPager.setCurrentItem(curPosition);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                curPosition = position;
                imgPre.setVisibility(position == 0 ? View.GONE : View.VISIBLE);
                imgNext.setVisibility(position == (adapter.getCount() - 1) ? View.GONE : View.VISIBLE);
                CarTripDayModel.InfoArrayBean bean = infoArrayBeen.get(position);
                getMvpPresenter().getCarTripTrack(AESUtil.encrypt(vin, Constants.TRACK_KEY), token, bean.getBeginTime(), bean.getEndTime());
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mMapView != null)
            mMapView.onDestroy();
    }


    @Override
    protected int getLayout() {
        return R.layout.activity_travel_path;
    }

    @Override
    public void setTripTrack(TripTrackModel model) {
        aMap.clear();
        List<LatLng> latLngs = new ArrayList<>();
        if (model.getGpsArray() != null && model.getGpsArray().size() > 0) {
            for (int i = 0; i < model.getGpsArray().size(); i++) {
                LatLng latLng = new LatLng(model.getGpsArray().get(i).getLatitudeGD(), model.getGpsArray().get(i).getLongitudeGD());
                latLngs.add(latLng);
            }
            setUpMap(latLngs);
            addMarkersToMap(latLngs.get(0), latLngs.get(latLngs.size() - 1));
        }
    }

    private void addMarkersToMap(LatLng start, LatLng end) {
        markerOption = new MarkerOptions().icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_start))
                .position(start)
                .draggable(false);
        aMap.addMarker(markerOption);
        markerOption = new MarkerOptions().icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_end))
                .position(end)
                .draggable(false);
        aMap.addMarker(markerOption);
    }

    @OnClick({R2.id.img_pre, R2.id.img_next})
    public void onViewClicked(View view) {
        int i = view.getId();
        if (i == R.id.img_pre) {
            viewPager.setCurrentItem(curPosition - 1);
        } else if (i == R.id.img_next) {
            viewPager.setCurrentItem(curPosition + 1);
        }
    }
}
