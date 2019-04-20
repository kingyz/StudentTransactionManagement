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

public class UpdatePassword {
    public static String UPDATA_PASSWORD_URL = "http://172.20.10.3:8080/TransactionManagement/updatePasswordServlet";
    private static final String TAG = "UpdatePassword";

    public static String UpdatePassword(String account, String oldPsd, String newPsd) {

        Log.e(TAG, "启动登录连接");
        String msg;
        OutputStream out = null;
        ByteArrayOutputStream message = null;
        InputStream is = null;
        HttpURLConnection connection = null;
        try {
            URL url = new URL(UPDATA_PASSWORD_URL);
            connection = (HttpURLConnection) url.openConnection();
            connection.setReadTimeout(5000);
            connection.setConnectTimeout(5000);
            connection.setRequestMethod("POST");
            connection.setDoInput(true);
            connection.setDoOutput(true);
            connection.setUseCaches(false);

            String data = "account=" + URLEncoder.encode(account, "UTF-8") +
                    "&oldPsd=" + URLEncoder.encode(oldPsd, "UTF-8") +
                    "&newPsd=" + URLEncoder.encode(newPsd, "UTF-8");
            Log.e(TAG, "上传数据");
            out = connection.getOutputStream();
            out.write(data.getBytes());
            out.flush();
            out.close();
            connection.connect();
            Log.e(TAG, "准备");

            if (connection.getResponseCode() == 200) {
                Log.e(TAG, "连接成功");
                is = connection.getInputStream();
                message = new ByteArrayOutputStream();
                int len;
                byte buffer[] = new byte[1024];
                while ((len = is.read(buffer)) != -1) {
                    message.write(buffer, 0, len);
                }
                is.close();
                message.close();
                msg = new String(message.toByteArray());
                return msg;
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
                if (is != null) {
                    is.close();
                }
                if (message != null) {
                    message.close();
                }
                if (connection != null) {
                    connection.disconnect();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        Log.e(TAG, "退出");
        return "fail";
    }
}
