package com.chowen.lightutils;

import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.chowen.cn.library.ioc.annotations.field.InjectChildView;
import com.chowen.cn.library.ioc.annotations.field.InjectContentView;

/**
 * Created by zhouwen on 16/7/23.
 */

@InjectContentView(value = R.layout.biz_component_fragment)
public class BizComponentFragment extends com.chowen.lightutils.base.BaseFragment implements View.OnClickListener{

    @InjectChildView(value = R.id.btn_login, listener = View.OnClickListener.class)
    private Button mBtn;


    @Override
    public void onClick(View v) {
        Toast.makeText(getActivity(), "return activity!", Toast.LENGTH_LONG).show();
    }
}
