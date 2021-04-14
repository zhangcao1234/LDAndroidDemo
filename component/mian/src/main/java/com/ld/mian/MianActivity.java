package com.ld.mian;

import com.ld.projectcore.base.view.BaseActivity;
import com.ld.projectcore.router.RouterHelper;


import butterknife.OnClick;

/**
 * Created by ZhangChao
 * on 2020/12/7
 * description:
 */
public class MianActivity extends BaseActivity {


    @Override
    public int getLayoutResId() {
        return R.layout.act_main;
    }

    @Override
    public void configViews() {

    }

    @Override
    public void initData() {

    }



    @OnClick(R2.id.button)
    public void onViewClicked() {
        jumpCommonActivity("我的", RouterHelper.toMine().getClass());
    }
}
