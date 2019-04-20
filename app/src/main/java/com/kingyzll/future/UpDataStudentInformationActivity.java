package com.kingyzll.future;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.util.LogTime;
import com.kingyzll.activityCollector.BaseActivity;
import com.kingyzll.information.ImplementUpdataInformation;

public class UpDataStudentInformationActivity extends BaseActivity implements View.OnClickListener {
    EditText edt_name, edt_sex, edt_symbol, edt_in, edt_intime, edt_birthplace, edt_homeaddr, edt_postcode,
            edt_health, edt_study, edt_contactaddr, edt_schoolpost;

    Button btn_cancel, btn_save;

    String name, sex, symbol, in, intime, birthplace, homeaddr, postcode, health, study,
            contactaddr, schoolpost, num;
    private static final String TAG = "UpDataStudentInformatio";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_up_data_student_information);
        Toolbar updateStudToolbar = findViewById(R.id.update_stud_infor_toolbar);
        setSupportActionBar(updateStudToolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        btn_cancel = findViewById(R.id.btn_cancel);
        btn_save = findViewById(R.id.btn_save);
        btn_cancel.setOnClickListener(this);
        btn_save.setOnClickListener(this);
        initView();

    }

    private void initView() {
        Intent intent = getIntent();
        name = intent.getStringExtra("name");
        sex = intent.getStringExtra("sex");
        symbol = intent.getStringExtra("symbol");
        in = intent.getStringExtra("in");
        intime = intent.getStringExtra("intime");
        birthplace = intent.getStringExtra("birthplace");
        homeaddr = intent.getStringExtra("homeaddr");
        postcode = intent.getStringExtra("postcode");
        health = intent.getStringExtra("health");
        study = intent.getStringExtra("study");
        contactaddr = intent.getStringExtra("contactaddr");
        schoolpost = intent.getStringExtra("schoolpost");
        num = intent.getStringExtra("num");
        bindText(name, sex, symbol, in, intime, birthplace, homeaddr, postcode, health, study, contactaddr, schoolpost);
    }

    private void bindText(String name, String sex, String symbol, String in, String intime, String birthplace,
                          String homeaddr, String postcode, String health, String study, String contactaddr, String schoolpost) {
        edt_name = findViewById(R.id.edt_i_name);
        edt_sex = findViewById(R.id.edt_i_sex);
        edt_symbol = findViewById(R.id.edt_i_symbol);
        edt_in = findViewById(R.id.edt_i_in);
        edt_intime = findViewById(R.id.edt_i_intime);
        edt_birthplace = findViewById(R.id.edt_i_birthplace);
        edt_homeaddr = findViewById(R.id.edt_i_homeaddr);
        edt_postcode = findViewById(R.id.edt_i_postcode);
        edt_health = findViewById(R.id.edt_i_health);
        edt_study = findViewById(R.id.edt_i_study);
        edt_contactaddr = findViewById(R.id.edt_i_contactaddr);
        edt_schoolpost = findViewById(R.id.edt_i_schoolpost);

        edt_name.setText(name);
        edt_sex.setText(sex);
        edt_symbol.setText(symbol);
        edt_in.setText(in);
        edt_intime.setText(intime);
        edt_birthplace.setText(birthplace);
        edt_homeaddr.setText(homeaddr);
        edt_postcode.setText(postcode);
        edt_health.setText(health);
        edt_study.setText(study);
        edt_contactaddr.setText(contactaddr);
        edt_schoolpost.setText(schoolpost);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_cancel:
                UpDataStudentInformationActivity.this.finish();
                break;
            case R.id.btn_save:
                readyToSave();
                break;
            default:
                break;
        }

    }

    private void readyToSave() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                String result = ImplementUpdataInformation.UpdataInformation(edt_name.getText().toString(),
                        edt_sex.getText().toString(), edt_symbol.getText().toString(), edt_in.getText().toString(),
                        edt_intime.getText().toString(), edt_birthplace.getText().toString(), edt_homeaddr.getText().toString(),
                        edt_postcode.getText().toString(), edt_health.getText().toString(), edt_study.getText().toString(), edt_contactaddr.getText().toString(),
                        edt_schoolpost.getText().toString(), num);
                Log.e(TAG, "result:" + result);
                setToastText(result);
            }
        }).start();


    }

    private void setToastText(final String result) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (result.equals("success")) {
                    Toast.makeText(UpDataStudentInformationActivity.this, "信息修改成功!", Toast.LENGTH_SHORT).show();
                    Log.e(TAG, "result:" + result);
                    // UpDataStudentInformationActivity.this.finish();
                } else {
                    Toast.makeText(UpDataStudentInformationActivity.this, "信息修改失败!", Toast.LENGTH_SHORT).show();
                    //UpDataStudentInformationActivity.this.finish();
                }
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

    public static void actionStartUpDataStudnetInformationActivity(Context context, String name,
                                                                   String sex, String symbol, String in, String intime,
                                                                   String birthplace, String homeaddr, String postcode,
                                                                   String health, String study, String contactaddr,
                                                                   String schoolpost, String num) {
        Intent intent = new Intent(context, UpDataStudentInformationActivity.class);
        intent.putExtra("name", name);
        intent.putExtra("sex", sex);
        intent.putExtra("symbol", symbol);
        intent.putExtra("in", in);
        intent.putExtra("intime", intime);
        intent.putExtra("birthplace", birthplace);
        intent.putExtra("homeaddr", homeaddr);
        intent.putExtra("postcode", postcode);
        intent.putExtra("health", health);
        intent.putExtra("study", study);
        intent.putExtra("contactaddr", contactaddr);
        intent.putExtra("schoolpost", schoolpost);
        intent.putExtra("num", num);
        context.startActivity(intent);
    }
}
