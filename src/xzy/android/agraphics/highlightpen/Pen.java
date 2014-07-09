
package xzy.android.agraphics.highlightpen;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PointF;

import java.util.ArrayList;

abstract class Pen {
    protected Path mHighLightPath;
    protected ArrayList<PointF> mPathArray;
    protected Paint mPaint;

    abstract void draw(Canvas canvas);

    abstract Paint createPaint();
    
    public Path createPath() {
        mHighLightPath = new Path();
        return mHighLightPath;
    }

    public ArrayList<PointF> createPathArray() {
        mPathArray = new ArrayList<PointF>();
        return mPathArray;
    }

    protected double calculateAngle(float x, float y, float centerX, float centerY) {
        double angle = Math.atan2(y - centerY, x - centerX) * 180 / Math.PI;
        if (angle < 0) {
            angle += 360;
        }
        return angle;
    }
}
