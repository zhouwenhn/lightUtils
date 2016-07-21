package com.chowen.lightutils.ioc.simple;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

import com.chowen.lightutils.ioc.InjectViewManager;
import com.chowen.lightutils.ioc.ViewFinder;

import java.lang.reflect.InvocationTargetException;

/**
 * Created by zhouwen on 16/7/21.
 */
public class BaseActivity extends Activity{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        InjectViewManager.getInstance().injectContentView(getClass(), this);
        ViewFinder viewFinder = new ViewFinder() {
            public View findViewById(int id) { return BaseActivity.this.findViewById(id); }
        };
        try {
            InjectViewManager.getInstance().injectChildViews(getClass(), this, viewFinder);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }
}
