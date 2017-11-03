package com.shenyu.laikaword.common;

import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Paint;

import com.squareup.picasso.Transformation;
import com.zxj.utilslibrary.utils.ImageUtil;
import com.zxj.utilslibrary.utils.UIUtil;

/**
 * Created by shenyu_zxjCode on 2017/9/29 0029.
 */

public class CircleTransform implements Transformation {
    /**
     * @param source :还未处理的矩形的Bitmap对象
     * @return ：返回的是处理后的圆形Bitmap对象
     */
    @Override
    public Bitmap transform(Bitmap source) {
        //1.压缩处理
        Bitmap zoomBitmp = ImageUtil.compressImage(source,100);
        //2.圆形处理
        Bitmap bitmap = ImageUtil.circleBitmap(zoomBitmp);
        //必须要回收source，否则会报错
        source.recycle();
        return bitmap;//返回圆形的Bitmap对象
    }

    /**
     * 该方法没有什么实际意义，但是要保证其返回的值不能为null！
     * @return
     */
    @Override
    public String key() {
        return "";
    }


}
