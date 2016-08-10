package com.chowen.cn.library.pagekit;

import android.os.Bundle;
import android.support.annotation.AnimRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.chowen.cn.library.R;

/**
 * Created by zhouwen on 16/8/3.
 */
public abstract class ActivityWrapper extends AppCompatActivity {

    public PageStackManager mManager;

    public IKeyDown mCallBack;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FrameLayout frameLayout = new FrameLayout(this);
        frameLayout.setLayoutParams(new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        frameLayout.setId(R.id.framLayoutId);
        setContentView(frameLayout);
        FragmentWrapper fragment = getRootFragment();
        mManager = new PageStackManager(this);
        mManager.setFragment(fragment);
        onCreateFirst(savedInstanceState);
    }

    /**
     * Set the bottom of the fragment
     *
     * @return fragment
     */
    protected abstract
    @NonNull
    FragmentWrapper getRootFragment();

    /**
     * Set page switch animation
     *
     * @param nextIn  The next page to enter the animation
     * @param nextOut The next page out of the animation
     * @param quitIn  The current page into the animation
     * @param quitOut Exit animation for the current page
     */
    public void setAnim(@AnimRes int nextIn, @AnimRes int nextOut, @AnimRes int quitIn, @AnimRes int quitOut) {
        mManager.setAnim(nextIn, nextOut, quitIn, quitOut);
    }

    /**
     * Rewriting onCreate method
     *
     * @param savedInstanceState savedInstanceState
     */
    public void onCreateFirst(Bundle savedInstanceState) {

    }


    @Override
    public final boolean onKeyDown(int keyCode, KeyEvent event) {
        switch (keyCode) {
            case KeyEvent.KEYCODE_BACK:
                mManager.onBackPressed();
                return true;
            default:
                if (mCallBack != null) {
                    return mCallBack.onKeyDown(keyCode, event);
                }
                break;
        }
        return super.onKeyDown(keyCode, event);
    }

    /**
     * Set button to click callback
     *
     * @param callBack callback
     */
    public void setKeyCallBack(IKeyDown callBack) {
        this.mCallBack = callBack;
    }


}
