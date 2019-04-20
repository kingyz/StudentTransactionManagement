package com.kingyzll.future;

import android.content.Context;
import android.content.Intent;
import android.drm.DrmStore;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.kingyzll.activityCollector.BaseActivity;
import com.kingyzll.adpater.NotificationAdapater;
import com.kingyzll.databases.Notification;

import org.litepal.crud.DataSupport;

import java.util.ArrayList;
import java.util.Collections;
import java.util.IllegalFormatCodePointException;
import java.util.List;

public class NotificationListActivity extends BaseActivity {
    private List<Notification> notificationList = new ArrayList<>();
    private NotificationAdapater adapater;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification_list);
        initView();
        Toolbar toolbar = findViewById(R.id.notification_list_toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        //adapater.notifyDataSetChanged();

        // Intent intent = getIntent();
        //int position = intent.getIntExtra("position", 0);
//        if (position != 0) {
//            notificationList.remove(position);
//            adapater.notifyDataSetChanged();
//        }

    }

    private void initView() {
        notificationList.clear();
        notificationList = DataSupport.findAll(Notification.class);
        Collections.reverse(notificationList);
        RecyclerView recyclerView = findViewById(R.id.notification_list_recycler_view);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        adapater = new NotificationAdapater(notificationList);
        recyclerView.setAdapter(adapater);
        Toolbar notification_list_toolbar = findViewById(R.id.notification_list_toolbar);
        setSupportActionBar(notification_list_toolbar);
    }

    @Override
    protected void onResume() {
        super.onResume();
        initView();
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

    //    public static void actionStartNotificationListActivity(Context context, int position) {
//        Intent intent = new Intent(context, NotificationListActivity.class);
//        intent.putExtra("position", position);
//        context.startActivity(intent);
//    }

}
