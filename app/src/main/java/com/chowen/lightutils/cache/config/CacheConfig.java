package com.chowen.lightutils.cache.config;


import com.chowen.lightutils.LightUtilsApplication;

import java.io.File;

/**
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2016</p>
 *
 * @author zhouwen
 * @version 1.0
 * @since 2016/4/8 16:30
 */
public interface CacheConfig {

    String FILE_NAME = "cache_data";

    String CACHE_PATH = LightUtilsApplication.getInstance().getFilesDir().getAbsolutePath() + File.separator + FILE_NAME;

    String CACHE_KEY = "cache";
}
