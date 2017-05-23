package com.go.cqh.goodprojects.view;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.View;

import com.alibaba.fastjson.JSON;
import com.baoyz.widget.PullRefreshLayout;
import com.go.cqh.goodprojects.R;
import com.go.cqh.goodprojects.adapter.CartoonAdapter;
import com.go.cqh.goodprojects.base.BaseActivity;
import com.go.cqh.goodprojects.entry.CartoonBean;
import com.go.cqh.goodprojects.utils.ConstantUtils;
import com.go.cqh.goodprojects.utils.ToastUtils;
import com.go.cqh.goodprojects.widget.LoadMoreView;
import com.yolanda.nohttp.NoHttp;
import com.yolanda.nohttp.RequestMethod;
import com.yolanda.nohttp.rest.OnResponseListener;
import com.yolanda.nohttp.rest.Request;
import com.yolanda.nohttp.rest.RequestQueue;
import com.yolanda.nohttp.rest.Response;
import com.zhy.adapter.recyclerview.MultiItemTypeAdapter;
import com.zhy.adapter.recyclerview.wrapper.LoadMoreWrapper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.wangyuwei.loadingview.LoadingView;

import static com.yolanda.nohttp.NoHttp.getContext;

public class CartoonActivity extends BaseActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    @BindView(R.id.refresh)
    PullRefreshLayout refreshLayout;
    @BindView(R.id.loading_view)
    LoadingView loadingView;
    private RequestQueue requestQueue;

    private int pagerNum;
    private final int PULLUP_WHAT = 0001;
    private final int PULLDOWN_WHAT = 0002;
    public static List<CartoonBean.ShowapiResBodyBean.PagebeanBean.ContentlistBean> mList = new ArrayList<>();
    private boolean isFromRefresh;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0:
                    pagerNum = 1;
                    //格式化请求url
                    //String path = String.format(ConstantUtils.PROJECT_TAB, Uri.encode(tab), itemNum, pagerNum);
                    String path = String.format(ConstantUtils.MANHUA, pagerNum);
                    //创建请求
                    Request<JSONObject> request = NoHttp.createJsonObjectRequest(path, RequestMethod.GET);
                    //将请求添加到队列  NOHTTP_WHAT 这条请求的请求码
                    requestQueue.add(PULLUP_WHAT, request, onResponseListener);
                    break;
                case 1:
                    isFromRefresh = true;
                    handler.sendEmptyMessage(0);
                    break;
                //加载更多
                case 2:
                    pagerNum++;
                    //格式化请求url
                    String path2 = String.format(ConstantUtils.MANHUA, pagerNum);
                    //创建请求
                    Request<JSONObject> request2 = NoHttp.createJsonObjectRequest(path2, RequestMethod.GET);
                    //将请求添加到队列  PULLDOWN_WHAT 这条请求的请求码
                    requestQueue.add(PULLDOWN_WHAT, request2, onResponseListener);
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
                    List<CartoonBean.ShowapiResBodyBean.PagebeanBean.ContentlistBean> refreshList;
                    try {
                        JSONObject jsonObject = (JSONObject) response.get();
                        JSONObject showapi_res_body = jsonObject.getJSONObject("showapi_res_body");
                        JSONObject pagebean = showapi_res_body.getJSONObject("pagebean");
                        JSONArray jsonArray = pagebean.getJSONArray("contentlist");
                        refreshList = JSON.parseArray(jsonArray.toString(), CartoonBean.ShowapiResBodyBean.PagebeanBean.ContentlistBean.class);
                        if (refreshList != null && refreshList.size() != 0) {
                            //数据加载完成，填充列表
                            mList.clear();
                            mList.addAll(refreshList);
                            initRecyclerView();
                        } else {
                            ToastUtils.showToast(getApplicationContext(), "没有相应数据");
                        }
                        if (isFromRefresh) {
                            refreshLayout.setRefreshing(false);
                            isFromRefresh = !isFromRefresh;
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    break;
                //上拉加载更多
                case PULLDOWN_WHAT:
                    List<CartoonBean.ShowapiResBodyBean.PagebeanBean.ContentlistBean> loadMoreList = null;
                    try {
                        JSONObject jsonObject = (JSONObject) response.get();
                        JSONObject showapi_res_body = jsonObject.getJSONObject("showapi_res_body");
                        JSONObject pagebean = showapi_res_body.getJSONObject("pagebean");
                        JSONArray jsonArray = pagebean.getJSONArray("contentlist");
                        loadMoreList = JSON.parseArray(jsonArray.toString(), CartoonBean.ShowapiResBodyBean.PagebeanBean.ContentlistBean.class);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    //加载完数据更新适配器
                    if (loadMoreList != null && loadMoreList.size() != 0) {
                        int size = mList.size();
                        mList.addAll(loadMoreList);
                        int size1 = mList.size();
                        recyclerView.getAdapter().notifyItemMoved(size, size1 - 1);
                    } else {
                        ToastUtils.showToast(getApplicationContext(), "没有更多数据");
                    }
                    break;
            }
            loadingView.setVisibility(View.GONE);
            loadingView.stop();
        }

        @Override
        public void onFailed(int what, String url, Object tag, Exception exception, int responseCode, long networkMillis) {
            ToastUtils.showToast(getApplicationContext(), "无法连接网络，请稍后重试");
            if (isFromRefresh) {
                refreshLayout.setRefreshing(false);
            }
        }

        @Override
        public void onFinish(int what) {

        }
    };
    private LinearLayoutManager linearLayoutManager;
    private View view;

    private void initRecyclerView() {
        linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        CartoonAdapter adapter = new CartoonAdapter(getApplicationContext(), R.layout.item, mList);
        adapter.setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, RecyclerView.ViewHolder holder, int position) {
                Intent intent = new Intent(getApplicationContext(), CartoonDetailActivity.class);
                intent.putExtra("url", mList.get(position).link);
                intent.putExtra("desc", mList.get(position).title);
                startActivity(intent);
            }

            @Override
            public boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, int position) {
                return false;
            }
        });
        /**
         * 添加滚动监听，判断是否滑动到底部，同时判断是否满一屏
         * 不能向上滑，同时可以向下滑时 执行加载更多
         * 否则可能是未到底部或者刚好满一屏
         */
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (!canScrollVertically(1) && canScrollVertically(-1)) {
                    view.setVisibility(View.VISIBLE);
                    handler.sendEmptyMessageDelayed(2, 1500);
                } else {
                    view.setVisibility(View.GONE);
                    handler.removeMessages(2);
                }
            }
        });
        final LoadMoreWrapper loadMoreWrapper = new LoadMoreWrapper(adapter);
        view = View.inflate(getApplicationContext(), R.layout.default_loading, null);
        loadMoreWrapper.setLoadMoreView(view);
        loadMoreWrapper.setOnLoadMoreListener(new LoadMoreWrapper.OnLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                //加载更多时 开始动画执行
                ((LoadMoreView) view.findViewById(R.id.loadingmore)).startAnim();
            }
        });
        recyclerView.setAdapter(loadMoreWrapper);
        refreshLayout.setVisibility(View.VISIBLE);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cartoon);
        ButterKnife.bind(this);
        //支持滑动返回
        flag = true;
        //状态栏沉浸
        setStatusbarColor();
        initToolBar();
        refreshLayout.setVisibility(View.GONE);
        loadingView.setVisibility(View.VISIBLE);
        if (loadingView != null) {
            loadingView.start();
        }
        requestQueue = NoHttp.newRequestQueue(3);
        //请求数据
        initRefresh();
        handler.sendEmptyMessage(0);
    }

    private void initRefresh() {
        refreshLayout.setRefreshStyle(PullRefreshLayout.STYLE_SMARTISAN);
        refreshLayout.setColor(ContextCompat.getColor(getApplicationContext(), R.color.maincolor));
        refreshLayout.setOnRefreshListener(new PullRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                handler.sendEmptyMessage(1);
            }
        });
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (loadingView != null) {
            loadingView.stop();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }

    /**
     * 初始化 toolbar
     */
    private void initToolBar() {
        toolbar.setTitle("内涵漫画");
        toolbar.setTitleTextColor(Color.WHITE);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        final Drawable upArrow = getResources().getDrawable(R.drawable.abc_ic_ab_back_material);
        upArrow.setColorFilter(getResources().getColor(R.color.black_bg), PorterDuff.Mode.SRC_ATOP);
        getSupportActionBar().setHomeAsUpIndicator(upArrow);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    /**
     * 返回键监听
     *
     * @param keyCode
     * @param event
     * @return
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            finish();
        }
        return super.onKeyDown(keyCode, event);
    }

    /**
     * 判断recyclerview是否滑动到底部
     * canScrollVertically(1)的值表示是否能向上滚动，false表示已经滚动到底部
     * canScrollVertically(-1)的值表示是否能向下滚动，false表示已经滚动到顶部
     * computeVerticalScrollExtent()是当前屏幕显示的区域高度，
     * computeVerticalScrollOffset() 是当前屏幕之前滑过的距离，
     * computeVerticalScrollRange()是整个View控件的高度。
     *
     * @param direction 方向 1 向上 -1 向下
     * @return
     */
    public boolean canScrollVertically(int direction) {
        final int offset = recyclerView.computeVerticalScrollOffset();
        final int range = recyclerView.computeVerticalScrollRange() - recyclerView.computeVerticalScrollExtent();
        if (range == 0) {
            return false;
        }
        if (direction < 0) {
            return offset > 0;
        } else {
            return offset < range - 1;
        }
    }
}
