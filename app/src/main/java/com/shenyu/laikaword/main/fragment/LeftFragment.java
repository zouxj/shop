package com.shenyu.laikaword.main.fragment;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.shenyu.laikaword.R;
import com.shenyu.laikaword.adapter.CommonAdapter;
import com.shenyu.laikaword.adapter.ViewHolder;
import com.shenyu.laikaword.base.IKWordBaseFragment;
import com.shenyu.laikaword.bean.reponse.LoginReponse;
import com.shenyu.laikaword.common.CircleTransform;
import com.shenyu.laikaword.common.Constants;
import com.shenyu.laikaword.module.login.activity.LoginActivity;
import com.shenyu.laikaword.module.mine.address.activity.AddressInfoActivity;
import com.shenyu.laikaword.module.mine.cards.activity.CardBankInfoActivity;
import com.shenyu.laikaword.module.shop.activity.BuyGoodsActivity;
import com.shenyu.laikaword.module.mine.remaining.PurchaseCardActivity;
import com.shenyu.laikaword.module.mine.cards.activity.CardPackageActivity;
import com.shenyu.laikaword.module.mine.remaining.UserRemainingActivity;
import com.shenyu.laikaword.module.mine.systemsetting.activity.SettingSystemActivity;
import com.shenyu.laikaword.rxbus.EventType;
import com.shenyu.laikaword.rxbus.RxBus;
import com.squareup.picasso.Picasso;
import com.zxj.utilslibrary.utils.IntentLauncher;
import com.zxj.utilslibrary.utils.SPUtil;
import com.zxj.utilslibrary.utils.UIUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import rx.functions.Action1;


public class LeftFragment extends IKWordBaseFragment {

    @BindView(R.id.tv_user_name)
    TextView tvUserName;
    @BindView(R.id.tv_user_head)
    ImageView tvUserHead;
    @BindView(R.id.rc_left_view)
    RecyclerView rcLeftView;
    CommonAdapter commonAdapter;
    public static final int[] leftData={
        R.mipmap.left_money_icon,R.mipmap.left_gouwuchei_icon,
                R.mipmap.left_tihuo_icon,R.mipmap.left_wodekabao_icon,
                R.mipmap.left_yinhangka_icon,R.mipmap.left_dizh_icon,R.mipmap.lfet_setting_icon};


    @Override
    public int bindLayout() {
        return R.layout.fragment_left;
    }

  @OnClick({R.id.tv_user_head})
  public void onClick(View view){
        switch (view.getId()){
            case R.id.tv_user_head:
                IntentLauncher.with(getActivity()).launch(LoginActivity.class);
                break;
        }

  }


    @Override
    public void doBusiness() {
        RxBus.getDefault().toObservable(EventType.class).subscribe(new Action1<EventType>() {
            @Override
            public void call(EventType eventType) {
                switch (eventType.action){
                    case EventType.ACTION_UPDATA_USER:
                        LoginReponse loginReponse = (LoginReponse) SPUtil.readObject(Constants.LOGININFO_KEY);
                        if (null!=loginReponse) {
                            Picasso.with(UIUtil.getContext()).load(loginReponse.getPayload().getAvatar()).placeholder(R.mipmap.left_user_icon)
                                    .error(R.mipmap.left_user_icon).resize(50, 50).transform(new CircleTransform()).into(tvUserHead);
                            tvUserName.setText(loginReponse.getPayload().getNickname());
                        }
                        break;
                }
            }
        });
        List<String> dataList = new ArrayList<>();
        dataList.add("我的余额");
        dataList.add("我的购买");
        dataList.add("我的提货");
        dataList.add("我的卡包");
        dataList.add("银行卡");
        dataList.add("我的地址");
        dataList.add("系统设置");

        rcLeftView.setLayoutManager(new LinearLayoutManager(getActivity()));
         commonAdapter=  new CommonAdapter<String>(R.layout.item_left_frame,dataList) {
            @Override
            protected void convert(ViewHolder holder, String s, int position) {
                holder.setText(R.id.tv_left_menu,s);
                holder.setBackground(R.id.img_left_menu, UIUtil.getDrawable(leftData[position]));
            }

            @Override
            public void onBindViewHolder(ViewHolder holder, final int position) {
                super.onBindViewHolder(holder, position);
                if (position==getItemCount()-1){
                    holder.getView(R.id.ly_line).setVisibility(View.VISIBLE);
                }
                holder.setOnClickListener(R.id.tv_left_fragment, new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        switch (position){
                            case 0:
                                //TODO 我的余额
                                IntentLauncher.with(getActivity()).launch(UserRemainingActivity.class);
                                break;
                            case 1:
                                //TODO 我的购买
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
                                IntentLauncher.with(getActivity()).launch(CardBankInfoActivity.class);

                                break;
                            case 5:
                                //TODO 我的地址
                                IntentLauncher.with(getActivity()).launch(AddressInfoActivity.class);
                                break;
                            case 6:
                                //TODO 我的设置
                                IntentLauncher.with(getActivity()).launch(SettingSystemActivity.class);
                                break;
                        }
                    }
                });
            }
        };
        rcLeftView.setAdapter(commonAdapter);

    }

    @Override
    public void setupFragmentComponent() {

    }

    @Override
    public void requestData() {
        LoginReponse loginReponse = (LoginReponse) SPUtil.readObject(Constants.LOGININFO_KEY);
        if (null!=loginReponse){
            Picasso.with(UIUtil.getContext()).load(loginReponse.getPayload().getAvatar()) .placeholder(R.mipmap.left_user_icon)
                    .error(R.mipmap.left_user_icon).resize(50, 50).transform(new CircleTransform()).into(tvUserHead);
       tvUserName.setText(loginReponse.getPayload().getNickname());
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
