package com.go.cqh.goodprojects.view;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.go.cqh.goodprojects.R;
import com.go.cqh.goodprojects.base.BaseActivity;
import com.go.cqh.goodprojects.utils.PrefUtils;
import com.go.cqh.goodprojects.utils.ToastUtils;
import com.go.cqh.goodprojects.utils.UIUtil;
import com.netease.nimlib.sdk.NIMClient;
import com.netease.nimlib.sdk.RequestCallback;
import com.netease.nimlib.sdk.auth.AuthService;
import com.netease.nimlib.sdk.auth.LoginInfo;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ImRActivity extends BaseActivity {

    @BindView(R.id.button3)
    Button button3;
    @BindView(R.id.name)
    EditText name;
    @BindView(R.id.pwd)
    EditText pwd;
    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_imr);
        ButterKnife.bind(this);
        //状态栏沉浸
        setStatusbarColor();
        initToolBar();
    }

    @OnClick({R.id.button3})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.button3:
                if (!TextUtils.isEmpty(name.getText().toString()) && !TextUtils.isEmpty(pwd.getText().toString())) {
                    login(name.getText().toString(), pwd.getText().toString());
                }
                break;
//            case R.id.button:
//                NIMClient.getService(AuthService.class).logout();
//                break;
        }
    }

    private void login(final String name, final String pwd) {
        final LoginInfo info = new LoginInfo(name, pwd);
        NIMClient.getService(AuthService.class).login(info).setCallback(new RequestCallback() {
                    @Override
                    public void onSuccess(Object o) {
                        PrefUtils.putString(UIUtil.getContext(), "name", name);
                        PrefUtils.putString(UIUtil.getContext(), "token", pwd);
                        ToastUtils.showToast(getApplicationContext(), "登录成功");
                        Intent intent = new Intent(ImRActivity.this, ChatActivity.class);
                        intent.putExtra("name", name);
                        startActivity(intent);
                    }

                    @Override
                    public void onFailed(int i) {
                        ToastUtils.showToast(getApplicationContext(), "登录失败-->" + i);
                    }

                    @Override
                    public void onException(Throwable throwable) {
                        ToastUtils.showToast(getApplicationContext(), "登录异常-->" + throwable.toString());
                    }
                });
    }

    private void initToolBar() {
        toolbar.setTitle("约聊");
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
}
