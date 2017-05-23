package com.go.cqh.goodprojects.adapter;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.go.cqh.goodprojects.R;
import com.go.cqh.goodprojects.utils.ImgLoadUtils;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;
import com.zhy.autolayout.utils.AutoUtils;

import java.util.List;

/**
 * Created by caoqianghui on 2016/11/17.
 */

public class QmDetailAdapter extends CommonAdapter<String> {

    public QmDetailAdapter(Context context, int layoutId, List<String> mList) {
        super(context, layoutId, mList);
    }

    @Override
    protected void convert(ViewHolder holder, String s, int position) {
        if (!"".equals(s)) {
            Log.d("-----s--", s);
            ImgLoadUtils.loadWithDef(((ImageView) holder.getView(R.id.qm_img)), s, null, R.mipmap.default_fuli);
        }
    }

    @Override
    public void onViewHolderCreated(ViewHolder holder, View itemView) {
        super.onViewHolderCreated(holder, itemView);
        //适配listview
        AutoUtils.autoSize(itemView);
    }
}
