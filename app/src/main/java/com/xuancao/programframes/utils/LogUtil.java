package com.xuancao.programframes.utils;


import com.orhanobut.logger.Logger;
import com.xuancao.programframes.BuildConfig;


public class LogUtil {
    public static final boolean IS_ENABLE = BuildConfig.DEBUG;

    public static void i(String message, Object... args) {
        if (IS_ENABLE) {
            Logger.i(message, args);
        }
    }

    public static void d(String message, Object... args) {
        if (IS_ENABLE) {
            Logger.d(message, args);
        }
    }

    public static void e(String message, Object... args) {
        if (IS_ENABLE) {
            Logger.e(message, args);
        }
    }

    public static void e(Throwable throwable, String message, Object... args) {
        if (IS_ENABLE) {
            Logger.e(throwable, message, args);
        }
    }


    public static void v(String message, Object... args) {
        if (IS_ENABLE) {
            Logger.v(message, args);
        }
    }

    public static void w(String message, Object... args) {
        if (IS_ENABLE) {
            Logger.w(message, args);
        }
    }

    public static void json(String json) {
        if ((IS_ENABLE)) {
            Logger.json(json);
        }
    }

    public static void xml(String xml) {
        if ((IS_ENABLE)) {
            Logger.xml(xml);
        }
    }


}
