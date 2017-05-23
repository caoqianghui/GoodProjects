package com.go.cqh.goodprojects.adapter;

import android.content.Context;
import android.view.View;

import com.go.cqh.goodprojects.entry.AiBean;
import com.zhy.adapter.recyclerview.MultiItemTypeAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;
import com.zhy.autolayout.utils.AutoUtils;

import java.util.List;

/**
 * Created by caoqianghui on 2017/3/9.
 */

public class AiAdapter extends MultiItemTypeAdapter<AiBean> {


    public AiAdapter(Context context, List<AiBean> datas) {
        super(context, datas);
        addItemViewDelegate(new MsgSendItemDelagate());
        addItemViewDelegate(new MsgComingItemDelagate());
        addItemViewDelegate(new MsgComingNewsItemDelagate());
    }

    @Override
    public void onViewHolderCreated(ViewHolder holder, View itemView) {
        super.onViewHolderCreated(holder, itemView);
        //适配listview
        AutoUtils.autoSize(itemView);
    }
}
