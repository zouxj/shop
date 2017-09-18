package com.shenyu.laikaword.module.mine.remaining;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.shenyu.laikaword.R;
import com.shenyu.laikaword.base.LKWordBaseActivity;
import com.shenyu.laikaword.module.mine.cards.CardBankActivity;
import com.zxj.utilslibrary.utils.IntentLauncher;
import com.zxj.utilslibrary.utils.ToastUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 余额提现
 */
public class WthdrawMoneyActivity extends LKWordBaseActivity {


    @BindView(R.id.tv_wthdraw_add_bank)
    TextView tvWthdrawAddBank;
    @BindView(R.id.et_wthdraw_num)
    EditText etWthdrawNum;
    @BindView(R.id.tv_next)
    TextView tvNext;

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

    }

    @Override
    public void setupActivityComponent() {

    }
    @OnClick({R.id.tv_next,R.id.tv_wthdraw_add_bank})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.tv_next:
                ToastUtil.showToastShort("next");
                break;
            case R.id.tv_wthdraw_add_bank:
                IntentLauncher.with(this).launch(CardBankActivity.class);
            break;
        }
    }

}
