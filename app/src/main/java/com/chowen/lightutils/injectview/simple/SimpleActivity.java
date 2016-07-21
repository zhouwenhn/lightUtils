package com.chowen.lightutils.injectview.simple;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.chowen.lightutils.R;
import com.chowen.lightutils.injectview.annotation.InjectChildView;
import com.chowen.lightutils.injectview.annotation.InjectContentLayoutView;
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
