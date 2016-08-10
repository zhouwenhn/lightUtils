package com.chowen.lightutils;

import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.chowen.cn.library.ioc.annotations.field.InjectChildView;
import com.chowen.cn.library.ioc.annotations.field.InjectContentView;
import com.chowen.cn.library.toast.ToastUtil;
import com.chowen.lightutils.R;
import com.chowen.lightutils.base.BaseFragment;

/**
 * @author zhouwen
 * @version 0.1
 * @since 16/7/23
 */

@InjectContentView(value = R.layout.common_frame_fragment)
public class CommonFrameFragment extends BaseFragment {

    @InjectChildView(value = R.id.btn_a_f, listener = View.OnClickListener.class)
    private Button mBtnAF;

    @InjectChildView(value = R.id.btn_jie_gu, listener = View.OnClickListener.class)
    private Button mBtnJiaGu;

    @InjectChildView(value = R.id.btn_dl_skin, listener = View.OnClickListener.class)
    private Button mBtnDlSkin;

    @InjectChildView(value = R.id.btn_hotfix, listener = View.OnClickListener.class)
    private Button mBtnHotfix;

    @InjectChildView(value = R.id.btn_dl_load, listener = View.OnClickListener.class)
    private Button mBtnDlLoad;

    @InjectChildView(value = R.id.btn_init, listener = View.OnClickListener.class)
    private Button mBtnInit;


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_a_f:
                ToastUtil.showToast(getActivity(), "This is one Activity + more Fragments framework!");
                break;
            case R.id.btn_jie_gu:
                Toast.makeText(getActivity(), "btn_jie_gu!", Toast.LENGTH_LONG).show();
                break;
            case R.id.btn_dl_skin:
                Toast.makeText(getActivity(), "btn_dl_skin!", Toast.LENGTH_LONG).show();
                break;
            case R.id.btn_hotfix:
                Toast.makeText(getActivity(), "btn_hotfix!", Toast.LENGTH_LONG).show();
                break;
            case R.id.btn_dl_load:
                Toast.makeText(getActivity(), "btn_dl_load!", Toast.LENGTH_LONG).show();
                break;
            case R.id.btn_init:
                Toast.makeText(getActivity(), "btn_init!", Toast.LENGTH_LONG).show();
                break;
        }
    }
}
