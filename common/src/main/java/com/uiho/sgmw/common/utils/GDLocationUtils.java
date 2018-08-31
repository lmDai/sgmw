package com.uiho.sgmw.common.utils;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.uiho.sgmw.common.base.BaseApplication;

/**
 * 作者：uiho_mac
 * 时间：2018/3/14
 * 描述：高德地图定位
 * 版本：1.0
 * 修订历史：
 */

public class GDLocationUtils {
    static GDLocationUtils mLocationUtils = null;
    AMapLocationClientOption mLocationOption;

    private GDLocationUtils() {
        initOption();
    }

    public static GDLocationUtils getInstance() {
        if (mLocationUtils == null) {
            synchronized (GDLocationUtils.class) {
                if (mLocationUtils == null) {
                    mLocationUtils = new GDLocationUtils();
                }
            }
        }
        return mLocationUtils;
    }

    public void getLoacattion(OnLocationChangedListener listener) {
        mListener = listener;
        init();
    }

    private void initOption() {
        //初始化定位参数
        AMapLocationClientOption mLocationOption = new AMapLocationClientOption()
                .setNeedAddress(true)//设置是否返回地址信息（默认返回地址信息）
                .setLocationMode(AMapLocationClientOption.
                        AMapLocationMode.Hight_Accuracy)//设置定位模式为高精度模式
                //   .setInterval(Constants.upload_position_time)//设置定位间隔,单位毫秒,默认为2000ms
                .setOnceLocation(true);//获取一次定位结果
        mLocationOption.setOnceLocationLatest(true);//获取最近3s内精度最高的一次定位结果
    }

    private void init() {
        AMapLocationClient mlocationClient = new AMapLocationClient(BaseApplication.getInstance());
        mlocationClient.setLocationListener(new AMapLocationListener() {
            @Override
            public void onLocationChanged(AMapLocation amapLocation) {
                if (amapLocation != null) {
                    if (amapLocation.getErrorCode() == 0) {
                        //定位成功回调信息，设置相关消息
                        amapLocation.getLocationType();//获取当前定位结果来源，如网络定位结果，详见定位类型表
                        if (mListener != null) {
                            mListener.onSuccess(amapLocation.getLatitude(), amapLocation.getLongitude(), amapLocation.getCity());
                        }

                    } else {
                        if (mListener != null) {
                            mListener.onFail(amapLocation.getErrorCode(), amapLocation.getErrorInfo());
                        }
                    }
                }
            }
        });

        //设置定位参数
        mlocationClient.setLocationOption(mLocationOption);
        mlocationClient.startLocation();
    }

    OnLocationChangedListener mListener;

    public interface OnLocationChangedListener {
        /**
         * 成功
         *
         * @param latitude   纬度
         * @param longitude  精度
         * @param addressstr 地址
         */
        void onSuccess(double latitude, double longitude, String addressstr);

        /**
         * 失败
         *
         * @param errCode 错误码
         * @param errInfo 错误信息
         */
        void onFail(int errCode, String errInfo);
    }
}
