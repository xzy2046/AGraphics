
package xzy.android.agraphics.highlightpen;

import android.graphics.BlurMaskFilter;
import android.graphics.BlurMaskFilter.Blur;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.MaskFilter;
import android.graphics.Paint;
import android.util.Log;

public class Pencil extends Pen {

    private MaskFilter mBlur;

    private final static int PAINT_STROKE_WIDTH = 64;

    public Pencil() {
        mBlur = new BlurMaskFilter(2, Blur.NORMAL);
    }

    @Override
    public void draw(Canvas canvas) {
        double startTime = System.currentTimeMillis();
        canvas.drawColor(Color.argb(0, 0, 0, 0));
        boolean debug = false;

        if (!mHighLightPath.isEmpty()) {
            canvas.drawPath(mHighLightPath, mPaint);
        }
        Log.i("xzy", "high Light Pen Time is : " + (System.currentTimeMillis() - startTime));

    }

    public Paint createPaint() {
        mPaint = new Paint();
        mPaint = new Paint();
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setAntiAlias(true);
        mPaint.setColor(Color.RED);
        mPaint.setAlpha(122);
        mPaint.setStrokeWidth(PAINT_STROKE_WIDTH);

        // mPaint.setStrokeJoin(Paint.Join.ROUND);
        // mPaint.setStrokeCap(Paint.Cap.SQUARE);

//        mPaint.setMaskFilter(mBlur);
//
//        ColorMatrix cm = new ColorMatrix();
//        cm.set(new float[] {
//                0, 0, 0, 0, 300f,
//                0, 1, 0, 0, 300f,
//                0, 0, 1, 0, 300f,
//                0, 0, 0, 1, 0
//        });
//
//        mPaint.setColorFilter(new ColorMatrixColorFilter(cm));
        return mPaint;
    }

}
