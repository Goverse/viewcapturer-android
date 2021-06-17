package com.goverse.capturer.transformation;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.ByteArrayOutputStream;

public class SampleCompressTransformation implements ITransformation {

    private int mDestWidth;
    private int mDestHeight;
    public SampleCompressTransformation(int destWidth, int destHeight) {
        mDestWidth = destWidth;
        mDestHeight = destHeight;
    }

    private int cacuateSampleSize(int srcWidth, int srcHeight) {
        int defaultSample = 1;
        if (srcWidth > mDestWidth || srcHeight > mDestHeight) {

            int ratioWidth = srcWidth / mDestWidth;
            int ratioHeight = srcHeight / mDestHeight;
            defaultSample = ratioWidth > ratioHeight ? ratioWidth : ratioHeight;
        }

        return defaultSample;
    }

    @Override
    public Bitmap transform(Bitmap bitmap) {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, bos);
        byte[] bytes = bos.toByteArray();
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeByteArray(bytes, 0, bytes.length, options);
        int height = options.outHeight;
        int width = options.outWidth;
        options.inSampleSize = cacuateSampleSize(width, height);
        options.inJustDecodeBounds = false;
        Bitmap destBitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length, options);
        if (!bitmap.isRecycled()) bitmap.recycle();
        return destBitmap;
    }
}
