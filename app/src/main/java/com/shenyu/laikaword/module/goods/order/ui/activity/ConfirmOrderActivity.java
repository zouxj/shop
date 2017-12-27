package com.shenyu.laikaword.module.goods.order.ui.activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Build;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.shenyu.laikaword.helper.ImageUitls;
import com.shenyu.laikaword.module.launch.LaiKaApplication;
import com.shenyu.laikaword.R;
import com.shenyu.laikaword.model.adapter.CommonAdapter;
import com.shenyu.laikaword.model.holder.ViewHolder;
import com.shenyu.laikaword.base.LKWordBaseActivity;
import com.shenyu.laikaword.model.bean.reponse.GoodBean;
import com.shenyu.laikaword.model.bean.reponse.LoginReponse;
import com.shenyu.laikaword.common.Constants;
import com.shenyu.laikaword.helper.RecycleViewDivider;
import com.shenyu.laikaword.module.goods.order.view.ConfirmOrderView;
import com.shenyu.laikaword.di.module.ShopModule;
import com.shenyu.laikaword.model.rxjava.rxbus.RxBus;
import com.shenyu.laikaword.model.rxjava.rxbus.RxBusSubscriber;
import com.shenyu.laikaword.model.rxjava.rxbus.RxSubscriptions;
import com.shenyu.laikaword.model.rxjava.rxbus.event.Event;
import com.shenyu.laikaword.model.rxjava.rxbus.event.EventType;
import com.shenyu.laikaword.module.goods.order.presenter.ConfirmOrderPresenter;
import com.shenyu.laikaword.ui.view.widget.AmountView;
import com.zxj.utilslibrary.utils.LogUtil;
import com.zxj.utilslibrary.utils.StringUtil;
import com.zxj.utilslibrary.utils.ToastUtil;
import com.zxj.utilslibrary.utils.UIUtil;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;
import rx.android.schedulers.AndroidSchedulers;

/**
 * 确认订单Activity
 */
public class ConfirmOrderActivity extends LKWordBaseActivity implements ConfirmOrderView {

    @BindView(R.id.av_zj)
    AmountView mAmountView;
    @BindView(R.id.rl_pay_type)
    RecyclerView recyclerView;
    @Inject
    RecycleViewDivider recycleViewDivider;
    @Inject
    LinearLayoutManager linearLayoutManager;
    @BindView(R.id.imge_head)
    ImageView imgeHead;
    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.tv_main_shop_price)
    TextView tvMainShopPrice;
    @BindView(R.id.tv_main_shop_original_price)
    TextView tvMainShopOriginalPrice;
    @BindView(R.id.tv_main_shop_purchase)
    TextView tvMainShopPurchase;
    @BindView(R.id.img_order)
    ImageView imgOrder;
    @BindView(R.id.tv_shop_name)
    TextView tvShopName;
    @BindView(R.id.tv_zhegou)
    TextView tvZhegou;
    @BindView(R.id.tv_price_count)
    TextView priceCount;
    private String moneyD;
    private String payType;//支付类型
    private int payFlogType = 5;
    private int count = 1;//购买数量
    @Inject
    ConfirmOrderPresenter mConfirmOrderPresenter;
    GoodBean mGoodBean;
    CommonAdapter commonAdapter;
    @Override
    public int bindLayout() {
        return R.layout.activity_confirm_order;

    }

    @Override
    public void initView() {
        setToolBarTitle("确认订单");

        mAmountView.setOnAmountChangeListener(new AmountView.OnAmountChangeListener() {
            @Override
            public void onAmountChange(View view, int amount) {
                //根据数量改变价格
                if (null!=tvMainShopPrice.getText().toString().trim()) {
                    if (amount>StringUtil.formatIntger(mGoodBean.getStock())){
                        ToastUtil.showToastShort("库存不足");
                    }
                    count = amount;
                    Double count = Double.parseDouble(mGoodBean.getDiscountPrice());
                    moneyD=StringUtil.m2(count*amount);
                    priceCount.setText("￥"+moneyD);
                }
            }
        });
        List<String> typeData = new ArrayList<>();
        typeData.add("账户余额");
        typeData.add("支付宝");
//        typeData.add("QQ钱包");
        typeData.add("微信支付");
        final int[] payIconList = {R.mipmap.pay_yue_icon, R.mipmap.pay_zhifubao_icon, R.mipmap.pay_qq_icon, R.mipmap.pay_wechat_icon};
        recyclerView.addItemDecoration(recycleViewDivider);
        linearLayoutManager.setAutoMeasureEnabled(true);
        recyclerView.setLayoutManager(linearLayoutManager);
         commonAdapter=  new CommonAdapter<String>(R.layout.item_pay_type, typeData) {
            int selectedPosition = 0;

            @SuppressLint("NewApi")
            @Override
            protected void convert(final ViewHolder holder, String strings, final int position) {
                if (strings.equals("账户余额")) {
                    TextView textView = holder.getView(R.id.tv_pay_type);
                    textView.setText(Html.fromHtml(strings + " :<font color='#e03226'><big> ￥"+userAount + "</big></font>"));

                } else {
                    holder.setText(R.id.tv_pay_type, strings);
                }

                holder.setBackground(R.id.iv_pay, payIconList[position]);
                CheckBox checkBox = holder.getView(R.id.cb_pay_type);
                checkBox.setChecked((position == selectedPosition ? true : false));
                holder.setOnClickListener(R.id.item_ck, new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (selectedPosition != position) {
                            //先取消上个item的勾选状态
                            if (selectedPosition != -1) {
                                holder.itemView.setSelected(false);
                                notifyItemChanged(selectedPosition);
                                payType = "";
                            }
                            //设置新Item的勾选状态
                            selectedPosition = position;
                            holder.itemView.setSelected(true);
                            notifyItemChanged(selectedPosition);
                            switch (position) {
                                case 0:
                                    payType = "账户余额";
                                    payFlogType = 5;
                                    break;
                                case 1:
                                    payType = "支付宝";
                                    payFlogType = 3;
                                    break;
                                case 2:
                                    payType = "QQ钱包";
                                    payFlogType = 2;
                                    break;
                                case 3:
                                    payType = "微信支付";
                                    payFlogType = 1;
                                    break;
                            }

                        } else if (selectedPosition == position) {
                            selectedPosition = -1; //选择的position赋值给参数，
                            holder.itemView.setSelected(false);
                            notifyItemChanged(position);//刷新当前点击item
                            payType = "";
                        }
                    }
                });
            }
        };
        recyclerView.setAdapter(commonAdapter);
    }

    @OnClick({R.id.tv_to_pay})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_to_pay:
                //TODO 去支付
                mConfirmOrderPresenter.cofirmPay(this.bindToLifecycle(),payFlogType,count,moneyD);
                break;
        }
    }

    @SuppressLint("NewApi")
    @Override
    public void doBusiness(Context context) {
        mConfirmOrderPresenter.initData();
        subscribeEvent();

    }

    @Override
    public void setupActivityComponent() {
        LaiKaApplication.get(this).getAppComponent().plus(new ShopModule(this, this)).inject(this);
    }

    @Override
    public void isLoading() {

    }

    @Override
    public void dataCountChanged(int count) {

    }

    @Override
    public void loadFinished() {

    }

    @Override
    public void loadFailure() {

    }

    @Override
    public void paySuccess() {

    }

    @Override
    public void payFaire() {


    }
    private String userAount;
    @SuppressLint("NewApi")
    @Override
    public void showData(LoginReponse loginReponse, GoodBean goodBean) {
        this.mGoodBean = goodBean;
        if (null != loginReponse && loginReponse.getPayload() != null) {
            ImageUitls.loadImgRound(mGoodBean.getSellerAvatar(),imgeHead);
            tvName.setText(mGoodBean.getNickName());
            userAount = loginReponse.getPayload().getMoney();
        } else {
            imgeHead.setImageBitmap(null);
            if (Build.VERSION.SDK_INT> Build.VERSION_CODES.ICE_CREAM_SANDWICH_MR1)
                imgeHead.setBackground(UIUtil.getDrawable(R.mipmap.left_user_icon));
            else
                imgeHead.setBackgroundResource(R.mipmap.left_user_icon);
            tvName.setText("");
        }
        if (goodBean != null) {
            ImageUitls.loadImg(goodBean.getGoodsImage(),imgOrder);
            StringBuilder sb = new StringBuilder(goodBean.getDiscount());//构造一个StringBuilder对象
            sb.insert(1, ".");//在指定的
            tvZhegou.setText(sb+ "折");
            tvShopName.setText(goodBean.getGoodsName());
            tvMainShopPrice.setText("￥"+goodBean.getDiscountPrice());
            tvMainShopPurchase.setText("剩余数量：" + goodBean.getStock()+"张");
            if (StringUtil.validText(goodBean.getDiscountPrice()))
                moneyD=StringUtil.m2(count * (StringUtil.formatDouble(goodBean.getDiscountPrice())));
                priceCount.setText("￥"+moneyD);
            if (StringUtil.validText(goodBean.getStock()))
                mAmountView.setGoods_storage(StringUtil.formatIntger(goodBean.getStock())>5?5:StringUtil.formatIntger(goodBean.getStock()));
        }
    }
    private void subscribeEvent() {
        RxSubscriptions.remove(mRxSub);
        mRxSub = RxBus.getDefault().toObservable(Event.class)
                .observeOn(AndroidSchedulers.mainThread()).subscribe(new RxBusSubscriber<Event>() {
                    @Override
                    protected void onEvent(Event myEvent) {
                        switch (myEvent.event) {
                            case EventType.ACTION_UPDATA_USER:
                                LoginReponse loginReponse= Constants.getLoginReponse();
                                userAount=loginReponse.getPayload().getMoney();
                                commonAdapter.notifyItemChanged(0);
                                break;
                        }
//            }
                    }
                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                        LogUtil.e(TAG, "onError");
                        /**
                         * 这里注意: 一旦订阅过程中发生异常,走到onError,则代表此次订阅事件完成,后续将收不到onNext()事件,
                         * 即 接受不到后续的任何事件,实际环境中,我们需要在onError里 重新订阅事件!
                         */
                        subscribeEvent();
                    }
                });
        RxSubscriptions.add(mRxSub);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        RxSubscriptions.remove(mRxSub);
        mConfirmOrderPresenter.detachView();
    }
}
