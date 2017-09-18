package com.shenyu.laikaword.module.mine.cards;

import android.widget.EditText;
import android.widget.TextView;

import com.jakewharton.rxbinding.widget.RxTextView;
import com.shenyu.laikaword.helper.SendMsgHelper;
import com.shenyu.laikaword.module.mine.cards.fragment.AddBankCardActivity;
import com.zxj.utilslibrary.utils.StringUtil;
import com.zxj.utilslibrary.utils.ToastUtil;

import rx.Observable;
import rx.Subscriber;
import rx.functions.Func2;

/**
 * Created by shenyu_zxjCode on 2017/9/18 0018.
 */

public class AddBankPresenter {

    AddBankView mAddBankView;
    public AddBankPresenter(AddBankView addBankView){
        this.mAddBankView=addBankView;
    }
    public void  setMonitor(EditText name,EditText bankNum){
        Observable<CharSequence> ObservableCardNum= RxTextView.textChanges(name);
        Observable<CharSequence> ObservableUsrName = RxTextView.textChanges(bankNum);
        Observable.combineLatest(ObservableCardNum, ObservableUsrName, new Func2<CharSequence, CharSequence, Boolean>() {
            @Override
            public Boolean call(CharSequence cardNum, CharSequence name) {
                return verifEditeText(cardNum.toString(),name.toString());
            }
        }).subscribe(new Subscriber<Boolean>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(Boolean verify) {
                if (verify) {
                    //TODO 请求
                    mAddBankView.setBankName("已经获取到了银行名字");
                }else{

                }
            }
        });
    }
    //发送短信
    public void sendMsg(String phone, TextView textView){
        //TODO 请求获取到短信后
        if (!StringUtil.isTelNumber(phone))

        SendMsgHelper.sendMsg(textView,phone);
//        mAddBankView.setMsgCode(phone);
    }
    //请求添加到银行卡信息
    public void setAddRequest(){
        //TODO 添加银行卡信息

    }
    //验证输入
    private  boolean verifEditeText(String nameEditText,String cardNumEditText){
        return StringUtil.checkBankCard(cardNumEditText) &&StringUtil.validText(nameEditText);
    }

}
