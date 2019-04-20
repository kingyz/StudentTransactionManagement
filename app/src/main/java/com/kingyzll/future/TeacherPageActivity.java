package com.kingyzll.future;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.kingyzll.activityCollector.BaseActivity;
import com.kingyzll.adpater.TeacherPageAdapter;
import com.kingyzll.bean.TeacherPage;
import com.kingyzll.information.GetTeacherPage;

import java.util.ArrayList;
import java.util.List;

public class TeacherPageActivity extends BaseActivity {

    private int tid;
    private String name;
    private List<TeacherPage> teacherPageList = new ArrayList<>();
    private TeacherPageAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_page);
        Intent intent = getIntent();
        tid = intent.getIntExtra("tid", 0);
        name = intent.getStringExtra("name");

        initTeacherPageData();
        RecyclerView recyclerView = findViewById(R.id.teacher_page_recyler_view);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new TeacherPageAdapter(teacherPageList);
        recyclerView.setAdapter(adapter);

        Toolbar toolbar = findViewById(R.id.teacher_page_toolbar);
        toolbar.setTitle(name+"老师");
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

    }

    private void initTeacherPageData() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                List<TeacherPage> list = GetTeacherPage.getJSONTeacherPage(tid);
                if (list != null) {
                    setTeacherPageData(list);
                }
            }
        }).start();
    }

    private void setTeacherPageData(final List<TeacherPage> list) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                for (TeacherPage teacherPages : list) {
                    TeacherPage teacherPage = new TeacherPage(teacherPages.getCourese(), teacherPages.getClassTime());
                    teacherPageList.add(teacherPage);
                }
                adapter.notifyDataSetChanged();
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

    public static void startTeacherPageActivity(Context context, int tid, String name) {
        Intent intent = new Intent(context, TeacherPageActivity.class);
        intent.putExtra("tid", tid);
        intent.putExtra("name", name);
        context.startActivity(intent);
    }
}
