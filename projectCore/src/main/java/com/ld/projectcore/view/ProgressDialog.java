package com.ld.projectcore.view;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.github.ybq.android.spinkit.SpinKitView;
import com.ld.projectcore.R;
import com.ld.projectcore.R2;
import com.ld.projectcore.utils.InputUtil;

import butterknife.BindView;
import butterknife.ButterKnife;


public class ProgressDialog extends Dialog {

    @BindView(R2.id.tv_progress_title)
    TextView tvProgressTitle;
    @BindView(R2.id.iv_loading)
    SpinKitView mIvLoading;
    @BindView(R2.id.v_bg)
    View mVBg;
    private boolean isTransparent;

    public ProgressDialog(@NonNull Context context, boolean isShow) {
        super(context, R.style.TransparentDialog);
        isTransparent = isShow;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Window window = super.getWindow();

        if (window != null) {
            window.getDecorView().setPadding(0, 0, 0, 0);
            WindowManager.LayoutParams params = window.getAttributes();
            params.width = WindowManager.LayoutParams.MATCH_PARENT;
            params.height = WindowManager.LayoutParams.MATCH_PARENT - InputUtil.INSTANCE.getStatusBarHeight();
            //计算dialog的高度第二种方式
//            params.height = DimensionUtils.getDisplayMetrics().heightPixels - InputUtil.INSTANCE.getStatusBarHeight() -
//                    App.getsInstance().getResources().getDimensionPixelSize(R.dimen.toolbar_height);
//            params.gravity = Gravity.BOTTOM;
            window.setAttributes(params);
        }
        super.setContentView(R.layout.dialog_progress);
//        super.setCanceledOnTouchOutside(true);//点击外部取消对话框
        super.setCanceledOnTouchOutside(false);
        ButterKnife.bind(this);

        // Glide.with(mIvLoading).load(R.drawable.gif_loading).into(mIvLoading);


        if (isTransparent)
            mVBg.setBackgroundColor(Color.TRANSPARENT);
    }

    public void showDialog(String title) {
        super.show();
        tvProgressTitle.setText(title);
        if (isTransparent)
            tvProgressTitle.setVisibility(View.INVISIBLE);
        else
            tvProgressTitle.setVisibility(View.VISIBLE);

    }


    public void hideDialog() {
        super.dismiss();
        if (!isTransparent)
            mVBg.setBackgroundColor(Color.TRANSPARENT);
        isTransparent = true;
    }
}

