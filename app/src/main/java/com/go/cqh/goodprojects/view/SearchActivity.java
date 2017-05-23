package com.go.cqh.goodprojects.view;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.go.cqh.goodprojects.R;
import com.go.cqh.goodprojects.adapter.ItemAdapter;
import com.go.cqh.goodprojects.base.BaseActivity;
import com.go.cqh.goodprojects.entry.ProjectsBean;
import com.go.cqh.goodprojects.utils.ConstantUtils;
import com.go.cqh.goodprojects.utils.ToastUtils;
import com.go.cqh.goodprojects.utils.WaitDialog;
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

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SearchActivity extends BaseActivity implements SearchView.OnQueryTextListener {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.appbar)
    AppBarLayout appbar;
    @BindView(R.id.rv)
    RecyclerView rv;
    @BindView(R.id.root)
    CoordinatorLayout root;
    @BindView(R.id.searchView)
    SearchView searchView;
    @BindView(R.id.tv)
    TextView textView;
    @BindView(R.id.layout)
    LinearLayout layout;
    //请求队列
    private RequestQueue requestQueue;
    //服务器拿到的数据
    public static List<ProjectsBean.ResultsBean> randomList = new ArrayList<>();
    /*搜索的数据*/
    public static List<ProjectsBean.ResultsBean> searchList = new ArrayList<>();
    private int itemNum;
    private int page;
    private static final int RANDOM_WHAT = 0x011;
    private static final int SEARCH_WHAT = 0x012;
    private String[] tabs = {"Android ", "iOS ", "休息视频", "拓展资源", "前端", "App", "瞎推荐"};
    //handler中请求数据
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                /**随机数据*/
                case 0:
                    /*随机条数*/
                    itemNum = 10;
                    /*随机类型*/
                    type = tabs[new Random().nextInt(7)];
                    //格式化请求url
                    String path = String.format(ConstantUtils.RANDOM_DATA, Uri.encode(type), itemNum);
                    //创建请求
                    Request<JSONObject> request = NoHttp.createJsonObjectRequest(path, RequestMethod.GET);
                    //将请求添加到队列  NOHTTP_WHAT 这条请求的请求码
                    requestQueue.add(RANDOM_WHAT, request, onResponseListener);
                    break;
                /**搜索数据*/
                case 1:
                    page = 1;
                    //格式化请求url
                    String url = String.format(ConstantUtils.SEARCH_DATA, Uri.encode(txt), 20, page);
                    //创建请求
                    request2 = NoHttp.createJsonObjectRequest(url, RequestMethod.GET);
                    //将请求添加到队列  NOHTTP_WHAT 这条请求的请求码
                    requestQueue.add(SEARCH_WHAT, request2, onResponseListener);
                    break;
            }
        }
    };
    private String type;
    private LinearLayoutManager linearLayoutManager;
    private String txt;
    private WaitDialog waitDialog;
    private Request<JSONObject> request2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        ButterKnife.bind(this);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handler.sendEmptyMessage(0);
            }
        });
        linearLayoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        };
        /*立马请求随机数据*/
        handler.sendEmptyMessage(0);
        searchView.setIconifiedByDefault(true);
        searchView.setFocusable(true);
        searchView.setIconified(false);
        searchView.requestFocusFromTouch();
        searchView.setOnCloseListener(new SearchView.OnCloseListener() {
            @Override
            public boolean onClose() {
                searchView.setIconifiedByDefault(true);
                return false;
            }
        });
        searchView.setOnQueryTextListener(this);
        searchView.setQueryHint("请输入搜索内容");
        //状态栏沉浸
        setStatusbarColor();
        //支持滑动返回
        flag = true;
        requestQueue = NoHttp.newRequestQueue(3);
        initToolBar();
        waitDialog = new WaitDialog(this, "正在搜索");
        /*用户返回键取消loading，则取消请求*/
        waitDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                if (request2 != null) {
                    request2.cancel();
                }
            }
        });
    }

    /**
     * toolbar
     */
    private void initToolBar() {
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        /*返会按钮*/
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        if (query != null && !query.equals("")) {
            txt = query;
            handler.sendEmptyMessage(1);
        } else {
            ToastUtils.showToast(getApplicationContext(), "请输入搜索内容");
        }
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        if (newText.equals("")) {
            handler.sendEmptyMessage(0);
        }
        return false;
    }

    /**
     * 服务器请求相应
     */
    public OnResponseListener<JSONObject> onResponseListener = new OnResponseListener<JSONObject>() {

        @Override
        public void onStart(int what) {
            switch (what) {
                case SEARCH_WHAT:
                    layout.setVisibility(View.GONE);
                    waitDialog.show();
                    break;
                case RANDOM_WHAT:
                    layout.setVisibility(View.VISIBLE);
                    waitDialog.dismiss();
                    break;
            }
        }

        @Override
        public void onSucceed(int what, Response response) {
            switch (what) {
                //搜索
                case SEARCH_WHAT:
                    List<ProjectsBean.ResultsBean> mList;
                    try {
                        JSONObject jsonObject = (JSONObject) response.get();
                        JSONArray jsonArray = jsonObject.getJSONArray("results");
                        mList = JSON.parseArray(jsonArray.toString(), ProjectsBean.ResultsBean.class);
                        if (mList != null && mList.size() != 0) {
                            //数据加载完成，填充列表
                            searchList.clear();
                            searchList.addAll(mList);
                            initRecyclerView(searchList, false);
                            ToastUtils.showToast(getApplicationContext(), "搜索成功");
                        } else {
                            ToastUtils.showToast(getApplicationContext(),"没有相应数据");
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    break;
                //随机
                case RANDOM_WHAT:
                    List<ProjectsBean.ResultsBean> list;
                    try {
                        JSONObject jsonObject = (JSONObject) response.get();
                        JSONArray jsonArray = jsonObject.getJSONArray("results");
                        list = JSON.parseArray(jsonArray.toString(), ProjectsBean.ResultsBean.class);
                        if (list != null && list.size() != 0) {
                            //数据加载完成，填充列表
                            randomList.clear();
                            randomList.addAll(list);
                            initRecyclerView(randomList, true);
                        } else {
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    break;
            }
        }

        @Override
        public void onFailed(int what, String url, Object tag, Exception exception, int responseCode, long networkMillis) {
            switch (what) {
                //搜索
                case SEARCH_WHAT:
                    ToastUtils.showToast(getApplicationContext(), "搜索失败");
                    break;
            }
        }

        @Override
        public void onFinish(int what) {
            switch (what) {
                case SEARCH_WHAT:
                    waitDialog.dismiss();
                    break;
            }
        }
    };

    private void initRecyclerView(List<ProjectsBean.ResultsBean> list, boolean isRandom) {
        rv.setLayoutManager(linearLayoutManager);
        rv.setItemAnimator(new DefaultItemAnimator());
        ItemAdapter adapter = new ItemAdapter(getApplicationContext(), R.layout.item, list);
        adapter.setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, RecyclerView.ViewHolder holder, int position) {
                Intent intent = new Intent(getApplicationContext(), WebViewActivity.class);
                intent.putExtra("item", randomList.get(position));
                startActivity(intent);
            }

            @Override
            public boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, int position) {
                return false;
            }
        });
        rv.setAdapter(adapter);
        if (isRandom) {
            textView.setVisibility(View.VISIBLE);
        } else {
            textView.setVisibility(View.GONE);
            layout.setVisibility(View.VISIBLE);
        }
    }
}
