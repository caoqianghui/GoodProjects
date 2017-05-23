package com.go.cqh.goodprojects.view;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import com.alibaba.fastjson.JSON;
import com.go.cqh.goodprojects.R;
import com.go.cqh.goodprojects.adapter.QmDetailAdapter;
import com.go.cqh.goodprojects.base.BaseActivity;
import com.go.cqh.goodprojects.entry.QmDetailBean;
import com.go.cqh.goodprojects.utils.ConstantUtils;
import com.go.cqh.goodprojects.utils.ToastUtils;
import com.go.cqh.goodprojects.utils.UIUtil;
import com.yolanda.nohttp.NoHttp;
import com.yolanda.nohttp.RequestMethod;
import com.yolanda.nohttp.rest.OnResponseListener;
import com.yolanda.nohttp.rest.Request;
import com.yolanda.nohttp.rest.RequestQueue;
import com.yolanda.nohttp.rest.Response;
import com.zhy.adapter.recyclerview.MultiItemTypeAdapter;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class QmItemDetailActivity extends BaseActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.appbar)
    AppBarLayout appbar;
    @BindView(R.id.recycler_view)
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
                    Log.d("-----------", String.format(ConstantUtils.QM_DETAIL, catid, id));
                    Request<JSONObject> request = NoHttp.createJsonObjectRequest(String.format(ConstantUtils.QM_DETAIL, catid, id), RequestMethod.GET);
                    //将请求添加到队列  NOHTTP_WHAT 这条请求的请求码
                    requestQueue.add(PULLUP_WHAT, request, onResponseListener);
                    break;
            }
        }
    };
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
                    try {
                        JSONObject jsonObject = (JSONObject) response.get();
                        JSONObject object = jsonObject.getJSONObject("data");
                        QmDetailBean.DataBean dataBean = JSON.parseObject(object.toString(), QmDetailBean.DataBean.class);
                        if (dataBean != null) {
                            List<String> content = dataBean.content;
                            Log.d("----------", content.size() + "");
                            //数据加载完成，填充列表
                            initRecyclerView(content);
                        } else {
                            ToastUtils.showToast(UIUtil.getContext(), "没有相应数据");
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    break;
            }
        }

        @Override
        public void onFailed(int what, String url, Object tag, Exception exception, int responseCode, long networkMillis) {
            ToastUtils.showToast(UIUtil.getContext(), "无法连接网络，请稍后重试");
        }

        @Override
        public void onFinish(int what) {

        }
    };
    private String catid;
    private String title;
    private String id;

    private void initRecyclerView(List<String> mList) {
        Rv.setLayoutManager(new LinearLayoutManager(UIUtil.getContext(), LinearLayoutManager.VERTICAL, false));
        Rv.setItemAnimator(new DefaultItemAnimator());
        QmDetailAdapter adapter = new QmDetailAdapter(UIUtil.getContext(), R.layout.item_qm_img, mList);
        adapter.setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, RecyclerView.ViewHolder holder, int position) {
            }

            @Override
            public boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, int position) {
                return false;
            }
        });
        Rv.setAdapter(adapter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qm_item);
        ButterKnife.bind(this);
        //状态栏沉浸
        setStatusbarColor();
        //支持滑动返回
        flag = true;
        catid = getIntent().getStringExtra("catid");
        id = getIntent().getStringExtra("id");
        title = getIntent().getStringExtra("title");
        initToolBar();
        requestQueue = NoHttp.newRequestQueue(3);
        handler.sendEmptyMessage(0);
    }

    /**
     * 初始化 toolbar
     */
    private void initToolBar() {
        appbar.post(new Runnable() {
            @Override
            public void run() {
                CoordinatorLayout.LayoutParams params = (CoordinatorLayout.LayoutParams) appbar.getLayoutParams();
                //params.topMargin =  mStatusBarHeight;
                appbar.setLayoutParams(params);
            }
        });
        toolbar.setTitle(title);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
