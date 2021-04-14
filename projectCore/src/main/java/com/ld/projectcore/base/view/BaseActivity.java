package com.ld.projectcore.base.view;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Looper;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;


import com.jaeger.library.StatusBarUtil;
import com.ld.core.base.CoreBaseActivity;

import com.ld.core.utils.ACache;
import com.ld.projectcore.R;
import com.ld.projectcore.base.application.BaseApplication;
import com.ld.projectcore.utils.PhoneTypeUtils;
import com.ld.projectcore.view.ProgressDialog;


import java.util.List;

import butterknife.ButterKnife;
import butterknife.Unbinder;


/**
 * Created by ZhangChao
 * on 2020/3/4
 * description:mvp的基础 Activity
 */
public abstract class BaseActivity extends CoreBaseActivity implements IBaseInitialization{
    protected BaseActivity mContext;
    private Unbinder mBind;
    public ACache mACache;
    public static boolean statusUpdate = false;
    private ProgressDialog mProgressDialog;
    private boolean mIsShow;  //为true时表示:  ProgressDialog不需要显示背景
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContentView(getLayoutResId());
        mContext = this;
        mBind = ButterKnife.bind(this);
        mACache = ACache.get(mContext);
        initStatusBar();
        configViews(savedInstanceState);
        initData();
        initRxBus();
    }



    protected void initRxBus() {
    }


    protected void startActivity(Class<?> cls) {
        Intent intent = new Intent(mContext, cls);
        mContext.startActivity(intent);
    }
    protected void initStatusBar() {
        StatusBarUtil.setLightMode(this);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M || PhoneTypeUtils.isXiaoMiOrMeizu()) {
            StatusBarUtil.setColor(this, getResources().getColor(R.color.white), 0);
        }

    }

    /**
     * 打开公用界面（需要登录）
     *
     * @param title
     * @param cls
     */
    public void needLoginJumpCommonActivity(String title, Class<? extends Fragment> cls) {
        /*if (AccountApiImpl.getInstance().isLogin()) {
            jumpCommonActivity(title, cls);
        } else {
            //ToastUtils.showSingleToast(getString(R.string.please_login));
            RouterHelper.toLoginForResult(this,Constants.LOGIN_SUCCEED_CODE);
        }*/
    }

    /**
     * 打开公用界面
     *
     * @param title 标题
     * @param cls   获取fragment的Class   eg: ConfirmOrderFragment.class
     */
    public void jumpCommonActivity(String title, Class<? extends Fragment> cls) {
        jumpCommonActivity(title, cls, null);
    }

    /**
     * 打开公用界面
     *
     * @param title 标题
     * @param cls   获取fragment的Class   eg: ConfirmOrderFragment.class
     * @param args  fragment 所需要的传入的参数
     */
    public void jumpCommonActivity(String title, Class<? extends Fragment> cls, Bundle args) {
        jumpCommonActivityForResult(title, cls, args, 0);
    }

    /**
     * 跳转公共界面
     *
     * @param title
     * @param cls
     * @param args
     * @param request 请求的code
     */
    public void jumpCommonActivityForResult(String title, Class<? extends Fragment> cls, Bundle args, int request) {
        jumpCommonActivityForResult(title, cls.getName(), args, request);
    }

    /**
     * 跳转公共界面
     *
     * @param title
     * @param cls
     * @param args
     * @param request 请求的code
     */
    public void jumpCommonActivityForResult(String title, String cls, Bundle args, int request) {
        Intent intent = new Intent(mContext, CommonActivity.class);
        intent.putExtra(CommonActivity.COMMON_FRAGMENT_CLASS_NAME, cls);
        intent.putExtra(CommonActivity.COMMON_TITLE, title);
        intent.putExtra(CommonActivity.COMMON_FRAGMETN_ARGUMENTS, args);
        super.startActivityForResult(intent, request);
    }



    /**
     * 隐藏键盘
     *
     * @param t
     */
    public void hideSoftInputFromWindow(View t) {
        if (t == null)
            return;
        InputMethodManager imm = (InputMethodManager) mContext.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(t.getWindowToken(), 0);
    }


    /**
     * 隐藏键盘
     */
    public void hideSoftInputFromWindow() {
        hideSoftInputFromWindow(super.getWindow().getDecorView());
    }

    @Override
    public void onBackPressed() {
        hideSoftInputFromWindow();
        BaseApplication app = BaseApplication.getInstance();
        if ( app== null) {
            super.onBackPressed();
            return;
        }

        List<String> createActList = app.getCreateActList();
        if (createActList.size() == 1) {
            startHome();
            // 只有一个 Activity 在栈中, 如果 不是 homeActivity , 表示从推送进来, 点击返回的时候, 启动 HomeActivity
            if (app.isHomeAct(this)) {
                super.onBackPressed();
            } else
                startHome();
        } else {
            super.onBackPressed();
        }
    }


    private void startHome() {
        //RouterHelper.toHome();
        finish();
    }

    public void onResume() {
        super.onResume();
        //MobclickAgent.onResume(this);
        mIsShow = true;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mBind != null)
            mBind.unbind();
        if (mProgressDialog != null) {
            mProgressDialog.dismiss();
            mProgressDialog = null;
        }
        //监控内存泄漏
    }

    public void showProgress(String progressMessage) {
        if (TextUtils.isEmpty(progressMessage)) {
            return;
        }

        if (Looper.getMainLooper() != Looper.myLooper()) {
            return;
        }
      if (mProgressDialog == null)
            mProgressDialog = new ProgressDialog(mContext, mIsShow);
        mProgressDialog.showDialog(progressMessage);

    }

    public void hideProgress() {
       if (mProgressDialog != null) {
            mProgressDialog.hideDialog();
        }
    }

    /**
     * 检查是否登入
     *
     * @return true已经登入  false直接跳转到登入界面
     */
/*    public boolean checkLogin() {


        if (!AccountApiImpl.getInstance().isLogin()) {
           // ToastUtils.showSingleToast("请先登录");
           RouterHelper.toLoginForResult(this,Constants.LOGIN_SUCCEED_CODE);
        }
        return AccountApiImpl.getInstance().isLogin();
    }*/
    /**
     * 不需要dialog背景
     */
    public void notDialogBg() {
        if (mProgressDialog == null)
                mProgressDialog = new ProgressDialog(mContext, true);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);



    }

    public BaseActivity getBaseActivity() {
        return mContext;
    }

    @Override
    protected void onRestart() {
        super.onRestart();


    }
}
