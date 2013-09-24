package jp.tsmsogn.camerapreview;

import jp.tsmsogn.camerapreview.view.CameraPreview;
import jp.tsmsogn.camerapreview.R;
import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.Window;
import android.widget.FrameLayout;

public class MainActivity extends Activity {

    private CameraPreview mCameraPreview;
    private FrameLayout mFrameLayout1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);

        mCameraPreview = new CameraPreview(this);

        mFrameLayout1 = (FrameLayout) findViewById(R.id.frameLayout1);
        mFrameLayout1.addView(mCameraPreview);
    }

}
