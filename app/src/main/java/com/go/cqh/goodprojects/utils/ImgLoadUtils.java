package com.go.cqh.goodprojects.utils;

import android.graphics.Bitmap;
import android.graphics.PointF;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.target.SizeReadyCallback;
import com.bumptech.glide.request.target.Target;
import com.davemorrissey.labs.subscaleview.ImageSource;
import com.davemorrissey.labs.subscaleview.ImageViewState;
import com.davemorrissey.labs.subscaleview.SubsamplingScaleImageView;
import com.go.cqh.goodprojects.R;

import java.io.File;

/**
 * Created by caoqianghui on 2016/11/19.
 */

public class ImgLoadUtils {

    /**
     * 提供一个加载完成的接口
     */
    public interface ImgLoadListener {
        /**
         * 加载完成
         *
         * @param width  宽
         * @param height 高
         */
        void isLoaded(int width, int height);
    }

    /**
     * 加载网络或者本地图片
     *
     * @param imageView
     * @param url       图片地址
     * @param listener  加载完成的监听
     */
    public static void load(ImageView imageView, String url, final ImgLoadListener listener) {
        Glide.with(imageView.getContext()).load(url).dontAnimate().crossFade().fitCenter().listener(new RequestListener<String, GlideDrawable>() {
            @Override
            public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
                return false;
            }

            @Override
            public boolean onResourceReady(final GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                if (listener != null) {
                    target.getSize(new SizeReadyCallback() {
                        @Override
                        public void onSizeReady(int width, int height) {
                            listener.isLoaded(width, height);
                        }
                    });
                }
                return false;
            }
        }).error(R.mipmap.fault).diskCacheStrategy(DiskCacheStrategy.SOURCE).into(imageView);
    }

    /**
     * 加载网络或者本地图片
     *
     * @param imageView
     * @param url       图片地址
     * @param listener  加载完成的监听
     */
    public static void loadWithDef(ImageView imageView, String url, final ImgLoadListener listener, @NonNull int defaultImg) {
        Glide.with(imageView.getContext()).load(url).dontAnimate().crossFade().fitCenter().listener(new RequestListener<String, GlideDrawable>() {
            @Override
            public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
                return false;
            }

            @Override
            public boolean onResourceReady(final GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                if (listener != null) {
                    target.getSize(new SizeReadyCallback() {
                        @Override
                        public void onSizeReady(int width, int height) {
                            listener.isLoaded(width, height);
                        }
                    });
                }
                return false;
            }
        }).placeholder(defaultImg).error(R.mipmap.default_fuli_error).diskCacheStrategy(DiskCacheStrategy.SOURCE).into(imageView);
    }

    public static void loadAsBitmap(ImageView imageView, String url, final ImgLoadListener listener) {
        Glide.with(imageView.getContext()).load(url).asBitmap().dontAnimate().fitCenter().listener(new RequestListener<String, Bitmap>() {
            @Override
            public boolean onException(Exception e, String model, Target<Bitmap> target, boolean isFirstResource) {
                return false;
            }

            @Override
            public boolean onResourceReady(Bitmap resource, String model, Target<Bitmap> target, boolean isFromMemoryCache, boolean isFirstResource) {
                if (listener != null) {
                    listener.isLoaded(resource.getWidth(), resource.getHeight());
                }
                return false;
            }
        }).diskCacheStrategy(DiskCacheStrategy.SOURCE).skipMemoryCache(false).into(imageView);
    }

    /**
     * 高斯模糊
     *
     * @param imageView
     * @param id
     */
    public static void loadBlur(ImageView imageView, int id) {
        Glide.with(imageView.getContext()).load(id).dontAnimate().transform(new FastBlur(imageView.getContext(), 20)).into(imageView);
    }

    public static void loadBigImg(final SubsamplingScaleImageView imageView, String url) {
        //下载图片保存到本地
        Glide.with(imageView.getContext())
                .load(url).downloadOnly(new SimpleTarget<File>() {
            @Override
            public void onResourceReady(File resource, GlideAnimation<? super File> glideAnimation) {
                // 将保存的图片地址给SubsamplingScaleImageView,这里注意设置ImageViewState设置初始显示比例
                imageView.setImage(ImageSource.uri(Uri.fromFile(resource)), new ImageViewState(2.0F, new PointF(0, 0), 0));
            }
        });
    }

    public static void loadBlurFromUrl(ImageView imageView, String url) {
        Glide.with(imageView.getContext()).load(url).asBitmap().dontAnimate().transform(new FastBlur(imageView.getContext(), 30)).into(imageView);
    }
}
