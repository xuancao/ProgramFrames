package com.xuancao.programframes.injectors.compontents;


import com.xuancao.programframes.injectors.modules.BroadcastModule;
import com.xuancao.programframes.injectors.scopes.PerService;
import com.xuancao.programframes.ui.receiver.NetWorkStateReceiver;

import dagger.Component;

@PerService
@Component(modules = BroadcastModule.class, dependencies = AppComponent.class)
public interface BroadcastComponent {

    void inject(NetWorkStateReceiver receiver);
}
