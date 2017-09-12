package com.shenyu.laikaword.module.login.address;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.bigkoo.pickerview.OptionsPickerView;
import com.bigkoo.pickerview.model.IPickerViewData;
import com.google.gson.Gson;
import com.shenyu.laikaword.R;
import com.shenyu.laikaword.base.LKWordBaseActivity;
import com.shenyu.laikaword.bean.JsonBean;
import com.shenyu.laikaword.helper.CityDataHelper;
import com.zxj.utilslibrary.utils.ToastUtil;
import com.zxj.utilslibrary.utils.UIUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONStringer;

import java.util.ArrayList;

import butterknife.OnClick;

public class EditeAddressActivity extends LKWordBaseActivity {
    private CityDataHelper cityDataHelper;

    @Override
    public int bindLayout() {
        return R.layout.activity_edite_address;
    }

    @Override
    public void doBusiness(Context context) {
        cityDataHelper= new CityDataHelper(this);
    }

    @Override
    public void setupActivityComponent() {

    }
    @OnClick({R.id.bt_select_address,R.id.bt_input_pwd})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.bt_select_address:
                cityDataHelper.ShowPickerView();
            break;
            case R.id.bt_input_pwd:
                break;
        }


    }






}
