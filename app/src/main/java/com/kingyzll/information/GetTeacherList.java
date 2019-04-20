package com.kingyzll.information;

import android.util.Log;

import com.kingyzll.bean.GradeInformation;
import com.kingyzll.bean.Teacher;

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

public class GetTeacherList {
    public static String GET_TEACHER_LIST = "http://172.20.10.3:8080/TransactionManagement/teacherListServlet";
    private static final String TAG = "GetTeacherList";


    public static List<Teacher> getJSONTeacherList(int did) {

        HttpURLConnection connection = null;
        InputStream in = null;
        OutputStream out = null;

        try {
            URL url = new URL(GET_TEACHER_LIST);
            connection = (HttpURLConnection) url.openConnection();
            connection.setReadTimeout(5000);
            connection.setConnectTimeout(5000);
            connection.setRequestMethod("POST");
            connection.setDoOutput(true);
            connection.setDoInput(true);
            connection.setUseCaches(false);

            String data = "did=" + did;
            out = connection.getOutputStream();
            out.write(data.getBytes());
            out.flush();
            out.close();
            connection.connect();
            Log.e(TAG, "准备连接");
            if (connection.getResponseCode() == 200) {
                Log.e(TAG, "开始连接");
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

    private static List<Teacher> parsJSON(InputStream in) {
        try {

            List<Teacher> list = new ArrayList<>();
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            byte[] buffer = new byte[1024];
            int len;
            while ((len = in.read(buffer)) != -1) {
                outputStream.write(buffer, 0, len);
            }
            outputStream.close();
            String json = new String(outputStream.toByteArray());
            JSONArray jsonArray = new JSONArray(json);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                int id = jsonObject.getInt("tid");
                String name = jsonObject.getString("tname");
                String type = jsonObject.getString("ttype");
                String email = jsonObject.getString("temail");
                String portrait = jsonObject.getString("tportrait");
                list.add(new Teacher(id, name, type, email, portrait));
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
