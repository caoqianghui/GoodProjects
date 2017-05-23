package com.go.cqh.goodprojects.view;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.go.cqh.goodprojects.R;
import com.go.cqh.goodprojects.base.BaseActivity;
import com.go.cqh.goodprojects.utils.ConstantUtils;
import com.go.cqh.goodprojects.utils.ToastUtils;
import com.yolanda.nohttp.NoHttp;
import com.yolanda.nohttp.RequestMethod;
import com.yolanda.nohttp.rest.OnResponseListener;
import com.yolanda.nohttp.rest.Request;
import com.yolanda.nohttp.rest.RequestQueue;
import com.yolanda.nohttp.rest.Response;
import com.zhy.autolayout.AutoLinearLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class CtsActivity extends BaseActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.key_et)
    EditText keyEt;
    @BindView(R.id.btn)
    Button btn;
    @BindView(R.id.type_tv)
    TextView typeTv;
    @BindView(R.id.num_tv)
    TextView numTv;
    @BindView(R.id.yayuntypetv)
    TextView yayuntypetv;
    @BindView(R.id.content)
    TextView content;
    @BindView(R.id.next)
    Button next;
    @BindView(R.id.copy)
    Button copy;
    @BindView(R.id.activity_cts)
    AutoLinearLayout activityCts;
    @BindView(R.id.layout)
    AutoLinearLayout layout;
    private int type = 1;
    private int num = 5;
    private int yayuntype = 1;
    private String url;
    private RequestQueue requestQueue;
    private final int WHAT = 0;
    private String o;
    private JSONArray list;
    private int pos;

    int item;
    private String[] strings;
    private String[] strings1;
    private String[] strings2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cts);
        requestQueue = NoHttp.newRequestQueue(3);
        ButterKnife.bind(this);
        //支持滑动返回
        flag = true;
        //状态栏沉浸
        setStatusbarColor();
        initToolBar();
    }

    @OnClick({R.id.btn, R.id.type_tv, R.id.num_tv, R.id.yayuntypetv, R.id.next, R.id.copy})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn:
                if (!keyEt.getText().toString().equals("")) {
                    url = String.format(ConstantUtils.CTSHI, keyEt.getText().toString(), num, type, yayuntype);
                    Log.d("---------", url);
                    //创建请求
                    Request<JSONObject> request = NoHttp.createJsonObjectRequest(url, RequestMethod.GET);
                    //将请求添加到队列  NOHTTP_WHAT 这条请求的请求码
                    requestQueue.add(WHAT, request, onResponseListener);
                } else {
                    Toast.makeText(this, "请输入内容", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.type_tv:
                strings = new String[]{"藏头", "藏尾", "藏中", "位置递增", "位置递减"};
                showChoise(strings, 0);
                break;
            case R.id.num_tv:
                strings1 = new String[]{"五言", "七律"};
                showChoise(strings1, 1);
                break;
            case R.id.yayuntypetv:
                strings2 = new String[]{"双句一压", "双句押韵", "一三四押"};
                showChoise(strings2, 2);
                break;
            case R.id.next:
                try {
                    pos++;
                    if (pos <= list.length() - 1) {
                        o = (String) list.get(pos);
                    } else {
                        pos = 0;
                        o = (String) list.get(0);
                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }
                String s2 = "";
                if (o != null && o.contains("。")) {
                    //数据加载完成，填充列表
                    String[] split = o.split("。");
                    for (String s : split) {
                        if (!s.equals("")) {
                            for (String s4 : s.split("，")) {
                                if (!s4.equals("")) {
                                    s2 += s4 + "\n";
                                }
                            }
                        }
                    }
                } else if (o != null && !o.contains("。") && o.contains("，")) {
                    String[] split = o.split("，");
                    for (String s : split) {
                        if (!s.equals("")) {
                            s2 += s + "\n";
                        }
                    }
                } else {
                    ToastUtils.showToast(getApplicationContext(), "没有相应数据");
                }
                content.setText(s2);
                break;
            case R.id.copy:
                if (!content.getText().toString().equals("")) {
                    ClipboardManager cm = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                    cm.setPrimaryClip(ClipData.newPlainText("text", o));
                    ToastUtils.showToast(getApplicationContext(), "复制成功");
                }

                break;
        }
    }


    private int showChoise(String[] s, final int flag) {
        item = 1;
        new AlertDialog.Builder(this).setTitle("请选择").setSingleChoiceItems(s, 0, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                item = i + 1;
                Log.d("*-------item-", item + "");
            }
        }).setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if (flag == 0) {
                    type = item;
                    typeTv.setText(strings[type - 1]);
                }
                if (flag == 1) {
                    if (item == 1) {
                        num = 5;
                    } else {
                        num = 7;
                    }
                    numTv.setText(strings1[item - 1]);
                }
                if (flag == 2) {
                    yayuntype = item;
                    yayuntypetv.setText(strings2[yayuntype - 1]);
                }
            }
        }).setNegativeButton("取消", null).show();
        return item;
    }

    /**
     * 初始化 toolbar
     */
    private void initToolBar() {
        toolbar.setTitle("藏头诗");
        toolbar.setTitleTextColor(Color.WHITE);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        final Drawable upArrow = getResources().getDrawable(R.drawable.abc_ic_ab_back_material );
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
                        JSONObject jsonObject = (JSONObject) response.get();
                        JSONObject showapi_res_body = jsonObject.getJSONObject("showapi_res_body");
                        pos = 0;
                        list = showapi_res_body.getJSONArray("list");
                        o = (String) list.get(pos);
                        String s2 = "";
                        if (o != null && o.contains("。")) {
                            //数据加载完成，填充列表
                            String[] split = o.split("。");
                            for (String s : split) {
                                if (!s.equals("")) {
                                    for (String s4 : s.split("，")) {
                                        if (!s4.equals("")) {
                                            s2 += s4 + "\n";
                                        }
                                    }
                                }
                            }
                        } else if (o != null && !o.contains("。") && o.contains("，")) {
                            String[] split = o.split("，");
                            for (String s : split) {
                                if (!s.equals("")) {
                                    s2 += s + "\n";
                                }
                            }
                        } else {
                            ToastUtils.showToast(getApplicationContext(), "没有相应数据");
                        }
                        content.setText(s2);
                        layout.setVisibility(View.VISIBLE);
                    } catch (JSONException e) {
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
}
