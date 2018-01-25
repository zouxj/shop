package com.shenyu.laikaword.model.itemviewdelegeate;

import android.widget.EditText;

import com.jakewharton.rxbinding.widget.RxTextView;
import com.shenyu.laikaword.R;
import com.shenyu.laikaword.model.bean.reponse.ShopMainReponse;
import com.shenyu.laikaword.model.holder.ViewHolder;

import rx.functions.Action1;

public class ResellInputCodeViewDelegate implements ItemViewDelegate<String>  {
    @Override
    public int getItemViewLayoutId() {
        return R.layout.item_resell_input_code;
    }

    @Override
    public boolean isForViewType(String item, int position) {
        return true;
    }

    @Override
    public void convert(final ViewHolder holder, String s, int position) {
//        final EditText editText =(EditText)holder.getView(R.id.et_input);
//        RxTextView.textChanges(editText).subscribe(new Action1<CharSequence>() {
//            @Override
//            public void call(CharSequence charSequence) {
//                if (charSequence.toString().length()>=16){
//                    editText.setFocusable(false);
//                }
//            }
//        });
    }
}
