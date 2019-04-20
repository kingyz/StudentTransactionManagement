package com.kingyzll.information;

import android.util.Log;

import com.kingyzll.bean.GradeInformation;


import org.json.JSONArray;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class GetGradeInformation {
    public static String GRADE_INFORMATION_URL = "http://172.20.10.3:8080/TransactionManagement/gradeInformationServlet";
    private static final String TAG = "GradeInformation";

    public static List<GradeInformation> getJSONGradeInformation(String num) {
        HttpURLConnection connection = null;
        OutputStream out = null;
        InputStream in = null;

        try {
            URL url = new URL(GRADE_INFORMATION_URL);
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setConnectTimeout(5000);
            connection.setReadTimeout(5000);
            connection.setDoInput(true);
            connection.setDoOutput(true);
            connection.setUseCaches(false);

            String data = "num=" + num;
            Log.e(TAG,data);
            out = connection.getOutputStream();
            out.write(data.getBytes());
            out.flush();
            out.close();
            connection.connect();
            Log.e(TAG, "开始连接");
            if (connection.getResponseCode() == 200) {
                in = connection.getInputStream();
                Log.e(TAG, "返回json");
                return parsJSON(in);
            }

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


        return null;
    }

    private static List<GradeInformation> parsJSON(InputStream in) {

        try {
            List<GradeInformation> list = new ArrayList<>();
            Log.e(TAG, "解析json");
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            byte[] buffer = new byte[1024];
            int len;
            while ((len = in.read(buffer)) != -1) {
                outputStream.write(buffer, 0, len);
            }
            outputStream.close();
            String json = new String(outputStream.toByteArray());
            Log.e(TAG,json);
            Log.e(TAG, "转字符串");
            JSONArray jsonArray = new JSONArray(json);
            for (int i = 0; i < jsonArray.length(); i++) {

                JSONObject jsonObject = jsonArray.getJSONObject(i);
                String course = jsonObject.getString("course");
                String property = jsonObject.getString("property");
                String credit = jsonObject.getString("credit");
                String semester = jsonObject.getString("semester");
                String code = jsonObject.getString("code");
                String score = jsonObject.getString("score");
                String gpa = jsonObject.getString("gpa");
                String supplymentary = jsonObject.getString("supplymentary");
                list.add(new GradeInformation(course, property, credit, semester, score, gpa, supplymentary,code));
            }
            Log.e(TAG, "返回list");
            return list;

        } catch (Exception e) {
            e.printStackTrace();
        }
        Log.e(TAG, "返回null");
        return null;
    }
}
