package com.chowen.lightutils;

import android.view.View;
import android.widget.Button;

import com.chowen.cn.library.pagekit.PageStackManager;
import com.chowen.cn.library.ioc.annotations.field.InjectChildView;
import com.chowen.cn.library.ioc.annotations.field.InjectContentView;
import com.chowen.cn.library.toast.ToastUtil;
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
                ToastUtil.showToast(getActivity(), "btn_orm");
                break;
            case R.id.btn_net:
                ToastUtil.showToast(getActivity(), "btn_net");
                break;
            case R.id.btn_download:
                ToastUtil.showToast(getActivity(), "btn_download");
                break;
            case R.id.btn_cache:
                ToastUtil.showToast(getActivity(), "btn_cache");
                break;
            case R.id.btn_log:
                ToastUtil.showToast(getActivity(), "btn_log");
                break;
            case R.id.btn_invoke:
                ToastUtil.showToast(getActivity(), "btn_invoke");
                break;
            case R.id.btn_utils:
                ToastUtil.showToast(getActivity(), "btn_utils");
                break;
            case R.id.btn_mes:
                ToastUtil.showToast(getActivity(), "btn_mes");
                break;
        }
    }
}
