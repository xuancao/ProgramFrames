package com.xuancao.programframes.base;

import android.content.Context;
import android.support.annotation.NonNull;


import com.xuancao.programframes.db.RealmHelper;
import com.xuancao.programframes.net.DataManager;
import com.xuancao.programframes.Rxbus.RxBus;
import com.xuancao.programframes.net.callback.Result;
import com.xuancao.programframes.net.error.ResponseErrorParsing;
import com.xuancao.programframes.ui.module.Login.UserHelper;
import com.xuancao.programframes.utils.LogUtil;

import javax.inject.Inject;

import io.realm.RealmObject;


public abstract class BasePresenter<V extends LifecycleView> implements IPresenter<LifecycleView> {

    @Inject
    public DataManager mDataManager;

    @Inject
    public ResponseErrorParsing mErrorParsing;

    public RxBus mBus;

    public V mView;

    @Override
    public void attachView(@NonNull LifecycleView view) {
        mErrorParsing.setDataManager(mDataManager);
        mBus = getBus();
        mView = (V)view;
    }

    @Override
    public void detachView() {

    }

    public Context getContext() {
        return mDataManager.getContext();
    }

    public RxBus getBus() {
        return mDataManager.getRxBus();
    }

    public DataManager getDataManager() {
        return mDataManager;
    }

    public RealmHelper<RealmObject> getmRealmHelper() {
        return mDataManager.getRealmHelper();
    }

    public UserHelper getUserHelper() {
        return mDataManager.getUserHelper();
    }

    public boolean isLogin() {
        return mDataManager.isLogin();
    }

    public int parsingError(Throwable error) {

        if (error != null) {
            if (error instanceof Result) {
                return mErrorParsing.parsingError((Result) error);
            } else {
                if (error.getMessage() != null) {
                    LogUtil.e(" parsingError no ResponseError : " + error.getMessage());
                } else {
                    LogUtil.e(" parsingError no ResponseError : " + error.toString());
                }
            }
        }

        return 0;
    }
}
