package com.yh.speedview.utils;

import android.content.Context;
import android.content.res.Resources;
import android.util.DisplayMetrics;
import android.util.TypedValue;

/**
 * 工具类,使用前要先通过{@link #init(Context)}实例化
 */
public class Utils {

    private static Resources mResources;
    private static DisplayMetrics mDisplayMetrics;

    public static void init (Context context) {
        if (context == null)
            throw new IllegalArgumentException (Utils.class.getName () + "Cannot be instantiated with null context");
        mResources = context.getResources ();
        mDisplayMetrics = mResources.getDisplayMetrics ();
    }

    /**
     * 此方法用于将dp转换为对等的pixels
     *
     * @param value 以dp为单位的值
     * @return 表示pixels的浮点值，该值与dp等效，取决于设备密度。
     */
    public static float convertDpToPixel (int value) {
        return TypedValue.applyDimension (TypedValue.COMPLEX_UNIT_DIP, value, mDisplayMetrics);
    }

    /**
     * 小数点后如果是0则只显示整数部分，否则保留
     *
     * @param value 被转换的浮点值
     * @return 转换后的字符串值
     */
    public static String convert (float value) {
        if (Math.round (value) - value == 0)
            return String.valueOf ((int) value);
        return String.valueOf (value);
    }
}
