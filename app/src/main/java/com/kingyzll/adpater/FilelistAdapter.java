package com.kingyzll.adpater;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.MimeTypeMap;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.kingyzll.bean.Files;
import com.kingyzll.customView.SlideDelete;
import com.kingyzll.future.R;

import java.io.File;
import java.util.List;

import static com.kingyzll.updateUI.DownloadTask.FILE_DIRECTORY;

public class FilelistAdapter extends RecyclerView.Adapter<FilelistAdapter.ViewHolder> {

    private Context mContext;
    private List<Files> mFilesList;

    public FilelistAdapter(List<Files> filesList) {
        mFilesList = filesList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        if (mContext == null) {
            mContext = viewGroup.getContext();
        }
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_slide, viewGroup, false);
        final ViewHolder holder = new ViewHolder(view);
        holder.fileDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int position = holder.getAdapterPosition();
                Files files = mFilesList.get(position);
                File file = new File(FILE_DIRECTORY + File.separator + files.getF_file_list_title());
                file.delete();
                mFilesList.remove(position);
                notifyDataSetChanged();
                //更新下父容器
                holder.slideDelete.requestLayout();
            }
        });

//        holder.fileTitle.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                int position = holder.getAdapterPosition();
//                Files files = mFilesList.get(position);
//                String savePath = FILE_DIRECTORY + File.separator + files.getF_file_list_title();
//                Intent sendIntent = new Intent();
//                sendIntent.setAction(Intent.ACTION_SEND);
//                String mimeType = MimeTypeMap.getSingleton().getMimeTypeFromExtension(getFileExtension(savePath));
//                sendIntent.setType(mimeType);
//                sendIntent.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(new File(savePath)));
//                sendIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);    //这一步很重要。给目标应用一个临时的授权。
//                mContext.startActivity(Intent.createChooser(sendIntent, "Share to..."));    //或者其它最终处理方式
//            }
//        });


        return holder;
    }

    private static String getFileExtension(String path) {
        String suffix = null;
        File file = new File(path);
        if (file.exists()) {
            String name = file.getName();
            final int idx = name.lastIndexOf(".");
            if (idx > 0) {
                suffix = name.substring(idx + 1);
            }
        }
        return suffix;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {
        Files file = mFilesList.get(position);
        String afterSubStringName = file.getF_file_list_title().substring(0, file.getF_file_list_title().indexOf("."));
        viewHolder.fileTitle.setText(afterSubStringName);
        viewHolder.fileIcon.setImageResource(file.getF_file_list_icon());
    }

    @Override
    public int getItemCount() {
        return mFilesList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        CardView cardView;
        LinearLayout linearLayout;
        SlideDelete slideDelete;
        TextView fileTitle;
        TextView fileDelete;
        ImageView fileIcon;


        private ViewHolder(@NonNull View itemView) {
            super(itemView);
            cardView = (CardView) itemView;
            //linearLayout = (LinearLayout) itemView;
            slideDelete = itemView.findViewById(R.id.slide_delete_view);
            fileTitle = itemView.findViewById(R.id.file_title);
            fileDelete = itemView.findViewById(R.id.file_delete);
            fileIcon = itemView.findViewById(R.id.file_icon);
        }
    }
}
