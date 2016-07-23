package com.chowen.lightutils.ioc.simple;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.chowen.lightutils.R;
import com.chowen.lightutils.ioc.annotations.field.InjectChildView;
import com.chowen.lightutils.ioc.annotations.field.InjectContentView;

/**
 * Created by zhouwen on 16/7/23.
 */

@InjectContentView(value = R.layout.ioc_simple_fragment_main)
public class IocSimpleFragment extends BaseFragment {

//    @InjectChildView(value = R.id.btn, listener = View.OnClickListener.class)
//    private Button mBtn;


//    @Override
//    public void onClick(View v) {
//        Toast.makeText(getActivity(), "return activity!", Toast.LENGTH_LONG).show();
//        getActivity().onBackPressed();
//    }
}
