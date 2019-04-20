package com.kingyzll.future;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.nfc.Tag;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.kingyzll.activityCollector.BaseActivity;
import com.kingyzll.login.LoginConnect;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.ref.WeakReference;
import java.net.HttpURLConnection;
import java.net.URL;

import cn.jpush.android.api.JPushInterface;

public class LoginActivity extends BaseActivity {
    private Button loginBtn;
    private EditText accountEdt, pwdEdt;
    private MyHandle myHandle = new MyHandle(this);
    private static final String TAG = "LoginActivity";
    private CheckBox checkBox;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        // SharedPreferences.Editor editor = getSharedPreferences("data", MODE_PRIVATE).edit();
        SharedPreferences pref = getSharedPreferences("data", MODE_PRIVATE);
        if (pref.getString("login", "").equals("1") && pref.getString("accountNum", null) != null) {
            //String accountNum = pref.getString("accountNum", "");
            HomeActivity.actionStartHomeActivity(LoginActivity.this);

            LoginActivity.this.finish();
        }
        initView();
        JPushInterface.stopPush(getApplicationContext());
    }


    private static class MyHandle extends Handler {
        private final WeakReference<LoginActivity> mActivity;

        public MyHandle(LoginActivity loginActivity) {
            mActivity = new WeakReference<LoginActivity>(loginActivity);
        }

        @Override
        public void handleMessage(Message msg) {
            //如果为空 表示被回收了
            if (mActivity.get() == null) {
                return;
            }
            mActivity.get().updateUIThread(msg);
        }
    }

    private void updateUIThread(Message msg) {
        Bundle bundle = new Bundle();
        bundle = msg.getData();
        String result = bundle.getString("result");
        if (result.equals("success")) {
            String accountNum = accountEdt.getText().toString();
            SharedPreferences.Editor editor2 = getSharedPreferences("data", MODE_PRIVATE).edit();
            if (checkBox.isChecked()) {
                editor2.putString("login", "1");
                editor2.apply();
            }
            editor2.putString("accountNum", accountNum);
            editor2.apply();
            HomeActivity.actionStartHomeActivity(LoginActivity.this);
            LoginActivity.this.finish();

        } else {
            pwdEdt.setText("");
            Toast.makeText(LoginActivity.this, result, Toast.LENGTH_SHORT).show();
        }
    }


    private void initView() {
        loginBtn = findViewById(R.id.login_btn);
        accountEdt = findViewById(R.id.login_account);
        pwdEdt = findViewById(R.id.login_password);
        checkBox = findViewById(R.id.login_checkbox);


        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (accountEdt.getText().toString().isEmpty() ||
                        pwdEdt.getText().toString().isEmpty()) {
                    Toast.makeText(LoginActivity.this,
                            "账号或密码不能为空", Toast.LENGTH_SHORT).show();
                    return;
                } else {
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            Log.e(TAG, "线程开始");

                            String result = LoginConnect.LoginByPost(accountEdt.getText().toString(),
                                    pwdEdt.getText().toString());
                            Bundle bundle = new Bundle();
                            bundle.putString("result", result);
                            Message msg = new Message();
                            msg.setData(bundle);
                            myHandle.sendMessage(msg);

                        }
                    }).start();
                }
            }
        });
    }


}


