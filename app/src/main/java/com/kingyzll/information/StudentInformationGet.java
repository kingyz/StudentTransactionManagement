package com.kingyzll.information;

import android.util.Log;

import com.kingyzll.bean.StudentInformations;

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

public class StudentInformationGet {
    public static String STUDENT_INFORMATION_GET = "http://172.20.10.3:8080/TransactionManagement/studentInformationsServlet";
    private static final String TAG = "StudentInformationGet";

    public static List<StudentInformations> getJSONStudentInformation(String inum) {
        HttpURLConnection connection = null;
        OutputStream out = null;
        InputStream in = null;

        try {
            Log.e(TAG, "准备连接");
            URL url = new URL(STUDENT_INFORMATION_GET);
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setReadTimeout(5000);
            connection.setConnectTimeout(5000);
            connection.setDoInput(true);
            connection.setDoOutput(true);
            connection.setUseCaches(false);

            String data = "inum=" + inum;
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
        Log.e(TAG, "返回null");

        return null;
    }

    private static List<StudentInformations> parsJSON(InputStream in) {
        try {
            List<StudentInformations> list = new ArrayList<>();
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
                int i_id = jsonObject.getInt("iid");
                String i_num = jsonObject.getString("inum");
                String i_name = jsonObject.getString("iname");
                String i_sex = jsonObject.getString("isex");
                String i_symbol = jsonObject.getString("isymbol");
                String i_in = jsonObject.getString("iin");
                String i_intime = jsonObject.getString("iintime");
                String i_birthplace = jsonObject.getString("ibirthplace");
                String i_homeaddr = jsonObject.getString("ihomeaddr");
                String i_postcode = jsonObject.getString("ipostcode");
                String i_health = jsonObject.getString("ihealth");
                String i_study = jsonObject.getString("istudy");
                String i_contactaddr = jsonObject.getString("icontactaddr");
                String i_schoolpost = jsonObject.getString("ischoolpost");
                list.add(new StudentInformations(i_id, i_num, i_name, i_sex, i_symbol, i_in, i_intime, i_birthplace,
                        i_homeaddr, i_postcode, i_health, i_study, i_contactaddr, i_schoolpost));

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
