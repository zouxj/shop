package com.shenyu.laikaword.helper;

import android.annotation.SuppressLint;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.shenyu.laikaword.R;
import com.squareup.picasso.Picasso;
import com.zxj.utilslibrary.utils.UIUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/8/15 0015.
 */

public class BannerHelper {
    /** 帮助类实例 */
    private static volatile BannerHelper instance = new BannerHelper();
    /** 是否需要显示banner描述信息 */
    private static volatile boolean needShowDesc = false;
    /** banner视图根布局 */
    private View mBannerRootLayout;
    /** banner显示图片容器 */
    private ViewPager mBannerViewpager;
    /** banner指示器容器 */
    private LinearLayout mPointersLayout;
    /** banner条目描述文字 */
    private TextView mBannerDesc;
    /** banner默认加载进度条 */
    private ProgressBar mLoadingPb;
    /** banner内容数据集合 */
    private List<BannerBean> mBannerList = new ArrayList<>();
    /** banner上一个选中条目位置 */
    private int previousEnabledPosition = 0;
    /** banner轮播执行任务 */
    private LoopShowTask mLoopShowTask;
    /** banner被触摸按下标记 */
    private boolean mIsTouchDown;
    /** banner条目点击事件 */
    private OnItemClickListener mOnItemClickListener;

    private boolean isAutoPlay = false;

    private BannerHelper() {
    }

    public static BannerHelper getInstance() {
        return instance;
    }

    /**
     * 初始化banner静态视图
     *
     * @param bannerView banner根布局视图
     */
    public BannerHelper init(@NonNull View bannerView) {
        mBannerRootLayout = bannerView;
        mBannerViewpager = (ViewPager) mBannerRootLayout.findViewById(R.id.banner_viewpager);
        mPointersLayout = (LinearLayout) mBannerRootLayout.findViewById(R.id.banner_pointers);
        mBannerDesc = (TextView) mBannerRootLayout.findViewById(R.id.banner_desc);
        mLoadingPb = (ProgressBar) mBannerRootLayout.findViewById(R.id.banner_loading);
        mLoadingPb.setVisibility(View.VISIBLE);
        mBannerDesc.setVisibility(needShowDesc ? View.VISIBLE : View.GONE);
        return instance;
    }

    /**
     * 开启banner显示
     *
     * @param bannerList          banner内容数据集合
     * @param onItemClickListener banner条目点击事件
     */
    public void startBanner(List<BannerBean> bannerList, OnItemClickListener onItemClickListener) {
        if (bannerList == null || bannerList.size() == 0) {
            return;
        }
        mBannerList.clear();
        mBannerList.addAll(bannerList);
        mOnItemClickListener = onItemClickListener;

        mBannerViewpager.setAdapter(new BannerPicturePagerAdapter());
        mBannerViewpager.removeOnPageChangeListener(mBannerPageChangeListener);
        mBannerViewpager.addOnPageChangeListener(mBannerPageChangeListener);
        mBannerViewpager.setOnTouchListener(mBannerOnTouchListener);
        mBannerViewpager.setOffscreenPageLimit(bannerList.size());

        // 初始化banner的点指示器
        mPointersLayout.removeAllViews();
        for (int x = 0; x < bannerList.size(); x++) {
            View v = new View(mBannerRootLayout.getContext());
            v.setBackgroundResource(R.drawable.selector_pointers);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(35, 35);
            if (x != 0) {
                params.leftMargin = 10;
            }
            v.setLayoutParams(params);
            v.setEnabled(false);
            mPointersLayout.addView(v);
        }
        //开启轮播 , 只有一个点的时候不需要切换以及显示点指示器
        mLoadingPb.setVisibility(View.GONE);
        if (mPointersLayout.getChildCount() > 1) {
            mPointersLayout.setVisibility(View.VISIBLE);
            previousEnabledPosition = 0;
            mPointersLayout.getChildAt(previousEnabledPosition).setEnabled(true);
            if (needShowDesc) {
                CharSequence pageTitle = mBannerViewpager.getAdapter().getPageTitle(0);
                if (!TextUtils.isEmpty(pageTitle)) {
                    mBannerDesc.setText(pageTitle);
                }
            }
            if (mLoopShowTask == null) {
                mLoopShowTask = new LoopShowTask();
                mLoopShowTask.start();
            } else if (!mIsTouchDown) {
                mLoopShowTask.stop();
                mLoopShowTask.start();
            }
        } else {
            mPointersLayout.removeAllViews();
            mPointersLayout.setVisibility(View.GONE);
        }
    }

    /**
     * call in current activity  onResume()
     */
    public void onResume() {
        if (mPointersLayout.getChildCount() > 1) {
            if (mLoopShowTask == null) {
                mLoopShowTask = new LoopShowTask();
            }
            mLoopShowTask.start();
        }
    }

    /**
     * call in current activity  onPause()
     */
    public void onPause() {
        if (mPointersLayout.getChildCount() > 1 && mLoopShowTask != null) {
            mLoopShowTask.stop();
        }
    }


    /**
     * call in current activity  onDestroy()
     */
    public void onDestroy() {
        mLoopShowTask.stop();
        mLoopShowTask.removeCallbacksAndMessages(null);
        mLoopShowTask = null;
        mPointersLayout.removeAllViews();
        mBannerViewpager.removeOnPageChangeListener(mBannerPageChangeListener);
        mBannerViewpager.removeAllViews();
        mOnItemClickListener = null;
    }


    /** banner页面切换监听 */
    private ViewPager.OnPageChangeListener mBannerPageChangeListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrollStateChanged(int arg0) {
        }

        @Override
        public void onPageScrolled(int arg0, float arg1, int arg2) {
        }

        @Override
        public void onPageSelected(int position) {
            int newPosition = position % mPointersLayout.getChildCount();
            mPointersLayout.getChildAt(previousEnabledPosition).setEnabled(false);
            mPointersLayout.getChildAt(newPosition).setEnabled(true);
            if (needShowDesc) {
                CharSequence pageTitle = mBannerViewpager.getAdapter().getPageTitle(newPosition);
                if (!TextUtils.isEmpty(pageTitle)) {
                    mBannerDesc.setText(pageTitle);
                }
            }
            previousEnabledPosition = newPosition;
        }
    };

    /** 手势滑动 */
    private View.OnTouchListener mBannerOnTouchListener = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            if (mPointersLayout.getChildCount() <= 1) {//只有一张banner不需滑动
                return false;
            }
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    mIsTouchDown = true;
                    mLoopShowTask.stop();
                    break;
                case MotionEvent.ACTION_MOVE:
                    break;
                case MotionEvent.ACTION_UP:
                    mIsTouchDown = false;
                    mLoopShowTask.start();
                    break;
            }
            return false;
        }
    };


    /** 循环播放banner图片任务 */
    @SuppressLint("HandlerLeak")
    private class LoopShowTask extends Handler implements Runnable {
        @Override
        public void run() {
            int item = mBannerViewpager.getCurrentItem();
            mBannerViewpager.setCurrentItem((++item) % mBannerList.size(), true);
            postDelayed(mLoopShowTask, 3000);
        }

        public void start() {
            stop();
            if (isAutoPlay)
                mLoopShowTask.postDelayed(mLoopShowTask, 3000);


        }

        public void stop() {
            mLoopShowTask.removeCallbacks(mLoopShowTask);
        }
    }

    /** banner图片数据列表适配器 */
    class BannerPicturePagerAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            if (mBannerList.size() == 1) {
                return 1;
            } else {
                return Integer.MAX_VALUE;
            }
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }

        /**
         * 若无banner描述，可不用复写该方法
         */
        @Override
        public CharSequence getPageTitle(int position) {
            return mBannerList.get(position % mBannerList.size()).getDesc();
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            ImageView iv = new ImageView(mBannerRootLayout.getContext());
            iv.setScaleType(ImageView.ScaleType.CENTER_CROP);
            iv.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));

            BannerBean item = mBannerList.get(position % mBannerList.size());

            //TODO use img loader here to load net img
//            iv.setImageResource(item.getTestImgResId());
            Picasso
                    .with(UIUtil.getContext())
                    .load(item.getImgurl())
                    .resize(1080, 1920)
                    .centerCrop()
                    .into(iv);

            iv.setTag(R.id.banner_rootlayout, item);
            iv.setOnClickListener(mOnClickListener);
            container.addView(iv);
            return iv;
        }

        /** 条目点击事件 */
        private View.OnClickListener mOnClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BannerBean bean = (BannerBean) v.getTag(R.id.banner_rootlayout);
                if (bean != null && mOnItemClickListener != null) {
                    mOnItemClickListener.onItemClick(bean);
                }
            }
        };
    }

    /**
     * 条目点击回调监听
     */
    public interface OnItemClickListener {
        void onItemClick(BannerBean bean);
    }
    public void setIsAuto(boolean auto){
        this.isAutoPlay = auto;
    }

    /**
     * 设置Gravity
     * @param gravity
     */
    public void setmPointersLayout(int gravity){
        if (null!=mPointersLayout){
            mPointersLayout.setPadding((int) UIUtil.dp2px(240),0, (int) UIUtil.dp2px(15),(int) UIUtil.dp2px(15));
            mPointersLayout.setGravity(gravity);

    }
    }
}
