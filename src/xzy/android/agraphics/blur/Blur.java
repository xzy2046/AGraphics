package xzy.android.agraphics.blur;
/**
 * @author xuchdeid@gmail.com
 *  __________________________     \_/
   |                          |   /._.\
   |  Android!Android!         > U|   |U
   |                xuchdeid  |   |___|
   |__________________________|    U U
 * */

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Build;
import android.support.v4.util.LruCache;
import android.util.Log;

import com.koalcat.view.JNIRender;
//import android.os.Build;

public class Blur {
	
	private static final String TAG = "Blur";
	
	private boolean USE_JNI = false;
	
	private static final float BITMAP_SCALE_NOMAL = 0.8f;//0.8f;
	private static final float BLUR_RADIUS_NOMAL = 12f;//12.0f;
	
//	public static float BITMAP_SCALE_FAST = 0.5f; //0.5f
//	public static float BLUR_RADIUS_FAST = 35.0f;  //10
	public static float BITMAP_SCALE_FAST = 0.5f; //0.5f
	public static float BLUR_RADIUS_FAST = 10.0f;  //10

	private BaseRender mRender;

	private LruCache<String, Bitmap> mMemoryCache; 

	public Blur(Context context) {
		
		if (!USE_JNI) {
			int sdk = Build.VERSION.SDK_INT;
			if (sdk < 17) {
				USE_JNI = true;
			} else {
				USE_JNI = false;
			}
		}
		
		if (!USE_JNI) {
			Log.i("xzy", "use rs");
			mRender = new ScriptIntrinsicBlurRender(context);
			//mRender = new BlurRSRender(context);
		} else {
		    Log.i("xzy", "use jni");
			mRender = new JNIRender();
		}
		
		
		//use LRUCahce to cache bitmap.  unit: KB
		final int maxMemory = (int) (Runtime.getRuntime().maxMemory() / 1024);  
		final int cacheSize = maxMemory / 16;  
		
		
		mMemoryCache = new LruCache<String, Bitmap>(cacheSize) {  
			@Override  
			protected int sizeOf(String key, Bitmap bitmap) {  
				return bitmap.getByteCount() / 1024;  
			}  
		};
	}

	public Bitmap blur(Bitmap image, boolean fast) {

		float scale = BITMAP_SCALE_NOMAL;
		float radius = BLUR_RADIUS_NOMAL;
		
		//use for test start
		if (BITMAP_SCALE_FAST < 1.0f) {
			BITMAP_SCALE_FAST += 0.01f;
		} else {
			BITMAP_SCALE_FAST = 0.1f;
		}
		
		if (BLUR_RADIUS_FAST < 25f) {
			BLUR_RADIUS_FAST += 1f;
		} else {
			BLUR_RADIUS_FAST = 1f;
		}
		
		Log.i("xzy", "BITMAP_SCALE_FAST is : " + BITMAP_SCALE_FAST
				+ "  BLUR_RADIUS_FAST is " + BLUR_RADIUS_FAST);
		//use for test end
		if (fast) {
			scale = BITMAP_SCALE_FAST;
			radius = BLUR_RADIUS_FAST;
		}
		
		int width = Math.round(image.getWidth() * scale);
		int height = Math.round(image.getHeight() * scale);

		Bitmap bitmap = Bitmap.createScaledBitmap(image, width, height, true);
		Bitmap outputBitmap = getBitmapFromMemCache("" + width + height);
		if (outputBitmap == null) {
			outputBitmap = bitmap.copy(bitmap.getConfig(), true);
			addBitmapToMemoryCache("" + width + height, outputBitmap);
		}

		mRender.blur(radius, bitmap, outputBitmap);
		bitmap.recycle();
		
		return outputBitmap;
	}
	
	private void addBitmapToMemoryCache(String key, Bitmap bitmap) {  
	    if (getBitmapFromMemCache(key) == null) {  
	        mMemoryCache.put(key, bitmap);  
	    }  
	}  
	  
	private Bitmap getBitmapFromMemCache(String key) {  
	    return mMemoryCache.get(key);  
	}
    
	public void Destroy() {
		if (mRender != null) mRender.destroy();
	}
	
/*	public Bitmap Blur_openGL(Bitmap image, boolean fast) {
		float scale = BITMAP_SCALE_NOMAL;
		float radius = BLUR_RADIUS_NOMAL;
		
		if (fast) {
			scale = BITMAP_SCALE_FAST;
			radius = BLUR_RADIUS_FAST;
		}
		
		int width = Math.round(image.getWidth() * scale);
		int height = Math.round(image.getHeight() * scale);

/*		Bitmap bitmap = Bitmap.createScaledBitmap(image, width, height, true);
		Bitmap outputBitmap = getBitmapFromMemCache("" + width + height);
		if (outputBitmap == null) {
			outputBitmap = bitmap.copy(bitmap.getConfig(), true);
			addBitmapToMemoryCache("" + width + height, outputBitmap);
		}
*/		
/*		if (gl == null) {
			gl = new GLRender(image.getWidth(), image.getHeight());
		}
		
		gl.Blur(image, fast);
		
		return image;
		
	}
	*/
}

