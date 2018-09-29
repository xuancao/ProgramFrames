package com.xuancao.programframes.net.interceptor;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.xuancao.programframes.BuildConfig;
import com.xuancao.programframes.net.error.ResponseErrorCode;
import com.xuancao.programframes.net.callback.Result;
import com.xuancao.programframes.net.callback.ResultParser;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Converter;

/**
 * @param <T>
 * @author lqx
 */
public class CustomGsonResponseBodyConverter<T> implements Converter<ResponseBody, T> {

    private final Gson gson;
    private final TypeAdapter<T> adapter;

    CustomGsonResponseBodyConverter(Gson gson, TypeAdapter<T> adapter) {
        this.gson = gson;
        this.adapter = adapter;
    }

    @Override
    public T convert(ResponseBody value) throws IOException {
        //把responsebody转为string
        String jsonString = value.string();
        if (BuildConfig.DEBUG) {
            //打印后台数据
            Log.e("responseData-->", jsonString);
        }

        Result mResult = ResultParser.parseResponse(jsonString);
        // 这里只是为了检测code是否为0,所以只解析HttpStatus中的字段,因为只要code和message就可以了
        if (mResult != null && mResult.getRetcode()!= ResponseErrorCode.SUCCESS_RESPONSE ) {
            value.close();
            //抛出一个RuntimeException, 这里抛出的异常会到subscribe的onError()方法中统一处理
            throw new Result(mResult.retcode,mResult.getMessage());
        }
        try {
            return adapter.fromJson(mResult.result);
        } finally {
            value.close();
        }
    }


}
