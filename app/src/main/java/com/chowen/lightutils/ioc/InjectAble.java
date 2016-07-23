package com.chowen.lightutils.ioc;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chowen.lightutils.ioc.annotations.field.InjectChildView;

import java.lang.reflect.InvocationTargetException;

/**
 * Deprecated: inject interface
 *
 * @author zhouwen
 * @version 0.1
 * @since 2016/07/21
 */
public interface InjectAble {

    /**
     * content view
     *
     * @param cls current activity's object
     * @param obj current object
     */
    void invokeContentView(Class cls, Object obj);

    /**
     * child views
     *
     * @param cls        current activity's object
     * @param obj        current object
     * @param viewFinder view's instance
     */
    void invokeChildViews(Class cls, Object obj, ViewFinder viewFinder) throws NoSuchMethodException, InstantiationException, IllegalAccessException, InvocationTargetException;

    /**
     * fragment's view
     * @param fragment fragment's instance
     * @param inflater inflater
     * @param container container
     * @param savedInstanceState savedInstanceState
     */
    View invokeContentView(Class cls, Fragment fragment, LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState);
    /**
     * view's listener
     *
     * @param clazz      current activity's object
     * @param annotation child's view annotation
     * @param view       current view's instance
     * @param listener   current object
     */
    void invokeViewListener(Class clazz, InjectChildView annotation, View view, Object listener) throws InvocationTargetException, IllegalAccessException, NoSuchMethodException, InstantiationException;
}
