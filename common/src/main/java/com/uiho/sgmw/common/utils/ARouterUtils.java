package com.uiho.sgmw.common.utils;

import com.alibaba.android.arouter.launcher.ARouter;

/**
 * 作者：uiho_mac
 * 时间：2018/8/21
 * 描述：路由工具类
 * 版本：1.0
 * 修订历史：
 */
public class ARouterUtils {
    public static void goPage(String path) {
        ARouter.getInstance().build(path).withTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right).navigation();
    }
}
