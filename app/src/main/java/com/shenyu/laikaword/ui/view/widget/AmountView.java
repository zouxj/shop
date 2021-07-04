package com.shenyu.laikaword.ui.view.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.text.Editable;
import android.text.InputFilter;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import androidx.annotation.RequiresApi;

import com.shenyu.laikaword.R;
import com.zxj.utilslibrary.utils.StringUtil;
import com.zxj.utilslibrary.utils.UIUtil;

/**
 * Created by shenyu_zxjCode on 2017/9/25 0025.
 */

public class AmountView extends LinearLayout implements View.OnClickListener, TextWatcher {
    private static final String TAG = "AmountView";
    Button btnDecrease;
    public  EditText etAmount;
    Button btnIncrease;
    private int amount = 1; //购买数量
    private int goods_storage = 1; //商品库存

    private Double damount = 1.0; //购买数量
    private Double dgoods_storage = 1.0; //商品库存
    private OnAmountChangeListener mListener;
    private OnDoubleAmountChangeListener onDoubleAmountChangeListener;
    private int flg=0;

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    public AmountView(Context context) {
        this(context, null);

    }


    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    public AmountView(Context context, AttributeSet attrs) {

        super(context, attrs);


        LayoutInflater.from(context).inflate(R.layout.view_amount, this);

        etAmount = findViewById(R.id.etAmount);

        btnDecrease = findViewById(R.id.btnDecrease);

        btnIncrease = findViewById(R.id.btnIncrease);

        btnDecrease.setOnClickListener(this);

        btnIncrease.setOnClickListener(this);

        etAmount.addTextChangedListener(this);


        TypedArray obtainStyledAttributes = getContext().obtainStyledAttributes(attrs, R.styleable.AmountView);

        int btnWidth = obtainStyledAttributes.getDimensionPixelSize(R.styleable.AmountView_btnWidth, LayoutParams.WRAP_CONTENT);

        int tvWidth = obtainStyledAttributes.getDimensionPixelSize(R.styleable.AmountView_tvWidth, 80);
        Drawable bg = obtainStyledAttributes.getDrawable(R.styleable.AmountView_bg);
        int tvTextSize = obtainStyledAttributes.getDimensionPixelSize(R.styleable.AmountView_tvTextSize, 0);
        Drawable btnBg = obtainStyledAttributes.getDrawable(R.styleable.AmountView_bgBtn);
        int btnTextSize = obtainStyledAttributes.getDimensionPixelSize(R.styleable.AmountView_btnTextSize, 0);
        int btnHeight=obtainStyledAttributes.getDimensionPixelSize(R.styleable.AmountView_btnHeight, 0);
        obtainStyledAttributes.recycle();


        LayoutParams btnParams = new LayoutParams(btnWidth,LayoutParams.MATCH_PARENT);

        btnDecrease.setLayoutParams(btnParams);

        btnIncrease.setLayoutParams(btnParams);

        if (btnTextSize != 0) {
            btnDecrease.setTextSize(TypedValue.COMPLEX_UNIT_PX, btnTextSize);
            btnIncrease.setTextSize(TypedValue.COMPLEX_UNIT_PX, btnTextSize);

        }


        LayoutParams textParams = new LayoutParams(tvWidth, LayoutParams.MATCH_PARENT);

        etAmount.setLayoutParams(textParams);

        if (tvTextSize != 0) {

            etAmount.setTextSize(tvTextSize);

        }
        if (bg!=null){
            setBackground(bg);
        }
        if (btnBg!=null){
            btnIncrease.setBackground(btnBg);
            btnDecrease.setBackground(btnBg);
        }
    }


    public void setOnAmountChangeListener(OnAmountChangeListener onAmountChangeListener) {

        this.mListener = onAmountChangeListener;

    }
    public void setFlg(int flg){
        this.flg=flg;
        if (flg!=0){
            etAmount.setFilters(new InputFilter[]{new MoneyValueFilter().setDigits(1)});
            etAmount.setInputType(InputType.TYPE_NUMBER_FLAG_DECIMAL);
            etAmount.setTextColor(UIUtil.getColor(R.color.app_theme_red));
        }
    }
    public void setOnDoubleAmountChangeListener(OnDoubleAmountChangeListener onDoubleAmountChangeListener) {

        this.onDoubleAmountChangeListener = onDoubleAmountChangeListener;

    }

    public void setGoods_storage(int goods_storage) {

        this.goods_storage = goods_storage;

    }
    public void setDGoods_storage(double dgoods_storage) {

        this.dgoods_storage = dgoods_storage;

    }
    public void setMixZheKou(double mixCount) {
        etAmount.setText(StringUtil.m1(mixCount));
        this.mixZheKou = mixCount;

    }
    private double mixZheKou;
    @Override

    public void onClick(View v) {

        int i = v.getId();
        if (flg==0) {
            if (i == R.id.btnDecrease) {

                if (amount > 1) {

                    amount--;

                    etAmount.setText(amount + "");

                }

            } else if (i == R.id.btnIncrease) {

                if (amount < goods_storage) {

                    amount++;

                    etAmount.setText(amount + "");

                }

            }


            etAmount.clearFocus();


            if (mListener != null) {

                mListener.onAmountChange(this, amount);

            }
        }else {
            if (i == R.id.btnDecrease) {

            if (damount > mixZheKou) {

                damount-=0.1;

                etAmount.setText(StringUtil.m1(damount));

            }

        } else if (i == R.id.btnIncrease) {

            if (damount < dgoods_storage) {

                damount+=0.1;

                etAmount.setText(StringUtil.m1(damount));

            }

        }


            etAmount.clearFocus();


            if (onDoubleAmountChangeListener != null) {

                onDoubleAmountChangeListener.onAmountChange(this, damount);

            }
        }

    }


    @Override

    public void beforeTextChanged(CharSequence s, int start, int count, int after) {


    }


    @Override

    public void onTextChanged(CharSequence s, int start, int before, int count) {


    }


    @RequiresApi(api = Build.VERSION_CODES.GINGERBREAD)
    @Override

    public void afterTextChanged(Editable s) {

        if (s.toString().isEmpty())

            return;
        if (flg==0) {
            amount = Integer.valueOf(s.toString());

            if (amount > goods_storage) {

                etAmount.setText(goods_storage + "");

                return;

            }


            if (mListener != null) {

                mListener.onAmountChange(this, amount);

            }
        }else {
            damount =StringUtil.formatDouble(s.toString());
            if (damount > dgoods_storage) {
                etAmount.setText(StringUtil.m1(dgoods_storage));
                return;

            }
            if (damount < mixZheKou) {
                etAmount.setText(StringUtil.m1(mixZheKou));
                return;

            }

            if (onDoubleAmountChangeListener != null) {

                onDoubleAmountChangeListener.onAmountChange(this, damount);

            }
        }

    }


    public interface OnAmountChangeListener {

        void onAmountChange(View view, int amount);

    }
    public interface OnDoubleAmountChangeListener {

        void onAmountChange(View view, double amount);

    }
}
