package com.chowen.cn.library.db.ORM.annotation;

/**
 * @author zhouwen
 * @version 0.1
 * @since 2016/07/26
 */
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface PrimaryKey {

    public String column() default "primary_key";

}
