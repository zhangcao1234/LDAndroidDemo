package com.ld.projectcore.base.view;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.ld.core.base.CoreBaseFragment;
import com.ld.core.utils.ACache;


import java.lang.ref.WeakReference;

import butterknife.ButterKnife;
import butterknife.Unbinder;


/**
 * Created by ZhangChao
 * on 2020/3/4
 * description:
 */
public abstract class BaseFragment extends CoreBaseFragment implements IBaseInitialization, View.OnTouchListener {
    protected WeakReference<View> mRootView;
    protected BaseFragment mContext;

    public BaseActivity mBaseActivity;
    protected View mParentView;
    public ACache mACache;
    private Unbinder mBinder;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mParentView = inflater.inflate(getLayoutResId(), container, false);
        return mParentView;
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        // 拦截触摸事件，防止泄露下去
        view.setOnTouchListener(this);
        String simpleName = super.getClass().getSimpleName();
        if (!simpleName.endsWith("KT"))   //过滤点  kotlin 写的类
            mBinder = ButterKnife.bind(this, view);
        if (getActivity() instanceof BaseActivity) {
            mBaseActivity = (BaseActivity) getActivity();
        }
        mContext = this;
        mACache = ACache.get(mBaseActivity);

        configViews();
        initData();
        initRxBus();
    }

    protected void initRxBus() {
    }


    /**
     * 打开公用界面(需要登录)
     *
     * @param title 标题
     * @param cls   获取fragment的Class   eg: ConfirmOrderFragment.class
     */
    public void needLoginJumpCommonActivity(String title, Class<? extends Fragment> cls) {
        mBaseActivity.needLoginJumpCommonActivity(title, cls);
    }


    /**
     * 打开公用界面
     *
     * @param title 标题
     * @param cls   获取fragment的Class   eg: ConfirmOrderFragment.class
     */
    public void jumpCommonActivity(String title, Class<? extends Fragment> cls) {
        mBaseActivity.jumpCommonActivity(title, cls);
    }


    /**
     * 打开公用界面
     *
     * @param title 标题
     * @param cls   获取fragment的Class   eg: ConfirmOrderFragment.class
     * @param args  fragment 所需要的传入的参数
     */
    public void jumpCommonActivity(String title, Class<? extends Fragment> cls, Bundle args) {
        mBaseActivity.jumpCommonActivity(title, cls, args);
    }

    /*
     */

    /**
     * 跳转公共界面
     */

    public void jumpCommonActivityForResult(String title, Class<? extends Fragment> cls, Bundle args, int request) {
        Intent intent = new Intent(mBaseActivity, CommonActivity.class);
        intent.putExtra(CommonActivity.COMMON_FRAGMENT_CLASS_NAME, cls.getName());
        intent.putExtra(CommonActivity.COMMON_TITLE, title);
        intent.putExtra(CommonActivity.COMMON_FRAGMETN_ARGUMENTS, args);
        super.startActivityForResult(intent, request);
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (mBinder != null)
            mBinder.unbind();

    }


    public void finish() {
        if (super.getActivity() != null) {
            super.getActivity().finish();
        }

    }


    @Override
    public void onDestroy() {
        super.onDestroy();

    }

    public void showProgress(String progressMessage) {

         mBaseActivity.showProgress(progressMessage);
    }

    public void hideProgress() {
         mBaseActivity.hideProgress();
    }

    /**
     * 不需要dialog背景
     */
    public void notDialogBg() {
         mBaseActivity.notDialogBg();
    }

    /**
     * 给下拉刷新用的,哪个使用,复写该方法
     */
    public void complete() {

    }
    /*
     */

    /**
     * 超时,需要重新登录
     */
    public void shouldLogin() {
        //  mBaseActivity.shouldLogin();
    }

    /**
     * 隐藏键盘
     *
     * @param t
     */
    public void hideSoftInputFromWindow(TextView t) {
        InputMethodManager imm = (InputMethodManager) mBaseActivity.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(t.getWindowToken(), 0);
    }


    public BaseActivity getBaseActivity() {
        return mBaseActivity;
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        return true;
    }
}