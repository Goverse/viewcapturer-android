package com.goverse.capturer.cropper;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.view.View;

public class WhiteBgCropper implements ICropper {
    private int color = -1;

    public WhiteBgCropper(int color) {
        this.color = color;
    }

    @Override
    public void crop(View view, OnCropListener onCropListener) {

        Bitmap destBitmap = Bitmap.createBitmap(view.getWidth(), view.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(destBitmap);
        canvas.drawColor(color);
        view.draw(canvas);
        onCropListener.onCropped(destBitmap);
    }
}
