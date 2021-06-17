package com.goverse.capturer.transformation;

import android.graphics.Bitmap;
import android.graphics.Canvas;

public class MergeBitmapTransformation implements ITransformation {

    private Bitmap mDestBitmap;

    /**
     * If the destBitmap can cover on the base bitmap.
     * false: destBitmap will be drawn first.
     */
    private boolean mOverlay;

    public MergeBitmapTransformation(Bitmap destBitmap) {
        this(destBitmap, false);
    }

    public MergeBitmapTransformation(Bitmap destBitmap, boolean overlay) {
        mDestBitmap = destBitmap;
        mOverlay = overlay;
    }

    @Override
    public Bitmap transform(Bitmap bitmap) {

        if (mDestBitmap != null) {
            int width = Math.max(bitmap.getWidth(), mDestBitmap.getWidth());
            int height = Math.max(bitmap.getHeight(), mDestBitmap.getHeight());
            Bitmap screenBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
            Canvas canvas = new Canvas(screenBitmap);
            if (mOverlay) {
                canvas.drawBitmap(bitmap, 0, 0, null);
                canvas.drawBitmap(mDestBitmap, 0, 0, null);
            } else {
                canvas.drawBitmap(mDestBitmap, 0, 0, null);
                canvas.drawBitmap(bitmap, 0, 0, null);
            }
            return screenBitmap;
        }
        return bitmap;
    }
}
