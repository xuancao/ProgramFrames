package com.xuancao.programframes.ui.module.mine;

import android.support.annotation.NonNull;

import com.xuancao.programframes.base.BasePresenter;
import com.xuancao.programframes.base.LifecycleView;
import com.xuancao.programframes.models.UserInfoModel;
import com.xuancao.programframes.Rxbus.MsgEvent;
import com.xuancao.programframes.utils.LogUtil;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class MinePresenter extends BasePresenter<MineView> {

    @Inject
    public MinePresenter(){

    }

    @Override
    public void attachView(@NonNull LifecycleView view) {
        super.attachView(view);
        registerBus();
    }

    private void registerBus(){
        mBus.toObservable(MsgEvent.class)
                .compose(mView.bindToLifecycle())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(messageEvent -> {
                    dealWithMsgEvent(messageEvent);
                }, throwable -> {
                    LogUtil.e(throwable.getMessage());
                });
    }

    private void dealWithMsgEvent(MsgEvent msgEvent){
        switch (msgEvent.type){
            case MsgEvent.LOGIN_SUCCESS_EVENT: //登录成功
                UserInfoModel loginModel = (UserInfoModel) msgEvent.getObject();
                mView.refreshPersonInfo(loginModel);
                break;
            case MsgEvent.UPDATE_USER_INFO_EVENT:  //更新用户信息
                UserInfoModel userInfoModel = (UserInfoModel) msgEvent.getObject();
                mView.refreshPersonInfo(userInfoModel);
                break;
        }
    }

    public void logout() {
        getUserHelper().deleteUser();
        mBus.send(new MsgEvent(MsgEvent.LOGOUT_SUCCESS_EVENT));
        mView.logout();
    }

    @Override
    public void detachView() {
        super.detachView();

    }
}
