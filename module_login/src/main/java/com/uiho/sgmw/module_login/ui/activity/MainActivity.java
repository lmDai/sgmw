package com.uiho.sgmw.module_login.ui.activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.FileProvider;
import android.view.KeyEvent;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.flyco.tablayout.SlidingTabLayout;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.tbruyelle.rxpermissions2.RxPermissions;
import com.uiho.sgmw.common.BuildConfig;
import com.uiho.sgmw.common.base.BaseMvpActivity;
import com.uiho.sgmw.common.base.RouterPath;
import com.uiho.sgmw.common.base.ViewManager;
import com.uiho.sgmw.common.eventbus.EventType;
import com.uiho.sgmw.common.https.RxBus;
import com.uiho.sgmw.common.model.DownloadModel;
import com.uiho.sgmw.common.model.UpdateModel;
import com.uiho.sgmw.common.mvp_senior.annotaions.CreatePresenterAnnotation;
import com.uiho.sgmw.common.utils.AppUtils;
import com.uiho.sgmw.common.utils.DialogUtils;
import com.uiho.sgmw.common.utils.EventUtil;
import com.uiho.sgmw.common.utils.SystemUtils;
import com.uiho.sgmw.common.utils.TabFragmentPagerAdapter;
import com.uiho.sgmw.common.utils.TabPagerAdapter;
import com.uiho.sgmw.common.widget.BaseLinkPageChangeListener;
import com.uiho.sgmw.common.widget.WrapContentHeightViewPager;
import com.uiho.sgmw.common.widget.dialog.AppUpdateProgressDialog;
import com.uiho.sgmw.module_login.R;
import com.uiho.sgmw.module_login.R2;
import com.uiho.sgmw.module_login.contract.MainContract;
import com.uiho.sgmw.module_login.presenter.MainPresenter;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.File;
import java.util.ArrayList;

import butterknife.BindView;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

import static com.uiho.sgmw.common.Constants.REQUEST_EXTERNAL;

/**
 * 作者：uiho_mac
 * 时间：2018/8/7
 * 描述：主页
 * 版本：1.0
 * 修订历史：
 */
@SuppressLint("Registered")
@CreatePresenterAnnotation(MainPresenter.class)
@Route(path = RouterPath.MAIN_ACTIVITY)
public class MainActivity extends BaseMvpActivity<MainContract.View, MainContract.Presenter>
        implements MainContract.View {
    @BindView(R2.id.wrap_view_pager)
    WrapContentHeightViewPager topViewpager;
    @BindView(R2.id.slide_layout)
    SlidingTabLayout slideLayout;
    @BindView(R2.id.view_pager)
    WrapContentHeightViewPager bottomViewPager;
    @BindView(R2.id.smart_layout)
    SmartRefreshLayout smartLayout;
    private long firstTime = 0L;
    private RxPermissions rxPermission;
    private String url;//app更新地址
    private AppUpdateProgressDialog dialog = null;
    private CompositeDisposable cd = new CompositeDisposable();
    String apkPath = Environment.getExternalStorageDirectory().getPath() + "/sgmw.apk";
    private Fragment palmTopFragment;
    private Fragment palmBottomFragment;
    private int currentPosition = 0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
        if (rxPermission == null) {
            rxPermission = new RxPermissions(this);
        }
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_main;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        palmTopFragment = (Fragment) ARouter.getInstance().build(RouterPath.PALM_TOP_FRAGMENT).navigation();
        palmBottomFragment = (Fragment) ARouter.getInstance().build(RouterPath.PALM_BOTTOM_FRAGMENT).navigation();
        ArrayList<Fragment> topFragments = new ArrayList<>();
        topFragments.add(palmTopFragment);
        setTopViewpager(topFragments);
        ArrayList<Fragment> fragments = new ArrayList<>();
        String[] titles = new String[]{getResources().getString(R.string.palm_baojun)};
        fragments.add(palmBottomFragment);
        setViewPage(titles, fragments);
        getMvpPresenter().checkedAppVersion();//检查应用版本
    }

    @Override
    protected void initEvent() {
        bottomViewPager.addOnPageChangeListener(new BaseLinkPageChangeListener(bottomViewPager, topViewpager) {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                bottomViewPager.resetHeight(position);//设置viewpager高度
                topViewpager.resetHeight(position);
                currentPosition = position;
            }
        });
        topViewpager.addOnPageChangeListener(new BaseLinkPageChangeListener(topViewpager, bottomViewPager) {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                slideLayout.onPageSelected(position);
            }

            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                super.onPageScrolled(position, positionOffset, positionOffsetPixels);
                slideLayout.onPageScrolled(position, positionOffset, positionOffsetPixels);
                bottomViewPager.resetHeight(position);
                topViewpager.resetHeight(position);
            }
        });

        smartLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                if (currentPosition == 0) {
                    EventBus.getDefault().post(new EventType(EventType.PALM));
                }
//                } else if (currentPosition == 1) {
//                    EventBus.getDefault().post(EventParameter.EXTRA_FROM_PALM);
//                } else if (currentPosition == 2) {
//                    EventBus.getDefault().post(EventParameter.EXTRA_FROM_PALM);
//                } else if (currentPosition == 3) {
//                    EventBus.getDefault().post(EventParameter.EXTRA_FROM_PALM);
//                }
            }
        });
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void stopRefresh(EventType eventType) {
        if (eventType.getType() == EventType.STOP_REFRESH) {
            smartLayout.finishRefresh();
        }
    }

    /**
     * 设置viewpager
     * 设置底部碎片
     *
     * @param titles
     * @param xFragment
     */
    public void setViewPage(String[] titles, final ArrayList<Fragment> xFragment) {
        //添加数据
        final TabFragmentPagerAdapter xFragmentPagerAdapter = new TabFragmentPagerAdapter(getSupportFragmentManager(), titles, xFragment);
        bottomViewPager.setAdapter(xFragmentPagerAdapter);
        bottomViewPager.setOffscreenPageLimit(xFragment.size());
        slideLayout.setViewPager(bottomViewPager);
    }


    /**
     * 设置顶部碎片
     *
     * @param topFragments
     */
    private void setTopViewpager(final ArrayList<Fragment> topFragments) {
        //添加数据
        final TabPagerAdapter tabPagerAdapter = new TabPagerAdapter(getSupportFragmentManager(), topFragments);
        topViewpager.setAdapter(tabPagerAdapter);
    }


    @Override
    public void subScribeEvent(String apkPath) {
        dialog = new AppUpdateProgressDialog(this);
        //正在下载时，不可关闭dialog
        dialog.setOnKeyListener(new DialogInterface.OnKeyListener() {
            @Override
            public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
                    return true;
                } else {
                    return false;
                }
            }
        });
        dialog.show();
        RxBus.getDefault().toObservable(DownloadModel.class)
                .subscribe(new Observer<DownloadModel>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        cd.add(d);
                    }

                    @Override
                    public void onNext(DownloadModel DownloadModel) {
                        int progress = (int) Math.round(DownloadModel.getBytesReaded() / (double) DownloadModel.getTotal() * 100);
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                Message msg = myHandler.obtainMessage();
                                msg.what = 100;
                                msg.obj = progress;
                                myHandler.sendMessage(msg);
                            }
                        }).start();
                        if (progress >= 100 && DownloadModel.getBytesReaded() == DownloadModel.getTotal()) {
                            dialog.dismiss();
                            File saveFile = new File(apkPath);
                            Intent install = new Intent(Intent.ACTION_VIEW);
                            if (Build.VERSION.SDK_INT >= 24) { //判读版本是否在7.0以上
                                Uri apkUri = FileProvider.getUriForFile(getApplicationContext(), BuildConfig.APPLICATION_ID + ".fileprovider", saveFile);//在AndroidManifest中的android:authorities值
                                install = new Intent(Intent.ACTION_VIEW);
                                install.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                install.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION); //添加这一句表示对目标应用临时授权该Uri所代表的文件
                                install.setDataAndType(apkUri, "application/vnd.android.package-archive");
                            } else {
                                install.setDataAndType(Uri.fromFile(saveFile), "application/vnd.android.package-archive");
                                install.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            }
                            startActivity(install);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        dialog.dismiss();
                    }

                    @Override
                    public void onComplete() {
                        dialog.dismiss();
                    }
                });
    }

    @Override
    public void showUpdateDialog(UpdateModel updateModel) {
        this.url = updateModel.getAndroid().getUrl();
        //0代表相等，1代表version1大于version2，-1代表version1小于version2
        String appVersion = AppUtils.getVersionName(mContext);
        int isForceUpdate = SystemUtils.compareVersion(updateModel.getAndroid().getMinVersion(), appVersion);//是否需要强制更新
        int isUpdate = SystemUtils.compareVersion(updateModel.getAndroid().getLatestVersion(), appVersion);//是否可以更新
        if (isForceUpdate == 1) {//强制更新
            DialogUtils.showUpdateDialog(mContext, updateModel.getAndroid().getContent(),
                    "更新提示", "升级", "下次再说", false, confirm -> {
                        if (confirm) {
                            startUpdate(url);
                        }
                    });
        } else if (isUpdate == 1) {
            DialogUtils.showUpdateDialog(mContext, updateModel.getAndroid().getContent(),
                    "更新提示", "升级", "下次再说", true, confirm -> {
                        if (confirm) {
                            startUpdate(url);
                        }
                    });
        }
    }

    //获取存储权限开始下载
    @SuppressLint("CheckResult")
    public void startUpdate(String url) {
        rxPermission
                .request(Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE)//多个权限用","隔开
                .observeOn(AndroidSchedulers.mainThread(), false, 100)
                .subscribe(aBoolean -> {
                    if (aBoolean) {
//                        if (showNegetive) {
//                            AppUpdateService.start(mContext, apkPath, url);
//                        } else {
                        getMvpPresenter().updateApp(url, apkPath, cd);
                        subScribeEvent(apkPath);
//                        }
                    } else {
                        DialogUtils.showPromptDialog(mContext, "检测到某些权限被禁止并设置了不再提醒,请您在设置-应用管理-权限管理中手动开启权限",
                                "温馨提示", "去开启", "取消", confirm -> {
                                    if (confirm) {
                                        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                                        Uri uri = Uri.fromParts("package", getPackageName(), null);
                                        intent.setData(uri);
                                        startActivityForResult(intent, REQUEST_EXTERNAL);
                                    }
                                });
                    }
                });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_EXTERNAL) {//权限设置返回查看
            startUpdate(url);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        currentPosition = topViewpager.getCurrentItem();
        topViewpager.requestLayout();
        bottomViewPager.requestLayout();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Override
    public void onBackPressed() {
        long secondTime = System.currentTimeMillis();
        if (secondTime - firstTime > 1500) {
            EventUtil.showToast(this, "再按一次退出");
            firstTime = secondTime;
        } else {
            ViewManager.getInstance().exitApp(mContext);
        }
    }

    @SuppressLint("HandlerLeak")
    Handler myHandler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 100:
                    int progress = (int) msg.obj;
                    dialog.setProgress(progress);
                    break;
            }
        }
    };
}
