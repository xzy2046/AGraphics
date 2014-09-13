/*
 *  This program is a lightweight graphic library base on Android platform
 *  Copyright (C) 2012  Xu Zhengyang
 *
 *  This program is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 */

package xzy.android.agraphics.animation;

import android.os.Handler;
import android.os.SystemClock;
import android.util.Log;
import android.view.animation.Interpolator;

/**
 * @author zhengyang.xu
 * @version 0.1
 * @date Mar 2, 2012 1:11:55 PM
 * @project AGraphics
 */
public class AAnimator {

    private long mDuration;

    private long mTimeDelay;   //延迟的时间

    private int mFrameDelay;    //延迟的帧数

    private int mTempFrameDelay;

    private long mStartTime;  //启动时的时间

    protected long mTotalTime;  //总共经过的时间

    protected float mStartValue;   //开始的位置   //TODO 动画结束后要置为非法值?

    protected float mEndValue;  //结束的位置

    private int mRepeatCount;  //重复的次数

    private int mTempRepeatCount;

    private boolean mRunning;  //是否在动画中

    private boolean mFillAfter;  //动画结束后是否停留在新位置

    private REPEAT_MODE mRepeatMode;

    protected ValuesHolder mValuesHolder;

    private Interpolator mInterpolator;

    private Object mTag;

    private boolean DEBUG = false;

    protected AnimationListener mAnimationListener;

    private AnimationUpdateListener mAnimationUpdateListener;

    private AAnimator mSecondAnimator;

    public static enum REPEAT_MODE {
        RESTART, REVERSE
    }

    public AAnimator() {  //TODO 初始化很多默认值
        mRepeatMode = REPEAT_MODE.RESTART;
        mFillAfter = false;
    }

    public void setDuration(int duration) {
        if (duration < 0) {
            throw new IllegalArgumentException("Animation duration cannot be negative");
        }
        mDuration = duration;
    }

    /**
     * TimeDelay 和 FrameDelay请勿同时使用
     */
    public void setTimeDelay(long timeDelay) {
        if (timeDelay < 0) {
            throw new IllegalArgumentException("Time delay cannot be negative");
        }
        mTimeDelay = timeDelay;
    }

    /**
     * TimeDelay 和FrameDelay 请勿同时使用
     *
     * @param frameDelay 延迟的帧数
     */
    public void setFrameDelay(int frameDelay) {
        mFrameDelay = frameDelay;
    }

    public long getDuration() {
        return mDuration;
    }

    /**
     * RESTART : 0
     * REVERSE : 1
     */
    public void setRepeatMode(REPEAT_MODE repeatMode) {   //重复的方式（头 -> 尾） or（尾的-> 头)
        mRepeatMode = repeatMode;
    }

    public REPEAT_MODE getRepeatMode() {
        return mRepeatMode;
    }

    public void setTag(Object o) {
        mTag = o;
    }

    public Object getTag() {
        return mTag;
    }

    public void setFillAfter(boolean fillAfter) {
        mFillAfter = fillAfter;
    }

    public boolean getFillAfter() {
        return mFillAfter;
    }

    /**
     * @param count count must > 0
     */
    public void setRepeatCount(int count) {
        mRepeatCount = count;
    }

    public int getRepeatCount() {
        return mRepeatCount;
    }

    public long getTimeDelay() {
        return mTimeDelay;
    }

    public int getFrameDelay() {
        return mFrameDelay;
    }

    public void setAnimationListener(AnimationListener l) {
        mAnimationListener = l;
    }

    public void setAnimationUpdateListener(AnimationUpdateListener l) {
        mAnimationUpdateListener = l;
    }

    public void setupAnimate(ValuesHolder holder, float startValue, float endValue) {
        mStartValue = startValue;
        mEndValue = endValue;
        mValuesHolder = holder;
    }

    public void setupAnimate(ValuesHolder holder, float startValue, float endValue, long duration) {
        mStartValue = startValue;
        mEndValue = endValue;
        mDuration = duration;
        mValuesHolder = holder;
    }

    private long getCurrentTime() {
        return SystemClock.uptimeMillis();
    }

    public void start() {
        mRunning = true;
        mTotalTime = 0;   //初始化其他值
        mStartTime = getCurrentTime();
        mTempFrameDelay = mFrameDelay;
        mTempRepeatCount = mRepeatCount;
        onAnimationStart();
        if (mSecondAnimator != null) {
            mSecondAnimator.start();
//            mSecondAnimator.onAnimationStart();
        }
        applyTranformation();

    }

    //取消本次动画，mValues的值需要恢复吗？
    //有多个动画时，取消一个一起取消
    public void cancel() {   //TODO  设置结束后的位置
        onAnimationCancel();
        if (mSecondAnimator!= null) {
            mSecondAnimator.cancel();
        }
        mRunning = false;
    }

    public Boolean isRunning() {
        return mRunning;
    }

    protected void onAnimationStart() {
        if (mAnimationListener != null) {
            mAnimationListener.onAnimationStart(this);
        }
//        if (mSecondAnimator != null) {
//            mSecondAnimator.onAnimationStart();
//        }
    }

    protected void onAnimationEnd() {
        mRunning = false;
        if (mAnimationListener != null) {
            mAnimationListener.onAnimationEnd(this);
        }
//        if (mSecondAnimator != null) {
//            mSecondAnimator.onAnimationEnd();
//        }
    }

    protected void onAnimationRepeat() {
        if (mAnimationListener != null) {
            mAnimationListener.onAnimationRepeat(this);
        }
//        if (mSecondAnimator != null) {
//            mSecondAnimator.onAnimationRepeat();
//        }
    }

    protected void onAnimationUpdated() {
        if (mAnimationUpdateListener != null) {
            mAnimationUpdateListener.onAnimationUpdate(this);
        }
//        if (mSecondAnimator != null) {
//            mSecondAnimator.onAnimationUpdated();
//        }
    }

    protected void onAnimationCancel() {
        if (mAnimationListener != null) {
            mAnimationListener.onAnimationCancel(this);
        }
//        if (mSecondAnimator != null) {
//            mSecondAnimator.onAnimationCancel();
//        }
    }

    /**
     * play with another animator
     * @param animator
     */
    public void playWith(AAnimator animator) {
        mSecondAnimator = animator;
    }

    // public Float3 animate(Float3 preVal, Float3 tarVal, float timeElapsed);

    protected void applyTranformation() {
        // mTotalTime = getCurrentTime() - mStartTime;
        // mTotalTime += timeElapsed;
        // timeElapsed = mTotalTime;
        // if (mTotalTime <= mDelay) {
        // return preVal;
        // } else {
        // timeElapsed -= mDelay;
        // }

        if (mTempFrameDelay != 0) {
            --mTempFrameDelay;
            mStartTime = getCurrentTime();
//            return;
        }

        if (DEBUG) {
            Log.i("xzy", "NormalizedTime is : " + getNormalizedTime());
        }
        float normalizedTime = getNormalizedTime();
        if (normalizedTime < 0f) {
            normalizedTime = 0;
        }

        if (normalizedTime > 1f) {
            normalizedTime = 1;
        }
        float interpolation = mInterpolator.getInterpolation(normalizedTime);
        if (DEBUG) {
            Log.i("xzy", "interpolation is " + interpolation);
        }
        float newValue = mStartValue + (mEndValue - mStartValue) * interpolation;
        mValuesHolder.setValue(newValue);
        onAnimationUpdated();
        if (normalizedTime == 1f) {
            if (mTempRepeatCount == 0) {
                if (mFillAfter) {
                    mValuesHolder.setValue(mStartValue);
                }
                onAnimationEnd();
                return;
            } else {
                --mTempRepeatCount;
                if (mRepeatMode == REPEAT_MODE.RESTART) {
                    mTotalTime = 0;   //初始化其他值
                    mStartTime = getCurrentTime();
                    mTempFrameDelay = mFrameDelay;
                } else if (mRepeatMode == REPEAT_MODE.REVERSE) {
                    mTotalTime = 0;   //初始化其他值
                    mStartTime = getCurrentTime();
                    mTempFrameDelay = mFrameDelay;
                    float mTempValue = mStartValue;
                    mStartValue = mEndValue;
                    mEndValue = mTempValue;
                }
            }
        }



        // applyTranformation();
        mHandler.postDelayed(new Runnable() {

            @Override
            public void run() {
                applyTranformation();
                //TODO test
//                if (mSecondAnimator != null) {
//                    mSecondAnimator.applyTranformation();
//                }
            }

        }, 10);
    }

    Handler mHandler = new Handler(); // 最好可以改成sendMessage..

    public float getNormalizedTime() { // public for demo use
        if (mDuration != 0) {
            float normalizedTime = ((float) (getCurrentTime() - (mTimeDelay + mStartTime)))
                    / (float) mDuration;
            return normalizedTime;
        }
        return 0;
    }

    public void setInterpolator(Interpolator interpolator) {
        mInterpolator = interpolator;
    }

    public Interpolator getInterpolator() {
        return mInterpolator;
    }

    public static interface AnimationListener {

        void onAnimationStart(AAnimator animator);

        void onAnimationEnd(AAnimator animator);

        void onAnimationRepeat(AAnimator animator);

        void onAnimationCancel(AAnimator animator);
    }

    public static interface AnimationUpdateListener {  //use for view to invalidate canvas

        void onAnimationUpdate(AAnimator animator);
    }

}
