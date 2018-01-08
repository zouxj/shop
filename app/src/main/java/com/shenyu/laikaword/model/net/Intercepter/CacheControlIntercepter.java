package com.shenyu.laikaword.model.net.Intercepter;

import com.zxj.utilslibrary.utils.LogUtil;
import com.zxj.utilslibrary.utils.NetworkUtil;
import com.zxj.utilslibrary.utils.UIUtil;

import java.io.IOException;

import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by shenyu_zxjCode on 2017/11/7 0007.
 */

public class CacheControlIntercepter implements Interceptor {

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        LogUtil.i("request="+request);

        if(!NetworkUtil.isMobileConnected(UIUtil.getContext())){
            request = request.newBuilder()
                    .cacheControl(CacheControl.FORCE_CACHE)
                    .build();
            LogUtil.w("no network");
        }
        Response response = chain.proceed(request);
        if (NetworkUtil.isMobileConnected(UIUtil.getContext())){
//TODO 有网络时候
            int maxAge = 30; // 有网络时 设置缓存超时时间0个小时
            LogUtil.i("has network maxAge="+maxAge);
            return response.newBuilder()
                    .header("Cache-Control", "public, max-age=" + maxAge)
                    .removeHeader("Pragma")// 清除头信息，因为服务器如果不支持，会返回一些干扰信息，不清除下面无法生效
                    .build();
        }else {
//TODO 无网络时候
            LogUtil.i("network error");
            int maxStale = 60 * 60 * 24 * 28; // 无网络时，设置超时为4周
            LogUtil.i("has maxStale="+maxStale);
          return   response.newBuilder()
                    .header("Cache-Control", "public, only-if-cached, max-stale=" + maxStale)
                    .removeHeader("Pragma")
                    .build();

        }
//        LogUtil.i("response="+response);
//        String cacheControl = request.cacheControl().toString();
//        if (TextUtils.isEmpty(cacheControl)) {
//            cacheControl = "public, max-age=60";
//        }
//        return response.newBuilder()
//                .header("Cache-Control", cacheControl)
//                .removeHeader("Pragma")
//                .build();
    }
}
