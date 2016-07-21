package com.chowen.lightutils.injectview.annotation;

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
    int value() default 0;
    Class[] listener() default {};
}
