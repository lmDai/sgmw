package com.uiho.module_palm.base;

import com.uiho.sgmw.common.base.BaseEnCodeResponse;
import com.uiho.sgmw.common.base.BaseResponse;

import io.reactivex.Observable;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Headers;
import retrofit2.http.POST;

/**
 * 作者：uiho_mac
 * 时间：2018/8/8
 * 描述：
 * 版本：1.0
 * 修订历史：
 */
public interface PalmService {
    //个人信息
    @FormUrlEncoded
    @POST("user/info")
    Observable<BaseResponse<String>> getUserInfo(@Field("phoneNum") String phoneNum, @Field("token") String token);

    //车辆状态
    @FormUrlEncoded
    @POST("car/data/state")
    Observable<BaseResponse<String>> getCarStatus(@Field("vin") String vin, @Field("token") String token);

    //车辆信息
    @FormUrlEncoded
    @POST("car/data/info")
    Observable<BaseResponse<String>> getCarInfo(@Field("vin") String vin, @Field("token") String token);

    //防盗警报
    @FormUrlEncoded
    @POST("car/security/burglarAlarmSelect")
    Observable<BaseResponse<String>> burglarAlarm(@Field("vin") String vin, @Field("token") String token);

    //防盗警报开关设置
    @FormUrlEncoded
    @POST("car/security/burglarAlarmSet")
    Observable<BaseResponse<String>> setBurglarAlarm(@Field("vin") String vin, @Field("alarmState") Integer alarmState,
                                                     @Field("token") String token);

    //日行驶信息
    @FormUrlEncoded
    @POST("car/trip/day/drive")
    Observable<BaseResponse<String>> getCarTripDay(@Field("vin") String vin, @Field("driveDate") String driveDate,
                                                   @Field("token") String token);

    //指定日期段的每日行程信息
    @FormUrlEncoded
    @POST("car/trip/days")
    Observable<BaseResponse<String>> getCarTripDays(@Field("vin") String vin, @Field("beginDate") String beginDate,
                                                    @Field("endDate") String endDate, @Field("token") String token);

    //指定日期段内的每月行程信息
    @FormUrlEncoded
    @POST("car/trip/months")
    Observable<BaseResponse<String>> getCarTripMonth(@Field("vin") String vin, @Field("beginMonth") String beginDate,
                                                     @Field("endMonth") String endDate, @Field("token") String token);

    //电子围栏查询
    @FormUrlEncoded
    @POST("car/security/fenceSelect")
    Observable<BaseResponse<String>> getCarSecurityFence(@Field("vin") String vin, @Field("token") String token);

    //行驶轨迹
    @FormUrlEncoded
    @POST("car/trip/track")
    Observable<BaseResponse<String>> getCarTripTrack(@Field("vin") String vin, @Field("token") String token,
                                                     @Field("beginTime") String beginTime, @Field("endTime") String endTime);

    //日充电信息
    @FormUrlEncoded
    @POST("car/trip/day/charge")
    Observable<BaseResponse<String>> getCarTripDayCharge(@Field("vin") String vin, @Field("chargeDate") String chargeDate,
                                                         @Field("token") String token);

    //电子围栏重设
    @FormUrlEncoded
    @POST("car/security/fenceReSet")
    Observable<BaseResponse<String>> carSecurityFenceReset(@Field("fenceData") String fenceData,
                                                           @Field("token") String token);

    //电子围栏开关设置
    @FormUrlEncoded
    @POST("car/security/fenceSet")
    Observable<BaseResponse<String>> carSecurityFenceSet(@Field("fenceState") String fenceState,
                                                         @Field("token") String token, @Field("vin") String vin);

    //停车位
    @Headers({"Content-Type: application/json", "Accept: application/json"})
    @POST("parking/v1/queryParkingSpaceByCenterPoint")
    Observable<BaseEnCodeResponse<String>> getParks(@Body RequestBody route);
}
