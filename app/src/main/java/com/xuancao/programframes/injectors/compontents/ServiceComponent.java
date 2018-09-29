package com.xuancao.programframes.injectors.compontents;

import com.xuancao.programframes.injectors.modules.ServiceModule;
import com.xuancao.programframes.injectors.scopes.PerService;

import dagger.Component;

@PerService
@Component(modules = ServiceModule.class, dependencies = AppComponent.class)
public interface ServiceComponent {

//    void inject(MBMService service);

}
