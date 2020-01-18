package com.addkey.keylibrary.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.media.MediaMetadataRetriever;
import android.media.ThumbnailUtils;
import android.provider.MediaStore;
import android.util.Log;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Hashtable;
import java.util.UUID;

/**
 * @author Created by ligaoyuan
 * @date 2019-08-27
 */
public class BitmapUtils {
    private static final String TAG = "BitmapUtils";
    /**
     * 对bitmap进行缩放
     *
     * @param bitmap
     * @param w
     * @param h
     * @return
     */
    public static Bitmap reSizeImge(Bitmap bitmap, int w, int h) {
        Bitmap orgBtmp = bitmap;
        int width = orgBtmp.getWidth();
        int height = orgBtmp.getHeight();

        float scaleW = ((float) w) / width;
        float scaleH = ((float) h / height);

        Matrix matrix = new Matrix();
        matrix.setScale(scaleW, scaleH);

//        matrix.postRotate(180);//旋转180度
        Bitmap resultBtmp = Bitmap.createBitmap(orgBtmp, 0, 0, width, height, matrix, true);
        return resultBtmp;
    }

    public static Bitmap scaleBitmap(Bitmap bitmap, int w, int h) {
        Bitmap bt = Bitmap.createScaledBitmap(bitmap, w, h, true);
        return bt;
    }


    public static String saveBitmap(Bitmap bitmap, Context context) {
        String bitmapName = UUID.randomUUID() + ".png";
        String localFile = context.getExternalCacheDir() + "/封面";
        File dir = new File(localFile);
        if (!dir.exists()) {
            dir.mkdir();
        }

        File f = new File(dir, bitmapName);
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(f);
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, fileOutputStream);
            fileOutputStream.flush();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return localFile + "/" + bitmapName;
    }

    public static Bitmap createVideoThumbnail(String filePath, int kind) {
        Bitmap bitmap = null;
        MediaMetadataRetriever retriever = new MediaMetadataRetriever();
        try {
            if (filePath.startsWith("http://")
                    || filePath.startsWith("https://")
                    || filePath.startsWith("widevine://")) {
                retriever.setDataSource(filePath, new Hashtable<String, String>());
            } else {
                Log.i(TAG, "createVideoThumbnail: ");
                retriever.setDataSource(filePath);
            }
            bitmap = retriever.getFrameAtTime(0, MediaMetadataRetriever.OPTION_CLOSEST_SYNC); //retriever.getFrameAtTime(-1);
        } catch (IllegalArgumentException ex) {
            // Assume this is a corrupt video file
            ex.printStackTrace();
        } catch (RuntimeException ex) {
            // Assume this is a corrupt video file.
            ex.printStackTrace();
        } finally {
            try {
                retriever.release();
            } catch (RuntimeException ex) {
                // Ignore failures while cleaning up.
                ex.printStackTrace();
            }
        }

        if (bitmap == null) {
            return null;
        }

        if (kind == MediaStore.Images.Thumbnails.MINI_KIND) {//压缩图片 开始处
            // Scale down the bitmap if it's too large.
            int width = bitmap.getWidth();
            int height = bitmap.getHeight();
            int max = Math.max(width, height);
            if (max > 512) {
                float scale = 512f / max;
                int w = Math.round(scale * width);
                int h = Math.round(scale * height);
                bitmap = Bitmap.createScaledBitmap(bitmap, w, h, true);
            }//压缩图片 结束处
        } else if (kind == MediaStore.Images.Thumbnails.MICRO_KIND) {
            bitmap = ThumbnailUtils.extractThumbnail(bitmap,
                    96,
                    96,
                    ThumbnailUtils.OPTIONS_RECYCLE_INPUT);
        }
        return bitmap;
    }
}
