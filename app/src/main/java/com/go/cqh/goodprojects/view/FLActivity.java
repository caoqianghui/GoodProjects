package com.go.cqh.goodprojects.view;

import android.app.WallpaperManager;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.PixelFormat;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.go.cqh.goodprojects.R;
import com.go.cqh.goodprojects.base.BaseActivity;
import com.go.cqh.goodprojects.entry.SexImgBean;
import com.go.cqh.goodprojects.utils.FileDownLoadUtil;
import com.go.cqh.goodprojects.utils.ImgLoadUtils;
import com.go.cqh.goodprojects.utils.ToastUtils;
import com.go.cqh.goodprojects.widget.GoImageView;

import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FLActivity extends BaseActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.appbar)
    AppBarLayout appbar;
    @BindView(R.id.root)
    CoordinatorLayout root;
    @BindView(R.id.goImg)
    GoImageView goImg;
    private String url;
    private String desc;
    private SexImgBean.ShowapiResBodyBean.NewslistBean item;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fuli);
        ButterKnife.bind(this);
        //支持滑动返回
        flag = true;
        //避免webview视频闪屏
        getWindow().setFormat(PixelFormat.TRANSLUCENT);
        //状态栏沉浸
        setStatusbarColor();
        item = ((SexImgBean.ShowapiResBodyBean.NewslistBean) getIntent().getSerializableExtra("item"));
        url = item.picUrl;
        desc = item.title;
        goImg.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                return false;
            }
        });
        //加载图片
        ImgLoadUtils.loadAsBitmap(goImg, url, null);
        initToolBar();
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
     * 创建toolbar菜单
     *
     * @param menu
     * @return
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.web_view_menu, menu);
        menu.getItem(4).setVisible(true);
        menu.getItem(5).setVisible(true);
        //menu.findItem(R.id.action_collect).setTitle(isCollected?"取消收藏":"收藏");
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
                ToastUtils.showToast(getApplicationContext(), "开发中");
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
            finish();
        }
        return super.onKeyDown(keyCode, event);
    }
}
