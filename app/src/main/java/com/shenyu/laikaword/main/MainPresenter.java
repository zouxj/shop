package com.shenyu.laikaword.main;

import java.util.ArrayList;
import java.util.List;

/**
 * 首页业务
 */

public class MainPresenter {
   private MainView mainView;
    public MainPresenter(MainView mainView){
        this.mainView = mainView;
    }
    public void requestData(){
        //TODO 请求商品数据
    }
    public void onLoadMore(){
        List<String> moreList = new ArrayList<>();
        for (int i=0;i<10;i++){
            moreList.add("more"+i);
        }
        mainView.loadMore(moreList);
    }

    /**
     * 下拉刷新
     */
    public void loadRefresh(){
        List<String> refesh = new ArrayList<>();
        for (int i=0;i<10;i++){
            refesh.add("refrsh"+i);
        }
        mainView.refreshPull(refesh);

    }
}
