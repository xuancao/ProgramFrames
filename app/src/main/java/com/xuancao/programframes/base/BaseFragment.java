package com.xuancao.programframes.base;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.trello.rxlifecycle2.components.support.RxFragment;
import com.xuancao.programframes.BaseApp;
import com.xuancao.programframes.R;
import com.xuancao.programframes.base.BaseHelper.BackHandledInterface;
import com.xuancao.programframes.injectors.compontents.AppComponent;
import com.xuancao.programframes.injectors.compontents.DaggerDataFragmentComponent;
import com.xuancao.programframes.injectors.compontents.DataFragmentComponent;
import com.xuancao.programframes.injectors.modules.FragmentModule;
import com.xuancao.programframes.ui.view.ITitleBar;
import com.xuancao.programframes.ui.view.PEmptyView;
import com.xuancao.programframes.ui.view.PLoadingView;
import com.xuancao.programframes.utils.ViewColorUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public abstract class BaseFragment extends RxFragment implements IBaseFragment, ITitleBar {

    public static final String FRAGMENT_TAG_KEY = "FRAGMENT_TAG_KEY";

    public static final String FRAGMENT_IS_SECOND = "FRAGMENT_IS_SECOND";

    private static final String FRAGMENT_STACKNAME = "FRAGMENT_STACKNAME";

    private String mLastStackName;

    Unbinder mUnbinder;

    private ViewGroup mView;

    protected BackHandledInterface mBackHandledInterface;

    @Nullable
    @BindView(R.id.base_loading)
    PLoadingView mLoadingView;

    @Nullable
    @BindView(R.id.base_empty)
    PEmptyView mEmptyView;


    public boolean onBackPressed() {
        return false;
    }

    protected AppComponent getAppComponent() {
        return ((BaseApp)getContext().getApplicationContext()).getApplicationComponent();
    }

    protected DataFragmentComponent getFragmentComponent() {
        return DaggerDataFragmentComponent.builder().appComponent(getAppComponent()).fragmentModule(new FragmentModule(this)).build();
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        injectorCompontent();
        getFragmentComponent().inject(this);
    }

    protected abstract void injectorCompontent();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        if (!(getActivity() instanceof BackHandledInterface)) {
            throw new ClassCastException("Hosting Activity must implement BackHandledInterface");
        } else {
            this.mBackHandledInterface = (BackHandledInterface) getActivity();
        }

        if (mView == null) {
            mView = (ViewGroup) inflater.inflate(getCreateViewLayoutId(), container, false);
        }
        ViewGroup parent = (ViewGroup) mView.getParent();
        if (parent != null) {
            parent.removeView(mView);
        }


        mUnbinder = ButterKnife.bind(this, mView);
        return mView;

    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //Fragment 穿透异常
        view.setClickable(true);
        initView(view, savedInstanceState);
        initListener();
        initData();
    }


    @Override
    public void findView(View inflateView, Bundle savedInstanceState) {

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mUnbinder.unbind();
    }

    public abstract int getCreateViewLayoutId();

    @Override
    public void onStart() {
        super.onStart();
        //告诉FragmentActivity，当前Fragment在栈顶
        mBackHandledInterface.setSelectedFragment(this);
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    public void startActivity(Class cls) {
        Intent intent = new Intent();
        intent.setClass(getActivity(), cls);
        getContext().startActivity(intent);
    }


    protected void showLodingView() {
        if (mLoadingView != null)
            mLoadingView.show();
    }

    protected void hideLodingView() {
        if (mLoadingView != null)
            mLoadingView.hide();
    }


    protected void showEmptyView() {
        if (mEmptyView != null)
            mEmptyView.show();
    }

    protected void hideEmptyView() {
        if (mEmptyView != null)
            mEmptyView.hide();
    }

    public void startFragment(FragmentActivity activity) {
        startFragment(activity, R.id.window_layout, null, null, 0, 0);
    }

    public void startFragmentAnima(FragmentActivity activity) {
        startFragment(activity, R.id.window_layout, null, null, R.anim.slide_right_in, R.anim.slide_right_out);
    }

    public void startFragmentAnima() {
        startFragment(getActivity(), R.id.window_layout, null, null, R.anim.slide_right_in, R.anim.slide_right_out);
    }

    public void startFragment(FragmentActivity activity, int layoutId, String stackName,
                              String tag, int animaIn, int animaOut) {
        if (!TextUtils.isEmpty(mLastStackName)) {
            this.finishFragment(mLastStackName);
            mLastStackName = null;
        }
        Bundle bundle = getArguments();
        if (bundle == null) {
            bundle = new Bundle();
        }
        if (stackName == null) {
            stackName = "" + System.currentTimeMillis() + hashCode();
        }
        bundle.putString(FRAGMENT_STACKNAME, stackName);
        mLastStackName = stackName;
        if (tag == null) {
            tag = stackName;
        }
        bundle.putString(FRAGMENT_TAG_KEY, tag);
        bundle.putBoolean(FRAGMENT_IS_SECOND, true);
        if (activity != null) {
            setArguments(bundle);
            FragmentTransaction transaction = activity.getSupportFragmentManager()
                    .beginTransaction();
            transaction.setCustomAnimations(animaIn, 0, 0, animaOut);
            transaction.addToBackStack(mLastStackName);
            transaction.add(layoutId, this, tag);
            transaction.addToBackStack(stackName);
            transaction.commitAllowingStateLoss();
            activity.getSupportFragmentManager().executePendingTransactions();
        }
    }


    public void finishFragment() {
        finishFragment(mLastStackName);
    }

    public void finishFragment(String name) {
        getFragmentManager().popBackStackImmediate(name,
                FragmentManager.POP_BACK_STACK_INCLUSIVE);
    }

    public void finishAboveFragment() {
        getFragmentManager().popBackStackImmediate(mLastStackName, 0);
    }

    public void finishAllFragment() {
        getFragmentManager().popBackStackImmediate(null, 1);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        @SuppressLint("RestrictedApi") List<Fragment> fragments = getChildFragmentManager().getFragments();
        if (fragments != null) {
            for (Fragment fragment : fragments) {
                fragment.onActivityResult(requestCode, resultCode, data);
            }
        }
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
}
