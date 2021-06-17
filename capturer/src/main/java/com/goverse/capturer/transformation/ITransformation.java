package com.goverse.capturer.transformation;

import android.graphics.Bitmap;

public interface ITransformation {
    /**
     * Called to transform the webview picture during capturing.
     * @param bitmap bitmap
     * @return bitmap
     */
    Bitmap transform(Bitmap bitmap);
}
