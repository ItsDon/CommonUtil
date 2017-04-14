package com.rrceo.android.utils;

/**
 * Created by itsdon on 17/4/13.
 */

import android.content.ComponentName;
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

    /**
     *   dp转px
     * @param context
     * @param dpValue
     * @return
     */
    public static int dp2px(Context context,int dpValue){
        float scale = context.getResources().getDisplayMetrics().density;
        return (int)(scale * dpValue + 0.5f);
    }

    /**
     *   px转dp
     * @param context
     * @param pxValue
     * @return
     */
    public static int px2dp(Context context,int pxValue){
        float scale = context.getResources().getDisplayMetrics().density;
        return (int)(pxValue / scale + 0.5f);
    }

    /**
     *   sp转px
     * @param context
     * @param spValue
     * @return
     */
    public static int sp2px(Context context,int spValue){
        float scale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int)(spValue * scale + 0.5f);
    }

    /**
     *   px转sp
     * @param context
     * @param pxValue
     * @return
     */
    public static int px2sp(Context context,int pxValue){
        float scale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int)(pxValue / scale + 0.5f);
    }


}
