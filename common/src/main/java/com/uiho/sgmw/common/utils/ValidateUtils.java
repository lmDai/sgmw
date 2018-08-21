package com.uiho.sgmw.common.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

/**
 * 作者：uiho_mac
 * 时间：2018/5/7
 * 描述：验证方法 输入控制
 * 版本：1.0
 * 修订历史：
 */

public class ValidateUtils {
    /**
     * 验证手机是否合法
     *
     * @param str
     * @return
     * @throws PatternSyntaxException
     */
    public static boolean isChinaPhoneLegal(String str)
            throws PatternSyntaxException {
        String regExp = "^((13[0-9])|(15[^4])|(166)|(17[0-8])|(18[0-9])|(19[8-9])|(147,145))\\d{8}$";
        Pattern p = Pattern.compile(regExp);
        Matcher m = p.matcher(str);
        return m.matches();
    }
}
