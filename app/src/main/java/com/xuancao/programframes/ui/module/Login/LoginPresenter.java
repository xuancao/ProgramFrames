package com.xuancao.programframes.ui.module.Login;

import android.support.annotation.NonNull;

import com.xuancao.programframes.base.BasePresenter;
import com.xuancao.programframes.base.LifecycleView;
import com.xuancao.programframes.Rxbus.MsgEvent;
import com.xuancao.programframes.net.callback.Result;
import com.xuancao.programframes.net.error.ResponseErrorCode;
import com.xuancao.programframes.utils.ToastUtil;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class LoginPresenter extends BasePresenter<LoginView> {

    @Inject
    public LoginPresenter(){

    }

    @Override
    public void attachView(@NonNull LifecycleView view) {
        super.attachView(view);

    }

    /**
     * 获取短信验证码
     * @param phone
     */
    public void sendPostForSmsCode(String phone,String version) {

        mDataManager.getSmsCode(phone,version)
                .compose(mView.bindToLifecycle())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(__ -> {

                }, error -> {
                    if (parsingError(error) == ResponseErrorCode.SUCCESS) {
                        ToastUtil.show("获取验证码成功");
                    }
                });
    }


    /**
     * 验证码登录
     * @param phone
     * @param code
     */
    public void sendPostForLogin(String phone, String code,String version) {
        mDataManager.getLoginModel(phone, code,version)
                .compose(mView.bindToLifecycle())
                .map(loginModel -> {
                    getUserHelper().saveUser(loginModel.basicInfo);
                    return loginModel;
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(loginModel -> {
                    mBus.send(new MsgEvent(MsgEvent.LOGIN_SUCCESS_EVENT,loginModel.basicInfo));
                    mView.loginSuccess(loginModel.basicInfo);
                }, error -> {
                    parsingError((Result)error);
//                    parsingError(error);
                    mView.showError();
                });
    }

    public void getLoginByPwd(String phone,String pwd){
        mDataManager.getLoginByPwd(phone,pwd)
                .compose(mView.bindToLifecycle())
                .map(loginModle ->{
                    getUserHelper().saveUser(loginModle.basicInfo);
                    return loginModle;
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(loginModle ->{
                    mBus.send(new MsgEvent(MsgEvent.LOGIN_SUCCESS_EVENT,loginModle.basicInfo));
                    mView.loginSuccess(loginModle.basicInfo);
                }, error -> {
                    parsingError((Result)error);
//                    mView.showError();
                });
    }

    @Override
    public void detachView() {
        super.detachView();

    }
}
