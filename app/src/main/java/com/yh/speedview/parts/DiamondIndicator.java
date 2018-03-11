package com.yh.speedview.parts;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;

import com.yh.speedview.render.IndicatorRender;
import com.yh.speedview.utils.ViewPortHandler;

/**
 * 菱形指针,指针中间带有一个小空心圆
 */
public class DiamondIndicator extends Indicator {
    /**
     * 指针旋转所围绕轴的画笔
     */
    private Paint mAxisPaint;
    private IndicatorRender mIndicatorRender;
    private ViewPortHandler mViewPortHandler;

    public DiamondIndicator (IndicatorRender render, Drawable background) {
        mIndicatorRender = render;
        mAxisPaint = new Paint (Paint.ANTI_ALIAS_FLAG);
        mAxisPaint.setStyle (Paint.Style.STROKE);
        mAxisPaint.setColor (getAxisColor (background));
        mViewPortHandler=render.getViewPortHandler ();
    }

    /**
     * 指针旋转所围绕轴的颜色
     */
    private int getAxisColor (Drawable axisColor) {
        int color;
        if (axisColor instanceof ColorDrawable)
            color = ((ColorDrawable) axisColor).getColor ();
        else
            color = 0;
        return color;
    }

    @Override
    public void onDraw (Canvas canvas) {
        //画指针
        canvas.save ();
        canvas.translate (mViewPortHandler.getCenterX (), mViewPortHandler.getCenterY ());
        //指针小圆的半径
        int handCircleRadius = 8;

        int indicatorRadius = mIndicatorRender.getIndicatorRadius ();

        float startRadians = (float) Math.toRadians (mViewPortHandler.getRealTimeIndex ());
        float startY = (float) (Math.sin (startRadians) * indicatorRadius);
        float startX = (float) (Math.cos (startRadians) * indicatorRadius);

        //顺时针增加90度后，画指针头部与圆相切的第一个点(实际高于相切点3px)
        float increase_90 = mViewPortHandler.getRealTimeIndex () + 90;
        float radians_90 = (float) Math.toRadians (increase_90);
        float increase90_Y = (float) (Math.sin (radians_90) * (handCircleRadius + 3));
        float increase90_X = (float) (Math.cos (radians_90) * (handCircleRadius + 3));

        //顺时针增加180度后，画指针尾巴点的坐标
        float increase_180 = mViewPortHandler.getRealTimeIndex () + 180;
        float radians_180 = (float) Math.toRadians (increase_180);
        float increase180_Y = (float) (Math.sin (radians_180) * 40);
        float increase180_X = (float) (Math.cos (radians_180) * 40);

        //逆时针减少90度后，画指针头部与圆相切的第二个点 (实际高于相切点3px)
        float reduce_90 = mViewPortHandler.getRealTimeIndex () - 90;
        float radians_reduce_90 = (float) Math.toRadians (reduce_90);
        float reduce90_Y = (float) (Math.sin (radians_reduce_90) * (handCircleRadius + 3));
        float reduce90_X = (float) (Math.cos (radians_reduce_90) * (handCircleRadius + 3));

        mIndicatorPath.reset ();
        mIndicatorPath.moveTo (startX, startY);
        mIndicatorPath.lineTo (increase90_X, increase90_Y);
        mIndicatorPath.lineTo (increase180_X, increase180_Y);
        mIndicatorPath.lineTo (reduce90_X, reduce90_Y);
        mIndicatorPath.close ();

        if (mSafeRangeListener != null) {
            if (mSafeRangeListener.isSafe ()) //正常范围指针的颜色
                mPaint.setColor (Color.BLUE);
            else
                mPaint.setColor (Color.RED);
        } else
            mPaint.setColor (Color.BLUE);

        canvas.drawPath (mIndicatorPath, mPaint);
        //指针环绕的轴
        canvas.drawCircle (0, 0, handCircleRadius, mAxisPaint);
        canvas.restore ();

    }
}
