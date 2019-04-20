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

public class StudentInformation {

    public static String STUDENT_INFORMATION_URL =
            "http://172.20.10.3:8080/TransactionManagement/studentInformationServlet";


    private static final String TAG = "StudentInformation";

    public static String[] basicInformation(String accountNum) {
        Log.e(TAG, "准备连接");
        String studentInformation;
        OutputStream out = null;
        HttpURLConnection connection = null;
        InputStream in = null;
        ByteArrayOutputStream information = null;
        try {
            URL url = new URL(STUDENT_INFORMATION_URL);
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setReadTimeout(5000);
            connection.setConnectTimeout(5000);
            connection.setDoInput(true);
            connection.setDoOutput(true);
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
                information = new ByteArrayOutputStream();
                int len;
                byte buffer[] = new byte[1024];
                while ((len = in.read(buffer)) != -1) {
                    information.write(buffer, 0, len);
                }
                in.close();
                information.close();
                studentInformation = new String(information.toByteArray());
                Log.e(TAG, "返回数据");
                //将一个一个数据分割
                String[] im = studentInformation.split("[,,]+");

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
                if (information != null) {
                    information.close();
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
