package com.shenyu.laikaword.module.mine.address.activity;

import android.content.Context;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import com.shenyu.laikaword.R;
import com.shenyu.laikaword.base.LKWordBaseActivity;
import com.shenyu.laikaword.bean.BaseReponse;
import com.shenyu.laikaword.bean.reponse.AddressReponse;
import com.shenyu.laikaword.helper.CityDataHelper;
import com.shenyu.laikaword.interfaces.IOptionPickerVierCallBack;
import com.shenyu.laikaword.retrofit.ApiCallback;
import com.shenyu.laikaword.retrofit.RetrofitUtils;
import com.shenyu.laikaword.rxbus.event.Event;
import com.shenyu.laikaword.rxbus.event.EventType;
import com.shenyu.laikaword.rxbus.RxBus;
import com.shenyu.laikaword.widget.loaddialog.LoadingDialog;
import com.zxj.utilslibrary.utils.KeyBoardUtil;
import com.zxj.utilslibrary.utils.StringUtil;
import com.zxj.utilslibrary.utils.ToastUtil;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

public class EditAddressActivity extends LKWordBaseActivity {

    @BindView(R.id.edit_name)
    EditText editName;
    @BindView(R.id.edit_tel)
    TextView editTel;
    @BindView(R.id.edite_address)
    TextView editeAddress;
    @BindView(R.id.edit_address_detail)
    EditText editAddressDetail;
    @BindView(R.id.cb_select_address)
    CheckBox cbSelectAddress;

    String useName;
    String tel;
    String address;
    String addressDetail;
    CityDataHelper cityDataHelper;
    Map<String,String> mapParam;
    LoadingDialog ld;
    AddressReponse.PayloadBean payloadBean;
    @Override
    public int bindLayout() {
        return R.layout.activity_edit_address;
    }

    @Override
    public void initView() {
        setToolBarTitle("编辑地址");
    }

    @Override
    public void doBusiness(Context context) {
        mapParam = new HashMap<>();
        String type = getIntent().getStringExtra("Type");

        if (null!=type&&type.equals("ADD")){
            setToolBarTitle("地址信息");
        }
        if (getIntent()!=null)
            payloadBean = getIntent().getParcelableExtra("AddressInfo");





        useName= editName.getText().toString().trim();
        tel= editTel.getText().toString().trim();
        address= editeAddress.getText().toString().trim();
        addressDetail= editAddressDetail.getText().toString().trim();
        cityDataHelper =new  CityDataHelper(this);
         ld = new LoadingDialog(this);
        ld.setLoadingText("添加中")
                .setSuccessText("添加成功")//显示加载成功时的文字
                .setFailedText("添加失败");
        if (payloadBean!=null){
            editName.setText(payloadBean.getReceiveName());
            editTel.setText(payloadBean.getPhone());
            editeAddress.setText(payloadBean.getProvince()+payloadBean.getCity());
            editAddressDetail.setText(payloadBean.getDetail());
            cbSelectAddress.setChecked(payloadBean.getDefaultX()==1);
            mapParam.put("addressId",payloadBean.getAddressId());
            mapParam.put("province",payloadBean.getProvince());
            mapParam.put("city",payloadBean.getCity());
            mapParam.put("district",payloadBean.getDetail());
            ld.setLoadingText("编辑中")
                    .setSuccessText("编辑成功")//显示加载成功时的文字
                    .setFailedText("编辑失败");
        }
//在你代码中合适的位置调用反馈


    }

    @Override
    public void setupActivityComponent() {

    }
    @OnClick({R.id.tv_select_address,R.id.cb_select_address,R.id.tv_save})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.tv_select_address:
            //TODO 选择地址
                if (KeyBoardUtil.isActive(this))
                    heideSoftInput();
                cityDataHelper.ShowPickerView(new IOptionPickerVierCallBack() {
                    @Override
                    public void callBack(String shen, String shi, String xian, String msg) {
                        editeAddress.setText(msg);
                        mapParam.put("province",shen);
                        mapParam.put("city",shi);
                        mapParam.put("district",xian);
                    }
                });
                break;
            case R.id.cb_select_address:
                //TODO 设置默认

                break;
            case R.id.tv_save:
                //TODO 设置保存
            if (verifyNULL()){
                    //TODO 上传服务器
                RetrofitUtils.getRetrofitUtils().addSubscription(RetrofitUtils.apiStores.setAddress(mapParam), new ApiCallback<BaseReponse>() {
                    @Override
                    public void onSuccess(BaseReponse model) {
                        if (model.isSuccess()) {
                            ld.loadSuccess();
                            RxBus.getDefault().post(new Event(EventType.ACTION_UPDATA_USER_ADDRESS,null));
                        }
                        else {
                            ld.loadFailed();
                        }
                    }

                    @Override
                    public void onFailure(String msg) {
                        ld.loadFailed();
                    }

                    @Override
                    public void onFinish() {

                    }

                    @Override
                    public void onStarts() {
                        super.onStarts();
                        ld.show();
                    }
                });
            }else {
                ToastUtil.showToastShort("请完善上面信息");
            }
                break;
        }
    }

    /**
     * 校验空值
     */
    public boolean verifyNULL(){
        useName= editName.getText().toString().trim();
        tel= editTel.getText().toString().trim();
        address= editeAddress.getText().toString().trim();
        addressDetail= editAddressDetail.getText().toString().trim();
        if (StringUtil.validText(useName)&&StringUtil.validText(tel)&&
                StringUtil.validText(address)&&StringUtil.validText(addressDetail)){

            mapParam.put("phone",tel);
            mapParam.put("receiveName",useName);
            mapParam.put("detail",addressDetail);
            mapParam.put("default",(cbSelectAddress.isChecked()?1:0)+"");

            return  true;
        }
        return false;
    }

}
