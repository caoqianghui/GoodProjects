package com.go.cqh.goodprojects.adapter;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;

import com.go.cqh.goodprojects.R;
import com.go.cqh.goodprojects.entry.QmBean;
import com.go.cqh.goodprojects.utils.ImgLoadUtils;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;
import com.zhy.autolayout.utils.AutoUtils;

import java.util.List;

/**
 * Created by caoqianghui on 2016/11/17.
 */

public class QmAdapter extends CommonAdapter<QmBean.DataBean> {

    public QmAdapter(Context context, int layoutId, List<QmBean.DataBean> datas) {
        super(context, layoutId, datas);
    }

    @Override
    protected void convert(ViewHolder holder, QmBean.DataBean resultsBean, int position) {
        if (resultsBean.catname != null) {
            holder.setText(R.id.textView, resultsBean.catname);
        } else {
            holder.setText(R.id.textView, resultsBean.title);
        }
        if (resultsBean.author != null) {
            holder.setText(R.id.people, resultsBean.author);
        }
        if (resultsBean.updatetime != null) {
            holder.setText(R.id.timeItem, resultsBean.updatetime);
        }
        if (resultsBean.sum != null) {
            holder.setText(R.id.timeItem, "浏览量：" + resultsBean.sum);
        }
        if (resultsBean.inputtime != null) {
            holder.setText(R.id.timeItem, resultsBean.inputtime);
        }
        if (resultsBean.parent_catname != null) {
            holder.setText(R.id.type, resultsBean.parent_catname);
        }
        if (resultsBean.views != null) {
            holder.setText(R.id.type, "浏览量：" + resultsBean.views);
        }
        if (resultsBean.thumb != null) {
            holder.getView(R.id.img).setVisibility(View.VISIBLE);
            ImgLoadUtils.load(((ImageView) holder.getView(R.id.img)), resultsBean.thumb, null);
        }
    }

    @Override
    public void onViewHolderCreated(ViewHolder holder, View itemView) {
        super.onViewHolderCreated(holder, itemView);
        //适配listview
        AutoUtils.autoSize(itemView);
    }
}
