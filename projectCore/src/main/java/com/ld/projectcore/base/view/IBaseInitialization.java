package com.ld.projectcore.base.view;

import android.os.Bundle;

import androidx.annotation.LayoutRes;



/**
 * Created by Administrator on 2017/3/16.
 * <p>
 * 基类需要初始化的数据
 */

public interface IBaseInitialization {

    @LayoutRes
    int getLayoutResId();
    /**
     * 对各种控件进行设置、适配、填充数据
     */
    void configViews();

    default void configViews(Bundle savedInstanceState){
        configViews();
    }

    void initData();
}
