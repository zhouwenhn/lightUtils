package com.chowen.lightutils.cache;

/**
 * @author zhouwen
 * @version 0.1
 * @since 2016/4/8
 */
/*byte[]/String
/JSONObject/JSONArray/
Serializable/Drawable/Bitmap*/
public interface ICacheAble<K, V> {

    void put(K key, V value);

    V getCache(K key);

    void remove(K key);

    void update(K key, V value);
}
