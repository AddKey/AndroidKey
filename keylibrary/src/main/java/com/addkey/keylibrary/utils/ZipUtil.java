package com.addkey.keylibrary.utils;

import android.os.AsyncTask;


import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

/**
 * Created by lgy on 2018/11/21
 */
public class ZipUtil {
    private final static String TAG = "ZipUtil";
    private final static int BUFF_SIZE = 2048;

    public static void unZip(String filePath, String newPath, OnUnZipListener listener) {
        UnZipAsyncTask task = new UnZipAsyncTask(filePath, newPath, listener);
        task.execute();
    }

    private static class UnZipAsyncTask extends AsyncTask<String, Integer, Boolean> {
        private String mFailInfo = "解压失败";
        private String mNewPath;
        private String mFilePath;
        private OnUnZipListener mListener;

        UnZipAsyncTask(String filePath, String newPath, OnUnZipListener mListener) {
            this.mNewPath = newPath;
            this.mFilePath = filePath;
            this.mListener = mListener;
        }

        @Override
        protected Boolean doInBackground(String... params) {
            if (mFilePath.endsWith(".zip")) {
                if (mNewPath == null) {
                    mNewPath = mFilePath.substring(0, mFilePath.length() - 4);
                }
            }
            mNewPath = createSeparator(mNewPath);
            BufferedOutputStream bos = null;
            ZipInputStream zis = null;

            boolean result = false;

            try {
                String filename;
                zis = new ZipInputStream(new BufferedInputStream(new FileInputStream(mFilePath)));
                ZipEntry ze = null;
                byte[] buffer = new byte[BUFF_SIZE];
                int count;
                int unZipSize = 0;
                while ((ze = zis.getNextEntry()) != null) {
                    filename = ze.getName();
                    LogUtils.d(TAG, "ze.getName() = " + filename);
                    createSubFolders(filename, mNewPath);
                    if (ze.isDirectory()) {
                        File fmd = new File(mNewPath + filename);
                        fmd.mkdirs();
                        continue;
                    }
                    LogUtils.d(TAG, "unzip file = " + mNewPath + filename);
                    bos = new BufferedOutputStream(new FileOutputStream(mNewPath + filename));
                    while ((count = zis.read(buffer)) != -1) {
                        bos.write(buffer, 0, count);
                        unZipSize += count;
                        LogUtils.i(TAG, "unZipSize:--" + unZipSize);
                        publishProgress(unZipSize * 100 / count);
                    }
                    bos.flush();
                    bos.close();
                }
                result = true;
            } catch (IOException e) {
                e.printStackTrace();
                mFailInfo = e.getMessage();
            } finally {
                try {
                    if (zis != null) {
                        zis.closeEntry();
                        zis.close();
                    }
                    if (bos != null) {
                        bos.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return result;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            if (mListener != null) {
                mListener.onStart();
            }
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            super.onPostExecute(aBoolean);
            if (mListener != null) {
                if (aBoolean) {
                    mListener.onSuccess(mNewPath);
                } else {
                    mListener.onFail(mNewPath, mFailInfo);
                }
            }
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            if (values != null && values.length > 0) {
                if (mListener != null) {
                    mListener.onProgress(values[0]);
                }
            }
        }
    }

    public static void createSubFolders(String filename, String path) {
        String[] subFolders = filename.split("/");
        if (subFolders.length <= 1) {
            return;
        }

        String pathNow = path;
        for (int i = 0; i < subFolders.length - 1; ++i) {
            pathNow = pathNow + subFolders[i] + "/";
            File fmd = new File(pathNow);
            if (fmd.exists()) {
                continue;
            }
            fmd.mkdirs();
        }
    }

    public static String createSeparator(String path) {
        File dir = new File(path);
        if (!dir.exists()) {
            dir.mkdirs();
        }
        if (path.endsWith("/")) {
            return path;
        }
        return path + '/';
    }

    public interface OnUnZipListener {
        void onStart();

        void onSuccess(String fileName);

        void onFail(String fileName, String failInfo);

        void onProgress(int progress);
    }

}
