package com.chowen.lightutils.log;

import android.util.Log;

/**
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2016</p>
 *
 * @author zhouwen
 * @version 1.0
 * @since 2016/6/21 11:24
 */
public class Logger {

    private static boolean DEBUG = true;

    /**
     * log
     * @param type tag's type
     * @param msg message
     */
    private static void log(int type, String msg) {
        String tag = getStackTraceInfo();
        switch (type){
            case Log.DEBUG:
                Log.d(tag, msg);
                break;
            case Log.INFO:
                Log.i(tag, msg);
                break;
            case Log.VERBOSE:
                Log.v(tag, msg);
                break;
            case Log.ERROR:
                Log.e(tag, msg);
                break;
            case Log.WARN:
                Log.w(tag, msg);
                break;
        }
    }

    /**
     * debug
     * @param msg message
     * @param objects objects
     */
    public static void d(String msg, Object ... objects){
        if (objects == null) {
            log(Log.DEBUG, msg);
        } else {
            log(Log.DEBUG, String.format(msg, objects));
        }
    }

    /**
     * info
     * @param msg message
     * @param objects objects
     */
    public static void i(String msg, Object ... objects){
        if (objects == null) {
            log(Log.INFO, msg);
        } else {
            log(Log.INFO, String.format(msg, objects));
        }
    }

    /**
     * verbose
     * @param msg message
     * @param objects objects
     */
    public static void v(String msg, Object ... objects){
        if (objects == null) {
            log(Log.VERBOSE, msg);
        } else {
            log(Log.VERBOSE, String.format(msg, objects));
        }
    }

    /**
     * error
     * @param msg message
     * @param objects objects
     */
    public static void e(String msg, Object ... objects){
        if (objects == null) {
            log(Log.ERROR, msg);
        } else {
            log(Log.ERROR, String.format(msg, objects));
        }
    }

    /**
     * warn
     * @param msg message
     * @param objects objects
     */
    public static void w(String msg, Object ... objects){
        if (objects == null) {
            log(Log.WARN, msg);
        } else {
            log(Log.WARN, String.format(msg, objects));
        }
    }

    public static String getStackTraceInfo() {
        StackTraceElement stackTrace = Thread.currentThread().getStackTrace()[5];
        String className = stackTrace.getClassName();
        return className.substring(className.lastIndexOf('.') + 1) + ">>" + stackTrace.getMethodName() + "#" + stackTrace.getLineNumber();
    }
}
