package com.xuancao.programframes.net.interceptor;


import com.xuancao.programframes.BaseApp;
import com.xuancao.programframes.net.callback.Result;
import com.xuancao.programframes.net.error.ResponseErrorCode;
import com.xuancao.programframes.ui.module.Login.UserHelper;
import com.xuancao.programframes.utils.NetworkUtil;
import com.xuancao.programframes.utils.PermissionsChecker;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;

/**
 * 请求头拦截器
 */
public class HeaderInterceptor implements Interceptor {

    PermissionsChecker mPermissionsChecker;

    UserHelper mUserHelper;



    public HeaderInterceptor(PermissionsChecker permissionsChecker, UserHelper userHelper) {
        mPermissionsChecker = permissionsChecker;
        mUserHelper = userHelper;
    }

    @Override
    public okhttp3.Response intercept(Chain chain) throws IOException {
        Request rqt = null;
        if (NetworkUtil.isNetworkConnected(BaseApp.getAppContext())) {
            Request request = chain.request();
            Request.Builder req = request.newBuilder()
//                    .addHeader("authorization", mUserHelper.token())
//                    .addHeader("x-umeng-device-token", mUserHelper.getUserAgent())
                    ;

            rqt = req.build();
        } else {
            Result error = new Result();
            error.setRetcode(ResponseErrorCode.ERROR_NOT_NET);
            error.setMessage("网络未连接");
            throw error;
        }

        return chain.proceed(rqt);

    }

}
