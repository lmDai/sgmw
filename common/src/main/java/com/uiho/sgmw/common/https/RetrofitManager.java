package com.uiho.sgmw.common.https;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.uiho.sgmw.common.Constants;
import com.uiho.sgmw.common.base.BaseApi;
import com.uiho.sgmw.common.https.intercept.InterceptorUtil;
import com.uiho.sgmw.common.model.FileResponseBody;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * 作者：uiho_mac
 * 时间：2018/8/6
 * 描述：rxjava+retrofit+框架封装
 * 版本：1.0
 * 修订历史：
 */
public class RetrofitManager {
    /**
     * 保存一个retrofit的实例，通过吸（baseUrl来获取）
     */
    private HashMap<String, Retrofit> mRetrofitHashMap = new HashMap<>();

    private static final int DEFAULT_MILLISECONDS = 60000;

    /**
     * 内部类单列设计模式
     */
    private RetrofitManager() {
    }

    private static class RetrofitManagerInstance {
        private final static RetrofitManager RETROFIT_MANAGER = new RetrofitManager();
    }

    public static RetrofitManager getInstance() {
        return RetrofitManagerInstance.RETROFIT_MANAGER;
    }

    /**
     * 获取retrofit的实例
     *
     * @return Retrofit
     */
    private Retrofit getRetrofit(String baseurl) {
        Retrofit retrofit;

        if (mRetrofitHashMap.containsKey(baseurl)) {
            retrofit = mRetrofitHashMap.get(baseurl);
        } else {
            retrofit = createrRetrofit(baseurl);
        }

        return retrofit;
    }

    /**
     * 创建retrofit
     *
     * @return Retrofit
     */
    private Retrofit createrRetrofit(String baseurl) {
        File cacheFile = new File(Constants.PATH_CACHE);
        Cache cache = new Cache(cacheFile, 1024 * 1024 * 50);
        OkHttpClient httpClient = new OkHttpClient().newBuilder()
                .readTimeout(DEFAULT_MILLISECONDS, TimeUnit.SECONDS)
                .connectTimeout(DEFAULT_MILLISECONDS, TimeUnit.SECONDS)
                .writeTimeout(DEFAULT_MILLISECONDS, TimeUnit.SECONDS)
//                .addNetworkInterceptor(new InterceptorUtil().HeaderInterceptor(BaseApplication.getInstance()))//添加其他拦截器
                .addInterceptor(InterceptorUtil.LogInterceptor())//添加日志拦截器
                .addInterceptor(new InterceptorUtil().cacheInterceptor())
                .cache(cache)
                .retryOnConnectionFailure(true)
                .build();

        return new Retrofit.Builder()
                .baseUrl(baseurl)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(httpClient)
                .build();
    }

    /**
     * 根据各模块业务接口 获取不同的retrofit service接口对象
     */
    public <T> T getRetrofitService(Class<T> cls) {

        return createrRetrofit(BaseApi.getBaseUrl()).create(cls);
    }

    /**
     * 根据各模块业务接口 获取不同的retrofit service接口对象
     * 传递url
     */
    public <T> T getRetrofitServiceByUrl(String url, Class<T> cls) {
        return getRetrofit(url).create(cls);
    }

    /**
     * 根据各模块业务接口 获取不同的retrofit service接口对象
     * 传递url
     */
    public <T> T getRetrofitDownLoad(String url, Class<T> cls) {
        return createDownLoadRetrofit(url).create(cls);
    }

    public Retrofit createDownLoadRetrofit(String url) {
        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(10, TimeUnit.SECONDS)
                .addInterceptor(new Interceptor() {
                    @Override
                    public Response intercept(Chain chain) throws IOException {
                        Response originalResponse = chain.proceed(chain.request());
                        return originalResponse
                                .newBuilder()
                                .body(new FileResponseBody(originalResponse))//将自定义的ResposeBody设置给它
                                .build();
                    }
                })
                .build();

        Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();

        Retrofit retrofit = new Retrofit.Builder()
                .client(client)
                .baseUrl(url)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
        return retrofit;
    }
}
