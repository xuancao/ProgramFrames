package com.xuancao.programframes.utils.Glide;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.FitCenter;
import com.bumptech.glide.request.target.Target;
import com.xuancao.programframes.BaseApp;
import com.xuancao.programframes.R;
import com.xuancao.programframes.utils.FileUtils;

import java.util.concurrent.ExecutionException;

public class ImageLoader {
    private final static int DEFAULT_ICON_RESID = R.mipmap.default_dynamic_pic;
    public static void loadImage(Uri uri, ImageView imageView) {
        Glide.with(imageView.getContext()).loadFromMediaStore(uri).centerCrop().placeholder(DEFAULT_ICON_RESID).dontAnimate().diskCacheStrategy(DiskCacheStrategy.ALL).into(imageView);
    }

    public static void loadImage(Integer resourceId, ImageView imageView) {
        Glide.with(imageView.getContext()).load(resourceId).centerCrop().placeholder(DEFAULT_ICON_RESID).dontAnimate().diskCacheStrategy(DiskCacheStrategy.ALL).into(imageView);
    }

    public static void loadImage(Integer resourceId, ImageView imageView, int radius) {
        Glide.with(imageView.getContext()).load(resourceId).transform(new FitCenter(imageView.getContext())
                ,new GlideRoundTransform(imageView.getContext(),radius)).placeholder(DEFAULT_ICON_RESID).dontAnimate().diskCacheStrategy(DiskCacheStrategy.ALL).into(imageView);
    }

    public static void loadImage(Integer resourceId, ImageView imageView, int radius, int default_icon) {
        Glide.with(imageView.getContext()).load(resourceId).transform(new FitCenter(imageView.getContext())
                ,new GlideRoundTransform(imageView.getContext(),radius)).placeholder(default_icon).dontAnimate().diskCacheStrategy(DiskCacheStrategy.ALL).into(imageView);
    }

    public static void loadImage(String url, ImageView imageView) {
        loadImage(url, imageView, DEFAULT_ICON_RESID);
    }

    public static void loadImage(String url, ImageView imageView, int default_icon) {
        Glide.with(imageView.getContext()).load(url.trim()).asBitmap().centerCrop().placeholder(default_icon).dontAnimate().diskCacheStrategy(DiskCacheStrategy.ALL).into(imageView);
    }

    public static void loadRoundImage(String url, ImageView imageView, int radius) {
        imageView.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        Glide.with(imageView.getContext()).load(url).centerCrop()
                .placeholder(DEFAULT_ICON_RESID).dontAnimate().diskCacheStrategy(DiskCacheStrategy.ALL).into(imageView);
    }

    public static void loadShadowImage(String url, ImageView imageView) {
        imageView.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        Glide.with(imageView.getContext()).load(url)
                .transform(new FitCenter(imageView.getContext()),new GlideShadowTrashform(imageView.getContext()))
                .placeholder(DEFAULT_ICON_RESID).dontAnimate().diskCacheStrategy(DiskCacheStrategy.ALL).into(imageView);
    }

    public static void loadRoundImage(int resId, ImageView imageView, int radius) {
        imageView.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        Glide.with(imageView.getContext()).load(resId).centerCrop()
                .placeholder(DEFAULT_ICON_RESID).dontAnimate().diskCacheStrategy(DiskCacheStrategy.ALL).into(imageView);
    }


    public static void loadRoundImage(String url, ImageView imageView) {
        Glide.with(imageView.getContext()).load(url).transform(new CenterCrop(imageView.getContext())
                ,new GlideRoundTransform(imageView.getContext(),5)).placeholder(DEFAULT_ICON_RESID).dontAnimate().diskCacheStrategy(DiskCacheStrategy.ALL).into(imageView);
    }

    public static void loadRoundImage(Fragment fragment, String url, ImageView imageView, int radius) {
        loadRoundImage(fragment, url, imageView, DEFAULT_ICON_RESID, radius);
    }

    public static void loadBottomLeftRightRoundImage(String url, ImageView imageView, int radius) {
        Glide.with(imageView.getContext()).load(url).transform(new CenterCrop(imageView.getContext())
                ,new GlideTopRoundTransform(imageView.getContext(),radius))
                .placeholder(DEFAULT_ICON_RESID).dontAnimate().diskCacheStrategy(DiskCacheStrategy.ALL).into(imageView);
    }
    public static void loadRoundImage(Fragment fragment, String url, ImageView imageView, int default_icon, int radius) {
        Glide.with(fragment).load(url).transform(new GlideRoundTransform(imageView.getContext(),radius))
                .placeholder(default_icon).dontAnimate().diskCacheStrategy(DiskCacheStrategy.ALL).into(imageView);
    }

    public static String userImgUrl(String url) {
        if(!url.contains("http")){
            url = "https:"+ url;
        }
        return url;
    }

    public static Bitmap getImage(Context context, String url) {
        try {
            return Glide.with(context).load(url).asBitmap().into(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL).get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Bitmap getBitmap(int resoureid) {
        try {
            return Glide.with(BaseApp.getAppContext()).load(resoureid).asBitmap().into(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL).get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return null;
    }

    /** 设置中显示缓存大小 */
    public static String getCacheSize(Context context) {
        try {
            long sizes = FileUtils.getFileSize(Glide.getPhotoCacheDir(context));
            //TODO 减小cache 大小
//            sizes = sizes / 10;
            return FileUtils.FormetFileSize(sizes);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "0.0MB";
    }

    /** 用于清除缓存调用 */
    public static boolean cleanCache(Context context) {
        return FileUtils.deleteDir(Glide.getPhotoCacheDir(context));
    }
}
