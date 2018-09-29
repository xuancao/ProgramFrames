package com.xuancao.programframes.net;

import android.content.Context;

import com.google.gson.JsonObject;
import com.xuancao.programframes.Rxbus.RxBus;
import com.xuancao.programframes.db.RealmHelper;
import com.xuancao.programframes.models.UserModel;
import com.xuancao.programframes.ui.module.Login.UserHelper;

import java.util.HashMap;

import io.reactivex.Observable;
import io.realm.RealmObject;


public class DataManager {

    RealmHelper<RealmObject> mRealmHelper;

    UserHelper mUserHelper;

    Context mContext;

    RxBus mBus;

    ApiInterface mApiService;

    public RealmHelper getRealmHelper() {
        return mRealmHelper;
    }

    public UserHelper getUserHelper() {return  mUserHelper;}

    public RxBus getRxBus() {
        return mBus;
    }

    public DataManager(Context context, ApiInterface apiService, RxBus rxBus, RealmHelper realmHelper, UserHelper userHelper){
        mContext = context;
        mApiService = apiService;
        mBus = rxBus;
        mRealmHelper = realmHelper;
        mUserHelper = userHelper;
    }

    public Context getContext() {
        return mContext;
    }

    public boolean isLogin() {
        return mUserHelper.isLogin();
    }

    public Observable<UserModel> getUserInfo() {
        return mApiService.getUserInfo();
    }

    public Observable<Void> getSmsCode(String phone,String version) {
//        JsonObject requestBody = new JsonObject();
//        requestBody.addProperty("mobile", phone);
//        requestBody.addProperty("v", version);
        HashMap<String, String> requestBody = new HashMap<>();
        requestBody.put("mobile", phone);
        requestBody.put("v",version);
        return mApiService.getSmsCode(requestBody);
    }

    public Observable<UserModel> getLoginModel(String phone, String code,String version) {
        JsonObject requestBody = new JsonObject();
        requestBody.addProperty("mobile", phone);
        requestBody.addProperty("verifyCode", code);
        requestBody.addProperty("v", version);
        return mApiService.getLoginModel(requestBody);
    }


    public Observable<UserModel> getLoginByPwd(String mobile, String pwd){
        return mApiService.getLoginByPwd(mobile,pwd,"6.2");
    }


}
