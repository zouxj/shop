package com.shenyu.laikaword.helper;

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
import android.widget.TextView;

import com.shenyu.laikaword.R;
import com.zxj.utilslibrary.utils.KeyBoardUtil;
import com.zxj.utilslibrary.widget.countdownview.PayPsdInputView;

import org.w3c.dom.Text;

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
    public static Dialog makeUpdate(Context context, String title, String msg , String positiveText, String negativeText, boolean is_must, final ButtonCallback callback){
        final Dialog dialog = new Dialog(context,R.style.Dialog);
        if(!is_must) {
            dialog.setCanceledOnTouchOutside(true);
        }else{
            dialog.setCanceledOnTouchOutside(false);
        }
        View view = View.inflate(context,R.layout.dialog_update,null);

        TextView tvTitle = (TextView)view.findViewById(R.id.tv_title);
        TextView tvMsg  = (TextView)view.findViewById(R.id.tv_msg);
        TextView tvOk = (TextView)view.findViewById(R.id.tv_ok);
        TextView tvCancel = (TextView)view.findViewById(R.id.tv_cancel);

        tvTitle.setText(title);
        tvMsg.setText(msg);

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
    public  static  Dialog setInputDialog(Context context, boolean is_must, String money,final LinstenrText linstenrText){
        final Dialog dialog = new Dialog(context,R.style.Dialog);
        if(!is_must) {
            dialog.setCanceledOnTouchOutside(true);
        }else{
            dialog.setCanceledOnTouchOutside(false);
        }
        View view = View.inflate(context,R.layout.dialog_input_passwprd,null);
        final PayPsdInputView textPassWord = view.findViewById(R.id.psd_view_password);
        TextView tvMoney = view.findViewById(R.id.tv_money);
        tvMoney.setText(money);
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
                linstenrText.onWjPassword();
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
    public static void deleteBankDialog(Context context, final ButtonCallback buttonCallback){
        final Dialog dialog = new Dialog(context, R.style.Dialog);
        dialog.setCanceledOnTouchOutside(true);
        View view = View.inflate(context,R.layout.dialog_delete_bank,null);
        TextView tvDelete= (TextView)view.findViewById(R.id.tv_delete);
        TextView tvCannel  = (TextView)view.findViewById(R.id.tv_cancel_dialog);
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
        dialog.setCanceledOnTouchOutside(false);
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
}
