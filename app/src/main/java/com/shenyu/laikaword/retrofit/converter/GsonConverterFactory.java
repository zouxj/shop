package com.shenyu.laikaword.retrofit.converter;

import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.reflect.TypeToken;
import com.shenyu.laikaword.bean.BaseReponse;
import com.shenyu.laikaword.retrofit.ApiModel;

import java.lang.annotation.Annotation;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Converter;
import retrofit2.Retrofit;

/**
 * Created by shenyu_zxjCode on 2017/10/19 0019.
 */

public final class GsonConverterFactory extends Converter.Factory {

    /**

     * Create an instance using a default {@link Gson} instance for conversion. Encoding to JSON and

     * decoding from JSON (when no charset is specified by a header) will use UTF-8.

     */

    public static GsonConverterFactory create() {

        return create(new Gson());

    }



    /**

     * Create an instance using {@code gson} for conversion. Encoding to JSON and

     * decoding from JSON (when no charset is specified by a header) will use UTF-8.

     */

    public static GsonConverterFactory create(Gson gson) {

        return new GsonConverterFactory(gson);

    }



    private final Gson gson;



    private GsonConverterFactory(Gson gson) {

        if (gson == null) throw new NullPointerException("gson == null");

        this.gson = gson;

    }



    @Override

    public Converter<ResponseBody, ?> responseBodyConverter(final Type type, Annotation[] annotations, Retrofit retrofit) {

        Type newType = new ParameterizedType() {

            @Override

            public Type[] getActualTypeArguments() {

                return new Type[] { type };

            }



            @Override

            public Type getOwnerType() {

                return null;

            }



            @Override

            public Type getRawType() {

                return ApiModel.class;

            }

        };

        TypeAdapter<?> adapter = gson.getAdapter(TypeToken.get(newType));

        return new GsonResponseBodyConverter<>(adapter);

    }



    @Override

    public Converter<?, RequestBody> requestBodyConverter(Type type, Annotation[] parameterAnnotations,

                                                          Annotation[] methodAnnotations, Retrofit retrofit) {

        TypeAdapter<?> adapter = gson.getAdapter(TypeToken.get(type));

        return new GsonRequestBodyConverter<>(gson, adapter);

    }

}