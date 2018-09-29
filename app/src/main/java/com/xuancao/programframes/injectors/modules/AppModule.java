package com.xuancao.programframes.injectors.modules;

import android.app.Application;
import android.content.Context;

import com.xuancao.programframes.db.RealmHelper;
import com.xuancao.programframes.net.ApiInterface;
import com.xuancao.programframes.net.DataManager;
import com.xuancao.programframes.Rxbus.RxBus;
import com.xuancao.programframes.net.error.ResponseErrorParsing;
import com.xuancao.programframes.ui.module.Login.UserHelper;
import com.xuancao.programframes.utils.PermissionsChecker;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import io.realm.RealmObject;


@Module
public class AppModule {

    Application mApplication;

    public AppModule(Application application) {
        mApplication = application;
    }

    @Provides
    @Singleton
    public DataManager provideApi(Context context, ApiInterface apiService, RxBus rxBus, RealmHelper<RealmObject> realmHelper, UserHelper userHelper) {
        return new DataManager(context, apiService, rxBus, realmHelper, userHelper);
    }

    @Singleton
    @Provides
    public RealmHelper<RealmObject> provideRealmHelper() {
        return new RealmHelper();
    }

    @Singleton
    @Provides
    public UserHelper provideUserHelper(RealmHelper<RealmObject> helper) {
        return new UserHelper(helper);
    }

    @Singleton
    @Provides
    public RxBus provideBus() {
        return new RxBus();
    }

    @Provides
    @Singleton
    Context provideContext() {
        return mApplication.getBaseContext();
    }


    @Singleton
    @Provides
    ResponseErrorParsing provideResponseErrorParsing() {
        return new ResponseErrorParsing();
    }


    @Provides
    @Singleton
    Application provideApplication() {
        return mApplication;
    }

    @Provides
    @Singleton
    PermissionsChecker providePermissionsChecker(Context context) {
        return new PermissionsChecker(context);
    }

}
