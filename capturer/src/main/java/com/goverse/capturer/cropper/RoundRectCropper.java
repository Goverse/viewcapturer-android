package com.goverse.capturer.cropper;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.view.View;

public class RoundRectCropper implements ICropper {

    private Rect mRect;
    private float mRadius;
    public RoundRectCropper(Rect rect, float radius) {
        mRect = rect;
        mRadius = radius;
    }

    @Override
    public void crop(View view, OnCropListener onCropListener) {

        view.measure(View.MeasureSpec.makeMeasureSpec(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED),
                View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
        view.layout(view.getLeft(), view.getTop(), view.getMeasuredWidth(), view.getHeight() + view.getTop());

        Bitmap viewBitmap = Bitmap.createBitmap(mRect.right,
                mRect.bottom, Bitmap.Config.RGB_565);
        Canvas viewCanvas = new Canvas(viewBitmap);
        view.draw(viewCanvas);

        Rect destRect = new Rect(0, 0, mRect.right - mRect.left, mRect.bottom - mRect.top);
        Bitmap destBitmap = Bitmap.createBitmap(destRect.width(),
                destRect.height(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(destBitmap);

        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        RectF srcRect = new RectF(0, 0, destBitmap.getWidth(), destBitmap.getHeight());
        canvas.drawRoundRect(srcRect, mRadius, mRadius,  paint);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(viewBitmap, mRect, destRect, paint);

        onCropListener.onCropped(destBitmap);
    }
}
