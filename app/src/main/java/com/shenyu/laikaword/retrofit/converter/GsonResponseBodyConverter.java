package com.shenyu.laikaword.retrofit.converter;

import com.google.gson.TypeAdapter;
import com.shenyu.laikaword.bean.BaseReponse;
import com.shenyu.laikaword.retrofit.ApiModel;
import com.shenyu.laikaword.retrofit.ErrorCode;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Converter;

/**
 * Created by shenyu_zxjCode on 2017/10/19 0019.
 */

final class GsonResponseBodyConverter<T> implements Converter<ResponseBody, Object> {
    private final TypeAdapter<T> adapter;
    GsonResponseBodyConverter(TypeAdapter<T> adapter) {
        this.adapter = adapter;

    }



    @Override

    public Object convert(ResponseBody value) throws IOException {

        try {

            ApiModel apiModel = (ApiModel) adapter.fromJson(value.charStream());

            if (null!=apiModel.getError()&&apiModel.getError().getCode() == ErrorCode.code) {

//                throw new TokenNotExistException();

//            } else if (apiModel.errorCode == ErrorCode.TOKEN_INVALID) {
//
//                throw new TokenInvalidException();
//
            }
                else if (!apiModel.isSuccess()) {

                // 特定 API 的错误，在相应的 Subscriber 的 onError 的方法中进行处理


            } else if (apiModel.isSuccess()) {

                return apiModel;

            }

        } finally {

            value.close();

        }

        return null;

    }

}