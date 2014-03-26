package com.example.givemefive.app;

import android.app.Dialog;
import android.content.Context;
import android.util.Log;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

/**
 * Created by ljj on 14-3-26.
 */
public class MainGridViewAdapter extends BaseAdapter {

    private Context context;
    private List<StateInfo> stateInfos;

    private int totalTime;
    private int totalRoom;
    private int beginTime;

    public MainGridViewAdapter(Context con, List<StateInfo> si, int tr, int tt, int bt){
        context = con;
        stateInfos = si;
        totalTime = tt;
        totalRoom = tr;
        beginTime = bt;
    }

    @Override
    public int getCount() {
        return stateInfos.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        int time = getTimeId(i);
        int room = getRoomId(i);

        if (time == 0){
            view = LayoutInflater.from(context).inflate(R.layout.grid_item_top,null);

            TextView textView = (TextView)view.findViewById(R.id.textViewRoom);
            textView.setText(""+room);
        }else if(room == 0){
            view = LayoutInflater.from(context).inflate(R.layout.grid_item_left,null);

            TextView textView = (TextView)view.findViewById(R.id.textViewTime);
            time = time + beginTime - 1;
            textView.setText(""+time+":00");
        }else{
            view = LayoutInflater.from(context).inflate(R.layout.grid_item_center,null);

            ImageButton imageButton = (ImageButton)view.findViewById(R.id.imageButtonCell);
            final int ti = time + beginTime - 1;
            final int ro = room;
            imageButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    final Dialog dialog = new Dialog(context);
                    dialog.setContentView(R.layout.dialog_book_info);
                    dialog.setTitle(ro + "号——开始时间:" + ti + ":00");

                    TextView textView = (TextView)dialog.findViewById(R.id.textViewState);
                    textView.setText("状态：暂不开放");

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
            });
        }



        return view;
    }

    public int getPosition(int time, int room){
        int position = time*(totalTime+1)+room;
        return position;
    }

    public int getTimeId(int position){
        return position/(totalRoom+1);
    }

    public int getRoomId(int position){
        return position%(totalRoom+1);
    }
}
