package com.chowen.lightutils.ioc;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.chowen.lightutils.ioc.annotations.field.InjectChildView;
import com.chowen.lightutils.ioc.annotations.field.InjectContentView;
import com.chowen.lightutils.ioc.annotations.field.InjectString;
import com.chowen.lightutils.log.Logger;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * @author zhouwen
 * @version 0.1
 * @since 2016/07/21
 */
public class AnnotationProcessor implements InjectAble, InjectStrAble {

    private static AnnotationProcessor sInjectViewManager = null;

    private final static String CONTENT_VIEW = "setContentView";

    private final static String FIND_VIEW_BY_ID = "findViewById";

    private final static String SET_TEXT = "setText";

    private static Map<Class, String> sSetListenerMethodMap = new HashMap<Class, String>();

    static {
        sSetListenerMethodMap.put(TextWatcher.class, "addTextChangedListener");
    }

    public AnnotationProcessor() {

    }

    public static AnnotationProcessor getInstance() {
        if (sInjectViewManager == null) {
            synchronized (AnnotationProcessor.class) {
                if (sInjectViewManager == null) {
                    sInjectViewManager = new AnnotationProcessor();
                }
            }
        }
        return sInjectViewManager;
    }

    /**
     * content view
     *
     * @param cls current activity's object
     * @param obj current object
     */
    @Override
    public void invokeContentView(Class cls, Object obj) {
        InjectContentView contentAnnotation = (InjectContentView) cls.getAnnotation(InjectContentView.class);
        if (contentAnnotation != null) {
            int contentId = contentAnnotation.value();
            try {
                Method method = cls.getMethod(CONTENT_VIEW, int.class);
                method.setAccessible(true);
                method.invoke(obj, contentId);
            } catch (Exception e) {
                Logger.e("Exception>>invokeContentView>>" + obj.getClass().getSimpleName() + e);
            }
        }
    }

    @Override
    public View invokeContentView(Class cls, final Fragment fragment, LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        InjectContentView injectContentView = (InjectContentView) cls.getAnnotation(InjectContentView.class);
        if (injectContentView != null) {
            int layoutId = injectContentView.value();
            if (layoutId > 0)
                return inflater.inflate(layoutId, container, false);

//            ViewFinder viewFinder = new ViewFinder() {
//                public View findViewById(int id) { return fragment.getActivity().findViewById(id); }
//            };
//            try {
//                AnnotationProcessor.getInstance().invokeChildViews(getClass(), this, viewFinder);
//            } catch (NoSuchMethodException e) {
//                e.printStackTrace();
//            } catch (java.lang.InstantiationException e) {
//                e.printStackTrace();
//            } catch (IllegalAccessException e) {
//                e.printStackTrace();
//            } catch (InvocationTargetException e) {
//                e.printStackTrace();
//            }
        }
        return null;
    }


    @Override
    public void invokeString(@NonNull Context context, Class<?> cls, Object obj, ViewFinder viewFinder)
            throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Field[] fields = cls.getDeclaredFields();
        for (Field field : fields) {
            InjectString injectString = field.getAnnotation(InjectString.class);
            if (injectString != null) {
                int strId = injectString.value();
                int viewId = injectString.viewId();
                Method method = viewFinder.findViewById(viewId).getClass()
                        .getMethod(SET_TEXT, CharSequence.class);
                method.setAccessible(true);
                method.invoke(viewFinder.findViewById(viewId), context.getResources().getString(strId));
            }
        }
    }

    /**
     * 注入string
     *
     * @param context    context
     * @param viewFinder view finder
     * @param viewId     view's id
     * @param strId      string res's id
     * @throws NoSuchMethodException
     * @throws InvocationTargetException
     * @throws IllegalAccessException
     */
    @Override
    public void invokeString(@NonNull Context context, ViewFinder viewFinder, int viewId, int[] strId)
            throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        TextView textView = (TextView) viewFinder.findViewById(viewId);
        Method method = textView.getClass().getMethod(SET_TEXT, CharSequence.class);
        method.setAccessible(true);
        method.invoke(textView, context.getResources().getString(strId[0]));
    }

    /**
     * @deprecated
     * 注入string
     *
     * @param context    context
     * @param cls        current's class
     * @param obj        current's object
     * @param viewFinder view finder
     * @throws NoSuchMethodException
     * @throws InstantiationException
     * @throws IllegalAccessException
     * @throws InvocationTargetException
     */
    public void invokeChildViews(@NonNull Context context, Class cls, Object obj, ViewFinder viewFinder)
            throws NoSuchMethodException, InstantiationException, IllegalAccessException, InvocationTargetException {
        Field[] fields = cls.getDeclaredFields();
        for (Field field : fields) {
            InjectChildView viewAnnotation = field.getAnnotation(InjectChildView.class);
            if (viewAnnotation != null) {
                int viewId = viewAnnotation.value();
                try {
                    Method method = cls.getMethod(FIND_VIEW_BY_ID, int.class);
                    Object object = method.invoke(obj, viewId);
                    field.setAccessible(true);
                    field.set(obj, object);
                } catch (Exception e) {
                    Logger.e("Exception>>invokeChildViews>>" + obj.getClass().getSimpleName() + e);
                }

                if (viewAnnotation.listener().length > 0) {
                    invokeViewListener(cls, viewAnnotation, viewFinder.findViewById(viewId), obj);
                }

                if (viewAnnotation.stringId().length > 0) {
                    invokeString(context, viewFinder, viewAnnotation.value(), viewAnnotation.stringId());
                }
            }
        }
    }

    /**
     * child views
     *
     * @param cls        current activity's object
     * @param obj        current object
     * @param viewFinder view's instance
     */
    @Override
    public void invokeChildViews(Class cls, Object obj, ViewFinder viewFinder)
            throws NoSuchMethodException, InstantiationException, IllegalAccessException, InvocationTargetException {
        Field[] fields = cls.getDeclaredFields();
        for (Field field : fields) {
            InjectChildView viewAnnotation = field.getAnnotation(InjectChildView.class);
            if (viewAnnotation != null) {
                int viewId = viewAnnotation.value();
                try {
                    Method method = cls.getMethod(FIND_VIEW_BY_ID, int.class);
                    Object object = method.invoke(obj, viewId);
                    field.setAccessible(true);
                    field.set(obj, object);
                } catch (Exception e) {
                    Logger.e("Exception>>invokeChildViews>>" + obj.getClass().getSimpleName() + e);
                }
                if (viewAnnotation.listener().length > 0) {
                    invokeViewListener(cls, viewAnnotation, viewFinder.findViewById(viewId), obj);
                }
            }
        }
    }

    /**
     * view's listener
     *
     * @param clazz      current activity's object
     * @param annotation child's view annotation
     * @param view       current view's instance
     * @param listener   current object
     */
    @Override
    public void invokeViewListener(Class clazz, InjectChildView annotation, View view, Object listener)
            throws InvocationTargetException
            , IllegalAccessException, NoSuchMethodException, InstantiationException {
        Class[] listeners = annotation.listener();
        for (int j = 0; j < listeners.length; ++j) {
            Class listenerClass = listeners[j];

            if (!listenerClass.isAssignableFrom(clazz)) {
                //throw new NotImplementedInterfaceException(clazz.getName() + " does not implement " + listenerClass.getName());
            }

            String methodName = sSetListenerMethodMap.get(listenerClass);
            if (methodName == null) {
                methodName = listenerClass.getSimpleName();
                // for interfaces from android.support.v4.**, Class.getSimpleName() may return names that contain the dollar sign
                // I have no idea whether this is a bug, the following workaround fixes the problem
                int index = methodName.lastIndexOf('$');
                if (index != -1) {
                    methodName = methodName.substring(index + 1);
                }
                methodName = "set" + methodName;

                sSetListenerMethodMap.put(listenerClass, methodName);
            }

            try {
                Method method = view.getClass().getMethod(methodName, listenerClass);
                method.invoke(view, listener);
            } catch (NoSuchMethodException e) {
                throw new NoSuchMethodException("No such method: " + listenerClass.getSimpleName());
            }
        }
    }
}
