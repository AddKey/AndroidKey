package com.addkey.keylibrary.utils;

import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.widget.Toast;

/**
 * ToastUtils
 * 
 * @author <a href="http://www.trinea.cn" target="_blank">Trinea</a> 2013-12-9
 */
public class ToastUtils {
    private static Context mContext;
    public static void setContext(Context context){
        mContext = context;
    }
    public static void show( int resId) {
        show(mContext, mContext.getResources().getText(resId), Toast.LENGTH_SHORT);
    }

    public static void show( int resId, int duration) {
        show(mContext, mContext.getResources().getText(resId), duration);
    }

    public static void show( CharSequence text) {
        show(mContext, text, Toast.LENGTH_SHORT);
    }

    public static void show(Context context, CharSequence text, int duration) {
        Toast.makeText(context, text, duration).show();
    }

    /**
     * 自定义toast的view
     * @param text
     * @param duration
     * @param view
     */
    public static void show(CharSequence text, int duration,View view) {
        Toast toast = Toast.makeText(mContext, text, duration);
        toast.setView(view);
        toast.show();
    }

    /**
     * 自定义toast的view,并设置toast的弹出位置
     * @param text
     * @param duration
     * @param view
     * @param gravity
     */
    public static void show( CharSequence text, int duration,View view,int gravity) {
        Toast toast = Toast.makeText(mContext, text, duration);
        toast.setView(view);
        toast.setGravity(gravity,0,50);
        toast.show();
    }
//    public static void show(int resId, Object... args) {
//        show(mContext, String.format(mContext.getResources().getString(resId), args), Toast.LENGTH_SHORT);
//    }

//    public static void show( String format, Object... args) {
//        show(mContext, String.format(format, args), Toast.LENGTH_SHORT);
//    }

    public static void show( int resId, int duration, Object... args) {
        show(mContext, String.format(mContext.getResources().getString(resId), args), duration);
    }

    public static void show( String format, int duration, Object... args) {
        show(mContext, String.format(format, args), duration);
    }

}
