package com.example.givemefive.app;

/**
 * Created by cyy on 14-3-15.
 */

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

public class CenterFragment extends Fragment {

    private Context context;

    private ImageView lv_left;
    private ImageView iv_right;
    private TextView textViewTitle;
    private String titleCurrent;

    public CenterFragment(Context con, int centerId){
        context = con;
        switch (centerId){
            case 0:
                titleCurrent = context.getString(R.string.title_piano_room);
                break;
            case 1:
                titleCurrent = context.getString(R.string.title_badminton_new);
                break;
            case 2:
                titleCurrent = context.getString(R.string.title_badminton_old);
                break;
            case 3:
                titleCurrent = context.getString(R.string.title_table_tennis_new);
                break;
        }
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.center_fragment, null);
        //顶上那栏
        lv_left = (ImageView) view.findViewById(R.id.iv_left);
        iv_right = (ImageView) view.findViewById(R.id.iv_right);
        textViewTitle = (TextView) view.findViewById(R.id.textViewTitle);
        lv_left.setOnClickListener(new OnClickListener() {

            public void onClick(View v) {
                ((MainActivity) getActivity()).showLeft();
            }
        });
        iv_right.setOnClickListener(new OnClickListener() {

            public void onClick(View v) {
                ((MainActivity) getActivity()).showRight();
            }
        });
        textViewTitle.setText(titleCurrent);

        //

        return view;
    }
}