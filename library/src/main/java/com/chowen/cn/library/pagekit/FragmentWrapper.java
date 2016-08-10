package com.chowen.cn.library.pagekit;

import android.os.Bundle;
import android.support.annotation.AnimRes;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;

/**
 * Created by zhouwen on 16/8/3.
 */
public abstract class FragmentWrapper extends Fragment implements INewIntentAble {

    /**
     * open a new Fragment
     *
     * @param fragment fragment
     */
    public void open(@NonNull FragmentWrapper fragment) {
        getRoot().mManager.addFragment(this, fragment, null);
    }

    /**
     * open a new Fragment,And transfer parameters with bundle
     * <p>
     * Like this
     * <pre>   {@code
     * Bundle bundle=new Bundle();
     * bundle.put(key,value);
     * In the new fragment, you can accept parameters like this
     * Bundle bundle = fragment.getArguments();
     * bundle.get(key);
     * }</pre>
     *
     * @param fragment fragment
     * @param bundle   bundle
     */
    public void open(@NonNull FragmentWrapper fragment, Bundle bundle) {
        getRoot().mManager.addFragment(this, fragment, bundle);
    }

    /**
     * open a new Fragment,And transfer parameters with bundle andr set StackMode
     * Like this
     * <pre>   {@code
     * Bundle bundle=new Bundle();
     * bundle.put(key,value);
     *
     * }</pre>
     * In the new fragment, you can accept parameters like this
     * <pre>   {@code
     * Bundle bundle = fragment.getArguments();
     * bundle.get(key);<br/>
     * }</pre>
     *
     * @param fragment  fragment
     * @param bundle    bundle
     * @param stackMode stackMode,{@link PageStackManager#STANDARD} or more
     */

    public void open(@NonNull FragmentWrapper fragment, Bundle bundle, int stackMode) {
        getRoot().mManager.addFragment(this, fragment, bundle, stackMode);
    }

    /**
     * Jump to the specified fragment and do not hide the current page.
     *
     * @param to To jump to the page
     */
    public void dialogFragment(Fragment to) {
        getRoot().mManager.dialogFragment(to);
    }

    /**
     * Set the animation to add fragment in dialog mode
     *
     * @param dialog_in  The next page to enter the animation
     * @param dialog_out The next page out of the animation
     */
    public void setDialogAnim(@AnimRes int dialog_in, @AnimRes int dialog_out) {
        getRoot().mManager.setDialogAnim(dialog_in, dialog_out);
    }

    /**
     * close this current Fragment
     */
    public void close() {
        getRoot().mManager.close(this);
    }

    /**
     * Closes the specified fragment
     *
     * @param fragment the specified fragment
     */
    public void close(FragmentWrapper fragment) {
        getRoot().mManager.close(fragment);
    }


    @Override
    public void onHiddenChanged(boolean hidden) {
        if (hidden) {
            onNowHidden();
        } else {
            onNextShow();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    /**
     * Override this method to facilitate access to the current page page Pause callback
     */
    private void onNowHidden() {

    }

    /**
     * Override this method to facilitate access to the current page page Resume callback
     */
    private void onNextShow() {

    }

    /**
     * Get fragment dependent Activity, many times this is very useful
     *
     * @return ActivityWrapper dependent Activity
     */
    public ActivityWrapper getRoot() {
        FragmentActivity activity = getActivity();
        if (activity instanceof ActivityWrapper) {
            return (ActivityWrapper) activity;
        } else {
            throw new ClassCastException("this activity mast be extends ActivityWrapper");
        }
    }

    /**
     * Override this method in order to facilitate the singleTop mode to be called in
     */
    @Override
    public void onNewIntent() {
    }


}
