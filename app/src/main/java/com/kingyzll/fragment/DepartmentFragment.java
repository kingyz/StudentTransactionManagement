package com.kingyzll.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.kingyzll.adpater.DepartmentAdapter;
import com.kingyzll.bean.Department;
import com.kingyzll.future.R;
import com.kingyzll.information.DepartmentIntro;

import java.util.ArrayList;
import java.util.List;

public class DepartmentFragment extends Fragment {
    private View view;
    private static final String TAG = "department";

    private List<Department> departmentList = new ArrayList<>();
    DepartmentAdapter adapter;
    SwipeRefreshLayout swipeRefreshLayout;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.activity_department, container, false);

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initDepartment();
        initRecyclerView();
        swipeRefreshLayout.setColorSchemeColors(getResources().getColor(R.color.colorAccent));
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                initDepartment();

            }
        });
    }

    private void initRecyclerView() {
        swipeRefreshLayout = view.findViewById(R.id.dep_swipe_refresh);
        RecyclerView recyclerView = view.findViewById(R.id.dep_recycler_view);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        adapter = new DepartmentAdapter(departmentList);
        recyclerView.setAdapter(adapter);

    }

    private void initDepartment() {
        // 改前
        new Thread(new Runnable() {
            @Override
            public void run() {
                List<Department> list = DepartmentIntro.getJSONLastDepartment();
                if (list != null) {
                    setDepartmentUI(list);
                }
                Log.e(TAG, "=============获取list");
                //getDepartmentList(list);

            }
        }).start();

    }

    private void setDepartmentUI(final List<Department> list) {
        if (getActivity() != null) {
            departmentList.clear();
            getActivity().runOnUiThread(new Runnable() {
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
                    swipeRefreshLayout.setRefreshing(false);
                }
            });


        }
    }

}


