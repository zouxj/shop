package com.shenyu.laikaword.module.us.resell.ui.activity;

import android.content.Context;
import android.text.Html;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.TextView;

import com.shenyu.laikaword.R;
import com.shenyu.laikaword.base.LKWordBaseActivity;
import com.shenyu.laikaword.helper.ImageUitls;
import com.shenyu.laikaword.model.bean.reponse.ResellParticularsReponse;
import com.shenyu.laikaword.model.net.api.ApiCallback;
import com.shenyu.laikaword.model.net.retrofit.RetrofitUtils;
import com.zxj.utilslibrary.utils.DateTimeUtil;
import com.zxj.utilslibrary.utils.StringUtil;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;

/**
 * 转卖详情
 */
public class ResellDetailsActivity extends LKWordBaseActivity {

    @BindView(R.id.expand_ly)
    ExpandableListView expandableListView;

    @BindView(R.id.img_purchase_img)
    ImageView imgPurchaseImg;
    @BindView(R.id.tv_zhuanmai_shop_name)
    TextView tvZhuanmaiShopName;
    @BindView(R.id.tv_zhuamai_price)
    TextView tvZhuamaiPrice;
    @BindView(R.id.tv_zhuanmai_count)
    TextView tvZhuanmaiCount;
    @BindView(R.id.tv_zhuanmai_time)
    TextView tvZhuanmaiTime;
    Map<String, List<String>> map = new LinkedHashMap<>();
    @Override
    public int bindLayout() {
        return R.layout.activity_resell_details;
    }

    @Override
    public void doBusiness(Context context) {
        String goodID = getIntent().getStringExtra("goodId");
        final String  type= getIntent().getStringExtra("type");
        retrofitUtils.addSubscription(RetrofitUtils.apiStores.resellDetail(goodID), new ApiCallback<ResellParticularsReponse>() {
            @Override
            public void onSuccess(ResellParticularsReponse model) {
                if (model.isSuccess()) {
                    if (model.getPayload() != null) ;
                    ImageUitls.loadImg(model.getPayload().getGoodsImage(),imgPurchaseImg);
                    tvZhuanmaiShopName.setText(model.getPayload().getGoodsName());
                    tvZhuamaiPrice.setText(Html.fromHtml("<font color= '#999999'>转卖单价:</font>"+model.getPayload().getDiscountPrice()+"元"));
                    tvZhuanmaiCount.setText(Html.fromHtml("<font color= '#999999'>转卖总数：</font>"+model.getPayload().getOriStock()));
                    tvZhuanmaiTime.setText( DateTimeUtil.formatDate(StringUtil.formatLong(model.getPayload().getCreateTime()),"yyyy-MM-dd HH:mm:ss"));
                    map.clear();
                    map.put("转卖总数:" + model.getPayload().getAllCodeList().size(), model.getPayload().getAllCodeList());
                    if (type.equals("2"))
                        map.put("转卖状态: 转卖完成!" , new ArrayList<String>());
                    else
                     map.put("已转卖:" + model.getPayload().getSoldCodeList().size(), model.getPayload().getSoldCodeList());
                    adapter = new com.shenyu.laikaword.model.adapter.ExpandableListAdapter(map,type);
                    expandableListView.setAdapter(adapter);
                }

            }

            @Override
            public void onFailure(String msg) {

            }

            @Override
            public void onFinish() {

            }
        });
    }
    ExpandableListAdapter adapter;
    @Override
    public void initView() {
        setToolBarTitle("转卖详情");
        expandableListView.setGroupIndicator(null);
        expandableListView.setChildIndicator(null);


    }

    @Override
    public void setupActivityComponent() {

    }
}
