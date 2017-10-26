package com.shenyu.laikaword.di.component;

import android.content.Context;
import android.view.View;

import com.shenyu.laikaword.R;
import com.shenyu.laikaword.base.LKWordBaseActivity;
import com.shenyu.laikaword.di.module.ShopModule;
import com.shenyu.laikaword.module.home.ui.activity.MainActivity;
import com.shenyu.laikaword.module.goods.order.ui.activity.ConfirmOrderActivity;
import com.shenyu.laikaword.module.mine.wallet.remaining.PurchaseCardActivity;
import com.zxj.utilslibrary.utils.IntentLauncher;

import butterknife.OnClick;
import dagger.Subcomponent;

/**
 * Created by shenyu_zxjCode on 2017/9/25 0025.
 */
@Subcomponent(modules = {ShopModule.class})
public interface ShopCommponent {
    ConfirmOrderActivity inject(ConfirmOrderActivity confirmOrderActivity);

    class PickUpSuccessActivity extends LKWordBaseActivity {


        @Override
        public int bindLayout() {
            return R.layout.activity_pick_up_success;
        }

        @Override
        public void doBusiness(Context context) {
            setToolBarTitle("申请完成");

        }


        @Override
        public void setupActivityComponent() {

        }

        @OnClick({R.id.tv_sq_check_state, R.id.tv_sq_back_main})
        public void onViewClicked(View view) {
            switch (view.getId()) {
                case R.id.tv_sq_check_state:
                    //TODO 查看申请状态
                    IntentLauncher.with(this).launch(PurchaseCardActivity.class);
                    break;
                case R.id.tv_sq_back_main:
                    //TODO 返回首页
                    IntentLauncher.with(this).launch(MainActivity.class);
                    break;
            }
        }
    }
}
