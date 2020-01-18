package com.addkey.keylibrary.utils;

import android.content.Context;
import android.content.res.Resources;
import android.util.TypedValue;

import java.text.DecimalFormat;

public class DoubleUtil {
    public static boolean isEqual(double d1, double d2) {
        return Math.abs(d1 - d2) < 0.0001;
    }

    public static boolean isBigger(double d1, double d2) {
        return d1 - d2 > 0.0001;
    }

    public static boolean isSmaller(double d1, double d2) {
        return d1 - d2 < -0.0001;
    }

    public static boolean isBiggerOrEqual(double d1, double d2) {
        return isEqual(d1, d2) || isBigger(d1, d2);
    }

    public static boolean isSmallrerOrEqual(double d1, double d2) {
        return isEqual(d1, d2) || isSmaller(d1, d2);
    }

    public static String format(Double d) {
        DecimalFormat df = new DecimalFormat("#####0.00");
        return df.format(d);
    }

    /**
     * 保留两位
     * @param data
     * @return
     */
    public static String to2Double(double data){
        DecimalFormat decimalFormat = new DecimalFormat("#.##");
        return decimalFormat.format(data);

    }

}
