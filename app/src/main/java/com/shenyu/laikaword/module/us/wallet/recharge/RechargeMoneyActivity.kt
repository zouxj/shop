package com.shenyu.laikaword.module.us.wallet.recharge

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Context
import android.os.Build
import android.view.View
import android.widget.EditText
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.TextView

import com.jakewharton.rxbinding.widget.RxTextView
import com.shenyu.laikaword.R
import com.shenyu.laikaword.base.LKWordBaseActivity
import com.shenyu.laikaword.common.Constants
import com.shenyu.laikaword.helper.DialogHelper
import com.shenyu.laikaword.model.bean.reponse.PayInfoReponse
import com.shenyu.laikaword.helper.PayHelper
import com.shenyu.laikaword.model.net.api.ApiCallback
import com.shenyu.laikaword.model.net.retrofit.RetrofitUtils
import com.shenyu.laikaword.model.rxjava.rxbus.RxBus
import com.shenyu.laikaword.model.rxjava.rxbus.event.Event
import com.shenyu.laikaword.model.rxjava.rxbus.event.EventType
import com.shenyu.laikaword.module.us.appsetting.acountbind.BoundPhoneActivity
import com.zxj.parlibary.resultlistener.QqPayListener
import com.zxj.utilslibrary.utils.IntentLauncher
import com.zxj.utilslibrary.utils.StringUtil
import com.zxj.utilslibrary.utils.ToastUtil
import com.zxj.utilslibrary.utils.UIUtil

import butterknife.BindView
import butterknife.OnClick

/**
 * 充值
 */
class RechargeMoneyActivity : LKWordBaseActivity() {
    @BindView(R.id.recharge_rb_num)
    internal var rechargeRbNum: EditText? = null
    @BindView(R.id.recharge_rb_alipay)
    internal var rechargeRbAlipay: RadioButton? = null
    @BindView(R.id.recharge_rb_qqpay)
    internal var rechargeRbQqpay: RadioButton? = null
    @BindView(R.id.recharge_tv_next)
    internal var rechargeTvNext: TextView? = null
    @BindView(R.id.recharge_rg)
    internal var radioGroup: RadioGroup? = null
    private var type = 3
    override fun bindLayout(): Int {
        return R.layout.activity_recharge_money
    }

    override fun initView() {
        setToolBarTitle("余额充值")
        radioGroup!!.setOnCheckedChangeListener { radioGroup, positon ->
            when (positon) {
                R.id.recharge_rb_alipay ->
                    //TODO 阿里支付
                    type = 3
                R.id.recharge_rb_qqpay ->
                    //TODO qq支付
                    type = 2
            }
        }
    }

    @OnClick(R.id.recharge_tv_next)
    fun onClick(view: View) {
        when (view.id) {
            R.id.recharge_tv_next -> {
                val loginReponse = Constants.getLoginReponse()
                if (null != loginReponse) {
                    if (!StringUtil.validText(loginReponse.payload.bindPhone)) {
                        //第一步查看有没有绑定手机
                        DialogHelper.makeUpdate(mActivity, "温馨提示", "你尚未绑定手机号码!请前往绑定?", "取消", "去绑定", false, object : DialogHelper.ButtonCallback {
                            override fun onNegative(dialog: Dialog) {
                                IntentLauncher.with(mActivity).launch(BoundPhoneActivity::class.java)

                            }

                            override fun onPositive(dialog: Dialog) {

                            }
                        }).show()
                        return
                    }

                }
                if (type == 3) {
                    retrofitUtils.addSubscription(RetrofitUtils.apiStores.rechargeMoney(rechargeRbNum!!.text.toString().trim { it <= ' ' }, type), object : ApiCallback<PayInfoReponse>() {
                        override fun onSuccess(model: PayInfoReponse) {
                            PayHelper.aliPaySafely(model.payload.payInfo, this@RechargeMoneyActivity) { resultInfo ->

                                if (resultInfo == "9000") {
                                    RxBus.getDefault().post(Event(EventType.ACTION_UPDATA_USER_REQUEST, null))
                                    IntentLauncher.with(this@RechargeMoneyActivity).put("pay_type", type.toString() + "").put("money", rechargeRbNum!!.text.toString().trim { it <= ' ' }).launchFinishCpresent(RechargeSuccessActivity::class.java)
                                } else if (resultInfo == "8000")
                                    ToastUtil.showToastShort("支付失败")
                                else if (resultInfo == "6001")
                                    ToastUtil.showToastShort("支付取消")
                                else
                                    ToastUtil.showToastShort(resultInfo)
                            }
                        }

                        override fun onFailure(msg: String) {
                            ToastUtil.showToastShort(msg)
                        }

                        override fun onFinish() {

                        }
                    })

                } else {
                    //TODO qq支付
                    PayHelper.qqPay(this, object : QqPayListener {
                        override fun onPaySuccess(successCode: Int) {

                        }

                        override fun onPayFailure(errorCode: Int) {

                        }
                    })
                    ////
                }
            }
        }//
    }

    override fun doBusiness(context: Context) {
        RxTextView.textChanges(rechargeRbNum!!).subscribe { charSequence ->
            if (null != charSequence.toString().trim { it <= ' ' } && charSequence.toString().trim { it <= ' ' }.length > 0) {
                rechargeTvNext!!.isEnabled = true
                if (Build.VERSION.SDK_INT > Build.VERSION_CODES.ICE_CREAM_SANDWICH_MR1)
                    rechargeTvNext!!.background = UIUtil.getDrawable(R.drawable.bg_bt_login_rectangle_light)
                else
                    rechargeTvNext!!.setBackgroundResource(R.drawable.bg_bt_login_rectangle_light)
            } else {
                rechargeTvNext!!.isEnabled = false
                if (Build.VERSION.SDK_INT > Build.VERSION_CODES.ICE_CREAM_SANDWICH_MR1)
                    rechargeTvNext!!.background = UIUtil.getDrawable(R.drawable.bg_gray_icon)
                else
                    rechargeTvNext!!.setBackgroundResource(R.drawable.bg_gray_icon)

            }
        }
    }

    override fun setupActivityComponent() {

    }

}
