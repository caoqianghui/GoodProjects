package com.go.cqh.goodprojects.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.alibaba.fastjson.JSON;
import com.go.cqh.goodprojects.R;
import com.go.cqh.goodprojects.adapter.NewsAdapter;
import com.go.cqh.goodprojects.base.BaseActivity;
import com.go.cqh.goodprojects.entry.NewsBean;
import com.go.cqh.goodprojects.entry.QmBean;
import com.go.cqh.goodprojects.utils.ConstantUtils;
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

import static com.yolanda.nohttp.NoHttp.getContext;

public class NewsActivity extends BaseActivity {
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.appbar)
    AppBarLayout appbar;
    private LinearLayoutManager linearLayoutManager;
    private int WHAT = 02;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);
        ButterKnife.bind(this);
        //状态栏沉浸
        setStatusbarColor();
        //支持滑动返回
        flag = true;
        initToolBar();
        init();
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
        toolbar.setTitle("新闻");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void init() {
        RequestQueue requestQueue = NoHttp.newRequestQueue(3);
        String url = String.format(ConstantUtils.NEWS, "top");
        //创建请求
        Request<JSONObject> request = NoHttp.createJsonObjectRequest(url, RequestMethod.GET);
        requestQueue.add(WHAT, request, new OnResponseListener<JSONObject>() {
            @Override
            public void onStart(int what) {

            }

            @Override
            public void onSucceed(int what, Response<JSONObject> response) {
                List<NewsBean.ResultBean.DataBean> list;
                try {
                    JSONObject jsonObject = response.get();
                    JSONObject showapi_res_body = jsonObject.getJSONObject("result");
                    JSONArray jsonArray = showapi_res_body.getJSONArray("data");
                    list = JSON.parseArray(jsonArray.toString(), NewsBean.ResultBean.DataBean.class);
                    initRecyclerView(list);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailed(int what, String url, Object tag, Exception exception, int responseCode, long networkMillis) {

            }

            @Override
            public void onFinish(int what) {

            }
        });
    }

    /**
     * 初始化列表
     */
    private void initRecyclerView(final List<NewsBean.ResultBean.DataBean> list) {
        linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        NewsAdapter newsAdapter = new NewsAdapter(getContext(), R.layout.item, list);
        newsAdapter.setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, RecyclerView.ViewHolder holder, int position) {
                Intent intent = new Intent(NewsActivity.this, CartoonDetailActivity.class);
                intent.putExtra("url", list.get(position).url);
                intent.putExtra("desc", list.get(position).title);
                startActivity(intent);
            }

            @Override
            public boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, int position) {
                return false;
            }
        });
        recyclerView.setAdapter(newsAdapter);
    }
}
