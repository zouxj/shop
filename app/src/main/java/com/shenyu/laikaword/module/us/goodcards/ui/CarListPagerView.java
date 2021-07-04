package com.shenyu.laikaword.module.us.goodcards.ui;

import
android.app.Activity;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.shenyu.laikaword.R;
import com.shenyu.laikaword.helper.ImageUitls;
import com.shenyu.laikaword.model.adapter.CommonAdapter;
import com.shenyu.laikaword.model.holder.ViewHolder;
import com.shenyu.laikaword.model.wrapper.EmptyWrapper;
import com.shenyu.laikaword.base.BaseViewPager;
import com.shenyu.laikaword.model.bean.reponse.CarPagerReponse;
import com.shenyu.laikaword.helper.RecycleViewDivider;
import com.shenyu.laikaword.module.goods.pickupgoods.ui.activity.PickUpActivity;
import com.shenyu.laikaword.module.goods.pickupgoods.ui.activity.PickUpTelActivity;
import com.zxj.utilslibrary.utils.IntentLauncher;
import com.zxj.utilslibrary.utils.ToastUtil;
import com.zxj.utilslibrary.utils.UIUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by shenyu_zxjCode on 2017/10/16 0016.
 */

public class CarListPagerView extends BaseViewPager<CarPagerReponse> {
    RecyclerView recyclerView;
    private List<CarPagerReponse.PayloadBean.ListBean> beanList;
    public CarListPagerView(Activity activity) {
        super(activity);
    }
    private String type;


    @Override
    public View initView() {
        View view = UIUtil.inflate(R.layout.fragment_car_list);
        recyclerView = view.findViewById(R.id.rlv_view);
        return view;
    }
    @Override
    public void initData(CarPagerReponse carPagerReponse,String type) {
        beanList = new ArrayList<>();
        this.type=type;
        if (carPagerReponse!=null) {
            for (int i=0;i<carPagerReponse.getPayload().size();i++){
                if (type.equals(carPagerReponse.getPayload().get(i).getName())){
                    beanList.clear();
                    beanList.addAll(carPagerReponse.getPayload().get(i).getList());
                    break;
                }else {
                    for (CarPagerReponse.PayloadBean.ListBean listBean:carPagerReponse.getPayload().get(i).getList()) {
                        beanList.add(listBean);
                    }
                }
            }
        }
        setData(beanList);
    }

    public void setData(List<CarPagerReponse.PayloadBean.ListBean> beanList){
        CommonAdapter commonAdapter=    new CommonAdapter<CarPagerReponse.PayloadBean.ListBean>(R.layout.item_carpackage,beanList) {
            @Override
            protected void convert(ViewHolder holder, final CarPagerReponse.PayloadBean.ListBean bean, int position) {
                holder.setText(R.id.tv_kpage_count,"数量："+bean.getQuantity());
                holder.setText(R.id.tv_page_name,bean.getGoodsName());
                ImageUitls.loadImg(bean.getGoodsImage(),(ImageView) holder.getView(R.id.iv_page_img));
                holder.setOnClickListener(R.id.tv_tihuo, new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        //TODO 手机号和京东卡提货
                        if (bean.getPickupMethodId().equals("2"))
                            IntentLauncher.with(mActivity).putObjectString("bean",bean).launch(PickUpActivity.class);
                        else
                            IntentLauncher.with(mActivity).putObjectString("bean",bean).launch(PickUpTelActivity.class);
                    }
                });
            }
        };
        recyclerView.addItemDecoration(new RecycleViewDivider(mActivity, LinearLayoutManager.HORIZONTAL,(int) UIUtil.dp2px(5),UIUtil.getColor(R.color.main_bg_gray)));
        recyclerView.setLayoutManager(new LinearLayoutManager(mActivity));
        EmptyWrapper emptyWrapper = new EmptyWrapper(commonAdapter);
        emptyWrapper.setEmptyView(R.layout.empty_view,UIUtil.getString(R.string.pager_empty));
        recyclerView.setAdapter(emptyWrapper);
    }
}
