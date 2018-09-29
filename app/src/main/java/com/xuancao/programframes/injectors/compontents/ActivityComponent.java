package com.xuancao.programframes.injectors.compontents;

import com.xuancao.programframes.injectors.modules.ActivityModule;
import com.xuancao.programframes.injectors.scopes.PerActivity;
import com.xuancao.programframes.ui.MainPage;
import com.xuancao.programframes.ui.module.Login.LoginPage;

import dagger.Component;

@PerActivity
@Component(modules = ActivityModule.class, dependencies = AppComponent.class)
public interface ActivityComponent {


    void inject(MainPage mainPage);

    void inject(LoginPage loginPage);

}
