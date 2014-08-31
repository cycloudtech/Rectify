package com.shuheikagawa.rectify;

import android.content.Context;
import android.hardware.Camera;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.io.IOException;

public class CameraPreview extends SurfaceView implements SurfaceHolder.Callback {
    private Camera camera;
    private SurfaceHolder holder;
    private static final String DEBUG_TAG = "CameraPreview";

    public CameraPreview(Context context, Camera camera) {
        super(context);

        this.camera = camera;

        holder = getHolder();
        holder.addCallback(this);
        // We don't need to call holder.setType() anymore on Android 3.0 and later.
    }

    @Override
    public void surfaceCreated(SurfaceHolder surfaceHolder) {
        Log.d(DEBUG_TAG, "Starting camera preview.");

        try {
            camera.setPreviewDisplay(holder);
            camera.startPreview();
        } catch (IOException e) {
            Log.d(DEBUG_TAG, "Got an error setting camera preview.");
            e.printStackTrace();
        }

        Log.d(DEBUG_TAG, "Started camera preview.");
    }

    @Override
    public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i2, int i3) {
        if (holder.getSurface() == null) {
            Log.d(DEBUG_TAG, "Preview surface does not exist.");
            return;
        }

        try {
            camera.stopPreview();
        } catch (Exception e ) {
            Log.d(DEBUG_TAG, "Tried to stop a non-existent preview.");
        }

        // TODO: If you set a specific size for camera preview, set preview size and
        // make any resize, rotate or reformatting changes here.

        // Start preview with the new setting.
        try {
            camera.setPreviewDisplay(holder);
            camera.startPreview();
        } catch (IOException e) {
            Log.d(DEBUG_TAG, "Got an error starting camera preview.");
        }
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
        // No op.
    }
}