package com.go.cqh.goodprojects.adapter;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.go.cqh.goodprojects.R;
import com.go.cqh.goodprojects.entry.CartoonBean;
import com.go.cqh.goodprojects.entry.ProjectsBean;
import com.go.cqh.goodprojects.utils.ImgLoadUtils;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;
import com.zhy.autolayout.utils.AutoUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by caoqianghui on 2016/11/17.
 */

public class CartoonAdapter extends CommonAdapter<CartoonBean.ShowapiResBodyBean.PagebeanBean.ContentlistBean> {

    public CartoonAdapter(Context context, int layoutId, List<CartoonBean.ShowapiResBodyBean.PagebeanBean.ContentlistBean> datas) {
        super(context, layoutId, datas);
    }

    @Override
    protected void convert(ViewHolder holder, CartoonBean.ShowapiResBodyBean.PagebeanBean.ContentlistBean resultsBean, int position) {
        holder.setText(R.id.textView, resultsBean.title);
    }
    @Override
    public void onViewHolderCreated(ViewHolder holder, View itemView) {
        super.onViewHolderCreated(holder, itemView);
        //适配listview
        AutoUtils.autoSize(itemView);
    }
}
