package com.kingyzll.future;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.kingyzll.activityCollector.BaseActivity;
import com.kingyzll.adpater.DepartmentAdapter;
import com.kingyzll.bean.Department;
import com.kingyzll.fragment.DepartmentFragment;
import com.kingyzll.information.DepartmentIntro;

import java.util.ArrayList;
import java.util.List;

public class DepartmentActivity extends BaseActivity {

    private static final String TAG = "department";

    private List<Department> departmentList = new ArrayList<>();
    private DepartmentAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_department);
        //initView();
//        RecyclerView recyclerView = findViewById(R.id.dep_recycler_view);
//        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
//        recyclerView.setLayoutManager(layoutManager);
//
//        adapter = new DepartmentAdapter(departmentList);
//        recyclerView.setAdapter(adapter);
        //initDepartment();
        DepartmentFragment departmentFragment = (DepartmentFragment) getSupportFragmentManager().findFragmentById(R.id.department_fragment);
        //departmentFragment.refresh(departmentList);


    }

    //获取服务器返回的json数据
    private void initDepartment() {
        // 改前
        new Thread(new Runnable() {
            @Override
            public void run() {
                List<Department> list = DepartmentIntro.getJSONLastDepartment();
                // setDepartmentUI(list);
                Log.e(TAG, "=============获取list");
                if (list != null) {
                    getDepartmentList(list);
                }

            }
        }).start();

    }

    //把服务器返回的json数据添加到数组里
    private void setDepartmentUI(final List<Department> list) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                for (Department departments : list) {
                    //int id = getResources().getIdentifier("icon" + i, "drawable", getPackageName());
                    Department department = new Department(departments.getDep_id(), departments.getDep_title(),
                            departments.getDep_content(), departments.getDep_imageId(),
                            departments.getDep_address(), departments.getDep_contact(),
                            departments.getDep_email());
                    departmentList.add(department);
                }
                //有时候打开此页面 有数据但item显示不出来 那么就要用到notifyDataSetChanged方法
                adapter.notifyDataSetChanged();
            }
        });
    }

    //把服务器返回的json数据添加到数组里
    private void getDepartmentList(List<Department> list) {
        for (Department departments : list) {
            Department department = new Department(departments.getDep_id(), departments.getDep_title(),
                    departments.getDep_content(), departments.getDep_imageId(),
                    departments.getDep_address(), departments.getDep_contact(),
                    departments.getDep_email());
            Log.e(TAG, "=============添加到departmentList中");
            departmentList.add(department);
        }
    }


//    private void initView() {
//        Department department1 = new Department(1, "电气与计算机工程学院", "aaaaa", R.drawable.icon0);
//        departmentList.add(department1);
//        Department department2 = new Department(2, "商学院", R.drawable.icon1);
//        departmentList.add(department2);
//    }
}
