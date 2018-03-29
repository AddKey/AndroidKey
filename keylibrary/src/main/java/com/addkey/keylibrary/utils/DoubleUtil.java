package com.addkey.keylibrary.utils;

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
}
