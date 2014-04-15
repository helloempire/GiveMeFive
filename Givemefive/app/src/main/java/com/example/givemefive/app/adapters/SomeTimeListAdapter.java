package com.example.givemefive.app.adapters;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.givemefive.app.R;
import com.example.givemefive.app.StateInfo;

import java.util.List;

/**
 * Created by ljj on 2014/4/13.
 */
public class SomeTimeListAdapter extends ArrayAdapter<StateInfo> {
    private Context context;
    private int resourceId;
    private List<StateInfo> stateInfos;

    public SomeTimeListAdapter(Context ct, int resource, List<StateInfo> objects) {
        super(ct, resource, objects);
        context = ct;
        resourceId = resource;
        stateInfos = objects;
    }

    public final class ViewHolder {
        TextView tvRoom;
        TextView tvState;
        Button btDo;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder holder = null;
        if (view == null) {
            view = LayoutInflater.from(context).inflate(resourceId, null);
            holder = new ViewHolder();
            holder.tvRoom = (TextView) view.findViewById(R.id.textViewRoomNum);
            holder.tvState = (TextView) view.findViewById(R.id.textViewRoomState);
            holder.btDo = (Button) view.findViewById(R.id.buttonDo);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }

        holder.tvRoom.setText(stateInfos.get(i).getRoomId()+"号");
        holder.tvState.setText(stateInfos.get(i).getStateName());

        final int ii = i;
        holder.btDo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialogBooking(context, stateInfos.get(ii));
            }
        });

        return view;
    }

    private void showDialogBooking(Context context, StateInfo stateInfo){
        final Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.dialog_book_info);
        dialog.setTitle(stateInfo.getRoomId() + "号——开始时间:" + stateInfo.getTimeId() + ":00");

        TextView textView = (TextView)dialog.findViewById(R.id.textViewState);
        textView.setText("状态：" + stateInfo.getStateName());

        Button buttonBook = (Button)dialog.findViewById(R.id.buttonBookNow);
        Button buttonComment = (Button)dialog.findViewById(R.id.buttonViewComment);
        buttonBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.hide();
            }
        });
        buttonComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.hide();
            }
        });

        LinearLayout linearLayout = (LinearLayout)dialog.findViewById(R.id.layout_only_admin);

        dialog.show();
    }
}
