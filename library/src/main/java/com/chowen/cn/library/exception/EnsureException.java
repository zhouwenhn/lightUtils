package com.chowen.cn.library.exception;


import android.util.Log;

/**
 * @author zhouwen
 * @version 0.1
 * @since 2016/4/8
 */
public class EnsureException extends BaseException{

    private final static String TAG = EnsureException.class.getSimpleName();

    /**
     * check NPE
     * @param params params
     */
    public static void ensureNPE(Object params) {
        if (params == null) {
            Log.e(TAG, "params can not null");
            throw new NullPointerException("params is not be null");
        }
    }
}
