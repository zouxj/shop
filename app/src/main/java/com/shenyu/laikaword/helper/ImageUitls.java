package com.shenyu.laikaword.helper;

import android.widget.ImageView;

import com.shenyu.laikaword.R;
import com.shenyu.laikaword.common.CircleTransform;
import com.squareup.picasso.Picasso;
import com.zxj.utilslibrary.utils.StringUtil;
import com.zxj.utilslibrary.utils.UIUtil;

/**
 * Created by shenyu_zxjCode on 2017/10/27 0027.
 * Picasso图片加载帮助类
 */

public final  class ImageUitls {

    public synchronized   static void loadImg(String url,ImageView imgView){
        if (!StringUtil.validText(url))
            url = null;
        Picasso.with(UIUtil.getContext()).load(url).placeholder(R.mipmap.defaul_icon).error(R.mipmap.defaul_icon).into(imgView);

    }
    public synchronized static void  loadImgRound(String url,ImageView imgView){
        if (!StringUtil.validText(url))
            url = null;
        Picasso.with(UIUtil.getContext()).load(url).placeholder(R.mipmap.left_user_icon)
                .error(R.mipmap.left_user_icon).resize(50, 50).transform(new CircleTransform()).into(imgView);
    }
    public synchronized static void  loadImgRound(String url,ImageView imgView,int value){
        if (!StringUtil.validText(url))
            url = null;
        Picasso.with(UIUtil.getContext()).load(url).placeholder(value)
                .error(value).resize(50, 50).into(imgView);
    }

}
