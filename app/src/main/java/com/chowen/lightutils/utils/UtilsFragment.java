package com.chowen.lightutils.utils;

import android.view.View;
import android.widget.Button;

import com.chowen.cn.library.ioc.annotations.field.InjectChildView;
import com.chowen.cn.library.ioc.annotations.field.InjectContentView;
import com.chowen.cn.library.toast.ToastUtil;
import com.chowen.cn.library.utils.DeviceUtils;
import com.chowen.cn.library.utils.PhoneUtils;
import com.chowen.cn.library.utils.ScreenUtils;
import com.chowen.cn.library.utils.ShellUtils;
import com.chowen.cn.library.utils.SizeUtils;
import com.chowen.lightutils.R;
import com.chowen.lightutils.base.BaseFragment;

/**
 * Created by zhouwen on 16/7/23.
 */

@InjectContentView(value = R.layout.utils_fragment)
public class UtilsFragment extends BaseFragment {

    @InjectChildView(value = R.id.getMacAddress, listener = View.OnClickListener.class)
    private Button mBtn;
    @InjectChildView(value = R.id.getModel, listener = View.OnClickListener.class)
    private Button mBtn1;

    @InjectChildView(value = R.id.getScreenWh, listener = View.OnClickListener.class)
    private Button mBtn2;

    @InjectChildView(value = R.id.getStatusBarHeight, listener = View.OnClickListener.class)
    private Button mBtn3;

    @InjectChildView(value = R.id.setLandscape, listener = View.OnClickListener.class)
    private Button mBtn4;

    @InjectChildView(value = R.id.isScreenLock, listener = View.OnClickListener.class)
    private Button mBtn5;

    @InjectChildView(value = R.id.isRoot, listener = View.OnClickListener.class)
    private Button mBtn6;

    @InjectChildView(value = R.id.getDeviceIMEI, listener = View.OnClickListener.class)
    private Button mBtn7;

    @InjectChildView(value = R.id.isPhone, listener = View.OnClickListener.class)
    private Button mBtn8;

    @InjectChildView(value = R.id.call_phone, listener = View.OnClickListener.class)
    private Button mBtn9;

    @InjectChildView(value = R.id.getPhoneStatus, listener = View.OnClickListener.class)
    private Button mBtn10;


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.getMacAddress:
                ToastUtil.showToast(getContext(), "Mac:"+DeviceUtils.getMacAddress());
                break;
            case R.id.getModel:
                ToastUtil.showToast(getContext(), "Model:"+DeviceUtils.getModel());
                break;
            case R.id.getScreenWh:
                ToastUtil.showToast(getContext(), "ScreenWidth*Height:"+ ScreenUtils.getScreenWidth(getContext())
                        +"*"+ScreenUtils.getScreenHeight(getContext()));
                break;
            case R.id.getStatusBarHeight:
                ToastUtil.showToast(getContext(), "StatusBarHeight:"+ScreenUtils.getStatusBarHeight(getContext()));
                break;
            case R.id.setLandscape:
                ScreenUtils.setLandscape(getActivity());
                break;
            case R.id.isScreenLock:
                ToastUtil.showToast(getContext(), "isScreenLock:"+ScreenUtils.isScreenLock(getContext()));
                break;
            case R.id.isRoot:
                ToastUtil.showToast(getContext(), "isRoot:"+ ShellUtils.isRoot());
                break;
            case R.id.getDeviceIMEI:
                ToastUtil.showToast(getContext(), "getDeviceIMEI:"+PhoneUtils.getDeviceIMEI(getContext()));
                break;
            case R.id.isPhone:
                ToastUtil.showToast(getContext(), "isPhone:"+PhoneUtils.isPhone(getContext()));
                break;
            case R.id.call_phone:
                ToastUtil.showToast(getContext(), "call");
                PhoneUtils.call(getContext(),"10086");
                break;
            case R.id.getPhoneStatus:
                ToastUtil.showToast(getContext(), "Model:"+PhoneUtils.getPhoneStatus(getContext()));
                break;

        }
    }
}
