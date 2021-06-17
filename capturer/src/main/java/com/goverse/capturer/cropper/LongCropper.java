package com.goverse.capturer.cropper;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.View;

public class LongCropper implements ICropper{
    @Override
    public void crop(View view, OnCropListener onCropListener) {
        view.measure(View.MeasureSpec.makeMeasureSpec(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED),
                View.MeasureSpec.makeMeasureSpec(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED));
        view.layout(0, 0, view.getMeasuredWidth(), view.getMeasuredHeight());
        Bitmap longImage = Bitmap.createBitmap(view.getMeasuredWidth(),
                view.getMeasuredHeight(), Bitmap.Config.ARGB_8888);

        Canvas canvas = new Canvas(longImage);
        Paint paint = new Paint();
        canvas.drawBitmap(longImage, 0, view.getMeasuredHeight(), paint);
        view.draw(canvas);
        view.requestLayout();
        onCropListener.onCropped(longImage);
    }
}
