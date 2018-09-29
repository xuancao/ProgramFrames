package com.xuancao.programframes.ui.view;

/**
 *   @author shvmily@126.com
 */

public interface ILoading {

    void show();

    void hide();

    void onError();

    void onDataEmpty();

    void setOnRetryAction(Runnable action);
}
