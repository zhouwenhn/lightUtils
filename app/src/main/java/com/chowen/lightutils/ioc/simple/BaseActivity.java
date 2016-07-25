package com.chowen.lightutils.ioc.simple;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

import com.chowen.lightutils.ioc.AnnotationProcessor;
import com.chowen.lightutils.ioc.ViewFinder;

import java.lang.reflect.InvocationTargetException;

/**
 * Created by zhouwen on 16/7/21.
 */
public class BaseActivity extends Activity{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ViewFinder viewFinder = new ViewFinder() {
            public View findViewById(int id) { return BaseActivity.this.findViewById(id); }
        };

        AnnotationProcessor.getInstance().invokeContentView(getClass(), this);
        try {
            AnnotationProcessor.getInstance().invokeChildViews(getClass(), this, viewFinder);
            AnnotationProcessor.getInstance().invokeString(getBaseContext(),getClass(),this,viewFinder);
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
