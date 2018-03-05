package com.yh.speedview.render;


import com.yh.speedview.utils.ViewPortHandler;

/**
 * 2018/2/7 13:32
 * MeterView
 *
 * @author wyh
 */
public class IndicatorRender extends Render {


    public IndicatorRender (ViewPortHandler handler) {
        super (handler);
    }

    /**
     * 指针的半径
     */
    private int mIndicatorRadius;

    public int getIndicatorRadius () {
        return mIndicatorRadius;
    }

    public void setIndicatorRadius (int indicatorRadius) {
        mIndicatorRadius = indicatorRadius;
    }
}
