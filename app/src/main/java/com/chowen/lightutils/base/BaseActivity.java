package com.chowen.lightutils.base;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.chowen.cn.library.ioc.AnnotationProcessor;
import com.chowen.cn.library.ioc.ViewFinder;

import java.lang.reflect.InvocationTargetException;

/**
 * Created by zhouwen on 16/7/21.
 */
public class BaseActivity extends AppCompatActivity {
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
