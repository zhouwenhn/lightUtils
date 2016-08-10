package com.chowen.cn.library.pagekit;

import android.support.v4.app.Fragment;

import java.util.ArrayList;

/**
 * Created by zhouwen on 16/8/3.
 */
public class PageStack {

    private ArrayList<ArrayList<FragmentWrapper>> mStackList = new ArrayList<>();

    private ArrayList<FragmentWrapper> mStack;

    private IActionAble mListener;

    public PageStack() {
        if (mStack == null) {
            mStack = new ArrayList<>();
        }
        mStackList.add(mStack);
    }


    /**
     * standard mode,Directly add to the current task mStack
     *
     * @param fragment Added fragment
     */
    public void setStandard(FragmentWrapper fragment) {
        mStackList.get(mStackList.size() - 1).add(fragment);
    }

    /**
     * SingleTop mode ,If the top is not created
     *
     * @param fragment Added fragment
     * @return Whether to contain the current instance
     */
    public boolean setSingleTop(FragmentWrapper fragment) {
        ArrayList<FragmentWrapper> lastList = mStackList.get(mStackList.size() - 1);
        if (lastList.isEmpty()) {
            lastList.add(fragment);
            return false;
        } else {
            FragmentWrapper last = lastList.get(lastList.size() - 1);
            if (last.getClass().getName().equals(fragment.getClass().getName())) {
                fragment.onNewIntent();
                return true;
            } else {
                lastList.add(fragment);
                return false;
            }
        }


    }

    /**
     * singTask mode ,If the current task mStack does not create and empty all of the upper instance
     *
     * @param fragment Added fragment
     * @return Whether to contain the current instance
     */
    public boolean setSingleTask(FragmentWrapper fragment) {
        boolean isRemove = false;
        ArrayList<FragmentWrapper> lastList = mStackList.get(mStackList.size() - 1);
        if (lastList.isEmpty()) {
            lastList.add(fragment);
        } else {
            int tempIndex = 0;
            for (int x = 0; x <= lastList.size() - 1; x++) {
                if (lastList.get(x).getClass().getName().equals(fragment.getClass().getName())) {
                    //clear all instance
                    isRemove = true;
                    tempIndex = x;
                    break;
                }
            }
            if (!isRemove) {
                lastList.add(fragment);
            } else {
                if (mListener != null) {
                    mListener.open(lastList.get(tempIndex));
                    PageStackManager.isFirstClose = true;
                    for (int i = lastList.size() - 1; i > tempIndex; i--) {
                        mListener.close(lastList.get(i));
                    }
                    for (int j = lastList.size() - 1; j > tempIndex; j--) {
                        lastList.remove(j);
                    }
                }

            }
        }
        return isRemove;

    }

    /**
     * singleInstance mode,Create a new task mStack at a time.
     *
     * @param fragment 加入的fragment
     */
    public void setSingleInstance(FragmentWrapper fragment) {
        ArrayList<FragmentWrapper> pages = new ArrayList<>();
        pages.add(fragment);
        mStackList.add(pages);
    }

    public void onBackPressed() {
        int i = mStackList.size() - 1;
        if (i >= 0) {
            ArrayList<FragmentWrapper> lastStack = mStackList.get(i);
            if (lastStack != null && (!lastStack.isEmpty())) {
                lastStack.remove(lastStack.size() - 1);
                if (lastStack.isEmpty()) {
                    mStackList.remove(lastStack);
                }
            } else {
                mStackList.remove(lastStack);
            }
        } else {
            mStackList.clear();
        }
    }

    protected void setCloseFragmentListener(IActionAble listener) {
        this.mListener = listener;
    }

    protected Fragment[] getLast() {
        Fragment[] pages = new Fragment[2];
        boolean hasFirst = false;
        for (int x = mStackList.size() - 1; x >= 0; x--) {
            ArrayList<FragmentWrapper> list = mStackList.get(x);
            if (list != null && (!list.isEmpty())) {
                if (hasFirst) {
                    pages[1] = list.get(list.size() - 1);
                    break;
                } else {
                    hasFirst = true;
                    pages[0] = list.get(list.size() - 1);
                    if (list.size() > 1) {
                        pages[1] = list.get(list.size() - 2);
                    }
                }

            }
        }
        return pages;
    }
}
