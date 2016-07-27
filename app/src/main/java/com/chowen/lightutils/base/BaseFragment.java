package com.chowen.lightutils.base;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chowen.cn.library.ioc.AnnotationProcessor;


/**
 * Created by chowen on 16/7/23.
 */
public class BaseFragment extends Fragment{
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        return AnnotationProcessor.getInstance().invokeContentView(getClass(), this, inflater, container, savedInstanceState);
    }
}
