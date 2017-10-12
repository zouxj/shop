package com.shenyu.laikaword.interfaces;

import com.alibaba.sdk.android.oss.ClientException;
import com.alibaba.sdk.android.oss.ServiceException;

/**
 * Created by shenyu_zxjCode on 2017/10/12 0012.
 */


public interface Callback<T1,T2> {
    void onSuccess(T1 request, T2 result);
    void onFailure(T1 request, ClientException clientException, ServiceException serviceException);

}
