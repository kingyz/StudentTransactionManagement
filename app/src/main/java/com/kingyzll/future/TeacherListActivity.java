package com.kingyzll.future;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.kingyzll.activityCollector.BaseActivity;
import com.kingyzll.adpater.TeacherAdapter;
import com.kingyzll.adpater.TeacherListAdapter;
import com.kingyzll.bean.Teacher;
import com.kingyzll.information.GetTeacherList;

import java.util.ArrayList;
import java.util.List;

public class TeacherListActivity extends BaseActivity {

    private static final String TAG = "TeacherListActivity";
    private int did;
    private List<Teacher> teacherList = new ArrayList<>();
    private TeacherListAdapter adapter;
    private Button btn_enter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_list);

        Intent intent = getIntent();
        did = intent.getIntExtra("did", 0);
        Log.e(TAG, "------------------------" + String.valueOf(did));
        initTeacherList();
        RecyclerView recyclerView = findViewById(R.id.teacher_list_recycler_view);
        GridLayoutManager layoutManager = new GridLayoutManager(this, 2);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new TeacherListAdapter(teacherList);
        recyclerView.setAdapter(adapter);

        Toolbar toolbar = findViewById(R.id.teacher_list_toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

    }

    private void initTeacherList() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                List<Teacher> list = GetTeacherList.getJSONTeacherList(did);
                if (list != null) {
                    Log.e(TAG, list.toString());
                    getTeachecrList(list);
                }
            }
        }).start();

    }

    private void getTeachecrList(final List<Teacher> list) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {

                for (Teacher teachers : list) {
                    Teacher teacher = new Teacher(teachers.getT_id(), teachers.getT_name(), teachers.getT_type(),
                            teachers.getT_email(), teachers.getT_portrait());
                    teacherList.add(teacher);
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

    public static void actionStartTeacherListActivity(Context context, int data) {
        Intent intent = new Intent(context, TeacherListActivity.class);
        intent.putExtra("did", data);
        context.startActivity(intent);
    }

}
