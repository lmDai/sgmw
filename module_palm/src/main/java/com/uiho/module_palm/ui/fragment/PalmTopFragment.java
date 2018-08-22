package com.uiho.module_palm.ui.fragment;

import android.content.Context;
import android.graphics.Color;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.amap.api.fence.GeoFenceClient;
import com.amap.api.maps.AMap;
import com.amap.api.maps.CameraUpdate;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.MapView;
import com.amap.api.maps.UiSettings;
import com.amap.api.maps.model.BitmapDescriptor;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.CameraPosition;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.Marker;
import com.amap.api.maps.model.MarkerOptions;
import com.amap.api.maps.model.MyLocationStyle;
import com.amap.api.maps.model.Polygon;
import com.uiho.module_palm.R;
import com.uiho.module_palm.R2;
import com.uiho.module_palm.base.BasePalmFragment;
import com.uiho.module_palm.contract.PalmTopContract;
import com.uiho.module_palm.presenter.PalmTopPresenter;
import com.uiho.sgmw.common.Constants;
import com.uiho.sgmw.common.base.RouterPath;
import com.uiho.sgmw.common.eventbus.EventType;
import com.uiho.sgmw.common.model.BurglarAlarmModel;
import com.uiho.sgmw.common.model.CarInfoModel;
import com.uiho.sgmw.common.model.CarSecurityFenceModel;
import com.uiho.sgmw.common.model.CarStatusModel;
import com.uiho.sgmw.common.model.UserModel;
import com.uiho.sgmw.common.mvp_senior.annotaions.CreatePresenterAnnotation;
import com.uiho.sgmw.common.utils.AESUtil;
import com.uiho.sgmw.common.utils.DateUtils;
import com.uiho.sgmw.common.utils.SPUtils;
import com.uiho.sgmw.common.utils.UserUtils;
import com.uiho.sgmw.common.utils.glide.ImageLoadUtils;
import com.uiho.sgmw.common.widget.NewCreditSesameView;
import com.uiho.sgmw.common.widget.SelectorFactory;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;

import butterknife.BindView;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * 作者：uiho_mac
 * 时间：2018/8/8
 * 描述：掌上宝骏顶部
 * 版本：1.0
 * 修订历史：
 */
@CreatePresenterAnnotation(PalmTopPresenter.class)
@Route(path = RouterPath.PALM_TOP_FRAGMENT) // 路由地址，必须注明
public class PalmTopFragment extends BasePalmFragment<PalmTopContract.View, PalmTopPresenter>
        implements PalmTopContract.View {
    @BindView(R2.id.txt_time)
    TextView txtTime;
    @BindView(R2.id.img_weather)
    ImageView imgWeather;
    @BindView(R2.id.txt_name)
    TextView txtName;
    @BindView(R2.id.txt_vin)
    TextView txtVin;
    @BindView(R2.id.img_head)
    ImageView imgHead;
    @BindView(R2.id.txt_nick_name)
    TextView txtNickName;
    @BindView(R2.id.btn_park)
    Button btnPark;
    @BindView(R2.id.txt_enduranceMileage)
    TextView txtEnduranceMileage;
    @BindView(R2.id.txt_elec)
    TextView txtElec;
    @BindView(R2.id.sesame_view)
    NewCreditSesameView sesameView;
    @BindView(R2.id.txt_total_mileage)
    TextView txtTotalMileage;
    @BindView(R2.id.txt_driving_state)
    TextView txtDrivingState;
    @BindView(R2.id.txt_light)
    TextView txtLight;
    @BindView(R2.id.txt_door)
    TextView txtDoor;
    @BindView(R2.id.txt_timestamp)
    TextView txtTimestamp;
    @BindView(R2.id.map_view)
    MapView mapView;
    @BindView(R2.id.txt_car_time)
    TextView txtCarTime;
    @BindView(R2.id.ib_postion)
    ImageButton ibPostion;
    @BindView(R2.id.switch_alarm)
    Switch switchAlarm;
    @BindView(R2.id.txt_fence)
    TextView txtFence;
    @BindView(R2.id.layout_elec_fence)
    LinearLayout layoutElecFence;
    Unbinder unbinder;
    private UiSettings mUiSettings;
    private MarkerOptions markerOption;
    private GeoFenceClient mGeoFenceClient;
    private String token;
    private String phoneNum;
    public String vin;
    private CarStatusModel status;
    private UserModel userModel;
    private Polygon polygon;
    private boolean hideVin = true;
    private Marker marker;
    private AMap aMap;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mapView.onCreate(savedInstanceState);
        if (aMap == null) {
            aMap = mapView.getMap();
        }
        setMapCustomStyleFile(mContext);
        aMap.setMapCustomEnable(true);//开启自定义
        initMap();
        mGeoFenceClient = new GeoFenceClient(mContext);
    }

    @Override
    protected void initView(LayoutInflater inflater) {
        txtTime.setText(DateUtils.formatDate(new Date(), "MM.dd") + " " + DateUtils.getWeek(new Date()));
        userModel = UserUtils.getUser(mContext);
        if (userModel != null) {
            ImageLoadUtils.displayRound(mContext, imgHead, userModel.getAvatar());
            if (!TextUtils.isEmpty(userModel.getVins())) {
                String[] vins = userModel.getVins().split(",");
                if (vins.length > 0) {
                    vin = vins[0];
                }
                //显示个人信息
                String newVin = "****" + vin.substring(vin.length() - 6, vin.length());
                //显示个人信息
                txtVin.setText(newVin);
            }
        }
        ibPostion.setBackground(SelectorFactory.newShapeSelector()
                .setDefaultBgColor(ContextCompat.getColor(mContext, R.color.color_white_trans))
                .setPressedBgColor(ContextCompat.getColor(mContext, R.color.color_85_white))
                .setCornerRadius(10)
                .create());
    }

    @Override
    protected void initEvent() {
        super.initEvent();
        initData();
        //设置防盗报警开关
        switchAlarm.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (vin != null) {
                    if (isChecked) {
                        getMvpPresenter().setBurglarAlarm(AESUtil.encrypt(vin, Constants.WARN_SETTING_KEY), 1, token);
                    } else {
                        getMvpPresenter().setBurglarAlarm(AESUtil.encrypt(vin, Constants.WARN_SETTING_KEY), 0, token);
                    }
                }
            }
        });
    }

    public void initData() {
        phoneNum = (String) SPUtils.getParam(mContext, Constants.PHONE, "");
        token = (String) SPUtils.getParam(mContext, Constants.TOKEN, "");
        getMvpPresenter().getUserInfo(phoneNum, token);//获取个人信息
    }

    @Override
    public void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mapView != null)
            mapView.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMainThread(EventType type) {

    }

    /**
     * 初始化地图
     */
    private void initMap() {
        aMap.setOnMyLocationChangeListener(new AMap.OnMyLocationChangeListener() {
            @Override
            public void onMyLocationChange(Location location) {

            }
        });
        MyLocationStyle myLocationStyle = new MyLocationStyle();//初始化定位蓝点样式类
        myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_LOCATION_ROTATE_NO_CENTER);//连续定位、且将视角移动到地图中心点，定位点依照设备方向旋转，并且会跟随设备移动。（1秒1次定位）如果不设置myLocationType，默认也会执行此种模式。
        BitmapDescriptor bitmapDescriptor = BitmapDescriptorFactory.fromResource(R.drawable.ic_position);
        myLocationStyle.myLocationIcon(bitmapDescriptor);//设置定位蓝点的icon图标方法，需要用到BitmapDescriptor类对象作为参数。
        myLocationStyle.interval(2000); //设置连续定位模式下的定位间隔，只在连续定位模式下生效，单次定位模式下不会生效。单位为毫秒。
        myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_FOLLOW_NO_CENTER);//连续定位、蓝点不会移动到地图中心点，并且蓝点会跟随设备移动。
        myLocationStyle.strokeColor(Color.argb(0, 0, 0, 0));// 设置圆形的边框颜色
        myLocationStyle.radiusFillColor(Color.argb(0, 0, 0, 0));// 设置圆形的填充颜色
        myLocationStyle.strokeWidth(0);//设置定位蓝点精度圈的边框宽度的方法。
        aMap.setMyLocationStyle(myLocationStyle);//设置定位蓝点的Style
        aMap.setMyLocationEnabled(true);// 设置为true表示启动显示定位蓝点，false表示隐藏定位蓝点并不进行定位，默认是false。
        mUiSettings = aMap.getUiSettings();//实例化UiSettings类对象
        mUiSettings.setZoomControlsEnabled(false);  //显示隐藏 缩放地图
        mUiSettings.setLogoBottomMargin(-50);//隐藏logo
        mUiSettings.setScrollGesturesEnabled(false);
        mUiSettings.setZoomGesturesEnabled(false);
    }

    private void setLocation(Double longitude, Double latitude) {
        if (latitude == 0 && longitude == 0) {
            return;
        }
        CameraUpdate mCameraUpdate = CameraUpdateFactory.newCameraPosition(new CameraPosition(new LatLng(latitude, longitude), 18, 0, 0));
        aMap.moveCamera(mCameraUpdate);
    }

    /**
     * 设置自定义地图
     */
    private void setMapCustomStyleFile(Context context) {
        String styleName = "style.data";
        FileOutputStream outputStream = null;
        InputStream inputStream = null;
        String filePath = null;
        try {
            inputStream = context.getAssets().open(styleName);
            byte[] b = new byte[inputStream.available()];
            inputStream.read(b);

            filePath = context.getFilesDir().getAbsolutePath();
            File file = new File(filePath + "/" + styleName);
            if (file.exists()) {
                file.delete();
            }
            file.createNewFile();
            outputStream = new FileOutputStream(file);
            outputStream.write(b);

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (inputStream != null)
                    inputStream.close();

                if (outputStream != null)
                    outputStream.close();

            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        aMap.setCustomMapStylePath(filePath + "/" + styleName);
        aMap.showMapText(true);
    }

    @Override
    public void setStatus(CarStatusModel model) {
        this.status = status;
        if (status != null) {
            if (!TextUtils.isEmpty(status.getLeftFrontDoor()) && !TextUtils.isEmpty(status.getRightFrontDoor())) {
                if (status.getLeftFrontDoor().equals("0") && status.getRightFrontDoor().equals("0")) {
                    txtDoor.setTextColor(ContextCompat.getColor(mContext, R.color.color_85_white));
                    txtDoor.setText("关闭");
                } else {
                    txtDoor.setTextColor(ContextCompat.getColor(mContext, R.color.color_light_yellow));
                    txtDoor.setText("未关闭");
                }
            }
            if (!TextUtils.isEmpty(status.getLeftTurnLight()) && !TextUtils.isEmpty(status.getRightTurnLight()) && !TextUtils.isEmpty(status.getPositionLight())) {
                if (status.getLeftTurnLight().equals("0") && status.getRightTurnLight().equals("0") && status.getPositionLight().equals("0")) {
                    txtLight.setTextColor(ContextCompat.getColor(mContext, R.color.color_85_white));
                    txtLight.setText("关闭");
                } else {
                    txtLight.setTextColor(ContextCompat.getColor(mContext, R.color.color_light_yellow));
                    txtLight.setText("未关闭");
                }
            }
            txtElec.setText(Integer.parseInt(status.getCurrentPowerPercent()) + "%");
            sesameView.setSesameValues(Integer.parseInt(status.getCurrentPowerPercent()));
            if (!TextUtils.isEmpty(status.getDrivingState())) {
                switch (status.getDrivingState()) {
                    case "0":
                        txtDrivingState.setTextColor(ContextCompat.getColor(mContext, R.color.color_85_white));
                        txtDrivingState.setText("静置中");
                        break;
                    case "1":
                        txtDrivingState.setTextColor(ContextCompat.getColor(mContext, R.color.color_light_yellow));
                        txtDrivingState.setText("行驶中");
                        break;
                    case "2":
                        txtDrivingState.setTextColor(ContextCompat.getColor(mContext, R.color.colorAccent));
                        txtDrivingState.setText("充电中");
                        break;
                }
            }
            txtTimestamp.setText(status.getCanCollectionTime());
            txtCarTime.setText(status.getGpsCollectionTime());
            txtEnduranceMileage.setText(status.getEnduranceMileage() + "km");
            txtTotalMileage.setText("总里程" + "\n" + status.getTotalMileage() + "\n" + "km");
            setMarker(status.getLongitudeGD(), status.getLatitudeGD(), status.getOrientation());
        }
    }

    @Override
    public void setBurglarAlarm(BurglarAlarmModel model) {
        if (model != null) {
            if (!TextUtils.isEmpty(model.getAlarmState() + "")) {
                switchAlarm.setChecked(model.getAlarmState() != 0);
            }
        }
    }

    @Override
    public void setUserInfo(UserModel userInfo) {
        if (!TextUtils.isEmpty(userInfo.getVins())) {
            String[] vins = userInfo.getVins().split(",");
            if (vins.length > 0) {
                vin = vins[0];
            }
            String newVin = "****" + vin.substring(vin.length() - 6, vin.length());
            //显示个人信息
            txtVin.setText(newVin);
            txtNickName.setText(userInfo.getXm());
            ImageLoadUtils.displayRoundError(mContext, imgHead, userInfo.getAvatar(), R.drawable.user);
            getMvpPresenter().burglarAlarm(AESUtil.encrypt(vin, Constants.WARN_SEARCH_KEY), token);//查询防盗警报
            getMvpPresenter().getCarStatus(AESUtil.encrypt(vin, Constants.CAR_STATUS_KEY), token);
            getMvpPresenter().getCarSecurityFence(AESUtil.encrypt(vin, Constants.ELEC_FANCE_SEARCH_KEY), token);
            getMvpPresenter().getCarInfo(AESUtil.encrypt(vin, Constants.CAR_KEY), token);
        }
    }

    @Override
    public void setCarSecurityFence(CarSecurityFenceModel model) {
        if (model.getFenceState() == 1) {
            txtFence.setTextColor(ContextCompat.getColor(mContext, R.color.colorAccent));
            txtFence.setText("已开启");
        } else {
            txtFence.setTextColor(ContextCompat.getColor(mContext, R.color.color_85_white));
            txtFence.setText("已关闭");
        }
    }

    @Override
    public void setCarInfo(CarInfoModel model) {
        if (model != null && !TextUtils.isEmpty(model.getAlias())) {
            txtName.setText(model.getAlias());
        }
        EventBus.getDefault().post(new EventType(EventType.CAR_INFO, model));
    }

    /**
     * 设置车位置
     *
     * @param longitude
     * @param latitude
     */
    public void setMarker(Double longitude, Double latitude, double orientation) {
        LatLng latLng = new LatLng(latitude, longitude);
        markerOption = new MarkerOptions().icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_car_position))
                .position(latLng)
                .draggable(true);
        if (aMap == null) {
            aMap = mapView.getMap();
        }
        if (marker != null) {
            marker.remove();
        }
        marker = aMap.addMarker(markerOption);
        marker.setRotateAngle(360 - (float) orientation);//设置车辆旋转角度
        setLocation(longitude, latitude);
    }

    @Override
    protected int getLayout() {
        return R.layout.fragment_plam_top;
    }

    @OnClick({R2.id.img_head, R2.id.btn_park, R2.id.ib_postion, R2.id.layout_elec_fence})
    public void onViewClicked(View view) {
        int i = view.getId();
        if (i == R.id.img_head) {
        } else if (i == R.id.btn_park) {
        } else if (i == R.id.ib_postion) {
            ARouter.getInstance().build(RouterPath.PALM_POSITION)
                    .withString("token", token)
                    .withString("vin", vin)
                    .withTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right)
                    .navigation();
        } else if (i == R.id.layout_elec_fence) {
        }
    }
}
