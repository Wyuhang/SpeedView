package com.yh.speedview.view;

import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;

import com.yh.speedview.R;
import com.yh.speedview.anim.SpeedAnimator;
import com.yh.speedview.render.BorderRender;
import com.yh.speedview.render.IndicatorRender;
import com.yh.speedview.render.MarkerRender;
import com.yh.speedview.render.RealTimeRender;
import com.yh.speedview.utils.Utils;
import com.yh.speedview.utils.ViewPortHandler;

/**
 * 各类表的基本类(电流表，电压表，温度表)
 */
public abstract class SpeedView extends View {

    /**
     * 实时读数的位置
     */
    private int mPosition;
    protected SpeedAnimator mAnimator;

    protected IndicatorRender mIndicatorRender;
    protected BorderRender mBorderRender;
    protected MarkerRender mMarkerRender;
    protected RealTimeRender mRealTimeRender;
    protected ViewPortHandler mViewPortHandler = new ViewPortHandler ();

    public SpeedView (Context context) {
        this (context, null);
    }

    public SpeedView (Context context, AttributeSet attrs) {
        super (context, attrs);
        Utils.init (getContext ());
        TypedArray a = context.obtainStyledAttributes (attrs, R.styleable.SpeedView);
        mViewPortHandler.setStartAngle (a.getFloat (R.styleable.SpeedView_start_angle, 180f));
        mViewPortHandler.setSweepAngle (a.getFloat (R.styleable.SpeedView_sweep_angle, 180f));
        //  mViewPortHandler.setRadius (a.getDimensionPixelSize (R.styleable.SpeedView_radius, (int) Utils.convertDpToPixel (100)));
        mPosition = a.getInt (R.styleable.SpeedView_position, -1);
        a.recycle ();
//        if (mViewPortHandler.getRadius () <= 0)
//            throw new IllegalArgumentException ("SpeedView radius must be greater than 0");
        initialize ();
    }

    /**
     * 设x最大数  设y最小数
     * （X-Y/2）/(X/10)  得到的数值 四舍五入
     */

    protected void initialize () {
        mIndicatorRender = new IndicatorRender (mViewPortHandler);
        mBorderRender = new BorderRender (mViewPortHandler);
        mMarkerRender = new MarkerRender (mViewPortHandler);
        mRealTimeRender = new RealTimeRender (mViewPortHandler);
        mRealTimeRender.setPosition (mPosition);

        mAnimator = new SpeedAnimator (new ValueAnimator.AnimatorUpdateListener () {
            @Override
            public void onAnimationUpdate (ValueAnimator animation) {
                mViewPortHandler.setRealTimeIndex ((Float) animation.getAnimatedValue ());
                postInvalidate ();
            }
        });
    }

    @Override
    protected void onMeasure (int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure (widthMeasureSpec, heightMeasureSpec);
        int widthSize = MeasureSpec.getSize (widthMeasureSpec);
        int widthMode = MeasureSpec.getMode (widthMeasureSpec);
        int heightSize = MeasureSpec.getSize (heightMeasureSpec);
        int heightMode = MeasureSpec.getMode (heightMeasureSpec);

        int tempWidth = 0;
        int tempHeight = 0;
        switch (widthMode) {
            case MeasureSpec.UNSPECIFIED:
            case MeasureSpec.AT_MOST:
                // mViewPortHandler.setWidth (radius * 2 + getPaddingLeft () + getPaddingRight ());
                tempWidth = (int) Utils.convertDpToPixel (80);
                mViewPortHandler.setWidth (tempWidth * 2 + getPaddingLeft () + getPaddingRight ());
                break;
            case MeasureSpec.EXACTLY:
                // mViewPortHandler.setWidth (widthSize < radius * 2 ? radius * 2 + getPaddingLeft () + getPaddingRight () : widthSize + getPaddingLeft () + getPaddingRight ());
                tempWidth = widthSize / 2;
                mViewPortHandler.setWidth (widthSize + getPaddingLeft () + getPaddingRight ());
                break;
        }
        switch (heightMode) {
            case MeasureSpec.UNSPECIFIED:
            case MeasureSpec.AT_MOST:
                tempHeight = (int) Utils.convertDpToPixel (80);
                mViewPortHandler.setHeight (tempHeight * 2 + getPaddingTop () + getPaddingBottom ());
                // mViewPortHandler.setHeight (radius * 2 + getPaddingTop () + getPaddingBottom ());
                break;
            case MeasureSpec.EXACTLY:
                // mViewPortHandler.setHeight (heightSize < radius * 2 ? radius * 2 + getPaddingTop () + getPaddingBottom () : heightSize + getPaddingTop () + getPaddingBottom ());
                tempHeight = heightSize / 2;
                mViewPortHandler.setHeight (heightSize + getPaddingTop () + getPaddingBottom ());
                break;
        }

        int radius = Math.min (tempWidth, tempHeight);
        mViewPortHandler.setRadius (radius);
        mIndicatorRender.setIndicatorRadius (radius - mIndicatorRender.getIndicatorReduc ());

        mViewPortHandler.setCenterX (mViewPortHandler.getWidth () / 2);
        mViewPortHandler.setCenterY (mViewPortHandler.getHeight () / 2);

        mViewPortHandler.setRectF (mViewPortHandler.getCenterX () - radius, mViewPortHandler.getCenterY () - radius, mViewPortHandler.getCenterX () + radius, mViewPortHandler.getCenterY () + radius);
        setMeasuredDimension (mViewPortHandler.getWidth (), mViewPortHandler.getHeight ());
    }

    /**
     * 根据实时数据获取对应的角度
     *
     * @param real 实时读数
     * @return 由实时读数转换的角度(float)
     */
    protected float getAngleFromReal (float real) {
        int maxRange = mViewPortHandler.getMaxRange ();
        int minRange = mViewPortHandler.getMinRange ();
        float startAngle = mViewPortHandler.getStartAngle ();
        float sweepAngle = mViewPortHandler.getSweepAngle ();

        if (real > maxRange)
            return startAngle + sweepAngle;//+ mDistance / 2
        if (real < minRange)
            return startAngle;//+ mDistance / 2;
        return sweepAngle * ((real - minRange) / (maxRange - minRange)) + startAngle; //mDistance / 2;
    }

    /**
     * 读数是否在正常范围内
     * true 正常范围，false 异常范围
     */
    protected boolean isSafeRange () {
        //正常读数范围最小值
        float safeRangeMin = getAngleFromReal (mViewPortHandler.getMinSafeRange ());
        //正常读数范围最大值
        float safeRangeMax = getAngleFromReal (mViewPortHandler.getMaxSafeRange ());
        //指向正常范围 mRealTimeValue >= rationalMin && mRealTimeValue <= rationalMax
        return mViewPortHandler.getRealTimeIndex () >= safeRangeMin && mViewPortHandler.getRealTimeIndex () <= safeRangeMax;
    }

    //将角度转为读数,保留小数点后一位
    protected String getRealFromAngle () {
        float value = mViewPortHandler.getMinRange () + (mViewPortHandler.getMaxRange () - mViewPortHandler.getMinRange ()) * (mViewPortHandler.getRealTimeIndex () - mViewPortHandler.getStartAngle ()) / mViewPortHandler.getSweepAngle ();//- (mStartAngle + mDistance / 2)) / mSweepAngle
        return Utils.convert (Math.round (value * 10) / 10f);
    }

    /**
     * 获取长刻度对应的读数数组
     *
     * @return 读数数组
     */
    protected String[] createNumerical () {
        int largeSpace = mMarkerRender.getIndexCount ();
        int max = mViewPortHandler.getMaxRange ();
        int min = mViewPortHandler.getMinRange ();
        int space = (max - min) / largeSpace;

        String[] index = new String[largeSpace + 1];
        for (int i = 0; i <= largeSpace; i++) {
            index[i] = Utils.convert ((min + space * i));
        }
        return index;
    }

    public float getRealTimeIndex () {
        return mViewPortHandler.getRealTimeIndex ();
    }

    public void setRealTimeIndex (float realTimeIndex) {
        mViewPortHandler.setRealTimeIndex (realTimeIndex);
    }
}
