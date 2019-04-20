package com.kingyzll.adpater;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.kingyzll.bean.GradeInformation;
import com.kingyzll.future.R;

import java.util.List;

public class GradeInformationAdapter extends RecyclerView.Adapter<GradeInformationAdapter.ViewHolder> {
    private Context mContext;
    private List<GradeInformation> mGradeInformationList;
    private int opened = -1;

    public GradeInformationAdapter(List<GradeInformation> gradeInformationList) {
        mGradeInformationList = gradeInformationList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        if (mContext == null) {
            mContext = viewGroup.getContext();
        }
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_grade_information, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {
        GradeInformation gradeInformation = mGradeInformationList.get(position);
        viewHolder.tv_course.setText(gradeInformation.getCourse());
        viewHolder.tv_property.setText(gradeInformation.getProperty());
        viewHolder.tv_credit.setText(gradeInformation.getCredit());
        viewHolder.tv_semester.setText(gradeInformation.getSemester());
        viewHolder.tv_score.setText(gradeInformation.getScore());
        viewHolder.tv_gpa.setText(gradeInformation.getGpa());
        viewHolder.tv_supplymentary.setText(gradeInformation.getSupplymentary());
        viewHolder.tv_code.setText(gradeInformation.getCode());
        viewHolder.bind(position);
        setScoreColor(gradeInformation.getScore(), viewHolder);
        if ((gradeInformation.getSupplymentary()).equals("null")){
            viewHolder.tv_supplymentary.setText("无");
        }
    }



    private void setScoreColor(String scores, ViewHolder viewHolder) {
        int score = Integer.parseInt(scores);
        if (score >= 90) {
            viewHolder.tv_score.setTextColor(mContext.getResources().getColor(R.color.colorAccent));
        } else if (score >= 80) {
            viewHolder.tv_score.setTextColor(mContext.getResources().getColor(R.color.colorPrimaryDark));
        } else if (score >= 70) {
            viewHolder.tv_score.setTextColor(mContext.getResources().getColor(R.color.colorPrimary));
        } else if (score >= 60) {
            viewHolder.tv_score.setTextColor(mContext.getResources().getColor(R.color.colorPrimaryLight));
        }else {
            viewHolder.tv_score.setTextColor(mContext.getResources().getColor(R.color.black));
        }
    }

    @Override
    public int getItemCount() {
        return mGradeInformationList.size();
    }


    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        CardView cardView;
        LinearLayout unfoldGradeInformation;
        TextView tv_course, tv_property, tv_credit, tv_semester, tv_score, tv_gpa, tv_supplymentary, tv_code;

        private ViewHolder(@NonNull View itemView) {
            super(itemView);
            cardView = (CardView) itemView;
            unfoldGradeInformation = itemView.findViewById(R.id.unfold_grade_information);
            tv_course = itemView.findViewById(R.id.coures_name);
            tv_property = itemView.findViewById(R.id.property);
            tv_credit = itemView.findViewById(R.id.credit);
            tv_semester = itemView.findViewById(R.id.semester);
            tv_score = itemView.findViewById(R.id.score);
            tv_gpa = itemView.findViewById(R.id.gpa);
            tv_supplymentary = itemView.findViewById(R.id.supplymentary);
            tv_code = itemView.findViewById(R.id.code);
            cardView.setOnClickListener(this);
        }

        private void bind(int position) {
            if (position == opened) {
                unfoldGradeInformation.setVisibility(View.VISIBLE);

            } else {
                unfoldGradeInformation.setVisibility(View.GONE);
            }
        }

        @Override
        public void onClick(View v) {
            if (opened == getPosition()) {
                opened = -1;
                notifyItemChanged(getPosition());
            } else {
                int oldOpened = opened;
                opened = getPosition();
                notifyItemChanged(oldOpened);
                notifyItemChanged(opened);
            }
        }
    }
}
