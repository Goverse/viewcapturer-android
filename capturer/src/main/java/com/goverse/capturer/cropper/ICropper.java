package com.goverse.capturer.cropper;

import android.graphics.Bitmap;
import android.view.View;

public interface ICropper {

    /**
     * Interface definition for a callback to be invoked after cropping view.
     */
    interface OnCropListener {
        void onCropped(Bitmap bitmap);
    }
    /**
     * Called to clip the view during capturing.
     * @param view view
     * @param onCropListener onCropListener
     */
    void crop(View view, OnCropListener onCropListener);
}

