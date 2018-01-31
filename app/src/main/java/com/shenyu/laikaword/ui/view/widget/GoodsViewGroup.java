package com.shenyu.laikaword.ui.view.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.shenyu.laikaword.R;
import com.shenyu.laikaword.model.GoodsViewGroupItem;
import com.zxj.utilslibrary.utils.UIUtil;

import java.util.ArrayList;
import java.util.List;

public class GoodsViewGroup extends ViewGroup {

    private List<GoodsViewGroupItem> mItems = new ArrayList<>();
    private Context mContext;

    private int horInterval; //水平间隔
    private int verInterval; //垂直间隔

    private int viewWidth;   //控件的宽度
    private int viewHeight;  //控件的高度

    //按钮水平跟垂直内边距
    private int horPadding;
    private int verPadding;

    //正常样式
    private float textSize;
    private int bgResoureNor;
    private int textColorNor;

    //选中的样式
    private int bgResoureSel;
    private int textColorSel;

    private boolean isSelector; //是否做选择之后的效果

    public GoodsViewGroup(Context context) {
        this(context, null);
    }

    public GoodsViewGroup(Context context, AttributeSet attrs) {
        super(context, attrs);
        getResources().getColor(R.color.goods_item_text_normal);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet set) {
        mContext = context;
        TypedArray attrs = mContext.obtainStyledAttributes(set, R.styleable.GoodsViewGroup);
        isSelector = attrs.getBoolean(R.styleable.GoodsViewGroup_isSelector, true);
        textSize = attrs.getDimensionPixelSize(R.styleable.GoodsViewGroup_itemTextSize, 0);
        if (textSize == 0) {
            textSize = getResources().getDimensionPixelSize(R.dimen.text_size_14sp);//14sp
        }
        horInterval = attrs.getDimensionPixelSize(R.styleable.GoodsViewGroup_horInterval, 20);
        verInterval = attrs.getDimensionPixelSize(R.styleable.GoodsViewGroup_verInterval, 20);
        horPadding = attrs.getDimensionPixelSize(R.styleable.GoodsViewGroup_horPadding, 20);
        verPadding = attrs.getDimensionPixelSize(R.styleable.GoodsViewGroup_verPadding, 10);
        bgResoureNor = attrs.getResourceId(R.styleable.GoodsViewGroup_normal_drawable, R.drawable.goods_item_btn_normal);
        bgResoureSel = attrs.getResourceId(R.styleable.GoodsViewGroup_selected_drawable, R.drawable.goods_item_btn_selected);
        textColorNor = attrs.getColor(R.styleable.GoodsViewGroup_normal_textColor, getColorResoure(R.color.color_333));
        textColorSel = attrs.getColor(R.styleable.GoodsViewGroup_selected_textColor, getColorResoure(R.color.goods_item_text_selected));
        attrs.recycle();
    }

    private int getColorResoure(int resId) {
        return getResources().getColor(resId);
    }

    /**
     * 计算控件的大小
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        viewWidth = measureWidth(widthMeasureSpec);
        viewHeight = measureHeight(heightMeasureSpec);
        // 计算自定义的ViewGroup中所有子控件的大小
        measureChildren(widthMeasureSpec, heightMeasureSpec);
        // 设置自定义的控件ViewGroup的大小
        setMeasuredDimension(viewWidth, viewHeight);
    }

    private int measureWidth(int pWidthMeasureSpec) {
        int result = 0;
        int widthMode = MeasureSpec.getMode(pWidthMeasureSpec);
        int widthSize = MeasureSpec.getSize(pWidthMeasureSpec);
        switch (widthMode) {
            case MeasureSpec.AT_MOST:
            case MeasureSpec.EXACTLY:
                result = widthSize;
                break;
        }
        return result;
    }

    private int measureHeight(int pHeightMeasureSpec) {
        int result = 0;
        int heightMode = MeasureSpec.getMode(pHeightMeasureSpec);
        int heightSize = MeasureSpec.getSize(pHeightMeasureSpec);
        switch (heightMode) {
            case MeasureSpec.UNSPECIFIED:
                result = getSuggestedMinimumHeight();
                break;
            case MeasureSpec.AT_MOST:
            case MeasureSpec.EXACTLY:
                result = heightSize;
                break;
        }
        return result;
    }

    /**
     * 计算控件的自适应高度
     */
    private int getViewHeight() {
        int viewwidth = horInterval;
        int viewheight = verInterval;
        //初始化控件的高度等于第一个元素
        if (getChildCount() > 0) {
            viewheight = getChildAt(0).getMeasuredHeight() + verInterval;
        }
        for (int i = 0; i < getChildCount(); i++) {
            View childView = getChildAt(i);
            // 获取在onMeasure中计算的视图尺寸
            int measureHeight = childView.getMeasuredHeight();
            int measuredWidth = childView.getMeasuredWidth();
            //------------当前按钮按钮是否在水平上够位置(2017/7/10)------------
            if (viewwidth + getChildCurrentWidth(i) > viewWidth) {
                viewwidth = (measuredWidth + horInterval * 2);
                viewheight += (measureHeight + verInterval);
            } else {
                viewwidth += (measuredWidth + horInterval);
            }
        }
        return viewheight;
    }

    /**
     * 覆写onLayout，其目的是为了指定视图的显示位置，方法执行的前后顺序是在onMeasure之后，因为视图肯定是只有知道大小的情况下，
     * 才能确定怎么摆放
     */
    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        // 遍历所有子视图
        int posLeft = 0;
        int posTop = verInterval;
        int posRight = horInterval;
        int posBottom;
        for (int i = 0; i < getChildCount(); i++) {
            View childView = getChildAt(i);
            // 获取在onMeasure中计算的视图尺寸
            int measureHeight = childView.getMeasuredHeight();
            int measuredWidth = childView.getMeasuredWidth();
            if (posRight + getChildCurrentWidth(i) > viewWidth) {
                posLeft = 0;
                posTop += (measureHeight + verInterval);
            }
            posRight = posLeft + measuredWidth;
            posBottom = posTop + measureHeight;
            childView.layout(posLeft, posTop, posRight, posBottom);
            posLeft += (measuredWidth + horInterval);
        }
    }


    /**
     * 获得当前按钮所需的宽度
     *
     * @param i
     * @return
     */
    private int getChildCurrentWidth(int i) {
        return getChildAt(i).getMeasuredWidth() + horInterval;
    }

    private OnGroupItemClickListener onGroupItemClickListener;

    public void setGroupClickListener(OnGroupItemClickListener listener) {
        onGroupItemClickListener = listener;
        for (int i = 0; i < getChildCount(); i++) {
            final TextView childView = (TextView) getChildAt(i);
            final int itemPos = i;
            childView.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View view) {
                    String valueName = ((TextView) view).getText().toString(); //白色
                    onGroupItemClickListener.onGroupItemClick(itemPos, getItemKey(valueName), valueName);
                    if (isSelector) {
                        chooseItemStyle(itemPos);
                    }
                }
            });
        }
    }

    /**
     * 获取按钮的颜色的按钮去找1 , value->key
     *
     * @param itemBtnText
     * @return
     */
    private String getItemKey(String itemBtnText) {
        for (int i = 0; i < mItems.size(); i++) {
            if (mItems.get(i).getValue().equals(itemBtnText)) {
                return mItems.get(i).getKey();
            }
        }
        return "";
    }

    /**
     * 选中那个的样式
     *
     * @param pos
     */
    public void chooseItemStyle(int pos) {
        clearItemsStyle();
        if (pos < getChildCount()) {
            TextView childView = (TextView) getChildAt(pos);
            childView.setBackgroundResource(bgResoureSel);
            childView.setTextColor(textColorSel);
            setItemPadding(childView);
        }
    }

    /**
     * 清除ViewGroup所有的样式
     */
    private void clearItemsStyle() {
        for (int i = 0; i < getChildCount(); i++) {
            TextView childView = (TextView) getChildAt(i);
            childView.setBackgroundResource(bgResoureNor);
            childView.setTextColor(textColorNor);
            setItemPadding(childView);
        }
    }

    private void setItemPadding(TextView view) {
        view.setPadding(horPadding, verPadding, horPadding, verPadding);
    }

    public void addItemViews(List<GoodsViewGroupItem> items) {
        if (items != null) {
            mItems = items;
            removeAllViews();
            for (GoodsViewGroupItem item : items) {
                addItemView(item);
            }
        }
        invalidate();
    }

    private void addItemView(GoodsViewGroupItem item) {
        TextView childView = new TextView(mContext);
        childView.setLayoutParams(new LayoutParams((int) UIUtil.dp2px(95),(int) UIUtil.dp2px(44)));
        childView.setTextSize(TypedValue.COMPLEX_UNIT_PX, textSize);
        childView.setBackgroundResource(bgResoureNor);
        childView.setGravity(Gravity.CENTER);
        setItemPadding(childView);
        childView.setTextColor(textColorNor);
        childView.setText(item.getValue());
        this.addView(childView);
    }

    public void setSelector(boolean selector) {
        isSelector = selector;
    }

    public interface OnGroupItemClickListener {
        void onGroupItemClick(int itemPos, String key, String value);
    }
}