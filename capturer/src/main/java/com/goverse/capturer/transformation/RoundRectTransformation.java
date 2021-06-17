package com.goverse.capturer.transformation;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;

public class RoundRectTransformation implements ITransformation {

    private float mRadiusX;
    private float mRadiusY;

    public RoundRectTransformation(float radiusX, float radiusY) {
        mRadiusX = radiusX;
        mRadiusY = radiusY;
    }

    @Override
    public Bitmap transform(Bitmap bitmap) {

        Canvas canvas = new Canvas(bitmap);

        Paint paint = new Paint();
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(Color.TRANSPARENT);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR));
        Path path = new Path();
        // left top
        path.lineTo(0, mRadiusY);
        path.lineTo(mRadiusX, 0);
        path.moveTo(0, mRadiusY);
        path.addArc(new RectF(0, 0, mRadiusX * 2, mRadiusY * 2), 180, 90);
        canvas.drawPath(path, paint);

        // right top
        path.reset();
        path.moveTo(bitmap.getWidth(), 0);
        path.lineTo(bitmap.getWidth() - mRadiusX, 0);
        path.lineTo(bitmap.getWidth(), mRadiusY);
        path.addArc(new RectF(bitmap.getWidth() - mRadiusX * 2, 0, bitmap.getWidth(), mRadiusY * 2), 270, 90);
        canvas.drawPath(path, paint);

        // left bottom
        path.reset();
        path.moveTo(0, bitmap.getHeight());
        path.lineTo(mRadiusX, bitmap.getHeight());
        path.lineTo(0, bitmap.getHeight() - mRadiusY);
        path.addArc(new RectF(0, bitmap.getHeight() - mRadiusY * 2, mRadiusX * 2, bitmap.getHeight()), 90, 90);
        canvas.drawPath(path, paint);

        // right bottom
        path.reset();
        path.moveTo(bitmap.getWidth(), bitmap.getHeight());
        path.lineTo(bitmap.getWidth(), bitmap.getHeight() - mRadiusY);
        path.lineTo(bitmap.getWidth() - mRadiusX, bitmap.getHeight());
        path.addArc(new RectF(bitmap.getWidth() - mRadiusX * 2, bitmap.getHeight() - mRadiusY * 2, bitmap.getWidth(), bitmap.getHeight()), 0, 90);
        canvas.drawPath(path, paint);
        return bitmap;
    }
}
