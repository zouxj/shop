package com.shenyu.laikaword.main;

import com.shenyu.laikaword.base.BaseLoadView;
import com.shenyu.laikaword.bean.reponse.ShopBeanReponse;

/**
 * Created by Administrator on 2017/9/7 0007.
 */

public interface MainView extends BaseLoadView {
    /**
     * 显示首页数据
     * @param shopBeanReponse
     */
    void showShop(ShopBeanReponse shopBeanReponse);


}
