package com.shenyu.laikaword.base;

/**
 * Created by Administrator on 2017/8/3 0003.
 */

public interface BaseLoadView {
    /** 正在加载数据 */
    void isLoading();

    /** 总数据个数变化 */
    void dataCountChanged(int count);

    /** 加载数据完毕 */
    void loadFinished();
}
