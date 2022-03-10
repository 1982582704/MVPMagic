package com.shen.mvpmagic;

import android.widget.Toast;

import com.shen.base.base.BaseActivity;
import com.shen.mvpmagic.databinding.ActivityMainBinding;

/**
 * @author:Shen
 * @time: 2022/3/1 18:02
 * @desc:
 **/
public class MainActivity extends BaseActivity<ActivityMainBinding> {

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initData() {
        mBinding.tv.setOnClickListener(v -> {
            Toast.makeText(this, "123000-0000", 0).show();
        });
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void getData() {

    }
}