package com.goverse.capturer.cropper;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Shader;
import android.view.View;

public class LinearBgCropper implements ICropper {

    private int startColor;
    private int endColor;

    public LinearBgCropper(int startColor, int endColor) {
        this.startColor = startColor;
        this.endColor = endColor;
    }

    @Override
    public void crop(View view, OnCropListener onCropListener) {
        Bitmap destBitmap = Bitmap.createBitmap(view.getWidth(), view.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(destBitmap);
        LinearGradient linearGradient = new LinearGradient(view.getWidth() / 2f, 0, view.getWidth() / 2f, view.getHeight(), startColor, endColor, Shader.TileMode.CLAMP);
        Paint paint = new Paint();
        paint.setShader(linearGradient);
        canvas.drawRect(0, 0, view.getWidth(), view.getHeight(), paint);
        view.draw(canvas);
        onCropListener.onCropped(destBitmap);
    }
}
