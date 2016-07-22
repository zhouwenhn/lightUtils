package com.chowen.lightutils;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.chowen.lightutils.ioc.annotations.field.InjectChildView;
import com.chowen.lightutils.ioc.annotations.field.InjectContentView;
import com.chowen.lightutils.ioc.annotations.field.InjectString;
import com.chowen.lightutils.ioc.simple.BaseActivity;
import com.chowen.lightutils.ioc.simple.IocSimpleActivity;
import com.chowen.lightutils.log.Logger;

@InjectContentView(value = R.layout.activity_main)
public class MainActivity extends BaseActivity implements View.OnClickListener{

    @InjectString(value = R.string.app_click, viewId = R.id.btn)
    @InjectChildView(value = R.id.btn, listener = View.OnClickListener.class)
    private Button mBtn;

    @InjectChildView(value = R.id.tv)
    private TextView mTv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Logger.d("chowen");
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(this, IocSimpleActivity.class);
        startActivity(intent);
    }
}
