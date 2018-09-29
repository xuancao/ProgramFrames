package com.xuancao.programframes.injectors.modules;

import android.app.Application;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import com.xuancao.programframes.net.ApiInterface;
import com.xuancao.programframes.net.interceptor.CustomGsonConverterFactory;
import com.xuancao.programframes.net.interceptor.ErrorInterceptor;
import com.xuancao.programframes.net.interceptor.HeaderInterceptor;
import com.xuancao.programframes.net.interceptor.HttpLoggingInterceptor;
import com.xuancao.programframes.ui.module.Login.UserHelper;
import com.xuancao.programframes.utils.PermissionsChecker;

import java.util.concurrent.TimeUnit;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.Cache;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;


/**
 * Created by linfeng on 2017/5/13.
 */
@Module
public class ApiModule {

    @Provides
    @Singleton
    public OkHttpClient provideOkHttpClient(HeaderInterceptor headerInterceptor, HttpLoggingInterceptor logInterceptor, Cache cache) {

        return new OkHttpClient.Builder()
                .retryOnConnectionFailure(true)//默认重试一次，若需要重试N次，则要实现拦截器。
                .addInterceptor(headerInterceptor)
                .addInterceptor(new ErrorInterceptor())
//                .addInterceptor(logInterceptor)
                .connectTimeout(10, TimeUnit.SECONDS)
                .writeTimeout(20, TimeUnit.SECONDS)
                .readTimeout(20, TimeUnit.SECONDS)
                .cache(cache)
                .build();
    }

    @Provides
    @Singleton
    HttpLoggingInterceptor provideHttpLoggingInterceptor() {
        HttpLoggingInterceptor logInterceptor = new HttpLoggingInterceptor();
        logInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        return logInterceptor;
    }

    @Provides
    @Singleton
    HeaderInterceptor provideHeaderInterceptor(PermissionsChecker permissionsChecker, UserHelper userHelper) {
        return new HeaderInterceptor(permissionsChecker, userHelper);
    }

    @Provides
    @Singleton
    Cache provideOkHttpCache(Application application) {
        int cacheSize = 10 * 1024 * 1024; // 10 MiB
        Cache cache = new Cache(application.getCacheDir(), cacheSize);
        return cache;
    }

    @Provides
    @Singleton
    Gson provideGson() {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES);
        return gsonBuilder.create();
    }

    @Provides
    @Singleton
    ApiInterface provideRetrofit(Gson gson, OkHttpClient okHttpClient) {
        return new Retrofit.Builder()
                .client(okHttpClient)
//                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
//                .addConverterFactory(GsonConverterFactory.create(gson))
                .addConverterFactory(CustomGsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .baseUrl(ApiInterface.BASE_URL)
                .build().create(ApiInterface.class);
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }

}
