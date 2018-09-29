package com.xuancao.programframes.net;

import com.google.gson.JsonObject;
import com.xuancao.programframes.models.UserModel;

import java.util.Map;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;

public interface ApiInterface {

    String BASE_URL = ApiHelper.getMainHost()+"/";


    //获取个人信息
    @GET("my/profile")
    Observable<UserModel> getUserInfo();

    /**
     * 获取短信验证码
     * @param
     * @return
     */
    @POST("member/fastLoginRequestCode")
//    Observable<UserModel> getSmsCode(@Body JsonObject smsCodeModel);
    Observable<Void> getSmsCode(@QueryMap Map<String,String> smsCodeModel);

    /**
     * 验证码登录
     * @param loginModel
     * @return
     */
    @POST("member/fastLogin")
    Observable<UserModel> getLoginModel(@Body JsonObject loginModel);

    /**
     * 密码登录
     * @param mobile
     * @param password
     * @param version
     * @return
     */
    @GET("/member/loginMobile")
    Observable<UserModel> getLoginByPwd(@Query("mobile") String mobile, @Query("password") String password, @Query("v") String version);
}
