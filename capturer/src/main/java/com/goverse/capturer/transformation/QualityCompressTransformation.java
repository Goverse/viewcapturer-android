package com.goverse.capturer.transformation;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;

import java.io.ByteArrayOutputStream;

public class QualityCompressTransformation implements ITransformation {

    private int mQuality;

    private Bitmap.CompressFormat  mCompressFormat = Bitmap.CompressFormat.PNG;

    public QualityCompressTransformation(int quality) {
        mQuality = quality;
    }

    public QualityCompressTransformation(Bitmap.CompressFormat compressFormat, int quality) {
        mCompressFormat = compressFormat;
        mQuality = quality;
    }

    @Override
    public Bitmap transform(Bitmap bitmap) {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        Bitmap srcBitmap = bitmap;
        if (mCompressFormat == Bitmap.CompressFormat.JPEG) {
            srcBitmap = convertBitmapToJpg(bitmap);
        }
        srcBitmap.compress(mCompressFormat, mQuality, bos);
        byte[] bytes = bos.toByteArray();
        Bitmap destBitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
        if (!srcBitmap.isRecycled()) srcBitmap.recycle();
        return destBitmap;
    }

    /**
     * 把bitmap,png格式的图片 转换成jpg图片
     * 因jpg不支持透明，透明部分会显示黑色。
     * 处理逻辑如下：
     * 复制Bitmap，把透明底色变成白色。
     * 创建一张白色图片，把原来的绘制上去，生成新的bitmap。
     * @param bitmap bitmap
     */
    private Bitmap convertBitmapToJpg(Bitmap bitmap) {

        Bitmap outBitmap = bitmap.copy(Bitmap.Config.RGB_565,true);
        Canvas canvas = new Canvas(outBitmap);
        canvas.drawColor(Color.WHITE);
        canvas.drawBitmap(bitmap, 0, 0, null);
        return outBitmap;
    }
}
