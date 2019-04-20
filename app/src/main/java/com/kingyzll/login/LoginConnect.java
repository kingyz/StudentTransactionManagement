package com.kingyzll.login;

import android.util.Log;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

public class LoginConnect {


    public static String LOGIN_URL = "http://172.20.10.3:8080/TransactionManagement/loginServlet";
    private static final String TAG = "LoginConnect";


    public static String LoginByPost(String account, String password) {
        Log.e(TAG, "启动登录连接");
        String msg;
        OutputStream out = null;
        ByteArrayOutputStream message = null;
        InputStream is = null;
        HttpURLConnection conn = null;
        try {
            URL url = new URL(LOGIN_URL);
            conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setReadTimeout(5000);
            conn.setConnectTimeout(5000);
            conn.setDoInput(true);
            conn.setDoOutput(true);
            conn.setUseCaches(false);

            Log.e(TAG, "gogogogo");

            String data = "password=" + URLEncoder.encode(password, "UTF-8") +
                    "&account=" + URLEncoder.encode(account, "UTF-8");

            Log.e(TAG, "上传数据");
            out = conn.getOutputStream();
            out.write(data.getBytes());
            out.flush();
            out.close();
            Log.e(TAG, "准备");
            conn.connect();
            Log.e(TAG, "准备");

            if (conn.getResponseCode() == 200) {
                Log.e(TAG, "连接成功");
                is = conn.getInputStream();
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
                if (conn != null) {
                    conn.disconnect();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

        }


        Log.e(TAG, "退出");
        return "登录失败";
    }


}
