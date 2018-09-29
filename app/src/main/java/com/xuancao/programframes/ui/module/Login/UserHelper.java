package com.xuancao.programframes.ui.module.Login;

import android.os.Build;
import android.webkit.WebSettings;

import com.xuancao.programframes.BaseApp;
import com.xuancao.programframes.Constant;
import com.xuancao.programframes.db.RealmHelper;
import com.xuancao.programframes.models.UserInfoModel;
import com.xuancao.programframes.utils.SharePreferUtils;
import com.xuancao.programframes.utils.StringUtil;

import io.realm.RealmObject;

public class UserHelper {

    private static String mMemberId = "";

    private RealmHelper<RealmObject> mHelper;
    private static String mUMToken = "";

    public UserHelper(RealmHelper<RealmObject> helper) {
        mHelper = helper;
    }

    public String getUserAgent() {
        String userAgent ="";
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            try {
                userAgent = WebSettings.getDefaultUserAgent(BaseApp.getAppContext());
            }catch (Exception e) {
                userAgent = System.getProperty("http.agent");
            }
        }else {
            userAgent = System.getProperty("http.agent");
        }
        StringBuffer sb =new StringBuffer();
        for (int i =0, length = userAgent.length(); i < length; i++) {
            char c = userAgent.charAt(i);
            if (c <='\u001f' || c >='\u007f') {
                sb.append(String.format("\\u%04x", (int) c));}else {
                sb.append(c);}
        }
        return sb.toString();
    }

    public boolean isLogin() {
        if (mHelper.queryFirst(UserInfoModel.class)!=null) {
            return true;
        } else {
            return false;
        }
    }

    public void setHeadUrl(String url) {
        UserInfoModel user = (UserInfoModel) mHelper.queryFirst(UserInfoModel.class);
        if (user != null) {
            user.setAvatar(url);
            mHelper.add(user);
        }
    }


    public String getHeadUrl() {
        UserInfoModel user = (UserInfoModel) mHelper.queryFirst(UserInfoModel.class);
        if (user == null) {
            return "";
        }
        if (StringUtil.isEmpty(user.getAvatar())) {
            return "";
        }

        String url = user.getAvatar();
        if(!url.contains("http")){
            url = "https:"+ url;
        }
        return url;
    }

    public String getNickName() {
        UserInfoModel user = (UserInfoModel) mHelper.queryFirst(UserInfoModel.class);
        if (user == null) {
            return "";
        }
        return user.getNickname();
    }

    public void setNickName(String name) {
        UserInfoModel user = (UserInfoModel) mHelper.queryFirst(UserInfoModel.class);
        if (user != null) {
            user.setNickname(name);
            mHelper.add(user);
        }
    }

    public String getMemberId() {
        UserInfoModel user = (UserInfoModel) mHelper.queryFirst(UserInfoModel.class);
        if (user == null) {
            return "";
        }
        return user.getMemberId();
    }

    public void setMemberId(String memberId){
        UserInfoModel user = (UserInfoModel) mHelper.queryFirst(UserInfoModel.class);
        if (user != null) {
            user.setMemberId(memberId);
            mHelper.add(user);
        }
    }

    public String getMobile(){
        UserInfoModel user = (UserInfoModel) mHelper.queryFirst(UserInfoModel.class);
        if (user == null) {
            return "";
        }
        return user.getMobile();
    }

    public void setMobile(String mobile){
        UserInfoModel user = (UserInfoModel) mHelper.queryFirst(UserInfoModel.class);
        if (user != null) {
            user.setMobile(mobile);
            mHelper.add(user);
        }
    }

    public String getIntro(){
        UserInfoModel user = (UserInfoModel) mHelper.queryFirst(UserInfoModel.class);
        if (user == null) {
            return "";
        }
        return user.getIntroduction();
    }

    public void setIntro(String intro){
        UserInfoModel user = (UserInfoModel) mHelper.queryFirst(UserInfoModel.class);
        if (user != null) {
            user.setIntroduction(intro);
            mHelper.add(user);
        }
    }



    public void saveUser(UserInfoModel user) {
        if (user != null) mHelper.add(user);
    }

    public void update(UserInfoModel user) {
        if (user != null) {
            user.setMemberId(getToken());
            mHelper.add(user);
        }
    }

    public void setToken(String memberId) {
        mMemberId = memberId;
        SharePreferUtils.getInstance().setString(Constant.USER_MEMBER, memberId);
    }

    public String getToken() {

        if (StringUtil.isNotEmpty(mMemberId)) return mMemberId;

        mMemberId = SharePreferUtils.getInstance().getString(Constant.USER_MEMBER, "");
        if (StringUtil.isNotEmpty(mMemberId)) return mMemberId;

        UserInfoModel user = (UserInfoModel) mHelper.queryFirst(UserInfoModel.class);
        if (user == null) return "";

        return user.getMemberId();
    }

    public void deleteUser() {
        mMemberId = "";
        SharePreferUtils.getInstance().setString(Constant.USER_MEMBER, "");
        SharePreferUtils.getInstance().remove(SharePreferUtils.SIGN_KEY);
        mHelper.delete(UserInfoModel.class);
    }



}
