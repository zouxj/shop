package com.shenyu.laikaword.base;

import com.shenyu.laikaword.model.bean.reponse.BaseReponse;

/**
 * Created by Administrator on 2017/8/3 0003.
 */

public interface BaseLoadView {
    /** 正在加载数据 */
    void isLoading();

    /** 加载数据完毕 */
    void loadSucceed(BaseReponse baseReponse);

    /**
     * 加载失败
     */
    void loadFailure();
}
