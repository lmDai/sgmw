package com.uiho.sgmw.common.base;

import android.support.annotation.Nullable;

import java.util.NoSuchElementException;

/**
 * 作者：uiho_mac
 * 时间：2018/4/16
 * 描述：返回结果
 * 版本：1.0
 * 修订历史：
 */

public class Optional<M> {
    private final M optional;//接收到的返回结果

    public Optional(@Nullable M optional) {
        this.optional = optional;
    }

    //判断返回结果是否为空
    public boolean isEmpty() {
        return this.optional == null;
    }

    public M get() {
        if (optional == null) {
            throw new NoSuchElementException("no value present");
        }
        return optional;
    }

    public M getIncludeNull() {
        return optional;
    }
}
