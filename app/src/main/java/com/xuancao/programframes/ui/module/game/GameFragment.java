package com.xuancao.programframes.ui.module.game;

import android.os.Bundle;
import android.view.View;

import com.xuancao.programframes.R;
import com.xuancao.programframes.base.BaseFragment;

public class GameFragment extends BaseFragment {

    @Override
    protected void injectorCompontent() {
        getFragmentComponent().inject(this);

    }

    @Override
    public int getCreateViewLayoutId() {
        return R.layout.fragment_game;
    }

    @Override
    public void initView(View inflateView, Bundle savedInstanceState) {

    }

    @Override
    public void initListener() {

    }

    @Override
    public void initData() {

    }
}
