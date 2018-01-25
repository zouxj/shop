package com.shenyu.laikaword.module.us.resell.ui.activity;

import android.app.Dialog;
import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.jakewharton.rxbinding.widget.RxTextView;
import com.shenyu.laikaword.R;
import com.shenyu.laikaword.base.LKWordBaseActivity;
import com.shenyu.laikaword.di.module.mine.BindAccountModule;
import com.shenyu.laikaword.di.module.mine.MineModule;
import com.shenyu.laikaword.helper.DialogHelper;
import com.shenyu.laikaword.helper.RecycleViewDivider;
import com.shenyu.laikaword.model.adapter.CommonAdapter;
import com.shenyu.laikaword.model.adapter.MultiItemTypeAdapter;
import com.shenyu.laikaword.model.bean.reponse.BaseReponse;
import com.shenyu.laikaword.model.holder.ViewHolder;
import com.shenyu.laikaword.model.itemviewdelegeate.HomeLeftItemViewDelegate;
import com.shenyu.laikaword.model.itemviewdelegeate.HomeLeftLastItemViewDelegate;
import com.shenyu.laikaword.model.itemviewdelegeate.ResellInputCodeViewDelegate;
import com.shenyu.laikaword.model.itemviewdelegeate.ResellShowCodeViewDelegate;
import com.shenyu.laikaword.module.launch.LaiKaApplication;
import com.shenyu.laikaword.module.us.resell.presenter.ResellInputCodePresenter;
import com.shenyu.laikaword.module.us.resell.view.ResellInputCodeView;
import com.zxj.utilslibrary.utils.KeyBoardUtil;
import com.zxj.utilslibrary.utils.ToastUtil;
import com.zxj.utilslibrary.utils.UIUtil;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import butterknife.BindView;
import butterknife.OnClick;
import butterknife.OnItemClick;
import rx.functions.Action1;

public class ResellInputCodeActivity extends LKWordBaseActivity implements ResellInputCodeView {
    @BindView(R.id.et_input_code)
    EditText etInputCode;
    @BindView(R.id.ry_code)
    RecyclerView ryCode;
    @BindView(R.id.tv_tishi)
    TextView tvTishi;
    Set<String> listset;
    List<String> list;
    CommonAdapter commonAdapter;
    @Override
    public int bindLayout() {
        return R.layout.activity_resell_input_code;
    }

    @Override
    public void initView() {
        ryCode.addItemDecoration(new RecycleViewDivider(this, LinearLayoutManager.HORIZONTAL,(int) UIUtil.dp2px(1),UIUtil.getColor(R.color.main_bg_gray)));
        ryCode.setLayoutManager(new LinearLayoutManager(this));

        list = new ArrayList<>();
        listset = new HashSet<String>(list);
        list.add("");
        commonAdapter =new CommonAdapter<String>(R.layout.item_resell_input_code,list) {
            @Override
            protected void convert(ViewHolder holder, String o, final int position) {
                final EditText editText =holder.getView(R.id.et_input);
                editText.setText(o);
                if (position==0){
                    holder.getView(R.id.tv_title).setVisibility(View.VISIBLE);
                }
                else {
                    holder.getView(R.id.tv_title).setVisibility(View.INVISIBLE);
                    editText.setFocusable(false);
                }
                RxTextView.textChanges(editText).subscribe(new Action1<CharSequence>() {
                    @Override
                    public void call(CharSequence charSequence) {
                        if (charSequence.toString().length()>=16)
                        {
                            editText.setFocusable(false);
                            if (listset.add(charSequence.toString().trim())){
                                list.clear();
                                list.add(charSequence.toString().trim());
                                notifyDataSetChanged();
                            }

                            KeyBoardUtil.heideSoftInput(mActivity);
                        }
                    }
                });
                holder.setOnClickListener(R.id.tv_del, new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (getItemCount()==1&&position==0){
                            editText.setText("");
                            editText.setFocusable(true);
                            KeyBoardUtil.showSoftInput(editText);
                        }else {
                            list.remove(position);
                            notifyDataSetChanged();
                        }
                    }
                });
            }


        };
        ryCode.setAdapter(commonAdapter);
    }

    @OnClick({R.id.tv_add_code,R.id.tv_zhuamai})
    public  void  onClick(View view){
        switch (view.getId()){
            case R.id.tv_add_code:
                //TODO 添加兑换码
                DialogHelper.inuputGoodsCode(mActivity, new DialogHelper.InputInterfaceGoodCode() {
                    @Override
                    public void onLintenerText(Dialog dialog, String passWord) {
                        if (passWord.length()>=16){
                            if (listset.add(passWord)){
                                list.add(passWord);
                                commonAdapter.notifyDataSetChanged();
                                dialog.dismiss();
                            }else {
                                ToastUtil.showToastShort("不能输入相同的兑换码");

                            }


                        }else {
                            ToastUtil.showToastShort("请输入正确的兑换码");

                        }
                    }
                }).show();
                break;
            case R.id.tv_zhuamai:
                //TODO 转卖
                break;
        }

    }
    public  boolean isSlideToBottom(RecyclerView recyclerView) {
        if (recyclerView == null) return false;
        if (recyclerView.computeVerticalScrollExtent()
                >=recyclerView.getHeight())
            return true;
        return false;
    }



    @Override
    public void doBusiness(Context context) {

    }

    @Override
    public void setupActivityComponent() {
        LaiKaApplication.get(this).getAppComponent().plus(new MineModule(this)).inject(this);
    }

    @Override
    public void isLoading() {

    }

    @Override
    public void loadSucceed(BaseReponse baseReponse) {

    }

    @Override
    public void loadFailure() {

    }
}
