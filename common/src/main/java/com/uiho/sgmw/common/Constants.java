package com.uiho.sgmw.common;

import android.os.Environment;

import com.uiho.sgmw.common.base.BaseApplication;

import java.io.File;

/**
 * 作者：uiho_mac
 * 时间：2018/8/7
 * 描述：
 * 版本：1.0
 * 修订历史：
 */
public class Constants {
    public final static String APP_ROOT_PATH = Environment.getExternalStorageDirectory().getAbsolutePath() + "/" + BaseApplication.getInstance().getPackageName();
    public final static String DOWNLOAD_DIR = "/downlaod/";
    private static final String PATH_DATA = BaseApplication.getInstance().getCacheDir().getAbsolutePath() + File.separator + "data";
    public static final String PATH_CACHE = PATH_DATA + File.separator + "NetCache";
    //系统相关
    public static final String USER = "user";//用户信息
    public static final int REQUEST_LOCATION = 0x001;//定位权限申请
    public static final int REQUEST_EXTERNAL = 0x002;//存储权限申请
    public static final String PHONE = "phone";//手机号
    public static final String VIN = "vin";//车架号
    public static final String TOKEN = "token";//token
    public static final String IS_LOGIN = "is_login";//是否登录
    public static final String FIRST_OPEN = "first_open";//是否第一次进入
    public static final String PASSWORD = "password";
    // 密钥
    // 登录接口
    public static final String LOGIN_PWD = "6C02900C72FFE28FE053360116AC597A";
    //个人信息接口
    public static final String USER_KEY = "6A90BA10C6B96B2BE053360116ACD99E";
    // 车辆信息查询接口
    public static final String CAR_KEY = "6B9A3A0BA3C214C7E053360116ACF9A5";
    //  车辆实时状态查询接口
    public static final String CAR_STATUS_KEY = "6BD67993A96EA217E053360116AC4CDF";
    // 防盗警报设置接口
    public static final String WARN_SETTING_KEY = "6BAD5E39B3965321E053360116AC7FE6";
    //   防盗警报查询接口
    public static final String WARN_SEARCH_KEY = "6BAD5E39B3985261E053360116AC7FE6";
    //     电子围栏开关设置接口
    public static final String ELEC_FANCE_KEY = "6BAD5E39B3965321E053360116AC7FE6";
    //      电子围栏重设接口
    public static final String ELEC_FANCE_RESET_KEY = "6BAD5E39B3965321E053360116AC7FE6";
    //       电子围栏查询接口
    public static final String ELEC_FANCE_SEARCH_KEY = "6BAD5E39B3985261E053360116AC7FE6";
    //       日行驶信息接口
    public static final String DAY_TRAVEL_KEY = "6BAD5E39B3942681E053360116AC7FE6";
    //      日充电信息接口6BAD5E39B3972681  6BAD5E39B3962681E053360116AC7FE6
    public static final String DAY_ELEC_KEY = "6BAD5E39B3972681";
    //       指定时间段每日行程信息接口
    public static final String DAYS_TRIP_KEY = "6BBEB501F5370978E053360116AC4131";
    //       指定时间段每月行程信息接口
    public static final String MONTHS_TRIP_KEY = "6BC26E82C78073C2E053360116ACF1C7";
    //      行驶轨迹信息接口
    public static final String TRACK_KEY = "6BD2E078343538FCE053360116ACC3B8";
    //       头像上传接口
    public static final String _KEY = "6BD2E078346818FCE053360116ACC3B8";
    //       车辆别名设置接口
    public static final String CAR_ALIA_SETTING_KEY = "6BD68446A96DA217E053360116AC4CDF";
    public static final int REFRESH = 1;//刷新
    public static final int STOP_REFRESH = 2;//停止刷新
    public static final int LOCATION = 3;//定位
    public static final String PARKING_KEY = "E5F43AFDA3B748ABB1D0EBC9F986E7E0";
    public static final String AUTHER_KEY_PARKING = "35B6EF141D3242469711943F4C769DB2";

}
