package com.shenyu.laikaword.Interactor;

/**
 * Created by shenyu_zxjCode on 2017/10/12 0012.
 */

public interface ProgressCallback<T1,T2> extends Callback<T1,T2> {

    void onProgress(T1 request, long currentSize, long totalSize);

}
