package com.chowen.cn.library.ioc;

import android.view.View;

/**
 * Created by zhouwen on 21/7/16.
 */
public interface ViewFinder {

    /**
     * find view instance
     * @param id view's id
     * @return view
     */
    View findViewById(int id);
}
