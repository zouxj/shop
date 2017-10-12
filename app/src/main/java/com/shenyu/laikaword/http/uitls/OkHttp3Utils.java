package com.shenyu.laikaword.http.uitls;

import com.shenyu.laikaword.common.Constants;
import com.shenyu.laikaword.http.Intercepter.DownIntercepter;
import com.shenyu.laikaword.http.downloadmanager.FileResponseBody;
import com.shenyu.laikaword.http.uitls.Interceptor.LogInterceptor;
import com.zxj.utilslibrary.utils.DeviceInfo;
import com.zxj.utilslibrary.utils.FileStorageUtil;
import com.zxj.utilslibrary.utils.LogUtil;
import com.zxj.utilslibrary.utils.NetworkUtil;
import com.zxj.utilslibrary.utils.PackageManagerUtil;
import com.zxj.utilslibrary.utils.SignUtil;
import com.zxj.utilslibrary.utils.ToastUtil;
import com.zxj.utilslibrary.utils.UIUtil;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.text.Collator;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import okhttp3.Cache;
import okhttp3.CacheControl;
import okhttp3.Cookie;
import okhttp3.CookieJar;
import okhttp3.FormBody;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;

/**
 * Created by Administrator on 2017/8/3 0003.
 */

public class OkHttp3Utils {
    private static OkHttpClient mOkHttpClient;
    //    private static SSLSocketFactory sslSocketFactory;
    //设置缓存目录
    private static File cacheDirectory = new File(FileStorageUtil.getAppCacheDirPath(), "MyCache");
    private static Cache cache = new Cache(cacheDirectory, 10 * 1024 * 1024);

    /**
     * ssh
     */
//    static {
//        final TrustManager[] trustAllCerts = new TrustManager[] {
//                new X509TrustManager() {
//                    @Override
//                    public void checkClientTrusted(java.security.cert.X509Certificate[] chain, String authType) throws CertificateException {
//                    }
//
//                    @Override
//                    public void checkServerTrusted(java.security.cert.X509Certificate[] chain, String authType) throws CertificateException {
//                    }
//
//                    @Override
//                    public java.security.cert.X509Certificate[] getAcceptedIssuers() {
//                        return new java.security.cert.X509Certificate[]{};
//                    }
//                }
//        };
//        // Install the all-trusting trust manager
//        SSLContext sslContext = null;
//        try {
//            try {
//                sslContext = SSLContext.getInstance("SSL");
//            } catch (NoSuchAlgorithmException e) {
//                e.printStackTrace();
//            }
//            sslContext.init(null, trustAllCerts, new java.security.SecureRandom());
//        } catch (KeyManagementException e) {
//            e.printStackTrace();
//        }
//        // Create an ssl socket factory with our all-trusting manager
//        sslSocketFactory  = sslContext.getSocketFactory();
//    }

    /**
     * 获取Okhttp对象
     *
     * @return
     */
    public static OkHttpClient getmOkHttpClient() {
        if (null == mOkHttpClient) {
            synchronized (OkHttp3Utils.class) {
                if (null == mOkHttpClient) {
                    OkHttpClient.Builder builder = new OkHttpClient.Builder();
                    // Log信息拦截器
                    HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor(new LogInterceptor());
                    loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
                    //设置 Debug Log 模式
                    builder.addInterceptor(loggingInterceptor);
                    builder.addInterceptor(new CommonParamntercepter());
                    mOkHttpClient = builder.build();
                }
            }
        }
        return mOkHttpClient;
    }

    public static OkHttpClient getmOkHttpClientDwon() {
                OkHttpClient.Builder builder = new OkHttpClient.Builder();
                // Log信息拦截器
                HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor(new LogInterceptor());
                loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
                //设置 Debug Log 模式
                builder.addInterceptor(loggingInterceptor);
                builder.networkInterceptors().add(new DownIntercepter());
                  return builder.build();

    }
    /**
     * 下载网络下载连拦截器
     */

//    /**
//     * 公共参数拦截
//     */
//
//    /**
//     * 自动管理Cookies
//     */
//    private static class CookiesManager implements CookieJar {
//        private final PersistentCookieStore cookieStore = new PersistentCookieStore(UIUtil.getContext());
//
//        @Override
//        public void saveFromResponse(HttpUrl url, List<Cookie> cookies) {
//            if (cookies != null && cookies.size() > 0) {
//                for (Cookie item : cookies) {
//                    cookieStore.add(url, item);
//                }
//            }
//        }
//
//        @Override
//        public List<Cookie> loadForRequest(HttpUrl url) {
//            List<Cookie> cookies = cookieStore.get(url);
//            return cookies;
//        }
//    }

}
