package com.kingyzll.information;

import android.util.Log;

import com.kingyzll.bean.TeacherPage;

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

public class GetTeacherPage {
    public static String GET_TEACHER_PAGE = "http://172.20.10.3:8080/TransactionManagement/teacherPageServlet";
    private static final String TAG = "GetTeacherPage";

    public static List<TeacherPage> getJSONTeacherPage(int tid) {

        HttpURLConnection connection = null;
        InputStream in = null;
        OutputStream out = null;

        try {
            URL url = new URL(GET_TEACHER_PAGE);
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setReadTimeout(5000);
            connection.setConnectTimeout(5000);
            connection.setDoInput(true);
            connection.setDoOutput(true);
            connection.setUseCaches(false);

            String data = "tid=" + tid;
            out = connection.getOutputStream();
            out.write(data.getBytes());
            out.flush();
            out.close();
            connection.connect();
            if (connection.getResponseCode() == 200) {
                Log.e(TAG, "连接成功");
                in = connection.getInputStream();
                return parsJSON(in);
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


        return null;
    }

    private static List<TeacherPage> parsJSON(InputStream in) {
        try {
            List<TeacherPage> list = new ArrayList<>();
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            byte[] buffer = new byte[1024];
            int len;
            while ((len = in.read(buffer)) != -1) {
                outputStream.write(buffer, 0, len);
            }
            outputStream.close();
            Log.e(TAG,"写完数据");
            String json = new String(outputStream.toByteArray());
            Log.e(TAG,json);
            JSONArray jsonArray = new JSONArray(json);
            Log.e(TAG,"准备遍历");
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                String course = jsonObject.getString("course");
                String classtime = jsonObject.getString("classtime");
                list.add(new TeacherPage(course, classtime));
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
