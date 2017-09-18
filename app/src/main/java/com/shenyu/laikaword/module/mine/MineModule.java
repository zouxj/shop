package com.shenyu.laikaword.module.mine;

import android.app.Activity;
import android.content.Context;
import android.support.v4.app.FragmentManager;

import com.shenyu.laikaword.adapter.CarPackageViewPagerAdapter;
import com.shenyu.laikaword.adapter.PurchaseViewPagerAdapter;
import com.shenyu.laikaword.helper.CityDataHelper;
import com.shenyu.laikaword.module.mine.address.AddPresenter;
import com.shenyu.laikaword.module.mine.address.AddressView;
import com.shenyu.laikaword.module.mine.cards.AddBankPresenter;
import com.shenyu.laikaword.module.mine.cards.AddBankView;
import com.shenyu.laikaword.module.mine.systemsetting.UserInfoActivity;
import com.shenyu.laikaword.module.mine.systemsetting.UserInfoPresenter;
import com.shenyu.laikaword.module.mine.systemsetting.UserInfoView;
import com.zxj.utilslibrary.utils.UIUtil;

import dagger.Module;
import dagger.Provides;

/**
 * Created by shenyu_zxjCode on 2017/9/13 0013.
 */
@Module
public class MineModule {
    private AddressView addressView;
    private Context mContext;
    private UserInfoView userInfoView;
    private Activity mActivity;
    private FragmentManager fm;
    private  AddBankView AddBankView;
    //添加添加地址View
    public MineModule(Context context,AddressView addressView){
        this.addressView=addressView;
        this.mContext=context;
    }
    public MineModule(Activity activity, UserInfoView userInfoView){
        this.mActivity =activity;
        this.userInfoView=userInfoView;
    }
    public MineModule(Context context, AddBankView addBankView){
    this.mContext=context;
    this.AddBankView = addBankView;
    }
    public MineModule(FragmentManager fm){
        this.fm= fm;
    }
    @Provides
    AddPresenter provideAddPresenter(){
        return new AddPresenter(addressView);
    }

    @Provides
    CityDataHelper provideCityDataHelper(){
        return new CityDataHelper(mContext);
    }

    @Provides
    UserInfoPresenter provideUserInfoPresenter(){
        return new UserInfoPresenter(mActivity,userInfoView);
    }
    @Provides
    CarPackageViewPagerAdapter provideCarPackageViewPagerAdapter(){
     return    new CarPackageViewPagerAdapter(fm);
    }
    @Provides
    PurchaseViewPagerAdapter providePurchaseViewPagerAdapter(){
        return    new PurchaseViewPagerAdapter(fm);
    }
    @Provides
    AddBankPresenter provideAddBankPresenter(){
        return new AddBankPresenter(AddBankView);
    }
}
