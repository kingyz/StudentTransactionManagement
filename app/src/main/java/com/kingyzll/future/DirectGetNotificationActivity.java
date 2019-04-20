package com.kingyzll.future;

import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.widget.TextView;

import com.kingyzll.activityCollector.BaseActivity;

import static com.kingyzll.future.HomeActivity.NOTIFICATION_CONTENT;
import static com.kingyzll.future.HomeActivity.NOTIFICATION_DATA;

public class DirectGetNotificationActivity extends BaseActivity {

    TextView directGetNotification, directGetDate;

    private static final String TAG = "DirectGetNotificationAc";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_direct_get_notification);
        directGetNotification = findViewById(R.id.direct_get_notification);
        directGetDate = findViewById(R.id.direct_get_date);

        directGetNotification.setText(NOTIFICATION_CONTENT);
        directGetDate.setText(NOTIFICATION_DATA);

        Toolbar fileListToolBar = findViewById(R.id.direct_get_notification_toolbar);
        setSupportActionBar(fileListToolBar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {

//            if (isMainActivityTop()) {
//                Intent intent = new Intent(DirectGetNotificationActivity.this, LoginActivity.class);
//                startActivity(intent);
////            }
            Intent intent = new Intent(DirectGetNotificationActivity.this, HomeActivity.class);
            startActivity(intent);
            finish();
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
//                if (isMainActivityTop()) {
////                    Intent intent = new Intent(DirectGetNotificationActivity.this, LoginActivity.class);
////                    startActivity(intent);
//////                }
                Intent intent = new Intent(DirectGetNotificationActivity.this, HomeActivity.class);
                startActivity(intent);
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

//    private boolean isMainActivityTop() {
//        ActivityManager manager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
//        String name = manager.getRunningTasks(2).get(1).topActivity.getClassName();
//        Log.e(TAG, "NAME:" + name);
//        return name.equals("com.yulong.android.launcher3.Launcher");
//    }


}
