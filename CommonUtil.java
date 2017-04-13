package com.rrceo.android.utils;

/**
 * Created by itsdon on 17/4/13.
 */

import android.content.Context;

/**
 *  常用工具类
 */
public class CommonUtil {


    /**
     *  获得手机屏幕宽度
     * @param context
     * @return
     */
    public static int  getScreenWidth(Context context){
        return context.getResources().getDisplayMetrics().widthPixels;
    }

    /**
     * 获得手机屏幕高度
     * @param context
     * @return
     */
    public static int getScreenHeight(Context context){
        return context.getResources().getDisplayMetrics().heightPixels;
    }


}
