package com.shenyu.laikaword.module.goods.pickupgoods.ui.activity;

import android.content.Context;

import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.shenyu.laikaword.model.bean.reponse.BaseReponse;
import com.shenyu.laikaword.model.bean.reponse.PickUpGoodsReponse;
import com.shenyu.laikaword.model.bean.reponse.PurChaseReponse;
import com.shenyu.laikaword.model.net.api.ApiCallback;
import com.shenyu.laikaword.model.net.retrofit.RetrofitUtils;
import com.shenyu.laikaword.module.launch.LaiKaApplication;
import com.shenyu.laikaword.R;
import com.shenyu.laikaword.model.adapter.PurchaseViewPagerAdapter;
import com.shenyu.laikaword.base.LKWordBaseActivity;
import com.shenyu.laikaword.helper.TabLayoutHelper;
import com.shenyu.laikaword.di.module.mine.MineModule;
import com.zxj.utilslibrary.utils.StringUtil;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;

/**
 * 提貨记录
 */
public class PurchaseCardActivity extends LKWordBaseActivity {


    @BindView(R.id.tb_pruchase_car_pack)
    TabLayout tbPruchaseCarPack;
    @BindView(R.id.vp_pruchase_car_pack)
    ViewPager vpPruchaseCarPack;
    @Inject
    PurchaseViewPagerAdapter purchaseViewPagerAdapter;
    private List<String> goodType = new ArrayList<>();

    @Override
    public int bindLayout() {
        return R.layout.activity_purchase_money;
    }

    @Override
    public void initView() {
        tbPruchaseCarPack.post(new Runnable() {
            @Override
            public void run() {
                TabLayoutHelper.setIndicator(tbPruchaseCarPack, 50, 50);
            }
        });
        setToolBarTitle("我的提货");
        vpPruchaseCarPack.setAdapter(purchaseViewPagerAdapter);
        tbPruchaseCarPack.setupWithViewPager(vpPruchaseCarPack);

    }

    @Override
    public void doBusiness(Context context) {
        getPurchaseGoods();


    }

    @Override
    public void setupActivityComponent() {
        LaiKaApplication.get(this).getAppComponent().plus(new MineModule(getSupportFragmentManager())).inject(this);
    }
    public void getPurchaseGoods(){
        retrofitUtils.addSubscription(retrofitUtils.apiStores.myExtract1(), new ApiCallback<PurChaseReponse>() {
            @Override
            public void onSuccess(PurChaseReponse model) {
                if (model!=null&&model.isSuccess()) {
                    String [] tab =new String[model.getPayload().size()];
                    for (int i=0;i<model.getPayload().size();i++){
                        goodType.add(model.getPayload().get(i).getType());
                        tab[i]=model.getPayload().get(i).getName();
                    }
                    purchaseViewPagerAdapter.setDataList(tab,model.getPayload());


                }
            }

            @Override
            public void onFailure(String msg) {

            }

            @Override
            public void onFinish() {

            }
        });
    }


}
