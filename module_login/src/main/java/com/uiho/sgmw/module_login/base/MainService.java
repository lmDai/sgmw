package com.uiho.sgmw.module_login.base;

import com.uiho.sgmw.common.base.BaseResponse;
import com.uiho.sgmw.common.model.UpdateModel;

import io.reactivex.Observable;
import okhttp3.ResponseBody;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Streaming;
import retrofit2.http.Url;

/**
 * 作者：uiho_mac
 * 时间：2018/8/6
 * 描述：
 * 版本：1.0
 * 修订历史：
 */
public interface MainService {
    //验证登陆
    @FormUrlEncoded
    @POST(MainApi.VERIFY_LOGIN)
    Observable<BaseResponse<Boolean>> verifyLogin(@Field("token") String token);

    //登录
    @FormUrlEncoded
    @POST(MainApi.LOGIN)
    Observable<BaseResponse<String>> login(@Field("phoneNum") String phoneNum,
                                           @Field("password") String password);

    //检查应用版本
    @GET("app.json")
    Observable<UpdateModel> checkVersion();

    @Streaming
    @GET
    Observable<ResponseBody> updateApp(@Url String url);
}
