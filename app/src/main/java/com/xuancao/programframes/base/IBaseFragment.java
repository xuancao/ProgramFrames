package com.xuancao.programframes.base;

import android.os.Bundle;
import android.view.View;


public interface IBaseFragment {

    int getCreateViewLayoutId();

    void initView(View inflateView, Bundle savedInstanceState);

    void initListener();

    void findView(View inflateView, Bundle savedInstanceState);

    void initData();

}
