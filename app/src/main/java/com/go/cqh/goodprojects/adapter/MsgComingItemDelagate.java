package com.go.cqh.goodprojects.adapter;

import android.text.Html;
import android.view.View;
import android.widget.TextView;

import com.go.cqh.goodprojects.R;
import com.go.cqh.goodprojects.entry.AiBean;
import com.go.cqh.goodprojects.view.IMActivity;
import com.zhy.adapter.recyclerview.base.ItemViewDelegate;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.Random;

/**
 * Created by caoqianghui on 2017/3/9.
 */

public class MsgComingItemDelagate implements ItemViewDelegate<AiBean> {


    @Override
    public int getItemViewLayoutId() {
        return R.layout.chat_from;
    }

    @Override
    public boolean isForViewType(AiBean item, int position) {
        return !item.isMyMsg && item.code != 302000 && item.code != 308000;
    }

    @Override
    public void convert(ViewHolder holder, AiBean chatMessage, final int position) {
        if (chatMessage.text != null) {
            holder.setText(R.id.text_left, chatMessage.text);
        }
        /*图片类 航班类 等*/
        if (chatMessage.text != null && chatMessage.url != null) {
            TextView view = holder.getView(R.id.text_left);
            //String s1 = "<a href="+chatMessage.url+">"+6666+"</a>";
            String s = "<font color=\"#323232\">" + chatMessage.text + "</font>" + "<br/>" + "<font color=\"#009688\" font-size:10px>" + chatMessage.url + "</font>";
            view.setText(Html.fromHtml(s));
        }
        /*菜谱类*/
        //if (chatMessage.list != null && chatMessage.list.size() > 0) {
        //    Random random = new Random();
        //    int i = random.nextInt(chatMessage.list.size());
        //    final String detailurl = chatMessage.list.get(i).detailurl;
        //    TextView view = holder.getView(R.id.text_left);
        //    view.setOnClickListener(new View.OnClickListener() {
        //        @Override
        //        public void onClick(View view) {
        //            new IMActivity().goBrower(detailurl);
        //        }
        //    });
        //    //String s1 = "<a href="+chatMessage.url+">"+6666+"</a>";
        //    String s = "<font color=\"#323232\">" + chatMessage.text + "</font>" + "<br/>" + "<font color=\"#009688\" font-size:10px>" + detailurl + "</font>";
        //    view.setText(Html.fromHtml(s));
        //}
    }

}
