package com.chowen.lightutils.ioc.simple;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.chowen.lightutils.R;
import com.chowen.lightutils.ioc.annotation.InjectChildView;
import com.chowen.lightutils.ioc.annotation.InjectContentLayoutView;
import com.chowen.lightutils.log.Logger;

@InjectContentLayoutView(value = R.layout.activity_main)
public class SimpleActivity extends BaseActivity implements View.OnClickListener{

    @InjectChildView(value = R.id.tv)
    private TextView mTv;

    @InjectChildView(value = R.id.btn, listener = View.OnClickListener.class)
    private Button mBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn:
                Logger.d("click btn!");
                break;
        }
    }
}
