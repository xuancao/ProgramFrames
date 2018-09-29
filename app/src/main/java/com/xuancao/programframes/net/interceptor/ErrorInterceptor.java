package com.xuancao.programframes.net.interceptor;

import com.google.gson.Gson;
import com.xuancao.programframes.net.callback.Result;
import com.xuancao.programframes.net.error.ResponseErrorCode;

import java.io.IOException;

import okhttp3.Interceptor;

/**
 * 错误拦截器
 */

public class ErrorInterceptor implements Interceptor {
    @Override
    public okhttp3.Response intercept(Chain chain) throws IOException {
        okhttp3.Response response = chain.proceed(chain.request());
        if (response!=null) {
            if (response.code() != ResponseErrorCode.SUCCESS) {
                String errorMsg = response.body().string();
                Gson gson = new Gson();
                Result error = gson.fromJson(errorMsg, Result.class);
                if (error != null) {
                    throw error;
                } else {
                    error = new Result();
                    if (response.code() == ResponseErrorCode.ERROR_NO_RETURN_DATA) {
                        error.setRetcode(ResponseErrorCode.ERROR_NO_RETURN_DATA);
                        error.setMessage("请求成功，无返回数据");
                    } else {
                        error.setRetcode(ResponseErrorCode.ERROR_UNKNOWN);
                        error.setMessage("未知错误");
                    }
                    throw error;
                }
            }
        } else {
            Result error = new Result();
            error.setRetcode(ResponseErrorCode.ERROR_UNKNOWN);
            error.setMessage("未知错误");
            throw error;
        }
        return response;
    }

}
