package com.go.cqh.goodprojects.base;

import android.app.Activity;
import android.app.Application;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.go.cqh.goodprojects.utils.DbUtil;
import com.go.cqh.goodprojects.widget.ActivityLifecycleHelper;
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
}
