package com.zxj.utilslibrary.widget.countdownview.step;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;

import com.zxj.utilslibrary.R;
import com.zxj.utilslibrary.utils.LogUtil;
import com.zxj.utilslibrary.utils.UIUtil;

/**
 * Created by shenyu_zxjCode on 2017/9/28 0028.
 */

public class StepBar extends View {

    public static final String TAG="StepBar";

    /**未完成的步骤的颜色*/

    public static final int COLOR_BAR_UNDONE=UIUtil.getColor(R.color.lightgray);

    /**完成步骤的颜色*/

    public static final int COLOR_BAR_DONE=UIUtil.getColor(R.color.app_theme_red);

    /**默认线条高度*/

    public static final int DEFAULT_LINE_HEIGHT = (int) UIUtil.dp2px(2);

    /**默认小圆的半径*/

    public static final int DEFAULT_SMALL_CIRCLE_RADIUS=(int) UIUtil.dp2px(15);

    /**默认大圆的半径*/

    public static final int DEFAULT_LARGE_CIRCLE_RADIUS=20;

    /**默认距离边缘的距离*/

    public static final int DEFAULT_PADDING=20;



    private float mCenterY=0.0f;

    private float mLeftX=0.0f;

    private float mLeftY=0.0f;

    private float mRightX=0.0f;

    private float mRightY=0.0f;

    private float mDistance=0.0f;





    private float mLineHeight=0f;

    private float mSmallRadius=0f;

    private float mLargeRadius=0f;

    private int mUnDoneColor=COLOR_BAR_UNDONE;

    private int mDoneColor=COLOR_BAR_DONE;

    private int mTotalStep;

    private int mCompleteStep;



    public StepBar(Context context) {

        super(context);

        init(context,null,0);

    }



    public StepBar(Context context, AttributeSet attrs) {

        super(context, attrs);

        init(context, attrs, 0);

    }



    public StepBar(Context context, AttributeSet attrs, int defStyleAttr) {

        super(context, attrs, defStyleAttr);

        init(context, attrs, defStyleAttr);

    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)

    public StepBar(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {

        super(context, attrs, defStyleAttr, defStyleRes);

        init(context, attrs, defStyleAttr);

    }



    /**

     * 用来处理自定义属性

     * 注意：此方法中最好不要涉及计算UI相关尺寸的逻辑，如果一定要在这里计算，那么务必使用{@link #post(Runnable)}

     * @param mContext

     * @param attrs

     * @param defStyeAttr

     */

    private void init(Context mContext,AttributeSet attrs,int defStyeAttr){





    }



    /**

     * 设置总的步骤数

     * @param mTotalStep

     */

    public void setTotalStep(int mTotalStep){

        if(mTotalStep<=0){

            throw new IllegalArgumentException("步骤总数必须大于0!");

        }

        this.mTotalStep=mTotalStep;

    }



    /**

     * 获取步骤总数

     * @return

     */

    public int getTotalStep(){

        return mTotalStep;

    }



    /**

     * 设置完成的步骤

     * @param mComplteStep

     *          步骤从1开始,如果设置为0,那么没有任何完成的步骤

     */

    public void setCompleteStep(int mComplteStep){

        if(mComplteStep<0 || mComplteStep>mTotalStep){

            return;

        }

        this.mCompleteStep=mComplteStep;

    }



    /**

     * 通过步骤序号，得到此步骤对应的点的位置,主要用于确定标题的位置

     * @param step

     * @return

     */

    public float getPositionByStep(int step){

        if(step<1 || step> mTotalStep){

            throw new IllegalArgumentException("step必须在 1~总步骤数之间!");

        }

        return mLeftX+(step-1)*mDistance;

    }



    /**

     * 进入下一个步骤

     * 如果已经是最后一个步骤,则不做任何处理

     */

    public void nextStep(){

        if(mCompleteStep==mTotalStep){

            return;

        }

        mCompleteStep++;

        invalidate();

    }



    /**

     * 重置步骤

     */

    public void reset(){

        mCompleteStep=0;

        invalidate();

    }



    /**

     * View的绘制的第一阶段调用

     * @param widthMeasureSpec

     * @param heightMeasureSpec

     */

    @Override

    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        int width=getDefaultWidth();

        if(MeasureSpec.UNSPECIFIED!=MeasureSpec.getMode(widthMeasureSpec)){

            width=MeasureSpec.getSize(widthMeasureSpec);

        }



        int height=40;

        if(MeasureSpec.UNSPECIFIED!=MeasureSpec.getMode(heightMeasureSpec)){

            height=MeasureSpec.getSize(heightMeasureSpec);

        }

        LogUtil.d(TAG, "onMeasure-->width:" + width + " height:" + height);

        setMeasuredDimension(width, height);



    }



    /*

     * 在View的绘制的第二阶段(layout)中，当尺寸发生变化时调用

     * 注意：第二阶段本来是调用onLayout方法，此方法是在onLayout方法中被调用

     * @param w

     * @param h

     * @param oldw

     * @param oldh

     */

    @Override

    protected void onSizeChanged(int w, int h, int oldw, int oldh) {

        super.onSizeChanged(w, h, oldw, oldh);

        //计算位置

        mCenterY=this.getHeight()/2;

        mLeftX=this.getLeft()+getPaddingLeft();

        mLeftY=mCenterY-mLineHeight/2;

        mRightX=this.getRight()-getPaddingRight();

        mRightY=mCenterY+mLineHeight/2;

        LogUtil.d(TAG,"onSizeChanged->mLeftX:"+mLeftX);

        LogUtil.d(TAG, "onSizeChanged->mRightX:" + mRightX);

        if(mTotalStep>1){

            mDistance=(mRightX-mLeftX)/(mTotalStep-1);

            LogUtil.d(TAG,"onSizeChanged->mDistance:"+mDistance);

        }

    }



    /**

     * View的绘制的第三阶段调用

     * @param canvas

     */

    @Override

    protected void onDraw(Canvas canvas) {

        super.onDraw(canvas);

        if(mTotalStep<=0 || mCompleteStep<0 || mCompleteStep>mTotalStep){

            return;

        }

        Paint mCirclePaint=new Paint();

        mCirclePaint.setAntiAlias(true);

        mCirclePaint.setStyle(Paint.Style.FILL);

        mCirclePaint.setColor(mUnDoneColor);



        canvas.drawRect(mLeftX,mLeftY,mRightX,mRightY,mCirclePaint);

        float xLoc=mLeftX;

        //画所有的步骤(圆形)

        for(int i=0;i<mTotalStep;i++){
            canvas.drawCircle(xLoc, mLeftY, mSmallRadius, mCirclePaint);
            xLoc=xLoc+mDistance;
            Paint paint = new Paint();
            paint.setColor(UIUtil.getColor(R.color.white));
            paint.setAntiAlias(true);
            paint.setStyle(Paint.Style.FILL);
            //设置中间字体的大小
            String mCircleStr=i+"";
            float textSize = getTextSize(mCircleStr);
            paint.setTextSize(textSize);
            //获取中间文字的宽度
            int textWidth = getTextWidth(paint, mCircleStr.trim());
            //绘制中间的文字
            canvas.drawText(mCircleStr, xLoc - textWidth / 2, mLeftY + textSize / 2 - UIUtil.dp2px( 1), paint);

        }



             //画已经完成的步骤(圆形加矩形)

        xLoc=mLeftX;

        mCirclePaint.setColor(mDoneColor);

        for(int i=0;i<mCompleteStep;i++){

            if(i>0){

                canvas.drawRect(xLoc-mDistance,mLeftY,xLoc,mRightY,mCirclePaint);

            }

            canvas.drawCircle(xLoc, mLeftY, mSmallRadius, mCirclePaint);





            //画当前步骤(加光晕效果)

            if(i==mCompleteStep-1){

                mCirclePaint.setColor(getTranspartColorByAlpha(mDoneColor,0.2f));

                canvas.drawCircle(xLoc, mLeftY, mLargeRadius, mCirclePaint);

            }else {

                xLoc=xLoc+mDistance;

            }



        }

    }


    /**
     * 获取字体的大小
     *
     * @param str
     * @return
     */
    public float getTextSize(String str){
        int strLength = str.length();
        return mSmallRadius * 2 / strLength;
    }

    /**
     * 获取字符串的宽度
     *
     * @param paint
     * @param str
     * @return
     */
    private static int getTextWidth(Paint paint, String str) {
        int iRet = 0;
        if (str != null && str.length() > 0) {
            int len = str.length();
            float[] widths = new float[len];
            paint.getTextWidths(str, widths);
            for (int j = 0; j < len; j++) {
                iRet += (int) Math.ceil(widths[j]);
            }
        }
        return iRet;
    }
    /**

     * 得到默认的StepBar的宽度

     * @return

     */

    private int getDefaultWidth(){

        int screenWidth=this.getResources().getDisplayMetrics().widthPixels;

        return screenWidth-2*dp2px(DEFAULT_PADDING);

    }



    /**

     * dp单位和px单位转换

     * @param dp

     * @return

     */

    public int dp2px(int dp){

        return (int)(this.getContext().getResources().getDisplayMetrics().density*dp+0.5);

    }



    /**

     * 将指定的颜色转换成制定透明度的颜色

     * @param color

     * @param ratio

     * @return

     */

    private int getTranspartColorByAlpha(int color, float ratio){

        int newColor = 0;

        int alpha = Math.round(Color.alpha(color) * ratio);

        int r = Color.red(color);

        int g = Color.green(color);

        int b = Color.blue(color);

        newColor = Color.argb(alpha, r, g, b);

        return newColor;

    }



    public void setLineHeight(float mLineHeight) {

        this.mLineHeight = mLineHeight;

    }



    public void setSmallRadius(float mSmallRadius) {

        this.mSmallRadius = mSmallRadius;

    }



    public void setLargeRadius(float mLargeRadius) {

        this.mLargeRadius = mLargeRadius;

    }



    public void setUnDoneColor(int mUnDoneColor) {

        this.mUnDoneColor = mUnDoneColor;

    }



    public void setDoneColor(int mDoneColor) {

        this.mDoneColor = mDoneColor;

    }

}