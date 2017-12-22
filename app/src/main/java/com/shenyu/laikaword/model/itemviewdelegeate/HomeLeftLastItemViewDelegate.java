package com.shenyu.laikaword.model.itemviewdelegeate;

import android.app.Activity;
import android.view.View;

import com.shenyu.laikaword.R;
import com.shenyu.laikaword.common.Constants;
import com.shenyu.laikaword.model.holder.ViewHolder;
import com.shenyu.laikaword.model.bean.reponse.LoginReponse;
import com.shenyu.laikaword.model.bean.reponse.ShopMainReponse;
import com.shenyu.laikaword.module.login.ui.activity.LoginActivity;
import com.shenyu.laikaword.module.us.appsetting.SettingSystemActivity;
import com.zxj.utilslibrary.utils.IntentLauncher;
import com.zxj.utilslibrary.utils.SPUtil;

/**
 * Created by shenyu_zxjCode on 2017/10/28 0028.
 */

public class HomeLeftLastItemViewDelegate implements ItemViewDelegate<ShopMainReponse.EntranceListBean> {

    private Activity mActivity;
    public HomeLeftLastItemViewDelegate(Activity activity){
        this.mActivity=activity;
    }
    @Override
    public int getItemViewLayoutId() {
        return R.layout.item_left_last;
    }

    @Override
    public boolean isForViewType(ShopMainReponse.EntranceListBean item, int position) {
        if (item.getTitle().equals("系统设置"))
            return true;
        return false;
    }

    @Override
    public void convert(ViewHolder holder, final ShopMainReponse.EntranceListBean entranceListBean, final int position) {
        holder.setText(R.id.tv_left_menu,entranceListBean.getTitle());
        holder.setBackground(R.id.img_left_menu,entranceListBean.getImgUrl());
        holder.itemView.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LoginReponse loginReponse = (LoginReponse) SPUtil.readObject(Constants.LOGININFO_KEY);
                if (null==loginReponse) {
                    IntentLauncher.with(mActivity).launch(LoginActivity.class);
                    return;
                }
                if (entranceListBean.getTitle().equals("系统设置")){
                    //TODO 我的设置
                    IntentLauncher.with(mActivity).launch(SettingSystemActivity.class);
                    return;
                }
            }
        });
    }


}
