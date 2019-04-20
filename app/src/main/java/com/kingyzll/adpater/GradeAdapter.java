package com.kingyzll.adpater;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.kingyzll.bean.Grade;
import com.kingyzll.future.R;

import java.util.List;

public class GradeAdapter extends RecyclerView.Adapter<GradeAdapter.ViewHolder> {

    private List<Grade> mGradeList;
    private Context mContext;

    static class ViewHolder extends RecyclerView.ViewHolder {
        CardView cardView;
        TextView tvCourseName, tvProperty, tvCredit, tvScore;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            cardView = (CardView) itemView;

            tvCourseName = itemView.findViewById(R.id.item_courseName);
            tvProperty = itemView.findViewById(R.id.item_property);
            tvCredit = itemView.findViewById(R.id.item_credit);
            tvScore = itemView.findViewById(R.id.item_score);
        }
    }

    public GradeAdapter(List<Grade> gradeList) {
        mGradeList = gradeList;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        if (mContext == null) {
            mContext = viewGroup.getContext();
        }
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_grade,
                viewGroup, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {
        Grade grade = mGradeList.get(position);
        viewHolder.tvCourseName.setText(grade.getCourseName());
        viewHolder.tvProperty.setText(grade.getProperty());
        viewHolder.tvCredit.setText(grade.getCredit());
        viewHolder.tvScore.setText(grade.getScore());
    }

    @Override
    public int getItemCount() {
        return mGradeList.size();
    }


}
