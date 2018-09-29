package com.xuancao.programframes.ui.module.mine;

import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.xuancao.programframes.R;
import com.xuancao.programframes.base.BaseFragment;
import com.xuancao.programframes.models.UserInfoModel;
import com.xuancao.programframes.ui.module.Login.LoginPage;
import com.xuancao.programframes.ui.view.PTitleBarView;
import com.xuancao.programframes.utils.Glide.ImageLoader;
import com.xuancao.programframes.utils.StringUtil;
import com.xuancao.programframes.utils.ToastUtil;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;

public class MineFragment extends BaseFragment implements MineView{

    @BindView(R.id.title_bar)
    PTitleBarView mTitleBarView;

    @BindView(R.id.tv_login_out)
    TextView mLoginOut;

    @BindView(R.id.iv_head)
    ImageView mHeadView;

    @BindView(R.id.tv_name)
    TextView mNameView;

    @BindView(R.id.tv_login_status)
    TextView mLoginStatus;

    @Inject
    MinePresenter mPresenter;

    @Override
    protected void injectorCompontent() {
        getFragmentComponent().inject(this);
        mPresenter.attachView(this);
    }

    @Override
    public int getCreateViewLayoutId() {
        return R.layout.fragment_mine;
    }

    @Override
    public void initView(View inflateView, Bundle savedInstanceState) {
        initTitleBar();
    }

    private void initTitleBar() {
        mTitleBarView.setTitleBar(this);
        mTitleBarView.setPageTitle("我的");
    }

    @Override
    public void initListener() {

    }

    @Override
    public void initData() {
    if (mPresenter.isLogin()) {
       refreshPersonInfo(null);
    }else {
        logout();
    }
    }

    @Override
    public void onTitleRightTipPressed() {
        if (mPresenter.isLogin()) {

        } else {
            startActivity(LoginPage.class);
        }
    }

    @OnClick(R.id.tv_login_out)
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_login_out:
                mPresenter.logout();
                break;
        }
    }

    @Override
    public void refreshPersonInfo(UserInfoModel userInfoModel) {
        mTitleBarView.setPageRightBtn(getContext(), -1, "更改个人信息");
        mLoginOut.setVisibility(View.VISIBLE);

        if (StringUtil.isNotEmpty(mPresenter.getUserHelper().getHeadUrl())) {
            ImageLoader.loadImage(mPresenter.getUserHelper().getHeadUrl(),mHeadView);
        } else {
            mHeadView.setImageResource(R.mipmap.iv_head_image);
        }
        if (StringUtil.isNotEmpty(mPresenter.getUserHelper().getNickName())) {
            mNameView.setText(mPresenter.getUserHelper().getNickName());
        } else {
            mNameView.setText("姓名");
        }
        if(StringUtil.isNotEmpty(mPresenter.getUserHelper().getMemberId())){
            mLoginStatus.setText("用户ID：" + mPresenter.getUserHelper().getMemberId());
        }else {
            mLoginStatus.setText("未登录");
        }
    }

    @Override
    public void logout() {
        mTitleBarView.setPageRightBtn(getContext(), -1, "点击登录");
        mLoginStatus.setText("未登录");
        mNameView.setText("姓名");
        mLoginOut.setVisibility(View.GONE);
        mHeadView.setImageResource(R.mipmap.iv_head_image);
        ToastUtil.show("退出登录成功！");
    }

    @Override
    public void showError() {

    }
}
