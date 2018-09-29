package com.xuancao.programframes.ui.module.mine;

import com.xuancao.programframes.base.LifecycleView;
import com.xuancao.programframes.models.UserInfoModel;

interface MineView<T> extends LifecycleView{

    void refreshPersonInfo(UserInfoModel userInfoModel);

    void logout();
}
