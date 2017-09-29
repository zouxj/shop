package com.zxj.utilslibrary.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;
import android.util.Base64;

import com.zxj.utilslibrary.AndroidUtilsCore;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.StreamCorruptedException;

/**
 * Created by Administrator on 2017/8/2 0002.
 * SharedPreferences管理类.
 */

public class SPUtil {

    private static SharedPreferences sp;

    private static SharedPreferences getSp() {
        if (sp == null) {
            Context context = AndroidUtilsCore.getContext();
            sp = context.getSharedPreferences(context.getPackageName(), Context.MODE_PRIVATE);
        }
        return sp;
    }

    /**
     * 获取boolean 数据
     */
    public static boolean getBoolean(String key, boolean defValue) {
        return getSp().getBoolean(key, defValue);
    }

    /**
     * 存boolean缓存
     */
    public static void putBoolean(String key, boolean value) {
        getSp().edit().putBoolean(key, value).apply();
    }

    /**
     * 获取String 数据
     */
    public static String getString(String key, String defValue) {
        return getSp().getString(key, defValue);
    }

    /**
     * 存String缓存
     */
    public static void putString(String key, String value) {
        getSp().edit().putString(key, value).apply();
    }





    /**
     * 获取Long 数据
     */
    public static Long getLong(String key, Long defValue) {
        return getSp().getLong(key, defValue);
    }

    /**
     * 存long缓存
     */
    public static void putLong(String key, Long value) {
        getSp().edit().putLong(key, value).apply();
    }


    /**
     * 存int缓存
     */
    public static void putInt(String key, int value) {
        getSp().edit().putInt(key, value).apply();
    }

    /**
     * 取int缓存
     */
    public static int getInt(String key, int defValue) {
        return getSp().getInt(key, defValue);
    }




    /**
     * desc:保存对象
     * @param
     * @param obj 要保存的对象，只能保存实现了serializable的对象
     * modified:
     */
    public static boolean saveObject(String Key,Object obj){
        // TODO Auto-generated method stub
        if (obj == null) {
            getSp().edit().remove(Key).apply();
        }
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ObjectOutputStream oos = null;
        try {
            oos = new ObjectOutputStream(baos);
            oos.writeObject(obj);
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
// 将对象放到OutputStream中
// 将对象转换成byte数组，并将其进行base64编码
        String objectStr = new String(Base64.encode(baos.toByteArray(),
                Base64.DEFAULT));
        try {
            baos.close();
            oos.close();
        } catch (IOException e) {
// TODO Auto-generated catch block
            e.printStackTrace();
        }
        SharedPreferences.Editor editor =  getSp().edit();
// 将编码后的字符串写到base64.xml文件中
        editor.putString(Key, objectStr);
        return editor.commit();
    }
    /**
     * desc:获取保存的Object对象
     * @return
     * modified:
     */
    public static Object readObject(String Key){
        try {
            String wordBase64 = getSp().getString(Key, "");
// 将base64格式字符串还原成byte数组
            if (wordBase64 == null || wordBase64.equals("")) { // 不可少，否则在下面会报java.io.StreamCorruptedException
                return null;
            }
            byte[] objBytes = Base64.decode(wordBase64.getBytes(),
                    Base64.DEFAULT);
            ByteArrayInputStream bais = new ByteArrayInputStream(objBytes);
            ObjectInputStream ois = new ObjectInputStream(bais);
// 将byte数组转换成product对象
            Object obj = ois.readObject();
            bais.close();
            ois.close();
            return obj;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;

    }

}
