package com.yh.speedview.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.util.AttributeSet;

import com.yh.speedview.R;
import com.yh.speedview.listener.NumericalListener;
import com.yh.speedview.listener.RealTimeIndexListener;
import com.yh.speedview.listener.SafeRangeListener;
import com.yh.speedview.parts.Border;
import com.yh.speedview.parts.DiamondIndicator;
import com.yh.speedview.parts.Marker;
import com.yh.speedview.parts.Numerical;
import com.yh.speedview.parts.RealTimeReading;


/**
 * 电流表
 */
public class Ammeter extends SpeedView implements SafeRangeListener, NumericalListener, RealTimeIndexListener {
    /**
     * 电流单位(毫安：mA,安：A)
     * 默认:mA
     */
    private int mUnit = -1;

    private DiamondIndicator mIndicator;
    private Border mBorder;
    private Marker mMarker;
    private Numerical mNumerical;
    private RealTimeReading mRealTimeReading;

    public Ammeter (Context context) {
        this (context, null);
    }

    public Ammeter (Context context, AttributeSet attrs) {
        super (context, attrs);
        TypedArray a = context.obtainStyledAttributes (attrs, R.styleable.Ammeter);
        mUnit = a.getInt (R.styleable.Ammeter_unit, -1);
        a.recycle ();
    }

    @Override
    protected void initialize () {
        super.initialize ();
        mViewPortHandler.setRealTimeIndex (getAngleFromReal (mViewPortHandler.getRealTimeIndex ()));
        mIndicator = new DiamondIndicator (mIndicatorRender, getBackground ());
        mIndicator.setSafeRangeListener (this);

        mBorder = new Border (mBorderRender);
        mMarker = new Marker (mMarkerRender);

        mNumerical = new Numerical (mMarkerRender);
        mNumerical.setNumericalListener (this);

        mRealTimeReading = new RealTimeReading (mRealTimeRender);
        mRealTimeReading.setSafeRangeListener (this);
        mRealTimeReading.setRealTimeIndexListener (this);
        mRealTimeReading.setUnit (mUnit);
    }

    @Override
    protected void onDraw (Canvas canvas) {
        super.onDraw (canvas);

        mBorder.onDraw (canvas);//绘制圆弧
        mMarker.onDraw (canvas);//绘制刻度
        mNumerical.onDraw (canvas);//绘制长刻度对应的读数
        mIndicator.onDraw (canvas);//绘制指针
        mRealTimeReading.onDraw (canvas);//绘制跟随指针实时变化的读数
    }

    /**
     * 设置显示多少个刻度值
     *
     * @param count 数量
     */
    public void setCount (int count) {
        mMarkerRender.setIndexCount (count);
    }

    /**
     * 设置电流表短刻度线的数量
     *
     * @param smallInterval 数量
     */
    public void setSmallSpace (int smallInterval) {
        mMarkerRender.setSmallSpace (smallInterval);
    }

    /**
     * 设置电流表的最大量程
     *
     * @param maxRange 最大量程
     */
    public void setMaxRange (int maxRange) {
        mViewPortHandler.setMaxRange (maxRange);
    }

    /**
     * 设置电流表的最小量程
     *
     * @param minRange 最小量程
     */
    public void setMinRange (int minRange) {
        mViewPortHandler.setMinRange (minRange);
    }

    /**
     * 设置实时读数
     *
     * @param realTimeData 由当前读数即将移动到的读数
     */
    public void setRealTimeData (int realTimeData) {
        mAnimator.indicatorRotate (mViewPortHandler.getRealTimeIndex (), getAngleFromReal (realTimeData));
    }

    /**
     * 设置电流表最大安全量程
     *
     * @param safeMaxRange 最大安全量程值
     */
    public void setMaxSafeRange (int safeMaxRange) {
        mViewPortHandler.setMaxSafeRange (safeMaxRange);
    }

    /**
     * 电流表最小安全量程
     *
     * @param safeMinRange 最小安全量程值
     */
    public void setMinSafeRange (int safeMinRange) {
        mViewPortHandler.setMinSafeRange (safeMinRange);
    }

    @Override
    public boolean isSafe () {
        return isSafeRange ();
    }

    @Override
    public String[] create () {
        return createNumerical ();
    }

    @Override
    public String show () {
        return getRealFromAngle ();
    }
}
