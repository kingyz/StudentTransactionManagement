package com.kingyzll.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.kingyzll.databases.Notification;
import com.kingyzll.eventbus.getmessage.MessageEvent;
import com.kingyzll.future.DirectGetNotificationActivity;
import com.kingyzll.future.HomeActivity;

import org.greenrobot.eventbus.EventBus;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import cn.jpush.android.api.JPushInterface;

public class JPushReceiver extends BroadcastReceiver {
    private static final String TAG = "JPush";

    @Override
    public void onReceive(Context context, Intent intent) {
        Bundle bundle = intent.getExtras();
        Intent i = new Intent(context, DirectGetNotificationActivity.class);
        Notification notification = new Notification();


        if (JPushInterface.ACTION_REGISTRATION_ID.equals(intent.getAction())) {
            String regId = bundle.getString(JPushInterface.EXTRA_REGISTRATION_ID);
            Log.e(TAG, "[MyReceiver] 接收Registration Id : " + regId);

        } else if (JPushInterface.ACTION_MESSAGE_RECEIVED.equals(intent.getAction())) {
            Log.e(TAG, "[MyReceiver] 接收到推送下来的自定义消息: " + bundle.getString(JPushInterface.EXTRA_MESSAGE));
            Log.e(TAG, "---------------------------------自定义消息" + bundle.getString(JPushInterface.EXTRA_MESSAGE));


        } else if (JPushInterface.ACTION_NOTIFICATION_RECEIVED.equals(intent.getAction())) {
            Log.e(TAG, "[MyReceiver] 接收到推送下来的通知");
            int notifactionId = bundle.getInt(JPushInterface.EXTRA_NOTIFICATION_ID);
            Log.e(TAG, "[MyReceiver] 接收到推送下来的通知的ID: " + notifactionId);
            String title = bundle.getString(JPushInterface.EXTRA_NOTIFICATION_TITLE);
            String content = bundle.getString(JPushInterface.EXTRA_ALERT);
            Log.e(TAG, "-------------------------------" + content);

            Calendar calendar = Calendar.getInstance();
            SimpleDateFormat format = new SimpleDateFormat("yy-MM-dd");
            String time = format.format(calendar.getTime());
            Log.e(TAG, "====================" + time);
            bundle.putString("content", bundle.getString(JPushInterface.EXTRA_ALERT));
            Log.e(TAG, "[MyReceiver] 接收到推送下来的title和content:" + title + " " + content);
            EventBus.getDefault().post(new MessageEvent(content, time));
            notification.setContent(content);
            notification.setDate(time);
            notification.save();
        } else if (JPushInterface.ACTION_NOTIFICATION_OPENED.equals(intent.getAction())) {
            Log.e(TAG, "[MyReceiver] 用户点击打开了通知");
            //点击了通知栏，打开自定义的Activity
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
            i.putExtra("bundle", bundle);
            context.startActivity(i);
        } else if (JPushInterface.ACTION_RICHPUSH_CALLBACK.equals(intent.getAction())) {
            Log.e(TAG, "[MyReceiver] 用户收到到RICH PUSH CALLBACK: " + bundle.getString(JPushInterface.EXTRA_EXTRA));
        } else if (JPushInterface.ACTION_CONNECTION_CHANGE.equals(intent.getAction())) {
            boolean connected = intent.getBooleanExtra(JPushInterface.EXTRA_CONNECTION_CHANGE, false);
            Log.e(TAG, "[MyReceiver]" + intent.getAction() + " connected state change to " + connected);
        } else {
            Log.e(TAG, "[MyReceiver] Unhandled intent - " + intent.getAction());
        }
    }
}

