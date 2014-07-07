package xzy.android.agraphics.activity;

import android.app.Activity;
import android.os.Bundle;

import xzy.android.agraphics.blur.MyView;

/**
 * (C) 2012 zhengyang.xu
 *
 * @author zhengyang.xu
 * @version 0.1
 * @since 1:10:31 PM Jun 5, 2012
 */
public class BlurActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        setContentView(new MyView(this));
        super.onCreate(savedInstanceState);
    }

}
