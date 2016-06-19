package com.chowen.lightutils;

import android.app.Application;

/**
 * Created by zhouwen on 2016/6/19.
 */
public class LightUtilsApplication extends Application {

    private static LightUtilsApplication sLightUtilsApplication = new LightUtilsApplication();

    public static LightUtilsApplication getInstance(){
        return sLightUtilsApplication;
    }
}
