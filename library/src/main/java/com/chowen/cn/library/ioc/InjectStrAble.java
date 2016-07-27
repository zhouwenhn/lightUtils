package com.chowen.cn.library.ioc;

import android.content.Context;
import android.support.annotation.NonNull;

import java.lang.reflect.InvocationTargetException;

/**
 * Deprecated: inject interface
 *
 * @author zhouwen
 * @version 0.1
 * @since 2016/07/22
 */

public interface InjectStrAble {

    void invokeString(@NonNull Context context, ViewFinder viewFinder, int viewId, int[] strId)
            throws NoSuchMethodException, InvocationTargetException, IllegalAccessException;

    void invokeString(@NonNull Context context, Class<?> cls, Object obj, ViewFinder viewFinder)
            throws NoSuchMethodException, InvocationTargetException, IllegalAccessException;
}
