package com.shenyu.laikaword.module.goods.pickupgoods.ui.activity;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.text.Spanned;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.shenyu.laikaword.R;
import com.shenyu.laikaword.base.LKWordBaseActivity;
import com.shenyu.laikaword.model.bean.reponse.BaseReponse;
import com.shenyu.laikaword.helper.ImageUitls;
import com.shenyu.laikaword.model.bean.reponse.AddressReponse;
import com.shenyu.laikaword.model.bean.reponse.CarPagerReponse;
import com.shenyu.laikaword.common.Constants;
import com.shenyu.laikaword.helper.DialogHelper;
import com.shenyu.laikaword.model.bean.reponse.LoginReponse;
import com.shenyu.laikaword.module.us.address.ui.activity.SelectAddressActivity;
import com.shenyu.laikaword.model.net.api.ApiCallback;
import com.shenyu.laikaword.model.net.retrofit.RetrofitUtils;
import com.shenyu.laikaword.module.us.setpassword.SetPassWordMsgCodeActivity;
import com.shenyu.laikaword.ui.view.widget.AmountView;
import com.zxj.utilslibrary.utils.IntentLauncher;
import com.zxj.utilslibrary.utils.SPUtil;
import com.zxj.utilslibrary.utils.StringUtil;
import com.zxj.utilslibrary.utils.ToastUtil;
import com.zxj.utilslibrary.widget.countdownview.step.StepView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 京东卡提货
 */
public class PickUpActivity extends LKWordBaseActivity {

    @BindView(R.id.step_view)
    StepView setStepTitles;
    @BindView(R.id.iv_tihuo_img)
    ImageView ivTihuoImg;
    @BindView(R.id.tv_tihuo_name)
    TextView tvTihuoName;
    @BindView(R.id.tv_tihuo_count)
    TextView tvTihuoCount;
    @BindView(R.id.tv_goumai_count)
    TextView tvGoumaiCount;
    @BindView(R.id.tv_tihuo_all_select)
    TextView tvTihuoAllSelect;
    @BindView(R.id.av_zj)
    AmountView avZj;
    @BindView(R.id.tv_tihuo_tianxia)
    TextView tvTihuoTianxia;
    @BindView(R.id.tv_tihuo_all)
    TextView tvTihuoAll;
    @BindView(R.id.tv_tihuo_commit)
    TextView tvTihuoCommit;
    private int count=1;
    CarPagerReponse.Bean bean;
    @Override
    public int bindLayout() {
        return R.layout.activity_pick_up;
    }

    @Override
    public void initView() {
        setToolBarTitle("申请提货");
        tvGoumaiCount.setText("提货数量");
        avZj.setOnAmountChangeListener(new AmountView.OnAmountChangeListener() {
            @Override
            public void onAmountChange(View view, int amount) {
                count = amount;
                tvTihuoAll.setText(amount+"张");
            }
        });
    }

    @Override
    public void doBusiness(Context context) {
        payloadBean= (AddressReponse.PayloadBean) SPUtil.readObject("SAVA_ADDRESS");
        if (payloadBean!=null){
            Spanned dizhi= Html.fromHtml("<font color='#333333' >"+payloadBean.getReceiveName()+payloadBean.getPhone()+"</font><br/><font color='#999999' >"+payloadBean.getProvince()+payloadBean.getCity()+payloadBean.getDetail()+"</font>");
            tvTihuoTianxia.setGravity(Gravity.CENTER);
            tvTihuoTianxia.setText(dizhi);

        }
        List<String> titles = new ArrayList<String>();
        titles.add("提货申请");
        titles.add("提货中");
        titles.add("提货完成");

        setStepTitles.setStepTitles(titles);
         bean = (CarPagerReponse.Bean) getIntent().getSerializableExtra("bean");
        if (null!=bean){
            ImageUitls.loadImg(bean.getGoodsImage(),ivTihuoImg);
            tvTihuoName.setText(bean.getGoodsName());
                tvTihuoCount.setText("数量:" + StringUtil.formatIntger(bean.getQuantity())+"张");
                avZj.setGoods_storage(StringUtil.formatIntger(bean.getQuantity()));
                tvTihuoAll.setText("1张");

        }
        payloadBean = (AddressReponse.PayloadBean) SPUtil.readObject(Constants.SAVA_ADDRESS);
        if (null!=payloadBean) {
            Spanned dizhi = Html.fromHtml("<font color='#333333' >" + payloadBean.getReceiveName() + payloadBean.getPhone() + "</font><br/><font color='#999999' >" + payloadBean.getProvince() + payloadBean.getCity() + payloadBean.getDetail() + "</font>");
            tvTihuoTianxia.setGravity(Gravity.CENTER);
            tvTihuoTianxia.setText(dizhi);
        }else {
            tvTihuoTianxia.setText("");
        }
    }


    @Override
    public void setupActivityComponent() {

    }

    @OnClick({R.id.tv_tihuo_tianxia, R.id.tv_tihuo_all_select, R.id.tv_tihuo_commit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_tihuo_tianxia:
                //TODO 去选择收货地址
                Intent intent = new Intent(PickUpActivity.this,SelectAddressActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("payloadBean",payloadBean);
                intent.putExtras(bundle);
                startActivityForResult(intent,Constants.REQUEST_ADDRESS);
                break;
            case R.id.tv_tihuo_all_select:
                if (null!=bean) {
                    count= StringUtil.formatIntger(bean.getQuantity());
                    avZj.etAmount.setText(bean.getQuantity());
                    tvTihuoAll.setText(bean.getQuantity()+"张");
                }
                break;
            case R.id.tv_tihuo_commit:
                if (StringUtil.validText(tvTihuoTianxia.getText().toString().trim())) {
                    Map<String,String> param = new HashMap<>();
                    param.put("packageId",bean.getPackageId());
                    param.put("quantity",count+"");
                    param.put("addressId",payloadBean.getAddressId());
                    requestData(param);
                } else{
                DialogHelper.tianXAddress(this, new DialogHelper.ButtonCallback() {
                    @Override
                    public void onNegative(Dialog dialog) {
                        Intent intent = new Intent(PickUpActivity.this,SelectAddressActivity.class);
                        startActivityForResult(intent,Constants.REQUEST_ADDRESS);
                    }

                    @Override
                    public void onPositive(Dialog dialog) {

                    }
                });
                }
                //TODO 确认提货
                break;
        }
    }
   private AddressReponse.PayloadBean payloadBean;
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            //判断是哪一个的回调
            if (requestCode == Constants.REQUEST_ADDRESS) {
              payloadBean   = (AddressReponse.PayloadBean) data.getSerializableExtra("address");
        if (null!=payloadBean){
            Spanned dizhi= Html.fromHtml("<font color='#333333' >"+payloadBean.getReceiveName()+payloadBean.getPhone()+"</font><br/><font color='#999999' >"+payloadBean.getProvince()+payloadBean.getCity()+payloadBean.getDetail()+"</font>");
            tvTihuoTianxia.setGravity(Gravity.CENTER);
            tvTihuoTianxia.setText(dizhi);
        }else {
            tvTihuoTianxia.setText("");
        }
            }
        }
    }

    /**
     * tihuo 提货提交
     * @param param
     */
    private void requestData(final Map<String,String> param){
        LoginReponse loginReponse= Constants.getLoginReponse();
        if (loginReponse.getPayload().getIsSetTransactionPIN()==0){
            DialogHelper.commonDialog(mActivity, "温馨提示", "您尚未设置支付密码", "取消", "去设置", false, new DialogHelper.ButtonCallback() {
                @Override
                public void onNegative(Dialog dialog) {
                    IntentLauncher.with(mActivity).launch(SetPassWordMsgCodeActivity.class);
                }

                @Override
                public void onPositive(Dialog dialog) {

                }
            }).show();
            return;
        }
        DialogHelper.setInputDialog(mActivity, true,"", "",new DialogHelper.LinstenrText() {
            @Override
            public void onLintenerText(String passWord) {
                param.put("transactionPIN",passWord);
                retrofitUtils.setLifecycleTransformer(PickUpActivity.this.bindToLifecycle()).addSubscription(RetrofitUtils.apiStores.extractPackage(param), new ApiCallback<BaseReponse>() {
                    @Override
                    public void onSuccess(BaseReponse model) {
                        if (model.isSuccess())
                            IntentLauncher.with(PickUpActivity.this).put("type","JD").launchFinishCpresent(PickUpSuccessActivity.class);

                    }

                    @Override
                    public void onFailure(String msg) {
                        ToastUtil.showToastShort(msg);
                    }

                    @Override
                    public void onFinish() {

                    }
                });
            }

            @Override
            public void onWjPassword() {

            }
        }).show();

    }
}
