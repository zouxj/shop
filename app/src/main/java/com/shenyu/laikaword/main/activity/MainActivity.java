package com.shenyu.laikaword.main.activity;

import android.app.Fragment;
import android.content.Context;
import android.content.res.Resources;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.util.TypedValue;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.githang.statusbar.StatusBarCompat;
import com.shenyu.laikaword.LaiKaApplication;
import com.shenyu.laikaword.R;
import com.shenyu.laikaword.base.LKWordBaseActivity;
import com.shenyu.laikaword.main.MainModule;
import com.shenyu.laikaword.main.fragment.LeftFragment;
import com.shenyu.laikaword.main.fragment.MainFragment;
import com.shenyu.laikaword.rxbus.EventType;
import com.shenyu.laikaword.rxbus.RxBus;
import com.shenyu.laikaword.rxbus.RxBusSubscriber;
import com.zxj.utilslibrary.utils.ActivityManageUtil;
import com.zxj.utilslibrary.utils.ToastUtil;
import com.zxj.utilslibrary.utils.UIUtil;

import java.lang.reflect.Field;

import javax.inject.Inject;

import butterknife.BindView;
import rx.functions.Action1;

/**
 * 首页Acitivity
 */
public class MainActivity extends LKWordBaseActivity {

    @BindView(R.id.left_drawer)
    FrameLayout frameLeft;
    @Inject
    FragmentTransaction fragmentTransaction;
    @Inject
    LeftFragment leftFragment;
    @Inject
    MainFragment mainFragment;
    @BindView(R.id.drawer_main)
    DrawerLayout drawerLayout;
    private long exitTime = 0;
    @Override
    public int bindLayout() {
        return R.layout.activity_main2;
    }

    @Override
    public void initView() {

////        ActionBarDrawerToggle actionBarDrawerToggle=new ActionBarDrawerToggle(this,drawerLayout,mToolbar,0,1);
//        actionBarDrawerToggle.syncState(); drawerLayout.setDrawerListener(actionBarDrawerToggle);

    }

    @Override
    public void doBusiness(Context context) {

       RxBus.getDefault().toObservable(EventType.class).subscribe(new Action1<EventType>() {
            @Override
            public void call(EventType eventType) {
                switch (eventType.action){
                    case EventType.ACTION_OPONE_LEFT:
                        drawerLayout.openDrawer(frameLeft);
                        break;
                }

            }
        });
        initFragment();

    }

    @Override
    public void setupActivityComponent() {
        LaiKaApplication.get(this).getAppComponent().plus(new MainModule(getSupportFragmentManager())).inject(this);
    }

    /**
     * 初始化Fragment
     */
    private void initFragment(){
        //添加MianFragment
        fragmentTransaction.replace(R.id.content_frame, mainFragment);
        //添加LeftFragment
        fragmentTransaction.replace(R.id.left_drawer, leftFragment);
//        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        RxBus.getDefault().removeAllStickyEvents();
    }

    @Override
    public void onBackPressed() {
        if (System.currentTimeMillis()-exitTime>2000){
            ToastUtil.showToastShort("在按一次退出应用");
            exitTime=System.currentTimeMillis();
        }else {
            ActivityManageUtil.getAppManager().finishAllActivity();
            System.exit(0);
        }
    }


}
