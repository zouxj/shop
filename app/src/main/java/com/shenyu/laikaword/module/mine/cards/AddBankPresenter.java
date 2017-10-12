package com.shenyu.laikaword.module.mine.cards;

import android.widget.EditText;
import android.widget.TextView;

import com.jakewharton.rxbinding.widget.RxTextView;
import com.shenyu.laikaword.base.BasePresenter;
import com.shenyu.laikaword.bean.BaseReponse;
import com.shenyu.laikaword.helper.SendMsgHelper;
import com.shenyu.laikaword.retrofit.ApiCallback;
import com.shenyu.laikaword.widget.loaddialog.LoadingDialog;
import com.zxj.utilslibrary.utils.StringUtil;
import com.zxj.utilslibrary.utils.ToastUtil;

import java.util.HashMap;
import java.util.Map;

import rx.Observable;
import rx.Subscriber;
import rx.functions.Func2;

/**
 * Created by shenyu_zxjCode on 2017/9/18 0018.
 */

public class AddBankPresenter extends BasePresenter<AddBankView> {



    public AddBankPresenter(AddBankView addBankView){
        this.mvpView=addBankView;
        attachView(mvpView);
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
                    mvpView.setBankName("已经获取到了银行名字");
                }else{

                }
            }
        });
    }
    //发送短信
    public void sendMsg(String phone, TextView textView){
        //TODO 请求获取到短信后
        if (!StringUtil.isTelNumber(phone))
        SendMsgHelper.sendMsg(textView,phone,"phoneLogin");
    }
    //请求添加到银行卡信息
    public void setAddRequest(String cardNum, String bankName, String bankAddress, String bankPhone, String bankMsgCode, String bankZhangName, String bankUserName, String bankProvince, String bankCity){
        //TODO 添加银行卡信息
        Map<String,String> mapParam = new HashMap<>();
        mapParam.put("name",bankUserName);
        mapParam.put("cardNo",cardNum);
        mapParam.put("SMSCode",bankMsgCode);
        mapParam.put("bankName",bankName);
        mapParam.put("bankDetail",bankZhangName);
        mapParam.put("province",bankProvince);
        mapParam.put("city",bankCity);
        mapParam.put("default","0");
        addSubscription(apiStores.setBankCard(mapParam), new ApiCallback<BaseReponse>() {
            @Override
            public void onSuccess(BaseReponse model) {
                    if (model.isSuccess())
                        mvpView.loadFinished();
                    else
                        ToastUtil.showToastShort(model.getError().getMessage());


            }

            @Override
            public void onStarts() {
                mvpView.isLoading();
            }

            @Override
            public void onFailure(String msg) {
                mvpView.loadFailure();
            }
            @Override
            public void onFinish() {
                mvpView.loadFinished();
            }
        });



    }
    //验证输入
    private  boolean verifEditeText(String nameEditText,String cardNumEditText){
        return StringUtil.checkBankCard(cardNumEditText) &&StringUtil.validText(nameEditText);
    }

}
