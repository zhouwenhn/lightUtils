package com.chowen.lightutils.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chowen.cn.library.pagekit.FragmentWrapper;
import com.chowen.cn.library.ioc.AnnotationProcessor;


/**
 * @author zw
 * @version 0.1
 * @since 16/7/23
 */
public class BaseFragment extends FragmentWrapper implements View.OnClickListener{
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = AnnotationProcessor.getInstance().invokeContentView(getClass(), this,
                inflater, container, savedInstanceState);
        return view;
    }

    @Override
    public void onClick(View v) {

    }
}
