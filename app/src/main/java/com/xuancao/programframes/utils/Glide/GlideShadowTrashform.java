package com.xuancao.programframes.utils.Glide;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;

import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool;
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation;

public class GlideShadowTrashform extends BitmapTransformation {

    float mPadding = 6.0f;

    public GlideShadowTrashform(Context context) {
        super(context);
    }

    @Override
    protected Bitmap transform(BitmapPool pool, Bitmap toTransform, int outWidth, int outHeight) {
        return toTransform;
    }

    private Bitmap addShadow(BitmapPool pool, Bitmap source) {
        if (source == null) return null;

        Bitmap result = pool.get(source.getWidth(), source.getHeight(), Bitmap.Config.RGB_565);
        if (result == null) {
            result = Bitmap.createBitmap(source.getWidth(), source.getHeight(), Bitmap.Config.RGB_565);
        }
//        Palette.Swatch mSwatch = Palette.from(result).generate().getDominantSwatch();
//        int rgb = Color.parseColor("#8D8D8D");
//        if (null != mSwatch) {
//            rgb = mSwatch.getRgb();
//        }
        Canvas canvas = new Canvas(result);
        Paint paint = new Paint();
        paint.setShadowLayer(6,0,0, Color.GRAY);
        paint.setShader(new BitmapShader(source, BitmapShader.TileMode.CLAMP, BitmapShader.TileMode.CLAMP));
        paint.setAntiAlias(true);
        RectF rectF = new RectF(mPadding, mPadding, source.getWidth() - mPadding, source.getHeight() - mPadding);
        canvas.drawRect(rectF, paint);
        return result;
    }

    private int getDarkerColor(int color) {
        float[] hsv = new float[3];
        Color.colorToHSV(color, hsv);
        hsv[1] = hsv[1] + 0.1f;
        hsv[2] = hsv[2] - 0.1f;
        int darkerColor = Color.HSVToColor(hsv);
        return darkerColor;
    }
    @Override
    public String getId() {
        return getClass().getName();
    }
}
