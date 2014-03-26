package com.example.givemefive.app;

/**
 * Created by cyy on 14-3-15.
 */

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

public class LeftFragment extends Fragment {

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.left_fragment, null);
        /*
        LinearLayout userLayout = (LinearLayout) view.findViewById(R.id.userLayout);
        userLayout.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                UserFragment user = new UserFragment();
                FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(com.example.givemefive.app.R.id.center_frame, user);
                fragmentTransaction.commit();
                ((MainActivity) getActivity()).showLeft();
            }
        });*/

        LinearLayout mainPage = (LinearLayout) view.findViewById(R.id.mainPage);
        mainPage.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.center_frame, new CenterFragment());
                fragmentTransaction.commit();
                ((MainActivity) getActivity()).showLeft();
            }
        });
        return view;
    }

    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

}