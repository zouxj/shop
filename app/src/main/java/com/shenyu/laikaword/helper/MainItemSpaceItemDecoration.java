package com.shenyu.laikaword.helper;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by shenyu_zxjCode on 2017/9/18 0018.
 */

public class MainItemSpaceItemDecoration extends RecyclerView.ItemDecoration {
    private  int space;

    public MainItemSpaceItemDecoration(int space){
        this.space=space;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        if (parent.getChildLayoutPosition(view)!=0){
            outRect.top=space;
            outRect.left=space/2;
            outRect.right=space/2;
        }else if (parent.getChildLayoutPosition(view)==0){
            outRect.top=space;
            outRect.left=space/2;
            outRect.right=space/2;
        }
        if (parent.getChildLayoutPosition(view)==parent.getChildCount()){
            outRect.bottom=space;
        }
    }
}
