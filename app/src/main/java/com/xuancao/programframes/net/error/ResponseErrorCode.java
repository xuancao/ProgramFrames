package com.xuancao.programframes.net.error;

public interface ResponseErrorCode {
    int SUCCESS = 200;       //框架请求成功code

    int SUCCESS_RESPONSE = 0; //服务器返回数据成功code

    int ERROR_NO_RETURN_DATA = 204;//请求成功，无返回数据

    int ERROR_NOT_NET = 9999;  //网络未连接

    int ERROR_LOGIN_ERROR = 10010; //登录失败

    int ERROR_USER_NO_EXIST = 10009; //用户不存在

    int ERROR_UNKNOWN= 404;
}
