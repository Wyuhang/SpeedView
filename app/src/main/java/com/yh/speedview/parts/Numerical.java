package com.yh.speedview.parts;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;

import com.yh.speedview.listener.NumericalListener;
import com.yh.speedview.render.MarkerRender;
import com.yh.speedview.utils.ViewPortHandler;


/**
 * 绘制每个长刻度对应的读数
 */
public class Numerical extends Parts {

    private Rect mBounds;

    private MarkerRender mMarkerRender;
    private NumericalListener mNumericalListener;
    private ViewPortHandler mPortHandler;

    public Numerical (MarkerRender render) {
        mMarkerRender = render;
        mPaint.setStyle (Paint.Style.STROKE);
        mPaint.setTextSize (30);
        mBounds = new Rect ();
        mPortHandler = render.getViewPortHandler ();
    }

    public void setNumericalListener (NumericalListener listener) {
        mNumericalListener = listener;
    }

    @Override
    public void onDraw (Canvas canvas) {

        int largeSpace = mMarkerRender.getIndexCount ();

        canvas.save ();
        canvas.translate (mPortHandler.getCenterX (), mPortHandler.getCenterY ());
        //表盘等分为total份
        float singleAngle = mMarkerRender.getSingleAngle ();

        if (mNumericalListener == null)
            throw new IllegalArgumentException ("NumericalListener is null ");
        String[] numerical = mNumericalListener.create ();

        for (int i = 0; i <= largeSpace; i++) {
            float bigCount = singleAngle * (i * mMarkerRender.getSmallSpace ());
            float indexAngle = mPortHandler.getStartAngle () + bigCount;//+ mDistance / 2
            String text = numerical[i];
            mPaint.getTextBounds(text, 0, text.length(), mBounds);

            canvas.rotate(indexAngle);
            canvas.translate(mPortHandler.getRadius() - mMarkerRender.getNumericalReduce(), 0);
            canvas.rotate(-indexAngle);

            canvas.drawText(text, -mBounds.width() / 2, mBounds.height() / 2, mPaint);

            canvas.rotate(indexAngle);
            canvas.translate(mMarkerRender.getNumericalReduce() - mPortHandler.getRadius(), 0);
            canvas.rotate(-indexAngle);
        }
        canvas.restore ();
    }


}
