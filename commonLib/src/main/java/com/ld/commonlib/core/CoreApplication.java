package com.ld.commonlib.core;

import android.app.Application;

import com.ld.commonlib.BuildConfig;
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


}
