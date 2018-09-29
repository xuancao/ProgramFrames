package com.xuancao.programframes.injectors.modules;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;

import com.xuancao.programframes.injectors.scopes.PerFragment;

import dagger.Module;
import dagger.Provides;

/**
 * Created by leon on 2017/11/6.
 */
@Module
public class FragmentModule {

    Fragment mFragment;

    public FragmentModule(Fragment fragment) {
        this.mFragment = fragment;
    }

    @Provides
    @PerFragment
    public Fragment provideFragment() {
        return mFragment;
    }

    @Provides
    @PerFragment
    public FragmentActivity provideActivity() {
        return mFragment.getActivity();
    }
}
