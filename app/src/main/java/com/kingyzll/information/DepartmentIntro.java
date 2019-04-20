package com.kingyzll.information;

import android.util.Log;

import com.kingyzll.bean.Department;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class DepartmentIntro {

    public static String DEPARTMENT_CONTENT_INTRO =
            "http://172.20.10.3:8080/TransactionManagement/departmentListServlet";

    private static final String TAG = "DepartmentIntro";

    public static List<Department> getJSONLastDepartment() {
        HttpURLConnection connection = null;
        InputStream json;
        try {
            Log.e(TAG, "准备连接");
            URL url = new URL(DEPARTMENT_CONTENT_INTRO);
            connection = (HttpURLConnection) url.openConnection();
            connection.setConnectTimeout(5000);
            connection.setReadTimeout(5000);
            connection.setRequestMethod("GET");
            connection.connect();

            if (connection.getResponseCode() == 200) {
                json = connection.getInputStream();
                Log.e(TAG, "返回json");
                return parseJSON(json);
            }

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }
        Log.e(TAG, "返回空");
        return null;
    }

    private static List<Department> parseJSON(InputStream jsonStream) {
        try {
            Log.e(TAG, "解析json");
            List<Department> list = new ArrayList<>();
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            byte[] buffer = new byte[1024];
            int len;
            Log.e(TAG, "开始read");
            while ((len = jsonStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, len);
            }
            Log.e(TAG, "read结束");
            jsonStream.close();
            Log.e(TAG, "输入流关闭");
            byte[] data = outputStream.toByteArray();
            Log.e(TAG, "byte data");
            String json = new String(data);
            Log.e(TAG, "new String");
            Log.e(TAG,json);
            JSONArray jsonArray = new JSONArray(json);
            Log.e(TAG, "创建JSONARRAY对象");
            Log.e(TAG, "准备遍历json");
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                int did = jsonObject.getInt("did");
                String dtitle = jsonObject.getString("dtitle");
                String dcontent = jsonObject.getString("dcontent");
                String dimageid = jsonObject.getString("dimageid");
                String daddress = jsonObject.getString("daddress");
                String dcontact = jsonObject.getString("dcontact");
                String demail = jsonObject.getString("demail");
                list.add(new Department(did, dtitle, dcontent, dimageid, daddress, dcontact, demail));
            }
            Log.e(TAG, "返回list");
            outputStream.close();
            return list;
        } catch (Exception e) {
            e.printStackTrace();
        }
        Log.e(TAG, "返回null");
        return null;

    }


}
