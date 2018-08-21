package com.uiho.sgmw.common.mvp_senior.annotaions;

import com.uiho.sgmw.common.base.BasePresenter;

import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * 作者：uiho_mac
 * 时间：2018/8/6
 * 描述：
 * 一个创建presenter的注解
 *
 * @Inherited 这个注解表示  只能在类上使用并且是运行时
 * <p>
 * 版本：1.0
 * 修订历史：
 */
@Inherited
@Retention(RetentionPolicy.RUNTIME)
public @interface CreatePresenterAnnotation {

    Class<? extends BasePresenter> value();
}
