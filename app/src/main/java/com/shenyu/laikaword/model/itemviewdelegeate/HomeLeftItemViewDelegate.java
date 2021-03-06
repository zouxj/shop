package com.shenyu.laikaword.model.itemviewdelegeate;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.shenyu.laikaword.R;
import com.shenyu.laikaword.common.Constants;
import com.shenyu.laikaword.helper.DialogHelper;
import com.shenyu.laikaword.helper.ImageUitls;
import com.shenyu.laikaword.model.holder.ViewHolder;
import com.shenyu.laikaword.model.bean.reponse.LoginReponse;
import com.shenyu.laikaword.model.bean.reponse.ShopMainReponse;
import com.shenyu.laikaword.model.web.MIActivity;
import com.shenyu.laikaword.model.web.ShopDateilActivity;
import com.shenyu.laikaword.module.goods.BuyGoodsActivity;
import com.shenyu.laikaword.module.login.ui.activity.LoginActivity;
import com.shenyu.laikaword.module.us.goodcards.ui.activity.CardPackageActivity;
import com.shenyu.laikaword.module.goods.pickupgoods.ui.activity.PurchaseCardActivity;
import com.shenyu.laikaword.module.us.wallet.remaining.UserRemainingActivity;
import com.shenyu.laikaword.module.us.resell.ui.activity.ResellActivity;
import com.shenyu.laikaword.model.web.GuessActivity;
import com.zxj.utilslibrary.utils.IntentLauncher;
import com.zxj.utilslibrary.utils.PackageManagerUtil;
import com.zxj.utilslibrary.utils.SPUtil;
import com.zxj.utilslibrary.utils.StringUtil;
import com.zxj.utilslibrary.utils.ToastUtil;
import com.zxj.utilslibrary.utils.UIUtil;

/**
 * Created by shenyu_zxjCode on 2017/10/28 0028.
 */

public class HomeLeftItemViewDelegate implements ItemViewDelegate<ShopMainReponse.EntranceListBean> {
    @Override
    public int getItemViewLayoutId() {
        return R.layout.item_left_frame;
    }
    private Activity mActivity;
    public HomeLeftItemViewDelegate(Activity activity){
        this.mActivity=activity;
    }
    @Override
    public boolean isForViewType(ShopMainReponse.EntranceListBean item, int position) {
        if (!item.getTitle().equals("????????????"))
            return true;
        return false;
    }

    @SuppressLint("NewApi")
    @Override
    public void convert(ViewHolder holder, final ShopMainReponse.EntranceListBean entranceListBean, final int position) {

        TextView textView= holder.getView(R.id.tv_left_menu);
        if (entranceListBean.isDot()){
            Drawable rightDrawable = UIUtil.getDrawable(R.drawable.cicle_point);
            rightDrawable.setBounds(0, 0, rightDrawable.getMinimumWidth(), rightDrawable.getMinimumHeight());
            textView.setCompoundDrawables(null, null, rightDrawable, null);

        }
        else {
            textView.setCompoundDrawables(null,null,null,null);
        }
        textView.setText(entranceListBean.getTitle());
        ImageView imageView=  holder.getView(R.id.img_left_menu);
        if (entranceListBean.getImgUrl()!=0) {
            imageView.setImageBitmap(null);
            if (Build.VERSION.SDK_INT> Build.VERSION_CODES.ICE_CREAM_SANDWICH_MR1)
                imageView.setBackground(UIUtil.getDrawable(entranceListBean.getImgUrl()));
            else
                imageView.setBackgroundResource(entranceListBean.getImgUrl());
        }
            if (StringUtil.validText(entranceListBean.getIconURL()))
                ImageUitls.loadImg(entranceListBean.getIconURL(), imageView);
            holder.itemView.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                (LoginReponse) SPUtil.readObject(Constants.LOGININFO_KEY);
                LoginReponse loginReponse = new LoginReponse();
                if (entranceListBean.getTitle().equals("????????????")){
                    //TODO ????????????
                    if (null==loginReponse) {
                        IntentLauncher.with(mActivity).launch(LoginActivity.class);
                        return;
                    }
                    IntentLauncher.with(mActivity).launch(UserRemainingActivity.class);
                }else if(entranceListBean.getTitle().equals("????????????")){
                    if (null==loginReponse) {
                        IntentLauncher.with(mActivity).launch(LoginActivity.class);
                        return;
                    }
                    IntentLauncher.with(mActivity).launch(PurchaseCardActivity.class);

                }else if(entranceListBean.getTitle().equals("????????????")){
                    if (null==loginReponse) {
                        IntentLauncher.with(mActivity).launch(LoginActivity.class);
                        return;
                    }
                    IntentLauncher.with(mActivity).launch(BuyGoodsActivity.class);
                }
                else if(entranceListBean.getTitle().equals("????????????")){
                    if (null==loginReponse) {
                        IntentLauncher.with(mActivity).launch(LoginActivity.class);
                        return;
                    }
                    IntentLauncher.with(mActivity).launch(CardPackageActivity.class);
                }
                else if(entranceListBean.getTitle().equals("????????????")){
                    if (null==loginReponse) {
                        IntentLauncher.with(mActivity).launch(LoginActivity.class);
                        return;
                    }
                    IntentLauncher.with(mActivity).launch(ResellActivity.class);
                }
                else if(entranceListBean.getTitle().equals("????????????")){
                    final ShopMainReponse shopMainReponse= (ShopMainReponse) SPUtil.readObject(Constants.MAIN_SHOP_KEY);
                    if (shopMainReponse!=null) {
                        String qq = shopMainReponse.getPayload().getContacts().getUrl();
                        if (StringUtil.validText(qq))
                            IntentLauncher.with(mActivity).put("weburl", qq).launch(GuessActivity.class);
                    }
                    else
                        ToastUtil.showToastShort("?????????????????????");
                }

                else {
//                        //TODO ????????????
                        IntentLauncher.with(mActivity).put("weburl",entranceListBean.getUrl()).launch(GuessActivity.class);

                }
//                else {
//                    IntentLauncher.with(mActivity).put("weburl",entranceListBean.getUrl()).launch(GuessActivity.class);
//                }
//                switch (position){
//                    case 0:
//                        //TODO ????????????
//                        if (null==loginReponse) {
//                            IntentLauncher.with(mActivity).launch(LoginActivity.class);
//                            return;
//                        }
//                        IntentLauncher.with(mActivity).launch(UserRemainingActivity.class);
//                        break;
//                    case 1:
//                        //TODO ????????????
//                        if (null==loginReponse) {
//                            IntentLauncher.with(mActivity).launch(LoginActivity.class);
//                            return;
//                        }
//                        IntentLauncher.with(mActivity).launch(BuyGoodsActivity.class);
//                        break;
//                    case 3:
//                        //TODO ????????????
//                        if (null==loginReponse) {
//                            IntentLauncher.with(mActivity).launch(LoginActivity.class);
//                            return;
//                        }
//                        IntentLauncher.with(mActivity).launch(PurchaseCardActivity.class);
//                        break;
//                    case 2:
//                        //TODO ????????????
//                        if (null==loginReponse) {
//                            IntentLauncher.with(mActivity).launch(LoginActivity.class);
//                            return;
//                        }
//                        IntentLauncher.with(mActivity).launch(CardPackageActivity.class);
//                        break;
//                    case 4:
//                        //TODO ????????????
////                        ToastUtil.showToastShort("????????????");
//                        if (null==loginReponse) {
//                            IntentLauncher.with(mActivity).launch(LoginActivity.class);
//                            return;
//                        }
//                        IntentLauncher.with(mActivity).launch(ResellActivity.class);
//                        break;
//                    case 5:
//                        //TODO ??????
//                        if (null==loginReponse) {
//                            IntentLauncher.with(mActivity).launch(LoginActivity.class);
//                            return;
//                        }
//                        final ShopMainReponse shopMainReponse= (ShopMainReponse) SPUtil.readObject(Constants.MAIN_SHOP_KEY);
//                        if (shopMainReponse!=null) {
//                            String qq = shopMainReponse.getPayload().getContacts().getQq();
//                            if (StringUtil.validText(qq))
//                                toQQServer(qq);
//                        }
//                        else
//                            ToastUtil.showToastShort("?????????????????????");
//
//                        break;
//                    case 6:
//                        //TODO ????????????
//                        IntentLauncher.with(mActivity).put("weburl",entranceListBean.getUrl()).launch(GuessActivity.class);
//                        break;
//                    case 7:
//                        //TODO QQ??????
//
//                        break;
//                    default:
//
//                        break;
//
//                }
            }
        });
    }
    private void toQQServer(final String qq){
        DialogHelper.commonDialog(mActivity, "????????????", "?????????????????????QQ????????????", "??????", "??????", false, new DialogHelper.ButtonCallback() {
            @Override
            public void onNegative(Dialog dialog) {
                //TODO ????????????
                if (PackageManagerUtil.checkApkExist(mActivity, "com.tencent.mobileqq")){
                    mActivity.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("mqqwpa://im/chat?chat_type=wpa&uin="+qq+"&version=1")));
                }else{
                    ToastUtil.showToastShort("???????????????QQ??????");
                }
            }

            @Override
            public void onPositive(Dialog dialog) {

            }
        }).show();
    }



}
