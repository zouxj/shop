package com.shenyu.laikaword.http.uitls;

import com.shenyu.laikaword.bean.BaseReponse;

import rx.Observable;
import rx.functions.Func1;

/**
 * Created by Administrator on 2017/8/7 0007.
 */

public class BaseResponseFunc<T> implements Func1<BaseReponse, Observable<T>> {


//    @Override
//    public Observable<T> call(BaseReponse<T> tBaseResponse) {
////        //遇到非200错误统一处理,将BaseResponse转换成您想要的对象
////        if (tBaseResponse.getStatus_code() != 200) {
////            return Observable.error(new Throwable(tBaseResponse.getStatus_msg()));
////        }else{
////            return Observable.just(tBaseResponse.getData());
////        }
//        return null;
//    }

    @Override
    public Observable<T> call(BaseReponse baseReponse) {
        return null;
    }
}
