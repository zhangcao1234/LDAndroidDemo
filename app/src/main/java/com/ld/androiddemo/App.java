package com.ld.androiddemo;

import android.os.Handler;
import android.os.Process;
import android.util.Log;

import com.ld.projectcore.base.application.BaseApplication;

import static android.os.Process.THREAD_PRIORITY_BACKGROUND;

/**
 * Created by ZhangChao
 * on 2020/12/7
 * description:
 */
public class App extends BaseApplication {
    @Override
    public void onCreate() {
        super.onCreate();
        initThreadService();
    }

    private void initThreadService() {

        new Thread(() -> {
            Process.setThreadPriority(THREAD_PRIORITY_BACKGROUND);
            //TaskDataBase.getInstance().init(getApplication());
           // initDownLoader();
           // initWebView();
           // initShare();
           // initPush();
           // initBaidu();
            //  initToutiao();
           // initApk();
           // initHuawei();
           /* if (BuildConfig.DEBUG) {//debug 模式下，记录崩溃信息在本地
                CrashHandler crashHandler = CrashHandler.getInstance();
                crashHandler.init(getApplicationContext());
            }*/
        }).start();

    }
}
