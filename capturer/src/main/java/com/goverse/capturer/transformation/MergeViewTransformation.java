package com.goverse.capturer.transformation;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.view.ViewGroup;

public class MergeViewTransformation implements ITransformation {
    private ViewGroup viewGroup;
    private Bitmap mapBitmap;

    public MergeViewTransformation(ViewGroup viewGroup, Bitmap bitmap) {
        this.viewGroup = viewGroup;
        this.mapBitmap = bitmap;
    }

    @Override
    public Bitmap transform(Bitmap bitmap) {
        int width = viewGroup.getWidth();
        int height = viewGroup.getHeight();
        final Bitmap screenBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(screenBitmap);
        if (mapBitmap != null) {
            canvas.drawBitmap(mapBitmap, viewGroup.getLeft(), viewGroup.getTop(), null);
        }

        int h = 0;
        // 获取listView实际高度
        for (int i = 0; i < viewGroup.getChildCount(); i++) {
            h += viewGroup.getChildAt(i).getHeight();
        }
        // 创建对应大小的bitmap
        Bitmap mBitmap = Bitmap.createBitmap(viewGroup.getWidth(), h,
                Bitmap.Config.ARGB_8888);
        Canvas canvas1 = new Canvas(mBitmap);
        viewGroup.draw(canvas1);

        canvas.drawBitmap(mBitmap, viewGroup.getLeft(), viewGroup.getTop(), null);
        return screenBitmap;
    }
}
