package com.meida.cosmeticsshopuser.utils;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Environment;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RemoteViews;
import android.widget.TextView;


import com.meida.cosmeticsshopuser.Bean.BaseBean;
import com.meida.cosmeticsshopuser.MyView.MUIToast;
import com.meida.cosmeticsshopuser.MyView.numsprogress.NumberProgressBar;
import com.meida.cosmeticsshopuser.R;
import com.meida.cosmeticsshopuser.nohttp.CallServer;
import com.yolanda.nohttp.Headers;
import com.yolanda.nohttp.NoHttp;
import com.yolanda.nohttp.download.DownloadListener;
import com.yolanda.nohttp.download.DownloadRequest;

import java.io.File;
import java.text.DecimalFormat;

import static android.content.Context.NOTIFICATION_SERVICE;

/**
 * Created by LFC
 * on 2017/10/25.
 * 下载更新
 */

public class Download_NohttpUtils {

    private Activity context;

    private DownloadRequest downloadRequest;
    private NumberProgressBar pb;
    private NotificationManager notificationManager;
    private RemoteViews remoteView;
    private NotificationCompat.Builder builder_notify;
    private final int notificationId = 100002;
    private static boolean is_bg = false; // 是否后台下载
    private AlertDialog dialog;


    /**
     * @param str_url       下载连接
     * @param context1
     * @param is_foceupload 是否强制更新
     */
    public void downMyfile(String str_url, final Activity context1, final boolean is_foceupload) {
        context = context1;
        LinearLayout tuichudia = (LinearLayout) context.getLayoutInflater().inflate(R.layout.download_dialog, null);
        AlertDialog.Builder builder = new AlertDialog.Builder(context).setView(tuichudia);
        dialog = builder.show();
        is_bg = false;
        pb = (NumberProgressBar) tuichudia.findViewById(R.id.progress_bar_download_dialog);
        TextView lay_bg = (TextView) tuichudia.findViewById(R.id.lay_down_bg_dialog);

        if (is_foceupload) {
            lay_bg.setVisibility(View.GONE);
            dialog.setCancelable(false);
            dialog.setCanceledOnTouchOutside(false);
        } else
            lay_bg.setVisibility(View.VISIBLE);

        lay_bg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendDefaultNotification();
                is_bg = true;
                dialog.dismiss();

            }
        });


        dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
//                如果是非必要更新  弹窗消失的时候 取消下载
              /*  if (!is_foceupload)
                    downloadRequest.cancel();*/
                if (!downloadRequest.isFinished()) {
                    sendDefaultNotification();
                    is_bg = true;
                }
            }
        });


        final String file_path = Environment.getExternalStorageDirectory().getPath() + File.separator + "HuaDou" + File.separator + "Apks";
        downloadRequest = NoHttp.createDownloadRequest(str_url, file_path, "text_a.apk", true, true);
        notificationManager = (NotificationManager) context.getSystemService(NOTIFICATION_SERVICE);
//        Request<String> mRequest = NoHttp.createStringRequest(HttpIp.URL, Const.POST);
        CallServer.getDownloadInstance().add(100, downloadRequest, new DownloadListener() {
            public long filesize = 0;

            @Override
            public void onDownloadError(int what, Exception exception) {
//                CommonUtil.showToask(baseContext, "下载失败  " + exception.toString());
                Log.d("--lfc", "下载失败");

            }

            @Override
            public void onStart(int what, boolean isResume, long rangeSize, Headers responseHeaders, long allCount) {
                Log.d("--lfc", "下载开始   ");
                filesize = allCount;
            }

            @Override
            public void onProgress(int what, int progress, long fileCount) {
                Log.d("--lfc", "progress  " + progress + "");

                if (is_bg) {
                    updataNofication(progress, filesize);
                } else {
                    if (dialog.isShowing())
                        pb.setProgress(progress);
                }
            }

            @Override
            public void onFinish(int what, String filePath) {
                if (dialog.isShowing()) {
                    pb.setProgress(100);
                    dialog.dismiss();
                }
                if (builder_notify != null) {
                    notificationManager.cancel(notificationId);
                }
                File file = new File(filePath);
                try {
                    installApk(file);
                } catch (Exception e) {
                    e.printStackTrace();
                    LoggerUtil.e("installINFO: " + e.toString());
                }

                MUIToast.show(context,"下载完成  " + filePath);
                Log.d("--lfc", "下载完成  " + filePath);
            }

            @Override
            public void onCancel(int what) {
                MUIToast.show(context,"下载取消");
                Log.d("--lfc", "下载取消");

            }
        });

    }

    /**
     * 发送通知
     */
    private void sendDefaultNotification() {
        remoteView = new RemoteViews(context.getPackageName(), R.layout.layout_notifi);
        remoteView.setTextViewText(R.id.textView, context.getResources().getString(R.string.app_name));
        remoteView.setImageViewResource(R.id.icon, R.mipmap.logo);

        String channelId = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            channelId = "1";
            NotificationChannel channel = new NotificationChannel(channelId, "Channel1", NotificationManager.IMPORTANCE_DEFAULT);
            channel.enableLights(true); //是否在桌面icon右上角展示小红点   
            channel.setLightColor(Color.RED); //小红点颜色   
            channel.setShowBadge(true); //是否在久按桌面图标时显示此渠道的通知 

            notificationManager.createNotificationChannel(channel);
        }

        builder_notify = new NotificationCompat.Builder(context, channelId);
        builder_notify.setSmallIcon(R.mipmap.logo);

        builder_notify.setTicker("正在下载...");
        builder_notify.setContent(remoteView);
//        builder_notify.setAutoCancel(true);
//        builder_notify.setOngoing(true);
        builder_notify.setPriority(Notification.PRIORITY_MAX);
        notificationManager.notify(notificationId, builder_notify.build());

    }

    private void updataNofication(int progress, long size) {

        remoteView.setProgressBar(R.id.progressBar, 100, progress, false);
        DecimalFormat df = new DecimalFormat("#.##");
        remoteView.setTextViewText(R.id.textSize, df.format(b2mbDouble(size)) + "");
        remoteView.setTextViewText(R.id.textSpeed, progress + "%");
        notificationManager.notify(notificationId, builder_notify.build());
    }

    public double b2mbDouble(long b) {
        return b * 1.0 / 1024 / 1024;
    }

    //安装apk
    protected void installApk(File file) {
        Intent intent = new Intent();
  /*      if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            Uri contentUri = FileProvider.getUriForFile(context,
                    BuildConfig.APPLICATION_ID + ".fileProvider", file);
            intent.setDataAndType(contentUri, "application/vnd.android.package-archive");
        } else {
            intent.setDataAndType(Uri.fromFile(file), "application/vnd.android.package-archive");
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        }*/

        //执行动作
        intent.setAction(Intent.ACTION_VIEW);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        //执行的数据类型
        intent.setDataAndType(Uri.fromFile(file), "application/vnd.android.package-archive");
        context.startActivity(intent);

    }



}
