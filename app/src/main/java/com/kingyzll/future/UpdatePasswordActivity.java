package com.kingyzll.future;

import android.support.v7.app.ActionBar;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.kingyzll.activityCollector.BaseActivity;
import com.kingyzll.information.UpdatePassword;

import static com.kingyzll.future.HomeActivity.STUDENT_NUM;

public class UpdatePasswordActivity extends BaseActivity implements View.OnClickListener {
    EditText edt_account, edt_old_password, edt_new_password, edt_repeat_password;
    //String num = "520";
    LinearLayout layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_password);

        Toolbar toolbar = findViewById(R.id.update_password_toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        initView();
    }

    private void initView() {
        edt_account = findViewById(R.id.edt_accout);
        edt_old_password = findViewById(R.id.edt_old_password);
        edt_new_password = findViewById(R.id.edt_new_password);
        edt_repeat_password = findViewById(R.id.edt_repeat_password);
        Button btn_affirm_account = findViewById(R.id.btn_affirm_account);
        Button btn_affirm_password = findViewById(R.id.btn_determined);
        layout = findViewById(R.id.update_password_layout);
        layout.setVisibility(View.GONE);
        btn_affirm_account.setOnClickListener(this);
        btn_affirm_password.setOnClickListener(this);


    }

    private void nextStep() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                String result = UpdatePassword.UpdatePassword(edt_account.getText().toString(),
                        edt_old_password.getText().toString(), edt_new_password.getText().toString());
                setToastText(result);
            }
        }).start();
    }

    private void setToastText(final String result) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (result.equals("success")) {
                    Toast.makeText(UpdatePasswordActivity.this, "密码修改成功!", Toast.LENGTH_SHORT).show();
                    layout.setVisibility(View.GONE);
                } else {
                    Toast.makeText(UpdatePasswordActivity.this, "密码修改失败!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_affirm_account:
                if (edt_account.getText().toString().equals(STUDENT_NUM)) {
                    layout.setVisibility(View.VISIBLE);
                } else {
                    Toast.makeText(this, "你输入的账号有误...", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.btn_determined:
                if (edt_new_password.getText().toString().equals(edt_repeat_password.getText().toString())
                        && edt_new_password.getText().toString().length() != 0 && edt_repeat_password.getText().toString().length() != 0
                        && edt_old_password.getText().toString().length() != 0) {

                    nextStep();
                } else if (!edt_new_password.getText().toString().equals(edt_repeat_password.getText().toString())) {
                    Toast.makeText(this, "两次输入的密码不一致...", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this, "你输入的有误...", Toast.LENGTH_SHORT).show();
                }
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
}
