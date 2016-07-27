package com.chowen.lightutils;

import android.app.Application;

/**
 * Created by zhouwen on 2016/6/19.
 */
public class MyApplication extends Application {

    private static MyApplication sLightUtilsApplication = new MyApplication();

    public static MyApplication getInstance(){
        return sLightUtilsApplication;
    }
}
