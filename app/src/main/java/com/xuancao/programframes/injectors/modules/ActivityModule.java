package com.xuancao.programframes.injectors.modules;

import android.app.Activity;

import com.xuancao.programframes.injectors.scopes.PerActivity;

import dagger.Module;
import dagger.Provides;

@Module
public class ActivityModule {

    private final Activity mActivity;


    public ActivityModule(Activity mActivity) {
        this.mActivity = mActivity;
    }

    @Provides
    @PerActivity
    public Activity provideActivity() {
        return mActivity;
    }
}

