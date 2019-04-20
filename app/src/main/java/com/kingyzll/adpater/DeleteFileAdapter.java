package com.kingyzll.adpater;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Vibrator;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.MimeTypeMap;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.kingyzll.bean.DownloadedFiles;
import com.kingyzll.customView.SlideLayout;
import com.kingyzll.databases.Notification;
import com.kingyzll.future.ClickGetNotification;
import com.kingyzll.future.R;

import org.litepal.crud.DataSupport;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Collections;

import de.hdodenhof.circleimageview.CircleImageView;

import static com.kingyzll.updateUI.DownloadTask.FILE_DIRECTORY;

public class DeleteFileAdapter extends BaseAdapter {


    private static final String TAG = "DeleteFileAdapter";

    private ArrayList<DownloadedFiles> mFileList;
    private SlideLayout mSlideLayout;


    public DeleteFileAdapter(ArrayList<DownloadedFiles> list) {
        this.mFileList = list;
    }

    @Override
    public int getCount() {
        return mFileList.size();
    }

    @Override
    public Object getItem(int position) {
        return mFileList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, final ViewGroup parent) {
        final ViewHolder holder;

        if (convertView == null) {
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_custom_slide_delete, parent, false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.civPhoto.setImageResource(mFileList.get(position).getImageId());
        holder.tvName.setText(mFileList.get(position).getTitle());
        holder.tvMessage.setText(mFileList.get(position).getType());

        holder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String fileName = mFileList.get(position).getTitle();
                try {
                    String savePath = FILE_DIRECTORY + File.separator + URLEncoder.encode(fileName, "UTF-8")
                            + mFileList.get(position).getType();
                    Intent sendIntent = new Intent();
                    sendIntent.setAction(Intent.ACTION_SEND);
                    String mimeType = MimeTypeMap.getSingleton().getMimeTypeFromExtension(getFileExtension(savePath));
                    sendIntent.setType(mimeType);
                    sendIntent.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(new File(savePath)));
                    sendIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);    //这一步很重要。给目标应用一个临时的授权。
                    parent.getContext().startActivity(Intent.createChooser(sendIntent, "分享到..."));    //或者其它最终处理方式
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
            }
        });


//        holder.tvTop.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Collections.swap(mFileList, position, 0);
//
//                notifyDataSetChanged();
//
//            }
//        });
        holder.linearLayout.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                AlertDialog.Builder dialog = new AlertDialog.Builder(parent.getContext());
                dialog.setTitle("删除文件");
                dialog.setMessage("一旦删除需要重新下载...");
                dialog.setCancelable(false);
                dialog.setPositiveButton("是", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        String fileName = mFileList.get(position).getTitle();

                        try {
                            File file = new File(FILE_DIRECTORY + File.separator + URLEncoder.encode(fileName, "UTF-8")
                                    + mFileList.get(position).getType());

                            boolean flag = file.delete();
                            Log.e(TAG, file.toString() + " " + flag);
                            mFileList.remove(position);
                            notifyDataSetChanged();
                        } catch (UnsupportedEncodingException e) {
                            e.printStackTrace();
                        }
                    }
                });
                dialog.setNegativeButton("否", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                dialog.show();
                return true;
            }
        });


        holder.tvDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String fileName = mFileList.get(position).getTitle();

                try {
                    File file = new File(FILE_DIRECTORY + File.separator + URLEncoder.encode(fileName, "UTF-8")
                            + mFileList.get(position).getType());

                    boolean flag = file.delete();
                    Log.e(TAG, file.toString() + " " + flag);
                    mFileList.remove(position);
                    notifyDataSetChanged();
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
            }
        });


        mSlideLayout = (SlideLayout) convertView;
        mSlideLayout.setOnSlideChangeListenr(new SlideLayout.onSlideChangeListenr() {
            @Override
            public void onMenuOpen(SlideLayout slideLayout) {
                mSlideLayout = slideLayout;
            }

            @Override
            public void onMenuClose(SlideLayout slideLayout) {
                if (mSlideLayout != null) {
                    mSlideLayout = null;
                }
            }

            @Override
            public void onClick(SlideLayout slideLayout) {
                if (mSlideLayout != null) {
                    mSlideLayout.closeMenu();
                }
            }
        });

        return convertView;
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

    static class ViewHolder {
        CircleImageView civPhoto;
        TextView tvName;
        TextView tvMessage;
        TextView tvDelete;
        //TextView tvTop;
        LinearLayout linearLayout;

        ViewHolder(View itemView) {
            linearLayout = itemView.findViewById(R.id.slide_content);
            civPhoto = itemView.findViewById(R.id.item_image);
            tvName = itemView.findViewById(R.id.item_title);
            tvMessage = itemView.findViewById(R.id.item_type);
            tvDelete = itemView.findViewById(R.id.item_delete);
            // tvTop = itemView.findViewById(R.id.item_top);
        }
    }
}
