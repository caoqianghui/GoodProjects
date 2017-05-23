package com.go.cqh.goodprojects.base;


import android.os.Build;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatDelegate;
import android.view.MotionEvent;

import com.go.cqh.goodprojects.R;
import com.go.cqh.goodprojects.utils.StatusBarUtil;
import com.go.cqh.goodprojects.widget.SwipeWindowHelper;
import com.zhy.autolayout.AutoLayoutActivity;

/**
 * Created by caoqianghui on 2016/11/17.
 */

public class BaseActivity extends AutoLayoutActivity {
    private SwipeWindowHelper mSwipeWindowHelper;
    static {
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
    }
    /**
     * 是否支持滑动返回  默认false
     */
    public  boolean flag = false;

    @Override
    public void onCreate(Bundle savedInstanceState, PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
    }

    //状态栏沉浸
    public void setStatusbarColor() {
        /**
         * 设置状态栏颜色
         */
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                StatusBarUtil.setStatusBarColor(this, R.color.maincolor);
            } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                StatusBarUtil.setStatusBarColor(this, R.color.maincolor);
            }
    }

    @Override
    protected void onResume() {
        super.onResume();
        /*暂时先去掉滑动返回的功能*/
        flag = false;
    }

    /**
     * 是否支持滑动返回
     *
     * @return
     */
    protected boolean supportSlideBack() {
        return this.flag;
    }
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if(!supportSlideBack()) {
            return super.dispatchTouchEvent(ev);
        }

        if(mSwipeWindowHelper == null) {
            mSwipeWindowHelper = new SwipeWindowHelper(getWindow());
        }
        return mSwipeWindowHelper.processTouchEvent(ev) || super.dispatchTouchEvent(ev);
    }
}
