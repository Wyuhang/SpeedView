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

            float radians = (float) Math.toRadians (indexAngle);

            /** ************************************************************待优化 */

            float y1 = (float) (Math.sin (radians) * (mPortHandler.getRadius () - mMarkerRender.getNumericalReduce ()));//一/+、二/+、三/-、四/-
            float x1 = (float) (Math.cos (radians) * (mPortHandler.getRadius () - mMarkerRender.getNumericalReduce ()));//一/+、二/-、三/-、四/+

            if (indexAngle % 360 > 135 && indexAngle % 360 < 225) {
                mPaint.setTextAlign (Paint.Align.LEFT);
            } else if ((indexAngle % 360 >= 0 && indexAngle % 360 < 45) || (indexAngle % 360 > 315 && indexAngle % 360 <= 360)) {
                mPaint.setTextAlign (Paint.Align.RIGHT);
            } else {
                mPaint.setTextAlign (Paint.Align.CENTER);
            }
            /** ************************************************************待优化 */


            String text = numerical[i];
            mPaint.getTextBounds (text, 0, text.length (), mBounds);
            canvas.drawText (text, x1, y1, mPaint);
        }
        canvas.restore ();
    }


}
