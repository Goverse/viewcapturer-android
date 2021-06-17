package com.goverse.capturer.cropper;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.view.View;

public class DefaultCropper implements ICropper{

    @Override
    public void crop(View view, OnCropListener onCropListener) {

        Bitmap destBitmap = Bitmap.createBitmap(view.getWidth(), view.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(destBitmap);
        view.draw(canvas);
        onCropListener.onCropped(destBitmap);
    }
}
