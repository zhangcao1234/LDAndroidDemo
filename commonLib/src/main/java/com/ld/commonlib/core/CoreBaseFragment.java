package com.ld.commonlib.core;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.orhanobut.logger.Logger;

/**
 * Created by ZhangChao
 * on 2020/3/4
 * description: fragment公共基类
 */
public abstract class CoreBaseFragment extends Fragment {



    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view,savedInstanceState);



    }

    @Override
    public void onResume() {
        super.onResume();
        Logger.d("当前的fragment:"+getClass().getSimpleName());
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

    }
}
