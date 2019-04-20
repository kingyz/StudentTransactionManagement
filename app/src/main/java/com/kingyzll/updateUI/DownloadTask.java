package com.kingyzll.updateUI;

import android.os.AsyncTask;
import android.os.Environment;
import android.util.Log;

import com.kingyzll.Interface.DownloadListener;

import java.io.File;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.net.HttpURLConnection;
import java.net.URL;

public class DownloadTask extends AsyncTask<String, Integer, Integer> {

    private static final String TAG = "DownloadTask";

    //文件存储路径
    public static String FILE_DIRECTORY = Environment.getExternalStorageDirectory() + "/file";

    public static final int TYPE_SUCCESS = 0;
    public static final int TYPE_FAILED = 1;
    public static final int TYPE_PAUSED = 2;
    public static final int TYPE_CANCELED = 3;

    private DownloadListener listener;

    private boolean isCanceled = false;
    private boolean isPaused = false;

    private int lastProgress;

    private boolean sdCardExist = false;


    public DownloadTask(DownloadListener listener) {
        this.listener = listener;
    }


    @Override
    protected Integer doInBackground(String... params) {
        HttpURLConnection connection = null;
        InputStream is = null;
        RandomAccessFile saveFile = null;
        File file = null;
        File directory = null;
        try {
            long downloadedLength = 0;
            String downloadURL = params[0];

            sdCardExist =
                    Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
            Log.e(TAG, "判断sd卡是否存在:" + sdCardExist);
            //if (sdCardExist)
            String fileName = downloadURL.substring(downloadURL.lastIndexOf("/"));
            directory = new File(FILE_DIRECTORY);
            if (!directory.exists()) {
                directory.mkdir();
            }
            Log.e(TAG, "文件位置:" + directory);
            file = new File(directory + fileName);
            if (file.exists()) {
                downloadedLength = file.length();
            }
            long contentLength = getContentLength(downloadURL);
            Log.e(TAG, "文件长度" + contentLength);
            if (contentLength == 0) {
                return TYPE_FAILED;
            } else if (contentLength == downloadedLength) {
                return TYPE_SUCCESS;
            }
            URL url = new URL(downloadURL);
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setReadTimeout(5000);
            connection.setConnectTimeout(5000);
            connection.setRequestProperty("Range", "bytes=" + downloadedLength + "-");
            is = connection.getInputStream();
            if (is != null) {
                Log.e(TAG, "开始下载");
                saveFile = new RandomAccessFile(file, "rw");
                saveFile.seek(downloadedLength);
                byte[] buffer = new byte[1024];
                int total = 0;
                int len;
                while ((len = is.read(buffer)) != -1) {
                    if (isCanceled) {
                        return TYPE_CANCELED;
                    } else if (isPaused) {
                        return TYPE_PAUSED;
                    } else {
                        //用于计算progress进度
                        total += len;
                        saveFile.write(buffer, 0, len);
                        int progress = (int) ((total + downloadedLength) * 100 / contentLength);
                        //传到onProgressUpdate做ui操作
                        publishProgress(progress);
                    }
                }
                return TYPE_SUCCESS;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (is != null) {
                    is.close();
                }
                if (connection != null) {
                    connection.disconnect();
                }
                if (saveFile != null) {
                    saveFile.close();
                }
                if (isCanceled && file != null) {
                    file.delete();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return TYPE_FAILED;
    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        int progress = values[0];
        if (progress > lastProgress) {
            listener.onProgress(progress);
            lastProgress = progress;
        }
    }

    @Override
    protected void onPostExecute(Integer status) {
        switch (status) {
            case TYPE_SUCCESS:
                listener.onSuccess();
                break;
            case TYPE_FAILED:
                listener.onFailed();
                break;
            case TYPE_PAUSED:
                listener.onPaused();
                break;
            case TYPE_CANCELED:
                listener.onCanceled();
                break;
            default:
                break;
        }
    }

    public void pauseDownload() {
        isPaused = true;
    }

    public void cancelDownload() {
        isCanceled = true;
    }

    private long getContentLength(String downloadURL) {
        HttpURLConnection connection = null;
        try {
            Log.e(TAG, downloadURL);
            URL url = new URL(downloadURL);
            connection = (HttpURLConnection) url.openConnection();
            if (connection.getResponseCode() == 200) {
                long contentLength = connection.getContentLength();
                Log.e(TAG, "文件长度为:" + contentLength);
                return contentLength;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }
}
