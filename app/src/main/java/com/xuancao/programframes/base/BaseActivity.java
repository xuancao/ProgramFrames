package com.xuancao.programframes.base;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.RelativeLayout;

import com.trello.rxlifecycle2.components.support.RxAppCompatActivity;
import com.xuancao.programframes.BaseApp;
import com.xuancao.programframes.R;
import com.xuancao.programframes.base.BaseHelper.ActManager;
import com.xuancao.programframes.base.BaseHelper.BackHandledInterface;
import com.xuancao.programframes.injectors.compontents.ActivityComponent;
import com.xuancao.programframes.injectors.compontents.AppComponent;
import com.xuancao.programframes.injectors.compontents.DaggerActivityComponent;
import com.xuancao.programframes.ui.view.ITitleBar;
import com.xuancao.programframes.ui.view.PTitleBarView;
import com.xuancao.programframes.utils.LogUtil;
import com.xuancao.programframes.utils.ViewColorUtils;

import butterknife.ButterKnife;
import butterknife.Unbinder;


public abstract class BaseActivity extends RxAppCompatActivity implements ITitleBar, BackHandledInterface {

    protected BaseFragment mFragment;

    protected AppComponent getApplicationComponent() {
        return ((BaseApp) getApplication()).getApplicationComponent();
    }

    protected ActivityComponent getActivityComponent() {
        return DaggerActivityComponent.builder().appComponent(getApplicationComponent()).build();
    }

    @Override
    public void setSelectedFragment(BaseFragment selectedFragment) {
        mFragment = selectedFragment;
    }

    private Unbinder mUnBinder;

    RelativeLayout mWindowRootLayout;

    private ViewGroup mView;

    protected int mStatusBarHeight;

    protected View mStatusBarView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(getLayoutResID());
        ActManager.getInstance().pushActivity(this);
        init(savedInstanceState);
    }

    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(R.layout.activity_base_layout);

        mWindowRootLayout = (RelativeLayout) findViewById(R.id.window_layout);
        View contentView = LayoutInflater.from(this).inflate(layoutResID, null);
        mWindowRootLayout.addView(contentView, new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        mView = findViewById(R.id.status_bar);

        mUnBinder = ButterKnife.bind(this, contentView);
    }

    private void init(Bundle savedInstanceState) {
        injectorCompontent();
        initState();
        showStatusBar();
        initTitleBar();
        initView(savedInstanceState);
        initListener();
        initData();

        //针对8.0系统问题：java.lang.RuntimeException: java.lang.IllegalStateException: Only fullscreen opaque activities can request orientation
        if(Build.VERSION.SDK_INT < Build.VERSION_CODES.O) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }
    }

    /**
     * 沉浸式状态栏
     */
    protected void initState() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);   //透明状态栏
//            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION); //隐藏底部导航栏
//            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION); //透明导航栏
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {     //设置导航栏颜色
                getWindow().setNavigationBarColor(getResources().getColor(R.color.status_bar_color));
            }
        }
    }

    protected void showStatusBar() {
        if (mStatusBarView == null) {
            mStatusBarView = new View(this);
            int screenWidth = getResources().getDisplayMetrics().widthPixels;
            mStatusBarHeight = ViewColorUtils.getStatusBarHeight(this);
            ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(screenWidth, mStatusBarHeight);
            mStatusBarView.setLayoutParams(params);
            mStatusBarView.requestLayout();
            mStatusBarView.setBackgroundResource(R.color.status_bar_color);
            if (mView != null)
                mView.addView(mStatusBarView, 0);
        }
    }

    protected void hideStatusBar() {
        if (mStatusBarView!=null) mStatusBarView.setVisibility(View.GONE);
    }



    @Override
    public void onTitlePressed() {

    }

    @Override
    public void onTitleLeftTipPressed() {

    }

    @Override
    public void onTitleRightTipPressed() {

    }

    protected abstract void injectorCompontent();
    protected abstract int getLayoutResID();
    protected abstract void initTitleBar();
    protected abstract void initView(Bundle savedInstanceState);
    protected abstract void initListener();
    protected abstract void initData();


    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mUnBinder.unbind();
        System.gc();
        ActManager.getInstance().removeActivity(this);
    }


    public void startActivity(Class<?> cls) {
        Intent intent = new Intent(this, cls);
        startActivity(intent);
    }

    @Override
    public void onBackPressed() {
        if (mFragment == null || !mFragment.onBackPressed()) {
            if (getSupportFragmentManager().getBackStackEntryCount() == 0) {
                super.onBackPressed();
            } else {
                getSupportFragmentManager().popBackStack();
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        LogUtil.e(getClass().getName()+" onActivityResultresultCode : " + resultCode);
        if (getSupportFragmentManager().getBackStackEntryCount() != 0) {
            for (Fragment fragment : getSupportFragmentManager().getFragments()) {
                fragment.onActivityResult(requestCode, resultCode, data);
            }
        }
    }
}
