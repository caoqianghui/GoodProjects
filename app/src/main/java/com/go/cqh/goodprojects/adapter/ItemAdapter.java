package com.go.cqh.goodprojects.adapter;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.go.cqh.goodprojects.R;
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

public class ItemAdapter extends CommonAdapter<ProjectsBean.ResultsBean> {

    public ItemAdapter(Context context, int layoutId, List<ProjectsBean.ResultsBean> datas) {
        super(context, layoutId, datas);
    }

    @Override
    protected void convert(ViewHolder holder, ProjectsBean.ResultsBean resultsBean, int position) {
        holder.setText(R.id.textView, resultsBean.desc);
        holder.setText(R.id.people,  resultsBean.who);
        holder.setText(R.id.type, resultsBean.type);
        if (resultsBean.images != null && resultsBean.images.size() > 0) {
            holder.getView(R.id.img).setVisibility(View.VISIBLE);
            ImgLoadUtils.load(((ImageView) holder.getView(R.id.img)), resultsBean.images.get(0), null);
        } else if (resultsBean.type.equals("福利")) {
            ImgLoadUtils.load(((ImageView) holder.getView(R.id.img)), resultsBean.url,null);
        } else {
            holder.getView(R.id.img).setVisibility(View.GONE);
        }
        if (resultsBean.publishedAt.contains("T") && resultsBean.publishedAt.contains("Z")) {
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS Z");//注意格式化的表达式
            try {
                Date d = format.parse(resultsBean.publishedAt.replace("Z", " UTC"));//注意是空格+UTC
                holder.setText(R.id.timeItem, new SimpleDateFormat("yyyy/MM/dd").format(d));
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }

    }
    @Override
    public void onViewHolderCreated(com.zhy.adapter.recyclerview.base.ViewHolder holder, View itemView) {
        super.onViewHolderCreated(holder, itemView);
        //适配listview
        AutoUtils.autoSize(itemView);
    }
}
