package com.chowen.lightutils.ioc;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.chowen.cn.library.ioc.annotations.field.InjectChildView;
import com.chowen.cn.library.ioc.annotations.field.InjectContentView;
import com.chowen.cn.library.ioc.annotations.field.InjectString;
import com.chowen.cn.library.ioc.annotations.method.InjectMeth;
import com.chowen.lightutils.R;
import com.chowen.lightutils.base.BaseActivity;

@InjectContentView(value = R.layout.ioc_simple_activity_main)
public class IocSimpleActivity extends BaseActivity implements View.OnClickListener{

    @InjectString(value = R.string.app_name, viewId = R.id.tv)
    @InjectChildView(value = R.id.tv)
    private TextView mTv;

    @InjectString(value = R.string.app_name, viewId = R.id.btn)
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
                Toast.makeText(this, "click btn!", Toast.LENGTH_SHORT).show();
                break;
        }
    }

    @InjectMeth private void method(){

    }

}
