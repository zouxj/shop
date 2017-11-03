package com.zxj.utilslibrary.utils;

import android.graphics.Typeface;
import android.text.Html;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.widget.TextView;

import com.zxj.utilslibrary.AndroidUtilsCore;

import java.text.DecimalFormat;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Administrator on 2017/8/2 0002.
 */

public class StringUtil {
    /** 是否是手号码 */
    public static boolean isTelNumber(String telnum) {
        return telnum.matches("^[1][34578][0-9]{9}$");
    }

    /** 隐藏手号码中间四位 */
    public static String formatPhoneNumber(String telnum) {
        if (telnum == null || telnum.length() == 0) {
            return null;
        }
        if (!isTelNumber(telnum)) {
            return telnum;
        }
        return telnum.substring(0, 3) + "****" + telnum.substring(7, telnum.length());
    }
    /** 显示银行卡后四位 */
    public static String formatBankNumber(String telnum) {
        if (telnum == null || telnum.length() == 0) {
            return null;
        }
        if (!checkBankCard(telnum)) {
            return telnum;
        }
        StringBuffer sy=new StringBuffer();
        for (int i=0;i<telnum.length()-4;i++){
            sy.append("*");
        }
        return sy.toString()+ telnum.substring(telnum.length()-4, telnum.length());
    }
    /** 显示银行卡后四位 */
    public static String getBankNumber(String telnum) {
        if (telnum == null || telnum.length() == 0) {
            return null;
        }
        if (!checkBankCard(telnum)) {
            return telnum;
        }
        return telnum.substring(telnum.length()-4, telnum.length());
    }
    /** 格式化字符串 */
    public static String format(int strResId, Object... args) {
        return format(UIUtil.getString(strResId), args);
    }

    /** 格式化字符串 */
    public static String format(String formatStr, Object... args) {
        return String.format(Locale.getDefault(), formatStr, args);
    }

    /**
     * 应用指定的字体到文字显示上
     *
     * @param targetTextView 目标TextView
     * @param fontFileName   需要设置的字体文件名(字体文件需要放置在assets/fonts目录下) 如 HandmadeTypewriter.ttf
     */
    public static void applyFont(TextView targetTextView, String fontFileName) {
        try {
            targetTextView.setTypeface(Typeface.createFromAsset(AndroidUtilsCore.getContext().getAssets(), "fonts/" + fontFileName));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /** 显示不同颜色字符串 */
    public static SpannableStringBuilder colorSubString(String contentStr, int colorForTarget, String targetString) {
        if (TextUtils.isEmpty(contentStr)) contentStr = "";
        SpannableStringBuilder style = new SpannableStringBuilder(contentStr);
        if (targetString != null && contentStr.contains(targetString)) {
            int start = contentStr.indexOf(targetString);
            style.setSpan(new ForegroundColorSpan(colorForTarget), start, start + targetString.length(), Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
        }
        return style;
    }

    /** 显示不同颜色字符串 */
    public static Spanned colorTextString(String prefixString, int colorForTarget, String targetString) {
        return Html.fromHtml(prefixString + "<font color='" + colorForTarget + "'>" + targetString + "</font>");
    }

    /**
     * 判断空字符
     * @param password
     * @return
     */
    public static boolean validText(String password) {
        return !TextUtils.isEmpty(password)&&password.length()>0;
    }

    /**
     * int转strign
     * @param time
     * @return
     */
    public static String formatNum(int time) {
        return time < 10 ? "0" + time : String.valueOf(time);
    }
    public static String m2(Double value) {
                 DecimalFormat df = new DecimalFormat("######0.00");
                   return  df.format(value);
          }

    /**
     * 字符串转int
     * @param num
     * @return
     */
    public static int formatIntger(String num) {
       if (validText(num))
           return  Integer.parseInt(num);
       return 0;
    }
    /**
     * 字符串转Double
     * @param num
     * @return
     */
    public static Double formatDouble(String num) {
        if (validText(num))
            return  Double.parseDouble(num);
        return 0.00;
    }

    /*
    毫秒转字符
     */
    public static String formatMillisecond(int millisecond) {
        String retMillisecondStr;
        if (millisecond > 99) {
            retMillisecondStr = String.valueOf(millisecond / 10);
        } else if (millisecond <= 9) {
            retMillisecondStr = "0" + millisecond;
        } else {
            retMillisecondStr = String.valueOf(millisecond);
        }

        return retMillisecondStr;
    }
    /**
     * 校验银行卡卡号
     * @param cardId
     * @return
     */
    public static boolean checkBankCard(String cardId) {
        if (!validText(cardId))
            return false;
        char bit = getBankCardCheckCode(cardId.substring(0, cardId.length() - 1));
        if(bit == 'N'){
            return false;
        }
        return cardId.charAt(cardId.length() - 1) == bit;
    }
    /**
     * 从不含校验位的银行卡卡号采用 Luhm 校验算法获得校验位
     * @param nonCheckCodeCardId
     * @return
     */
    public static char getBankCardCheckCode(String nonCheckCodeCardId){
        if(nonCheckCodeCardId == null || nonCheckCodeCardId.trim().length() == 0
                || !nonCheckCodeCardId.matches("\\d+")) {
        //如果传的不是数据返回N
            return 'N';
        }
        char[] chs = nonCheckCodeCardId.trim().toCharArray();
        int luhmSum = 0;
        for(int i = chs.length - 1, j = 0; i >= 0; i--, j++) {
            int k = chs[i] - '0';
            if(j % 2 == 0) {
                k *= 2;
                k = k / 10 + k % 10;
            }
            luhmSum += k;
        }
        return (luhmSum % 10 == 0) ? '0' : (char)((10 - luhmSum % 10) + '0');
    }

    //判断字符串是否是数字
    public static boolean isNumeric(String str){
        Pattern pattern = Pattern.compile("[0-9]*");
        Matcher isNum = pattern.matcher(str);
        if( !isNum.matches() ){
            return false;
        }
        return true;
    }

}
