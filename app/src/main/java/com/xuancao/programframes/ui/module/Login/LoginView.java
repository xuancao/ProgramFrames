package com.xuancao.programframes.ui.module.Login;

import com.xuancao.programframes.base.LifecycleView;
import com.xuancao.programframes.models.UserInfoModel;

interface LoginView extends LifecycleView {

    void loginSuccess(UserInfoModel userModel);

}
