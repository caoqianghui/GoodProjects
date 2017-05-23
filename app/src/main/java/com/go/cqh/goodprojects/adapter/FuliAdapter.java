package com.go.cqh.goodprojects.adapter;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;

import com.go.cqh.goodprojects.R;
import com.go.cqh.goodprojects.entry.SexImgBean;
import com.go.cqh.goodprojects.utils.ImgLoadUtils;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;
import com.zhy.autolayout.utils.AutoUtils;

import java.util.List;
import java.util.Map;

/**
 * Created by caoqianghui on 2016/11/17.
 */

public class FuliAdapter extends CommonAdapter<SexImgBean.ShowapiResBodyBean.NewslistBean> {
    /**
     * 用于存储瀑布流中已加载过的图片的尺寸 绑定图片url
     */
    private Map<String, int[]> map;

    public FuliAdapter(Context context, int layoutId, List<SexImgBean.ShowapiResBodyBean.NewslistBean> datas) {
        super(context, layoutId, datas);
    }

    @Override
    protected void convert(final ViewHolder holder, final SexImgBean.ShowapiResBodyBean.NewslistBean resultsBean, int position) {
        //holder.setText(R.id.fuli_desc, resultsBean.desc);
        //确保只使用一个map
        //if (map == null) {
        //    map = new HashMap<>();
        //}
        //final int[] size = new int[2];
        //if (map.containsKey(resultsBean.url)) {
        //    int[] ints = map.get(resultsBean.url);
        //    ViewGroup.LayoutParams layoutParams = holder.getView(R.id.fuli_img).getLayoutParams();
        //    layoutParams.width = ints[0];
        //    layoutParams.height = ints[1];
        //    holder.getView(R.id.fuli_img).setLayoutParams(layoutParams);
        ImgLoadUtils.loadWithDef(((ImageView) holder.getView(R.id.fuli_img)), resultsBean.picUrl, null, R.mipmap.default_fuli);
        //} else {
        //    ImgLoadUtils.loadAsBitmap(((ImageView) holder.getView(R.id.fuli_img)), resultsBean.url , new ImgLoadUtils.ImgLoadListener() {
        //        @Override
        //        public void isLoaded(int width, int height) {
        //            /**
        //             * 已经加载出来的图片，将图片尺寸进行保存，复用时就不用再次计算大小，直接拿这个用，防止瀑布流图片跳动
        //             */
        //            size[0] = width;
        //            size[1] = height;
        //            map.put(resultsBean.url, size);
        //        }
        //    });
        //}
        //map.put(resultsBean.url, size);
        //if (resultsBean.publishedAt.contains("T") && resultsBean.publishedAt.contains("Z")) {
        //    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS Z");//注意格式化的表达式
        //    try {
        //        Date d = format.parse(resultsBean.publishedAt.replace("Z", " UTC"));//注意是空格+UTC
        //        holder.setText(R.id.fuli_time, new SimpleDateFormat("yyyy/MM/dd").format(d));
        //    } catch (ParseException e) {
        //        e.printStackTrace();
        //    }
        //holder.setText(R.id.people,  resultsBean.who);
        //holder.setText(R.id.type, resultsBean.type);
        //if (resultsBean.images != null && resultsBean.images.size() > 0) {
        //    holder.getView(R.id.img).setVisibility(View.VISIBLE);
        //    ImgLoadUtils.load(((ImageView) holder.getView(R.id.img)), resultsBean.images.get(0), new ImgLoadUtils.ImgLoadListener() {
        //        @Override
        //        public void isLoaded() {
        //
        //        }
        //    });
        //} else if (resultsBean.type.equals("福利")) {
        //    ImgLoadUtils.load(((ImageView) holder.getView(R.id.img)), resultsBean.url, new ImgLoadUtils.ImgLoadListener() {
        //        @Override
        //        public void isLoaded() {
        //
        //        }
        //    });
        //} else {
        //    holder.getView(R.id.img).setVisibility(View.GONE);
        //}
        //if (resultsBean.publishedAt.contains("T") && resultsBean.publishedAt.contains("Z")) {
        //    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS Z");//注意格式化的表达式
        //    try {
        //        Date d = format.parse(resultsBean.publishedAt.replace("Z", " UTC"));//注意是空格+UTC
        //        holder.setText(R.id.timeItem, new SimpleDateFormat("yyyy/MM/dd").format(d));
        //    } catch (ParseException e) {
        //        e.printStackTrace();
        //    }
        //}
        //}
    }

    @Override
    public void onViewHolderCreated(ViewHolder holder, View itemView) {
        super.onViewHolderCreated(holder, itemView);
        //适配listview
        AutoUtils.autoSize(itemView);
    }
}
