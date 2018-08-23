package com.uiho.module_palm.ui.activity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.amap.api.location.AMapLocation;
import com.amap.api.maps.AMap;
import com.amap.api.maps.CameraUpdate;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.MapView;
import com.amap.api.maps.UiSettings;
import com.amap.api.maps.model.BitmapDescriptor;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.CameraPosition;
import com.amap.api.maps.model.Circle;
import com.amap.api.maps.model.CircleOptions;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.Marker;
import com.amap.api.maps.model.MarkerOptions;
import com.amap.api.maps.model.MyLocationStyle;
import com.amap.api.maps.model.animation.Animation;
import com.amap.api.maps.model.animation.ScaleAnimation;
import com.amap.api.services.core.LatLonPoint;
import com.amap.api.services.geocoder.GeocodeResult;
import com.amap.api.services.geocoder.GeocodeSearch;
import com.amap.api.services.geocoder.RegeocodeQuery;
import com.amap.api.services.geocoder.RegeocodeResult;
import com.amap.api.services.help.Tip;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.uiho.module_palm.R;
import com.uiho.module_palm.R2;
import com.uiho.module_palm.contract.ParksContract;
import com.uiho.module_palm.presenter.ParksPresenter;
import com.uiho.module_palm.ui.adapter.ParkAdapter;
import com.uiho.sgmw.common.base.BaseMvpActivity;
import com.uiho.sgmw.common.base.RouterPath;
import com.uiho.sgmw.common.model.ParksModel;
import com.uiho.sgmw.common.mvp_senior.annotaions.CreatePresenterAnnotation;
import com.uiho.sgmw.common.utils.AdapterUtil;
import com.uiho.sgmw.common.utils.GDLocationUtils;
import com.uiho.sgmw.common.utils.IntentUtils;
import com.uiho.sgmw.common.utils.RecyclerViewUtils;
import com.uiho.sgmw.common.utils.SystemUtils;

import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import butterknife.BindView;
import butterknife.OnClick;

import static com.uiho.sgmw.common.utils.IntentUtils.OPEN_ACTIVITY_KEY;

/**
 * 作者：uiho_mac
 * 时间：2018/7/17
 * 描述：停车场
 * 版本：1.0
 * 修订历史：
 */
@CreatePresenterAnnotation(ParksPresenter.class)
@Route(path = RouterPath.PALM_PARK) // 路由地址，必须注明
public class ParkActivity extends BaseMvpActivity<ParksContract.View, ParksPresenter> implements ParksContract.View {
    @BindView(R2.id.map_view)
    MapView mapView;
    @BindView(R2.id.recyclerview)
    RecyclerView recyclerview;
    @BindView(R2.id.bottom_sheet)
    RelativeLayout bottomSheet;
    @BindView(R2.id.cl_chouti)
    CoordinatorLayout clChouti;
    @BindView(R2.id.bt_tv_title)
    TextView btTvTitle;
    @BindView(R2.id.btn_add)
    Button btnAdd;
    @BindView(R2.id.btn_del)
    Button btnDel;
    @BindView(R2.id.rl_zoom)
    RelativeLayout rlZoom;
    @BindView(R2.id.radio_group)
    RadioGroup radioGroup;
    @BindView(R2.id.design_bottom_sheet_bar)
    AppBarLayout bottomSheetBar;
    @BindView(R2.id.app_search_bar)
    AppBarLayout appSearchBar;
    @BindView(R2.id.frame_layout)
    FrameLayout frameLayout;
    private AMap aMap;
    private BottomSheetBehavior<RelativeLayout> behavior;
    private final List<ParksModel> mList = new ArrayList<>();
    private ParkAdapter adapter;
    private double longitude, latitude;
    private List<Marker> markers = new ArrayList<>();
    private LatLng latLngCar;
    private LatLng latLngMy;
    private boolean setBottomSheetHeight = false;
    private List<ParksModel> newList = new ArrayList<>();
    private long firstTime = 0L;
    private Marker screenMarker;
    private Circle circle;//圆形覆盖物

    /**
     * 初始化地图
     */
    private void initMap() {
        aMap.setOnMyLocationChangeListener(new AMap.OnMyLocationChangeListener() {
            @Override
            public void onMyLocationChange(Location location) {
                latLngMy = new LatLng(location.getLatitude(), location.getLongitude());
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
        mUiSettings.setZoomPosition(1000);
        mUiSettings.setZoomControlsEnabled(false);  //显示隐藏 缩放地图
        mUiSettings.setRotateGesturesEnabled(false);
        mUiSettings.setMyLocationButtonEnabled(false);
        mUiSettings.setLogoBottomMargin(-50);//隐藏logo
        aMap.moveCamera(CameraUpdateFactory.zoomTo(15));//设置地图默认显示缩放层级
        aMap.setOnMapLoadedListener(new AMap.OnMapLoadedListener() {
            @Override
            public void onMapLoaded() {
                addMarkerInScreenCenter();//添加屏幕中心marker
            }
        });
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        setTopTitle("输入地点关键字，查找周边停车场");
        mapView.onCreate(savedInstanceState);
        //初始化地图控制器对象
        if (aMap == null) {
            aMap = mapView.getMap();
        }
        initMap();

        bottomSheetBar.setVisibility(View.INVISIBLE);
        final Drawable upArrow = ContextCompat.getDrawable(mContext, R.drawable.ic_black_back);
        if (upArrow != null) {
            upArrow.setColorFilter(ContextCompat.getColor(mContext, R.color.color_black), PorterDuff.Mode.SRC_ATOP);
        }
        if (getSupportActionBar() != null) {
            getSupportActionBar().setHomeAsUpIndicator(upArrow);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        adapter = new ParkAdapter(mList);
        RecyclerViewUtils.getInstance().buildDefault(mContext, recyclerview);
        recyclerview.setHasFixedSize(true);
        AdapterUtil.getInstance().buildDefault(adapter, recyclerview);
        behavior = BottomSheetBehavior.from(bottomSheet);
        behavior.setState(BottomSheetBehavior.STATE_HIDDEN);
        behavior.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View bottomSheet, @BottomSheetBehavior.State int newState) {
                switch (newState) {
                    case BottomSheetBehavior.STATE_EXPANDED:
                        rlZoom.setVisibility(View.GONE);
                        break;
                    case BottomSheetBehavior.STATE_COLLAPSED:
                        rlZoom.setVisibility(View.VISIBLE);
                        break;
                    case BottomSheetBehavior.STATE_HIDDEN:
                        rlZoom.setVisibility(View.VISIBLE);
                        break;
                }
            }

            @Override
            public void onSlide(@NonNull View bottomSheet, float slideOffset) {
                rlZoom.setVisibility(View.GONE);
                if (bottomSheet.getTop() == bottomSheetBar.getHeight()) {
                    bottomSheetBar.setVisibility(View.VISIBLE);
                    bottomSheetBar.setAlpha(slideOffset);
                    appSearchBar.setVisibility(View.GONE);
                } else {
                    appSearchBar.setAlpha(1 - slideOffset);
                    appSearchBar.setVisibility(View.VISIBLE);
                    bottomSheetBar.setVisibility(View.INVISIBLE);
                }
            }
        });
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.rb_one) {
                    sortAdapter(1);
                } else if (checkedId == R.id.rb_two) {
                    sortAdapter(2);
                } else if (checkedId == R.id.rb_three) {
                    sortAdapter(3);
                }
            }
        });
    }

    /**
     * 根据类型排序显示
     *
     * @param type 分类
     */
    private void sortAdapter(int type) {
        Collections.sort(adapter.getData(), new Comparator<ParksModel>() {
            @Override
            public int compare(ParksModel o2, ParksModel o1) {
                switch (type) {
                    case 1:
                        return String.valueOf(o2.getDistance() * 1000).compareTo(String.valueOf(o1.getDistance() * 1000));
                    case 2:
                        return (o1.getTotalCount() - o1.getUsed()) - (o2.getTotalCount() - o2.getUsed());
                    default:
                        return o1.getTotalCount() - o2.getTotalCount();
                }
            }
        });
        adapter.setNewData(adapter.getData());
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (!setBottomSheetHeight) {
            CoordinatorLayout.LayoutParams params =
                    (CoordinatorLayout.LayoutParams) bottomSheet.getLayoutParams();
            params.height = recyclerview.getHeight();
            bottomSheet.setLayoutParams(params);
            setBottomSheetHeight = true;
        }
    }

    @Override
    protected void initEvent() {
        if (aMap == null) {
            aMap = mapView.getMap();
        }
        GDLocationUtils.getLocation(new GDLocationUtils.MyLocationListener() {
            @Override
            public void result(AMapLocation location) {

            }
        });
        Intent intent = getIntent();
        if (intent != null) {
            latLngCar = intent.getParcelableExtra(OPEN_ACTIVITY_KEY);
            drawMarkerOnMap(latLngCar,
                    R.drawable.ic_car_position,
                    "", "", null);
            if (latLngCar != null) {//判断参数是否为空
                longitude = latLngCar.longitude;
                latitude = latLngCar.latitude;
                setParkLocation(latLngCar, null);
            }

        }
        adapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                ParksModel parksModel = (ParksModel) adapter.getData().get(position);
                if (view.getId() == R.id.txt_go) {//打开高德地图
                    setUpGaodeAppByMine(parksModel.getLatitude(), parksModel.getLongitude(), parksModel.getName());
                }
            }
        });
        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter1, View view, int position) {
                ParksModel parksModel = (ParksModel) adapter1.getData().get(position);
                setParkLocation(null, parksModel);
                behavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
                adapter.setSelected(position);
                recyclerview.scrollToPosition(position);
            }
        });
        aMap.setOnMarkerClickListener(new AMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                if (marker.getObject() instanceof ParksModel) {
                    ParksModel model = (ParksModel) marker.getObject();
                    adapter.setSelectedItem(model);
                    behavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
                    marker.showInfoWindow();
                }
                return true;
            }
        });
        aMap.setOnCameraChangeListener(new AMap.OnCameraChangeListener() {//滑动地图搜索周围停车场
            @Override
            public void onCameraChange(CameraPosition cameraPosition) {
                btTvTitle.setText("正在获取位置信息");
                if (circle != null) circle.remove();
            }

            @Override
            public void onCameraChangeFinish(CameraPosition cameraPosition) {
                addCiclePark(new LatLng(cameraPosition.target.latitude, cameraPosition.target.longitude));
                long secondTime = System.currentTimeMillis();
                if (secondTime - firstTime > 1500) {//延迟请求数据
                    getAddress(new LatLonPoint(cameraPosition.target.latitude, cameraPosition.target.longitude));
                    getMvpPresenter().getParks(cameraPosition.target.longitude, cameraPosition.target.latitude);
                    firstTime = secondTime;
                }
            }
        });
        aMap.setOnMapClickListener(new AMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                behavior.setState(BottomSheetBehavior.STATE_HIDDEN);
            }
        });
    }

    /**
     * 在屏幕中心添加一个Marker
     */
    private void addMarkerInScreenCenter() {
        LatLng latLng = aMap.getCameraPosition().target;
        Point screenPosition = aMap.getProjection().toScreenLocation(latLng);
        screenMarker = aMap.addMarker(new MarkerOptions()
                .anchor(0.5f, 0.5f)
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.purple_pin)));
        //设置Marker在屏幕上,不跟随地图移动
        screenMarker.setPositionByPixels(screenPosition.x, screenPosition.y);

    }

    @Override
    protected int getLayout() {
        showBack = false;
        return R.layout.activity_park;
    }

    @Override
    public void getParksSuccess(List<ParksModel> mList) {
        for (Marker marker : aMap.getMapScreenMarkers()) {
            if (marker.getObject() instanceof ParksModel) {
                marker.remove();
            }
        }
        for (ParksModel model : mList) {
            newList.add(model);
            if (model.getUsed() >= model.getTotalCount()) {
                markers.add(
                        drawMarkerOnMap(new LatLng(model.getLatitude(), model.getLongitude()),
                                R.drawable.ic_park_empty,
                                model.getName(), model.getAddress(), model));
            } else if (model.getUsed() < model.getTotalCount() / 2) {
                markers.add(
                        drawMarkerOnMap(new LatLng(model.getLatitude(), model.getLongitude()),
                                R.drawable.ic_park_enough,
                                model.getName(), model.getAddress(), model));
            } else {
                markers.add(
                        drawMarkerOnMap(new LatLng(model.getLatitude(), model.getLongitude()),
                                R.drawable.ic_park,
                                model.getName(), model.getAddress(), model));
            }
        }
        adapter.setNewData(removeDuplicateUser(mList, radioGroup.getCheckedRadioButtonId()));
    }

    /**
     * 去除list中重复的数据
     *
     * @param users 数据
     * @return list
     */
    private static ArrayList<ParksModel> removeDuplicateUser(List<ParksModel> users, int checkId) {
        Set<ParksModel> set = new TreeSet<>(new Comparator<ParksModel>() {
            @Override
            public int compare(ParksModel o2, ParksModel o1) {
                if (checkId == R.id.rb_one) {
                    return String.valueOf(o2.getDistance() * 1000).compareTo(String.valueOf(o1.getDistance() * 1000));
                } else if (checkId == R.id.rb_two) {
                    return (o1.getTotalCount() - o1.getUsed()) - (o2.getTotalCount() - o2.getUsed());
                } else if (checkId == R.id.rb_three) {
                    return o1.getTotalCount() - o2.getTotalCount();
                } else {
                    return o1.getId().compareTo(o2.getId());
                }
            }
        });
        set.addAll(users);
        return new ArrayList<>(set);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mapView != null)
            mapView.onDestroy();
    }

    /**
     * 我的位置BY高德
     */
    private void setUpGaodeAppByMine(double LATITUDE_B, double LONGTITUDE_B, String name) {
        try {
            //noinspection deprecation
            Intent intent = Intent.getIntent("androidamap://route?sourceApplication=softname&sname=我的位置&dlat=" + LATITUDE_B + "&dlon=" + LONGTITUDE_B + "&dname=" + name + "&dev=1&m=2&t=3");
            if (SystemUtils.isInstallByread("com.autonavi.minimap")) {
                startActivity(intent);
            } else {
                Log.e("single", "没有安装高德地图客户端");
            }
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }

    /**
     * 显示停车场Location
     */
    private void setParkLocation(LatLng latLng, ParksModel park) {
        LatLng newLatLng = null;
        if (latLng != null) {
            newLatLng = latLng;
        } else if (park != null) {
            newLatLng = new LatLng(park.getLatitude(), park.getLongitude());
        }
        CameraUpdate mCameraUpdate = CameraUpdateFactory.newCameraPosition(new CameraPosition(newLatLng, aMap.getCameraPosition().zoom, 0, 0));
        if (aMap == null) {
            aMap = mapView.getMap();
        }
        aMap.moveCamera(mCameraUpdate);
        if (park != null) {
            for (Marker marker : aMap.getMapScreenMarkers()) {
                if (marker.getObject() instanceof ParksModel) {
                    ParksModel markerPark = (ParksModel) marker.getObject();
                    if (TextUtils.equals(park.getId(), markerPark.getId())) {
                        marker.showInfoWindow();
                    }
                }
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data != null) {
            if (resultCode == 0x002) {
                Tip tip = data.getParcelableExtra(OPEN_ACTIVITY_KEY);
                if (tip != null && tip.getPoint() != null) {
                    btTvTitle.setText(tip.getName());
                    longitude = tip.getPoint().getLongitude();
                    latitude = tip.getPoint().getLatitude();
                    getMvpPresenter().getParks(longitude, latitude);//获取附近停车位
                    setParkLocation(new LatLng(latitude, longitude), null);
                }
            }
        }
    }

    private Marker drawMarkerOnMap(LatLng point, int markerIcon, String name, String address, ParksModel model) {
        if (aMap != null && point != null) {
            MarkerOptions markerOption1 = new MarkerOptions().anchor(0.5f, 0.5f)
                    .position(point).title(name)
                    .snippet(address).icon(BitmapDescriptorFactory
                            .fromResource(markerIcon))
                    .draggable(true).period(10);
            Marker marker = aMap.addMarker(markerOption1);
            marker.setObject(model);
            return marker;
        }
        return null;
    }

    @OnClick({R2.id.bt_tv_title, R2.id.ib_car, R2.id.txt_up, R2.id.ib_postion, R2.id.btn_add, R2.id.btn_del, R2.id.btn_back})
    public void onViewClicked(View view) {
        int i = view.getId();
        if (i == R.id.ib_car) {
            setParkLocation(latLngCar, null);
        } else if (i == R.id.ib_postion) {
            setParkLocation(latLngMy, null);
        } else if (i == R.id.btn_add) {
            aMap.moveCamera(CameraUpdateFactory.zoomIn());
        } else if (i == R.id.btn_del) {
            aMap.moveCamera(CameraUpdateFactory.zoomOut());
        } else if (i == R.id.bt_tv_title) {
            int requestCode = 0x001;
            IntentUtils.get().goActivityResult(mContext, InputtipsActivity.class, requestCode);
        } else if (i == R.id.btn_back) {
            behavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
        } else if (i == R.id.txt_up) {
            behavior.setState(BottomSheetBehavior.STATE_COLLAPSED);

        }
    }

    private void getAddress(LatLonPoint latLng) {
        GeocodeSearch geocodeSearch = new GeocodeSearch(this);
        geocodeSearch.setOnGeocodeSearchListener(new GeocodeSearch.OnGeocodeSearchListener() {
            @Override
            public void onRegeocodeSearched(RegeocodeResult regeocodeResult, int rCode) {
                if (rCode == 1000) {//返回结果成功或者失败的响应码。1000为成功，其他为失败
                    String address = regeocodeResult.getRegeocodeAddress().getFormatAddress();
                    btTvTitle.setText(address);
                }
            }

            @Override
            public void onGeocodeSearched(GeocodeResult geocodeResult, int i) {

            }
        });
        RegeocodeQuery query = new RegeocodeQuery(latLng, 100, GeocodeSearch.AMAP);
        geocodeSearch.getFromLocationAsyn(query);
    }

    /**
     * 地上生长的Marker
     */
    private void startGrowAnimation() {
        if (screenMarker != null) {
            Animation animation = new ScaleAnimation(0, 1, 0, 1);
            animation.setInterpolator(new LinearInterpolator());
            //整个移动所需要的时间
            animation.setDuration(500);
            //设置动画
            screenMarker.setAnimation(animation);
            //开始动画
            screenMarker.startAnimation();
        } else {
            Log.e("amap", "screenMarker is null");
        }
    }

    private void addCiclePark(LatLng latLng) {
        startGrowAnimation();
        if (circle != null) {
            circle.remove();
        }
        circle = aMap.addCircle(new CircleOptions()
                .center(latLng)
                .radius(1000)
                .strokeColor(Color.parseColor("#4F888888")) // 边框颜色
                .fillColor(Color.parseColor("#3F888888"))
                .strokeWidth(5));
    }

}
