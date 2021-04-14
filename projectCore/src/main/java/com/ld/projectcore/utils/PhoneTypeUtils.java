package com.ld.projectcore.utils;

import android.app.ActivityManager;
import android.app.Application;
import android.content.ComponentName;
import android.content.Context;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import com.ld.projectcore.base.application.BaseApplication;

import java.util.List;
import java.util.Locale;

/**
 * Created by mac on 2018/1/23.
 * <p>
 * 手机类型的api
 */

public class PhoneTypeUtils {

    public static final int HUA_WEI = 1;
    public static final int XIAO_MI = 2;
    public static final int VIVO = 3;

    public static final String HUA_WEI_NUMBER = "huawei_number";

    public static int getPhoneType() {

        String vendor = Build.MANUFACTURER;
        if (vendor.toLowerCase(Locale.ENGLISH).contains("xiaomi")) {
            return XIAO_MI;
        } else if (vendor.toLowerCase(Locale.ENGLISH).contains("huawei")) {
            return HUA_WEI;
        }else if (vendor.toLowerCase(Locale.ENGLISH).contains("vivo")) {
            return VIVO;
        }
        return 0;
    }


    /**
     * 手机是小米或者魅族
     *
     * @return
     */
    public static boolean isXiaoMiOrMeizu() {
        String vendor = Build.MANUFACTURER;
        if (vendor.toLowerCase(Locale.ENGLISH).contains("xiaomi")) {
            return true;
        } else
            return vendor.toLowerCase(Locale.ENGLISH).contains("meizu");
    }


    /**
     * 华为手机显示角标数字
     */
    public static void showHuaWei() {

        if (getPhoneType() != HUA_WEI) {
            return;
        }

        int number = SPUtil.getInstance().getInt(HUA_WEI_NUMBER);

        Application context = BaseApplication.getsInstance();
        if (isApplicationBroughtToBackground(context)) {   //后台服务
            number++;
        } else {
            number = 0;
        }

        Bundle bundle = new Bundle();
        bundle.putString("package", context.getPackageName());
        String launchClassName = context.getPackageManager().getLaunchIntentForPackage(context.getPackageName()).getComponent().getClassName();
        bundle.putString("class", launchClassName);
        bundle.putInt("badgenumber", number);
        context.getContentResolver().call(Uri.parse("content://com.huawei.android.launcher.settings/badge/"), "change_badge", null, bundle);
        SPUtil.getInstance().putInt(HUA_WEI_NUMBER, number);

    }


    /**
     * 清除华为显示的数据
     */
    public static void clearHuaWeiNumber() {
        if (getPhoneType() != HUA_WEI) {
            return;
        }
        Application context = BaseApplication.getsInstance();
        int number = SPUtil.getInstance().getInt(HUA_WEI_NUMBER);
        if (number != 0) {
            Bundle bundle = new Bundle();
            bundle.putString("package", context.getPackageName());
            String launchClassName = context.getPackageManager().getLaunchIntentForPackage(context.getPackageName()).getComponent().getClassName();
            bundle.putString("class", launchClassName);
            bundle.putInt("badgenumber", 0);
            context.getContentResolver().call(Uri.parse("content://com.huawei.android.launcher.settings/badge/"), "change_badge", null, bundle);
            SPUtil.getInstance().putInt(HUA_WEI_NUMBER, 0);
        }
    }

    public static boolean isApplicationBroughtToBackground(final Context context) {
        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningTaskInfo> tasks = am.getRunningTasks(1);
        if (!tasks.isEmpty()) {
            ComponentName topActivity = tasks.get(0).topActivity;
            if (!topActivity.getPackageName().equals(context.getPackageName())) {
                return true;
            }
        }
        return false;
    }

}
