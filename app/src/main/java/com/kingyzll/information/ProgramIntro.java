package com.kingyzll.information;

import android.util.Log;

import com.kingyzll.bean.Program;

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

public class ProgramIntro {
    public static String PROGRAM_INTRODUCE =
            "http://172.20.10.3:8080/TransactionManagement/programListServlet";
    private static final String TAG = "ProgramIntro";

    public static List<Program> getJSONProgram(int did) {

        HttpURLConnection connection = null;
        OutputStream out = null;
        InputStream in = null;

        try {
            Log.e(TAG, "准备连接");
            URL url = new URL(PROGRAM_INTRODUCE);
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setReadTimeout(5000);
            connection.setConnectTimeout(5000);
            connection.setDoInput(true);
            connection.setDoOutput(true);
            connection.setUseCaches(false);

            String data = "did=" + did;
            out = connection.getOutputStream();
            out.write(data.getBytes());
            out.flush();
            out.close();
            connection.connect();
            Log.e(TAG, "开始连接");
            if (connection.getResponseCode() == 200) {
                Log.e(TAG, "连接成功");
                in = connection.getInputStream();
                Log.e(TAG, "返回json");
                return parsJSON(in);
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Log.e(TAG, "返回空");
        return null;
    }

    private static List<Program> parsJSON(InputStream in) {

        try {
            List<Program> list = new ArrayList<>();
            Log.e(TAG, "解析json");
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
                int p_id = jsonObject.getInt("pid");
                String p_title = jsonObject.getString("ptitle");
                String p_feature = jsonObject.getString("pfeature");
                String p_intro = jsonObject.getString("pintro");
                String p_core = jsonObject.getString("pcore");
                String p_imgurl = jsonObject.getString("pimgurl");
                list.add(new Program(p_id, p_title, p_feature, p_intro, p_core, p_imgurl));
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