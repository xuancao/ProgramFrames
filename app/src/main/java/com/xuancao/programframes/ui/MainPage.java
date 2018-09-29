package com.xuancao.programframes.ui;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.xuancao.programframes.BaseApp;
import com.xuancao.programframes.Constant;
import com.xuancao.programframes.R;
import com.xuancao.programframes.base.BaseActivity;
import com.xuancao.programframes.base.BaseHelper.ActManager;
import com.xuancao.programframes.ui.module.find.FindFragment;
import com.xuancao.programframes.ui.module.game.GameFragment;
import com.xuancao.programframes.ui.module.home.HomeFragment;
import com.xuancao.programframes.ui.module.mine.MineFragment;
import com.xuancao.programframes.utils.ToastUtil;
import com.xuancao.programframes.widgets.tabview.TabView;
import com.xuancao.programframes.widgets.tabview.TabViewChild;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;

public class MainPage extends BaseActivity implements MainView{


    @BindView(R.id.tabView)
    TabView mTabView;

    @Inject
    MainPresenter mPresenter;


    @Override
    protected void injectorCompontent() {
        getActivityComponent().inject(this);
        mPresenter.attachView(this);
    }

    @Override
    protected int getLayoutResID() {
        return R.layout.page_main;
    }

    @Override
    protected void initTitleBar() {

    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        List<TabViewChild> tab = new ArrayList<>();

        TabViewChild tab_bookshelf = new TabViewChild(
                R.mipmap.tab_home_select, R.mipmap.tab_home_normal,
                getResources().getString(R.string.tip_home), new HomeFragment());
        TabViewChild tab_home = new TabViewChild(
                R.mipmap.tab_game_select, R.mipmap.tab_game_normal,
                getResources().getString(R.string.tip_game), new GameFragment());
        TabViewChild tab_recharge = new TabViewChild(
                R.mipmap.tab_find_select, R.mipmap.tab_find_normal,
                getResources().getString(R.string.tip_find), new FindFragment());
        TabViewChild tab_mine = new TabViewChild(
                R.mipmap.tab_my_select, R.mipmap.tab_my_normal,
                getResources().getString(R.string.tip_mine), new MineFragment());

        tab.add(tab_bookshelf);
        tab.add(tab_home);
        tab.add(tab_recharge);
        tab.add(tab_mine);
        mTabView.setTabViewDefaultPosition(Constant.PAGE_HOME);
        mTabView.setTabViewChild(tab, getSupportFragmentManager());
    }

    @Override
    protected void initListener() {
        mTabView.setOnTabChildClickListener(new TabView.OnTabChildClickListener() {
            @Override
            public void onTabChildClick(int position, ImageView currentImageIcon, TextView currentTextView) {

            }
        });

    }

    @Override
    protected void initData() {

    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        mTabView = null;
        System.gc();
    }

    @Override
    public void onBackPressed() {
        if (mFragment == null || !mFragment.onBackPressed()) {
            if (getSupportFragmentManager().getBackStackEntryCount() == 0) {
                exit();
            } else {
                getSupportFragmentManager().popBackStack();
            }
        }
    }

    /**
     * 上一次点击退出的时间
     **/
    private long mLastExitTime = 0;
    public boolean isDoubleExit;
    private void exit() {
        long now = System.currentTimeMillis();
        if (now - mLastExitTime < 500) {
            isDoubleExit = true;
            ActManager.getInstance().AppExit(this);
        } else {
            mLastExitTime = now;
            ToastUtil.show("再按一次退出程序");
        }
    }

    @Override
    public Context getContext() {
        return null;
    }


    @Override
    public void showError() {

    }
}
