package com.xuancao.programframes.net.error;

import com.xuancao.programframes.net.DataManager;
import com.xuancao.programframes.net.callback.Result;
import com.xuancao.programframes.utils.LogUtil;
import com.xuancao.programframes.utils.ToastUtil;

public class ResponseErrorParsing {

    private DataManager mDataManager;

    public int parsingError(Result error) {

        switch (error.getRetcode()) {
            case ResponseErrorCode.ERROR_NOT_NET:
                ToastUtil.show(error.getMessage());
                break;
                case ResponseErrorCode.ERROR_USER_NO_EXIST:
                case ResponseErrorCode.ERROR_LOGIN_ERROR:
                    ToastUtil.show(error.getMessage());
                    break;

            default:
                LogUtil.e("default error : "+error.getMessage());
                break;
        }

        return error.getRetcode();
    }

    public void setDataManager(DataManager manager) {
        mDataManager = manager;
    }
}