package com.go.cqh.goodprojects.base;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.go.cqh.goodprojects.R;
import com.go.cqh.goodprojects.view.MainActivity;
import com.go.cqh.goodprojects.widget.DropDownWarning;


/**
 * Created by caoqianghui on 2016/11/17.
 */

public class BaseFragment extends Fragment {
    private static DropDownWarning dropDownWarning;
    private boolean isVisible;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    //下落消息
    public void showDropView(String msg) {
        if (dropDownWarning == null) {
            dropDownWarning = new DropDownWarning.Builder(getActivity(), MainActivity.relativeLayout)
                    .message(msg)
                    .backgroundColor(ContextCompat.getColor(getActivity(), R.color.warn_down))
                    .foregroundColor(ContextCompat.getColor(getActivity(), R.color.colorWhite))
                    .build();
        } else {
            if (dropDownWarning.isShown()) {
                //dropDownWarning.hide();
            }
        }
        dropDownWarning.show();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                dropDownWarning.hide();
            }
        }, 2000);
    }
    /**
     * viewpager + Fragment 解决预加载问题
     * @param isVisibleToUser
     */
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if(getUserVisibleHint()) {
            isVisible = true;
            onVisible();
        } else {
            isVisible = false;
            onInvisible();
        }
    }

    /**
     * fragment对用户可见时
     */
    protected void onVisible(){
        //加载数据
        lazyLoad();
    }
    protected void lazyLoad(){}

    /**
     * fragment对用户不可见时
     */
    protected void onInvisible(){}
}
