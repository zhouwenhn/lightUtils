package com.chowen.lightutils.ioc.annotations.field;

import android.support.annotation.IdRes;
import android.support.annotation.StringRes;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author zhouwen
 * @version 0.1
 * @since 2016/7/22
 */

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface InjectString {
    /**
     * invoke string
     */
    @StringRes int value() default 0;

    /**
     * view's id
     */
    @IdRes int viewId() default 0;
}
