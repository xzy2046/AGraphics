
package xzy.android.agraphics.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import xzy.android.agraphics.R;
import xzy.android.agraphics.highlightpen.HighLightPenView;

public class HighLightPenActivity extends Activity {

    HighLightPenView mView;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /*setContentView(R.layout.activity_main);*/
        mView  = new HighLightPenView(this);
        setContentView(mView);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        
        switch (item.getItemId()) {
            case R.id.action_highlight_pen:
                mView.setPaintMode(HighLightPenView.PaintMode.HighLight);
                break;
            case R.id.action_pencil:
                mView.setPaintMode(HighLightPenView.PaintMode.Pencil);
                break;
        }
        mView.clearPath();
        return super.onOptionsItemSelected(item);
    }
    
    public void onGroupItemClick(MenuItem item) {
        // One of the group items (using the onClick attribute) was clicked
        // The item parameter passed here indicates which item it is
        // All other menu item clicks are handled by onOptionsItemSelected()
    }

    @Override
    protected void onPause() {
//        finish();
//        System.exit(0);
        super.onPause();
    }

}
