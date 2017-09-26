package com.shenyu.laikaword.retrofit;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by shenyu_zxjCode on 2017/9/21 0021.
 */

public abstract class RetrofitCallback<M> implements Callback<M> {



    public abstract void onSuccess(M model);



    public abstract void onFailure(int code, String msg);



    public abstract void onThrowable(Throwable t);



    public abstract void onFinish();



    @Override

    public void onResponse(Call<M> call, Response<M> response) {

        if (response.isSuccessful()) {

            onSuccess(response.body());

        } else {

            onFailure(response.code(), response.errorBody().toString());

        }

        onFinish();

    }



    @Override

    public void onFailure(Call<M> call, Throwable t) {

        onThrowable(t);

        onFinish();

    }

}
