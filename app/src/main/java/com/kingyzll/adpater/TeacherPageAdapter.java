package com.kingyzll.adpater;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.kingyzll.bean.TeacherPage;
import com.kingyzll.future.R;

import java.util.List;

public class TeacherPageAdapter extends RecyclerView.Adapter<TeacherPageAdapter.ViewHolder> {

    private Context mContext;
    private List<TeacherPage> mTeacherPageList;

    public  TeacherPageAdapter(List<TeacherPage> teacherPageList) {
        mTeacherPageList = teacherPageList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int position) {
        if (mContext == null) {
            mContext = viewGroup.getContext();
        }
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_teacher_page_classtime, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {
        TeacherPage teacherPage = mTeacherPageList.get(position);
        viewHolder.tv_course.setText(teacherPage.getCourese());
        viewHolder.tv_classtime.setText(teacherPage.getClassTime());
    }

    @Override
    public int getItemCount() {
        return mTeacherPageList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        LinearLayout linearLayout;
        TextView tv_course, tv_classtime;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            linearLayout = (LinearLayout) itemView;
            tv_course = itemView.findViewById(R.id.course);
            tv_classtime = itemView.findViewById(R.id.classtime);
        }
    }
}
