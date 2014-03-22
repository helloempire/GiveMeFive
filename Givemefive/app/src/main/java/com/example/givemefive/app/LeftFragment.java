package com.example.givemefive.app;

/**
 * Created by cyy on 14-3-15.
 */

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

public class LeftFragment extends Fragment {

    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        View view = inflater.inflate(com.example.givemefive.app.R.layout.left_fragment, null);

        //监听userlayout
        LinearLayout userLayout = (LinearLayout) view.findViewById(com.example.givemefive.app.R.id.userLayout);
        userLayout.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                UserFragment user = new UserFragment();
                FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                //center_frame是个基础，点击之后后者替换前者
                ft.replace(com.example.givemefive.app.R.id.center_frame, user);
                ft.commit();
                Log.i("leftfragment","userLayout replace center_frame-user");
                ((MainActivity) getActivity()).showLeft();
            }
        });

        LinearLayout mainPage = (LinearLayout) view.findViewById(com.example.givemefive.app.R.id.mainPage);
        mainPage.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                //ft.replace(com.example.givemefive.app.R.id.center_frame, new SampleListFragment());
                ft.replace(com.example.givemefive.app.R.id.center_frame, new SampleListFragment());
                ft.commit();
                Log.i("leftfragment","userLayout replace center_frame-mainpage");
                ((MainActivity) getActivity()).showLeft();
            }
        });

        //控制中心显示区的layout
        LinearLayout otherLayout = (LinearLayout) view.findViewById(R.id.other_Layout);
        otherLayout.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                UserFragment other = new UserFragment();
                FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                ft.replace(com.example.givemefive.app.R.id.center_frame, other);
                ft.commit();
                Log.i("leftfragment","userLayout replace center_frame-other");
                ((MainActivity) getActivity()).showLeft();
            }
        });

        return view;
    }

    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

}