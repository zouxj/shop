package com.shenyu.laikaword.helper;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Paint;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.shenyu.laikaword.R;
import com.shenyu.laikaword.module.us.setpassword.SetPassWordMsgCodeActivity;
import com.zxj.utilslibrary.utils.IntentLauncher;
import com.zxj.utilslibrary.utils.KeyBoardUtil;
import com.zxj.utilslibrary.utils.StringUtil;
import com.zxj.utilslibrary.utils.UIUtil;
import com.zxj.utilslibrary.widget.countdownview.PayPsdInputView;

/**
 * Created by Administrator on 2017/8/4 0004.
 */

public  final  class DialogHelper {
    /**
     * 拍照调起来
     * @param context
     * @param listener
     */
    public static void takePhoto(Context context, final TakePhotoListener listener){
        final Dialog dialog = new Dialog(context, R.style.DialogBottom);
        dialog.setCanceledOnTouchOutside(true);
        View view = View.inflate(context,R.layout.dialog_select_photo,null);
        TextView tvPhoto = (TextView)view.findViewById(R.id.tv_photo);
        TextView tvCamera = (TextView)view.findViewById(R.id.tv_camera);
        TextView tvCannel  = (TextView)view.findViewById(R.id.tv_cannel);
        tvPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                if(listener!=null){
                    listener.takeByPhoto();
                }
            }
        });
        tvCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                if(listener!=null){
                    listener.takeByCamera();
                }
            }
        });
        tvCannel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.setContentView(view);
        Window window = dialog.getWindow();
        WindowManager.LayoutParams windowParams = window.getAttributes();
        windowParams.x = 0;
        //window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE );
        windowParams.gravity = Gravity.BOTTOM;
        //设置window的布局参数
        window.setAttributes(windowParams);
        // window.setBackgroundDrawableResource(R.drawable.alert_dialog_background);

        // 显示的大小是contentView 的大小
        dialog.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.WRAP_CONTENT);
        dialog.show();
    }

    public  interface TakePhotoListener {
        void takeByPhoto();
        void takeByCamera();
    }

    /**
     * 更新应用
     * @param context
     * @param title
     * @param msg
     * @param positiveText
     * @param negativeText
     * @param is_must
     * @param callback
     * @return
     */
    public static Dialog commonDialog(Context context, String title, String msg , String positiveText, String negativeText, boolean is_must, final ButtonCallback callback){
        final Dialog dialog = new Dialog(context,R.style.Dialog);
        if(!is_must) {
            dialog.setCanceledOnTouchOutside(true);
        }else{
            dialog.setCanceledOnTouchOutside(false);
        }
        View view = View.inflate(context,R.layout.dialog_update,null);

        TextView tvTitle = (TextView)view.findViewById(R.id.tv_title);
        TextView tvMsg  = (TextView)view.findViewById(R.id.tv_msg);
        tvMsg.setText(msg);
        TextView tvOk = (TextView)view.findViewById(R.id.tv_ok);
        TextView tvCancel = (TextView)view.findViewById(R.id.tv_cancel);

        tvTitle.setText(title);


        if(!TextUtils.isEmpty(positiveText)){
            tvOk.setText(positiveText);
        }
        if(!TextUtils.isEmpty(negativeText)){
            tvCancel.setText(negativeText);
        }

        tvOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                if(callback!=null){
                    callback.onPositive(dialog);
                }
            }
        });

        tvCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                if(callback!=null){
                    callback.onNegative(dialog);
                }
            }
        });

        dialog.setContentView(view);
        Window window = dialog.getWindow();
        WindowManager.LayoutParams windowParams = window.getAttributes();
        int width = (int)(window.getWindowManager().getDefaultDisplay().getWidth()*0.8);
        windowParams.x = 0;
        windowParams.width = width;

        window.setAttributes(windowParams);

        dialog.setOnKeyListener(new DialogInterface.OnKeyListener() {
            @Override
            public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                if(keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0){
                    return true;
                }
                return false;
            }
        });
        return dialog;
    }
    public static Dialog tDialog(Context context, String msg, String okdec, final ButtonCallback buttonCallback) {
        final Dialog dialog = new Dialog(context, R.style.Dialog);
        dialog.setCanceledOnTouchOutside(true);
        View view = View.inflate(context, R.layout.dialog_tishi, null);
        @SuppressLint("WrongViewCast")
        TextView tvMsg = view.findViewById(R.id.tv_msg);
        tvMsg.setText(msg);
        TextView tvOk = (TextView) view.findViewById(R.id.tv_ok);
        tvOk.setText(okdec);
        tvOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buttonCallback.onNegative(dialog);
            }
        });

        dialog.setContentView(view);
        Window window = dialog.getWindow();
        WindowManager.LayoutParams windowParams = window.getAttributes();
        int width = (int) (window.getWindowManager().getDefaultDisplay().getWidth() * 0.8);
        windowParams.x = 0;
        windowParams.width = width;

        window.setAttributes(windowParams);
        dialog.getWindow().setType(WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);
        dialog.setOnKeyListener(new DialogInterface.OnKeyListener() {
            @Override
            public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
                    return true;
                }
                return false;
            }
        });
        return dialog;

    }

        public interface  ButtonCallback {
        void onNegative(Dialog dialog);
        void onPositive(Dialog dialog);
    }

    /**
     *设置密码Dialog
     * @param context
     * @param title
     * @param msg
     * @param positiveText
     * @param negativeText
     * @param is_must
     * @param callback
     * @return
     */
    public  static  Dialog setPwdDialog(Context context, String title, String msg , String positiveText, String negativeText, boolean is_must, final ButtonCallback callback){
        final Dialog dialog = new Dialog(context,R.style.Dialog);
        if(!is_must) {
            dialog.setCanceledOnTouchOutside(true);
        }else{
            dialog.setCanceledOnTouchOutside(false);
        }
        View view = View.inflate(context,R.layout.dialog_set_pwd,null);
        dialog.setContentView(view);
        Window window = dialog.getWindow();
        WindowManager.LayoutParams windowParams = window.getAttributes();
        int width = (int)(window.getWindowManager().getDefaultDisplay().getWidth()*0.8);
        windowParams.x = 0;
        windowParams.width = width;
        window.setAttributes(windowParams);
        return dialog;
    }

    /**
     * 输入密码框
     * @param context
     * @param is_must
     * @param linstenrText
     * @return
     */
    public  static  Dialog setInputDialog(final Activity context, boolean is_must, String money,String msg, final LinstenrText linstenrText){
        final Dialog dialog = new Dialog(context,R.style.Dialog);
        if(!is_must) {
            dialog.setCanceledOnTouchOutside(true);
        }else{
            dialog.setCanceledOnTouchOutside(false);
        }
        View view = View.inflate(context,R.layout.dialog_input_passwprd,null);
        final PayPsdInputView textPassWord = view.findViewById(R.id.psd_view_password);
        TextView tvMoney = view.findViewById(R.id.tv_money);
        TextView tvTishi = view.findViewById(R.id.tv_tishi);
        tvTishi.setText(msg);
        if (StringUtil.validText(money)){
            tvMoney.setVisibility(View.VISIBLE);
            if (money.length()>10) {
                tvMoney.setTextSize(UIUtil.sp2px(context, 8));
                tvMoney.setText(money);
            }
            else {
                tvMoney.setTextSize(UIUtil.sp2px(context, 10));
                tvMoney.setText("￥" + money);
            }
        }else {
            tvMoney.setVisibility(View.GONE);
        }
        TextView forpasword = view.findViewById(R.id.tv_forgert_pwd);
        view.findViewById(R.id.iv_close).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        forpasword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO 忘记密码
                IntentLauncher.with(context).put("RESERT","RESERT").launch(SetPassWordMsgCodeActivity.class);
                dialog.dismiss();
            }
        });
        //设置忘记密码下划线
        forpasword.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG); //下划线
        forpasword.getPaint().setAntiAlias(true);//抗锯齿
        textPassWord.setOnInputPasswordListener(new PayPsdInputView.onInputPasswordListener() {
            @Override
            public void onInputListner(String str) {
                linstenrText.onLintenerText(str);
                dialog.dismiss();

            }
        });
        dialog.setContentView(view);
        Window window = dialog.getWindow();
        //弹出对话框后直接弹出键盘
        KeyBoardUtil.showSoftInput(textPassWord);
        WindowManager.LayoutParams windowParams = window.getAttributes();
        int width = (int)(window.getWindowManager().getDefaultDisplay().getWidth()*0.9);
        windowParams.x = 0;
        windowParams.width = width;
        window.setAttributes(windowParams);

        return dialog;
    }
    //监听输入框密码
    public interface LinstenrText{
        //回调返回密码
        void onLintenerText(String passWord);
        void onWjPassword();
    }

    /**
     * 删除银行卡
     * @param context
     * @param buttonCallback
     */
    public static void deleteBankDialog(Context context, String msg,String okBt,final ButtonCallback buttonCallback){
        final Dialog dialog = new Dialog(context, R.style.Dialog);
        dialog.setCanceledOnTouchOutside(true);
        View view = View.inflate(context,R.layout.dialog_delete_bank,null);
        TextView tvDelete= (TextView)view.findViewById(R.id.tv_delete);
        tvDelete.setText(okBt);
        TextView tvCannel  = (TextView)view.findViewById(R.id.tv_cancel_dialog);
        TextView tvMsg = view.findViewById(R.id.tv_delete_msg);
        tvMsg.setText(msg);
        tvDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(buttonCallback!=null){
                    buttonCallback.onNegative(dialog);
                }
                dialog.dismiss();

            }
        });
        tvCannel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(buttonCallback!=null){
                    buttonCallback.onPositive(dialog);
                }
                dialog.dismiss();
            }
        });
        dialog.setContentView(view);
        Window window = dialog.getWindow();
        WindowManager.LayoutParams windowParams = window.getAttributes();
        windowParams.x = 0;
        //window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE );
        windowParams.gravity = Gravity.BOTTOM;
        //设置window的布局参数
        window.setAttributes(windowParams);
        // window.setBackgroundDrawableResource(R.drawable.alert_dialog_background);

        // 显示的大小是contentView 的大小
        dialog.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.WRAP_CONTENT);
        dialog.show();
    }


    /**
     * 删除银行卡
     * @param context
     * @param buttonCallback
     */
    public static void tianXAddress(Context context, final ButtonCallback buttonCallback){
        final Dialog dialog = new Dialog(context, R.style.Dialog);
        dialog.setCanceledOnTouchOutside(true);
        View view = View.inflate(context,R.layout.dialog_shengqi_adress,null);
        view.findViewById(R.id.tv_select_address).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(buttonCallback!=null){
                    buttonCallback.onNegative(dialog);
                }
                dialog.dismiss();

            }
        });

        dialog.setContentView(view);
        Window window = dialog.getWindow();
        WindowManager.LayoutParams windowParams = window.getAttributes();
        int width = (int)(window.getWindowManager().getDefaultDisplay().getWidth()*0.8);
        windowParams.x = 0;
        windowParams.width = width;
        window.setAttributes(windowParams);
        dialog.show();
    }


    public static Dialog appupate(Context context, String title, String msg , String negativeText, boolean is_must, final ButtonCallback callback){
        final Dialog dialog = new Dialog(context,R.style.Dialog);

        View view = View.inflate(context,R.layout.app_update_dialog,null);
        TextView tvMsg  = (TextView)view.findViewById(R.id.tv_msg);
        tvMsg.setText(msg);
        TextView tvOk = (TextView)view.findViewById(R.id.bt_commit);
        ImageView tvCancel = (ImageView)view.findViewById(R.id.tv_cancel);
        if(!is_must) {
            dialog.setCanceledOnTouchOutside(true);
//            tvCancel.setVisibility(View.GONE);
        }else{
            tvCancel.setVisibility(View.GONE);
            dialog.setCanceledOnTouchOutside(false);
        }
        if(!TextUtils.isEmpty(negativeText)){
            tvOk.setText(negativeText);
        }

        tvCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                if(callback!=null){
                    callback.onPositive(dialog);
                }
            }
        });

        tvOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                if(callback!=null){
                    callback.onNegative(dialog);
                }
            }
        });

        dialog.setContentView(view);
        Window window = dialog.getWindow();
        WindowManager.LayoutParams windowParams = window.getAttributes();
        int width = (int)(window.getWindowManager().getDefaultDisplay().getWidth()*0.8);
        windowParams.x = 0;
        windowParams.width = width;

        window.setAttributes(windowParams);

        dialog.setOnKeyListener(new DialogInterface.OnKeyListener() {
            @Override
            public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                if(keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0){
                    return true;
                }
                return false;
            }
        });
        return dialog;
    }

    public static Dialog inuputGoodsCode(Context context, final InputInterfaceGoodCode linstenrText) {
        final Dialog dialog = new Dialog(context, R.style.Dialog);
        dialog.setCanceledOnTouchOutside(true);
        View view = View.inflate(context, R.layout.dialog_input_goods_code, null);
        @SuppressLint("WrongViewCast")
        final EditText tvMsg = view.findViewById(R.id.tv_msg);
        TextView tvOk = view.findViewById(R.id.tv_ok);
        tvOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                linstenrText.onLintenerText(dialog,tvMsg.getText().toString().trim());
            }
        });
        view.findViewById(R.id.iv_close).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    dialog.dismiss();
            }
        });

        dialog.setContentView(view);
        Window window = dialog.getWindow();
        WindowManager.LayoutParams windowParams = window.getAttributes();
        int width = (int) (window.getWindowManager().getDefaultDisplay().getWidth() * 0.8);
        windowParams.x = 0;
        windowParams.width = width;

        window.setAttributes(windowParams);
        dialog.getWindow().setType(WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);
        dialog.setOnKeyListener(new DialogInterface.OnKeyListener() {
            @Override
            public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
                    return true;
                }
                return false;
            }
        });
        return dialog;

    }
    /**
     * 更新应用
     * @param context
     * @param msg
     * @param positiveText
     * @param negativeText
     * @param is_must
     * @param callback
     * @return
     */
    public static Dialog tDialog(Context context,String msg , String positiveText, String negativeText, boolean is_must, final ButtonCallback callback){
        final Dialog dialog = new Dialog(context,R.style.Dialog);
        if(!is_must) {
            dialog.setCanceledOnTouchOutside(true);
        }else{
            dialog.setCanceledOnTouchOutside(false);
        }
        View view = View.inflate(context,R.layout.dialog_resell_tishi,null);

        TextView tvMsg  = (TextView)view.findViewById(R.id.tv_msg);
        tvMsg.setText(msg);
        TextView tvOk = (TextView)view.findViewById(R.id.tv_ok);
        TextView tvCancel = (TextView)view.findViewById(R.id.tv_cancel);



        if(!TextUtils.isEmpty(positiveText)){
            tvOk.setText(positiveText);
        }
        if(!TextUtils.isEmpty(negativeText)){
            tvCancel.setText(negativeText);
        }

        tvCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(callback!=null){
                    callback.onPositive(dialog);
                }
            }
        });

        tvOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                if(callback!=null){
                    dialog.dismiss();
                    callback.onNegative(dialog);
                }
            }
        });

        dialog.setContentView(view);
        Window window = dialog.getWindow();
        WindowManager.LayoutParams windowParams = window.getAttributes();
        int width = (int)(window.getWindowManager().getDefaultDisplay().getWidth()*0.8);
        windowParams.x = 0;
        windowParams.width = width;

        window.setAttributes(windowParams);

        dialog.setOnKeyListener(new DialogInterface.OnKeyListener() {
            @Override
            public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                if(keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0){
                    return true;
                }
                return false;
            }
        });
        return dialog;
    }

    //商品兑换码接口
    public interface InputInterfaceGoodCode {
        void onLintenerText(Dialog dialog,String passWord);
    }
}
