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
import com.shenyu.laikaword.model.bean.reponse.SellInfoReponse;
import com.shenyu.laikaword.model.holder.ViewHolder;
import com.shenyu.laikaword.model.itemviewdelegeate.HomeLeftItemViewDelegate;
import com.shenyu.laikaword.model.itemviewdelegeate.HomeLeftLastItemViewDelegate;
import com.shenyu.laikaword.model.itemviewdelegeate.ResellInputCodeViewDelegate;
import com.shenyu.laikaword.model.itemviewdelegeate.ResellShowCodeViewDelegate;
import com.shenyu.laikaword.module.launch.LaiKaApplication;
import com.shenyu.laikaword.module.us.resell.presenter.ResellInputCodePresenter;
import com.shenyu.laikaword.module.us.resell.view.ResellInputCodeView;
import com.zxj.utilslibrary.utils.IntentLauncher;
import com.zxj.utilslibrary.utils.KeyBoardUtil;
import com.zxj.utilslibrary.utils.StringUtil;
import com.zxj.utilslibrary.utils.ToastUtil;
import com.zxj.utilslibrary.utils.UIUtil;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.inject.Inject;

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
    @Inject
    ResellInputCodePresenter resellInputCodePresenter;
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
            protected void convert(final ViewHolder holder, String o, final int position) {
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
                        if (position==0){
                            if (StringUtil.validText(charSequence.toString().trim())){
                                holder.getView(R.id.tv_del).setVisibility(View.VISIBLE);
                            }else {
                                holder.getView(R.id.tv_del).setVisibility(View.GONE);
                            }
                        }
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
    StringBuffer stringBuffer;
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
                            }else if (list.size()==10){
                                ToastUtil.toS(mActivity,"兑换码1次最多输入10个");
                            }
                            else {
                                ToastUtil.toS(mActivity,"已存在该兑换码，请重新输入");

                            }
                        }else {
                            ToastUtil.toS(mActivity,"请输入正确的兑换码");

                        }
                    }
                }).show();
                break;
            case R.id.tv_zhuamai:
                //TODO 转卖
                 stringBuffer = new StringBuffer();
                for (int i=0;i<list.size();i++){
                    if (i==list.size()-1){
                        stringBuffer.append(list.get(i));
                    }else {
                        stringBuffer.append(list.get(i)+",");
                    }
                }
                if (StringUtil.validText(etInputCode.getText().toString().trim()))
                    resellInputCodePresenter.sellInfo(bindToLifecycle(),stringBuffer.toString(),etInputCode.getText().toString().trim());
                else {
                ToastUtil.showToastShort("请输入用户编号");
            }
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
        LaiKaApplication.get(this).getAppComponent().plus(new MineModule(this,this)).inject(this);
    }

    @Override
    public void isLoading() {

    }
    private  SellInfoReponse sellInfoReponse;
    @Override
    public void loadSucceed(BaseReponse baseReponse) {
        if (baseReponse instanceof SellInfoReponse){
            sellInfoReponse = (SellInfoReponse) baseReponse;
        }
        if (sellInfoReponse.isSuccess()){
            if (sellInfoReponse.getPayload()!=null){
                IntentLauncher.with(this).put("cdkeys",stringBuffer.toString()).putObjectString("resellInfo",sellInfoReponse).launch(CommitResellActivity.class);
            }
        }

    }

    @Override
    public void loadFailure() {

    }
}
