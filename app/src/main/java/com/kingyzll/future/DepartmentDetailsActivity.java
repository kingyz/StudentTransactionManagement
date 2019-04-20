package com.kingyzll.future;

import android.content.Context;
import android.content.Intent;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.kingyzll.activityCollector.BaseActivity;
import com.kingyzll.adpater.ProgramAdapter;
import com.kingyzll.bean.Program;
import com.kingyzll.information.ProgramIntro;

import java.util.ArrayList;
import java.util.List;

import static com.kingyzll.future.TeacherListActivity.actionStartTeacherListActivity;

public class DepartmentDetailsActivity extends BaseActivity {

    //主要控件参数
    private String dep_title, dep_content, dep_imageId, dep_address, dep_contact, dep_email;

    private FloatingActionButton fab;
    private int dep_id;
    private List<Program> programList = new ArrayList<>();
    private ProgramAdapter adapter;
    private static final String TAG = "DepDetailsActiviy";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_department_details);

        //获取传递过来的值
        Intent intent = getIntent();
        dep_id = intent.getIntExtra("dep_id", 0);
        dep_title = intent.getStringExtra("dep_title");
        dep_content = intent.getStringExtra("dep_content");
        dep_imageId = intent.getStringExtra("dep_imageId");
        dep_address = intent.getStringExtra("dep_address");
        dep_contact = intent.getStringExtra("dep_contact");
        dep_email = intent.getStringExtra("dep_email");


        initProgram();
        initView();

        RecyclerView recyclerView = findViewById(R.id.dep_detail_recycler_view);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new ProgramAdapter(programList);
        recyclerView.setAdapter(adapter);

        Toolbar toolbar = findViewById(R.id.dep_detail_toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e(TAG, "------------------------" + String.valueOf(dep_id));
                actionStartTeacherListActivity(DepartmentDetailsActivity.this, dep_id);
            }
        });

    }

    private void initProgram() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                Log.e(TAG, "id:" + dep_id);
                List<Program> list = ProgramIntro.getJSONProgram(dep_id);
                if (list != null) {
                    setProgramUI(list);
                }

            }
        }).start();
    }

    private void setProgramUI(final List<Program> list) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                //programList.clear();
                for (Program programs : list) {
                    Log.e(TAG, programs.getP_feature());
                    Log.e(TAG, programs.getP_imgurl());
                    Program program = new Program(programs.getP_id(), programs.getP_title(),
                            programs.getP_feature(), programs.getP_intro(), programs.getP_core(), programs.getP_imgurl());
                    programList.add(program);
                }
                adapter.notifyDataSetChanged();
            }
        });
    }


//    private void initProgram() {
//        Program program = new Program(1, "计算机科学与技术", R.drawable.icon);
//        programList.add(program);
//        Program program1 = new Program(2, "计算机", R.drawable.icon0);
//        programList.add(program1);
//        Program program2 = new Program(3, "学技术", R.drawable.icon1);
//        programList.add(program2);
//        Program program3 = new Program(4, "科学与技术", R.drawable.icon);
//        programList.add(program3);
//        Program program4 = new Program(5, "技术", R.drawable.icon0);
//        programList.add(program4);
//
//    }


    private void initView() {
        //主要控件
        TextView dep_detail_content = findViewById(R.id.dep_detail_content);
        ImageView dep_detail_image = findViewById(R.id.dep_detail_iamge);
        TextView dep_tvAddress = findViewById(R.id.dep_address);
        TextView dep_tvContact = findViewById(R.id.dep_contact);
        TextView dep_tvEmail = findViewById(R.id.dep_email);

        fab = findViewById(R.id.enter_teacher_lsit_fab);
        CollapsingToolbarLayout collapsingToolbar = findViewById(R.id.dep_detail_collapsing_toolbar);
        //setSupportActionBar(dep_detail_toolbar);

        //主要控件设值
        collapsingToolbar.setTitle(dep_title);
        dep_detail_content.setText(dep_content);
        dep_tvAddress.setText(dep_address);
        dep_tvContact.setText(dep_contact);
        dep_tvEmail.setText(dep_email);
        Glide.with(DepartmentDetailsActivity.this).load(dep_imageId).into(dep_detail_image);

    }

    //改前启动活动
    public static void actionStartDepartmentDetailsActivity(Context context, int dep_id, String dep_title,
                                                            String dep_content, String dep_imageId,
                                                            String dep_address, String dep_contact, String dep_email) {
        Intent intent = new Intent(context, DepartmentDetailsActivity.class);
        intent.putExtra("dep_id", dep_id);
        intent.putExtra("dep_title", dep_title);
        intent.putExtra("dep_content", dep_content);
        intent.putExtra("dep_imageId", dep_imageId);
        intent.putExtra("dep_address", dep_address);
        intent.putExtra("dep_contact", dep_contact);
        intent.putExtra("dep_email", dep_email);
        context.startActivity(intent);
    }
//    public static void actionStartDepartmentDetailsActivity(Context context, int dep_id, String dep_title
//            , int dep_imageId) {
//        Intent intent = new Intent(context, DepartmentDetailsActivity.class);
//        intent.putExtra("dep_id", dep_id);
//        intent.putExtra("dep_title", dep_title);
//        intent.putExtra("dep_imageId", dep_imageId);
//        context.startActivity(intent);
//    }


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
