package com.shenyu.laikaword.module.us.resell.ui.activity;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.shenyu.laikaword.R;
import com.shenyu.laikaword.base.LKWordBaseActivity;
import com.shenyu.laikaword.di.module.mine.MineModule;
import com.shenyu.laikaword.helper.ImageUitls;
import com.shenyu.laikaword.helper.RecycleViewDivider;
import com.shenyu.laikaword.model.GoodsViewGroupItem;
import com.shenyu.laikaword.model.adapter.CommonAdapter;
import com.shenyu.laikaword.model.bean.reponse.BaseReponse;
import com.shenyu.laikaword.model.bean.reponse.SellInfoReponse;
import com.shenyu.laikaword.model.holder.ViewHolder;
import com.shenyu.laikaword.module.launch.LaiKaApplication;
import com.shenyu.laikaword.module.us.resell.presenter.CommitResellPresenter;
import com.shenyu.laikaword.module.us.resell.view.CommitResellView;
import com.shenyu.laikaword.module.us.resell.view.ResellInputCodeView;
import com.shenyu.laikaword.ui.view.widget.AmountView;
import com.shenyu.laikaword.ui.view.widget.GoodsViewGroup;
import com.zxj.utilslibrary.utils.IntentLauncher;
import com.zxj.utilslibrary.utils.StringUtil;
import com.zxj.utilslibrary.utils.ToastUtil;
import com.zxj.utilslibrary.utils.UIUtil;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;

public class CommitResellActivity extends LKWordBaseActivity implements CommitResellView {

    @BindView(R.id.goods_viewgroup)
    GoodsViewGroup goodsViewGroup;
    @BindView(R.id.ry_lv)
    RecyclerView recyclerView;
    @BindView(R.id.av_zj)
    AmountView amountView;
    @BindView(R.id.tv_zhekou)
    TextView tgZheKou;
    private List<SellInfoReponse.PayloadBean> payloadBeans =new ArrayList<>();
    @BindView(R.id.tv_add_code)
    TextView textCode;
    @Inject
    CommitResellPresenter commitResellPresenter;

    @Override
    public int bindLayout() {
        return R.layout.activity_commit_resell;
    }

    @Override
    public void initView() {
        amountView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.WRAP_CONTENT));
        amountView.setFlg(1);
        amountView.setOnDoubleAmountChangeListener(new AmountView.OnDoubleAmountChangeListener() {
            @Override
            public void onAmountChange(View view, double amount) {
            }
        });


        recyclerView.setNestedScrollingEnabled(false);
        recyclerView.addItemDecoration(new RecycleViewDivider(this, LinearLayoutManager.HORIZONTAL,(int) UIUtil.dp2px(1),UIUtil.getColor(R.color.main_bg_gray)));
        recyclerView.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
        recyclerView.setAdapter(new CommonAdapter< SellInfoReponse.PayloadBean>(R.layout.resell_goods_item,payloadBeans) {
            @Override
            protected void convert(ViewHolder holder, SellInfoReponse.PayloadBean payloadBean, int position) {
                ImageUitls.loadImg(payloadBean.getGoodsImg(), (ImageView) holder.getView(R.id.img_rsell_img));
                holder.setText(R.id.tv_resell_shop_name,payloadBean.getGoodsName());
                holder.setText(R.id.tv_zhuamai_count,"转卖数量:"+payloadBean.getNum());
            }


        });
    }
     SellInfoReponse sellInfoReponse;
    @Override
    public void doBusiness(Context context) {

        sellInfoReponse = (SellInfoReponse) getIntent().getSerializableExtra("resellInfo");
        if (sellInfoReponse!=null){
            payloadBeans.add(sellInfoReponse.getPayload());
            tgZheKou.setText("设置转卖折扣（"+sellInfoReponse.getPayload().getDiscountRange().get(0)+"-"+sellInfoReponse.getPayload().getDiscountRange().get(1)+"折）");
            goodsViewGroup.addItemViews(getItems(sellInfoReponse.getPayload().getDiscountOptions()));
            goodsViewGroup.setGroupClickListener(new GoodsViewGroup.OnGroupItemClickListener() {
                @Override
                public void onGroupItemClick(int itemPos, String key, String value) {
                    amountView.etAmount.setText(sellInfoReponse.getPayload().getDiscountOptions().get(itemPos).getValue().trim());
                }
            });
            goodsViewGroup.chooseItemStyle(1);
            textCode.setText("转卖总额:"+StringUtil.m2((double) (StringUtil.formatDouble(sellInfoReponse.getPayload().getOriginPrice())*sellInfoReponse.getPayload().getNum())));
            amountView.setDGoods_storage(StringUtil.formatDouble(sellInfoReponse.getPayload().getDiscountRange().get(1)));
            amountView.setMixZheKou(StringUtil.formatDouble(sellInfoReponse.getPayload().getDiscountRange().get(0)));
        }

    }

    @Override
    public void setupActivityComponent() {
        LaiKaApplication.get(this).getAppComponent().plus(new MineModule(this,this)).inject(this);
    }
    private List<GoodsViewGroupItem> getItems(List<SellInfoReponse.PayloadBean.DiscountOptionsBean> discountOptions) {
        List<GoodsViewGroupItem> items = new ArrayList<>();
        for (SellInfoReponse.PayloadBean.DiscountOptionsBean discountOptionsBean:discountOptions){
            items.add(new GoodsViewGroupItem(0 + "", discountOptionsBean.getValue()+"折\n"+discountOptionsBean.getDesc()));
        }
        return items;
    }
    @OnClick({R.id.tv_zhuamai})
    public void onClick(){
        //TODO 确认转卖接口
        if (getIntent().getStringExtra("cdkeys")!=null) {
            commitResellPresenter.sellApply(bindToLifecycle(),getIntent().getStringExtra("cdkeys"),amountView.etAmount.getText().toString().trim() );
        }

}
    @Override
    public void isLoading() {

    }

    @Override
        public void loadSucceed(BaseReponse baseReponse) {
        //转卖完成
        if (baseReponse.isSuccess()){
            IntentLauncher.with(this).launchFinishCpresent(ResellFinshActivity.class);
        }

    }

    @Override
    public void loadFailure() {

    }
}
