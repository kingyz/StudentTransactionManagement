package com.kingyzll.information;

import com.kingyzll.bean.Files;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class GetDownloadList {
    public static String GET_DOWNLOAD_LIST =
            "http://172.20.10.3:8080/TransactionManagement/downloadListServlet";
    private static final String TAG = "GetDownloadList";

    public static List<Files> getJSONLastDownload() {
        HttpURLConnection connection = null;
        InputStream json;
        try {
            URL url = new URL(GET_DOWNLOAD_LIST);
            connection = (HttpURLConnection) url.openConnection();
            connection.setConnectTimeout(5000);
            connection.setReadTimeout(5000);
            connection.setRequestMethod("GET");
            connection.connect();

            if (connection.getResponseCode() == 200) {
                json = connection.getInputStream();
                return parseJSON(json);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }
        return null;
    }

    private static List<Files> parseJSON(InputStream jsonStream) {
        try {
            List<Files> list = new ArrayList<>();
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            byte[] buffer = new byte[1024];
            int len;
            while ((len = jsonStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, len);
            }
            jsonStream.close();
            byte[] data = outputStream.toByteArray();
            String json = new String(data);
            JSONArray jsonArray = new JSONArray(json);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                int fid = jsonObject.getInt("fid");
                String ftitle = jsonObject.getString("ftitle");
                String fcontent = jsonObject.getString("fcontent");
                String fdate = jsonObject.getString("fdate");
                String fpublisher = jsonObject.getString("fpublisher");
                String furl = jsonObject.getString("furl");
                list.add(new Files(fid, ftitle, fcontent, fdate, fpublisher, furl));
            }
            outputStream.close();
            return list;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
