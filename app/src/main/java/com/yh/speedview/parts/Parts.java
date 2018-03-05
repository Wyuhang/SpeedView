package com.yh.speedview.parts;

import android.graphics.Canvas;
import android.graphics.Paint;

/**
 * 表盘上的小零件的基类 (指针，圆弧，刻度，读数)
 */
public abstract class Parts {
    /**
     * 画笔
     */
    protected Paint mPaint;

    public Parts () {
        initialize ();
    }

    protected void initialize () {
        mPaint = new Paint (Paint.ANTI_ALIAS_FLAG);
        //mPaint.setMaskFilter (new BlurMaskFilter (5, BlurMaskFilter.Blur.NORMAL));
    }

    public abstract void onDraw (Canvas canvas);
}
