package com.shenyu.laikaword.model.net.Intercepter;

import com.shenyu.laikaword.common.Constants;
import com.zxj.utilslibrary.utils.DeviceInfo;
import com.zxj.utilslibrary.utils.LogUtil;
import com.zxj.utilslibrary.utils.PackageManagerUtil;
import com.zxj.utilslibrary.utils.SignUtil;
import com.zxj.utilslibrary.utils.UIUtil;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import okhttp3.FormBody;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.MultipartBody;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by shenyu_zxjCode on 2017/9/21 0021.
 */

public   class CommonParamntercepter implements Interceptor {
    public CommonParamntercepter(){
    }
    @Override
    public Response intercept(Chain chain) throws IOException {

        Request request = chain.request();
            //添加请求头
            request = request.newBuilder().addHeader("token", Constants.getToken()).build();
            //添加公共参数
            if (request.method().equals("GET")){
                request =addGetParams(request);
            }
            if (request.method().equals("POST")){
                request =addPostParams(request);
            }

        return chain.proceed(request);
    }

    //get请求 添加公共参数 签名
    private static Request addGetParams(Request request) {
        //添加公共参数
        HttpUrl httpUrl = request.url()
                .newBuilder()
                .addQueryParameter("appVersion", PackageManagerUtil.getVersionName(UIUtil.getContext()))
                .addQueryParameter("deviceVersion", DeviceInfo.getSystemVersion())
                .addQueryParameter("system", "android")
                .addQueryParameter("timestamp", String.valueOf(System.currentTimeMillis()))
                .addQueryParameter("device",DeviceInfo.getSystemModel())
                .addQueryParameter("channel", PackageManagerUtil.getAppMetaData(UIUtil.getContext(),"UMENG_CHANNEL"))
                .build();

        //添加签名
        Set<String> nameSet = httpUrl.queryParameterNames();
        ArrayList<String> nameList = new ArrayList<>();
        nameList.addAll(nameSet);
        Collections.sort(nameList);
        StringBuilder buffer = new StringBuilder();
        //重新拼接url
//        HttpUrl.Builder httpUrlBuilder = request.url().newBuilder();
        for (int i = 0; i < nameList.size(); i++) {
            if (i==0){
                buffer.append(nameList.get(i)).append("=").append(httpUrl.queryParameterValues(nameList.get(i)) != null &&
                        httpUrl.queryParameterValues(nameList.get(i)).size() > 0 ? httpUrl.queryParameterValues(nameList.get(i)).get(0) : "");
            }else {
                buffer.append("&").append(nameList.get(i)).append("=").append(httpUrl.queryParameterValues(nameList.get(i)) != null &&
                        httpUrl.queryParameterValues(nameList.get(i)).size() > 0 ? httpUrl.queryParameterValues(nameList.get(i)).get(0) : "");
            }
        }
        String sign=buffer.append("&"+ Constants.KEY_VALUE).toString();
        httpUrl= httpUrl.newBuilder().addQueryParameter("sign", SignUtil.md5(sign)).build();
        request = request.newBuilder().url(httpUrl).build();
        return request;
    }
    //post 添加签名和公共参数
    private static Request addPostParams(Request request){
        if (request.body() instanceof FormBody) {
            FormBody.Builder bodyBuilder = new FormBody.Builder();
            FormBody formBody = (FormBody) request.body();
            //把原来的参数添加到新的构造器，（因为没找到直接添加，所以就new新的）
            for (int i = 0; i < formBody.size(); i++) {
                bodyBuilder.addEncoded(formBody.encodedName(i), formBody.encodedValue(i));
            }
            formBody = bodyBuilder
                    .addEncoded("system", "android")
                    .addEncoded("device",DeviceInfo.getSystemModel())
                    .addEncoded("deviceVersion", DeviceInfo.getSystemVersion())
                    .addEncoded("appVersion", PackageManagerUtil.getVersionName(UIUtil.getContext()))
                    .addEncoded("timestamp",String.valueOf(System.currentTimeMillis()))
                    .addEncoded("channel", PackageManagerUtil.getAppMetaData(UIUtil.getContext(),"UMENG_CHANNEL"))
                    .build();
            Map<String, String> bodyMap = new HashMap<>();
            List<String> nameList = new ArrayList<>();
            for (int i = 0; i < formBody.size(); i++) {
                nameList.add(formBody.encodedName(i));
                try {
                    bodyMap.put(formBody.encodedName(i), URLDecoder.decode(formBody.encodedValue(i), "UTF-8"));
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
            }
            StringBuilder builder = new StringBuilder();
            Collections.sort(nameList);
            for (int i = 0; i < nameList.size(); i++) {

                try {
                    if (i==0){
                        builder.append(nameList.get(i)).append("=").append(URLDecoder.decode(bodyMap.get(nameList.get(i)), "UTF-8"));
                    }else {
                        builder.append("&").append(nameList.get(i)).append("=").append(URLDecoder.decode(bodyMap.get(nameList.get(i)), "UTF-8"));
                    }

                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
            }
            String sign=builder.append("&"+ Constants.KEY_VALUE).toString();
            formBody = bodyBuilder.addEncoded("sign", SignUtil.md5(sign)).build();
            request = request.newBuilder().post(formBody).build();
        }else if (request.body() instanceof MultipartBody){
            LogUtil.i("xxx");
        }
        return request;
    }

}
