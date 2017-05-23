package com.go.cqh.goodprojects.utils;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;

import com.go.cqh.goodprojects.base.BaseApplication;

public final class UIUtil {
    public RecyclerView recyclerView;

    public RecyclerView getRecyclerView() {
        return recyclerView;
    }

    public static Context getContext() {
        return BaseApplication.getInstance();
    }

    public void setRecyclerView(RecyclerView recyclerView) {
        this.recyclerView = recyclerView;
    }

    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    public static int dip2px(Context context, double dpValue) {
        float density = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * density + 0.5);
    }

    /**
     * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
     */
    public static int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    public static int[] getScreenSize(Context context) {
        DisplayMetrics dm = context.getResources().getDisplayMetrics();
        int[] size = new int[2];
        size[0] = dm.widthPixels;
        size[1] = dm.heightPixels;
        return size;
    }

    public static void goToTop(RecyclerView recyclerView) {
        recyclerView.stopScroll();
        LinearLayoutManager layoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
        int firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition();
        layoutManager.setSmoothScrollbarEnabled(true);
        if (firstVisibleItemPosition > 10) {
            layoutManager.scrollToPositionWithOffset(10, 0);
        }
        recyclerView.smoothScrollToPosition(0);

    }

    public interface GoUpListener {
        void goUp();
    }
}