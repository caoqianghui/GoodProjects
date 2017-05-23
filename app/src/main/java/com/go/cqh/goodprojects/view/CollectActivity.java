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
import com.cqh.greendao.gen.DBeanDao;
import com.go.cqh.goodprojects.R;
import com.go.cqh.goodprojects.adapter.ItemAdapter;
import com.go.cqh.goodprojects.base.BaseActivity;
import com.go.cqh.goodprojects.entry.DBean;
import com.go.cqh.goodprojects.entry.ProjectsBean;
import com.go.cqh.goodprojects.utils.DbUtil;
import com.zhy.adapter.recyclerview.MultiItemTypeAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.yolanda.nohttp.NoHttp.getContext;

public class CollectActivity extends BaseActivity {

    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.appbar)
    AppBarLayout appbar;
    @BindView(R.id.root)
    CoordinatorLayout root;
    private List<DBean> collectList;
    public static List<ProjectsBean.ResultsBean> mList = new ArrayList<>();
    private LinearLayoutManager linearLayoutManager;
    private ItemAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_collect);
        ButterKnife.bind(this);
        //状态栏沉浸
        setStatusbarColor();
        //支持滑动返回
        flag = true;
        initToolBar();
    }

    @Override
    protected void onResume() {
        super.onResume();
        collectList = DbUtil.dBeanDao.queryBuilder()
                .where(DBeanDao.Properties.IsCollect.eq(true))
                .orderAsc(DBeanDao.Properties.Id)
                .build()
                .list();
        mList.clear();
        for (DBean dBean : collectList) {
            ProjectsBean.ResultsBean resultsBean = JSON.parseObject(dBean.getJson(), ProjectsBean.ResultsBean.class);
            mList.add(resultsBean);
        }
        if (adapter == null) {
            initRecyclerView();
        } else {
            adapter.notifyDataSetChanged();
        }

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
        toolbar.setTitle("收藏列表");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
    /**
     * 初始化列表
     */
    private void initRecyclerView() {
        linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        adapter = new ItemAdapter(getContext(), R.layout.item, mList);
        adapter.setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, RecyclerView.ViewHolder holder, int position) {
                Intent intent = new Intent(CollectActivity.this, WebViewActivity.class);
                intent.putExtra("item", mList.get(position));
                startActivity(intent);
            }

            @Override
            public boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, int position) {
                return false;
            }
        });
        recyclerView.setAdapter(adapter);
    }
}
