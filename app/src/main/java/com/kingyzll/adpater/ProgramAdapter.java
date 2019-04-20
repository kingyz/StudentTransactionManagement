package com.kingyzll.adpater;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.kingyzll.bean.Program;
import com.kingyzll.future.ProgramDetailsActivity;
import com.kingyzll.future.R;

import java.util.List;

public class ProgramAdapter extends RecyclerView.Adapter<ProgramAdapter.ViewHolder> {
    private Context mContext;
    private List<Program> mProgramList;

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        if (mContext == null) {
            mContext = viewGroup.getContext();
        }
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_program, viewGroup, false);

        final ViewHolder holder = new ViewHolder(view);
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int p_position = holder.getAdapterPosition();
                Program program = mProgramList.get(p_position);
                ProgramDetailsActivity.actionStartProgramDetailsActivity(mContext, program.getP_id(),
                        program.getP_title(), program.getP_feature(), program.getP_intro(), program.getP_core(),
                        program.getP_imgurl());
            }
        });

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {
        Program program = mProgramList.get(position);
        viewHolder.pg_title.setText(program.getP_title());
        Glide.with(mContext).load(program.getP_imgurl()).into(viewHolder.pg_image);

    }

    @Override
    public int getItemCount() {
        return mProgramList.size();
    }

    public ProgramAdapter(List<Program> programList) {
        mProgramList = programList;
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        CardView cardView;
        TextView pg_title;
        ImageView pg_image;


        private ViewHolder(@NonNull View itemView) {
            super(itemView);
            cardView = (CardView) itemView;
            pg_title = itemView.findViewById(R.id.pg_title);
            pg_image = itemView.findViewById(R.id.pg_image);
        }
    }
}
