package com.shenyu.laikaword.ui.view.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.ViewGroup;

import androidx.appcompat.widget.TintTypedArray;

import com.shenyu.laikaword.R;
import com.zxj.utilslibrary.utils.UIUtil;

/**
 * Created by Administrator on 2017/8/21 0021.
 */

public class IdentityImageView extends ViewGroup {
    private Context mContext;
    private CircleImageView bigImageView;//大圆
    private CircleView smallImageView;//小圆
    private float radiusScale;//小图片与大图片的比例，默认0.28，刚刚好，大了不好看
    int radius;//大图片半径
    private int smallRadius;//小图片半径
    private double angle = 90; //标识角度大小
    private int borderWidth;//边框、进度条宽度
    private boolean hintSmallView;//标识符是否隐藏
    private Drawable bigImage;//大图片
    private int totalwidth;

    public IdentityImageView(Context context) {
        this(context, null);
    }

    public IdentityImageView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public IdentityImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        setWillNotDraw(false);//是的ondraw方法被执行
        addThreeView();
        initAttrs(attrs);

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int viewWidthMode = MeasureSpec.getMode(widthMeasureSpec);
        int viewHeightMode = MeasureSpec.getMode(heightMeasureSpec);
        int viewWidht = MeasureSpec.getSize(widthMeasureSpec);
        int viewHeight = MeasureSpec.getSize(heightMeasureSpec);
        switch (viewWidthMode) {
            case MeasureSpec.EXACTLY:   //说明在布局文件中使用的是具体值：100dp或者match_parent
                //为了方便，让半径等于宽高中小的那个，再设置宽高至半径大小
                totalwidth = viewWidht < viewHeight ? viewWidht : viewHeight;
                float scale = 1 + radiusScale;
                int radius2 = totalwidth / 2;
                radius = (int) (radius2 / scale);
                break;
            case MeasureSpec.AT_MOST:  //说明在布局文件中使用的是wrap_content:
                //这时我们也写死宽高
                radius = 200;
                totalwidth = (int) ((radius + radius * radiusScale) * 2);
                break;
            default:
                radius = 200;
                totalwidth = (int) ((radius + radius * radiusScale) * 2);
                break;
        }
        setMeasuredDimension(totalwidth, totalwidth);
        adjustThreeView();
    }





    @Override
    protected void onLayout(boolean b, int i, int i1, int i2, int i3) {
        //重点在于smallImageView的位置确定,默认为放在右下角，可自行拓展至其他位置
        double cos = Math.cos(angle * Math.PI / 180);
        double sin = Math.sin(angle * Math.PI / 180);
        double left = totalwidth / 2 + (radius * cos - smallRadius);
        double top = totalwidth / 2 + (radius * sin - smallRadius);
        int right = (int) (left + 2 * smallRadius);
        int bottom = (int) (top + 2 * smallRadius);
        bigImageView.layout(smallRadius + borderWidth / 2, smallRadius + borderWidth / 2, totalwidth - smallRadius - borderWidth / 2, totalwidth - smallRadius - borderWidth / 2);
        smallImageView.layout((int) left, (int) top, right, bottom);

    }

    private void adjustThreeView() {
        bigImageView.setLayoutParams(new LayoutParams(radius, radius));
        smallRadius = (int) (radius * radiusScale);
        smallImageView.setLayoutParams(new LayoutParams(smallRadius, smallRadius));
    }

    private void addThreeView() {

        bigImageView = new CircleImageView(mContext);//大圆
        bigImageView.setBorderWidth(2);
        bigImageView.setBorderColor(UIUtil.getColor(R.color.bisque));
        smallImageView = new CircleView(mContext);//小圆
        smallImageView.setTextC("1");
        addView(bigImageView, 0, new LayoutParams(radius, radius));
        smallRadius = (int) (radius * radiusScale);
        addView(smallImageView, 1, new LayoutParams(smallRadius, smallRadius));
        smallImageView.bringToFront();//使小图片位于最上层


    }


    @SuppressLint("RestrictedApi")
    private void initAttrs(AttributeSet attrs) {
        @SuppressLint("RestrictedApi") TintTypedArray tta = TintTypedArray.obtainStyledAttributes(getContext(), attrs,
                R.styleable.IdentityImageView);
        bigImage = tta.getDrawable(R.styleable.IdentityImageView_iciv_bigimage);
        angle = tta.getFloat(R.styleable.IdentityImageView_iciv_angle, 90);//小图以及进度条起始角度
        radiusScale = tta.getFloat(R.styleable.IdentityImageView_iciv_radiusscale, 0.28f);//大图和小图的比例
        borderWidth = tta.getInteger(R.styleable.IdentityImageView_iciv_border_width, 0);//边框宽（，同为进度条）
        hintSmallView = tta.getBoolean(R.styleable.IdentityImageView_iciv_hint_smallimageview, false);//隐藏小图片
        if (hintSmallView) {
            smallImageView.setVisibility(GONE);
        }
        if (bigImage != null) {
            bigImageView.setImageDrawable(bigImage);
        }

    }

    /**
     * 获得大图片
     *
     * @return bigImageView
     */
    public CircleImageView getBigCircleImageView() {
        if (bigImageView != null) return bigImageView;
        else return null;
    }

}
