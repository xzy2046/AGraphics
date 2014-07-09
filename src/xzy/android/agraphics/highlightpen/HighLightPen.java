package xzy.android.agraphics.highlightpen;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PointF;
import android.graphics.Shader;
import android.util.Log;


public class HighLightPen extends Pen {

    private Bitmap mHighLightPoint;
    
    private static final int SHADER_WIDTH = 20;
    
    static final int PAINT_STROKE_WIDTH = 80;
    
    private static final float[] ArrayX = {
        0.1f, 1f, 0.125f, 0.95f, 0.65f, 0.0f, 0.94f, 0.1f, 0.91f, 0.25f, 0.99f, 0.1f, 0.125f,
        0.925f, 0.815f, 0.12f, 0.86f, 0.635f, 0.1f, 1f, 0.125f, 0.95f, 0.65f, 0.0f, 0.94f,
        0.1f, 0.91f, 0.25f, 0.99f, 0.1f, 0.125f, 0.925f, 0.815f, 0.12f, 0.86f, 0.635f,
    };
    
    public HighLightPen() {
        mHighLightPoint = buildPaintPoint();
    }

    @Override
    public void draw(Canvas canvas) {
        canvas.drawColor(Color.argb(0, 0, 0, 0));
        boolean debug = false;

        if (!mHighLightPath.isEmpty()) {
            canvas.drawPath(mHighLightPath, mPaint);
            double startTime = System.currentTimeMillis();

            debug = true;
            if (debug) {
                if (mPathArray.size() > 1) {
                    PointF start = mPathArray.get(0);
                    PointF end = mPathArray.get(1);
                    canvas.save();
                    canvas.rotate((float) calculateAngle(end.x, end.y, start.x, start.y), start.x,
                            start.y);
                    canvas.drawBitmap(mHighLightPoint, start.x - PAINT_STROKE_WIDTH
                            / 2 - 2, start.y - PAINT_STROKE_WIDTH
                            / 2, null);
//                    canvas.drawBitmap(mHighLightPointWhite, start.x - PAINT_STROKE_WIDTH / 2 - 2, start.y - PAINT_STROKE_WIDTH
//                            / 2, null);
                    canvas.restore();

                    if (!HighLightPenView.sIsInTouch) {
                        start = mPathArray.get(mPathArray.size() - 1);
                        end = mPathArray.get(mPathArray.size() - 2);
                        canvas.save();
                        canvas.rotate((float) calculateAngle(end.x, end.y, start.x, start.y),
                                start.x,
                                start.y);
                        canvas.drawBitmap(mHighLightPoint, start.x
                                - PAINT_STROKE_WIDTH / 2 - 2, start.y
                                - PAINT_STROKE_WIDTH
                                / 2, null);
                        canvas.restore();
                    }
                }
            }
            
            Log.i("xzy", "high Light Pen Time is : " + (System.currentTimeMillis() - startTime));
        }

        debug = false;
        if (debug) {
            canvas.drawBitmap(mHighLightPoint, 100, 100, null);

            canvas.save();
            canvas.translate(-100, -200);
            canvas.scale(2, 2);
            canvas.translate(100, 200);
            canvas.drawBitmap(mHighLightPoint, 0, 0, null);
            canvas.restore();
        }
    }
    
    private Bitmap buildPaintPoint() {
        Bitmap bitmap = Bitmap.createBitmap(20, PAINT_STROKE_WIDTH, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas();
        canvas.setBitmap(bitmap);
        Path path = new Path();
        path.moveTo(0, 0);
        path.lineTo(SHADER_WIDTH, 0);
        path.lineTo(SHADER_WIDTH, PAINT_STROKE_WIDTH);
        path.lineTo(0, PAINT_STROKE_WIDTH);
        float delta = 4.5f;
        float halfDelta = delta / 2f;
        for (int i = 0; i < 30; i++) {
            path.lineTo((float) (halfDelta * ArrayX[i]), PAINT_STROKE_WIDTH - i
                    * PAINT_STROKE_WIDTH / 30);
        }

        Paint paint = new Paint();
        paint.setStyle(Paint.Style.FILL);
        paint.setAntiAlias(true);

        //change paint point color
        paint.setShader(new LinearGradient(0, 0,
                SHADER_WIDTH, 0, Color.argb(150, 255, 0, 0), Color
                        .argb(0, 255, 0, 0), Shader.TileMode.CLAMP));

        canvas.drawPath(path, paint);

        return bitmap;
    }

    @Override
    public Paint createPaint() {
        mPaint = new Paint();
        mPaint = new Paint();
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setAntiAlias(true);
        mPaint.setColor(Color.RED);
        mPaint.setAlpha(122);
        mPaint.setStrokeWidth(PAINT_STROKE_WIDTH);
    
        mPaint.setStrokeJoin(Paint.Join.ROUND);
        mPaint.setStrokeCap(Paint.Cap.SQUARE);
        return mPaint;
    }
}
