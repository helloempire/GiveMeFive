package com.example.givemefive.app;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

/**
 * Created by cyy on 14-3-19.
 */

//如果不是ListFragment就不带左右手势滑动效果
public class CenterFragment extends Fragment {

    private ImageView lv_left;
    private ImageView iv_right;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.center_fragment, null);
        lv_left = (ImageView) view.findViewById(com.example.givemefive.app.R.id.iv_left);
        iv_right = (ImageView) view.findViewById(com.example.givemefive.app.R.id.iv_right);
        return view;
    }

    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        lv_left.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                ((MainActivity) getActivity()).showLeft();
            }
        });

        iv_right.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                ((MainActivity) getActivity()).showRight();

            }
        });

    }
}