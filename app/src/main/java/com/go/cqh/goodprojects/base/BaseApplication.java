package com.go.cqh.goodprojects.base;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Environment;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;

import com.go.cqh.goodprojects.R;
import com.go.cqh.goodprojects.utils.DbUtil;
import com.go.cqh.goodprojects.utils.PrefUtils;
import com.go.cqh.goodprojects.utils.SystemUtil;
import com.go.cqh.goodprojects.view.MainActivity;
import com.go.cqh.goodprojects.widget.ActivityLifecycleHelper;
import com.netease.nimlib.sdk.NIMClient;
import com.netease.nimlib.sdk.SDKOptions;
import com.netease.nimlib.sdk.StatusBarNotificationConfig;
import com.netease.nimlib.sdk.auth.LoginInfo;
import com.netease.nimlib.sdk.msg.constant.SessionTypeEnum;
import com.netease.nimlib.sdk.uinfo.UserInfoProvider;
import com.taobao.android.SophixManager;
import com.taobao.android.listener.PatchLoadStatusListener;
import com.taobao.android.util.PatchStatus;
import com.tencent.bugly.Bugly;
import com.tencent.smtt.sdk.QbSdk;
import com.yolanda.nohttp.NoHttp;

/**
 * Created by caoqianghui on 2016/11/17.
 */

public class BaseApplication extends Application {
    private ActivityLifecycleHelper mActivityLifecycleHelper;
    private static BaseApplication instance;

    @Override
    public void onCreate() {
        super.onCreate();
        initApp();
        //NIMPushClient.registerMixPushMessageHandler(new DemoMixPushMessageHandler());
        NIMClient.init(this,new LoginInfo(PrefUtils.getString(this,"name"),PrefUtils.getString(this,"token")),options());
        if (inMainProcess(getApplicationContext())) {
            // 初始化UIKit模块
            initUIKit();
        }
        /*Hotfix 2.0热更新*/
        SophixManager.getInstance().setContext(this)
                .setAppVersion(appVersion)
                .setAesKey(null)
                .setEnableDebug(true)
                .setPatchLoadStatusStub(new PatchLoadStatusListener() {
                    //参数说明如下:
                    //
                    //mode: 补丁模式, 0:正常请求模式 1:扫码模式 2:本地补丁模式
                    //code: 补丁加载状态码, 详情查看PatchStatusCode类说明
                    //info: 补丁加载详细说明, 详情查看PatchStatusCode类说明
                    //handlePatchVersion: 当前处理的补丁版本号, 0:无 -1:本地补丁 其它:后台补丁
                    @Override
                    public void onload(final int mode, final int code, final String info, final int handlePatchVersion) {
                        // 补丁加载回调通知
                        if (code == PatchStatus.CODE_LOAD_SUCCESS) {
                            Log.d("------", "补丁加载成功");
                            // TODO: 10/24/16 表明补丁加载成功
                        } else if (code == PatchStatus.CODE_LOAD_RELAUNCH) {
                            // TODO: 10/24/16 表明新补丁生效需要重启. 业务方可自行实现逻辑, 提示用户或者强制重启, 建议: 用户可以监听进入后台事件, 然后应用自杀

                        } else if (code == PatchStatus.CODE_LOAD_FAIL) {
                            // 内部引擎加载异常, 推荐此时清空本地补丁, 但是不清空本地版本号, 防止失败补丁重复加载
                            SophixManager.getInstance().cleanPatches();
                        } else {
                            // TODO: 10/25/16 其它错误信息, 查看PatchStatusCode类说明
                            Log.d("------", "错误信息--->>" + "mode:" + mode + ",code:" + code + ",info:" + info + ",handlePatchVersion:" + handlePatchVersion);
                        }
                    }
                }).initialize();
        instance = this;
        registerActivityLifecycleCallbacks(mActivityLifecycleHelper = new ActivityLifecycleHelper());
        //初始化X5内核
        QbSdk.initX5Environment(getApplicationContext(), null);
        // 初始化NoHttp
        NoHttp.initialize(this);
        //设置全局默认超时时间
        NoHttp.setDefaultConnectTimeout(10000);
        NoHttp.setDefaultReadTimeout(10000);
        /*i初始化数据库*/
        DbUtil.init(this, "cache_db");
        /**
         * 初始化bugly
         * 参数1：上下文对象
         * 参数2：注册时申请的APPID
         * 参数3：是否开启debug模式，true表示打开debug模式，false表示关闭调试模式
         */
        Bugly.init(getApplicationContext(), "de5e809256", false);
    }

    public static String appVersion;
    public static String appId;

    private void initApp() {
        this.appId = "100008-1"; //替换掉自己应用的appId
        try {
            this.appVersion = this.getPackageManager().getPackageInfo(this.getPackageName(), 0).versionName;
        } catch (Exception e) {
            this.appVersion = "2.0";
        }
    }

    public static BaseApplication getInstance() {
        // 这里不用判断instance是否为空
        return instance;
    }

    public ActivityLifecycleHelper getActivityLifecycleHelper() {
        return mActivityLifecycleHelper;
    }

    public void onSlideBack(boolean isReset, float distance) {
        if (mActivityLifecycleHelper != null) {
            Activity lastActivity = mActivityLifecycleHelper.getPreActivity();
            if (lastActivity != null) {
                View contentView = lastActivity.findViewById(android.R.id.content);
                if (isReset) {
                    contentView.setX(contentView.getLeft());
                } else {
                    final int width = getResources().getDisplayMetrics().widthPixels;
                    contentView.setX(-width / 3 + distance / 3);
                }
            }
        }
    }
    public static boolean inMainProcess(Context context) {
        String packageName = context.getPackageName();
        String processName = SystemUtil.getProcessName(context);
        return packageName.equals(processName);
    }
    private void initUIKit() {
        // 初始化，使用 uikit 默认的用户信息提供者
//        NimUIKit.init(this);

        // 设置地理位置提供者。如果需要发送地理位置消息，该参数必须提供。如果不需要，可以忽略。
//        NimUIKit.setLocationProvider(new NimDemoLocationProvider());

        // 会话窗口的定制初始化。
//        SessionHelper.init();

        // 通讯录列表定制初始化
//        ContactHelper.init();
        // 添加自定义推送文案以及选项，请开发者在各端（Android、IOS、PC、Web）消息发送时保持一致，以免出现通知不一致的情况
        // NimUIKit.CustomPushContentProvider(new DemoPushContentProvider());

//        NimUIKit.setOnlineStateContentProvider(new DemoOnlineStateContentProvider());
    }
    /*================== 网易云信 Begin ==================*/
    // 如果返回值为 null，则全部使用默认参数。
    private SDKOptions options() {
        SDKOptions options = new SDKOptions();

        // 如果将新消息通知提醒托管给 SDK 完成，需要添加以下配置。否则无需设置。
        StatusBarNotificationConfig config = new StatusBarNotificationConfig();
        config.notificationEntrance = MainActivity.class; // 点击通知栏跳转到该Activity
        config.notificationSmallIconId = R.mipmap.ic_launcher;
        // 呼吸灯配置
        config.ledARGB = Color.GREEN;
        config.ledOnMs = 1000;
        config.ledOffMs = 1500;
        // 通知铃声的uri字符串
        config.notificationSound = "android.resource://com.lqr.wechat/raw/msg";
        options.statusBarNotificationConfig = config;

        // 配置保存图片，文件，log 等数据的目录
        // 如果 options 中没有设置这个值，SDK 会使用下面代码示例中的位置作为 SDK 的数据目录。
        // 该目录目前包含 log, file, image, audio, video, thumb 这6个目录。
        // 如果第三方 APP 需要缓存清理功能， 清理这个目录下面个子目录的内容即可。
        String sdkPath = Environment.getExternalStorageDirectory() + "/" + getPackageName() + "/nim";
        options.sdkStorageRootPath = sdkPath;

        // 配置是否需要预下载附件缩略图，默认为 true
        options.preloadAttach = true;

        // 配置附件缩略图的尺寸大小。表示向服务器请求缩略图文件的大小
        // 该值一般应根据屏幕尺寸来确定， 默认值为 Screen.width / 2
        options.thumbnailSize = 720 / 2;

        // 用户资料提供者, 目前主要用于提供用户资料，用于新消息通知栏中显示消息来源的头像和昵称
        options.userInfoProvider = new UserInfoProvider() {
            @Override
            public UserInfo getUserInfo(String account) {
                return null;
            }

            @Override
            public int getDefaultIconResId() {
                return R.mipmap.man;
            }

            @Override
            public Bitmap getTeamIcon(String tid) {
                return null;
            }

            @Override
            public Bitmap getAvatarForMessageNotifier(String account) {
                return null;
            }

            @Override
            public String getDisplayNameForMessageNotifier(String account, String sessionId, SessionTypeEnum sessionType) {
                return null;
            }
        };
        return options;
    }
}
