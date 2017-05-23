package com.go.cqh.goodprojects.adapter;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;

import com.go.cqh.goodprojects.R;
import com.go.cqh.goodprojects.entry.FunBean;
import com.go.cqh.goodprojects.utils.ImgLoadUtils;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;
import com.zhy.autolayout.utils.AutoUtils;

import java.util.List;

/**
 * Created by caoqianghui on 2016/11/17.
 */

public class FunAdapter extends CommonAdapter<FunBean.ResultBean.DataBean> {

    public FunAdapter(Context context, int layoutId, List<FunBean.ResultBean.DataBean> datas) {
        super(context, layoutId, datas);
    }

    @Override
    protected void convert(ViewHolder holder, FunBean.ResultBean.DataBean resultsBean, int position) {
        holder.setText(R.id.fun_title, resultsBean.content);
        if (resultsBean.url != null) {
            ImgLoadUtils.loadWithDef(((ImageView) holder.getView(R.id.fun_img)), resultsBean.url, null,R.mipmap.default_xx);
        }

    }
    @Override
    public void onViewHolderCreated(ViewHolder holder, View itemView) {
        super.onViewHolderCreated(holder, itemView);
        //适配listview
        AutoUtils.autoSize(itemView);
    }
}
