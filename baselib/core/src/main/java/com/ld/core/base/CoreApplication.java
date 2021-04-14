package com.ld.core.base;

import android.app.Application;
import android.content.Context;
import android.content.Intent;

import com.bumptech.glide.Glide;
import com.ld.core.BuildConfig;
import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.Logger;

/**
 * Created by ZhangChao
 * on 2020/3/4
 * description: Application基类
 */
public class CoreApplication extends Application {
    public static CoreApplication mApplication;
    public ActivityControl mActivityControl;
    public static boolean IS_DEBUG = BuildConfig.DEBUG ;
    //内存泄露

   /* public CoreApplication(Application application, int tinkerFlags, boolean tinkerLoadVerifyFlag, long applicationStartElapsedTime, long applicationStartMillisTime, Intent tinkerResultIntent) {
        super(application, tinkerFlags, tinkerLoadVerifyFlag, applicationStartElapsedTime, applicationStartMillisTime, tinkerResultIntent);
    }*/

    @Override
    public void onCreate() {
        super.onCreate();
        mApplication = this;
        mActivityControl = new ActivityControl();//activity统一管理器
        // 初始化Logger工具
        Logger.addLogAdapter(new AndroidLogAdapter(){
            @Override
            public boolean isLoggable(int priority, String tag) {
                return BuildConfig.DEBUG;
            }
        });
        Logger.i("当前是否为debug模式："+IS_DEBUG );
    }


    public static CoreApplication getCoreApplication(){
        return mApplication;
    }

    public ActivityControl getActivityControl() {
        return mActivityControl;
    }

    /**
     * 程序终止的时候执行
     */
    @Override
    public void onTerminate() {
        super.onTerminate();
        exitApp();
    }
    /**
     * 退出应用
     */
    public void exitApp() {
        mActivityControl.finishiAll();
        android.os.Process.killProcess(android.os.Process.myPid());
        System.exit(0);
    }


    /**
     * 低内存的时候执行
     */
    @Override
    public void onLowMemory() {
        super.onLowMemory();
        Glide.get(getApplicationContext()).clearMemory();
    }

    // 程序在内存清理的时候执行
    /**
     * 程序在内存清理的时候执行
     */
    @Override
    public void onTrimMemory(int level) {
        super.onTrimMemory(level);
        if (level == Application.TRIM_MEMORY_UI_HIDDEN) {
            Glide.get(getApplicationContext()).clearMemory();
        }
        Glide.get(getApplicationContext()).trimMemory(level);
    }

}
