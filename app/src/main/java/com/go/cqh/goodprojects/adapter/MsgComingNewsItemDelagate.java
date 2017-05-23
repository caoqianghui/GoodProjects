package com.go.cqh.goodprojects.adapter;

import android.content.Context;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.go.cqh.goodprojects.R;
import com.go.cqh.goodprojects.entry.AiBean;
import com.go.cqh.goodprojects.utils.ImgLoadUtils;
import com.go.cqh.goodprojects.view.IMActivity;
import com.zhy.adapter.recyclerview.MultiItemTypeAdapter;
import com.zhy.adapter.recyclerview.base.ItemViewDelegate;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by caoqianghui on 2017/3/9.
 */

public class MsgComingNewsItemDelagate implements ItemViewDelegate<AiBean> {


    private Context context;

    @Override
    public int getItemViewLayoutId() {
        return R.layout.chat_from_news;
    }

    @Override
    public boolean isForViewType(AiBean item, int position) {
        return item.code == 302000 || item.code == 308000;
    }

    @Override
    public void convert(ViewHolder holder, AiBean chatMessage, final int position) {
        final RecyclerView recyclerView = holder.getView(R.id.rv_from);
        context = recyclerView.getContext();
        final List<AiBean.ListBean> mList = chatMessage.list;
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        final List<AiBean.ListBean> mList2 = new ArrayList<>();
        if (chatMessage.list.get(0).icon.equals("")) {
            holder.getView(R.id.news_first).setVisibility(View.GONE);
        } else {
            AiBean.ListBean first = mList.get(0);
            ImgLoadUtils.load((ImageView) holder.getView(R.id.news_first_img), first.icon, null);
            if (chatMessage.code == 302000) {
                holder.setText(R.id.news_first_tv, first.article);
            } else {
                holder.setText(R.id.news_first_tv, first.name);
            }
            for (int i = 1; i < mList.size(); i++) {
                mList2.add(mList.get(i));
            }
            holder.getView(R.id.news_first).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    new IMActivity().goBrower(mList.get(0).detailurl);
                }
            });
        }

        ImNewItemAdapter adapter ;
        if (chatMessage.list.get(0).icon.equals("")) {
            adapter = new ImNewItemAdapter(context, R.layout.item_im_news, mList);
            adapter.setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(View view, RecyclerView.ViewHolder holder, int position) {
                    new IMActivity().goBrower(mList.get(position).detailurl);
                }

                @Override
                public boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, int position) {
                    return false;
                }
            });
        } else {
            adapter = new ImNewItemAdapter(context, R.layout.item_im_news, mList2);
            adapter.setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(View view, RecyclerView.ViewHolder holder, int position) {
                    new IMActivity().goBrower(mList2.get(position).detailurl);
                }

                @Override
                public boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, int position) {
                    return false;
                }
            });
        }
        recyclerView.setAdapter(adapter);
    }
}
