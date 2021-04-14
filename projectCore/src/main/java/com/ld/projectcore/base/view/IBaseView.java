package com.ld.projectcore.base.view;

/**
 * Created by Administrator on 2017/3/15.
 */

public interface IBaseView {

    void showProgress(String progressMessage);

    void hideProgress();

    /**
     * 给下拉刷新用的,哪个使用,复写该方法
     */
   // void complete();

  //  void shouldLogin();

    BaseActivity getBaseActivity();

}
