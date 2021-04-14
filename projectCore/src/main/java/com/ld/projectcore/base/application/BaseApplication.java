package com.ld.projectcore.base.application;

import android.app.Activity;
import android.app.Application;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;


import com.ld.core.base.CoreApplication;
import com.ld.projectcore.BuildConfig;
import com.ld.projectcore.base.view.BaseActivity;
import com.ld.projectcore.utils.PhoneTypeUtils;
import com.ld.router.RouterConfig;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ZhangChao
 * on 2020/3/4
 * description:保存全局变量设计的基本类application
 */
public class BaseApplication extends CoreApplication {
    private static BaseApplication sInstance;
    public static boolean isInstall = false;
    public static boolean isUpdate = false;
    private List<String> createdAct = new ArrayList<>();
    private boolean mIsBackGround = true;     //应用进入后台了

  /*  public BaseApplication(Application application, int tinkerFlags, boolean tinkerLoadVerifyFlag, long applicationStartElapsedTime, long applicationStartMillisTime, Intent tinkerResultIntent) {
        super(application, tinkerFlags, tinkerLoadVerifyFlag, applicationStartElapsedTime, applicationStartMillisTime, tinkerResultIntent);
    }*/

    @Override
    public void onCreate() {
        super.onCreate();
        //arouter路由初始化
        RouterConfig.init(getCoreApplication(), BuildConfig.IS_DEBUG);
        sInstance = this;
        getInstance().registerActivityLifecycleCallbacks(new Application.ActivityLifecycleCallbacks() {
            @Override
            public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
                createdAct.add(activity.getClass().getSimpleName());
            }

            @Override
            public void onActivityStarted(Activity activity) {
            }

            @Override
            public void onActivityResumed(Activity activity) {
                if (mIsBackGround) {
                    mIsBackGround = false;
                    PhoneTypeUtils.clearHuaWeiNumber();
                }
            }

            @Override
            public void onActivityPaused(Activity activity) {
            }

            @Override
            public void onActivityStopped(Activity activity) {
            }

            @Override
            public void onActivitySaveInstanceState(Activity activity, Bundle outState) {
            }

            @Override
            public void onActivityDestroyed(Activity activity) {
                if (activity == null)
                    return;

                for (int i = createdAct.size() - 1; i >= 0; i--) {
                    if (createdAct.get(i).equals(activity.getClass().getSimpleName())) {
                        createdAct.remove(i);
                        break;
                    }
                }

            }
        });
    }

    public List<String> getCreateActList() {
        return this.createdAct;
    }

    public static BaseApplication getInstance() {
        return sInstance;
    }

    public static Application getsInstance() {
        return sInstance;
    }

    public boolean isHomeAct(BaseActivity activity) {

        if (activity == null)
            return false;

        String actName = activity.getClass().getSimpleName();

        return TextUtils.equals(actName, "HomeActivity");
    }
}
