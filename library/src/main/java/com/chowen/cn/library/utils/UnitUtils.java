package com.chowen.cn.library.utils;

/**
 * Created by zhouwen on 16/8/14.
 */
public class UnitUtils {

    private UnitUtils() {
        throw new UnsupportedOperationException("is static, un supported operation exception");
    }

    /**
     * Byte与Byte的倍数
     */
    public static final long BYTE = 1;
    /**
     * KB与Byte的倍数
     */
    public static final long KB = 1024;
    /**
     * MB与Byte的倍数
     */
    public static final long MB = 1048576;
    /**
     * GB与Byte的倍数
     */
    public static final long GB = 1073741824;



    /**
     * 毫秒与毫秒的倍数
     */
    public static final int MSEC = 1;
    /**
     * 秒与毫秒的倍数
     */
    public static final int SEC = 1000;
    /**
     * 分与毫秒的倍数
     */
    public static final int MIN = 60000;
    /**
     * 时与毫秒的倍数
     */
    public static final int HOUR = 3600000;
    /**
     * 天与毫秒的倍数
     */
    public static final int DAY = 86400000;
}
