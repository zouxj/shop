package com.shenyu.laikaword.model.net.downloadmanager;

import android.app.NotificationManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.FileProvider;

import com.shenyu.laikaword.R;
import com.shenyu.laikaword.common.Constants;
import com.shenyu.laikaword.model.net.okhttp.OkHttp3Utils;
import com.shenyu.laikaword.model.net.retrofit.ApiStores;
import com.shenyu.laikaword.model.net.callback.FileCallback;
import com.zxj.utilslibrary.utils.LogUtil;
import com.zxj.utilslibrary.utils.UIUtil;

import java.io.File;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Retrofit;

/**
 * Created by Administrator on 2017/8/6 0006.
 */

public class DownLoadService extends Service {
    /**
     * 目标文件存储的文件夹路径
     */
    private String  destFileDir = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator;
    /**
     * 目标文件存储的文件名
     */
    private String destFileName ="com.shenyu.laika.apk";
    private String downURL;
    private Context mContext;
    private int preProgress = 0;
    private int NOTIFY_ID = 1000;
    private NotificationCompat.Builder builder;
    private NotificationManager notificationManager;
    private Retrofit.Builder retrofit;
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        mContext = this;
        downURL = intent.getStringExtra("DWONAPKURL");
        loadFile();
        return super.onStartCommand(intent, flags, startId);
    }
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
    /**
     * 下载文件
     */
    private void loadFile() {
        initNotification();
        if (retrofit == null) {
            retrofit =new Retrofit.Builder();
        }
        retrofit.client(OkHttp3Utils.getmOkHttpClientDwon())
                .baseUrl(Constants.HOSTDOWN_URL)
                .build()
                .create(ApiStores.class)
                .downApk(downURL)
                .enqueue(new FileCallback(destFileDir, System.currentTimeMillis()+"."+destFileName) {
                    @Override
                    public void onSuccess(File file) {
                        LogUtil.e("file", file.toString()+"   filesize()=>"+file.length());
                        // 安装软件
                        cancelNotification();
                        installApk(file);

                    }
                    @Override
                    public void onLoading(long progress, long total) {
                        LogUtil.e("zs", progress + "----" + total);
                        updateNotification(progress * 100 / total);
                    }
                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        LogUtil.e("zs", "请求失败");


                    }

                });

    }




    /**

     * 安装软件

     *

     * @param file

     */

    private void installApk(File file) {

//        Uri uri = Uri.fromFile(file);
//
//        Intent install = new Intent(Intent.ACTION_VIEW);
//
//        install.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//
//        install.setDataAndType(uri, "application/vnd.android.package-archive");
//
//        // 执行意图进行安装
//
//       mContext .startActivity(install);

        //apk文件的本地路径
        //会根据用户的数据类型打开android系统相应的Activity。
//        Intent intent = new Intent(Intent.ACTION_VIEW);
//
//        //为这个新apk开启一个新的activity栈
//        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//        //设置intent的数据类型是应用程序application
//        intent.setDataAndType(Uri.fromFile(file), "application/vnd.android.package-archive");
//        //开始安装
//        mContext.startActivity(intent);
//        //关闭旧版本的应用程序的进程
//        android.os.Process.killProcess(android.os.Process.myPid());
//    LogUtil.i("file_apk"+file.length());
//        Intent intent = new Intent(Intent.ACTION_VIEW);
//        //版本在7.0以上是不能直接通过uri访问的
//        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.N) {
//            // 由于没有在Activity环境下启动Activity,设置下面的标签
//            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//            //参数1 上下文, 参数2 Provider主机地址 和配置文件中保持一致   参数3  共享的文件
//            Uri apkUri = FileProvider.getUriForFile(mContext, "com.shenyu.laikaword.fileprovider", file);
//            //添加这一句表示对目标应用临时授权该Uri所代表的文件
//            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
//            intent.setDataAndType(apkUri, "application/vnd.android.package-archive");
//        } else {
//            intent.setDataAndType(Uri.fromFile(file),
//                    "application/vnd.android.package-archive");
//        }
//        mContext.startActivity(intent);




            Intent intentForInstall = new Intent();

            intentForInstall.setAction(Intent.ACTION_VIEW);

            intentForInstall.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);


            Uri apkUri;

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {

                apkUri = FileProvider.getUriForFile(mContext, "com.shenyu.laikaword.fileprovider", file);

                intentForInstall.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);

            } else {

                apkUri = Uri.fromFile(file);

            }

            intentForInstall.setDataAndType(apkUri, "application/vnd.android.package-archive");

           mContext.startActivity(intentForInstall);
           Constants.VERSION_NEW=null;



    }


    /**

     * 初始化Notification通知

     */
    public void initNotification() {
        builder = new NotificationCompat.Builder(mContext)

                .setSmallIcon(R.mipmap.ic_launcher)

                .setContentText("0%")

                .setContentTitle(UIUtil.getString(R.string.app_name)+"更新")

                .setProgress(100, 0, false);

        notificationManager = (NotificationManager) mContext.getSystemService(Context.NOTIFICATION_SERVICE);

        notificationManager.notify(NOTIFY_ID, builder.build());

    }



    /**

     * 更新通知

     */

    public void updateNotification(long progress) {

        int currProgress = (int) progress;

        if (preProgress < currProgress) {

            builder.setContentText(progress + "%");

            builder.setProgress(100, (int) progress, false);

            notificationManager.notify(NOTIFY_ID, builder.build());

        }

        preProgress = (int) progress;

    }



    /**

     * 取消通知

     */

    public void cancelNotification() {

        notificationManager.cancel(NOTIFY_ID);

    }
}