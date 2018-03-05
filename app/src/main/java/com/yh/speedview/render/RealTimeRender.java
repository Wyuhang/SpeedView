package com.yh.speedview.render;


import com.yh.speedview.utils.ViewPortHandler;

/**
 * 2018/2/7 11:32
 * MeterView
 * 实时读数的渲染器
 *
 * @author wyh
 */
public class RealTimeRender extends Render {

    /**
     * 读数相对于指针的位置(y轴方向)
     */
    private int mDistance = 0;

    public RealTimeRender (ViewPortHandler portHandler) {
        super (portHandler);
    }

    /**
     * 跟随指针实时变化的读数的位置
     */
    private int position = -1;

    public void setPosition (int position) {
        this.position = position;
    }

    public int getDistance () {
        if (position == 0)
            return mDistance = -mViewPortHandler.getRadius () / 3;
        else
            return mDistance = mViewPortHandler.getRadius () / 3;
    }


}
