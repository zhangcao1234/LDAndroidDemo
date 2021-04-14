package com.ld.mine;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.ld.projectcore.base.view.BaseFragment;
import com.ld.projectcore.router.RouterPath;

/**
 * Created by ZhangChao
 * on 2020/12/7
 * description:
 */
@Route(path = RouterPath.MINE_MINE)
public class MineFragment extends BaseFragment {
    @Override
    public int getLayoutResId() {
        return R.layout.frag_miane;
    }

    @Override
    public void configViews() {

    }

    @Override
    public void initData() {

    }
}
