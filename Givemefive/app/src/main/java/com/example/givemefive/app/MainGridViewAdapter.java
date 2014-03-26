package com.example.givemefive.app;

import android.content.Context;
import android.util.Log;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
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

    public MainGridViewAdapter(Context con, List<StateInfo> si, int tr, int tt){
        context = con;
        stateInfos = si;
        totalTime = tt;
        totalRoom = tr;
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
        final int time = getTimeId(i);
        final int room = getRoomId(i);

        if (time == 0){
            view = LayoutInflater.from(context).inflate(R.layout.grid_item_top,null);
        }else if(room == 0){
            view = LayoutInflater.from(context).inflate(R.layout.grid_item_left,null);
        }else{
            view = LayoutInflater.from(context).inflate(R.layout.grid_item_center,null);
        }

        ImageButton imageButton = (ImageButton)view.findViewById(R.id.imageButtonCell);

        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context,""+time+" "+room,Toast.LENGTH_LONG).show();
            }
        });

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
