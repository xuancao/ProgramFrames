package com.xuancao.programframes.ui.module.Login;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.xuancao.programframes.R;
import com.xuancao.programframes.base.BaseActivity;
import com.xuancao.programframes.models.UserInfoModel;
import com.xuancao.programframes.ui.view.PTitleBarView;
import com.xuancao.programframes.utils.ToastUtil;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;

public class LoginPage extends BaseActivity implements LoginView{

    @BindView(R.id.title_bar)
    PTitleBarView mTitleBarView;

    @BindView(R.id.tv_login_way)
    TextView tv_login_way;

    @BindView(R.id.ll_code)
    LinearLayout ll_code;

    @BindView(R.id.et_phone)
    EditText et_phone;

    @BindView(R.id.et_code)
    EditText et_code;

    @BindView(R.id.et_pwd)
    EditText et_pwd;

    @BindView(R.id.tv_code)
    TextView tv_code;

    @BindView(R.id.btn_login)
    Button btn_login;





    @Inject
    LoginPresenter mLoginPresenter;

    @Override
    protected void injectorCompontent() {
        getActivityComponent().inject(this);
        mLoginPresenter.attachView(this);
    }

    @Override
    protected int getLayoutResID() {
        return R.layout.page_login;
    }

    @Override
    protected void initTitleBar() {
        if (mTitleBarView!=null){
            mTitleBarView.setTitleBar(this);
            mTitleBarView.setTitleBar(this);
            mTitleBarView.setPageTitle("登录");
        }
    }

    @Override
    protected void initView(Bundle savedInstanceState) {

    }

    @Override
    protected void initListener() {

    }

    @OnClick({R.id.tv_code,R.id.btn_login,R.id.tv_login_way})
    public void onClick(View view){
        switch (view.getId()) {
            case R.id.tv_login_way:
                if (tv_login_way.isSelected()){
                    tv_login_way.setSelected(false);
                    tv_login_way.setText("验证码登录");
                    ll_code.setVisibility(View.GONE);
                    et_pwd.setVisibility(View.VISIBLE);
                }else {
                    tv_login_way.setSelected(true);
                    tv_login_way.setText("密码登录");
                    ll_code.setVisibility(View.VISIBLE);
                    et_pwd.setVisibility(View.GONE);
                }
                break;
            case R.id.tv_code:
                mLoginPresenter.sendPostForSmsCode(et_phone.getText().toString().trim(),"6.2");
                break;
            case R.id.btn_login:
                if (tv_login_way.isSelected()){
                    if (TextUtils.isEmpty(et_phone.getText().toString().trim()) || TextUtils.isEmpty(et_code.getText().toString().trim())){
                        ToastUtil.show("请输入手机号或验证码");
                        return;
                    }
                    mLoginPresenter.sendPostForLogin(et_phone.getText().toString().trim(),et_code.getText().toString().trim(),"6.2");
                }else {
                    if (TextUtils.isEmpty(et_phone.getText().toString().trim()) || TextUtils.isEmpty(et_pwd.getText().toString().trim())){
                        ToastUtil.show("请输入手机号或密码");
                        return;
                    }
                    mLoginPresenter.getLoginByPwd(et_phone.getText().toString().trim(),et_pwd.getText().toString().trim());
                }
                break;
        }
    }


    @Override
    protected void initData() {

    }


    @Override
    public void showError() {

    }

    @Override
    public void loginSuccess(UserInfoModel userModel) {
        finish();
    }
}
