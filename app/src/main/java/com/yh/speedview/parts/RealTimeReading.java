package com.yh.speedview.parts;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import com.yh.speedview.listener.RealTimeIndexListener;
import com.yh.speedview.listener.SafeRangeListener;
import com.yh.speedview.render.RealTimeRender;
import com.yh.speedview.utils.ViewPortHandler;


/**
 * 指针转动的实时读数
 *
 * @author wyh
 */
public class RealTimeReading extends Parts {
    private String mUnit;
    private RealTimeRender mReadingRender;
    private ViewPortHandler mPortHandler;

    public RealTimeReading (RealTimeRender readingRender) {
        mReadingRender = readingRender;
        mPaint.setStyle (Paint.Style.STROKE);
        mPaint.setTextSize (30);
        mPaint.setTextAlign (Paint.Align.CENTER);
        mPortHandler = readingRender.getViewPortHandler ();
    }

    private SafeRangeListener mSafeRangeListener;

    private RealTimeIndexListener mRealTimeIndexListener;

    public void setSafeRangeListener (SafeRangeListener safeRangeListener) {
        mSafeRangeListener = safeRangeListener;
    }

    public void setRealTimeIndexListener (RealTimeIndexListener listener) {
        mRealTimeIndexListener = listener;
    }

    @Override
    public void onDraw (Canvas canvas) {
        canvas.save ();
        canvas.translate (mPortHandler.getCenterX (), mPortHandler.getCenterY ());
        int distance = mReadingRender.getDistance ();
        if (mSafeRangeListener != null) {
            if (mSafeRangeListener.isSafe ()) //正常范围指针的颜色
                mPaint.setColor (Color.BLUE);
            else
                mPaint.setColor (Color.RED);
        } else
            mPaint.setColor (Color.BLUE);
        canvas.drawText (mRealTimeIndexListener.show () + mUnit, 0, distance, mPaint);
    }

    public void setUnit (int unit) {
        //安
        String AMP = "A";
        //毫安
        String MILLI_AMP = "mA";
        switch (unit) {
            case 0:
                mUnit = MILLI_AMP;
                break;
            case 1:
                mUnit = AMP;
                break;
            default:
                mUnit = MILLI_AMP;
                break;
        }
    }
}
