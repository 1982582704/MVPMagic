package com.shen.base.base;

import android.os.Bundle;

import androidx.annotation.Nullable;

import com.gyf.immersionbar.ImmersionBar;
import com.shen.base.R;
import com.shen.base.utils.AppManager;
import com.trello.rxlifecycle2.LifecycleTransformer;
import com.trello.rxlifecycle2.android.ActivityEvent;
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity;
import com.xiaoyezi.networkdetector.NetStateObserver;
import com.xiaoyezi.networkdetector.NetworkDetector;
import com.xiaoyezi.networkdetector.NetworkType;

/**
 * @author:Shen
 * @time: 2022/3/4 15:46
 * @desc:Activity基类
 **/
public abstract class BaseActivity<T extends BasePresenter> extends RxAppCompatActivity implements BaseView {

    protected T mPresenter;
    protected NetStateObserver netStateObserver;
    protected boolean isImmersionBarEnabled = true;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getLayoutResId();
        AppManager.getInstance().addActivity(this);
        initStatusBar();
        initData();
        initView();
        getData();
        initNetStateObserver();

    }

    /**
     * 添加网络监听
     */
    private void initNetStateObserver() {
        netStateObserver = new NetStateObserver() {
            @Override
            public void onDisconnected() {
                //网络断开
                BaseActivity.this.onDisconnected();
            }

            @Override
            public void onConnected(NetworkType networkType) {
                //网络重连
                BaseActivity.this.onConnected(networkType);
            }
        };
        if (isNetStateObserver()) {
            NetworkDetector.getInstance().addObserver(netStateObserver);
        }
    }

    /**
     * 获取activity的布局Id
     *
     * @return
     */
    protected abstract void getLayoutResId();

    /**
     * 初始化数据
     */
    protected abstract void initData();

    /**
     * 初始化页面布局 晚于initData
     */
    protected abstract void initView();

    /**
     * 获取数据或者处理业务逻辑
     */
    protected abstract void getData();


    /**
     * 全屏方法
     */
    protected void reqFullScreen() {

    }

    /**
     * 是否可以使用沉浸式
     * Is immersion bar enabled boolean.
     *
     * @return the boolean
     */
    protected boolean isImmersionBarEnabled() {
        return isImmersionBarEnabled;
    }

    /**
     * 初始化沉浸式状态栏
     */
    protected void initStatusBar() {
        if (isImmersionBarEnabled()) {
            initImmersionBar();
        }
    }

    protected void initImmersionBar() {
        ImmersionBar.with(this)
                .statusBarColor(R.color.base_text_gray_f2)
                .fitsSystemWindows(true)
                .init();
    }

    /**
     * show loading
     *
     * @param msg
     */
    @Override
    public void showLoading(String msg) {

    }

    /**
     * hide Loading
     */
    @Override
    public void hideLoading() {

    }

    /**
     * 当前页面是否允许网络监听
     *
     * @return
     */
    protected boolean isNetStateObserver() {
        return false;
    }

    /**
     * 网络断开
     */
    protected void onDisconnected() {

    }

    /**
     * 网络重连
     *
     * @param networkType 网络类型
     */
    protected void onConnected(NetworkType networkType) {

    }

    @Override
    public LifecycleTransformer bindLifecycle() {
        return bindUntilEvent(ActivityEvent.DESTROY);
    }

    @Override
    protected void onDestroy() {
        if (isNetStateObserver()) {
            NetworkDetector.getInstance().removeObserver(netStateObserver);
        }
        if (mPresenter != null) {
            mPresenter.onDestroy();
            mPresenter = null;
        }
        AppManager.getInstance().finishActivity(this);
        super.onDestroy();
    }

}
