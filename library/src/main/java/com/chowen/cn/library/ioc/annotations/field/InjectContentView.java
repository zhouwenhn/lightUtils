package com.chowen.cn.library.ioc.annotations.field;

import android.support.annotation.LayoutRes;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author zhouwen
 * @version 0.1
 * @since 2015/11/3
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.CLASS)
public @interface InjectContentView {
    /**inject content view*/
    @LayoutRes int value();
}
