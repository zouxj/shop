package com.shenyu.laikaword.main;

import android.widget.ImageView;

import com.shenyu.laikaword.R;
import com.shenyu.laikaword.base.BasePresenter;
import com.shenyu.laikaword.bean.reponse.ShopMainReponse;
import com.shenyu.laikaword.common.CircleTransform;
import com.shenyu.laikaword.retrofit.ApiCallback;
import com.squareup.picasso.Picasso;
import com.zxj.utilslibrary.utils.UIUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * 首页业务
 */

public class MainPresenter extends BasePresenter<MainView> {

    public MainPresenter(MainView mainView){
        this.mvpView = mainView;
        attachView(mvpView);
    }
    public void requestData(){
        //TODO 请求商品数据
        addSubscription(apiStores.getMainShop(), new ApiCallback<ShopMainReponse>() {
            @Override
            public void onSuccess(ShopMainReponse model) {
                if (model.isSuccess())
                mvpView.showShop(model);
            }

            @Override
            public void onFailure(String msg) {

            }

            @Override
            public void onFinish() {

            }
        });

    }
    public void onLoadMore(){
        List<String> moreList = new ArrayList<>();
        for (int i=0;i<10;i++){
            moreList.add("more"+i);
        }
        mvpView.loadMore(moreList);
    }

    /**
     * 下拉刷新
     */
    public void loadRefresh(){
        List<String> refesh = new ArrayList<>();
        for (int i=0;i<10;i++){
            refesh.add("refrsh"+i);
        }
        mvpView.refreshPull(refesh);
    }

    /**
     * 加载头像
     * @param headURl
     * @param headImg
     */
    public void setImgHead(String headURl, ImageView headImg){
        Picasso.with(UIUtil.getContext()).load(headURl) .placeholder(R.mipmap.left_user_icon)
                .error(R.mipmap.left_user_icon).resize(50, 50).transform(new CircleTransform()).into(headImg);
    }
}
