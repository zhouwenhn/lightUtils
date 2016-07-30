package com.chowen.lightutils;

import com.chowen.cn.library.ioc.annotations.field.InjectContentView;
import com.chowen.lightutils.base.BaseFragment;

/**
 * Created by zhouwen on 16/7/23.
 */

@InjectContentView(value = R.layout.message_fragment)
public class MessageFragment extends BaseFragment {

//    @InjectChildView(value = R.id.btn, listener = View.OnClickListener.class)
//    private Button mBtn;


//    @Override
//    public void onClick(View v) {
//        Toast.makeText(getActivity(), "return activity!", Toast.LENGTH_LONG).show();
//        getActivity().onBackPressed();
//    }
}
