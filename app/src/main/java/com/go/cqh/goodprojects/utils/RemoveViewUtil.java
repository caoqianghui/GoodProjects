package com.go.cqh.goodprojects.utils;

import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

/**
 * Created by caoqianghui on 2016/11/22.
 */

public class RemoveViewUtil {
    public static void removeParent(View v){
        /**
         *先找到父控件，再通过父控件去移除子控件
         */
        ViewParent parent = v.getParent();

        /**
         *所有控件都有父控件，父控件一般就是ViewGroup
         */
        if(parent instanceof ViewGroup){
            ViewGroup group=(ViewGroup) parent;
            group.removeView(v);
        }
    }
}
