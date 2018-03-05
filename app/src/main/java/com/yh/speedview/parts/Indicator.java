package com.yh.speedview.parts;

import android.graphics.Paint;
import android.graphics.Path;

import com.yh.speedview.listener.SafeRangeListener;


/**
 * 表盘指针基类
 */
public abstract class Indicator extends Parts {

    /**
     * 指针的路径
     */
    protected Path mIndicatorPath;

    public Indicator () {
    }

    protected SafeRangeListener mSafeRangeListener;

    public void setSafeRangeListener (SafeRangeListener listener) {
        mSafeRangeListener = listener;
    }

    @Override
    protected void initialize () {
        super.initialize ();

        mIndicatorPath = new Path ();
        mPaint.setStyle (Paint.Style.FILL);
    }
}
