
package xzy.android.agraphics.highlightpen;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PointF;
import android.graphics.Shader;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;

import java.util.ArrayList;

public class HighLightPenView extends View {

    private Path mHighLightPath;

    private Paint mPaint;

    private Bitmap mHighLightPoint;
    
    private Bitmap mHighLightPointWhite;

    private ArrayList<PointF> mPathArray;

    private ViewConfiguration mVC;

    static boolean sIsInTouch;

    private static final int PAINT_STROKE_WIDTH = 80;

    private static final int SHADER_WIDTH = 20;

    private static final float[] ArrayX = {
            0.1f, 1f, 0.125f, 0.95f, 0.65f, 0.0f, 0.94f, 0.1f, 0.91f, 0.25f, 0.99f, 0.1f, 0.125f,
            0.925f, 0.815f, 0.12f, 0.86f, 0.635f, 0.1f, 1f, 0.125f, 0.95f, 0.65f, 0.0f, 0.94f,
            0.1f, 0.91f, 0.25f, 0.99f, 0.1f, 0.125f, 0.925f, 0.815f, 0.12f, 0.86f, 0.635f,
    };
    
    public enum PaintMode {
        HighLight, Pencil,
    }

    private PaintMode mPaintMode = PaintMode.HighLight;
    
    private Pen mPen;
    
    public HighLightPenView(Context context) {
        super(context);
        init();
    }

    public HighLightPenView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public HighLightPenView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    private void init() {
        
        setPaintMode(mPaintMode);
        

        mHighLightPoint = buildPaintPoint();
        mHighLightPointWhite = buildPaintPointWhite();
        
        mVC = ViewConfiguration.get(getContext());
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawColor(Color.WHITE);
        /*
        canvas.drawColor(Color.argb(0, 0, 0, 0));
        boolean debug = false;

        if (!mHighLightPath.isEmpty()) {
            canvas.drawPath(mHighLightPath, mPaint);
            double startTime = System.currentTimeMillis();

            if (debug) {
                if (mPathArray.size() > 2) {
                    PointF start = mPathArray.get(0);
                    PointF end = mPathArray.get(1);
                    PointF extendPoint = extentPoint(start, end, 10);
                    PointF halfExtendPoint = extentPoint(start, end, 5);
                    int[] colors = {
                            Color.argb(255, 255, 0, 0), Color
                                    .argb(0, 255, 0, 0), Color
                                    .argb(0, 255, 0, 0)
                    };
                    float[] positions = {
                            0, 0.5f, 1f
                    };

                    mPaint.setShader(new LinearGradient(start.x, start.y,
                            halfExtendPoint.x, halfExtendPoint.y, colors, positions,
                            Shader.TileMode.CLAMP));

                    Path path = new Path();
                    path.moveTo(start.x, start.y);
                    path.lineTo(extendPoint.x, extendPoint.y);
                    canvas.drawPath(path, mPaint);
                    mPaint.setShader(null);
                }
            }

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

                    if (!mIsInTouch) {
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
            
            Log.i("xzy", "time is : " + (System.currentTimeMillis() - startTime));
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
        super.onDraw(canvas);
    */
        mPen.draw(canvas);
        super.onDraw(canvas);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Log.i("xzy", "event.toString() " + event.toString());
        float x = event.getX();
        float y = event.getY();
        PointF point = new PointF(x, y);
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                sIsInTouch = true;
                mHighLightPath.reset();
                mPathArray.clear();
                mHighLightPath.moveTo(x, y);
                mPathArray.add(point);
                break;
            case MotionEvent.ACTION_MOVE:
                float dx = Math.abs(x - mPathArray.get(mPathArray.size() - 1).x);
                if (dx >  /*mVC.getScaledTouchSlop()*/ 3) {
                    
//                    mHighLightPath.quadTo(mPathArray.get(mPathArray.size() - 1).x,
//                            mPathArray.get(mPathArray.size() - 1).y,
//                            (x + mPathArray.get(mPathArray.size() - 1).x) / 2,
//                            (y + mPathArray.get(mPathArray.size() - 1).y) / 2);
//                    mPathArray.add(new PointF((float)Math.hypot(mPathArray.get(mPathArray.size() - 1).x, (x + mPathArray.get(mPathArray.size() - 1).x) / 2),
//                            (float)Math.hypot(mPathArray.get(mPathArray.size() - 1).y, (x + mPathArray.get(mPathArray.size() - 1).y) / 2)));
                    mHighLightPath.lineTo(x, y);
                    mPathArray.add(point);
                }
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                float dx2 = Math.abs(x - mPathArray.get(mPathArray.size() - 1).x);
                if (dx2 < mVC.getScaledTouchSlop()) {
                    if (mPathArray.size() < 2) {
                        PointF p = new PointF(mPathArray.get(0).x + PAINT_STROKE_WIDTH / 2,
                                mPathArray.get(0).y);
                        mHighLightPath.lineTo(p.x, p.y);
                        mPathArray.add(p);
                    }/* else {
                        mHighLightPath.lineTo(x, y);
                        mPathArray.add(point);
                    }*/
                } else {
                    mHighLightPath.lineTo(x, y);
                    mPathArray.add(point);
                }
                sIsInTouch = false;
                break;
        }
        this.invalidate();
        return true;
    }

    public void clearPath() {
        mHighLightPath.reset();
        invalidate();
    }

    public static PointF extentPoint(PointF startPoint, PointF endPoint, float disToStartPoint) {
        float dx = endPoint.x - startPoint.x;
        float dy = endPoint.y - startPoint.y;
        float dis = (float) Math.sqrt(dx * dx + dy * dy);
        float sin = (endPoint.y - startPoint.y) / dis;
        float cos = (endPoint.x - startPoint.x) / dis;
        float deltaX = disToStartPoint * cos;
        float deltaY = disToStartPoint * sin;
        return new PointF(startPoint.x + deltaX, startPoint.y + deltaY);
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
    
    private Bitmap buildPaintPointWhite() {
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
                SHADER_WIDTH, 0, Color.argb(255, 255, 255, 255), Color
                        .argb(0, 255, 255, 255), Shader.TileMode.CLAMP));

        canvas.drawPath(path, paint);

        return bitmap;
    }
    
    public void setPaintMode(PaintMode paintMode) {
        mPaintMode = paintMode;
        if (mPaintMode == PaintMode.HighLight) {
            mPen = new HighLightPen();
        } else {
            mPen = new Pencil();
        }
        mPathArray = mPen.createPathArray();
        mPaint = mPen.createPaint();
        mHighLightPath = mPen.createPath();
    }
}
