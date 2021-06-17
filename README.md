# viewcapturer-android
用于完成Android View的截图，并可以对图片进行处理


使用方法如下：
```
new ViewCapturer.Builder(View)
                .setCropper({@link ICropper}) // set Cropper which used to clip the webview picture as shape you want.
                .addTransfermation({@link ITransformation}) // add transformation to modify the webview picture's style.
                .setOnCaptureListener({@link OnCaptureListener})
                .build()
                .capture();
 ```

### setCropper
> 完成对View的选中区域进行截取

```
public interface ICropper {

    /**
     * Interface definition for a callback to be invoked after cropping view.
     */
    interface OnCropListener {
        void onCropped(Bitmap bitmap);
    }
    /**
     * Called to clip the view during capturing.
     * @param view view
     * @param onCropListener onCropListener
     */
    void crop(View view, OnCropListener onCropListener);
}
```
#### 目前提供以下Cropper:
1. DefaultCropper:默认裁剪器，只裁剪view可见区域。
2. LongCropper:长截图裁剪器，截取view全部内容。
3. RectCropper:矩形裁剪器，截取view自定义矩形位置。
4. RoundCropper:圆形区域裁剪器，完成圆形区域的裁剪。
5. RoundRectCropper:圆角矩形裁剪器。

### addTransfermation
```
public interface ITransformation {
    /**
     * Called to transform the webview picture during capturing.
     * @param bitmap bitmap
     * @return bitmap
     */
    Bitmap transform(Bitmap bitmap);
}
```
执行对选中区域生成的Bitmap进行处理，可以添加多个依次处理
#### 目前提供以下Cropper:
1. RoundRectTransformation:圆角矩形转换器
2. QualityCompressTransformation:质量压缩转换器
3. SampleCompressTransformation:采样压缩转换器，转换成目标尺寸
4. SaveFileTransformation:文件转换器
 
 
