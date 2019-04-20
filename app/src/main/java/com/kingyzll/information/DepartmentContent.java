//package com.kingyzll.information;
//
//import com.kingyzll.bean.Department;
//
//import org.json.JSONException;
//import org.json.JSONObject;
//
//import java.io.ByteArrayOutputStream;
//import java.io.IOException;
//import java.io.InputStream;
//import java.io.OutputStream;
//import java.net.HttpURLConnection;
//import java.net.MalformedURLException;
//import java.net.URL;
//import java.util.ArrayList;
//import java.util.List;
//
//public class DepartmentContent {
//    public static String DEPARTMENT_CONTENT =
//            "http://192.168.31.179:8080/TransactionManagement/newDepartmentServlet";
//
//    public static String getJSONDepartmentContent(int did) {
//
//        HttpURLConnection connection = null;
//        OutputStream out = null;
//        InputStream in = null;
//        try {
//            URL url = new URL(DEPARTMENT_CONTENT);
//            connection = (HttpURLConnection) url.openConnection();
//            connection.setRequestMethod("POST");
//            connection.setConnectTimeout(5000);
//            connection.setReadTimeout(5000);
//            connection.setDoInput(true);
//            connection.setDoOutput(true);
//            connection.setUseCaches(false);
//
//            String data = "did=" + did;
//            out = connection.getOutputStream();
//            out.write(data.getBytes());
//            out.flush();
//            out.close();
//            connection.connect();
//
//            if (connection.getResponseCode() == 200) {
//                in = connection.getInputStream();
//                return parseJSON(in);
//            }
//
//        } catch (MalformedURLException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        } finally {
//            try {
//                if (out != null) {
//                    out.close();
//                }
//                if (in != null) {
//                    in.close();
//                }
//                if (connection != null) {
//                    connection.disconnect();
//                }
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//
//        }
//
//        return null;
//    }
//
//    private static String parseJSON(InputStream in) {
//        //创建一个数组存储解析出来的对象
//        try {
//            //List<Department> list = new ArrayList<>();
//            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
//            byte[] buffer = new byte[1024];
//            int len;
//            while ((len = in.read(buffer)) != -1) {
//                outputStream.write(buffer, 0, len);
//            }
//            outputStream.close();
//            String json = new String(outputStream.toByteArray());
//            JSONObject jsonObject = new JSONObject(json);
//            String d_content = jsonObject.getString("d_content");
//            //list.add(new Department(d_content));
//            return d_content;
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return null;
//    }
//}
