package com.kingyzll.adpater;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.kingyzll.bean.Department;
import com.kingyzll.future.DepartmentDetailsActivity;
import com.kingyzll.future.R;

import java.util.List;

public class DepartmentAdapter extends RecyclerView.Adapter<DepartmentAdapter.ViewHolder> {

    private Context mContext;
    private List<Department> mDepartmentList;
    int dep_position;
    private static final String TAG = "DepartmentAdapter";

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        if (mContext == null) {
            mContext = viewGroup.getContext();
        }
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_department,
                viewGroup, false);

        final ViewHolder holder = new ViewHolder(view);
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dep_position = holder.getAdapterPosition();
                final Department department = mDepartmentList.get(dep_position);
//                new Thread(new Runnable() {
//                    @Override
//                    public void run() {
//                        String jsonDepartmentContent = DepartmentContent.getJSONDepartmentContent(dep_position);
//                        departments.setDep_content(jsonDepartmentContent);
//                    }
//                }).start();
                DepartmentDetailsActivity.actionStartDepartmentDetailsActivity(mContext, department.getDep_id(),
                        department.getDep_title(), department.getDep_content(), department.getDep_imageId(),
                        department.getDep_address(), department.getDep_contact(), department.getDep_email());

//                DepartmentDetailsActivity.actionStartDepartmentDetailsActivity(mContext, department.getDep_id(),
//                        department.getDep_title(), department.getDep_imageId());
            }
        });

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {
        Department department = mDepartmentList.get(position);
        viewHolder.dep_title.setText(department.getDep_title());
        //viewHolder.dep_content.setText(department.getDep_content());
        //Glide.with(mContext).load(department.getDep_imageId()).into(viewHolder.dep_image);
        //viewHolder.dep_image.setImageResource(department.getDep_imageId());
        Glide.with(mContext).load(department.getDep_imageId()).into(viewHolder.dep_image);
    }

    @Override
    public int getItemCount() {
        return mDepartmentList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        //LinearLayout linearLayout;
        CardView cardView;
        ImageView dep_image;
        TextView dep_title, dep_content;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            cardView = (CardView) itemView;
            //linearLayout = (LinearLayout) itemView;
            dep_image = itemView.findViewById(R.id.dep_img);
            dep_title = itemView.findViewById(R.id.dep_text);
            //dep_image = itemView.findViewById(R.id.department_image);
            //dep_title = itemView.findViewById(R.id.department_title);
            //dep_content = itemView.findViewById(R.id.department_content);
        }
    }

    public DepartmentAdapter(List<Department> departmentList) {
        mDepartmentList = departmentList;
    }

}
