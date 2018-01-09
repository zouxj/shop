package com.shenyu.laikaword.module.goods.pickupgoods.ui.activity;

import android.app.Dialog;
import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.shenyu.laikaword.R;
import com.shenyu.laikaword.base.LKWordBaseActivity;
import com.shenyu.laikaword.model.bean.reponse.BaseReponse;
import com.shenyu.laikaword.helper.ImageUitls;
import com.shenyu.laikaword.model.bean.reponse.CarPagerReponse;
import com.shenyu.laikaword.common.Constants;
import com.shenyu.laikaword.helper.DialogHelper;
import com.shenyu.laikaword.model.bean.reponse.LoginReponse;
import com.shenyu.laikaword.model.net.api.ApiCallback;
import com.shenyu.laikaword.model.net.retrofit.RetrofitUtils;
import com.shenyu.laikaword.module.us.setpassword.SetPassWordMsgCodeActivity;
import com.shenyu.laikaword.ui.view.widget.AmountView;
import com.zxj.utilslibrary.utils.IntentLauncher;
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
 * 手机卡提货
 */
public class PickUpTelActivity extends LKWordBaseActivity {


    @BindView(R.id.step_view)
    StepView stepView;
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
    EditText tvTihuoTianxia;
    @BindView(R.id.tv_tihuo_all)
    TextView tvTihuoAll;
    @BindView(R.id.tv_tihuo_commit)
    TextView tvTihuoCommit;
    @BindView(R.id.tv_heji)
    TextView tv_bottom;
    @BindView(R.id.tv_moren)
    TextView tvMoren;
    private int count=1;
    String phone;

    @Override
    public int bindLayout() {
        return R.layout.activity_pick_up_tel;
    }
    CarPagerReponse.Bean bean;
    @Override
    public void initView() {
        tvGoumaiCount.setText("提货数量");
        tv_bottom.setText("充值金额:");
        avZj.setOnAmountChangeListener(new AmountView.OnAmountChangeListener() {
            @Override
            public void onAmountChange(View view, int amount) {
                count = amount;
                if (bean!=null)
                        tvTihuoAll.setText(StringUtil.m2((StringUtil.formatDouble(bean.getGoodsValue())*amount))+"元");
            }
        });
        setToolBarTitle("提货申请");
        tvTihuoCommit.setText("确认充值");

    }

    @Override
    public void doBusiness(Context context) {
        List<String> titles = new ArrayList<String>();
        titles.add("提货申请");
        titles.add("提货中");
        titles.add("提货完成");
        stepView.setStepTitles(titles);
        bean = (CarPagerReponse.Bean) getIntent().getSerializableExtra("bean");
            if (null!=bean)
                avZj.setGoods_storage(StringUtil.formatIntger(bean.getQuantity()));
                ImageUitls.loadImg(bean.getGoodsImage(),ivTihuoImg);
                tvTihuoName.setText(bean.getGoodsName());
            if (StringUtil.validText(bean.getQuantity())) {
                tvTihuoCount.setText("数量:" + StringUtil.formatIntger(bean.getQuantity())+"张");
                avZj.setGoods_storage(StringUtil.formatIntger(bean.getQuantity()));
            }
                tvTihuoAll.setText(StringUtil.m2(StringUtil.formatDouble((bean.getGoodsValue())))+"元");
        tvTihuoTianxia.setText(Constants.getLoginReponse().getPayload().getBindPhone());
        phone = tvTihuoTianxia.getText().toString().trim().replace(" ", "");
        if (phone.length()>=11) {
            if (phone.contains("-")) {
                phone=  phone.replace("-", "");
            }
            StringBuilder sb = new StringBuilder(phone);
            for (int i=0;i<sb.length();i++){
                if (i==3||i==8){
                    sb.insert(i,' ');
                }
            }
            tvTihuoTianxia.setText(sb.toString());
        }
        tvTihuoTianxia.addTextChangedListener(new TextWatcher() {
               @Override
               public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }
                 @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                     vaildPhone(s, start, before);
                }

            /**
             * 修改手机格式
             * @param s
             * @param start
             * @param before
             */
            private void vaildPhone(CharSequence s, int start, int before) {
                String phone = s.toString().trim();
                if (phone.length()>=11) {
                    if (phone.contains("-")) {
                        phone=  phone.replace("-", "");
                    }
                }
                if (phone  == null || phone.length() == 0)
                    return;
                StringBuilder sb = new StringBuilder();
                for (int i = 0; i < phone.length(); i++) {
                    if (i != 3 && i != 8 && phone.charAt(i) == ' ') {
                        continue;
                    } else {
                        sb.append(phone.charAt(i));
                        if ((sb.length() == 4 || sb.length() == 9)
                                && sb.charAt(sb.length() - 1) != ' ') {
                            sb.insert(sb.length() - 1, ' ');
                        }
                    }
                }
                if (!sb.toString().equals(phone.toString())) {
                    int index = start + 1;
                    if (sb.charAt(start) == ' ') {
                        if (before == 0) {
                            index++;
                        } else {
                            index--;
                        }
                    } else {
                        if (before == 1) {
                            index--;
                        }
                    }
                    tvTihuoTianxia.setText(sb.toString());
                    tvTihuoTianxia.setSelection(index);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
              if (!editable.toString().equals(Constants.getLoginReponse().getPayload().getBindPhone())){
                       tvMoren.setVisibility(View.GONE);
                    }
               }
           });
        }



    @Override
    public void setupActivityComponent() {

    }


    @OnClick({ R.id.tv_tihuo_all_select, R.id.tv_tihuo_commit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_tihuo_all_select:
                if (null!=bean)
                avZj.etAmount.setText(bean.getQuantity());
                tvTihuoAll.setText(bean.getQuantity());
                tvTihuoAll.setText(Double.valueOf(bean.getGoodsValue())*Integer.valueOf(bean.getQuantity())+"元");
                break;
            case R.id.tv_tihuo_commit:
                String phone = tvTihuoTianxia.getText().toString().trim().replace(" ", "");
                if (!StringUtil.validText(phone)) {
                    ToastUtil.showToastShort("请输入充值手机号码");
                    return;
                }
                if (phone.length()<11) {

                    ToastUtil.showToastShort("请输入正确手机号码");
                    return;
                }
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

                    Map<String,String> param = new HashMap<>();
                    param.put("packageId",bean.getPackageId());
                    param.put("quantity",count+"");
                     param.put("phone",phone);
                    requestData(param);

                break;
        }
    }

    /**
     * tihuo 充值提交
     * @param param
     */
    private void requestData(final Map<String,String> param){
            DialogHelper.setInputDialog(mActivity, true,"充值号码:"+param.get("phone")+"\n"+"充值金额:"+tvTihuoAll.getText().toString().trim(), "",new DialogHelper.LinstenrText() {
            @Override
            public void onLintenerText(String passWord) {
                param.put("transactionPIN",passWord);
                retrofitUtils.setLifecycleTransformer(PickUpTelActivity.this.bindToLifecycle()).addSubscription(RetrofitUtils.apiStores.extractPackage(param), new ApiCallback<BaseReponse>() {
                    @Override
                    public void onSuccess(BaseReponse model) {
                        if (model.isSuccess())
                            IntentLauncher.with(PickUpTelActivity.this).put("type","HUAFEI").launchFinishCpresent(PickUpSuccessActivity.class);

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
