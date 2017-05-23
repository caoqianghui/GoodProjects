package com.go.cqh.goodprojects.view;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.go.cqh.goodprojects.R;
import com.go.cqh.goodprojects.base.BaseActivity;
import com.go.cqh.goodprojects.utils.ImgLoadUtils;
import com.go.cqh.goodprojects.utils.ToastUtils;
import com.tencent.smtt.sdk.WebChromeClient;
import com.tencent.smtt.sdk.WebSettings;
import com.tencent.smtt.sdk.WebView;
import com.tencent.smtt.sdk.WebViewClient;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class CartoonDetailActivity extends BaseActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.progressBar)
    ProgressBar progressBar;
    @BindView(R.id.appbar)
    AppBarLayout appbar;
    @BindView(R.id.webView)
    WebView webView;
    @BindView(R.id.nestedscrollview)
    NestedScrollView nestedscrollview;
    @BindView(R.id.bgImg)
    ImageView bgImg;
    @BindView(R.id.collapsing_toolbar)
    CollapsingToolbarLayout collapsingToolbar;
    private String url;
    private String desc;
    private List<Integer> list;
    private MenuItem collectView;

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);
        ButterKnife.bind(this);
        initImgs();
        //支持滑动返回
        flag = true;
        //避免webview视频闪屏
        getWindow().setFormat(PixelFormat.TRANSLUCENT);
        //状态栏沉浸
        setStatusbarColor();
        url = getIntent().getStringExtra("url");
        desc = getIntent().getStringExtra("desc");
        //if (itemBean.images == null || itemBean.images.size() < 1) {
        ImgLoadUtils.loadBlur(bgImg, list.get(new Random().nextInt(10)));
        //} else {
        //    ImgLoadUtils.loadBlurFromUrl(bgImg,itemBean.images.get(0)+ "?imageView2/0/w/200");
        //}
        progressBar.setMax(100);
        initToolBar();
        collapsingToolbar.setExpandedTitleColor(ContextCompat.getColor(getBaseContext(), R.color.maincolor));//设置还没收缩时状态下字体颜色
        collapsingToolbar.setCollapsedTitleTextColor(ContextCompat.getColor(getBaseContext(), R.color.colorWhite));//设置收缩后Toolbar上字体的颜色
        initWebView();
    }

    private void initImgs() {
        list = new ArrayList<>();
        list.add(R.mipmap.s01);
        list.add(R.mipmap.s02);
        list.add(R.mipmap.s03);
        list.add(R.mipmap.s04);
        list.add(R.mipmap.s05);
        list.add(R.mipmap.s06);
        list.add(R.mipmap.s07);
        list.add(R.mipmap.s08);
        list.add(R.mipmap.s09);
        list.add(R.mipmap.s12);
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
        toolbar.setTitle(desc);
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
     * 初始化 webview
     */
    private void initWebView() {
        webView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return false;
            }
        });
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });
        WebSettings settings = webView.getSettings();
        if (Build.VERSION.SDK_INT >= 19) {
            settings.setLoadsImagesAutomatically(true);
        } else {
            settings.setLoadsImagesAutomatically(false);
        }
        settings.setSupportZoom(true);
        settings.setBuiltInZoomControls(true);
        settings.setUseWideViewPort(true);
        settings.setLoadWithOverviewMode(true);
        settings.setJavaScriptEnabled(true);
        settings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        settings.setRenderPriority(WebSettings.RenderPriority.HIGH);
        // 开启 DOM storage API 功能
        settings.setDomStorageEnabled(true);
        /*图片之后加载*/
        settings.setBlockNetworkImage(false);
        // 开启H5(APPCache)缓存功能
        settings.setAppCacheEnabled(true);
        settings.setDefaultTextEncodingName("utf-8");
        webView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                if (newProgress == 100) {
                    progressBar.setVisibility(View.GONE);
                } else {
                    progressBar.setProgress(newProgress);
                }
            }
        });
        webView.loadUrl(url);
    }

    /**
     * 点击事件
     */
    @OnClick(R.id.upBtn)
    public void onClick() {
        nestedscrollview.fullScroll(View.FOCUS_UP);
    }

    /**
     * 创建toolbar菜单
     *
     * @param menu
     * @return
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.web_view_menu, menu);
        collectView = menu.findItem(R.id.action_collect);
        menu.getItem(4).setVisible(false);
        menu.getItem(5).setVisible(false);
        /*福利页隐藏收藏菜单*/
        collectView.setVisible(false);
        return true;
    }

    /**
     * toolbar 菜单点击事件
     *
     * @param item
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            //使用浏览器打开
            case R.id.action_open:
                Intent intent = new Intent();
                intent.setAction("android.intent.action.VIEW");
                Uri content_url = Uri.parse(url);
                intent.setData(content_url);
                startActivity(intent);
                break;
            //复制链接
            case R.id.copyUrl:
                //复制文字到粘贴板
                ClipboardManager myClipboard = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
                myClipboard.setPrimaryClip(ClipData.newPlainText("url", url));
                ToastUtils.showToast(getApplicationContext(), "复制成功");
                break;
            //分享
            case R.id.shareWeb:
                ToastUtils.showToast(getApplicationContext(), "开发中");
                break;
        }
        return super.onOptionsItemSelected(item);
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
            if (webView.canGoBack()) {
                webView.goBack();
                return true;
            } else {
                finish();
            }
        }
        return super.onKeyDown(keyCode, event);
    }
}
