package com.kingyzll.future;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.kingyzll.activityCollector.BaseActivity;
import com.kingyzll.bean.StudentInformations;
import com.kingyzll.information.StudentInformationGet;

import java.util.List;

import static com.kingyzll.future.HomeActivity.STUDENT_NUM;

public class StudentInformationActivity extends BaseActivity implements View.OnClickListener {

    private FloatingActionButton studentInformationFab;
    TextView i_num, i_name, i_sex, i_symbol, i_in, i_intime, i_birthplace, i_homeaddr, i_postcode,
            i_health, i_study, i_contactaddr, i_schoolpost, look_detail;
    LinearLayout moreLinearLayout;
    ImageView fold_img;
    int mLayoutHeight;
    boolean isOpen = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_information);
        Toolbar studInforToolbar = findViewById(R.id.stud_infor_toolbar);
        setSupportActionBar(studInforToolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        initView();
        initStudentInformation();
        initShowHide();
        look_detail.setOnClickListener(this);
        studentInformationFab.setOnClickListener(this);
    }

    private void initShowHide() {
        moreLinearLayout.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                moreLinearLayout.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                mLayoutHeight = moreLinearLayout.getHeight();
                moreLinearLayout.setPadding(0, -mLayoutHeight, 0, 0);
            }
        });
    }


    private void initStudentInformation() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                //传入值得时候不能直接传！！！
                List<StudentInformations> list = StudentInformationGet.getJSONStudentInformation(STUDENT_NUM);
                if (list != null) {
                    setStudentInformationUI(list);
                }
            }
        }).start();

    }

    private void setStudentInformationUI(final List<StudentInformations> list) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                for (StudentInformations studentInformations : list) {
                    i_num.setText(studentInformations.getI_num());
                    if (studentInformations.getI_name().equals("null")) {
                        i_name.setText("无");
                    } else {
                        i_name.setText(studentInformations.getI_name());
                    }
                    if (studentInformations.getI_sex().equals("null")) {
                        i_sex.setText("无");
                    } else {
                        i_sex.setText(studentInformations.getI_sex());
                    }
                    if (studentInformations.getI_symbol().equals("null")) {
                        i_symbol.setText("无");
                    } else {
                        i_symbol.setText(studentInformations.getI_symbol());
                    }
                    if (studentInformations.getI_in().equals("null")) {
                        i_in.setText("无");
                    } else {
                        i_in.setText(studentInformations.getI_in());
                    }
                    if (studentInformations.getI_in().equals("null")) {
                        i_intime.setText("无");
                    } else {
                        i_intime.setText(studentInformations.getI_intime());
                    }
                    if (studentInformations.getI_birthplace().equals("null")) {
                        i_birthplace.setText("无");
                    } else {
                        i_birthplace.setText(studentInformations.getI_birthplace());
                    }
                    if (studentInformations.getI_homeaddr().equals("null")) {
                        i_homeaddr.setText("无");
                    } else {
                        i_homeaddr.setText(studentInformations.getI_homeaddr());
                    }
                    if (studentInformations.getI_postcode().equals("null")) {
                        i_postcode.setText("无");
                    } else {
                        i_postcode.setText(studentInformations.getI_postcode());
                    }
                    if (studentInformations.getI_health().equals("null")) {
                        i_health.setText("无");
                    } else {
                        i_health.setText(studentInformations.getI_health());
                    }
                    if (studentInformations.getI_study().equals("null")) {
                        i_study.setText("无");
                    } else {
                        i_study.setText(studentInformations.getI_study());
                    }
                    if (studentInformations.getI_contactaddr().equals("null")) {
                        i_contactaddr.setText("无");
                    } else {
                        i_contactaddr.setText(studentInformations.getI_contactaddr());
                    }
                    if (studentInformations.getI_schoolpost().equals("null")) {
                        i_schoolpost.setText("无");
                    } else {
                        i_schoolpost.setText(studentInformations.getI_schoolpost());
                    }
                }
            }
        });
    }

    private void initView() {
        i_num = findViewById(R.id.i_num);
        i_name = findViewById(R.id.i_name);
        i_sex = findViewById(R.id.i_sex);
        i_symbol = findViewById(R.id.i_symbol);
        i_in = findViewById(R.id.i_in);
        i_intime = findViewById(R.id.i_intime);
        i_birthplace = findViewById(R.id.i_birthplace);
        i_homeaddr = findViewById(R.id.i_homeaddr);
        i_postcode = findViewById(R.id.i_postcode);
        i_health = findViewById(R.id.i_health);
        i_study = findViewById(R.id.i_study);
        i_contactaddr = findViewById(R.id.i_contactaddr);
        i_schoolpost = findViewById(R.id.i_schoolpost);
        look_detail = findViewById(R.id.look_detail);

        moreLinearLayout = findViewById(R.id.foldInformation);
        fold_img = findViewById(R.id.fold_img);
        studentInformationFab = findViewById(R.id.student_information_fab);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.look_detail:
                ValueAnimator valueAnimator = new ValueAnimator();
                if (isOpen) {
                    valueAnimator.setIntValues(0, -mLayoutHeight);
                    look_detail.setText("查看详情");
                } else {
                    valueAnimator.setIntValues(-mLayoutHeight, 0);
                }
                valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                    @Override
                    public void onAnimationUpdate(ValueAnimator animation) {
                        int value = (int) animation.getAnimatedValue();
                        moreLinearLayout.setPadding(0, value, 0, 0);
                    }
                });
                valueAnimator.addListener(new Animator.AnimatorListener() {
                    @Override
                    public void onAnimationStart(Animator animation) {
                        look_detail.setClickable(false);
                    }

                    @Override
                    public void onAnimationEnd(Animator animation) {
                        look_detail.setClickable(true);
                    }

                    @Override
                    public void onAnimationCancel(Animator animation) {

                    }

                    @Override
                    public void onAnimationRepeat(Animator animation) {

                    }
                });
                valueAnimator.setDuration(500);
                valueAnimator.start();
                isOpen = !isOpen;
                ViewCompat.animate(fold_img).rotationBy(180f).setDuration(500).start();
                break;
            case R.id.student_information_fab:

                UpDataStudentInformationActivity.actionStartUpDataStudnetInformationActivity(
                        StudentInformationActivity.this, i_name.getText().toString(), i_sex.getText().toString(),
                        i_symbol.getText().toString(), i_in.getText().toString(), i_intime.getText().toString(), i_birthplace.getText().toString(),
                        i_homeaddr.getText().toString(), i_postcode.getText().toString(), i_health.getText().toString(),
                        i_study.getText().toString(), i_contactaddr.getText().toString(), i_schoolpost.getText().toString(), i_num.getText().toString());
                break;
            default:
                break;
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

    @Override
    protected void onResume() {
        super.onResume();

        initStudentInformation();
    }
}
