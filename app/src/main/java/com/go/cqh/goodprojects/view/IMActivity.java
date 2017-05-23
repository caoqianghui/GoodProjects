package com.go.cqh.goodprojects.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
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
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.go.cqh.goodprojects.R;
import com.go.cqh.goodprojects.adapter.AiAdapter;
import com.go.cqh.goodprojects.base.BaseActivity;
import com.go.cqh.goodprojects.entry.AiBean;
import com.go.cqh.goodprojects.entry.DictationResult;
import com.go.cqh.goodprojects.utils.ConstantUtils;
import com.go.cqh.goodprojects.utils.PrefUtils;
import com.go.cqh.goodprojects.utils.ToastUtils;
import com.go.cqh.goodprojects.utils.UIUtil;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.iflytek.cloud.ErrorCode;
import com.iflytek.cloud.InitListener;
import com.iflytek.cloud.RecognizerResult;
import com.iflytek.cloud.SpeechConstant;
import com.iflytek.cloud.SpeechError;
import com.iflytek.cloud.SpeechUtility;
import com.iflytek.cloud.ui.RecognizerDialog;
import com.iflytek.cloud.ui.RecognizerDialogListener;
import com.yolanda.nohttp.NoHttp;
import com.yolanda.nohttp.RequestMethod;
import com.yolanda.nohttp.rest.OnResponseListener;
import com.yolanda.nohttp.rest.Request;
import com.yolanda.nohttp.rest.RequestQueue;
import com.yolanda.nohttp.rest.Response;
import com.zhy.adapter.recyclerview.MultiItemTypeAdapter;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static android.support.v7.widget.RecyclerView.SCROLL_STATE_DRAGGING;
import static com.yolanda.nohttp.NoHttp.getContext;

public class IMActivity extends BaseActivity {

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
    private RequestQueue requestQueue;
    private final int WHAT = 0x11;
    private String url;
    private List<AiBean> list = new ArrayList<>();
    private AiAdapter adapter;
    public static Context context;
    private RecognizerDialog iatDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_im);
        ButterKnife.bind(this);
        /*初始化讯飞语音*/
        SpeechUtility.createUtility(getApplicationContext(), SpeechConstant.APPID + "=58c8e250");
        list = PrefUtils.getList(UIUtil.getContext(), "datalist", AiBean.class);
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
        requestQueue = NoHttp.newRequestQueue(3);
        //支持滑动返回
        flag = true;
        //状态栏沉浸
        setStatusbarColor();
        initToolBar();
    }

    private void initToolBar() {
        toolbar.setTitle("萌小O");
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
                case WHAT:
                    try {
                        JSONObject object = (JSONObject) response.get();
                        AiBean aiBean = JSON.parseObject(object.toString(), AiBean.class);
                        list.add(aiBean);
                        initRv(list);
                        Log.d("---------", object.toString());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;
            }
        }

        @Override
        public void onFailed(int what, String url, Object tag, Exception exception, int responseCode, long networkMillis) {
            ToastUtils.showToast(getApplicationContext(), "无法连接网络，请稍后重试");
        }

        @Override
        public void onFinish(int what) {

        }
    };
    private LinearLayoutManager linearLayoutManager;

    private void initRv(final List<AiBean> list) {
        if (adapter != null) {
            adapter.notifyItemChanged(list.size() - 1);
        } else {
            linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
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
        PrefUtils.putList(UIUtil.getContext(), "datalist", list);
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
// ②初始化有交互动画的语音识别器
                iatDialog = new RecognizerDialog(IMActivity.this, mInitListener);
                //③设置监听，实现听写结果的回调
                iatDialog.setListener(new RecognizerDialogListener() {
                    String resultJson = "[";//放置在外边做类的变量则报错，会造成json格式不对（？）

                    @Override
                    public void onResult(RecognizerResult recognizerResult, boolean isLast) {
                        System.out.println("-----------------   onResult   -----------------");
                        if (!isLast) {
                            resultJson += recognizerResult.getResultString() + ",";
                        } else {
                            resultJson += recognizerResult.getResultString() + "]";
                        }

                        if (isLast) {
                            //解析语音识别后返回的json格式的结果
                            Gson gson = new Gson();
                            List<DictationResult> resultList = gson.fromJson(resultJson,
                                    new TypeToken<List<DictationResult>>() {
                                    }.getType());
                            String result = "";
                            for (int i = 0; i < resultList.size() - 1; i++) {
                                result += resultList.get(i).toString();
                            }
                            if (!result.equals("")) {
                                AiBean aiBean = new AiBean();
                                aiBean.isMyMsg = true;
                                aiBean.code = 100000;
                                aiBean.text = result;
                                list.add(aiBean);
                                initRv(list);
                                url = ConstantUtils.AI;
                                //创建请求
                                Request<JSONObject> request = NoHttp.createJsonObjectRequest(url, RequestMethod.POST);
                                request.add("key", "9b9488bde9da4e0eb5ade3aa5a9bc8b4");
                                request.add("info", result);
                                request.add("userid", "123456");
                                //将请求添加到队列  NOHTTP_WHAT 这条请求的请求码
                                requestQueue.add(WHAT, request, onResponseListener);
                            }
                            //inputEt.setText(result);
                            ////获取焦点
                            //inputEt.requestFocus();
                            ////将光标定位到文字最后，以便修改
                            //inputEt.setSelection(result.length());
                        }
                    }

                    @Override
                    public void onError(SpeechError speechError) {
                        //自动生成的方法存根
                        speechError.getPlainDescription(true);
                    }
                });
                //开始听写，需将sdk中的assets文件下的文件夹拷入项目的assets文件夹下（没有的话自己新建）
                iatDialog.show();
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
                    url = ConstantUtils.AI;
                    //创建请求
                    Request<JSONObject> request = NoHttp.createJsonObjectRequest(url, RequestMethod.POST);
                    request.add("key", "9b9488bde9da4e0eb5ade3aa5a9bc8b4");
                    request.add("info", inputEt.getText().toString());
                    inputEt.setText("");
                    request.add("userid", "123456");
                    //将请求添加到队列  NOHTTP_WHAT 这条请求的请求码
                    requestQueue.add(WHAT, request, onResponseListener);
                } else {
                    Toast.makeText(this, "请输入内容", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    /**
     * 初始化监听器。
     */
    private InitListener mInitListener = new InitListener() {

        @Override
        public void onInit(int code) {
            Log.d("------", "SpeechRecognizer init() code = " + code);
            if (code != ErrorCode.SUCCESS) {
                ToastUtils.showToast(UIUtil.getContext(), "初始化失败，错误码：" + code);
            }
        }
    };
}
