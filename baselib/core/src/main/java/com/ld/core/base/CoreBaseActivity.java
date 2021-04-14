package com.ld.core.base;


import android.app.Activity;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentActivity;




/**
 * Created by ZhangChao
 * on 2020/3/4
 * description: activity公共基类
 */
public abstract class CoreBaseActivity extends FragmentActivity {
    protected Activity mActivity;
    public Context mContext;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivity = this;
        mContext = this;
       // baseInit();
        //加入activity管理
        CoreApplication.getCoreApplication().getActivityControl().addActivity(this);

    }


    @Override
    protected void onDestroy() {
        super.onDestroy();

        //移除类
        CoreApplication.getCoreApplication().getActivityControl().removeActivity(this);
    }

}
