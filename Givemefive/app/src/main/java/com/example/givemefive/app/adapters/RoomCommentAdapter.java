package com.example.givemefive.app.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.example.givemefive.app.R;

import java.util.List;
import java.util.Map;

/**
 * Created by ljj on 2014/4/13.
 */
public class RoomCommentAdapter extends ArrayAdapter<Map<String, String>> {
    private Context context;
    private int resourceId;
    private List<Map<String, String>> comments;

    public RoomCommentAdapter(Context ct, int resource, List<Map<String, String>> objects) {
        super(ct, resource, objects);
        context = ct;
        resourceId = resource;
        comments = objects;
    }

    public final class ViewHolder {
        TextView tvContent;
        TextView tvTime;
        TextView tvUserName;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder holder = null;
        if (view == null) {
            view = LayoutInflater.from(context).inflate(resourceId, null);
            holder = new ViewHolder();
            holder.tvContent = (TextView) view.findViewById(R.id.textViewCmtContent);
            holder.tvTime = (TextView) view.findViewById(R.id.textViewCmtTime);
            holder.tvUserName = (TextView) view.findViewById(R.id.textViewCmtUser);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }

        Map<String, String> cmt = comments.get(i);
        holder.tvContent.setText(cmt.get("content"));
        holder.tvUserName.setText(cmt.get("user"));
        holder.tvTime.setText(cmt.get("time"));

        return view;
    }
}
