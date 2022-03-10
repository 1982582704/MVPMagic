package com.shen.base.base;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;

import com.gyf.immersionbar.ImmersionBar;
import com.shen.base.R;
import com.shen.base.utils.AppManager;
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity;

/**
 * @author:Shen
 * @time: 2022/3/9 18:18
 * @desc:Activity基类(没用网络请求) todo 添加该类主要是为了当BaseActivity泛型带着 Presenter mPresenter时，此时的Activity不存在网络请求时如何处理。
 **/
public abstract class ViewDataBindingActivity<D extends ViewDataBinding> extends RxAppCompatActivity {

    protected D mBinding;

    protected boolean isImmersionBarEnabled = true;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        reqFullScreen();
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, getLayoutResId());
        AppManager.getInstance().addActivity(this);
        initStatusBar();
        initData();
        initView();
        getData();

    }


    /**
     * 获取activity的布局Id
     *
     * @return
     */
    protected abstract int getLayoutResId();

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

    @Override
    protected void onDestroy() {

        if (mBinding != null) {
            mBinding.unbind();
            mBinding = null;
        }
        AppManager.getInstance().finishActivity(this);
        super.onDestroy();
    }

}
