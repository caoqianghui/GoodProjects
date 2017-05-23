package com.go.cqh.goodprojects.view;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.LinearLayout;

import com.go.cqh.goodprojects.R;
import com.go.cqh.goodprojects.base.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ChatActivity extends BaseActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.tangseng)
    LinearLayout tangseng;
    @BindView(R.id.bajie)
    LinearLayout bajie;
    @BindView(R.id.dasheng)
    LinearLayout dasheng;
    @BindView(R.id.shaseng)
    LinearLayout shaseng;
    private String name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        ButterKnife.bind(this);
        //状态栏沉浸
        setStatusbarColor();
        initToolBar();
        name = getIntent().getStringExtra("name");
        if (name.equals("bajie")) {
            bajie.setVisibility(View.GONE);
        }
        if (name.equals("tangseng")) {
            tangseng.setVisibility(View.GONE);
        }
        if (name.equals("shaseng")) {
            shaseng.setVisibility(View.GONE);
        }
        if (name.equals("dasheng")) {
            dasheng.setVisibility(View.GONE);
        }
    }

    private void initToolBar() {
        toolbar.setTitle("通讯录");
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

    @OnClick({R.id.tangseng, R.id.bajie, R.id.dasheng, R.id.shaseng})
    public void onViewClicked(View view) {
        Intent intent = new Intent(ChatActivity.this, ImRChatActivity.class);
        switch (view.getId()) {
            case R.id.tangseng:
                intent.putExtra("name", "tangseng");
                break;
            case R.id.bajie:
                intent.putExtra("name", "bajie");
                break;
            case R.id.dasheng:
                intent.putExtra("name", "dasheng");
                break;
            case R.id.shaseng:
                intent.putExtra("name", "shaseng");
                break;
        }
        startActivity(intent);
    }
}
