package com.xuancao.programframes.injectors.modules;

import android.content.BroadcastReceiver;

import com.xuancao.programframes.injectors.scopes.PerService;

import dagger.Module;
import dagger.Provides;

@Module
public class BroadcastModule {

    BroadcastReceiver receiver;

    public BroadcastModule(BroadcastReceiver receiver) {
        this.receiver = receiver;
    }

    @Provides
    @PerService
    public BroadcastReceiver provideBroadcastReceiver() {
        return receiver;
    }
}
