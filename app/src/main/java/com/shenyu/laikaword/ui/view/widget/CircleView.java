package com.shenyu.laikaword.ui.view.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.Gravity;

import com.shenyu.laikaword.R;

/**
 * Created by Administrator on 2017/8/14 0014.
 */

public class CircleView extends android.support.v7.widget.AppCompatTextView {

    private float mRadius;
    private int backgroundColor;
    private int textColor;

    public CircleView(Context context) {
        this(context, null);
    }

    public CircleView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
        setTextColor(Color.WHITE);
    }

    private void init(Context context, AttributeSet attrs) {
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.circleTextView);
        backgroundColor = typedArray.getColor(R.styleable.circleTextView_ct_backgroundColor, Color.RED);
        textColor = typedArray.getColor(R.styleable.circleTextView_ct_text_color,Color.WHITE);
        typedArray.recycle();
        setGravity(Gravity.CENTER);
    }

    Paint paint = new Paint();
    @Override
    protected void onDraw(Canvas canvas) {
        mRadius = Math.min(getHeight(), getWidth()) / 2;
        paint.setColor(backgroundColor);
        paint.setAntiAlias(true);
        canvas.drawCircle(getWidth() / 2, getHeight() / 2, mRadius, paint);
        super.onDraw(canvas);
    }

    public void setTextC(CharSequence text) {
        setText(text);
    }

    public void setBorderColor(int borderColor) {
        this.backgroundColor = borderColor;
        invalidate();
    }
}
