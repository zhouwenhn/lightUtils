package com.chowen.lightutils.fragments;

import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.chowen.cn.library.fragmentkit.StackManager;
import com.chowen.cn.library.ioc.annotations.field.InjectChildView;
import com.chowen.cn.library.ioc.annotations.field.InjectContentView;
import com.chowen.lightutils.R;

/**
 * Created by zhouwen on 16/7/23.
 */

@InjectContentView(value = R.layout.base_component_fragment)
public class BaseComponentFragment extends com.chowen.lightutils.base.BaseFragment
        implements View.OnClickListener {

    @InjectChildView(value = R.id.btn_orm, listener = View.OnClickListener.class)
    private Button mBtnOrm;

    @InjectChildView(value = R.id.btn_net, listener = View.OnClickListener.class)
    private Button mBtnNet;

    @InjectChildView(value = R.id.btn_download, listener = View.OnClickListener.class)
    private Button mBtnDownload;

    @InjectChildView(value = R.id.btn_cache, listener = View.OnClickListener.class)
    private Button mBtnCache;

    @InjectChildView(value = R.id.btn_log, listener = View.OnClickListener.class)
    private Button mBtnLog;

    @InjectChildView(value = R.id.btn_invoke, listener = View.OnClickListener.class)
    private Button mBtnInvoke;

    @InjectChildView(value = R.id.btn_utils, listener = View.OnClickListener.class)
    private Button mBtnUtils;

    @InjectChildView(value = R.id.btn_mes, listener = View.OnClickListener.class)
    private Button mBtnMes;


    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.btn_orm:
                Toast.makeText(getActivity(), "btn_orm", Toast.LENGTH_LONG).show();
                open(new Fragment2(), null, StackManager.STANDARD);
                break;
            case R.id.btn_net:
                Toast.makeText(getActivity(), "btn_net", Toast.LENGTH_LONG).show();
                break;
            case R.id.btn_download:
                Toast.makeText(getActivity(), "btn_download", Toast.LENGTH_LONG).show();
                break;
            case R.id.btn_cache:
                Toast.makeText(getActivity(), "btn_cache", Toast.LENGTH_LONG).show();
                break;
            case R.id.btn_log:
                Toast.makeText(getActivity(), "btn_log", Toast.LENGTH_LONG).show();
                break;
            case R.id.btn_invoke:
                Toast.makeText(getActivity(), "btn_invoke", Toast.LENGTH_LONG).show();
                break;
            case R.id.btn_utils:
                Toast.makeText(getActivity(), "btn_utils", Toast.LENGTH_LONG).show();
                break;
            case R.id.btn_mes:
                Toast.makeText(getActivity(), "btn_mes", Toast.LENGTH_LONG).show();
                break;
        }
    }
}
