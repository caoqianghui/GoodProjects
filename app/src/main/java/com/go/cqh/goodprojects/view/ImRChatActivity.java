package com.go.cqh.goodprojects.view;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.go.cqh.goodprojects.R;
import com.go.cqh.goodprojects.adapter.AiAdapter;
import com.go.cqh.goodprojects.base.BaseActivity;
import com.go.cqh.goodprojects.entry.AiBean;
import com.go.cqh.goodprojects.utils.PrefUtils;
import com.go.cqh.goodprojects.utils.UIUtil;
import com.netease.nimlib.sdk.NIMClient;
import com.netease.nimlib.sdk.Observer;
import com.netease.nimlib.sdk.msg.MessageBuilder;
import com.netease.nimlib.sdk.msg.MsgService;
import com.netease.nimlib.sdk.msg.MsgServiceObserve;
import com.netease.nimlib.sdk.msg.constant.SessionTypeEnum;
import com.netease.nimlib.sdk.msg.model.IMMessage;
import com.zhy.adapter.recyclerview.MultiItemTypeAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static android.support.v7.widget.RecyclerView.SCROLL_STATE_DRAGGING;

public class ImRChatActivity extends BaseActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.rv)
    RecyclerView rv;
    @BindView(R.id.input_et)
    EditText inputEt;
    @BindView(R.id.send)
    Button send;
    @BindView(R.id.mike)
    ImageView mike;
    @BindView(R.id.input_tv)
    TextView input_tv;
    private final int WHAT = 0x11;
    private String url;
    private List<AiBean> list = new ArrayList<>();
    private AiAdapter adapter;
    public static Context context;
    private Observer<List<IMMessage>> incomingMessageObserver;
    private String name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_im);
        ButterKnife.bind(this);
        list = PrefUtils.getList(UIUtil.getContext(), "ImRDataList", AiBean.class);
        name = getIntent().getStringExtra("name");
        if (name.equals("bajie")) {
            name = "八戒";
        }
        if (name.equals("tangseng")) {
            name = "唐僧";
        }
        if (name.equals("shaseng")) {
            name = "沙僧";
        }
        if (name.equals("dasheng")) {
            name = "大圣";
        }
        initIm();
        if (list == null) {
            list = new ArrayList<>();
        } else if (list != null && list.size() > 0) {
            initRv(list);
        }
        inputEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                // 判断输入不为空，按钮可点击
                if (!TextUtils.isEmpty(inputEt.getText().toString())) {
                    send.setEnabled(true);
                    send.setBackground(getResources().getDrawable(R.drawable.sendbtn_select));
                } else {
                    send.setEnabled(false);
                    send.setBackground(getResources().getDrawable(R.drawable.sendbtn_unenable));
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        inputEt.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
            }
        });
        rv.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == SCROLL_STATE_DRAGGING) {

                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                if (dy < 0) {//时为向下滚动
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    if (imm != null) {
                        imm.hideSoftInputFromWindow(getWindow().getDecorView().getWindowToken(), 0);
                    }
                }
            }
        });
        context = this;
        //状态栏沉浸
        setStatusbarColor();
        initToolBar();
    }

    private void initIm() {
        // 处理新收到的消息，为了上传处理方便，SDK 保证参数 messages 全部来自同一个聊天对象。
        incomingMessageObserver = new Observer<List<IMMessage>>() {
            @Override
            public void onEvent(List<IMMessage> messages) {
                String content = messages.get(0).getContent();
                // 处理新收到的消息，为了上传处理方便，SDK 保证参数 messages 全部来自同一个聊天对象。
                AiBean aiBean = new AiBean();
                aiBean.isMyMsg = false;
                aiBean.code = 100000;
                aiBean.text = content;
                list.add(aiBean);
                initRv(list);
            }
        };
        NIMClient.getService(MsgServiceObserve.class)
                .observeReceiveMessage(incomingMessageObserver, true);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        NIMClient.getService(MsgServiceObserve.class).observeReceiveMessage(incomingMessageObserver, false);
    }

    private void initToolBar() {
        toolbar.setTitle(name);
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

    public void goBrower(String url) {
        Intent intent = new Intent();
        intent.setAction("android.intent.action.VIEW");
        Uri content_url = Uri.parse(url);
        intent.setData(content_url);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }


    private LinearLayoutManager linearLayoutManager;

    private void initRv(final List<AiBean> list) {
        if (adapter != null) {
            adapter.notifyItemChanged(list.size() - 1);
        } else {
            linearLayoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);
            rv.setLayoutManager(linearLayoutManager);
            rv.setItemAnimator(new DefaultItemAnimator());
            adapter = new AiAdapter(UIUtil.getContext(), list);
            rv.setAdapter(adapter);
            adapter.setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(View view, RecyclerView.ViewHolder holder, int position) {
                    if (list.get(position).url != null && !list.get(position).url.equals("")) {
                        goBrower(list.get(position).url);
                    }
                }

                @Override
                public boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, int position) {
                    return false;
                }
            });
        }
        rv.scrollToPosition(list.size() - 1);
        PrefUtils.putList(UIUtil.getContext(), "ImRDataList", list);
    }

    public void toggleBtn() {
        if (input_tv.getVisibility() == View.VISIBLE) {
            mike.setImageResource(R.mipmap.ic_cheat_voice);
            input_tv.setVisibility(View.GONE);
            inputEt.setVisibility(View.VISIBLE);
        } else {
            mike.setImageResource(R.mipmap.ic_cheat_keyboard);
            input_tv.setVisibility(View.VISIBLE);
            inputEt.setVisibility(View.GONE);
        }
    }

    @OnClick({R.id.input_tv, R.id.mike, R.id.send})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.input_tv:
                break;
            case R.id.mike:
                toggleBtn();
                break;
            case R.id.send:
                if (!inputEt.getText().toString().equals("")) {
                    AiBean aiBean = new AiBean();
                    aiBean.isMyMsg = true;
                    aiBean.code = 100000;
                    aiBean.text = inputEt.getText().toString();
                    list.add(aiBean);
                    initRv(list);
                    send(inputEt.getText().toString());
                    inputEt.setText("");
                } else {
                    Toast.makeText(this, "请输入内容", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    private void send(String msg) {
            // 创建文本消息
            IMMessage message = MessageBuilder.createTextMessage(name, SessionTypeEnum.P2P,msg);
            // 发送消息。如果需要关心发送结果，可设置回调函数。发送完成时，会收到回调。如果失败，会有具体的错误码。
            NIMClient.getService(MsgService.class).sendMessage(message,true);
    }
}
