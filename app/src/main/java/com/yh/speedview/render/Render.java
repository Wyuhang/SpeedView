package com.yh.speedview.render;


import com.yh.speedview.utils.ViewPortHandler;

/**
 * 2018/2/7 11:32
 * MeterView
 *
 * @author wyh
 */
public abstract class Render {
    protected ViewPortHandler mViewPortHandler;
    /**
     * 刻度线距离表盘边界的距离
     */
    private int mDistance = 10;

    /**
     * 长刻度线终点Y坐标的时候需要减去的值
     */
    private int mLongY = 40;

    /**
     * 短刻度线终点Y坐标的时候需要减去的值
     */
    private int mShortY = 28;

    /**
     * 长刻度线所对应的读数坐标需要减去的值
     */
    private int mNumericalReduce = mLongY + 10;

    /**
     *
     */
    private int mIndicatorReduc = mNumericalReduce + 30;

    public int getIndicatorReduc () {
        return mIndicatorReduc;
    }

    public int getNumericalReduce () {
        return mNumericalReduce;
    }

    public int getDistance () {
        return mDistance;
    }

    public int getLongY () {
        return mLongY;
    }

    public int getShortY () {
        return mShortY;
    }

    public Render (ViewPortHandler handler) {
        mViewPortHandler = handler;
    }

    public ViewPortHandler getViewPortHandler () {
        return mViewPortHandler;
    }

    public void setViewPortHandler (ViewPortHandler viewPortHandler) {
        mViewPortHandler = viewPortHandler;
    }
}
