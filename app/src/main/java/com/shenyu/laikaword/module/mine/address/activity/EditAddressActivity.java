package com.shenyu.laikaword.module.mine.address.activity;

import android.content.Context;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;

import com.shenyu.laikaword.R;
import com.shenyu.laikaword.base.LKWordBaseActivity;
import com.shenyu.laikaword.helper.CityDataHelper;
import com.shenyu.laikaword.interfaces.IOptionPickerVierCallBack;
import com.zxj.utilslibrary.utils.StringUtil;
import com.zxj.utilslibrary.utils.ToastUtil;

import butterknife.BindView;
import butterknife.OnClick;

public class EditAddressActivity extends LKWordBaseActivity {


    @BindView(R.id.edit_name)
    EditText editName;
    @BindView(R.id.edit_tel)
    EditText editTel;
    @BindView(R.id.edite_address)
    EditText editeAddress;
    @BindView(R.id.edit_address_detail)
    EditText editAddressDetail;
    @BindView(R.id.cb_select_address)
    CheckBox cbSelectAddress;

    String useName;
    String tel;
    String address;
    String addressDetail;
    CityDataHelper cityDataHelper;

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
        String type = getIntent().getStringExtra("Type");
        if (null!=type&&type.equals("ADD")){
            setToolBarTitle("地址信息");
        }
        useName= editName.getText().toString().trim();
        tel= editTel.getText().toString().trim();
        address= editeAddress.getText().toString().trim();
        addressDetail= editAddressDetail.getText().toString().trim();
        cityDataHelper =new  CityDataHelper(this);
    }

    @Override
    public void setupActivityComponent() {

    }
    @OnClick({R.id.tv_select_address,R.id.cb_select_address,R.id.tv_save})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.tv_select_address:
            //TODO 选择地址
                cityDataHelper.ShowPickerView(new IOptionPickerVierCallBack() {
                    @Override
                    public void callBack(String shen, String shi, String xian, String msg) {
                        editeAddress.setText(msg);
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
                ToastUtil.showToastShort("上传服务器");
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
            return  true;
        }
        return false;
    }
}
