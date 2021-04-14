package com.ld.projectcore.router;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;

import androidx.fragment.app.Fragment;

import com.alibaba.android.arouter.launcher.ARouter;


/**
 * Created by ZhangChao
 * on 2020/3/5
 * description:路由跳转帮助类
 */
public class RouterHelper {

    /**
     * 获取我的页
     */
    public static Fragment toMine() {
        Fragment fragment = (Fragment) ARouter.getInstance().build(RouterPath.MINE_MINE).navigation();
        if (fragment == null) {
            fragment = new Fragment();
        }
        return fragment;
    }



    /**
     * 主页
     */
    public static void toHome(){
        ARouter.getInstance().build(RouterPath.MAIN_HOME).navigation();
    }





}
