package com.chowen.lightutils.cache.utils;

import android.annotation.TargetApi;
import android.os.Build;
import android.os.Environment;
import android.os.StatFs;

import java.io.File;

/**
 * <p>Description: cache helper</p>
 * <p>Copyright: Copyright (c) 2016</p>
 *
 * @author zhouwen
 * @version 1.0
 * @since 2016/4/11 9:31
 */
public class CacheHelper {

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR2)
    public static int getSDCardFreeSpace() {
        StatFs statFs = new StatFs(Environment.getExternalStorageDirectory().getPath());
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {
            return (int) statFs.getFreeBytes();
        } else {
            return statFs.getAvailableBlocks() * statFs.getBlockSize();
        }
    }

    public static int getCacheSize(String path) {
        File[] files = new File(path).listFiles();
        if (files == null) {
            return 0;
        }
        if (!Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            return 0;
        }
        int length = files.length;
        int fileSize = 0;
        for (int i = 0; i < length; i++) {
            fileSize += files[i].length();
        }
        return fileSize;
    }

    public static void updateFileCreateTime(String path) {
        File file = new File(path);
        long newModifyTime = System.currentTimeMillis();
        file.setLastModified(newModifyTime);
    }
}
