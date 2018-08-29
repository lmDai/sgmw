package com.uiho.sgmw.module_login.ui;

import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.FileProvider;
import android.util.Log;
import android.widget.RemoteViews;

import com.uiho.sgmw.common.Constants;
import com.uiho.sgmw.common.utils.SPUtils;
import com.uiho.sgmw.common.utils.Utils;
import com.uiho.sgmw.module_login.R;
import com.uiho.sgmw.module_login.base.MainModule;
import com.uiho.sgmw.module_login.ui.callback.DownloadCallBack;

import java.io.File;
import java.io.InputStream;
import java.io.RandomAccessFile;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import okhttp3.ResponseBody;

/**
 * App更新Service
 */
public class AppUpdateService extends IntentService {

    private static final String TAG = "DownloadIntentService";
    private NotificationManager mNotifyManager;
    private String mDownloadFileName;
    private Notification mNotification;
    public static final String PRIMARY_CHANNEL = "default";
    NotificationCompat.Builder mBuilder;

    public AppUpdateService() {
        super("InitializeService");
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        String downloadUrl = intent.getExtras().getString("download_url");
        final int downloadId = intent.getExtras().getInt("download_id");
        mDownloadFileName = intent.getExtras().getString("download_file");
        Log.d(TAG, "download_url --" + downloadUrl);
        Log.d(TAG, "download_file --" + mDownloadFileName);
        final File file = new File(Constants.APP_ROOT_PATH + Constants.DOWNLOAD_DIR + mDownloadFileName);
        long range = 0;
        int progress = 0;
        if (file.exists()) {
            range = (long) SPUtils.getParam(Utils.getContext(), downloadUrl, 0L);
            progress = (int) (range * 100 / file.length());
            if (range == file.length()) {
                installApp(file);
                return;
            }
        }
        Log.d(TAG, "range = " + range);
        final RemoteViews remoteViews = new RemoteViews(getPackageName(), R.layout.notify_download);
        remoteViews.setProgressBar(R.id.pb_progress, 100, progress, false);
        remoteViews.setTextViewText(R.id.tv_progress, "已下载" + progress + "%");
        mNotifyManager = (NotificationManager)
                getSystemService(Context.NOTIFICATION_SERVICE);
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel chan1 = new NotificationChannel(PRIMARY_CHANNEL,
                    "Primary Channel", NotificationManager.IMPORTANCE_DEFAULT);
            chan1.setLightColor(Color.GREEN);
            chan1.setLockscreenVisibility(Notification.VISIBILITY_PRIVATE);
//            chan1.setSound(null, null);
            mNotifyManager.createNotificationChannel(chan1);
            mBuilder = new NotificationCompat.Builder(Utils.getContext(), PRIMARY_CHANNEL);

        } else {
            mBuilder = new NotificationCompat.Builder(Utils.getContext(), null);
        }
        mBuilder.setContent(remoteViews)
                .setTicker("正在下载")
                .setOnlyAlertOnce(true)
                .setSmallIcon(R.drawable.che);
        mNotification = mBuilder.build();
        mNotifyManager.notify(downloadId, mNotification);
        downloadFile(range, downloadUrl, mDownloadFileName, new DownloadCallBack() {
            @Override
            public void onProgress(int progress) {
                remoteViews.setProgressBar(R.id.pb_progress, 100, progress, false);
                remoteViews.setTextViewText(R.id.tv_progress, "已下载" + progress + "%");
                mNotifyManager.notify(downloadId, mNotification);
            }

            @Override
            public void onCompleted() {
                Log.d(TAG, "下载完成");
                mNotifyManager.cancel(downloadId);
                installApp(file);
            }

            @Override
            public void onError(String msg) {
                mNotifyManager.cancel(downloadId);
                Log.d(TAG, "下载发生错误--" + msg);
            }
        });
    }

    public void downloadFile(final long range, final String url, final String fileName, final DownloadCallBack downloadCallback) {
        //断点续传时请求的总长度
        File file = new File(Constants.APP_ROOT_PATH + Constants.DOWNLOAD_DIR, fileName);
        String totalLength = "-";
        if (file.exists()) {
            totalLength += file.length();
        }
        MainModule.createDownLoadRetrofit().executeDownload("bytes=" + Long.toString(range) + totalLength, url)
                .subscribe(new Observer<ResponseBody>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(ResponseBody responseBody) {
                        RandomAccessFile randomAccessFile = null;
                        InputStream inputStream = null;
                        long total = range;
                        long responseLength = 0;
                        try {
                            byte[] buf = new byte[2048];
                            int len = 0;
                            responseLength = responseBody.contentLength();
                            inputStream = responseBody.byteStream();
                            String filePath = Constants.APP_ROOT_PATH + Constants.DOWNLOAD_DIR;
                            File file = new File(filePath, fileName);
                            File dir = new File(filePath);
                            if (!dir.exists()) {
                                dir.mkdirs();
                            }
                            randomAccessFile = new RandomAccessFile(file, "rwd");
                            if (range == 0) {
                                randomAccessFile.setLength(responseLength);
                            }
                            randomAccessFile.seek(range);
                            int progress = 0;
                            int lastProgress = 0;

                            while ((len = inputStream.read(buf)) != -1) {
                                randomAccessFile.write(buf, 0, len);
                                total += len;
                                lastProgress = progress;
                                progress = (int) (total * 100 / randomAccessFile.length());
                                if (progress > 0 && progress != lastProgress) {
                                    downloadCallback.onProgress(progress);
                                }
                                Log.i(TAG, progress + "");
                            }
                            downloadCallback.onCompleted();
                        } catch (Exception e) {
                            Log.d(TAG, e.getMessage());
                            downloadCallback.onError(e.getMessage());
                            e.printStackTrace();
                        } finally {
                            try {
                                SPUtils.setParam(Utils.getContext(), url, total);
                                if (randomAccessFile != null) {
                                    randomAccessFile.close();
                                }

                                if (inputStream != null) {
                                    inputStream.close();
                                }

                            } catch (Exception e) {
                                e.printStackTrace();
                            }

                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        downloadCallback.onError(e.toString());
                    }

                    @Override
                    public void onComplete() {
                    }
                });
    }

    private void installApp(File file) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
//判断是否是AndroidN以及更高的版本
        String authority = getPackageName() + ".provider";
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            Uri contentUri = FileProvider.getUriForFile(Utils.getContext(), authority, file);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            intent.setDataAndType(contentUri, "application/vnd.android.package-archive");
        } else {
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.setDataAndType(Uri.fromFile(file), "application/vnd.android.package-archive");
        }
        startActivity(intent);
    }

}
