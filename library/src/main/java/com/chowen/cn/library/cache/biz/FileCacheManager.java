package com.chowen.cn.library.cache.biz;

import android.os.Environment;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.util.Log;

import com.chowen.cn.library.cache.config.CacheConfig;
import com.chowen.cn.library.cache.utils.CacheHelper;
import com.chowen.cn.library.cache.utils.CheckException;
import com.chowen.cn.library.cache.utils.FileNameGenerator;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;

/**
 * @author zhouwen
 * @version 0.1
 * @since 2016/4/8
 */
public class FileCacheManager extends AbsCache<String, String> implements IUpdateCache {

    private static String TAG = FileCacheManager.class.getSimpleName();

    private static FileCacheManager sFileCacheManager;

    private final static int MB = 1024 * 1024;

    private final static int CACHE_SIZE = 20 * MB;

    private final static int SD_CARD_SIZE = 10 * MB;

    private static String sPathDir;

    public FileCacheManager() {
        File file = new File(sPathDir);
        if (!file.exists()) {
            file.mkdirs();
        }
    }

    public static FileCacheManager getInstance() {
        if (sFileCacheManager == null) {
            synchronized (FileCacheManager.class) {
                if (sFileCacheManager == null) {
                    sFileCacheManager = new FileCacheManager();
                }
            }
        }
        return sFileCacheManager;
    }

    /**
     * set cache dir
     * @param pathDir cache path dir
     */
    public static void setFileCacheDir(String pathDir){
        if (!TextUtils.isEmpty(pathDir)){
            sPathDir = pathDir;
        } else {
            sPathDir = CacheConfig.CACHE_PATH;
        }
    }

    /**
     * get data from file's cache
     * @param key key
     * @return cache's data
     */
    @Override
    public String getCache(@NonNull String key) {
        long startGetCache = System.currentTimeMillis();
        String path = sPathDir + File.separator + FileNameGenerator.generator(key);
        Log.d(TAG, "cache>>>>getCache path>>" + path);
        File file = new File(path);
        byte[] buffer = new byte[1024];
        BufferedInputStream bs = null;
        String value = null;
        if (file != null && file.exists()) {
            try {
                bs = new BufferedInputStream(new FileInputStream(file));
                int bytesRead = 0;
                while ((bytesRead = bs.read(buffer)) != -1) {
                    value = new String(buffer, 0, bytesRead);
                }
                if (value == null) {
                    file.delete();
                } else {
                    CacheHelper.updateFileCreateTime(path);
                    return value;
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                Log.d(TAG ,"cache>>>>get data from cache duration=" + (System.currentTimeMillis() - startGetCache) / 1000 + "s");
                if (bs != null) {
                    try {
                        bs.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return null;
    }

    /**
     * add data to file's cache
     * @param key key
     * @param value value
     */
    @Override
    public void addCache(String key, String value) {
        long startWriteTimeMillis = System.currentTimeMillis();
        File rootFile = new File(sPathDir);
        File file = new File(sPathDir + File.separator + FileNameGenerator.generator(key));
        Log.d(TAG, "cache>>>>addCache path>>" + file.toString());
        if (!Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            Log.e(FileCacheManager.class.getSimpleName(), "sdcard is not exist");
            return;
        }
        //大于sdcard space & file space，更新下存储空间
        if (CACHE_SIZE < CacheHelper.getCacheSize(rootFile.toString()) || SD_CARD_SIZE > CacheHelper.getSDCardFreeSpace()) {
            updateCacheSize(rootFile.toString());
        }
        if (!rootFile.exists()) {
            rootFile.mkdirs();
        }

        byte[] b = value.getBytes();
        BufferedOutputStream bos = null;
        try {
            FileOutputStream fo = new FileOutputStream(file);
            bos = new BufferedOutputStream(fo);
            bos.write(b);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            Log.d(TAG, "cache>>>>write data to cache duration=" + (System.currentTimeMillis() - startWriteTimeMillis) / 1000 + "s");
            if (bos != null) {
                try {
                    bos.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        }
    }

    /**
     * update cache size
     * @param path path
     * @return update success return true otherwise false
     */
    @Override
    public boolean updateCacheSize(@NonNull String path) {
        CheckException.checkNPE(path);
        File[] files = new File(path).listFiles();
        if (files == null) {
            return true;
        }
        if (!Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            return false;
        }
        int length = files.length;
        //升序排列文件
        Collections.sort(Arrays.asList(files), new FileLastModifySort());
        //清除50%最近没用的文件
        int removeCount = (int) (0.5 * length);
        for (int i = 0; i < removeCount; i++) {
            File file = files[i];
            if (file.exists()) {
                file.delete();
            }
        }
        return true;
    }

    /**
     * remove file's cache
     * @param key key
     */
    @Override
    public void remove(@NonNull String key) {
        File file = new File(sPathDir + File.separator + FileNameGenerator.generator(key));
        Log.d(TAG, "cache>>>>remove path>>" + file.toString());
        file.delete();
    }

    /**
     * update file's cache
     * @param key key
     * @param value value
     */
    @Override
    public void update(String key, String value) {
        remove(key);
        addCache(key, value);
    }

    /**
     * remove all file's cache
     */
    @Override
    public void removeAll() {
        File file = new File(sPathDir);
        file.delete();
    }

    /**
     * release cache size
     * @return success return true otherwise false
     */
    public boolean releaseCachesize() {
        File rootFile = new File(sPathDir);
        if (CACHE_SIZE < CacheHelper.getCacheSize(rootFile.toString()) || SD_CARD_SIZE > CacheHelper.getSDCardFreeSpace()) {
            return updateCacheSize(rootFile.toString());
        }
        return false;
    }

    private class FileLastModifySort implements Comparator<File> {
        @Override
        public int compare(File lhs, File rhs) {
            if (lhs.lastModified() > rhs.lastModified()) {
                return 1;
            } else if (lhs.lastModified() == rhs.lastModified()) {
                return 0;
            } else {
                return -1;
            }
        }
    }
}
