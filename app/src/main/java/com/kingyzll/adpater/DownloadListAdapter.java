package com.kingyzll.adpater;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.kingyzll.bean.Files;
import com.kingyzll.future.DownloadFileActivity;
import com.kingyzll.future.DownloadListActivity;
import com.kingyzll.future.R;

import java.io.File;
import java.util.List;

//public class DownloadlistAdapter extends ArrayAdapter<Files> {
//
//    private int resourceId;
//
//    public DownloadlistAdapter(@NonNull Context context, int resource, List<Files> objects) {
//        super(context, resource, objects);
//        resourceId = resource;
//    }
//
//    @NonNull
//    @Override
//    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
//        Files files = getItem(position);
//        View view;
//        ViewHolder viewHolder;
//        if (convertView == null) {
//            view = LayoutInflater.from(getContext()).inflate(resourceId, parent, false);
//            viewHolder = new ViewHolder();
//            viewHolder.downloadlistTitle = view.findViewById(R.id.download_list_title);
//            view.setTag(viewHolder);
//        } else {
//            view = convertView;
//            viewHolder = (ViewHolder) view.getTag();
//        }
//        viewHolder.downloadlistTitle.setText(files.getF_title());
//        return view;
//    }
//
//    class ViewHolder {
//        TextView downloadlistTitle;
//    }
//}

public class DownloadListAdapter extends RecyclerView.Adapter<DownloadListAdapter.ViewHolder> {

    private Context mContext;
    private List<Files> mFilesList;

    public DownloadListAdapter(List<Files> filesList) {
        mFilesList = filesList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int position) {
        if (mContext == null) {
            mContext = viewGroup.getContext();
        }
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_download_list, viewGroup, false);
        final ViewHolder holder = new ViewHolder(view);
        holder.downloadlistTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = holder.getAdapterPosition();
                Files file = mFilesList.get(position);
                DownloadFileActivity.actionStartDownloadFileActivity(mContext,
                        file.getF_id(), file.getF_title(), file.getF_content(),
                        file.getF_date(), file.getF_publisher(), file.getF_url());
            }
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {
        Files files = mFilesList.get(position);
        viewHolder.downloadlistTitle.setText(files.getF_title());
    }

    @Override
    public int getItemCount() {
        return mFilesList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        LinearLayout linearLayout;
        TextView downloadlistTitle;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            linearLayout = (LinearLayout) itemView;
            downloadlistTitle = itemView.findViewById(R.id.download_list_title);
        }
    }
}
