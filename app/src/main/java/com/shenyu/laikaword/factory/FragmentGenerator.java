package com.shenyu.laikaword.factory;

/**
 * Created by shenyu_zxjCode on 2017/9/15 0015.
 */


import android.support.v4.app.Fragment;

import com.shenyu.laikaword.base.IKWordBaseFragment;
import com.shenyu.laikaword.common.Constants;
import com.shenyu.laikaword.main.fragment.MainFragment;
import com.shenyu.laikaword.module.mine.cards.fragment.CarListFragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * framgent 工程模式
 */
public class FragmentGenerator {

static Map<Integer,android.support.v4.app.Fragment> fragmentHashMap = new HashMap<>();
    public static Fragment generatorFragment(int flog){
        Fragment fragment = fragmentHashMap.get(flog);
        if (null ==fragment) {
            switch (flog) {
                //主页面的frament
                case Constants.FRAGEMTN_MAIN_FLOG:
                    fragment = new MainFragment();
                    fragmentHashMap.put(Constants.FRAGEMTN_MAIN_FLOG, fragment);
                    break;
                //卡包的Framgent
                case Constants.FRAGEMTN_CARPACK_FLOG:
                    fragment = new CarListFragment();
                    fragmentHashMap.put(Constants.FRAGEMTN_CARPACK_FLOG, fragment);
                    break;
            }
        }
        return fragment;
    }


}
