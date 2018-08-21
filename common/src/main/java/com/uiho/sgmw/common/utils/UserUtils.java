package com.uiho.sgmw.common.utils;

import android.content.Context;
import android.text.TextUtils;

import com.uiho.sgmw.common.Constants;
import com.uiho.sgmw.common.model.UserModel;


/**
 * 作者：uiho_mac
 * 时间：2018/5/11
 * 描述：
 * 版本：1.0
 * 修订历史：
 */

public class UserUtils {
    public static String getVins(Context mContext) {
        String user = (String) SPUtils.getParam(mContext, Constants.USER, "");
        if (TextUtils.isEmpty(user)) {
            return "";
        } else {
            UserModel userModel = GsonUtils.GsonToBean(user, UserModel.class);
            if (userModel != null) {
                return userModel.getVins();
            }
        }
        return "";
    }

    public static String getHead(Context mContext) {
        String user = (String) SPUtils.getParam(mContext, Constants.USER, "");
        if (TextUtils.isEmpty(user)) {
            return "";
        } else {
            UserModel userModel = GsonUtils.GsonToBean(user, UserModel.class);
            if (userModel != null) {
                return userModel.getAvatar();
            }
        }
        return "";
    }

    public static String getName(Context mContext) {
        String user = (String) SPUtils.getParam(mContext, Constants.USER, "");
        if (TextUtils.isEmpty(user)) {
            return "";
        } else {
            UserModel userModel = GsonUtils.GsonToBean(user, UserModel.class);
            if (userModel != null) {
                return userModel.getXm();
            }
        }
        return "";
    }

    public static UserModel getUser(Context mContext) {
        String user = (String) SPUtils.getParam(mContext, Constants.USER, "");
        if (TextUtils.isEmpty(user)) {
            return null;
        } else {
            UserModel userModel = GsonUtils.GsonToBean(user, UserModel.class);
            if (userModel != null) {
                return userModel;
            }
        }
        return null;
    }

    ;

}
