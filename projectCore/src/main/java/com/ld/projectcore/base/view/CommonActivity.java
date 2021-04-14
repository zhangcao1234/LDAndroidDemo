package com.ld.projectcore.base.view;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import com.ld.projectcore.R;
import com.ld.projectcore.R2;

import com.ld.projectcore.utils.FastClickUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * Created by Administrator on 2017/3/29.
 * <p>
 * 相同布局的activity   防止创建太多的activity  ,共用同一个activity
 * <p>
 * 当需要显示右边布局的实现 OnMenuRightClickListener
 */

public class CommonActivity extends BaseActivity {


    public static final String COMMON_TITLE = "commonTitle";
    public static final String COMMON_FRAGMENT_CLASS_NAME = "fragmentClassName";
    public static final String COMMON_FRAGMETN_ARGUMENTS = "commonFragmetnArguments";
    @BindView(R2.id.iv_back)
    ImageView mIvBack;
    @BindView(R2.id.tv_center)
    TextView mTvCenter;
    @BindView(R2.id.tv_right)
    TextView mTvRight;
    @BindView(R2.id.head)
    ConstraintLayout head;


    private OnMenuRightClickListener mListener;

    private HandleBackClickListener mHandleBackListener;

    @Override
    public int getLayoutResId() {
        return R.layout.act_common;
    }

    @Override
    public void initData() {

        //设置标题
        Intent intent = super.getIntent();
        String title = intent.getStringExtra(COMMON_TITLE);
        if (TextUtils.isEmpty(title)){
            head.setVisibility(View.GONE);
        }
        mTvCenter.setText(title);

        String fragmentClassName = intent.getStringExtra(COMMON_FRAGMENT_CLASS_NAME);
        Bundle fragmentArgument = intent.getBundleExtra(COMMON_FRAGMETN_ARGUMENTS);
        Fragment fragment = null;
        try {
            // 采用 instantiate 方法初始化 Fragment, 可将 fragment 需要的参数传入,  方便使用
            fragment = Fragment.instantiate(mContext, fragmentClassName, fragmentArgument);
            //显示fragment
            super.getSupportFragmentManager().beginTransaction().replace(R.id.fl_common, fragment, title).commit();
        } catch (Exception e) {
            e.printStackTrace();
            Log.e("", "fragment will not null " + fragmentClassName);
            super.finish();
            return;
        }

        if (fragment instanceof OnMenuRightClickListener) {  //显示右边menu
            mListener = (OnMenuRightClickListener) fragment;
            if (!TextUtils.isEmpty(mListener.getMenuRightTitle())) {
                mTvRight.setVisibility(View.VISIBLE);
                mTvRight.setText(mListener.getMenuRightTitle());
            } else if (mListener.getMenuRightIcon() != 0) {
                mTvRight.setVisibility(View.VISIBLE);
                mTvRight.setCompoundDrawablesWithIntrinsicBounds(mListener.getMenuRightIcon(), 0, 0, 0);
            }
            mListener.initTvRight(mTvRight);
        }

        if (fragment instanceof HandleBackClickListener)
            mHandleBackListener = (HandleBackClickListener) fragment;


    }

    @Override
    public void configViews() {
    }



    @Override
    protected void onDestroy() {
        super.onDestroy();
        mListener = null;
    }

    @OnClick({R2.id.tv_right, R2.id.iv_back})
    public void onViewClicked(View view) {
        if (FastClickUtils.getInstance().isFastClick()) {
            return;
        }

        hideSoftInputFromWindow(mTvCenter);
        int id = view.getId();
        if (id == R.id.iv_back) {
            hideSoftInputFromWindow(mTvCenter);
            onBackPressed();
        } else if (id == R.id.tv_right) {
            if (mListener != null) {
                mListener.onMenuRightClick(view);
            }
        }


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //   if (resultCode == BasePayActKT.getCANCEL_PAY()) {    //取消支付回调
        List<Fragment> fragments = super.getSupportFragmentManager().getFragments();
        for (Fragment frag : fragments)
            frag.onActivityResult(requestCode, resultCode, data);
        //  }
    }

    @Override
    public void finish() {
        hideSoftInputFromWindow(mTvCenter);
        super.finish();
    }

    @Override
    public void onBackPressed() {
        if (null != mHandleBackListener) {
            if (!mHandleBackListener.onBackClick())
                super.onBackPressed();
        } else
            super.onBackPressed();
    }

    public void setTextTitle(String title) {
        if (mTvCenter != null)
            mTvCenter.setText(title);
    }

    public void setRightTitle(String title) {
        mTvRight.setText(title);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    /**
     * 对于需要显示又边menu的fragment需要实现该接口
     */
    public interface OnMenuRightClickListener {

        /**
         * item 显示的文字
         *
         * @return
         */
        String getMenuRightTitle();

        /**
         * item 显示的图标   若没有返回-1
         *
         * @return
         */
        default int getMenuRightIcon() {
            return 0;
        }

        /**
         * 当item被点击的回调
         *
         * @param item
         */
        default void onMenuRightClick(View item) {
        }


        default void initTvRight(TextView tvRight) {

        }

    }

    public interface HandleBackClickListener {

        /**
         * 是否要自己处理返回按钮事件,    点击左上角图标, 和 返回按钮的时候,   都会调用这个方法...
         *
         * @return true 表示要消费返回事件,  则 CommonActivity 不会走返回事件, false 表示不消费返回事件,  默认交给 CommonActivity 去处理
         */
        boolean onBackClick();

    }

    @Override
    public String toString() {
        Intent intent = getIntent();
        String title = mTvCenter == null ? "" : mTvCenter.getText().toString();
        if (intent != null) {
            String fragName = intent.getStringExtra(COMMON_FRAGMENT_CLASS_NAME);
            if (!TextUtils.isEmpty(fragName))
                title = fragName;
        }
        return getClass().getSimpleName() + " -> " + title;
    }


    @Override
    public void onResume() {
        super.onResume();

    }

    @Override
    public void onPause() {
        super.onPause();

    }
}
