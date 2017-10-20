package com.shenyu.laikaword.main;

import com.shenyu.laikaword.base.BaseLoadView;
import com.shenyu.laikaword.bean.reponse.ShopMainReponse;

import java.util.List;

/**
 * 首页View
 */

public interface MainView extends BaseLoadView {
    /**
     * 显示首页数据
     * @param shopBeanReponse
     */
    void showShop(ShopMainReponse shopBeanReponse);

    /**
     * 下拉加载更多
     */
    void loadMore(List list);

    /**
     * 上拉刷新
     * @param list
     */
    void refreshPull(List list);

}
