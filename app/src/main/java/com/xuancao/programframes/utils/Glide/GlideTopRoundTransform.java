package com.xuancao.programframes.utils.Glide;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;

import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool;
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation;

public class GlideTopRoundTransform extends BitmapTransformation {
    private static float radius = 0f;

    public GlideTopRoundTransform(Context context) {
        this(context, 4);
    }

    public GlideTopRoundTransform(Context context, int dp) {
        super(context);
        radius = Resources.getSystem().getDisplayMetrics().density * dp;
    }

    @Override
    protected Bitmap transform(BitmapPool pool, Bitmap toTransform, int outWidth, int outHeight) {
        return roundCrop(pool, toTransform);
    }

    private Bitmap roundCrop(BitmapPool pool, Bitmap source) {
        if (source == null) return null;
        Bitmap result = pool.get(source.getWidth(), source.getHeight(), Bitmap.Config.ARGB_8888);
        if (result == null) {
            result = Bitmap.createBitmap(source.getWidth(), source.getHeight(), Bitmap.Config.ARGB_8888);
        }
        Canvas canvas = new Canvas(result);
        Paint paint = new Paint();
        paint.setShader(new BitmapShader(source, BitmapShader.TileMode.CLAMP, BitmapShader.TileMode.CLAMP));
        paint.setAntiAlias(true);
        RectF rectF = new RectF(0f, 0f, source.getWidth(), source.getHeight());
        canvas.drawRoundRect(rectF, radius, radius, paint);

        canvas.drawRect(0, rectF.bottom - radius, radius, rectF.bottom, paint);
        canvas.drawRect(rectF.right - radius, rectF.bottom - radius, rectF.right, rectF.bottom, paint);
//            canvas.drawRect(0, 0, radius, radius, paint);
//            canvas.drawRect(rectF.right - radius, 0, rectF.right, radius, paint);

//        int notRoundedCorners = corners ^ CORNER_ALL;
        //哪个角不是圆角我再把你用矩形画出来
//        if ((notRoundedCorners & CORNER_TOP_LEFT) != 0) {
//            canvas.drawRect(0, 0, radius, radius, paint);
//        }
//        if ((notRoundedCorners & CORNER_TOP_RIGHT) != 0) {
//            canvas.drawRect(rectF.right - radius, 0, rectF.right, radius, paint);
//        }
//        if ((notRoundedCorners & CORNER_BOTTOM_LEFT) != 0) {
//            canvas.drawRect(0, rectF.bottom - radius, radius, rectF.bottom, paint);
//        }
//        if ((notRoundedCorners & CORNER_BOTTOM_RIGHT) != 0) {
//            canvas.drawRect(rectF.right - radius, rectF.bottom - radius, rectF.right, rectF.bottom, paint);
//        }


        return result;
    }

    @Override
    public String getId() {
        return getClass().getName() + Math.round(radius);
    }
}
