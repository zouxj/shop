package com.shenyu.laikaword.module.home.ui.fragment;


import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.shenyu.laikaword.R;
import com.shenyu.laikaword.helper.GridSpacingItemDecoration;
import com.shenyu.laikaword.helper.StatusBarManager;
import com.shenyu.laikaword.model.adapter.MultiItemTypeAdapter;
import com.shenyu.laikaword.base.IKWordBaseFragment;
import com.shenyu.laikaword.model.bean.reponse.LoginReponse;
import com.shenyu.laikaword.common.Constants;
import com.shenyu.laikaword.model.bean.reponse.ShopMainReponse;
import com.shenyu.laikaword.module.us.appsetting.SettingSystemActivity;
import com.shenyu.laikaword.module.us.appsetting.UserInfoActivity;
import com.zxj.utilslibrary.utils.IntentLauncher;
import com.zxj.utilslibrary.utils.UIUtil;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import butterknife.BindView;



public class LeftFragment extends IKWordBaseFragment {

    //    @BindView(R.id.tv_user_name)
//    TextView tvUserName;
//    @BindView(R.id.tv_user_head)
//    ImageView tvUserHead;
    @BindView(R.id.rc_left_view)
    RecyclerView rcLeftView;
    //    @BindView(R.id.ly_user_head)
//    RelativeLayout leftLayout;
    List<ShopMainReponse.EntranceListBean> dataList = new ArrayList<>();
    MultiItemTypeAdapter<ShopMainReponse.EntranceListBean> commonAdapter;
    public static final int[] leftData = {
            R.mipmap.left_money_icon, R.mipmap.left_gouwuchei_icon,
            R.mipmap.left_tihuo_icon, R.mipmap.left_wodekabao_icon,
            R.mipmap.left_yinhangka_icon, R.mipmap.left_dizh_icon, R.mipmap.lfet_setting_icon, R.mipmap.kefu_icon, R.mipmap.zhuanmai};


    @Override
    public int bindLayout() {
        return R.layout.fragment_left;
    }

    @Override
    public void initView(View view) {
//        StatusBarManager statusBarManager = new StatusBarManager(getActivity(), UIUtil.getColor(R.color.app_theme_red));
//        int statusBarHeight=  statusBarManager.getStatusBarHeight();
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT&&Build.VERSION.SDK_INT <Build.VERSION_CODES.LOLLIPOP) {
//            LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) leftLayout.getLayoutParams();
//            params.topMargin=statusBarHeight;
//            leftLayout.setLayoutParams(params);
//        }
//        rcLeftView.setLayoutManager(new LinearLayoutManager(getActivity()));
//        commonAdapter=  new MultiItemTypeAdapter(dataList) ;
//        commonAdapter.addItemViewDelegate(new HomeLeftItemViewDelegate(getActivity()));
//        commonAdapter.addItemViewDelegate(new HomeLeftLastItemViewDelegate(getActivity()));
//        rcLeftView.setAdapter(commonAdapter);
        List<String> dataList = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            int x = new Random().nextInt(350) + 100;
            dataList.add(x + "");
        }
        rcLeftView.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
        rcLeftView.addItemDecoration(new GridSpacingItemDecoration(2, (int) UIUtil.dp2px(10), true,0));
        UserAdapter mUserAdapter = new UserAdapter(R.layout.item_new_goods, dataList);
        View headView = LayoutInflater.from(requireActivity()).inflate(R.layout.head_user_fragment, null, false);
        mUserAdapter.addHeaderView(headView);
        rcLeftView.setAdapter(mUserAdapter);
    }

//    @OnClick({R.id.ly_user_head, R.id.iv_seting})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ly_user_head:
                LoginReponse loginReponse = Constants.getLoginReponse();
                if (null != loginReponse) {

                }
                IntentLauncher.with(getActivity()).launch(UserInfoActivity.class);
//                else{
//                    IntentLauncher.with(getActivity()).launch(LoginActivity.class);
//                }

                break;
            case R.id.iv_seting:
                IntentLauncher.with(getActivity()).launch(SettingSystemActivity.class);

//                if (null!=  Constants.getLoginReponse()) {
//                    IntentLauncher.with(getActivity()).launch(SettingSystemActivity.class);
//                }else{
//                    IntentLauncher.with(getActivity()).launch(LoginActivity.class);
//                }
                break;
        }

    }
//    private void subscribeEvent() {
//        RxSubscriptions.remove(mRxSub);
//        mRxSub = RxBus.getDefault().toObservable(Event.class)
//                .observeOn(AndroidSchedulers.mainThread()).subscribe(new RxBusSubscriber<Event>() {
//                    @SuppressLint("NewApi")
//                    @Override
//                    protected void onEvent(Event myEvent) {
//                        switch (myEvent.event) {
//                            case EventType.ACTION_UPDATA_USER:
//                                LoginReponse loginReponse = Constants.getLoginReponse();
//                                if (null!=loginReponse) {
//                                    ImageUitls.loadImgRound(loginReponse.getPayload().getAvatar(),tvUserHead);
//                                    tvUserName.setText(loginReponse.getPayload().getNickname());
//                                }else{
//                                    tvUserName.setText("未登录");
//                                    tvUserHead.setImageBitmap(null);
//                                    if (Build.VERSION.SDK_INT> Build.VERSION_CODES.ICE_CREAM_SANDWICH_MR1)
//                                        tvUserHead.setBackground(UIUtil.getDrawable(R.mipmap.left_user_icon));
//                                    else
//                                        tvUserHead.setBackgroundResource(R.mipmap.left_user_icon);
//                                }
//                                break;
//                            case EventType.ACTION_LFET_DATA:
//                                initLeftData(( List<ShopMainReponse.EntranceListBean> )myEvent.object);
//                                break;
//                        }
////            }
//                    }
//                    @Override
//                    public void onError(Throwable e) {
//                        super.onError(e);
//                        LogUtil.e(TAG, "onError");
//                        /**
//                         * 这里注意: 一旦订阅过程中发生异常,走到onError,则代表此次订阅事件完成,后续将收不到onNext()事件,
//                         * 即 接受不到后续的任何事件,实际环境中,我们需要在onError里 重新订阅事件!
//                         */
//                        subscribeEvent();
//                    }
//                });
//        RxSubscriptions.add(mRxSub);
//    }

    @Override
    public void doBusiness() {
//        subscribeEvent();
//        initLeftData(null);

    }

    @Override
    public void setupFragmentComponent() {

    }

    @Override
    public void requestData() {
//        LoginReponse loginReponse = Constants.getLoginReponse();
//        if (null!=loginReponse&&loginReponse.getPayload()!=null){
//            ImageUitls.loadImgRound(loginReponse.getPayload().getAvatar(),tvUserHead);
//             tvUserName.setText(loginReponse.getPayload().getNickname());
//        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    //    public void  initLeftData(List<ShopMainReponse.EntranceListBean> object){
//        dataList.clear();
//        final ShopMainReponse shopMainReponse= (ShopMainReponse) SPUtil.readObject(Constants.MAIN_SHOP_KEY);
//        dataList.add(new ShopMainReponse.EntranceListBean("我的余额",leftData[0],null,null,false));
//        dataList.add(new ShopMainReponse.EntranceListBean("我的订单",leftData[1],null,null,false));
//        String flog = "0";
//        if (null!=shopMainReponse)
//                flog=shopMainReponse.getPayload().getFlag().getnewExtractFlag();
//        dataList.add(new ShopMainReponse.EntranceListBean("我的商品",leftData[3],null,null,   flog.equals("1")));
//        dataList.add(new ShopMainReponse.EntranceListBean("我的提货",leftData[2],null,null,false));
////       //判断专卖显示不显示
////        if (null!=shopMainReponse)
////            if (shopMainReponse.getPayload().getResellShow()!=null)
////            flog=shopMainReponse.getPayload().getResellShow();
////        if (flog.equals("1"))
//        dataList.add(new ShopMainReponse.EntranceListBean("我的转卖",leftData[8],null,null,false));
////        dataList.add(new ShopMainReponse.EntranceListBean("银行卡",leftData[4],null,null,false));
////        dataList.add(new ShopMainReponse.EntranceListBean("我的地址",leftData[5],null,null,false));
//        dataList.add(new ShopMainReponse.EntranceListBean("联系客服",leftData[7],null,null,false));
//       if (null!=object) {
//           for (ShopMainReponse.EntranceListBean listBean : object) {
//               dataList.add(listBean);
//           }
//       }
//        dataList.add(new ShopMainReponse.EntranceListBean("系统设置",leftData[6],null,null,false));
//        commonAdapter.notifyDataSetChanged();
//    }
    class UserAdapter extends BaseQuickAdapter<String, BaseViewHolder> {

        public UserAdapter(int layoutResId, @Nullable List<String> data) {
            super(layoutResId, data);
        }

        @Override
        protected void convert(BaseViewHolder helper, String item) {
            TextView bgImage = helper.getView(R.id.iv_goumai_img);
            ViewGroup.LayoutParams layoutParams = bgImage.getLayoutParams();
            layoutParams.height = Integer.parseInt(item);
            bgImage.setLayoutParams(layoutParams);
        }
    }
}
