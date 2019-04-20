package com.kingyzll.adpater;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.kingyzll.databases.Notification;
import com.kingyzll.future.ClickGetNotification;
import com.kingyzll.future.R;

import java.util.List;

public class NotificationAdapater extends RecyclerView.Adapter<NotificationAdapater.ViewHolder> {

    private Context mContext;
    private List<Notification> mNotificationList;


    public NotificationAdapater(List<Notification> notificationList) {
        mNotificationList = notificationList;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        if (mContext == null) {
            mContext = viewGroup.getContext();
        }
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_notification_list, viewGroup, false);
        final ViewHolder holder = new ViewHolder(view);
        holder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = holder.getAdapterPosition();
                Notification notification = mNotificationList.get(position);
                ClickGetNotification.actionStartClickGetNotification(mContext, notification.getContent(),notification.getDate(),position);
            }
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {
        Notification notification = mNotificationList.get(position);
        viewHolder.tvNotification.setText(notification.getContent());
        viewHolder.tvNotificationDate.setText(notification.getDate());
    }

    @Override
    public int getItemCount() {
        return mNotificationList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        LinearLayout linearLayout;
        TextView tvNotification, tvNotificationDate;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            linearLayout = (LinearLayout) itemView;
            tvNotification = itemView.findViewById(R.id.tv_notification);
            tvNotificationDate = itemView.findViewById(R.id.tv_notification_date);
        }
    }
}
