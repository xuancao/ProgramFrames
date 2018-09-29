package com.xuancao.programframes.injectors.compontents;


import com.xuancao.programframes.base.BaseFragment;
import com.xuancao.programframes.injectors.modules.FragmentModule;
import com.xuancao.programframes.injectors.scopes.PerFragment;
import com.xuancao.programframes.ui.module.find.FindFragment;
import com.xuancao.programframes.ui.module.game.GameFragment;
import com.xuancao.programframes.ui.module.home.HomeFragment;
import com.xuancao.programframes.ui.module.mine.MineFragment;

import dagger.Component;

/**
 * Created by leon on 16/6/2.
 */
@PerFragment
@Component(modules = FragmentModule.class, dependencies = AppComponent.class)
public interface DataFragmentComponent {

//    FragmentActivity getFragmentActivityy();

    void inject(BaseFragment baseFragment);

    void inject(MineFragment mineFragment);

    void inject(HomeFragment homeFragment);

    void inject(FindFragment findFragment);

    void inject(GameFragment gameFragment);

}
