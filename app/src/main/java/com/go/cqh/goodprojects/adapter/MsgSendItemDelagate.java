package com.go.cqh.goodprojects.adapter;

import android.text.Html;
import android.view.View;
import android.widget.TextView;

import com.go.cqh.goodprojects.R;
import com.go.cqh.goodprojects.entry.AiBean;
import com.go.cqh.goodprojects.view.IMActivity;
import com.zhy.adapter.recyclerview.base.ItemViewDelegate;
import com.zhy.adapter.recyclerview.base.ViewHolder;

/**
 * Created by caoqianghui on 2017/3/9.
 */

public class MsgSendItemDelagate implements ItemViewDelegate<AiBean> {

    private String url;

    @Override
    public int getItemViewLayoutId() {
        return R.layout.chat_send;
    }

    @Override
    public boolean isForViewType(AiBean item, int position) {
        return item.isMyMsg;
    }

    @Override
    public void convert(ViewHolder holder, AiBean chatMessage, int position) {
        if (chatMessage.text != null) {
            holder.setText(R.id.text_right, chatMessage.text);
        }
        if (chatMessage.text != null && chatMessage.url != null) {
            TextView view = holder.getView(R.id.text_right);
            //String s1 = "<a href="+chatMessage.url+">"+6666+"</a>";
            String s = "<font color=\"#323232\">" + chatMessage.text + "</font>" + "<br/>" + "<font color=\"#009688\" font-size:10px>" + chatMessage.url + "</font>";
            view.setText(Html.fromHtml(s));
        }
    }
}
