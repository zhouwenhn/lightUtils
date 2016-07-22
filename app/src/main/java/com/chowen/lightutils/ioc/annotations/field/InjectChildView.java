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
 * @since 2015/11/3
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface InjectChildView {

    /**注入view id*/
    @IdRes int value() default 0;

    /**注入 TextView Str*/
    @StringRes int[] stringId() default {};

    /**注入 listener*/
    Class[] listener() default {};
}
