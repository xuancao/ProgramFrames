package com.xuancao.programframes.ui;

import android.support.annotation.NonNull;

import com.xuancao.programframes.base.BasePresenter;
import com.xuancao.programframes.base.LifecycleView;
import com.xuancao.programframes.Rxbus.MsgEvent;
import com.xuancao.programframes.models.UserInfoModel;
import com.xuancao.programframes.utils.LogUtil;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class MainPresenter extends BasePresenter<MainView> {


    @Inject
    public MainPresenter(){}

    @Override
    public void attachView(@NonNull LifecycleView view) {
        super.attachView(view);
        mBus.toObservable(MsgEvent.class)
                .compose(mView.bindToLifecycle())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(command -> {
                    dealWithMsgEvent(command);
                });
    }

    private void dealWithMsgEvent(MsgEvent msgEvent){
        switch (msgEvent.type){
            case MsgEvent.NETWORK_STATE_EVENT: //登录成功
                if (msgEvent.getObject() instanceof Boolean){
                    boolean isConnected = (Boolean) msgEvent.getObject();
                    if (isConnected) {
                        LogUtil.e("NetWorkStateCommand isConnected");
                        getUserInfo();
                    }
                }
                break;
        }
    }


    /**
     * 初始化个人信息
     */
    public void getUserInfo() {
        if (getUserHelper().isLogin()) {
            mDataManager.getUserInfo()
                    .compose(mView.bindToLifecycle())
                    .map(loginModel -> {
                        getUserHelper().update(loginModel.basicInfo);
                        return loginModel;
                    })
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(userModel -> {
                        mBus.send(new MsgEvent(MsgEvent.LOGIN_SUCCESS_EVENT,userModel));
                    }, error -> {
                        parsingError(error);
                    });
        }
    }



    @Override
    public void detachView() {
        super.detachView();

    }
}
