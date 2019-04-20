package com.kingyzll.adpater;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.kingyzll.bean.Teacher;
import com.kingyzll.bean.TeacherPage;
import com.kingyzll.future.R;
import com.kingyzll.future.TeacherPageActivity;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class TeacherListAdapter extends RecyclerView.Adapter<TeacherListAdapter.ViewHolder> {
    private Context mContext;
    private List<Teacher> mTeacherList;


    public TeacherListAdapter(List<Teacher> teacherList) {
        mTeacherList = teacherList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int position) {
        if (mContext == null) {
            mContext = viewGroup.getContext();
        }
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_teacherlist, viewGroup, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {
        final Teacher teacher = mTeacherList.get(position);
        viewHolder.tv_tname.setText(teacher.getT_name());
        viewHolder.tv_ttype.setText(teacher.getT_type());
        viewHolder.tv_temail.setText(teacher.getT_email());
        Glide.with(mContext).load(teacher.getT_portrait()).into(viewHolder.portraitImg);

        viewHolder.btn_enter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TeacherPageActivity.startTeacherPageActivity(mContext, teacher.getT_id(), teacher.getT_name());
            }
        });
    }

    @Override
    public int getItemCount() {
        return mTeacherList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        CardView cardView;
        CircleImageView portraitImg;
        TextView tv_tname, tv_ttype, tv_temail;
        Button btn_enter;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            cardView = (CardView) itemView;
            portraitImg = itemView.findViewById(R.id.t_img);
            tv_tname = itemView.findViewById(R.id.t_name);
            tv_ttype = itemView.findViewById(R.id.t_type);
            tv_temail = itemView.findViewById(R.id.t_emil);
            btn_enter = itemView.findViewById(R.id.enter_teacher_homepage);

        }
    }
}
