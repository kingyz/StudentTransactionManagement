package com.kingyzll.future;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.kingyzll.activityCollector.BaseActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class TestGetImage extends BaseActivity {
    TextView textView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_get_image);

         textView = findViewById(R.id.text1);
        ImageView imageView = findViewById(R.id.get_image);
        //Glide.get(this).clearDiskCache();
        //Glide.get(this).clearMemory();
        Glide.with(TestGetImage.this).load("http://192.168.31.179:8080/TransactionManagement/image/abcabc.jpg").into(imageView);
        getJson();
    }

    private void getJson() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                HttpURLConnection connection = null;
                BufferedReader reader = null;
                try {
                    URL url = new URL("http://192.168.31.179:8080/zhonghang/hello.json");
                    connection = (HttpURLConnection) url.openConnection();
                    connection.setRequestMethod("GET");
                    connection.setReadTimeout(5000);
                    connection.setConnectTimeout(5000);
                    InputStream in = connection.getInputStream();
                    reader = new BufferedReader(new InputStreamReader(in));
                    StringBuilder response = new StringBuilder();
                    String line;
                    while ((line = reader.readLine()) != null) {
                        response.append(line);
                    }
                    JSONObject jsonObject = new JSONObject(response.toString());
                    String name = jsonObject.getString("name");
                    setUI(name);

                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    private void setUI(final String name) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                textView.setText(name);
            }
        });
    }
}
