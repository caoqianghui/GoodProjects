package com.go.cqh.goodprojects.fragments;


import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.alibaba.fastjson.JSON;
import com.baoyz.widget.PullRefreshLayout;
import com.go.cqh.goodprojects.R;
import com.go.cqh.goodprojects.adapter.FuliAdapter;
import com.go.cqh.goodprojects.base.BaseFragment;
import com.go.cqh.goodprojects.entry.SexImgBean;
import com.go.cqh.goodprojects.utils.ConstantUtils;
import com.go.cqh.goodprojects.utils.PrefUtils;
import com.go.cqh.goodprojects.utils.ToastUtils;
import com.go.cqh.goodprojects.utils.UIUtil;
import com.go.cqh.goodprojects.view.FLActivity;
import com.go.cqh.goodprojects.view.MainActivity;
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

/**
 * A simple {@link Fragment} subclass.
 */
public class FuliFragment extends BaseFragment {


    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    @BindView(R.id.refresh)
    PullRefreshLayout refreshLayout;
    @BindView(R.id.loading_view)
    LoadingView loadingView;
    //标签名
    private String tab;
    //是否刷新过
    private boolean isFromRefresh;
    //页码
    private int pagerNum;
    //每次加载多少条
    private int itemNum = 20;
    //请求队列
    private RequestQueue requestQueue;
    //下拉刷新请求码
    private static final int PULLUP_WHAT = 0x001;
    //上拉加载更多请求码
    private static final int PULLDOWN_WHAT = 0x002;
    //服务器拿到的数据
    public static List<SexImgBean.ShowapiResBodyBean.NewslistBean> mList = new ArrayList<>();
    //handler中请求数据
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0:
                    pagerNum = 1;
                    //格式化请求url
                    //String path = String.format(ConstantUtils.PROJECT_TAB, Uri.encode(tab), itemNum, pagerNum);
                    String path = String.format(ConstantUtils.SEX_IMG, itemNum, pagerNum);
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
                    String path2 = String.format(ConstantUtils.SEX_IMG, itemNum, pagerNum);
                    //创建请求
                    Request<JSONObject> request2 = NoHttp.createJsonObjectRequest(path2, RequestMethod.GET);
                    //将请求添加到队列  PULLDOWN_WHAT 这条请求的请求码
                    requestQueue.add(PULLDOWN_WHAT, request2, onResponseListener);
                    break;
            }
        }
    };
    private boolean isPrepared;
    //private StaggeredGridLayoutManager staggeredGridLayoutManager;
    private View view;
    private GridLayoutManager gridLayoutManager;

    public FuliFragment() {
    }

    public FuliFragment(String tab) {
        this.tab = tab;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_all, container, false);
        isPrepared = true;
        ButterKnife.bind(this, view);
        gridLayoutManager = new GridLayoutManager(getContext(), 2);
        //staggeredGridLayoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        refreshLayout.setVisibility(View.GONE);
        loadingView.setVisibility(View.VISIBLE);
        loadingView.start();
        requestQueue = NoHttp.newRequestQueue(3);
        //请求数据
        initRefresh();
        return view;
    }

    @Override
    protected void onVisible() {
        super.onVisible();
        MainActivity.setGoUpTvOnClickListener(new UIUtil.GoUpListener() {
            @Override
            public void goUp() {
                UIUtil.goToTop(recyclerView);
            }
        });
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
                    List<SexImgBean.ShowapiResBodyBean.NewslistBean> refreshList;
                    try {
                        JSONObject jsonObject = (JSONObject) response.get();
                        JSONObject showapi_res_body = jsonObject.getJSONObject("showapi_res_body");
                        JSONArray jsonArray = showapi_res_body.getJSONArray("newslist");
                        refreshList = JSON.parseArray(jsonArray.toString(), SexImgBean.ShowapiResBodyBean.NewslistBean.class);
                        if (refreshList != null && refreshList.size() != 0) {
                            //数据加载完成，填充列表
                            mList.clear();
                            mList.addAll(refreshList);
                            initRecyclerView();
                        } else {
                            ToastUtils.showToast(getContext(), "没有相应数据");
                        }
                        if (isFromRefresh) {
                            refreshLayout.setRefreshing(false);
                            showDropView("刷新成功");
                            isFromRefresh = !isFromRefresh;
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    break;
                //上拉加载更多
                case PULLDOWN_WHAT:
                    List<SexImgBean.ShowapiResBodyBean.NewslistBean> loadMoreList = null;
                    try {
                        JSONObject jsonObject = (JSONObject) response.get();
                        JSONObject showapi_res_body = jsonObject.getJSONObject("showapi_res_body");
                        JSONArray jsonArray = showapi_res_body.getJSONArray("newslist");
                        loadMoreList = JSON.parseArray(jsonArray.toString(), SexImgBean.ShowapiResBodyBean.NewslistBean.class);

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
                        ToastUtils.showToast(getContext(), "没有更多数据");
                    }
                    break;
            }
            loadingView.setVisibility(View.GONE);
            loadingView.stop();
        }

        @Override
        public void onFailed(int what, String url, Object tag, Exception exception, int responseCode, long networkMillis) {
            ToastUtils.showToast(getContext(), "无法连接网络，请稍后重试");
            if (isFromRefresh) {
                refreshLayout.setRefreshing(false);
            }
        }

        @Override
        public void onFinish(int what) {

        }
    };

    /**
     * 初始化列表
     */
    private void initRecyclerView() {
        //防止item 交换位置
        //staggeredGridLayoutManager.setGapStrategy(StaggeredGridLayoutManager.GAP_HANDLING_NONE);
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setPadding(0, 0, 0, 0);
        FuliAdapter adapter = new FuliAdapter(getContext(), R.layout.item_img, mList);
        adapter.setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, RecyclerView.ViewHolder holder, int position) {
                Intent intent = new Intent(getActivity(), FLActivity.class);
                intent.putExtra("item", mList.get(position));
                startActivity(intent);
            }

            @Override
            public boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, int position) {
                return false;
            }
        });
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                //防止第一行到顶部有空白区域
                //staggeredGridLayoutManager.invalidateSpanAssignments();
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                /**
                 * 用与判断是否到底部，同时也用于判断是否满一屏
                 */
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
        view = View.inflate(getActivity(), R.layout.default_loading, null);
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

    /**
     * 初始化下拉刷新
     */
    private void initRefresh() {
        refreshLayout.setRefreshStyle(PullRefreshLayout.STYLE_SMARTISAN);
        refreshLayout.setColor(ContextCompat.getColor(getActivity(), R.color.maincolor));
        refreshLayout.setOnRefreshListener(new PullRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                handler.sendEmptyMessage(1);
            }
        });
    }

    /**
     * 加载数据
     */
    @Override
    protected void lazyLoad() {
        //每个页面第一次加载数据时懒加载
        if (!PrefUtils.getBoolean(UIUtil.getContext(), tab, false)) {
            handler.sendEmptyMessage(0);
            PrefUtils.putBoolean(UIUtil.getContext(), tab, true);
        }
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
