package com.chowen.lightutils;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chowen.cn.library.fragmentkit.FragmentWrapper;
import com.chowen.cn.library.fragmentkit.StackManager;
import com.chowen.lightutils.R;
import com.chowen.lightutils.fragments.Fragment2;
import com.chowen.lightutils.fragments.Fragment3;


/**
 * @author zhouwen
 * @version 0.1
 * @since 16/7/23
 */
public class BaseComponentFragment extends FragmentWrapper implements View.OnClickListener {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View inflate = inflater.inflate(R.layout.base_component_fragment, null);
        inflate.findViewById(R.id.btn_orm).setOnClickListener(this);
        inflate.findViewById(R.id.btn_net).setOnClickListener(this);
        inflate.findViewById(R.id.btn_download).setOnClickListener(this);
        return inflate;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_orm:
                open(new Fragment2(), null, StackManager.STANDARD);
                break;
            case R.id.btn_net:
                open(new Fragment3());
                break;
            case R.id.btn_download:
                open(new Fragment3());
                break;
        }
    }
}
