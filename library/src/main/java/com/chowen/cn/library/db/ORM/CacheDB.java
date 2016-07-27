package com.chowen.cn.library.db.ORM;


import com.chowen.cn.library.LibApplication;

/**
 * @author zhouwen
 * @version 0.1
 * @since 2016/07/26
 */
public class CacheDB {

    private static final String CACHE_DB = "cache_db";

    public static SQLiteUtility getFeedCacheSQLite() {
        if (SQLiteUtility.getInstance(CACHE_DB) == null)
            new SQLiteUtilityBuilder()
                    .configDBName(CACHE_DB)
                    .configVersion(1)
                    .build(LibApplication.getInstance().getApplicationContext());

        return SQLiteUtility.getInstance(CACHE_DB);
    }
}
