package com.go.cqh.goodprojects.view;

import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.go.cqh.goodprojects.R;
import com.go.cqh.goodprojects.adapter.MyViewPagerAdapter;
import com.go.cqh.goodprojects.base.BaseActivity;
import com.go.cqh.goodprojects.fragments.HotFragment;
import com.go.cqh.goodprojects.fragments.LatestUpFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class QhActivity extends BaseActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.appbar)
    AppBarLayout appbar;
    @BindView(R.id.latestUpdateTv)
    TextView latestUpdateTv;
    @BindView(R.id.latestUpdateTag)
    View latestUpdateTag;
    @BindView(R.id.latestUpdate)
    LinearLayout latestUpdate;
    @BindView(R.id.hotLayoutTv)
    TextView hotLayoutTv;
    @BindView(R.id.hotLayoutTag)
    View hotLayoutTag;
    @BindView(R.id.hotLayout)
    LinearLayout hotLayout;
    @BindView(R.id.viewpager)
    ViewPager pager;
    private List<Fragment> fragmentList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qh);
        ButterKnife.bind(this);
        //状态栏沉浸
        setStatusbarColor();
        //支持滑动返回
        flag = true;
        initToolBar();
        init();
    }

    private void init() {
        fragmentList = new ArrayList<>();
        fragmentList.add(new LatestUpFragment());
        fragmentList.add(new HotFragment());
        pager.setAdapter(new MyViewPagerAdapter(getSupportFragmentManager(), fragmentList));
        pager.setCurrentItem(0);
        pager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (position == 0) {
                    latestUpdateTv.setTextColor(Color.parseColor("#009688"));
                    latestUpdateTag.setVisibility(View.VISIBLE);
                    hotLayoutTv.setTextColor(Color.parseColor("#808080"));
                    hotLayoutTag.setVisibility(View.INVISIBLE);
                } else {
                    latestUpdateTv.setTextColor(Color.parseColor("#808080"));
                    latestUpdateTag.setVisibility(View.INVISIBLE);
                    hotLayoutTv.setTextColor(Color.parseColor("#009688"));
                    hotLayoutTag.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
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
        toolbar.setTitle("趣话");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @OnClick({R.id.latestUpdate, R.id.hotLayout})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.latestUpdate:
                latestUpdateTv.setTextColor(Color.parseColor("#009688"));
                latestUpdateTag.setVisibility(View.VISIBLE);
                hotLayoutTv.setTextColor(Color.parseColor("#009688"));
                hotLayoutTag.setVisibility(View.INVISIBLE);
                pager.setCurrentItem(0);
                break;
            case R.id.hotLayout:
                latestUpdateTv.setTextColor(Color.parseColor("#009688"));
                latestUpdateTag.setVisibility(View.INVISIBLE);
                hotLayoutTv.setTextColor(Color.parseColor("#009688"));
                hotLayoutTag.setVisibility(View.VISIBLE);
                pager.setCurrentItem(1);
                break;
        }
    }
}
