package com.xuancao.programframes.injectors.compontents;


import android.content.Context;


import com.xuancao.programframes.db.RealmHelper;
import com.xuancao.programframes.net.DataManager;
import com.xuancao.programframes.Rxbus.RxBus;
import com.xuancao.programframes.net.error.ResponseErrorParsing;
import com.xuancao.programframes.injectors.modules.ApiModule;
import com.xuancao.programframes.injectors.modules.AppModule;
import com.xuancao.programframes.ui.module.Login.UserHelper;
import com.xuancao.programframes.utils.PermissionsChecker;

import javax.inject.Singleton;

import dagger.Component;
import io.realm.RealmObject;

@Singleton
@Component(modules = {AppModule.class, ApiModule.class})
public interface AppComponent {

    DataManager getDataManager();

    RealmHelper<RealmObject> getRealmHelper();

    UserHelper getUserHelper();

    RxBus getBus();

    Context getContext();

    PermissionsChecker getPermissionsChecker();

    ResponseErrorParsing getResponseErrorParsing();
}
