package com.yh.speedview.utils;


import android.graphics.RectF;

/**
 * 该类包含有仪表盘当前视图设置的信息，包括：起始绘制角度，总共绘制角度
 * 绘制颜色等等
 */
public class ViewPortHandler {

    /**
     * 表盘控件的宽
     */
    private int mWidth;
    /**
     * 表盘控件的高
     */
    private int mHeight;

    /**
     * 表盘中心点X坐标
     */
    private float mCenterX = 0f;
    /**
     * 表盘中心点Y坐标
     */
    private float mCenterY = 0f;
    /**
     * 指针当前值指向的实时读数
     */
    private float mRealTimeIndex = 0f;
    /**
     * 表盘圆弧的半径 以dp为单位
     */
    protected int mRadius ;

    /**
     * 表盘控件所在的矩形
     */
    private RectF mRectF = new RectF ();

    /**
     * 起始角(以度计)
     */
    protected float mStartAngle = 180;
    /**
     * 表盘扫过角(以度计，顺时针方向)
     */
    protected float mSweepAngle = 180;

    /**
     * 最大安全量程
     */
    protected int mMaxSafeRange = 0;
    /**
     * 最小安全量程
     */
    protected int mMinSafeRange = 0;

    /**
     * 表盘量程的最大值
     */
    private int mMaxRange = 100;

    /**
     * 表盘量程的最小值
     */
    private int mMinRange = 0;

    public ViewPortHandler () {

    }

    public int getMaxRange () {
        return mMaxRange;
    }

    public void setMaxRange (int maxRange) {
        mMaxRange = maxRange;
    }

    public int getMinRange () {
        return mMinRange;
    }

    public void setMinRange (int minRange) {
        mMinRange = minRange;
    }

    public int getMaxSafeRange () {
        return mMaxSafeRange;
    }

    public void setMaxSafeRange (int maxSafeRange) {
        if (maxSafeRange > mMaxRange) {
            mMaxSafeRange = mMaxRange;
        } else if (maxSafeRange < mMinRange) {
            mMaxSafeRange = mMinRange;
        } else {
            mMaxSafeRange = maxSafeRange;
        }
        if (mMaxSafeRange < mMinSafeRange)
            throw new IllegalArgumentException ("SafeMaxRange can't be less than SafeMinRange");
    }

    public int getMinSafeRange () {
        return mMinSafeRange;
    }

    public void setMinSafeRange (int minSafeRange) {
        if (mMinSafeRange > mMaxSafeRange)
            throw new IllegalArgumentException ("mMinSafeRange is" + mMinSafeRange + ", can't be greater than mMaxSafeRange " + mMaxSafeRange);
        if (minSafeRange > mMaxRange) {
            mMinSafeRange = mMaxRange;
        } else if (minSafeRange < mMinRange) {
            mMinSafeRange = mMinRange;
        } else {
            mMinSafeRange = minSafeRange;
        }
    }


    public int getRadius () {
        return mRadius;
    }

    public void setRadius (int radius) {
        mRadius = radius;
    }

    public float getStartAngle () {
        return mStartAngle;
    }

    public void setStartAngle (float startAngle) {
        mStartAngle = startAngle;
    }

    public float getSweepAngle () {
        return mSweepAngle;
    }

    public void setSweepAngle (float sweepAngle) {
        mSweepAngle = sweepAngle;
    }

    public RectF getRectF () {
        return mRectF;
    }

    public void setRectF (float left, float top, float right, float bottom) {
        mRectF.set (left, top, right, bottom);
    }

    public int getWidth () {
        return mWidth;
    }

    public void setWidth (int width) {
        mWidth = width;
    }

    public int getHeight () {
        return mHeight;
    }

    public void setHeight (int height) {
        mHeight = height;
    }

    public float getCenterX () {
        return mCenterX;
    }

    public void setCenterX (float centerX) {
        mCenterX = centerX;
    }

    public float getCenterY () {
        return mCenterY;
    }

    public void setCenterY (float centerY) {
        mCenterY = centerY;
    }

    public float getRealTimeIndex () {
        return mRealTimeIndex;
    }

    public void setRealTimeIndex (float realTimeIndex) {
        mRealTimeIndex = realTimeIndex;
    }
}

