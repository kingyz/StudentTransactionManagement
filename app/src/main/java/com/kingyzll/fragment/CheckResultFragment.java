package com.kingyzll.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.kingyzll.Interface.GetAndSumCredit;
import com.kingyzll.adpater.GradeInformationAdapter;
import com.kingyzll.bean.Grade;
import com.kingyzll.bean.GradeInformation;
import com.kingyzll.future.R;
import com.kingyzll.information.GetGradeInformation;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static com.kingyzll.future.HomeActivity.STUDENT_NUM;

public class CheckResultFragment extends Fragment {

    private View view;
    private static final String TAG = "CheckResultActivity";
    private List<GradeInformation> gradeList = new ArrayList<>();
    // private TextView tv_getCredit, tv_sumCredit;
    GradeInformationAdapter gradeInformationAdapter;
    SwipeRefreshLayout swipeRefreshLayout;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.activity_check_result, container, false);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        //tv_sumCredit = view.findViewById(R.id.sum_credit);
        //tv_getCredit = view.findViewById(R.id.get_credit);
        initGrade();
        initView();
        swipeRefreshLayout.setColorSchemeColors(getResources().getColor(R.color.colorAccent));
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                initGrade();

            }
        });

    }


    private void initGrade() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                List<GradeInformation> list = GetGradeInformation.getJSONGradeInformation(STUDENT_NUM);
                if (list != null) {
                    setGradeList(list);
                    //setSumAndGetCreditUI(list);
                }
            }
        }).start();
    }

//    private void setSumAndGetCreditUI(final List<GradeInformation> list) {
//        if (getActivity() != null) {
//            getActivity().runOnUiThread(new Runnable() {
//                @Override
//                public void run() {
//                    int sum = 0;
//                    int get = 0;
//                    for (GradeInformation gradeInformationss : list) {
//                        int credit = Integer.parseInt(gradeInformationss.getCredit());
//                        sum += credit;
//                        String score = gradeInformationss.getScore();
//                        if (Integer.parseInt(score) >= 60) {
//                            get += credit;
//                        }
//                        if (gradeInformationss.getSupplymentary().equals("优秀") || gradeInformationss.getSupplymentary().equals("良好") || gradeInformationss.equals("及格")) {
//                            get += credit;
//                        }
//                    }
//                    String sums = String.valueOf(sum);
//                    String gets = String.valueOf(get);
//                    tv_sumCredit.setText(sums);
//                    tv_getCredit.setText(gets);
//                }
//            });
//        }
//    }

    private void setGradeList(final List<GradeInformation> list) {
        if (getActivity() != null) {
            gradeList.clear();
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    for (GradeInformation gradeInformations : list) {
                        GradeInformation gradeInformation = new GradeInformation(gradeInformations.getCourse(),
                                gradeInformations.getProperty(), gradeInformations.getCredit(), gradeInformations.getSemester(),
                                gradeInformations.getScore(), gradeInformations.getGpa(), gradeInformations.getSupplymentary(), gradeInformations.getCode());
                        gradeList.add(gradeInformation);
                    }
                    Collections.reverse(gradeList);
                    gradeInformationAdapter.notifyDataSetChanged();
                    swipeRefreshLayout.setRefreshing(false);
                }
            });
        }
    }

    private void initView() {
        swipeRefreshLayout = view.findViewById(R.id.swipe_refresh);
        RecyclerView mRecyclerView = view.findViewById(R.id.check_result_recycleView);
        mRecyclerView.getItemAnimator().setChangeDuration(500);
        mRecyclerView.getItemAnimator().setMoveDuration(500);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(layoutManager);
        gradeInformationAdapter = new GradeInformationAdapter(gradeList);
        mRecyclerView.setAdapter(gradeInformationAdapter);

    }


}
