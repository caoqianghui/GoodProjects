package com.go.cqh.goodprojects.view;

import android.app.WallpaperManager;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.PixelFormat;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.alibaba.fastjson.JSON;
import com.cqh.greendao.gen.DBeanDao;
import com.go.cqh.goodprojects.R;
import com.go.cqh.goodprojects.base.BaseActivity;
import com.go.cqh.goodprojects.entry.DBean;
import com.go.cqh.goodprojects.entry.ProjectsBean;
import com.go.cqh.goodprojects.utils.DbUtil;
import com.go.cqh.goodprojects.utils.FileDownLoadUtil;
import com.go.cqh.goodprojects.utils.ImgLoadUtils;
import com.go.cqh.goodprojects.utils.ToastUtils;
import com.tencent.smtt.sdk.WebChromeClient;
import com.tencent.smtt.sdk.WebSettings;
import com.tencent.smtt.sdk.WebView;
import com.tencent.smtt.sdk.WebViewClient;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class WebViewActivity extends BaseActivity {

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
    @BindView(R.id.upBtn)
    FloatingActionButton upBtn;
    @BindView(R.id.root)
    CoordinatorLayout root;
    @BindView(R.id.bgImg)
    ImageView bgImg;
    @BindView(R.id.collapsing_toolbar)
    CollapsingToolbarLayout collapsingToolbar;
    private String url;
    private String desc;
    private ProjectsBean.ResultsBean itemBean;
    private List<Integer> list;
    private String id_item;
    private MenuItem collectView;
    private DBean dBean;
    private int tag;

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
        itemBean = ((ProjectsBean.ResultsBean) getIntent().getSerializableExtra("item"));
        id_item = itemBean._id;
        url = itemBean.url;
        desc = itemBean.desc;
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
        if (!itemBean.type.equals("福利")) {
            menu.getItem(4).setVisible(false);
            menu.getItem(5).setVisible(false);
        } else {
            /*福利页隐藏收藏菜单*/
            collectView.setVisible(false);
        }
        /*检查数据库中 这个item*/
        dBean = DbUtil.dBeanDao.queryBuilder().where(DBeanDao.Properties.ItemId.eq(id_item)).build().unique();
        if (dBean != null) {
            /*如果数据不为空，根据收藏状态设置菜单*/
            collectView.setTitle(dBean.getIsCollect() ? "取消收藏" : "收藏");
        } else {//如果数据压根不存在，则自然是没有收藏过的
            collectView.setTitle("收藏");
        }
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
            //收藏
            case R.id.action_collect:
                if (collectView.getTitle().equals("取消收藏")) {
                    collectView.setTitle("收藏");
                    /*将数据库中的这个条目的 收藏状态更新为false*/
                    dBean.setIsCollect(false);
                    DbUtil.dBeanDao.update(dBean);
                    ToastUtils.showToast(getApplicationContext(), "取消收藏成功");
                } else if (collectView.getTitle().equals("收藏")) {
                    collectView.setTitle("取消收藏");
                    if (dBean != null) {
                        /*将数据库中的这个条目的 收藏状态更新为true*/
                        dBean.setIsCollect(true);
                        DbUtil.dBeanDao.update(dBean);
                    } else {
                        DBean dBean = new DBean(null, id_item, JSON.toJSONString(itemBean), true);
                        DbUtil.dBeanDao.insert(dBean);
                    }
                    ToastUtils.showToast(getApplicationContext(), "收藏成功");
                }
                break;
            //下载
            case R.id.download:
                FileDownLoadUtil.down(url, null, "img" + desc + ".jpg", true, true, new FileDownLoadUtil.DownloadListener() {
                    @Override
                    public void start(int progress) {

                    }

                    @Override
                    public void progress(int progress) {

                    }

                    @Override
                    public void finish() {
                        ToastUtils.showToast(getApplicationContext(), "保存成功：" + FileDownLoadUtil.getFilePath(desc));
                    }
                });
                break;
            //设置为壁纸
            case R.id.set:
                final WallpaperManager mWallManager = WallpaperManager.getInstance(getApplicationContext());
                try {
                    final String filePath = FileDownLoadUtil.getFilePath(desc);
                    if (filePath == null) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(this);
                        builder.setMessage("请先进行下载");
                        builder.setIcon(android.R.drawable.ic_dialog_alert);
                        builder.setPositiveButton("下载并设置",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        FileDownLoadUtil.down(url, null, "img" + desc + ".jpg", true, true, new FileDownLoadUtil.DownloadListener() {
                                            @Override
                                            public void start(int progress) {

                                            }

                                            @Override
                                            public void progress(int progress) {

                                            }

                                            @Override
                                            public void finish() {
                                                try {
                                                    mWallManager.setBitmap(BitmapFactory.decodeFile(FileDownLoadUtil.getFilePath(desc)));
                                                } catch (IOException e) {
                                                    e.printStackTrace();
                                                }
                                                ToastUtils.showToast(getApplicationContext(), "设置成功");
                                            }
                                        });
                                    }
                                });
                        builder.setNegativeButton("取消",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                    }
                                });
                        builder.create().show();
                    } else {
                        mWallManager.setBitmap(BitmapFactory.decodeFile(filePath));
                        ToastUtils.showToast(getApplicationContext(), "设置成功");
                    }
                } catch (IOException e) {
                }
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
