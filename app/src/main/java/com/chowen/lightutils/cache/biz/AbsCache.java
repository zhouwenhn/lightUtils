package com.chowen.lightutils.cache.biz;

/**
 * @author zhouwen
 * @version 0.1
 * @since 2016/4/8
 */
public abstract class AbsCache<K, V> {

    public abstract V getCache(K k);

    public abstract void addCache(K k, V v);

    public abstract void remove(K k);

    public abstract void update(K k, V v);

    public abstract void removeAll();
}
