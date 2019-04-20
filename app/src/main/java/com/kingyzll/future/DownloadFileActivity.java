package com.kingyzll.future;

import android.Manifest;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.PackageManager;
import android.os.IBinder;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.kingyzll.activityCollector.BaseActivity;
import com.kingyzll.service.DownloadService;

public class DownloadFileActivity extends BaseActivity {

    private DownloadService.DownloadBinder downloadBinder;
    private ServiceConnection connection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            downloadBinder = (DownloadService.DownloadBinder) service;
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };

    private int f_id;
    private String f_title, f_content, f_date, f_publisher, f_url;
    private TextView tv_title, tv_content, tv_date, tv_publisher;
    private FloatingActionButton downloadFab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_download_file);
        Toolbar downloadFileToolBar = findViewById(R.id.download_file_toolbar);
        setSupportActionBar(downloadFileToolBar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }


        Intent serviceIntent = new Intent(this, DownloadService.class);
        startService(serviceIntent);
        bindService(serviceIntent, connection, BIND_AUTO_CREATE);
        //权限
        if (ContextCompat.checkSelfPermission(DownloadFileActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(DownloadFileActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
        }

        Intent intent = getIntent();
        f_id = intent.getIntExtra("f_id", 0);
        f_title = intent.getStringExtra("f_title");
        f_content = intent.getStringExtra("f_content");
        f_date = intent.getStringExtra("f_date");
        f_publisher = intent.getStringExtra("f_publisher");
        f_url = intent.getStringExtra("f_url");
        initView();
        downloadFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                downloadBinder.startDownload(f_url);
            }
        });

    }

    private void initView() {
        downloadFab = findViewById(R.id.download_fab);
        tv_title = findViewById(R.id.download_title);
        tv_content = findViewById(R.id.download_content);
        tv_date = findViewById(R.id.download_date);
        tv_publisher = findViewById(R.id.download_publisher);
        tv_title.setText(f_title);
        tv_content.setText(f_content);
        tv_date.setText(f_date);
        tv_publisher.setText(f_publisher);
    }

    //启动活动
    public static void actionStartDownloadFileActivity(Context context, int f_id, String f_title, String f_content,
                                                       String f_date, String f_publisher, String f_url) {
        Intent intent = new Intent(context, DownloadFileActivity.class);
        intent.putExtra("f_id", f_id);
        intent.putExtra("f_title", f_title);
        intent.putExtra("f_content", f_content);
        intent.putExtra("f_date", f_date);
        intent.putExtra("f_publisher", f_publisher);
        intent.putExtra("f_url", f_url);
        context.startActivity(intent);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case 1:
                if (grantResults.length > 0 && grantResults[0] != PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(this, "拒绝权限无法使用程序", Toast.LENGTH_SHORT).show();
                    finish();
                }
                break;
            default:
                break;
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbindService(connection);
    }
}
