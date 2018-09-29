package com.xuancao.programframes.Rxbus;

/**
 * Created by xuancao on 2017/4/28.
 */

public class MsgEvent {

    public String type;
    public Object object;

    public MsgEvent(String type) {
        this.type = type;
    }

    public MsgEvent(Object object) {
        this.object = object;
    }

    public MsgEvent(String type, Object object) {
        this.type = type;
        this.object = object;
    }

    public Object getObject() {
        return object;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }

    /**
     * 网络状态监听
     */
    public final static String NETWORK_STATE_EVENT = "network_state_event";


    /**
     * 登录成功消息
     */
    public final static String LOGIN_SUCCESS_EVENT = "login_success_event";

    /**
     * 退出登录成功消息
     */
    public final static String LOGOUT_SUCCESS_EVENT = "logout_success_event";

    /** 更新用户信息 */
    public final static String UPDATE_USER_INFO_EVENT = "update_user_info_event";


}
