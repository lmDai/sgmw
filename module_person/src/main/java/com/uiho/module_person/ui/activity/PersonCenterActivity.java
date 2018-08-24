package com.uiho.module_person.ui.activity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.uiho.module_person.R;
import com.uiho.module_person.R2;
import com.uiho.module_person.contract.PersonCenterContract;
import com.uiho.module_person.presenter.PersonCenterPresenter;
import com.uiho.sgmw.common.Constants;
import com.uiho.sgmw.common.base.BaseMvpActivity;
import com.uiho.sgmw.common.base.RouterPath;
import com.uiho.sgmw.common.base.ViewManager;
import com.uiho.sgmw.common.model.UserModel;
import com.uiho.sgmw.common.mvp_senior.annotaions.CreatePresenterAnnotation;
import com.uiho.sgmw.common.utils.ARouterUtils;
import com.uiho.sgmw.common.utils.SPUtils;
import com.uiho.sgmw.common.utils.UserUtils;
import com.uiho.sgmw.common.utils.glide.ImageLoadUtils;
import com.uiho.sgmw.common.widget.SelectorFactory;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 作者：uiho_mac
 * 时间：2018/8/24
 * 描述：
 * 版本：1.0
 * 修订历史：
 */
@SuppressLint("Registered")
@CreatePresenterAnnotation(PersonCenterPresenter.class)
@Route(path = RouterPath.PERSON_CENTER_MAIN)
public class PersonCenterActivity extends BaseMvpActivity<PersonCenterContract.View, PersonCenterPresenter> implements PersonCenterContract.View {
    @BindView(R2.id.smart_layout)
    SmartRefreshLayout smartRefreshLayout;
    @BindView(R2.id.img_head)
    ImageView imgHead;
    @BindView(R2.id.ib_back)
    ImageButton ibBack;
    @BindView(R2.id.txt_nick_name)
    TextView txtNickName;
    @BindView(R2.id.txt_balance)
    TextView txtBalance;
    @BindView(R2.id.layout_count)
    LinearLayout layoutCount;
    @BindView(R2.id.txt_integral)
    TextView txtIntegral;
    @BindView(R2.id.layout_intergral)
    LinearLayout layoutIntergral;
    @BindView(R2.id.layout_message)
    LinearLayout layoutMessage;
    @BindView(R2.id.layout_setting)
    LinearLayout layoutSetting;
    @BindView(R2.id.layout_logout)
    LinearLayout layoutLogout;
    @BindView(R2.id.layout_basic)
    LinearLayout layoutBasic;

    @Override
    protected int getLayout() {
        return R.layout.activity_person_center;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        UserModel userModel = UserUtils.getUser(mContext);
        if (userModel != null) {
            ImageLoadUtils.displayRoundError(mContext, imgHead, userModel.getAvatar(), R.drawable.user);
            txtNickName.setText(userModel.getXm());
        }
        ibBack.setBackground(SelectorFactory.newShapeSelector()
                .setDefaultBgColor(ContextCompat.getColor(this, R.color.color_trans))
                .setPressedBgColor(ContextCompat.getColor(this, R.color.color_white_trans))
                .setCornerRadius(10)
                .create());
        //基本信息
        layoutBasic.setBackground(SelectorFactory.newShapeSelector()
                .setDefaultBgColor(ContextCompat.getColor(this, R.color.color_white))
                .setPressedBgColor(ContextCompat.getColor(this, R.color.color_85_white))
                .create());
        //我的余额
        layoutCount.setBackground(SelectorFactory.newShapeSelector()
                .setDefaultBgColor(ContextCompat.getColor(this, R.color.color_white))
                .setPressedBgColor(ContextCompat.getColor(this, R.color.color_85_white))
                .create());
        //积分
        layoutIntergral.setBackground(SelectorFactory.newShapeSelector()
                .setDefaultBgColor(ContextCompat.getColor(this, R.color.color_white))
                .setPressedBgColor(ContextCompat.getColor(this, R.color.color_85_white))
                .create());
        //消息
        layoutMessage.setBackground(SelectorFactory.newShapeSelector()
                .setDefaultBgColor(ContextCompat.getColor(this, R.color.color_white))
                .setPressedBgColor(ContextCompat.getColor(this, R.color.color_85_white))
                .create());
        //设置
        layoutSetting.setBackground(SelectorFactory.newShapeSelector()
                .setDefaultBgColor(ContextCompat.getColor(this, R.color.color_white))
                .setPressedBgColor(ContextCompat.getColor(this, R.color.color_85_white))
                .create());

        //注销
        layoutLogout.setBackground(SelectorFactory.newShapeSelector()
                .setDefaultBgColor(ContextCompat.getColor(this, R.color.color_white))
                .setPressedBgColor(ContextCompat.getColor(this, R.color.color_85_white))
                .create());


    }

    @OnClick({R2.id.ib_back, R2.id.layout_basic, R2.id.layout_count, R2.id.layout_intergral, R2.id.layout_message, R2.id.layout_setting, R2.id.layout_logout})
    public void onViewClicked(View view) {
        int i = view.getId();
        if (i == R.id.layout_basic) {
        } else if (i == R.id.layout_count) {
        } else if (i == R.id.layout_intergral) {
        } else if (i == R.id.layout_message) {
        } else if (i == R.id.layout_setting) {
        } else if (i == R.id.layout_logout) {
            SPUtils.clear(mContext, Constants.IS_LOGIN);
            SPUtils.clear(mContext, Constants.TOKEN);
            ViewManager.getInstance().finishAllActivity();
            ARouterUtils.goPage(RouterPath.LOGIN_ACTIVITY);
        } else if (i == R.id.ib_back) {
            finish();
        }
    }
}
