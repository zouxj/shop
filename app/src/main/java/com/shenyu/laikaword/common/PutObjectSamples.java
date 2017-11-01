package com.shenyu.laikaword.common;

import com.alibaba.sdk.android.oss.ClientConfiguration;
import com.alibaba.sdk.android.oss.ClientException;
import com.alibaba.sdk.android.oss.OSS;
import com.alibaba.sdk.android.oss.OSSClient;
import com.alibaba.sdk.android.oss.ServiceException;
import com.alibaba.sdk.android.oss.callback.OSSCompletedCallback;
import com.alibaba.sdk.android.oss.callback.OSSProgressCallback;
import com.alibaba.sdk.android.oss.common.OSSLog;
import com.alibaba.sdk.android.oss.common.auth.OSSCredentialProvider;
import com.alibaba.sdk.android.oss.common.auth.OSSStsTokenCredentialProvider;
import com.alibaba.sdk.android.oss.internal.OSSAsyncTask;
import com.alibaba.sdk.android.oss.model.PutObjectRequest;
import com.alibaba.sdk.android.oss.model.PutObjectResult;
import com.shenyu.laikaword.Interactor.ProgressCallback;
import com.shenyu.laikaword.base.BaseReponse;
import com.shenyu.laikaword.model.bean.reponse.ImgSTSReponse;
import com.zxj.utilslibrary.utils.LogUtil;
import com.zxj.utilslibrary.utils.ToastUtil;
import com.zxj.utilslibrary.utils.UIUtil;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;

/**
 * Created by shenyu_zxjCode on 2017/10/26 0026.
 * 阿里云图片存在
 */

public class PutObjectSamples {

    private OSS oss;
    private static final String endpoint = "https://oss-cn-shanghai.aliyuncs.com";
    private String objectKey;
    PutObjectRequest put;
    private String uploadImgUrl;
    private ImgSTSReponse mImgSTSReponse;

    public PutObjectSamples(ImgSTSReponse imgSTSReponse,String uploadImgUrl,String objectKey){
        this.mImgSTSReponse = imgSTSReponse;
        this.objectKey=objectKey;
        this.uploadImgUrl=uploadImgUrl;
        setOssClient(imgSTSReponse.getPayload().getAccessKeyId(),imgSTSReponse.getPayload().getAccessKeySecret(),imgSTSReponse.getPayload().getSecurityToken());
    }
    public Observable<String> uploadImg(){
      return  Observable.create(new ObservableOnSubscribe<String>() {
          @Override
          public void subscribe(final ObservableEmitter<String> observableEmitter) throws Exception {
              OSSAsyncTask task = oss.asyncPutObject(put, new OSSCompletedCallback<PutObjectRequest, PutObjectResult>() {
                  @Override
                  public void onSuccess(PutObjectRequest request, PutObjectResult result) {
                          observableEmitter.onNext(result.getStatusCode()+"");
                          observableEmitter.onComplete();

                      LogUtil.d("PutObject", "UploadSuccess");
                      LogUtil.d("ETag", result.getETag());
                      LogUtil.d("RequestId", result.getRequestId());

                  }

                  @Override
                  public void onFailure(PutObjectRequest request, ClientException clientExcepion, ServiceException serviceException) {
                      // 请求异常
                      if (clientExcepion != null) {
                          // 本地异常如网络异常等
                          clientExcepion.printStackTrace();
                      }
                      if (serviceException != null) {
                          observableEmitter.onNext(serviceException.getErrorCode());
                          observableEmitter.onComplete();
                          // 服务异常
                          LogUtil.e("ErrorCode", serviceException.getErrorCode());
                          LogUtil.e("RequestId", serviceException.getRequestId());
                          LogUtil.e("HostId", serviceException.getHostId());
                          LogUtil.e("RawMessage", serviceException.getRawMessage());
                      }
                  }
              });
              task.waitUntilFinished();
          }
      });

    }

    private OSSCredentialProvider mCredentialProvider;
    /**
     * 初始化oss
     */
    public void setOssClient(String ak,String sk,String token) {
        if(mCredentialProvider == null || oss == null) {
            mCredentialProvider = new OSSStsTokenCredentialProvider(ak, sk, token);
            OSSCredentialProvider credentialProvider = new OSSStsTokenCredentialProvider(ak, sk, token);
            ClientConfiguration conf = new ClientConfiguration();
            conf.setConnectionTimeout(15 * 1000); // 连接超时，默认15秒
            conf.setSocketTimeout(15 * 1000); // socket超时，默认15秒
            conf.setMaxConcurrentRequest(5); // 最大并发请求书，默认5个
            conf.setMaxErrorRetry(2); // 失败后最大重试次数，默认2次
            OSSLog.enableLog(); //这个开启会支持写入手机sd卡中的一份日志文件位置在SD_path\OSSLog\logs.csv
            oss = new OSSClient(UIUtil.getContext(), endpoint, credentialProvider, conf);
            initPutObjectRequest();
        }else{
            ((OSSStsTokenCredentialProvider)mCredentialProvider).setAccessKeyId(ak);
            ((OSSStsTokenCredentialProvider)mCredentialProvider).setSecretKeyId(sk);
            ((OSSStsTokenCredentialProvider)mCredentialProvider).setSecurityToken(token);

        }
    }
    public void initPutObjectRequest(){
        put = new PutObjectRequest(mImgSTSReponse.getPayload().getBucketName(), objectKey, uploadImgUrl);
        put.setProgressCallback(new OSSProgressCallback<PutObjectRequest>() {
            @Override
            public void onProgress(PutObjectRequest request, long currentSize, long totalSize) {
                LogUtil.d("PutObject", "currentSize: " + currentSize + " totalSize: " + totalSize);
            }
        });

    }

}
