package com.uiho.module_palm.ui.fragment;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.amap.api.fence.GeoFenceClient;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.Marker;
import com.amap.api.maps.model.MarkerOptions;
import com.amap.api.maps.model.Polygon;
import com.amap.api.services.weather.LocalWeatherForecastResult;
import com.amap.api.services.weather.LocalWeatherLive;
import com.amap.api.services.weather.LocalWeatherLiveResult;
import com.amap.api.services.weather.WeatherSearch;
import com.amap.api.services.weather.WeatherSearchQuery;
import com.uiho.module_palm.R;
import com.uiho.module_palm.R2;
import com.uiho.module_palm.contract.PalmTopContract;
import com.uiho.module_palm.presenter.PalmTopPresenter;
import com.uiho.module_palm.ui.activity.ElectricFenceActivity;
import com.uiho.module_palm.ui.activity.ParkActivity;
import com.uiho.sgmw.common.Constants;
import com.uiho.sgmw.common.base.BaseMapFragment;
import com.uiho.sgmw.common.base.RouterPath;
import com.uiho.sgmw.common.eventbus.EventType;
import com.uiho.sgmw.common.model.BurglarAlarmModel;
import com.uiho.sgmw.common.model.CarInfoModel;
import com.uiho.sgmw.common.model.CarSecurityFenceModel;
import com.uiho.sgmw.common.model.CarStatusModel;
import com.uiho.sgmw.common.model.UserModel;
import com.uiho.sgmw.common.mvp_senior.annotaions.CreatePresenterAnnotation;
import com.uiho.sgmw.common.utils.AESUtil;
import com.uiho.sgmw.common.utils.ARouterUtils;
import com.uiho.sgmw.common.utils.DateUtils;
import com.uiho.sgmw.common.utils.GDLocationUtils;
import com.uiho.sgmw.common.utils.IntentUtils;
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

/**
 * 作者：uiho_mac
 * 时间：2018/8/8
 * 描述：掌上宝骏顶部
 * 版本：1.0
 * 修订历史：
 */
@CreatePresenterAnnotation(PalmTopPresenter.class)
@Route(path = RouterPath.PALM_TOP_FRAGMENT) // 路由地址，必须注明
public class PalmTopFragment extends BaseMapFragment<PalmTopContract.View, PalmTopPresenter>
        implements PalmTopContract.View, WeatherSearch.OnWeatherSearchListener {
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
    @BindView(R2.id.txt_temper)
    TextView txtTemper;
    @BindView(R2.id.txt_address)
    TextView txtAddress;
    private String token;
    public String vin;
    private CarStatusModel status;
    private Polygon polygon;
    private boolean hideVin = true;
    private Marker marker;
    private WeatherSearchQuery mquery;//天气查询
    private WeatherSearch mweathersearch;
    private String city;


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        mMapView = view.findViewById(R.id.map_view);
        super.onViewCreated(view, savedInstanceState);
        setMapCustomStyleFile(mContext);
        aMap.setMapCustomEnable(true);//开启自定义
        GeoFenceClient mGeoFenceClient = new GeoFenceClient(mContext);
        GDLocationUtils.getInstance().getLoacattion(new GDLocationUtils.OnLocationChangedListener() {
            @Override
            public void onSuccess(double latitude, double longitude, String addressstr) {
                Log.i("single", addressstr);
                mquery = new WeatherSearchQuery(addressstr, WeatherSearchQuery.WEATHER_TYPE_LIVE);
                mweathersearch = new WeatherSearch(mContext);
                mweathersearch.setOnWeatherSearchListener(PalmTopFragment.this);
                mweathersearch.setQuery(mquery);
                mweathersearch.searchWeatherAsyn();//异步搜索
            }

            @Override
            public void onFail(int errCode, String errInfo) {

            }
        });

    }

    @Override
    protected void initView(LayoutInflater inflater) {
        super.initView(inflater);
        txtTime.setText(DateUtils.formatDate(new Date(), "MM.dd") + " " + DateUtils.getWeek(new Date()));
        UserModel userModel = UserUtils.getUser(mContext);
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
        switchAlarm.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (vin != null) {
                if (isChecked) {
                    getMvpPresenter().setBurglarAlarm(AESUtil.encrypt(vin, Constants.WARN_SETTING_KEY), 1, token);
                } else {
                    getMvpPresenter().setBurglarAlarm(AESUtil.encrypt(vin, Constants.WARN_SETTING_KEY), 0, token);
                }
            }
        });
    }

    public void initData() {
        String phoneNum = (String) SPUtils.getParam(mContext, Constants.PHONE, "");
        token = (String) SPUtils.getParam(mContext, Constants.TOKEN, "");
        getMvpPresenter().getUserInfo(phoneNum, token);//获取个人信息
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMainThread(EventType type) {
        if (type.getType() == EventType.PALM) {
            initData();
        }
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
        MarkerOptions markerOption = new MarkerOptions().icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_car_position))
                .position(latLng)
                .draggable(true);
        if (marker != null) {
            marker.remove();
        }
        marker = aMap.addMarker(markerOption);
        marker.setRotateAngle(360 - (float) orientation);//设置车辆旋转角度
        setLocation(new LatLng(latitude, longitude));
    }

    @Override
    protected int getLayout() {
        return R.layout.fragment_plam_top;
    }

    @OnClick({R2.id.img_head, R2.id.btn_park, R2.id.ib_postion, R2.id.layout_elec_fence, R2.id.txt_vin})
    public void onViewClicked(View view) {
        int i = view.getId();
        Bundle bundle = new Bundle();
        if (i == R.id.img_head) {
            ARouterUtils.goPage(RouterPath.PERSON_CENTER_MAIN);
        } else if (i == R.id.btn_park) {
            if (marker != null) {
                LatLng latLng = marker.getPosition();//传递车的位置到停车场页面
                IntentUtils.get().goActivity(mContext, ParkActivity.class, latLng);
            }
        } else if (i == R.id.ib_postion) {
            ARouter.getInstance().build(RouterPath.PALM_POSITION)
                    .withString("token", token)
                    .withString("vin", vin)
                    .withTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right)
                    .navigation();
        } else if (i == R.id.layout_elec_fence) {
            bundle.putString("vin", vin);
            bundle.putString("token", token);
            IntentUtils.get().goActivity(mContext, ElectricFenceActivity.class, bundle);
        } else if (i == R.id.txt_vin) {
            hideVin = !hideVin;
            Drawable drawable;
            if (hideVin) {
                String newVin = "****" + vin.substring(vin.length() - 6, vin.length());
                //显示个人信息
                txtVin.setText(newVin);
                drawable = ContextCompat.getDrawable(mContext, R.drawable.ic_hide);
            } else {
                //显示个人信息
                txtVin.setText(vin);
                drawable = ContextCompat.getDrawable(mContext, R.drawable.ic_visible);
            }
            drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight()); //设置边界
            txtVin.setCompoundDrawables(null, null, drawable, null);//画在右边
        }
    }

    @Override
    public void onWeatherLiveSearched(LocalWeatherLiveResult weatherLiveResult, int rCode) {
        if (rCode == 1000) {//返回天气信息
            if (weatherLiveResult != null && weatherLiveResult.getLiveResult() != null) {
                LocalWeatherLive weatherlive = weatherLiveResult.getLiveResult();
                txtAddress.setText(weatherlive.getCity());//城市
//                weather.setText(weatherlive.getWeather());
                txtTemper.setText(String.format("%s℃", weatherlive.getTemperature()));//温度
            }
        }
    }

    @Override
    public void onWeatherForecastSearched(LocalWeatherForecastResult localWeatherForecastResult, int i) {

    }
}
