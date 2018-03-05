package com.yh.speedview.render;

import android.graphics.Paint;

import com.yh.speedview.utils.ViewPortHandler;

import java.text.DecimalFormat;


/**
 * 2018/2/7 14:31
 * MeterView
 *
 * @author wyh
 */
public class MarkerRender extends Render {
    /**
     * 长刻度线的画笔
     */
    private Paint mLargeSpacePaint;

    /**
     * 短刻度线的画笔
     */
    private Paint mSmallSpacePaint;
    /**
     * 每个长刻度线间距内短刻度线的数量
     */
    private int mSmallSpace = 10;

    /**
     * 刻度值数量
     */
    private int mIndexCount = 10;

    public MarkerRender (ViewPortHandler portHandler) {
        super (portHandler);

        mLargeSpacePaint = new Paint (Paint.ANTI_ALIAS_FLAG);
        mLargeSpacePaint.setStyle (Paint.Style.STROKE);
        mLargeSpacePaint.setStrokeWidth (3);

        mSmallSpacePaint = new Paint (Paint.ANTI_ALIAS_FLAG);
        mSmallSpacePaint.setStyle (Paint.Style.STROKE);
        mSmallSpacePaint.setStrokeWidth (2);
    }

    /**
     * 由{@link ViewPortHandler#mSweepAngle}/({@link #mIndexCount}*{@link #mSmallSpace})
     * 计算得来的用以表示等分圆弧的一份角的数值
     */
    public float getSingleAngle () {
        float single = mViewPortHandler.getSweepAngle () / (mIndexCount * mSmallSpace);
        DecimalFormat df = new DecimalFormat ("#.00");
        return Float.valueOf (df.format (single));
    }

    /**
     * 由{@link #mIndexCount}*{@link #mSmallSpace}
     * 计算而来，表示将{@link ViewPortHandler#mSweepAngle}等分的总数
     */
    public int getTotalSlice () {
        return mIndexCount * mSmallSpace;
    }

    public Paint getLargeSpacePaint () {
        return mLargeSpacePaint;
    }

    public void setLargeSpacePaint (Paint largeSpacePaint) {
        mLargeSpacePaint = largeSpacePaint;
    }

    public Paint getSmallSpacePaint () {
        return mSmallSpacePaint;
    }

    public void setSmallSpacePaint (Paint smallSpacePaint) {
        mSmallSpacePaint = smallSpacePaint;
    }

    public int getIndexCount () {
        return mIndexCount;
    }

    public void setIndexCount (int largeSpace) {
        mIndexCount = largeSpace;
    }

    public int getSmallSpace () {
        return mSmallSpace;
    }

    public void setSmallSpace (int smallSpace) {
        mSmallSpace = smallSpace;
    }
}
