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

package xzy.android.agraphics;

import xzy.android.agraphics.animation.AAnimator;
import xzy.android.agraphics.animation.AAnimator.REPEAT_MODE;
import xzy.android.agraphics.animation.ValuesHolder;
import xzy.android.agraphics.interpolater.AInterpolator;
import xzy.android.agraphics.interpolater.EaseType.Type;
import xzy.android.agraphics.interpolater.QuadInterpolator;
import xzy.android.agraphics.util.CPUUsage;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

/**
 * @author zhengyang.xu
 * @version 0.1
 * @date Mar 2, 2012 1:11:55 PM
 * @project AGraphics TODO 抽象一个 Shape类出来
 */
public class AAnimationView extends View implements AAnimator.AnimationUpdateListener {

    private Bitmap mBitmap;

    private ValuesHolder mValuesHolderX;

    private ValuesHolder mValuesHolderY;

    private AInterpolator mInterpolator;

    private Path mEaseInPath;

    private Path mEaseOutPath;

    private Path mEaseInOutPath;

    private Path mPathInUse;

    private Paint mPaint;

    private int mHeight;

    private int mWidth;

    private AAnimator mAnimator;

    private int mShapeWidth;

    private CPUUsage mCPUUsage;

    public AAnimationView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.ic_launcher);
        mShapeWidth = mBitmap.getWidth();

        mValuesHolderX = new ValuesHolder();
        mValuesHolderX.setValue(0);
        mValuesHolderY = new ValuesHolder();
        mValuesHolderY.setValue(0);
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(5);
        mPaint.setColor(Color.BLUE);

        mAnimator = new AAnimator();
        mAnimator.setAnimationUpdateListener(this);
        mAnimator.setDuration(2000);
        mAnimator.setupAnimate(mValuesHolderX, 0, this.getWidth() - mShapeWidth);
        mAnimator.setInterpolator(new QuadInterpolator(Type.IN)); // default
                                                                  // Interpolator

        mAnimator.setRepeatMode(REPEAT_MODE.REVERSE);
        mAnimator.setRepeatCount(5);
//        mAnimator.setTimeDelay(2000);
//        mAnimator.setFrameDelay(500);
        mCPUUsage = new CPUUsage();
    }

    @Override
    public void draw(Canvas canvas) {
        mCPUUsage.update();
        canvas.drawBitmap(mBitmap, mValuesHolderX.getValue(), mValuesHolderY.getValue(), null);
        if (mEaseInPath != null) {
            mPaint.setColor(Color.YELLOW);
            canvas.drawPath(mEaseInPath, mPaint);
        }
        if (mEaseOutPath != null) {
            mPaint.setColor(Color.BLUE);
            canvas.drawPath(mEaseOutPath, mPaint);
        }
        if (mEaseInOutPath != null) {
            mPaint.setColor(Color.RED);
            canvas.drawPath(mEaseInOutPath, mPaint);
        }
        if (mPathInUse != null) {
            mPaint.setColor(Color.GREEN);
            canvas.drawPath(mPathInUse, mPaint);
        }
        super.draw(canvas);
    }

    public ValuesHolder getValuesHolderX() {
        return mValuesHolderX;
    }

    public ValuesHolder getValuesHolderY() {
        return mValuesHolderY;
    }

    public void setInterpoltor(AInterpolator interpolator) {
        mAnimator.setInterpolator(interpolator);
        mInterpolator = interpolator;
        if (mInterpolator != null) {
            // ----------------Path in------------------------------
            mEaseInPath = new Path();
            mEaseInPath.moveTo(0, mHeight * .8f - mInterpolator.getInterpolation(0)
                    * (mHeight * .5f));
            for (float i = 0.01f; i < 1; i += 0.01f) {
                mEaseInPath.lineTo(mWidth * i, mHeight * 0.8f - mInterpolator.getInterpolation(i)
                        * (mHeight * 0.5f));
            }
            // ----------------------Path out----------------------
            if (mInterpolator instanceof AInterpolator) {
                mEaseOutPath = new Path();
                ((AInterpolator) mInterpolator).setType(Type.OUT);
                mEaseOutPath.moveTo(0, mHeight * .8f - mInterpolator.getInterpolation(0)
                        * (mHeight * .5f));
                for (float i = 0.01f; i < 1; i += 0.01f) {
                    mEaseOutPath.lineTo(mWidth * i,
                            mHeight * 0.8f - mInterpolator.getInterpolation(i) * (mHeight * 0.5f));
                }
                // ----------------------Path InOut---------------------
                mEaseInOutPath = new Path();
                ((AInterpolator) mInterpolator).setType(Type.INOUT);
                mEaseInOutPath.moveTo(0, mHeight * .8f - mInterpolator.getInterpolation(0)
                        * (mHeight * .5f));
                for (float i = 0.01f; i < 1; i += 0.01f) {
                    mEaseInOutPath.lineTo(mWidth * i,
                            mHeight * 0.8f - mInterpolator.getInterpolation(i) * (mHeight * 0.5f));
                }
                ((AInterpolator) mInterpolator).setType(Type.IN);
            }
        }
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        mHeight = h;
        mWidth = w;
        mAnimator.setupAnimate(mValuesHolderX, 0, mWidth - mShapeWidth);
        super.onSizeChanged(w, h, oldw, oldh);
    }

    public AAnimator getAnimator() {
        return mAnimator;
    }

    public void startAnimation() {
        if (mAnimator != null) {
            mAnimator.start();
        }
    }

    @Override
    public void AnimationUpdate(AAnimator animator) {
        if (mPathInUse == null) {
            mPathInUse = new Path();
        } else {
            mPathInUse.reset();
        }
        mPathInUse.moveTo(0, mHeight * .8f - mInterpolator.getInterpolation(0) * (mHeight * .5f));
        Log.i("xzy", "interpolatorTime is : " + mAnimator.getNormalizedTime());
        for (float i = 0.01f; i < mAnimator.getNormalizedTime(); i += 0.01f) {
            mPathInUse.lineTo(mWidth * i, mHeight * 0.8f - mInterpolator.getInterpolation(i)
                    * (mHeight * 0.5f));
        }
        this.invalidate();
    }

}
