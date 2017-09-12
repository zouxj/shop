package com.shenyu.laikaword.http.uitls;

import com.shenyu.laikaword.http.downloadmanager.FileResponseBody;
import com.zxj.utilslibrary.utils.FileStorageUtil;
import com.zxj.utilslibrary.utils.NetworkUtil;
import com.zxj.utilslibrary.utils.ToastUtil;
import com.zxj.utilslibrary.utils.UIUtil;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.CacheControl;
import okhttp3.Cookie;
import okhttp3.CookieJar;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Administrator on 2017/8/3 0003.
 */

public class OkHttp3Utils {
    private static OkHttpClient mOkHttpClient;

    //设置缓存目录
    private static File cacheDirectory = new File(FileStorageUtil.getAppCacheDirPath(), "MyCache");
    private static Cache cache = new Cache(cacheDirectory, 10 * 1024 * 1024);

    /**
     * 获取Okhttp对象
     * @return
     */
    public static OkHttpClient getmOkHttpClient(){
        if (null==mOkHttpClient){
            synchronized (OkHttp3Utils.class) {
                if (null==mOkHttpClient) {
                    OkHttpClient.Builder builder = new OkHttpClient.Builder();
                    builder.connectTimeout(30, TimeUnit.SECONDS)
                            .writeTimeout(30, TimeUnit.SECONDS)
                            .readTimeout(30, TimeUnit.SECONDS)
                            .cache(cache);
                    builder.cookieJar(new CookiesManager());
                    builder.addInterceptor(new LaiIntercepter());
                    builder.networkInterceptors().add(new DownIntercepter());
                    mOkHttpClient = builder.build();
//            mOkHttpClient=new OkHttpClient.Builder().cookieJar(new CookiesManager()) //添加拦截器
//                    .addInterceptor(new LaiIntercepter())
//                    //添加网络连接器
//                    //.addNetworkInterceptor(new CookiesInterceptor(MyApplication.getInstance().getApplicationContext()))
//                    //设置请求读写的超时时间
//                    .connectTimeout(30, TimeUnit.SECONDS)
//                    .writeTimeout(30, TimeUnit.SECONDS)
//                    .readTimeout(30, TimeUnit.SECONDS)
//                    .cache(cache)
//                    .build();
                }
            }
        }
        return  mOkHttpClient;
    }

    /**
     * 下载网络下载连拦截器
     */
    private static class DownIntercepter implements Interceptor{

        @Override
        public Response intercept(Chain chain) throws IOException {
            Response originalResponse = chain.proceed(chain.request());
            return originalResponse
                    .newBuilder()
                    .body(new FileResponseBody(originalResponse))
                    .build();
        }
    }
    /**
     * 请求错误拦截器
     */
    private static class LaiIntercepter implements Interceptor {
        private Map<String, String> headers;
        public LaiIntercepter(){
            headers = new HashMap<>();
            headers.put("token","user_token");
        }
        @Override
        public Response intercept(Chain chain) throws IOException {
            Request request = chain.request();
            if (!NetworkUtil.isNetworkConnected(UIUtil.getContext())) {
                ToastUtil.showToastLong("网络异常");
                request = request.newBuilder()
                        .cacheControl(CacheControl.FORCE_CACHE)//无网络时只从缓存中读取
                        .build();
            }

            Response response = chain.proceed(request);
            if (!NetworkUtil.isNetworkConnected(UIUtil.getContext())) {
                int maxAge = 60 * 60; // 有网络时 设置缓存超时时间1个小时
                response.newBuilder()
                        .removeHeader("Pragma")
                        .header("Cache-Control", "public, max-age=" + maxAge)
                        .build();

            }else {
                if (headers != null && headers.size() > 0) {
                    Set<String> keys = headers.keySet();
                    for (String headerKey : keys) {
                        request.newBuilder().addHeader(headerKey, headers.get(headerKey)).build();
                    }
                }
                int maxStale = 60 * 60 * 24 * 28; // 无网络时，设置超时为4周
                response.newBuilder()
                        .removeHeader("Pragma")
                        .header("Cache-Control", "public, only-if-cached, max-stale=" + maxStale)
                        .build();
            }
            return response;
        }
    }
    /**
     * 自动管理Cookies
     */
    private static class CookiesManager implements CookieJar {
        private final PersistentCookieStore cookieStore = new PersistentCookieStore(UIUtil.getContext());

        @Override
        public void saveFromResponse(HttpUrl url, List<Cookie> cookies) {
            if (cookies != null && cookies.size() > 0) {
                for (Cookie item : cookies) {
                    cookieStore.add(url, item);
                }
            }
        }

        @Override
        public List<Cookie> loadForRequest(HttpUrl url) {
            List<Cookie> cookies = cookieStore.get(url);
            return cookies;
        }
    }
}
