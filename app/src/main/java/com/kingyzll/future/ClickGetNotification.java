package com.kingyzll.future;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.kingyzll.activityCollector.BaseActivity;
import com.kingyzll.databases.Notification;

import org.litepal.crud.DataSupport;

public class ClickGetNotification extends BaseActivity {
    String content;
    String date;
    int position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_click_get_notification);

        Toolbar toolbar = findViewById(R.id.click_get_notification_toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        Intent intent = getIntent();
        content = intent.getStringExtra("content");
        date = intent.getStringExtra("date");
        position = intent.getIntExtra("position", 0);
        TextView clickGetNotificationContent = findViewById(R.id.click_get_notification_textview);
        TextView clickGetNotificationDate = findViewById(R.id.click_get_notification_date);
        clickGetNotificationContent.setText(content);
        clickGetNotificationDate.setText(date);

        deleteNotification();
    }

    private void deleteNotification() {
        FloatingActionButton fab = findViewById(R.id.click_get_notification_fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder dialog = new AlertDialog.Builder(ClickGetNotification.this);
                dialog.setTitle("删除通知");
                dialog.setMessage("一旦删除无法恢复...");
                dialog.setCancelable(false);
                dialog.setPositiveButton("是", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        DataSupport.deleteAll(Notification.class, "content=?", content);
                        //NotificationListActivity.actionStartNotificationListActivity(ClickGetNotification.this, position);
                        ClickGetNotification.this.finish();
                    }
                });
                dialog.setNegativeButton("否", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                dialog.show();
            }
        });
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

    //启动活动
    public static void actionStartClickGetNotification(Context context, String content, String date, int position) {
        Intent intent = new Intent(context, ClickGetNotification.class);
        intent.putExtra("content", content);
        intent.putExtra("date", date);
        intent.putExtra("position", position);
        context.startActivity(intent);
    }
}
