package com.xuancao.programframes;

import android.app.Application;
import android.content.Context;
import android.support.multidex.MultiDex;

import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.Logger;
import com.xuancao.programframes.db.InovelMigration;
import com.xuancao.programframes.db.RealmHelper;
import com.xuancao.programframes.injectors.compontents.AppComponent;
import com.xuancao.programframes.injectors.compontents.DaggerAppComponent;
import com.xuancao.programframes.injectors.modules.ApiModule;
import com.xuancao.programframes.injectors.modules.AppModule;
import com.xuancao.programframes.utils.SharePreferUtils;

import io.realm.Realm;
import io.realm.RealmConfiguration;

public class BaseApp extends Application {

    private static Context context;
    private boolean isDug;


    private AppComponent mAppComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        context = this;

        isDug = BuildConfig.DEBUG;
        //是否打印日志
        Logger.addLogAdapter(new AndroidLogAdapter() {
            @Override
            public boolean isLoggable(int priority, String tag) {
                return isDug;
            }
        });

        mAppComponent = DaggerAppComponent.builder()
                .appModule(new AppModule(this))
                .apiModule(new ApiModule())

                .build();

        initRealm();
        SharePreferUtils.initialize(this);
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

    public static Context getAppContext(){
        return context;
    }


    private void initRealm() {
        Realm.init(this);
        RealmConfiguration configuration = new RealmConfiguration.Builder()
                .name(RealmHelper.DB_NAME)
                .schemaVersion(RealmHelper.DB_VERSION)
                .migration(new InovelMigration())
                .build();
        Realm.setDefaultConfiguration(configuration);
    }

    public AppComponent getApplicationComponent() {
        return mAppComponent;
    }
}
