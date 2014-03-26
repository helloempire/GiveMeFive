package com.example.givemefive.app;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.List;

/**
 * Created by ljj on 14-3-26.
 */
public class MainGridViewAdapter extends BaseAdapter {

    private Context context;
    private List<StateInfo> stateInfos;

    public MainGridViewAdapter(Context con, List<StateInfo> si){
        context = con;
        stateInfos = si;
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
        view = LayoutInflater.from(context).inflate(R.layout.grid_item_center,null);
        return view;
    }
}
