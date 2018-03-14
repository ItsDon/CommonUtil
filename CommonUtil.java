package com.rrceo.android.utils;

/**
 * Created by itsdon on 17/4/13.
 */

import android.app.Activity;
import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.ContentResolver;
import android.content.Context;
import android.content.res.Configuration;
import android.database.Cursor;
import android.location.LocationManager;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.SoundPool;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;

import com.easybike.util.LogUtil;
import com.wlcxbj.bike.R;

import java.util.List;
import java.util.Locale;

import static android.content.Context.ACTIVITY_SERVICE;

/**
 * 常用工具类
 */
public class CommonUtil {


    /**
     * 获得手机屏幕宽度
     *
     * @param context
     * @return
     */
    public static int getScreenWidth(Context context) {
        return context.getResources().getDisplayMetrics().widthPixels;
    }

    /**
     * 获得手机屏幕高度
     *
     * @param context
     * @return
     */
    public static int getScreenHeight(Context context) {
        return context.getResources().getDisplayMetrics().heightPixels;
    }

    /**
     * dp转px
     *
     * @param context
     * @param dpValue
     * @return
     */
    public static int dp2px(Context context, int dpValue) {
        float scale = context.getResources().getDisplayMetrics().density;
        return (int) (scale * dpValue + 0.5f);
    }

    /**
     * px转dp
     *
     * @param context
     * @param pxValue
     * @return
     */
    public static int px2dp(Context context, int pxValue) {
        float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    /**
     * sp转px
     *
     * @param context
     * @param spValue
     * @return
     */
    public static int sp2px(Context context, int spValue) {
        float scale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (spValue * scale + 0.5f);
    }

    /**
     * px转sp
     *
     * @param context
     * @param pxValue
     * @return
     */
    public static int px2sp(Context context, int pxValue) {
        float scale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (pxValue / scale + 0.5f);
    }

    /**
     * 判断GPS是否开启
     *
     * @param context
     * @return
     */
    public static boolean isGPSOpen(Context context) {
        LocationManager locationManager = (LocationManager) context
                .getSystemService(Context.LOCATION_SERVICE);
        return locationManager.isProviderEnabled(android.location.LocationManager.GPS_PROVIDER);
    }

    /**
     * 保持当前屏幕常亮
     *
     * @param context
     */
    public static void keepScrrenOn(Activity context) {
        context.getWindow().addFlags(
                WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD |

                        WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON |

                        WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
    }

    /**
     * 弹出软键盘
     *
     * @param view
     */
    public static void openSoftInput(View view) {
        InputMethodManager inputManager =
                (InputMethodManager) view.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        inputManager.showSoftInput(view, 0);
    }


    /**
     * 收到推送时播放提示音
     *
     * @param context
     */
    public static void playSound(Context context) {
        SoundPool soundPool;
        LogUtil.e(TAG, "Build.VERSION.SDK_INT >= 21" + (Build.VERSION.SDK_INT >= 21));
        if (Build.VERSION.SDK_INT >= 21) {
            SoundPool.Builder builder = new SoundPool.Builder();
            //传入音频的数量
            builder.setMaxStreams(1);
            //AudioAttributes是一个封装音频各种属性的类
            AudioAttributes.Builder attrBuilder = new AudioAttributes.Builder();
            //设置音频流的合适属性
            attrBuilder.setLegacyStreamType(AudioManager.STREAM_MUSIC);
            builder.setAudioAttributes(attrBuilder.build());
            soundPool = builder.build();
        } else {
            //第一个参数是可以支持的声音数量，第二个是声音类型，第三个是声音品质
            soundPool = new SoundPool(1, AudioManager.STREAM_SYSTEM, 5);
        }
        //第一个参数Context,第二个参数资源Id，第三个参数优先级
        final int soundId = soundPool.load(context, R.raw.receive_push, 1);
        soundPool.setOnLoadCompleteListener(new SoundPool.OnLoadCompleteListener() {
            @Override
            public void onLoadComplete(SoundPool soundPool, int sampleId, int status) {
                soundPool.play(soundId, 1, 1, 0, 0, 1);
            }
        });
    }

    /**
     * 根据Uri获取图片的路径
     *
     * @param uri
     * @return
     */
    public static String getPicPath(Context context, Uri uri) {
        if (uri.getScheme().equals("content")) { // 从相册获取
            String[] s = {MediaStore.Images.Media.DATA};
            ContentResolver cr = context.getContentResolver();
            Cursor cursor = cr.query(uri, s, null, null, null);
            int index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            Log.v("TAG", "选中的图片的路径是：" + cursor.getString(index));
            return cursor.getString(index);

        } else if (uri.getScheme().equals("file")) { // 拍照
            Log.v("TAG", "选中的图片的路径是：" + uri.getPath());
            return uri.getPath();
        }
        return null;
    }

    /**
     * 自定义app语言
     *
     * @param locale
     * @param context
     */
    public static void setAppLanguage(Locale locale, Context context) {
        if (locale == null) {
            return;
        }
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.locale = locale;
        context.getApplicationContext().getResources().updateConfiguration(config, null);
    }

    /**
     * 验证字符窜是否都是数字
     *
     * @param str
     * @return
     */
    public static boolean isNumeric(String str) {
        for (int i = 0; i < str.length(); i++) {
            System.out.println(str.charAt(i));
            if (!Character.isDigit(str.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    /**
     * 判断当前应用是否在前台
     *
     * @param context
     * @return
     */
    public static boolean isApplicationForegroud(Context context) {
        ActivityManager am = (ActivityManager) context.getSystemService(ACTIVITY_SERVICE);
        List<ActivityManager.RunningAppProcessInfo> runnings = am.getRunningAppProcesses();
        for (ActivityManager.RunningAppProcessInfo running : runnings) {
            if (running.processName.equals(context.getPackageName())) {
                if (running.importance == ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND
                        || running.importance == ActivityManager.RunningAppProcessInfo.IMPORTANCE_VISIBLE) {
                    return true;
                } else {
                    return false;
                }
            }
        }
        return false;
    }


    /**
     * 将app从后台唤醒
     *
     * @param activity
     */
    public static void activeApplicationToForeground(Activity activity) {
        ActivityManager am = (ActivityManager) activity.getSystemService(Activity.ACTIVITY_SERVICE);
        am.moveTaskToFront(activity.getTaskId(), 0);
    }

    /**
     * 获取设备唯一标识deviceId
     *
     * @param context
     * @return
     */
    public static String getDeviceId(Context context) {
        TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context
                .TELEPHONY_SERVICE);
        String deviceId = telephonyManager.getDeviceId();
        return deviceId;
    }


}
