package com.chowen.cn.library.cache;


import android.util.Log;

import com.chowen.cn.library.cache.biz.FileCacheManager;
import com.chowen.cn.library.cache.biz.MemoryCacheManager;

/**
 * @author zhouwen
 * @version 0.1
 * @since 2015/11/15
 */
public class LoadDataManager implements ICacheAble<String, String> {

    private static String TAG = LoadDataManager.class.getSimpleName();

    private static LoadDataManager sLoadCacheData;

    public LoadDataManager() {}

    public static LoadDataManager getInstance() {
        if (sLoadCacheData == null) {
            synchronized (LoadDataManager.class) {
                if (sLoadCacheData == null) {
                    sLoadCacheData = new LoadDataManager();
                }
            }
        }
        return sLoadCacheData;
    }

    /**
     * load from network
     * @param key key
     * @return from network data
     */
    private String loadFromNetwork(String key) {
        //from network
        return null;
    }

    /**
     * set cache dir
     * @param pathDir path dir
     */
    public void setFileCacheDir(String pathDir){
        FileCacheManager.setFileCacheDir(pathDir);
    }

    /**
     * put cache to file
     * @param key key
     * @param value value
     */
    @Override
    public void put(String key, String value) {
        MemoryCacheManager.getInstance().addCache(key, value);
        FileCacheManager.getInstance().addCache(key, value);
    }

    /**
     * get data from cache
     * @param key
     * @return
     */
    @Override
    public String getCache(String key) {
        String value = MemoryCacheManager.getInstance().getCache(key);
        if (value == null) {
            value = FileCacheManager.getInstance().getCache(key);
            if (value != null) {
                Log.d(TAG, "cache>>>>getCache from file cache>>");
                MemoryCacheManager.getInstance().addCache(key, value);
            } else {
                value = loadFromNetwork(key);
                if (value != null) {
                    MemoryCacheManager.getInstance().addCache(key, value);
                    FileCacheManager.getInstance().addCache(key, value);
                }
            }
        } else {
            Log.d(TAG, "cache>>>>getCache from memory cache>>");
        }
        return value;
    }

    /**
     * remove cache
     * @param key key
     */
    @Override
    public void remove(String key) {
        MemoryCacheManager.getInstance().remove(key);
        FileCacheManager.getInstance().remove(key);
    }

    /**
     * update cache
     * @param key key
     * @param value value
     */
    @Override
    public void update(String key, String value) {
        MemoryCacheManager.getInstance().update(key, value);
        FileCacheManager.getInstance().update(key, value);
    }
}
