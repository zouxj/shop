package com.shenyu.laikaword.module.mine.remaining;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.jakewharton.rxbinding.widget.RxTextView;
import com.shenyu.laikaword.R;
import com.shenyu.laikaword.base.LKWordBaseActivity;
import com.shenyu.laikaword.bean.BaseReponse;
import com.shenyu.laikaword.module.mine.cards.activity.CardBankActivity;
import com.shenyu.laikaword.retrofit.ApiCallback;
import com.shenyu.laikaword.retrofit.RetrofitUtils;
import com.shenyu.laikaword.rxbus.RxBus;
import com.shenyu.laikaword.rxbus.event.Event;
import com.shenyu.laikaword.rxbus.event.EventType;
import com.zxj.utilslibrary.utils.IntentLauncher;
import com.zxj.utilslibrary.utils.StringUtil;
import com.zxj.utilslibrary.utils.ToastUtil;
import com.zxj.utilslibrary.utils.UIUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Observable;
import rx.Observer;
import rx.functions.Action1;
import rx.functions.Func2;

/**
 * 余额提现
 */
public class WthdrawMoneyActivity extends LKWordBaseActivity {


    @BindView(R.id.tv_select_bank)
    ImageView tvSelectBank;
    @BindView(R.id.et_tixian_num)
    EditText etTixianNum;
    @BindView(R.id.tv_account_yue)
    TextView tvAccountYue;
    @BindView(R.id.tv_all_tixian)
    TextView tvAllTixian;
    @BindView(R.id.tv_tixianing)
    TextView tvTixianing;
    @BindView(R.id.tv_bank_type)
    TextView tvBankType;
    String carID;

    @Override
    public int bindLayout() {
        return R.layout.activity_wthdraw_money;
    }

    @Override
    public void initView() {
        setToolBarTitle("余额提现");
    }

    @Override
    public void doBusiness(Context context) {
        Observable.combineLatest(RxTextView.textChanges(etTixianNum), RxTextView.textChanges(tvBankType), new Func2<CharSequence, CharSequence, Boolean>() {
            @Override
            public Boolean call(CharSequence charSequence, CharSequence charSequence2) {
                return isJyan();
            }
        }).subscribe(new Action1<Boolean>() {
            @SuppressLint("NewApi")
            @Override
            public void call(Boolean o) {
                if (o) {
                    tvTixianing.setEnabled(true);
                    tvTixianing.setBackground(UIUtil.getDrawable(R.drawable.bg_bt_login_rectangle_light));
                }else {
                    tvTixianing.setEnabled(false);
                    tvTixianing.setBackground(UIUtil.getDrawable(R.drawable.bg_gray_icon));
                }
            }
        });

        String yue = getIntent().getStringExtra("acountyue");
        if (StringUtil.validText(yue))
        tvAccountYue.setText(yue);
    }

    /**
     * 判断空值
     */
    @SuppressLint("NewApi")
    public Boolean isJyan(){
        String bankName = etTixianNum.getText().toString().trim();
        String yue = tvBankType.getText().toString().trim();
        if (StringUtil.validText(yue)&&StringUtil.validText(bankName))
            return true;
        else
            return  false;
    }
    @Override
    public void setupActivityComponent() {

    }

    @OnClick({R.id.tv_tixianing, R.id.tv_select_bank,R.id.tv_all_tixian})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_tixianing:
                RetrofitUtils.getRetrofitUtils().addSubscription(RetrofitUtils.apiStores.withdrawMoney(etTixianNum.getText().toString().trim(), carID), new ApiCallback<BaseReponse>() {
                    @Override
                    public void onSuccess(BaseReponse model) {
                        if (model.isSuccess()) {
                            ToastUtil.showToastShort("提现成功");
                            RxBus.getDefault().post(new Event(EventType.ACTION_UPDATA_USER_REQUEST, null));
                        }
                        else {
                            ToastUtil.showToastShort(model.getError().getMessage());
                        }
                    }

                    @Override
                    public void onFailure(String msg) {

                    }

                    @Override
                    public void onFinish() {

                    }
                });
                break;
            case R.id.tv_select_bank:
                Intent intent = new Intent(this,CardBankActivity.class);
                startActivityForResult(intent,0);
                break;
            case R.id.tv_all_tixian:
                etTixianNum.setText(tvAccountYue.getText().toString().trim());
                break;
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==0 && resultCode==RESULT_OK){
            Bundle bundle = data.getExtras();
            String text =null;
            if(bundle!=null)
                text=bundle.getString("bankName");
                carID = bundle.getString("carID");
                tvBankType.setText(text);
        }
    }

}
