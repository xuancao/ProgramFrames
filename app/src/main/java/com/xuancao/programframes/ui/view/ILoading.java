package com.xuancao.programframes.ui.view;

public interface ILoading {

    void show();

    void hide();

    void onError();

    void onDataEmpty();

    void setOnRetryAction(Runnable action);
}
