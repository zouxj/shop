package com.shenyu.laikaword.module.home.view;

import com.shenyu.laikaword.base.BaseLoadView;
import com.shenyu.laikaword.model.bean.reponse.ShopMainReponse;
import com.shenyu.laikaword.model.rxjava.rxbus.event.Event;

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

    void subscribeEvent(Event event);

}
