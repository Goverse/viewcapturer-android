package com.goverse.capturer.transformation;

import android.graphics.Bitmap;
import android.os.Handler;
import android.os.Looper;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class SaveFileTransformation implements ITransformation {

    public interface OnFileSaveListener {
        void onSaveSuccess(File file);
        void onSaveFailed();
    }

    private String mSavePath;
    private String mFileName;
    private int mWriteQuality = 100;
    private OnFileSaveListener mOnFileSaveListener;
    private Handler mHandler = new Handler(Looper.getMainLooper());
    public SaveFileTransformation(String savePath, String fileName, int writeQuality) {
        mSavePath = savePath;
        mFileName = fileName;
        mWriteQuality = writeQuality;
    }

    public SaveFileTransformation(String savePath, String fileName, OnFileSaveListener onFileSaveListener) {
        mSavePath = savePath;
        mFileName = fileName;
        mOnFileSaveListener = onFileSaveListener;
    }

    public void setOnFileSaveListener(OnFileSaveListener mOnFileSaveListener) {
        this.mOnFileSaveListener = mOnFileSaveListener;
    }

    @Override
    public Bitmap transform(final Bitmap bitmap) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                saveBitmapToFile(bitmap, mSavePath, mFileName);
            }
        }).start();
        return bitmap;
    }

    private void saveBitmapToFile(Bitmap bitmap, String savePath, String fileName) {

        FileOutputStream fos = null;
        BufferedOutputStream bos = null;
        File screenShotDir = new File(savePath);
        File file = new File(savePath + fileName);
        try {
            if (!screenShotDir.exists()) screenShotDir.mkdirs();
            if (!file.exists()) file.createNewFile();
            fos = new FileOutputStream(file);
            bos = new BufferedOutputStream(fos);
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, bos);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            notifyOnSaveFailed();
            return;
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (bos != null) {
                    bos.flush();
                }
                if (bos != null) {
                    bos.close();
                }
                if (fos != null) {
                    fos.flush();
                }
                if (fos != null) {
                    fos.close();
                }
                notifyOnSaveSuccess(file);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void notifyOnSaveSuccess(final File file) {
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                if (mOnFileSaveListener != null) mOnFileSaveListener.onSaveSuccess(file);
            }
        });
    }

    private void notifyOnSaveFailed() {
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                if (mOnFileSaveListener != null) mOnFileSaveListener.onSaveFailed();

            }
        });
    }
}
