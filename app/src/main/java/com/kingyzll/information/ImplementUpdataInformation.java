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

public class ImplementUpdataInformation {

    public static String UPDATA_INFORMATION_URL = "http://172.20.10.3:8080/TransactionManagement/updataInformationServlet";
    private static final String TAG = "ImplementUpdataInformat";

    public static String UpdataInformation(String name, String sex, String symbol, String in, String intime, String birthplace,
                                           String homeaddr, String postcode, String health, String study,
                                           String contactaddr, String schoolpost, String num) {
        Log.e(TAG, "启动登录连接");
        String msg;
        OutputStream out = null;
        ByteArrayOutputStream message = null;
        InputStream is = null;
        HttpURLConnection connection = null;
        try {
            URL url = new URL(UPDATA_INFORMATION_URL);
            connection = (HttpURLConnection) url.openConnection();
            connection.setReadTimeout(5000);
            connection.setConnectTimeout(5000);
            connection.setRequestMethod("POST");
            connection.setDoInput(true);
            connection.setDoOutput(true);
            connection.setUseCaches(false);
//            String data = "name=" + name +
//                    "&sex=" + sex +
//                    "&symbol=" + symbol +
//                    "&in=" + in +
//                    "&intime=" + intime +
//                    "&birthplace=" + birthplace +
//                    "&homeaddr=" + homeaddr +
//                    "&postcode=" + postcode +
//                    "&health=" + health +
//                    "&study=" + study +
//                    "&contactaddr=" + contactaddr +
//                    "&schoolpost=" + schoolpost +
//                    "&num=" + num;

            String data = "name=" + URLEncoder.encode(name, "UTF-8") +
                    "&sex=" + URLEncoder.encode(sex, "UTF-8") +
                    "&symbol=" + URLEncoder.encode(symbol, "UTF-8") +
                    "&in=" + URLEncoder.encode(in, "UTF-8") +
                    "&intime=" + URLEncoder.encode(intime, "UTF-8") +
                    "&birthplace=" + URLEncoder.encode(birthplace, "UTF-8") +
                    "&homeaddr=" + URLEncoder.encode(homeaddr, "UTF-8") +
                    "&postcode=" + URLEncoder.encode(postcode, "UTF-8") +
                    "&health=" + URLEncoder.encode(health, "UTF-8") +
                    "&study=" + URLEncoder.encode(study, "UTF-8") +
                    "&contactaddr=" + URLEncoder.encode(contactaddr, "UTF-8") +
                    "&schoolpost=" + URLEncoder.encode(schoolpost, "UTF-8")+
                    "&num=" + URLEncoder.encode(num, "UTF-8");

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
