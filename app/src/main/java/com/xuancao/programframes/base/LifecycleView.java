package com.xuancao.programframes.base;


import com.trello.rxlifecycle2.LifecycleTransformer;

public interface LifecycleView extends IBaseView {
    <T> LifecycleTransformer<T> bindToLifecycle();
}
