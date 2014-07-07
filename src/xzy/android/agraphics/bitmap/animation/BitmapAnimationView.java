
package xzy.android.agraphics.bitmap.animation;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.View;

/**
 * (C) 2012 zhengyang.xu
 *
 * @author zhengyang.xu
 * @version 0.1
 * @since 11:42:49 AM Jun 5, 2012
 */
public class BitmapAnimationView extends View {

    private Bitmap mBitmapFrom;

    private Bitmap mBitmapTo;

    public static enum Direction {
        LEFT, RIGHT
    }

    public BitmapAnimationView(Context context) {
        super(context);
        // TODO Auto-generated constructor stub
    }

    public BitmapAnimationView(Context context, AttributeSet attrs) {
        super(context, attrs);
        // TODO Auto-generated constructor stub
    }

    public BitmapAnimationView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        // TODO Auto-generated constructor stub
    }

    public void setBitmapFrom(Bitmap bitmap) {

        if (mBitmapFrom != null) { // TODO : may be not right
            mBitmapFrom.recycle();
            mBitmapFrom = null;
        }
        mBitmapFrom = bitmap;
    }

    public void setBitmapTo(Bitmap bitmap) {

        if (mBitmapTo != null) {
            mBitmapTo.recycle();
            mBitmapTo = null;
        }
        mBitmapTo = bitmap;
    }

    private void createBitmapFrom() {
        if (mBitmapFrom != null) {
            mBitmapFrom.recycle();
            mBitmapFrom = null;
        }

        // TODO
    }

    private void createBitmapTo() {
        if (mBitmapTo != null) {
            mBitmapTo.recycle();
            mBitmapTo = null;
        }
        // TODO
    }

    @Override
    protected void onDraw(Canvas canvas) {
        // TODO Auto-generated method stub
        super.onDraw(canvas);
    }
}
