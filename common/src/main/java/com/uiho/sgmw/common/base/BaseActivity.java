package com.uiho.sgmw.common.base;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

import com.alibaba.android.arouter.launcher.ARouter;
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity;
import com.uiho.sgmw.common.R;
import com.uiho.sgmw.common.eventbus.EventType;
import com.uiho.sgmw.common.utils.DialogUtils;
import com.uiho.sgmw.common.utils.StatusBarCompatUtils;
import com.uiho.sgmw.common.utils.SystemUtils;
import com.uiho.sgmw.common.utils.Utils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * 作者：uiho_mac
 * 时间：2018/8/6
 * 描述：
 * 版本：1.0
 * 修订历史：
 */
@SuppressLint("Registered")
public abstract class BaseActivity extends RxAppCompatActivity {
    protected Activity mContext;
    private Unbinder mUnBinder;
    protected boolean showBack = true;
    protected Toolbar toolbar;
    protected TextView textCancel;
    protected TextView textRight;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
        if (getLayout() != 0) {
            setContentView(getLayout());
        }
        getIntentData();
        mContext = this;
        mUnBinder = ButterKnife.bind(this);
        toolbar = findViewById(R.id.toolbar);
        if (toolbar != null) {
            toolbar.setTitle("");
            textRight = toolbar.findViewById(R.id.btn_right);
            textCancel = toolbar.findViewById(R.id.btn_left);
            if (textCancel != null)
                textCancel.setOnClickListener(v -> onBackListener());
            setSupportActionBar(toolbar);
            if (showBack) {
                final Drawable upArrow = ContextCompat.getDrawable(mContext, R.drawable.ic_back);
                upArrow.setColorFilter(ContextCompat.getColor(mContext, R.color.color_white), PorterDuff.Mode.SRC_ATOP);
                if (getSupportActionBar() != null) {
                    getSupportActionBar().setHomeAsUpIndicator(upArrow);
                    getSupportActionBar().setDisplayHomeAsUpEnabled(true);
                }
            }
        }
        initView(savedInstanceState);
        initEvent();
        initStatusBar();
        EventBus.getDefault().register(this);
    }

    /**
     * 初始化
     */
    protected void init() {
        ViewManager.getInstance().addActivity(this);
    }

    protected void initStatusBar() {
        StatusBarCompatUtils.setColor(mContext, ContextCompat.getColor(mContext, R.color.colorPrimaryDark));
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            //重写ToolBar返回按钮的行为，防止重新打开父Activity重走生命周期方法
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * 设置标题
     *
     * @param str
     */
    public void setTopTitle(String str) {
        TextView title = findViewById(R.id.bt_tv_title);
        title.setText(str);
    }

    protected void onBackListener() {
        finish();
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mUnBinder != null)
            mUnBinder.unbind();
        ViewManager.getInstance().finishActivity(this);
        EventBus.getDefault().unregister(this);
    }

    protected abstract int getLayout();

    protected abstract void initView(Bundle savedInstanceState);

    protected void initEvent() {
    }

    protected void getIntentData() {
    }

    @Subscribe
    public void onEventType(EventType eventType) {
        if (eventType.getType() == EventType.EVENT_NO_LOGIN) {
            DialogUtils.showTokenInvalidDialog(mContext, Utils.getString(R.string.No_Login_Exception),
                    "温馨提示", "去登录", "取消", confirm -> {
                        if (confirm) {
                            ViewManager.getInstance().finishAllActivity();
                            ARouter.getInstance().build(RouterPath.LOGIN_ACTIVITY).navigation();
                        }
                    });
        }
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                View view = getCurrentFocus();
                SystemUtils.hideKeyboard(ev, view, BaseActivity.this);//调用方法判断是否需要隐藏键盘
                break;
            default:
                break;
        }
        return super.dispatchTouchEvent(ev);
    }

}
