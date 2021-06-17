package com.goverse.capturer.cropper;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.View;

public class RectCropper implements ICropper {

    private Rect mRect;
    public RectCropper(Rect rect) {
        mRect = rect;
    }

    @Override
    public void crop(View view, OnCropListener onCropListener) {

        view.measure(View.MeasureSpec.makeMeasureSpec(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED),
                View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
        view.layout(view.getLeft(), view.getTop(), view.getMeasuredWidth(), view.getHeight() + view.getTop());

        Bitmap viewBitmap = Bitmap.createBitmap(mRect.right,
                mRect.bottom, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(viewBitmap);
        view.draw(canvas);

        Rect destRect = new Rect(0, 0, mRect.right - mRect.left, mRect.bottom - mRect.top);
        Bitmap destBitmap = Bitmap.createBitmap(destRect.width(),
                destRect.height(), Bitmap.Config.ARGB_8888);
        canvas.setBitmap(destBitmap);
        canvas.drawBitmap(viewBitmap, mRect, destRect, new Paint());
        onCropListener.onCropped(destBitmap);
    }
}
