package com.shenyu.laikaword.main.fragment;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.shenyu.laikaword.R;
import com.shenyu.laikaword.adapter.CommonAdapter;
import com.shenyu.laikaword.adapter.ViewHolder;
import com.shenyu.laikaword.base.IKWordBaseFragment;
import com.shenyu.laikaword.module.shop.BuyGoodsActivity;
import com.shenyu.laikaword.module.mine.address.activity.AddAdressActivity;
import com.shenyu.laikaword.module.mine.PurchaseCardActivity;
import com.shenyu.laikaword.module.mine.cards.CardBankActivity;
import com.shenyu.laikaword.module.mine.cards.CardPackageActivity;
import com.shenyu.laikaword.module.mine.remaining.UserRemainingActivity;
import com.shenyu.laikaword.module.mine.systemsetting.SettingSystemActivity;
import com.zxj.utilslibrary.utils.IntentLauncher;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;


public class LeftFragment extends IKWordBaseFragment {

    @BindView(R.id.tv_user_head)
    TextView tvUserHead;
    @BindView(R.id.rc_left_view)
    RecyclerView rcLeftView;

    @Override
    public int bindLayout() {
        return R.layout.fragment_left;
    }

    @Override
    public void doBusiness() {
        List<String> dataList = new ArrayList<>();
        dataList.add("我的余额");
        dataList.add("我的购买");
        dataList.add("我的提货");
        dataList.add("我的卡包");
        dataList.add("银行卡");
        dataList.add("我的地址");
        dataList.add("系统设置");

        rcLeftView.setLayoutManager(new LinearLayoutManager(getActivity()));
        rcLeftView.setAdapter(new CommonAdapter<String>(R.layout.item_left_frame,dataList) {
            @Override
            protected void convert(ViewHolder holder, String s, int position) {
                holder.setText(R.id.tv_left_menu,s);
            }

            @Override
            public void onBindViewHolder(ViewHolder holder, final int position) {
                super.onBindViewHolder(holder, position);
                holder.setOnClickListener(R.id.tv_left_fragment, new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        switch (position){
                            case 0:
                                //TODO 我的余额
                               IntentLauncher.with(getActivity()).launch(UserRemainingActivity.class);
                                break;
                            case 1:
                                //TODO
                                IntentLauncher.with(getActivity()).launch(BuyGoodsActivity.class);
                                break;
                            case 2:
                                //TODO 我的提货
                                IntentLauncher.with(getActivity()).launch(PurchaseCardActivity.class);
                                break;
                            case 3:
                                //TODO 我的卡包
                                IntentLauncher.with(getActivity()).launch(CardPackageActivity.class);
                                break;
                            case 4:
                                //TODO 我的银行卡
                                IntentLauncher.with(getActivity()).launch(CardBankActivity.class);

                                break;
                            case 5:
                                //TODO 我的地址
                                IntentLauncher.with(getActivity()).launch(AddAdressActivity.class);
                                break;
                            case 6:
                                //TODO 我的设置
                                IntentLauncher.with(getActivity()).launch(SettingSystemActivity.class);
                                break;
                        }
                    }
                });
            }
        });
    }

    @Override
    public void setupFragmentComponent() {

    }

    @Override
    public void requestData() {

    }

}
