package com.go.cqh.goodprojects.adapter;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;

import com.go.cqh.goodprojects.R;
import com.go.cqh.goodprojects.entry.HistoryBean;
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

public class HistoryQueryAdapter extends CommonAdapter<HistoryBean.ResultBean> {

    public HistoryQueryAdapter(Context context, int layoutId, List<HistoryBean.ResultBean> datas) {
        super(context, layoutId, datas);
    }

    @Override
    protected void convert(ViewHolder holder, HistoryBean.ResultBean resultsBean, int position) {
        holder.setText(R.id.timeTv, resultsBean.date);
        holder.setText(R.id.titleTv, resultsBean.title);
    }
    @Override
    public void onViewHolderCreated(ViewHolder holder, View itemView) {
        super.onViewHolderCreated(holder, itemView);
        //适配listview
        AutoUtils.autoSize(itemView);
    }
}
