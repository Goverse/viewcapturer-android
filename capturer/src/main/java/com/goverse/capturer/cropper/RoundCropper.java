package com.goverse.capturer.cropper;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.view.View;

public class RoundCropper implements ICropper {

    private int mLeft;
    private int mTop;
    private int mRadius;
    public RoundCropper(int left, int top, int radius) {

        mLeft = left;
        mTop = top;
        mRadius = radius;
    }

    @Override
    public void crop(View view, OnCropListener onCropListener) {

        view.measure(View.MeasureSpec.makeMeasureSpec(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED),
                View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
        view.layout(0, 0, view.getMeasuredWidth(), view.getMeasuredHeight());
        Bitmap longBitmap = Bitmap.createBitmap(view.getMeasuredWidth(),
                view.getMeasuredHeight(), Bitmap.Config.RGB_565);

        Canvas longCanvas = new Canvas(longBitmap);
        Paint paint = new Paint();
        longCanvas.drawBitmap(longBitmap, 0, view.getMeasuredHeight(), paint);
        view.draw(longCanvas);

        Bitmap destBitmap = Bitmap.createBitmap(mRadius * 2, mRadius * 2, Bitmap.Config.RGB_565);
        Canvas canvas = new Canvas(destBitmap);
        RectF rectF = new RectF(0, 0, mRadius, mRadius);
        canvas.drawOval(rectF, paint);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        Rect rect = new Rect(mLeft, mTop, mLeft + mRadius  * 2, mTop + mRadius * 2);
        canvas.drawBitmap(longBitmap, rect, rectF, paint);
        onCropListener.onCropped(destBitmap);
    }
}
