package jp.tsmsogn.camerapreview.view;

import java.io.IOException;
import java.util.List;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.hardware.Camera;
import android.hardware.Camera.Size;
import android.util.Log;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.ViewGroup.LayoutParams;
import android.widget.FrameLayout;

/**
 * Created by work on 2013/07/04.
 */
@SuppressLint("NewApi")
public class CameraPreview extends SurfaceView implements
        SurfaceHolder.Callback {
    private final String TAG = getClass().getCanonicalName();
    private final Activity mActivity;
    private Camera mCamera;
    private final SurfaceHolder mHolder;

    public CameraPreview(Activity activity) {
        super(activity);

        mActivity = activity;

        mHolder = getHolder();
        mHolder.addCallback(this);
        mHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
    }

    @Override
    public void surfaceCreated(SurfaceHolder surfaceHolder) {
        Log.v(TAG, "surfaceCreated");
        mCamera = Camera.open();

        if (mCamera != null) {
            try {
                mCamera.setPreviewDisplay(surfaceHolder);
                mCamera.startPreview();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void surfaceChanged(SurfaceHolder surfaceHolder, int format, int w,
            int h) {
        Log.v(TAG, "surfaceChanged");
        if (mHolder.getSurface() == null) {
            return;
        }

        if (mCamera != null) {
            mCamera.stopPreview();

            Log.d("CameraView", w + ", " + h);

            setCameraDisplayOrientation(mActivity, 0, mCamera);

            try {
                mCamera.setPreviewDisplay(mHolder);
                mCamera.startPreview();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
        Log.v(TAG, "surfaceDestroyed");
        if (mCamera != null) {
            mCamera.stopPreview();
            mCamera.release();
            mCamera = null;
        }
    }

    public static void setCameraDisplayOrientation(Activity activity,
            int cameraId, android.hardware.Camera camera) {
        android.hardware.Camera.CameraInfo info = new android.hardware.Camera.CameraInfo();
        android.hardware.Camera.getCameraInfo(cameraId, info);
        int rotation = activity.getWindowManager().getDefaultDisplay()
                .getRotation();
        int degrees = 0;
        switch (rotation) {
        case Surface.ROTATION_0:
            degrees = 0;
            break;
        case Surface.ROTATION_90:
            degrees = 90;
            break;
        case Surface.ROTATION_180:
            degrees = 180;
            break;
        case Surface.ROTATION_270:
            degrees = 270;
            break;
        }

        int result;
        if (info.facing == Camera.CameraInfo.CAMERA_FACING_FRONT) {
            result = (info.orientation + degrees) % 360;
            result = (360 - result) % 360; // compensate the mirror
        } else { // back-facing
            result = (info.orientation - degrees + 360) % 360;
        }
        camera.setDisplayOrientation(result);
    }

    @Override
    public SurfaceHolder getHolder() {

        return super.getHolder();
    }

}
