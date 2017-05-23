package com.go.cqh.goodprojects.adapter;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;

import com.go.cqh.goodprojects.R;
import com.go.cqh.goodprojects.entry.AiBean;
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

public class ImNewItemAdapter extends CommonAdapter<AiBean.ListBean> {

    public ImNewItemAdapter(Context context, int layoutId, List<AiBean.ListBean> datas) {
        super(context, layoutId, datas);
    }

    @Override
    protected void convert(ViewHolder holder, AiBean.ListBean resultsBean, int position) {
        if (resultsBean.article != null) {
            holder.setText(R.id.textView, resultsBean.article);
        }
        if (resultsBean.name != null) {
            holder.setText(R.id.textView, resultsBean.name);
        }
        if (resultsBean.article != null) {
        }
        if (resultsBean.icon != null && !resultsBean.icon.equals("")) {
            ImgLoadUtils.load(((ImageView) holder.getView(R.id.img)), resultsBean.icon, null);
        }

    }

    @Override
    public void onViewHolderCreated(ViewHolder holder, View itemView) {
        super.onViewHolderCreated(holder, itemView);
        //适配listview
        AutoUtils.autoSize(itemView);
    }
}
