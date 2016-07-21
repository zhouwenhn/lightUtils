package com.chowen.lightutils.ioc;

import android.text.TextWatcher;
import android.view.View;

import com.chowen.lightutils.ioc.annotation.InjectChildView;
import com.chowen.lightutils.ioc.annotation.InjectContentLayoutView;
import com.chowen.lightutils.log.Logger;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * @author zhouwen
 * @version 0.1
 * @since 2015/11/3
 */
public class InjectViewManager {

    private static InjectViewManager sInjectViewManager = null;

    private final static String CONTENT_VIEW = "setContentView";

    private final static String FIND_VIEW_BY_ID = "findViewById";

    private static Map<Class, String> sSetListenerMethodMap = new HashMap<Class, String>();

    static {
        sSetListenerMethodMap.put(TextWatcher.class, "addTextChangedListener");
    }

    public InjectViewManager() {

    }

    public static InjectViewManager getInstance() {
        if (sInjectViewManager == null) {
            synchronized (InjectViewManager.class) {
                if (sInjectViewManager == null) {
                    sInjectViewManager = new InjectViewManager();
                }
            }
        }
        return sInjectViewManager;
    }

    public void injectContentView(Class cls, Object obj) {
        InjectContentLayoutView contentAnnotation = (InjectContentLayoutView) cls.getAnnotation(InjectContentLayoutView.class);
        if (contentAnnotation != null) {
            int contentId = contentAnnotation.value();
            try {
                Method method = cls.getMethod(CONTENT_VIEW, int.class);
                method.setAccessible(true);
                method.invoke(obj, contentId);
            } catch (Exception e) {
                Logger.e("Exception>>injectContentView>>" + obj.getClass().getSimpleName() + e);
            }
        }
    }

    public void injectChildViews(Class cls, Object obj, ViewFinder viewFinder)
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
                    Logger.e("Exception>>injectChildViews>>" + obj.getClass().getSimpleName() + e);
                }
                if (viewAnnotation.listener().length > 0) {
                    injectViewListener(cls, viewAnnotation, viewFinder.findViewById(viewId), obj);
                }
            }
        }
    }

    private void injectViewListener(Class clazz, InjectChildView annotation, View view, Object listener) throws InvocationTargetException
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
