package com.go.cqh.goodprojects.utils;

import android.os.Environment;
import android.support.annotation.NonNull;
import android.util.Log;

import com.yolanda.nohttp.Headers;
import com.yolanda.nohttp.NoHttp;
import com.yolanda.nohttp.download.DownloadQueue;
import com.yolanda.nohttp.download.DownloadRequest;

import java.io.File;

/**
 * 封装一个文件下载工具类  基于nohttp
 * Created by caoqianghui on 2016/11/19.
 */

public class FileDownLoadUtil {
    private static DownloadQueue downloadQueue;
    /**
     * 默认保存路径
     */
    private static String path = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + "imgs";
    private static DownloadRequest downloadRequest;

    public interface DownloadListener {
        void start(int progress);

        void progress(int progress);

        void finish();
    }

    /**
     * 下载
     *
     * @param url         下载url
     * @param fileFolder  文件保存路径
     * @param filename    保存文件名
     * @param isRange     是否支持断点
     * @param isDeleteOld 如果发现已有此文件，是否要删除旧文件
     */
    public static void down(@NonNull String url, String fileFolder, @NonNull String filename, boolean isRange, boolean isDeleteOld, @NonNull final DownloadListener listener) {
        //创建一个下载队列
        if (downloadQueue == null) {
            downloadQueue = NoHttp.newDownloadQueue(3);
        }
        if (fileFolder == null) {
            downloadRequest = NoHttp.createDownloadRequest(url, path, filename, isRange, isDeleteOld);
        } else {
            path = fileFolder;
            downloadRequest = NoHttp.createDownloadRequest(url, fileFolder, filename, isRange, isDeleteOld);
        }
        downloadQueue.add(0, downloadRequest, new com.yolanda.nohttp.download.DownloadListener() {
            @Override
            public void onDownloadError(int what, Exception exception) {

            }

            @Override
            public void onStart(int what, boolean isResume, long rangeSize, Headers responseHeaders, long allCount) {
                int progress = 0;
                if (allCount != 0) {
                    progress = (int) (rangeSize * 100 / allCount);
                }
                listener.start(progress);
            }

            @Override
            public void onProgress(int what, int progress, long fileCount) {
                listener.progress(progress);
            }

            @Override
            public void onFinish(int what, String filePath) {
                listener.finish();
            }

            @Override
            public void onCancel(int what) {

            }
        });

    }

    /**
     * 获得文件路径
     *
     * @param desc
     * @return
     */
    public static String getFilePath(String desc) {
        File f = new File(path, "img" + desc + ".jpg");
        if (!f.exists()) {
            return null;
        }
        return f.getAbsolutePath();
    }
}
