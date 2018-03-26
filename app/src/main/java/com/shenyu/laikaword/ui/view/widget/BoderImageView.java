package com.shenyu.laikaword.ui.view.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.ImageView;

import com.shenyu.laikaword.R;
import com.zxj.utilslibrary.utils.UIUtil;


@SuppressLint("AppCompatCustomView")
public class BoderImageView extends ImageView {
    private Paint mPaint = new Paint();
    private int mHeight;
    private int mWidth;
    private RectF border;
    private int borderWidth = 6;
    public BoderImageView(Context context) {
        super(context);
    }

    public BoderImageView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public BoderImageView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        this.mWidth = w;
        this.mHeight = h;
        border = new RectF(borderWidth/2, borderWidth/2, mWidth-borderWidth/2, mHeight-borderWidth/2);
    }



    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        // 第一步：绘制边框
//        canvas.drawCircle(100,100,50,mPaint);


        mPaint.setColor(UIUtil.getColor(R.color.color_ddd));
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setAntiAlias(true);
        mPaint.setStrokeWidth(UIUtil.dp2px(1f));
        canvas.drawRoundRect(getBorderLineRectF(canvas),UIUtil.dp2px(borderWidth),UIUtil.dp2px(borderWidth),mPaint);
    }
    private RectF getBorderLineRectF(Canvas canvas){
        //通过画布得到ImageView所占用的矩形区域
        Rect borderRect = canvas.getClipBounds();
        //设置边框的宽度
        borderRect.left++;
        borderRect.top++;
        borderRect.right--;
        borderRect.bottom--;
        //创建圆角矩形
        RectF borderRectF = new RectF(borderRect);
        return borderRectF;
    }
}
