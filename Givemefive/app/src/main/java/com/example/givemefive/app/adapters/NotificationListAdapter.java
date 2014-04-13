package com.example.givemefive.app.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.givemefive.app.R;

import java.util.List;
import java.util.Map;

/**
 * Created by ljj on 2014/4/13.
 */
public class NotificationListAdapter extends ArrayAdapter<Map<String, String>> {
    private Context context;
    private int resourceId;
    private List<Map<String, String>> notificationList;

    public NotificationListAdapter(Context ct, int resource,  List<Map<String, String>> objects) {
        super(ct, resource, objects);
        context = ct;
        resourceId = resource;
        notificationList = objects;
    }

    public final class ViewHolder{
        //活动简要信息
        TextView tvTitle;
        TextView tvTime;
        ImageView ivType;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder holder = null;
        if (view == null){
            view = LayoutInflater.from(context).inflate(resourceId,null);
            holder=new ViewHolder();
            holder.tvTitle = (TextView)view.findViewById(R.id.textViewNoticeTitle);
            holder.tvTime = (TextView)view.findViewById(R.id.textViewNoticeTime);
            holder.ivType = (ImageView)view.findViewById(R.id.imageViewNoticeType);
            view.setTag(holder);
        }else {
            holder = (ViewHolder) view.getTag();
        }
        Map<String, String> notification = notificationList.get(i);
        holder.tvTitle.setText(notification.get("title"));
        holder.tvTime.setText(notification.get("time"));

        return view;
    }
}
