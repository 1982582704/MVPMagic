package com.shen.base.base;

import com.trello.rxlifecycle2.LifecycleTransformer;

/**
 * @author:Shen
 * @time: 2022/3/8 10:53
 * @desc:View基类
 **/
public interface BaseView {

    /**
     * show Loading
     *
     * @param msg
     */
    void showLoading(String msg);

    /**
     * hide Loading
     */
    void hideLoading();

    /**
     * bind Lifecycle
     *
     * @return
     */
    LifecycleTransformer bindLifecycle();
}
