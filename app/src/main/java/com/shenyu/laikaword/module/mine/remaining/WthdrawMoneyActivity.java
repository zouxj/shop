package com.shenyu.laikaword.module.mine.remaining;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.jakewharton.rxbinding.widget.RxTextView;
import com.shenyu.laikaword.R;
import com.shenyu.laikaword.base.LKWordBaseActivity;
import com.shenyu.laikaword.module.mine.cards.activity.CardBankActivity;
import com.zxj.utilslibrary.utils.IntentLauncher;
import com.zxj.utilslibrary.utils.StringUtil;
import com.zxj.utilslibrary.utils.ToastUtil;
import com.zxj.utilslibrary.utils.UIUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.functions.Action1;

/**
 * 余额提现
 */
public class WthdrawMoneyActivity extends LKWordBaseActivity {


    @BindView(R.id.tv_select_bank)
    TextView tvSelectBank;
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
        RxTextView.textChanges(etTixianNum).subscribe(new Action1<CharSequence>() {
            @SuppressLint("NewApi")
            @Override
            public void call(CharSequence charSequence) {
                isJyan();
            }
        });
        RxTextView.textChanges(tvBankType).subscribe(new Action1<CharSequence>() {
            @SuppressLint("NewApi")
            @Override
            public void call(CharSequence charSequence) {
                isJyan();
            }
        });
    }

    /**
     * 判断空值
     */
    @SuppressLint("NewApi")
    public void isJyan(){
        String bankName = etTixianNum.getText().toString().trim();
        String yue = tvBankType.getText().toString().trim();;
        if (StringUtil.validText(yue)&&StringUtil.validText(bankName)) {
            tvTixianing.setEnabled(true);
            tvTixianing.setBackground(UIUtil.getDrawable(R.drawable.bg_bt_login_rectangle_light));
        } else {
            tvTixianing.setEnabled(false);
            tvTixianing.setBackground(UIUtil.getDrawable(R.drawable.bg_gray_icon));
        }
    }
    @Override
    public void setupActivityComponent() {

    }

    @OnClick({R.id.tv_tixianing, R.id.tv_select_bank,R.id.tv_all_tixian})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_tixianing:
                ToastUtil.showToastShort("next");
                break;
            case R.id.tv_select_bank:
                Intent intent = new Intent(this,CardBankActivity.class);
                startActivityForResult(intent,0);
                break;
            case R.id.tv_all_tixian:
                etTixianNum.setText("3000.0");
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
                tvBankType.setText(text);
        }
    }

}
