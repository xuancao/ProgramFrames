package com.xuancao.programframes.injectors.modules;

import android.app.Service;


import com.xuancao.programframes.injectors.scopes.PerActivity;

import dagger.Module;
import dagger.Provides;

@Module
public class ServiceModule {

    Service mService;

    public ServiceModule(Service service) {
        this.mService = service;
    }

    @Provides
    @PerActivity
    public Service provideService() {
        return mService;
    }
}
