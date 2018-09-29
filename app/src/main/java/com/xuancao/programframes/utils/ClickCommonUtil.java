package com.xuancao.programframes.utils;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.v4.content.ContextCompat;

public class ClickCommonUtil {
    private static long FIRST_TIME = 0;

    public static boolean isFastDoubleClick() {
        long currentTime = System.currentTimeMillis();
        long mTime_step = currentTime - FIRST_TIME;
        if (mTime_step > 0 && mTime_step < 500) {
            return true;
        }
        FIRST_TIME = currentTime;
        return false;
    }


    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public static Drawable getDrawableResource(Context ctx, int res) {
        try {
            Drawable drawable = ContextCompat.getDrawable(ctx, res);
            drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
            return drawable;
        } catch (NoSuchMethodError noSuchMethodError) {
        }
        return null;
    }

}
