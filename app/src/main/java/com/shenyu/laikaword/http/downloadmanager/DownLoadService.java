package com.shenyu.laikaword.http.downloadmanager;

import android.app.NotificationManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;

import com.shenyu.laikaword.R;
import com.shenyu.laikaword.common.UrlConstant;
import com.shenyu.laikaword.http.Intercepter.DownIntercepter;
import com.shenyu.laikaword.http.NetWorks;
import com.shenyu.laikaword.http.uitls.OkHttp3Utils;
import com.shenyu.laikaword.retrofit.ApiClient;
import com.shenyu.laikaword.retrofit.ApiStores;
import com.shenyu.laikaword.retrofit.RetrofitUtils;
import com.shenyu.laikaword.retrofit.callback.FileCallback;
import com.zxj.utilslibrary.utils.LogUtil;
import com.zxj.utilslibrary.utils.StringUtil;
import com.zxj.utilslibrary.utils.UIUtil;

import java.io.File;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.http.GET;

/**
 * Created by Administrator on 2017/8/6 0006.
 */

public class DownLoadService extends Service {
    /**
     * 目标文件存储的文件夹路径
     */
    private String  destFileDir = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + "M_DEFAULT_DIR";
    /**
     * 目标文件存储的文件名
     */
    private String destFileName = "laika.apk";
    private Context mContext;
    private int preProgress = 0;
    private int NOTIFY_ID = 1000;
    private NotificationCompat.Builder builder;
    private NotificationManager notificationManager;
    private Retrofit.Builder retrofit;
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        mContext = this;
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
                .baseUrl(UrlConstant.HOST)
                .build()
                .create(ApiStores.class)
                .downApk()
                .enqueue(new FileCallback(destFileDir, destFileName) {
                    @Override
                    public void onSuccess(File file) {
                        LogUtil.e("zs", "请求成功");
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
                        cancelNotification();

                    }

                });

    }



    public interface IFileLoad {
        @GET("download")
        Call<ResponseBody> loadFile();

    }



    /**

     * 安装软件

     *

     * @param file

     */

    private void installApk(File file) {

        Uri uri = Uri.fromFile(file);

        Intent install = new Intent(Intent.ACTION_VIEW);

        install.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        install.setDataAndType(uri, "application/vnd.android.package-archive");

        // 执行意图进行安装

        mContext.startActivity(install);

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