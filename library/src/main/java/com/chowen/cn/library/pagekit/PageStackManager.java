package com.chowen.cn.library.pagekit;

import android.os.Bundle;
import android.support.annotation.AnimRes;
import android.support.annotation.IntDef;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.chowen.cn.library.R;

/**
 * Created by zhouwen on 16/8/3.
 */
public class PageStackManager implements IActionAble {

    public static final int STANDARD = 0x11;
    public static final int SINGLE_TOP = 0x12;
    public static final int SINGLE_TASK = 0x13;
    public static final int SINGLE_INSTANCE = 0x14;
    public static final int KEEP_CURRENT = 0x15;

    private PageStack mStack;

    private final FragmentActivity mContext;

    private long mClickInterval = 500;

    private long mCurrentTime;

    private int mCurrentMode;

    private int mNextIn;

    private int mNextOut;

    private int mQuitIn;

    private int mQuitOut;

    private Animation mAniNextIn;

    private Animation mAniNextOut;

    private int mDialogIn;

    private int mDialogOut;

    /**
     * Set the time to click to Prevent repeated clicks,default 500ms
     *
     * @param CLICK_SPACE Repeat click time
     */
    public void setClickSpace(long CLICK_SPACE) {
        this.mClickInterval = CLICK_SPACE;
    }


    public PageStackManager(FragmentActivity context) {
        mStack = new PageStack();
        mStack.setCloseFragmentListener(this);
        this.mContext = context;

    }

    /**
     * Set the bottom of the fragment
     * @param mTargetFragment  bottom of the fragment
     */
    public void setFragment(@NonNull FragmentWrapper mTargetFragment) {
        FragmentTransaction transaction = mContext.getSupportFragmentManager().beginTransaction();
        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                .replace(R.id.framLayoutId, mTargetFragment, mTargetFragment.getClass().getName())
                .commit();
        mStack.setStandard(mTargetFragment);
    }

    /**
     * Jump to the specified fragment
     * @param from current fragment
     * @param to next fragment
     */
    public void addFragment(@NonNull final Fragment from, @NonNull final Fragment to) {
        if (System.currentTimeMillis() - mCurrentTime > mClickInterval) {
            mCurrentTime = System.currentTimeMillis();

            FragmentTransaction transaction = mContext.getSupportFragmentManager().beginTransaction();
            if (mNextIn != 0 && mNextOut != 0 && mQuitIn != 0 && mQuitOut != 0) {
                transaction
                        .setCustomAnimations(mNextIn, mNextOut)
                        .add(R.id.framLayoutId, to, to.getClass().getName())
//                        .setCustomAnimations(mNextIn, mNextOut)
//                        .hide(from)
                        .commit();
            } else {
                transaction
                        .add(R.id.framLayoutId, to, to.getClass().getName())
//                        .hide(from)
                        .commit();
            }

        }
    }

    /**
     * Set page switch animation
     *
     * @param nextIn  The next page to enter the animation
     * @param nextOut The next page out of the animation
     * @param quitIn  The current page into the animation
     * @param quitOut Exit animation for the current page
     */
    public void setAnim(@AnimRes int nextIn, @AnimRes int nextOut, @AnimRes int quitIn, @AnimRes int quitOut) {
        this.mNextIn = nextIn;
        this.mNextOut = nextOut;
        this.mQuitIn = quitIn;
        this.mQuitOut = quitOut;
        mAniNextIn = AnimationUtils.loadAnimation(mContext, quitIn);
        mAniNextOut = AnimationUtils.loadAnimation(mContext, quitOut);
    }


    /**
     * Jump to the specified fragment
     * @param from current fragment
     * @param to next fragment
     * @param bundle Parameter carrier
     * @param stackMode fragment mStack Mode
     */
    public void addFragment(FragmentWrapper from, FragmentWrapper to, Bundle bundle, @StackMode int stackMode) {
        if (stackMode != KEEP_CURRENT) {
            mCurrentMode = stackMode;
        }
        if (bundle != null) {
            to.setArguments(bundle);
        }
        switch (mCurrentMode) {
            case SINGLE_TOP:
                if (!mStack.setSingleTop(to)) {
                    addFragment(from, to);
                }
                break;
            case SINGLE_TASK:
                if (!mStack.setSingleTask(to)) {
                    addFragment(from, to);
                }
                break;
            case SINGLE_INSTANCE:
                mStack.setSingleInstance(to);
                addFragment(from, to);
                break;
            default:
                mStack.setStandard(to);
                addFragment(from, to);
                break;
        }


    }

    /**
     * open this fragment
     * @param from current fragment
     * @param to next fragment
     */
    public void openFragment(FragmentWrapper from, FragmentWrapper to) {
        addFragment(from, to, null, KEEP_CURRENT);
    }

    /**
     * Jump to the specified fragment with a parameter form
     * @param from current fragment
     * @param to next fragment
     * @param bundle Parameter carrier
     */
    public void addFragment(FragmentWrapper from, FragmentWrapper to, Bundle bundle) {
        addFragment(from, to, bundle, KEEP_CURRENT);
    }

    /**
     * Jump to the specified fragment and do not hide the current page.
     *
     * @param to To jump to the page
     */
    public void dialogFragment(Fragment to) {
        FragmentTransaction transaction = mContext.getSupportFragmentManager().beginTransaction();
        if (!to.isAdded()) {
            if (mDialogIn != 0 && mDialogOut != 0) {
                transaction
                        .setCustomAnimations(mDialogIn, mDialogOut)
                        .add(R.id.framLayoutId, to, to.getClass().getName())
                        .commit();
            } else {
                transaction
                        .add(R.id.framLayoutId, to, to.getClass().getName())
                        .commit();
            }

        }
    }

    /**
     * Set the animation to add fragment in dialog mode
     *
     * @param dialog_in  The next page to enter the animation
     * @param dialog_out The next page out of the animation
     */
    public void setDialogAnim(@AnimRes int dialog_in, @AnimRes int dialog_out) {
        this.mDialogIn = dialog_in;
        this.mDialogOut = dialog_out;
    }

    /**
     * Closes the specified fragment
     *
     * @param mTargetFragment fragment
     */
    public void closeFragment(Fragment mTargetFragment) {
        FragmentTransaction transaction = mContext.getSupportFragmentManager().beginTransaction();
        transaction.remove(mTargetFragment).commit();
    }

    /**
     * Close the specified fragment by tag
     *
     * @param tag fragment tag
     */
    public void closeFragment(String tag) {
        Fragment fragmentByTag = mContext.getSupportFragmentManager().findFragmentByTag(tag);
        if (fragmentByTag != null) {
            closeFragment(fragmentByTag);
            mContext.getSupportFragmentManager().popBackStackImmediate(tag, FragmentManager.POP_BACK_STACK_INCLUSIVE);
        }
    }

    public void close() {
        mContext.getSupportFragmentManager().popBackStack();
    }


    /**
     * Close all fragment
     */
    public void closeAllFragment() {
        int backStackCount = mContext.getSupportFragmentManager().getBackStackEntryCount();
        for (int i = 0; i < backStackCount; i++) {
            int backStackId = mContext.getSupportFragmentManager().getBackStackEntryAt(i).getId();
            mContext.getSupportFragmentManager().popBackStack(backStackId, FragmentManager.POP_BACK_STACK_INCLUSIVE);
        }
    }

    public void onBackPressed() {
        Fragment[] last = mStack.getLast();
        final Fragment from = last[0];
        Fragment to = last[1];

        if (from != null) {
            if (to != null) {
                FragmentTransaction transaction = mContext.getSupportFragmentManager().beginTransaction();
                transaction.show(to).commit();
            }
            View fromVie = from.getView();
            if (fromVie != null && mAniNextOut != null) {
                fromVie.startAnimation(mAniNextOut);
                mAniNextOut.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {
                        mStack.onBackPressed();
                        closeFragment(from);
                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {

                    }
                });

            } else {
                mStack.onBackPressed();
                closeFragment(from);
            }
        }
        if (to != null) {
            View toView = to.getView();
            if (toView != null && mAniNextIn != null) {
                toView.startAnimation(mAniNextIn);
            }
        } else {
            closeAllFragment();
            mContext.finish();
        }
    }

    public static boolean isFirstClose = true;

    @Override
    public void close(final FragmentWrapper fragment) {
        if (isFirstClose) {
            View view = fragment.getView();
            if (view != null) {
                if (mAniNextOut != null) {
                    view.startAnimation(mAniNextOut);
                    mAniNextOut.setAnimationListener(new Animation.AnimationListener() {
                        @Override
                        public void onAnimationStart(Animation animation) {

                        }

                        @Override
                        public void onAnimationEnd(Animation animation) {
                            closeFragment(fragment);
                        }

                        @Override
                        public void onAnimationRepeat(Animation animation) {

                        }
                    });
                } else {
                    closeFragment(fragment);
                }
            }
            isFirstClose = false;
        } else {
            closeFragment(fragment);
        }

    }

    @Override
    public void open(FragmentWrapper fragment) {
        FragmentTransaction transaction = mContext.getSupportFragmentManager().beginTransaction();
        transaction.show(fragment).commit();
        View view = fragment.getView();
        if (view != null && mAniNextIn != null) {
            view.startAnimation(mAniNextIn);
        }
    }

    @IntDef({STANDARD, SINGLE_TOP, SINGLE_TASK,SINGLE_INSTANCE,KEEP_CURRENT})
    public @interface StackMode {

    }

}
