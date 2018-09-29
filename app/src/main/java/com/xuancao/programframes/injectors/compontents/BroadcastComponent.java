package com.xuancao.programframes.injectors.compontents;


import com.xuancao.programframes.injectors.modules.BroadcastModule;
import com.xuancao.programframes.injectors.scopes.PerService;
import com.xuancao.programframes.ui.receiver.NetWorkStateReceiver;

import dagger.Component;

/**
 * Created by leon on 2017/9/24.
 */

@PerService
@Component(modules = BroadcastModule.class, dependencies = AppComponent.class)
public interface BroadcastComponent {

    void inject(NetWorkStateReceiver receiver);
}
