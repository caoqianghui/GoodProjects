package com.go.cqh.goodprojects.view;

import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.go.cqh.goodprojects.R;
import com.go.cqh.goodprojects.base.BaseActivity;
import com.go.cqh.goodprojects.entry.HistoryBean;
import com.go.cqh.goodprojects.entry.HistoryDetailBean;
import com.go.cqh.goodprojects.utils.ConstantUtils;
import com.go.cqh.goodprojects.utils.ImgLoadUtils;
import com.yolanda.nohttp.NoHttp;
import com.yolanda.nohttp.RequestMethod;
import com.yolanda.nohttp.rest.OnResponseListener;
import com.yolanda.nohttp.rest.Request;
import com.yolanda.nohttp.rest.RequestQueue;
import com.yolanda.nohttp.rest.Response;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HistoryDetailActivity extends BaseActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.history_pic)
    ImageView historyPic;
    @BindView(R.id.history_pic_title)
    TextView historyPicTitle;
    @BindView(R.id.history_content)
    TextView historyContent;
    private HistoryBean.ResultBean histroy;
    private final int WHAT = 01;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history_detail);
        ButterKnife.bind(this);
        //支持滑动返回
        flag = true;
        //状态栏沉浸
        setStatusbarColor();
        histroy = ((HistoryBean.ResultBean) getIntent().getSerializableExtra("histroy"));
        initToolBar();
        init();
    }

    private void init() {
        RequestQueue requestQueue = NoHttp.newRequestQueue(3);
        String  url = String.format(ConstantUtils.HISTORY_DETAIL, histroy.e_id);
        //创建请求
        Request<JSONObject> request = NoHttp.createJsonObjectRequest(url, RequestMethod.GET);
        requestQueue.add(WHAT, request, new OnResponseListener<JSONObject>() {
            @Override
            public void onStart(int what) {

            }

            @Override
            public void onSucceed(int what, Response<JSONObject> response) {
                JSONObject jsonObject =  response.get();
                try {
                    JSONArray result = jsonObject.getJSONArray("result");
                    Log.d("----------result---", result.toString());

                    List<HistoryDetailBean.ResultBean> resultBeen = JSON.parseArray(result.toString(),HistoryDetailBean.ResultBean.class);
                    HistoryDetailBean.ResultBean resultBean = resultBeen.get(0);
                    if (!"0".equals(resultBean.picNo)) {
                        if (resultBean.picUrl.get(0) != null && !resultBean.picUrl.get(0).equals("")) {
                            ImgLoadUtils.load(historyPic,resultBean.picUrl.get(0).url,null);
                            historyPicTitle.setText(resultBean.picUrl.get(0).pic_title);
                        }
                    }
                    historyContent.setText(Html.fromHtml(resultBean.content,null, null));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailed(int what, String url, Object tag, Exception exception, int responseCode, long networkMillis) {

            }

            @Override
            public void onFinish(int what) {

            }
        });
    }


    private void initToolBar() {
        toolbar.setTitle(histroy.title);
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
}
