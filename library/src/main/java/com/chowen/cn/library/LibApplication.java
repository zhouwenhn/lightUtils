package com.chowen.cn.library;

import android.app.Application;

/**
 * Created by zhouwen on 2016/6/19.
 */
public class LibApplication extends Application {

    private static LibApplication sLibApplication = new LibApplication();

    public static LibApplication getInstance(){
        return sLibApplication;
    }
}
