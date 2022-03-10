package com.shen.base.base;

import io.reactivex.disposables.Disposable;

/**
 * @author:Shen
 * @time: 2022/3/4 16:00
 * @desc: Presenter基类
 **/
public interface BasePresenter {

    void onDestroy();

    void addDisposable(Disposable disposable);
}
