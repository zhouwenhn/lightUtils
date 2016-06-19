package com.chowen.lightutils.cache.biz;

import android.support.annotation.NonNull;
import android.support.v4.util.LruCache;

import com.chowen.lightutils.cache.utils.CheckException;

import java.lang.ref.SoftReference;
import java.util.LinkedHashMap;

/**
 * @author zhouwen
 * @version 0.1
 * @since 2016/4/8
 */
public class MemoryCacheManager extends AbsCache<String, String> {

    private static MemoryCacheManager sMemoryCacheManager;

    private final static int MB = 1024 * 1024;

    private static int LRC_CACHE_SIZE = 6 * MB;

    private static int SOFT_REFERENCE_COUNT = 30;

    private LruCache<String, String> mLruCache;

    private LinkedHashMap<String, SoftReference<String>> mLinkedHashMap;

    public static MemoryCacheManager getInstance() {
        if (sMemoryCacheManager == null) {
            synchronized (MemoryCacheManager.class) {
                if (sMemoryCacheManager == null) {
                    sMemoryCacheManager = new MemoryCacheManager();
                }
            }
        }
        return sMemoryCacheManager;
    }

    public MemoryCacheManager() {
        mLruCache = new LruCache<String, String>(LRC_CACHE_SIZE) {
            @Override
            protected void entryRemoved(boolean evicted, String key, String oldValue, String newValue) {
                if (oldValue != null) {
                    mLinkedHashMap.put(key, new SoftReference<String>(oldValue));
                }
            }

            @Override
            protected int sizeOf(String key, String value) {
                if (value != null) {
                    return value.length();
                } else {
                    return 0;
                }
            }
        };

        mLinkedHashMap = new LinkedHashMap<String, SoftReference<String>>(SOFT_REFERENCE_COUNT, 0.75f, true) {
            @Override
            protected boolean removeEldestEntry(Entry<String, SoftReference<String>> eldest) {
                if (size() > SOFT_REFERENCE_COUNT) {
                    return true;
                }
                return super.removeEldestEntry(eldest);
            }
        };
    }

    /**
     * get memory's cache
     * @param key key
     * @return cache's data
     */
    @Override
    public String getCache(@NonNull String key) {
        CheckException.checkNPE(key);
        //先到强引用中取，没取到再去弱引用取
        String s;
        synchronized (MemoryCacheManager.class) {
            s = mLruCache.get(key);
            if (s != null) {
                mLruCache.remove(key);
                mLinkedHashMap.put(key, new SoftReference<>(s));
            } else {
                SoftReference<String> softReference = mLinkedHashMap.get(key);
                if (softReference != null){
                    s = softReference.get();
                    if (s != null) {
                        mLruCache.put(key, s);
                        mLinkedHashMap.remove(key);
                    }
                }
            }
        }
        return s;
    }

    /**
     * add data to memory's cache
     * @param key key
     * @param value value
     */
    @Override
    public void addCache(@NonNull String key, String value) {
        CheckException.checkNPE(key);
        CheckException.checkNPE(value);
        synchronized (mLruCache) {
            mLruCache.put(key, value);
        }
    }

    /**
     * remove cache
     * @param key key
     */
    @Override
    public void remove(@NonNull String key) {
        synchronized (mLruCache) {
            mLruCache.remove(key);
            mLinkedHashMap.remove(key);
        }
    }

    /**
     * update memory's cache
     * @param key key
     * @param value value
     */
    @Override
    public void update(String key, String value) {
        remove(key);
        addCache(key, value);
    }

    /**
     * remove all memory's cache
     */
    @Override
    public void removeAll() {
        mLruCache.evictAll();
        mLinkedHashMap.clear();
    }
}
