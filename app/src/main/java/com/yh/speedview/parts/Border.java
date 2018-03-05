package com.yh.speedview.parts;

import android.graphics.BlurMaskFilter;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import com.yh.speedview.render.BorderRender;
import com.yh.speedview.utils.ViewPortHandler;


/**
 * 表盘的圆弧边界
 */
public class Border extends Parts {

    private BorderRender mBorderRender;
    private ViewPortHandler mPortHandler;

    public Border (BorderRender render) {
        mBorderRender = render;
        mPaint.setStyle (Paint.Style.STROKE);
        mPaint.setColor (Color.BLUE);
        mPaint.setStrokeWidth (8);
        mPaint.setMaskFilter (new BlurMaskFilter (50, BlurMaskFilter.Blur.SOLID));
        mPortHandler = render.getViewPortHandler ();
    }

    @Override
    public void onDraw (Canvas canvas) {
        canvas.drawArc (mPortHandler.getRectF (), mPortHandler.getStartAngle (), mPortHandler.getSweepAngle (), false, mPaint);
    }
}
