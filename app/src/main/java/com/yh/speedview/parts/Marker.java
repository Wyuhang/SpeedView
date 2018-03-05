package com.yh.speedview.parts;

import android.graphics.Canvas;
import android.graphics.Color;

import com.yh.speedview.render.MarkerRender;
import com.yh.speedview.utils.ViewPortHandler;


/**
 * 2018/1/19 15:14
 * MeterView
 * 表盘刻度
 *
 * @author wyh
 */
public class Marker extends Parts {

    private MarkerRender mMarkerRender;
    private ViewPortHandler mPortHandler;

    public Marker (MarkerRender render) {
        mMarkerRender = render;
        mPortHandler = render.getViewPortHandler ();
    }

    @Override
    public void onDraw (Canvas canvas) {
        //画刻度
        canvas.save ();
        canvas.translate (mPortHandler.getCenterX (), mPortHandler.getCenterY ());

        int radius = mPortHandler.getRadius ();
        int smallSpace = mMarkerRender.getSmallSpace ();
        int maxRange = mPortHandler.getMaxRange ();
        int minRange = mPortHandler.getMinRange ();
        int mSafeMaxRange = mPortHandler.getMaxSafeRange ();
        int mSafeMinRange = mPortHandler.getMinSafeRange ();

        int total = mMarkerRender.getTotalSlice ();

        float singleRange = (float) (maxRange - minRange) / total;

        float singleAngle = mMarkerRender.getSingleAngle ();

        //画第一个刻度需要旋转的角度
        float first = mPortHandler.getStartAngle () - 90;//mDistance / 2;
        canvas.rotate (first);

        //绘制刻度
        for (int i = 0; i <= total; i++) {
            if ((i * singleRange + minRange) >= mSafeMinRange && (i * singleRange + minRange) <= mSafeMaxRange) {//安全范围
                mMarkerRender.getLargeSpacePaint ().setColor (Color.CYAN);
                mMarkerRender.getSmallSpacePaint ().setColor (Color.BLUE);
            } else {
                mMarkerRender.getLargeSpacePaint ().setColor (Color.RED);
                mMarkerRender.getSmallSpacePaint ().setColor (Color.RED);
            }

            if ((i * singleAngle) % (smallSpace * singleAngle) == 0) {
                //长刻度
                canvas.drawLine (0, radius - mMarkerRender.getDistance (), 0, radius - mMarkerRender.getLongY (), mMarkerRender.getLargeSpacePaint ());
            } else {
                //短刻度
                canvas.drawLine (0, radius - mMarkerRender.getDistance (), 0, radius - mMarkerRender.getShortY (), mMarkerRender.getSmallSpacePaint ());
            }
            canvas.rotate (singleAngle);
        }
        canvas.restore ();
    }
}
