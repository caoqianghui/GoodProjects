package com.go.cqh.goodprojects.fragments;


import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.alibaba.fastjson.JSON;
import com.go.cqh.goodprojects.R;
import com.go.cqh.goodprojects.adapter.QmAdapter;
import com.go.cqh.goodprojects.entry.QmBean;
import com.go.cqh.goodprojects.utils.ConstantUtils;
import com.go.cqh.goodprojects.utils.ToastUtils;
import com.go.cqh.goodprojects.utils.UIUtil;
import com.go.cqh.goodprojects.view.QmItemActivity;
import com.yolanda.nohttp.NoHttp;
import com.yolanda.nohttp.RequestMethod;
import com.yolanda.nohttp.rest.OnResponseListener;
import com.yolanda.nohttp.rest.Request;
import com.yolanda.nohttp.rest.RequestQueue;
import com.yolanda.nohttp.rest.Response;
import com.zhy.adapter.recyclerview.MultiItemTypeAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class LatestUpFragment extends Fragment {
    @BindView(R.id.Rv)
    RecyclerView Rv;
    //请求队列
    private RequestQueue requestQueue;
    //下拉刷新请求码
    private static final int PULLUP_WHAT = 0x001;
    //handler中请求数据
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0:
                    //创建请求
                    Request<JSONObject> request = NoHttp.createJsonObjectRequest(ConstantUtils.QM_UPDATE, RequestMethod.GET);
                    //将请求添加到队列  NOHTTP_WHAT 这条请求的请求码
                    requestQueue.add(PULLUP_WHAT, request, onResponseListener);
                    break;
            }
        }
    };

    public LatestUpFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_hot, container, false);
        ButterKnife.bind(this, view);
        requestQueue = NoHttp.newRequestQueue(3);
        //请求数据
        handler.sendEmptyMessage(0);
        return view;
    }

    /**
     * 服务器请求相应
     */
    private OnResponseListener<JSONObject> onResponseListener = new OnResponseListener<JSONObject>() {

        @Override
        public void onStart(int what) {
        }

        @Override
        public void onSucceed(int what, Response response) {
            switch (what) {
                //下拉刷新
                case PULLUP_WHAT:
                    List<QmBean.DataBean> refreshList;
                    try {
                        JSONObject jsonObject = (JSONObject) response.get();
                        JSONArray jsonArray = jsonObject.getJSONArray("data");
                        refreshList = JSON.parseArray(jsonArray.toString(), QmBean.DataBean.class);
                        if (refreshList != null && refreshList.size() != 0) {
                            //数据加载完成，填充列表
                            initRecyclerView(refreshList);
                        } else {
                            ToastUtils.showToast(getContext(), "没有相应数据");
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    break;
            }
        }

        @Override
        public void onFailed(int what, String url, Object tag, Exception exception, int responseCode, long networkMillis) {
            ToastUtils.showToast(getContext(), "无法连接网络，请稍后重试");
        }

        @Override
        public void onFinish(int what) {

        }
    };

    private void initRecyclerView(final List<QmBean.DataBean> mList) {
        Rv.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        Rv.setItemAnimator(new DefaultItemAnimator());
        QmAdapter adapter = new QmAdapter(UIUtil.getContext(), R.layout.item, mList);
        adapter.setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, RecyclerView.ViewHolder holder, int position) {
                Intent intent = new Intent(getActivity(), QmItemActivity.class);
                intent.putExtra("catid", mList.get(position).catid);
                intent.putExtra("title", mList.get(position).catname);
                startActivity(intent);
            }

            @Override
            public boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, int position) {
                return false;
            }
        });
        Rv.setAdapter(adapter);
    }
}
