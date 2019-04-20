package com.kingyzll.future;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.TextView;

import com.kingyzll.activityCollector.BaseActivity;
import com.kingyzll.adpater.GradeAdapter;
import com.kingyzll.bean.Grade;
import com.kingyzll.fragment.CheckResultFragment;
import com.kingyzll.information.CheckResult;

import java.util.ArrayList;
import java.util.List;

public class CheckResultActivity extends BaseActivity {

    private static final String TAG = "CheckResultActivity";
    //一共有4列
    final int COLUMNCOUNT = 4;
    private List<Grade> gradeList = new ArrayList<>();
    private GradeAdapter gradeAdapter;
    private RecyclerView mRecyclerView;
    private String[] result;
    private Grade grade;
    private TextView totalScore;
    private int plus = 0;
    private int sum = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_result);
        CheckResultFragment checkResultFragment = (CheckResultFragment) getSupportFragmentManager().findFragmentById(R.id.check_result_fragment);
        //数据测试
        //initGrade();
        //initView();


    }

//    private void initGrade() {
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                result = CheckResult.performanceInformation("520");
//                if (result != null) {
//                    setGradeUI(result);
//                }
//
//            }
//        }).start();
//    }
//
//    private void setGradeUI(String[] result) {
//        int mul = (result.length + 1) / COLUMNCOUNT;
//        for (int j = 1; j <= mul; j++) {
//            grade = new Grade(result[plus], result[plus + 1], result[plus + 2], result[plus + 3]);
//            gradeList.add(grade);
//            sum += Integer.parseInt(result[plus + 2]);
//            Log.e(TAG, String.valueOf(sum));
//            plus += COLUMNCOUNT;
//        }
//        totalScore.setText(sum);
//        gradeAdapter.notifyDataSetChanged();
//    }

//    private void setTotalGrade(final int sum,) {
//        runOnUiThread(new Runnable() {
//            @Override
//            public void run() {
//                totalScore.setText("获得学分：" + sum);
//            }
//        });
//    }


//数据测试
//    private void initGrade() {
//        for(int i=0;i<20;i++){
//            Grade grade = new Grade("aaaaaaaaaaaaaaaaaaaaaaaaajava高级编程","专业必须","3","89");
//            gradeList.add(grade);
//        }
//    }
//
//    private void initView() {
//        //totalScore = findViewById(R.id.totalScore);
//        mRecyclerView = findViewById(R.id.check_result_recycleView);
//        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
//        mRecyclerView.setLayoutManager(layoutManager);
//        gradeAdapter = new GradeAdapter(gradeList);
//        mRecyclerView.setAdapter(gradeAdapter);
//    }


    public static void actionStartCheckResultActivity(Context context, String data) {
        Intent intent = new Intent(context, CheckResultActivity.class);
        intent.putExtra("accountNum", data);
        context.startActivity(intent);
    }
}
