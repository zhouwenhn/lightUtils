package com.chowen.lightutils.exception;


import android.util.Log;

/**
 * @author zhouwen
 * @version 0.1
 * @since 2016/4/8
 */
public class CheckException {

    private final static String TAG = CheckException.class.getSimpleName();

    /**
     * check NPE
     * @param params params
     */
    public static void checkNPE(Object params) {
        if (params == null) {
            Log.e(TAG, "params can not null");
            throw new NullPointerException("params is not be null");
        }
    }
}
