package com.uiho.sgmw.common.base;

import android.app.Application;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.multidex.MultiDex;
import android.support.v7.app.AppCompatDelegate;

import com.alibaba.android.arouter.launcher.ARouter;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.DefaultRefreshFooterCreator;
import com.scwang.smartrefresh.layout.api.DefaultRefreshHeaderCreator;
import com.scwang.smartrefresh.layout.api.RefreshFooter;
import com.scwang.smartrefresh.layout.api.RefreshHeader;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.footer.ClassicsFooter;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;
import com.uiho.sgmw.common.R;
import com.uiho.sgmw.common.utils.Utils;

/**
 * 作者：uiho_mac
 * 时间：2018/8/6
 * 描述：
 * 版本：1.0
 * 修订历史：
 */
public class BaseApplication extends Application {
    private static BaseApplication mApplication;
    static {
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);//启用矢量图兼容
        SmartRefreshLayout.setDefaultRefreshHeaderCreator(new DefaultRefreshHeaderCreator() {
            @NonNull
            @Override
            public RefreshHeader createRefreshHeader(@NonNull Context context, @NonNull RefreshLayout layout) {

                layout.setPrimaryColorsId(R.color.colorPrimary, R.color.color_white);
                return new ClassicsHeader(context);
            }
        });
        SmartRefreshLayout.setDefaultRefreshFooterCreator(new DefaultRefreshFooterCreator() {
            @NonNull
            @Override
            public RefreshFooter createRefreshFooter(@NonNull Context context, @NonNull RefreshLayout layout) {
                layout.setPrimaryColorsId(R.color.colorPrimary, R.color.color_white);
                return new ClassicsFooter(context);
            }
        });
    }
    @Override
    public void onCreate() {
        super.onCreate();
        mApplication = this;
        ARouter.openLog();//打印日志
        ARouter.openDebug();//开启调试模式（如果在InstantRun 模式下运行，线上版本需关闭，否则有安全风险）
        ARouter.init(this);//推荐在application中初始化
        Utils.init(this);
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(base);
    }

    /**
     * 利用单利模式获取Application实例
     * @return mApplicaiton
     */
    public static BaseApplication getInstance(){
        if (null==mApplication){
            mApplication=new BaseApplication();
        }
        return mApplication;
    }
}
