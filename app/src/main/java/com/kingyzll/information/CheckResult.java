package com.kingyzll.information;

import android.util.Log;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

public class CheckResult {

    public static String STUDENT_CHECKRESULT =
            "http://172.20.10.3:8080/TransactionManagement/checkResultServlet";
    private static final String TAG = "CheckResult";

    public static String[] performanceInformation(String accountNum) {
        Log.e(TAG, "准备连接");
        String result;
        OutputStream out = null;
        InputStream in = null;
        ByteArrayOutputStream byteArrayOutputStream = null;
        HttpURLConnection connection = null;

        try {
            URL url = new URL(STUDENT_CHECKRESULT);
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setReadTimeout(5000);
            connection.setConnectTimeout(5000);
            connection.setDoOutput(true);
            connection.setDoInput(true);
            connection.setUseCaches(false);
            Log.e(TAG, "准备就绪");
            String data = "accountNum=" + URLEncoder.encode(accountNum, "UTF-8");
            out = connection.getOutputStream();
            out.write(data.getBytes());
            out.flush();
            out.close();
            connection.connect();

            if (connection.getResponseCode() == 200) {
                Log.e(TAG, "连接成功");
                in = connection.getInputStream();
                byteArrayOutputStream = new ByteArrayOutputStream();
                int len;
                byte buffer[] = new byte[1024];
                while ((len = in.read(buffer)) != -1) {
                    byteArrayOutputStream.write(buffer, 0, len);
                }
                result = new String(byteArrayOutputStream.toByteArray());
                in.close();
                byteArrayOutputStream.close();
                Log.e(TAG, "返回数据");
                String[] im = result.split("[,,]+");
                return im;

            }

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (out != null) {
                    out.close();
                }
                if (in != null) {
                    in.close();
                }
                if (byteArrayOutputStream != null) {
                    byteArrayOutputStream.close();
                }
                if (connection != null) {
                    connection.disconnect();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
        Log.e(TAG, "返回空");

        return null;
    }
}
