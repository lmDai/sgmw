package com.uiho.sgmw.module_login.ui.activity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.uiho.sgmw.common.Constants;
import com.uiho.sgmw.common.base.BaseMvpActivity;
import com.uiho.sgmw.common.base.RouterPath;
import com.uiho.sgmw.common.model.UserModel;
import com.uiho.sgmw.common.mvp_senior.annotaions.CreatePresenterAnnotation;
import com.uiho.sgmw.common.utils.AESUtil;
import com.uiho.sgmw.common.utils.EventUtil;
import com.uiho.sgmw.common.utils.GsonUtils;
import com.uiho.sgmw.common.utils.SPUtils;
import com.uiho.sgmw.common.utils.StringUtils;
import com.uiho.sgmw.common.utils.SystemUtils;
import com.uiho.sgmw.common.utils.Utils;
import com.uiho.sgmw.common.utils.ValidateUtils;
import com.uiho.sgmw.common.widget.SelectorFactory;
import com.uiho.sgmw.module_login.R;
import com.uiho.sgmw.module_login.R2;
import com.uiho.sgmw.module_login.contract.LoginContract;
import com.uiho.sgmw.module_login.presenter.LoginPresenter;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 作者：uiho_mac
 * 时间：2018/8/7
 * 描述：登录页
 * 版本：1.0
 * 修订历史：
 */
@SuppressLint("Registered")
@CreatePresenterAnnotation(LoginPresenter.class)
@Route(path = RouterPath.LOGIN_ACTIVITY)
public class LoginActivity extends BaseMvpActivity<LoginContract.View, LoginPresenter>
        implements LoginContract.View {
    @BindView(R2.id.edit_phone)
    EditText editPhone;
    @BindView(R2.id.edit_code)
    EditText editCode;
    @BindView(R2.id.checkbox)
    CheckBox checkbox;
    @BindView(R2.id.btn_login)
    Button btnLogin;

    @Override
    protected int getLayout() {
        return R.layout.activity_login;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        String phone = (String) SPUtils.getParam(mContext, Constants.PHONE, "");
        String password = (String) SPUtils.getParam(mContext, Constants.PASSWORD, "");
        editCode.setText(password);
        editPhone.setText(phone);
        btnLogin.setBackground(SelectorFactory.newShapeSelector()
                .setDefaultBgColor(ContextCompat.getColor(this, R.color.color_bule))
                .setPressedBgColor(ContextCompat.getColor(this, R.color.color_85_bule))
                .setCornerRadius(10)
                .create());
    }

    @Override
    protected void initEvent() {
        checkbox.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                editCode.setTransformationMethod(PasswordTransformationMethod.getInstance());
            } else {
                editCode.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
            }
        });
    }

    @Override
    public void loginResult(String result) {
        if (!TextUtils.isEmpty(result)) {
            UserModel userModel = GsonUtils.GsonToBean(AESUtil.decrypt(result, Constants.LOGIN_PWD), UserModel.class);
            String userStr = GsonUtils.GsonString(userModel);
            SPUtils.setParam(mContext, Constants.USER, userStr);
            SPUtils.setParam(mContext, Constants.PHONE, userModel.getPhoneNum());
            SPUtils.setParam(mContext, Constants.IS_LOGIN, true);
            SPUtils.setParam(mContext, Constants.TOKEN, userModel.getToken());
            SPUtils.setParam(mContext, Constants.PASSWORD, editCode.getText().toString());
            ARouter.getInstance().build(RouterPath.MAIN_ACTIVITY).withTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right).navigation();
        } else {
            EventUtil.showToast(this, Utils.getString(R.string.login_failed));
        }
    }

    @OnClick(R2.id.btn_login)
    public void onViewClicked() {
        if (!ValidateUtils.isChinaPhoneLegal(editPhone.getText().toString())) {
            EventUtil.showToast(mContext, Utils.getString(R.string.error_phone));
            return;
        }
        if (StringUtils.isEmpty(editCode.getText().toString())) {
            EventUtil.showToast(mContext, Utils.getString(R.string.empty_password));
            return;
        }
        getMvpPresenter().login(editPhone.getText().toString(), SystemUtils.MdPassword(editCode.getText().toString()));
    }
}
