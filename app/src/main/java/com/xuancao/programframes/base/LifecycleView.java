package com.xuancao.programframes.base;


import com.trello.rxlifecycle2.LifecycleTransformer;

/**
 * Created by ChenLiang on 16/6/3.
 */
public interface LifecycleView extends IBaseView {
    <T> LifecycleTransformer<T> bindToLifecycle();
}
