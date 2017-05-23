package com.go.cqh.goodprojects.adapter;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;

import com.go.cqh.goodprojects.R;
import com.go.cqh.goodprojects.entry.NewsBean;
import com.go.cqh.goodprojects.utils.ImgLoadUtils;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;
import com.zhy.autolayout.utils.AutoUtils;

import java.util.List;

/**
 * Created by caoqianghui on 2017/2/6.
 */

public class NewsAdapter extends CommonAdapter<NewsBean.ResultBean.DataBean> {
    public NewsAdapter(Context context, int layoutId, List<NewsBean.ResultBean.DataBean> datas) {
        super(context, layoutId, datas);
    }

    @Override
    protected void convert(ViewHolder holder, NewsBean.ResultBean.DataBean dataBean, int position) {
        holder.setText(R.id.textView, dataBean.title);
        holder.setText(R.id.people,  dataBean.category);
        holder.setText(R.id.type, dataBean.author_name);
        holder.setText(R.id.timeItem, dataBean.date);
        ImgLoadUtils.load(((ImageView) holder.getView(R.id.img)), dataBean.thumbnail_pic_s, null);
    }
    @Override
    public void onViewHolderCreated(ViewHolder holder, View itemView) {
        super.onViewHolderCreated(holder, itemView);
        //适配listview
        AutoUtils.autoSize(itemView);
    }
}
