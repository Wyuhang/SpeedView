package com.yh.speedview.anim;

import android.animation.TimeInterpolator;
import android.animation.ValueAnimator;
import android.animation.ValueAnimator.AnimatorUpdateListener;
import android.view.animation.LinearInterpolator;

/**
 * 2018/1/30 11:23
 * MeterView
 * 指针转动的动画
 *
 * @author wyh
 */
public class SpeedAnimator {

    protected AnimatorUpdateListener mUpdateListener;

    public SpeedAnimator (AnimatorUpdateListener listener) {
        mUpdateListener = listener;
    }

    protected float mRotate = 0f;

    /**
     * 指针旋转动画 ，默认
     */
    public void indicatorRotate (float start, float end) {
        indicatorRotate (start, end, 800);
    }

    public void indicatorRotate (float start, float end, long duration) {
        indicatorRotate (start, end, duration, new LinearInterpolator ());
    }

    public void indicatorRotate (float start, float end, long duration, TimeInterpolator interpolator) {
        ValueAnimator animator = ValueAnimator.ofFloat (start, end);
        animator.setInterpolator (interpolator);
        animator.setDuration (duration);
        animator.addUpdateListener (mUpdateListener);
        animator.start ();
    }


    public float getRotate () {
        return mRotate;
    }

    public void setRotate (float rotate) {
        mRotate = rotate;
    }

}
