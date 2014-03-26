package com.example.givemefive.app;

/**
 * Created by cyy on 14-3-15.
 */

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

public class LeftFragment extends Fragment {

    private Context context;

    public LeftFragment(Context con){
        context = con;
    }

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
                fragmentTransaction.replace(R.id.center_frame, new CenterFragment(getActivity(), 0));
                fragmentTransaction.commit();
                ((MainActivity) getActivity()).showLeft();
            }
        });

        LinearLayout pianoPage = (LinearLayout) view.findViewById(R.id.pianoLayout);
        pianoPage.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.center_frame, new CenterFragment(getActivity(), 0));
                fragmentTransaction.commit();
                ((MainActivity) getActivity()).showLeft();
            }
        });

        LinearLayout badNewPage = (LinearLayout) view.findViewById(R.id.badNewLayout);
        badNewPage.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.center_frame, new CenterFragment(getActivity(), 1));
                fragmentTransaction.commit();
                ((MainActivity) getActivity()).showLeft();
            }
        });

        LinearLayout badOldPage = (LinearLayout) view.findViewById(R.id.badOldLayout);
        badOldPage.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.center_frame, new CenterFragment(getActivity(), 2));
                fragmentTransaction.commit();
                ((MainActivity) getActivity()).showLeft();
            }
        });

        LinearLayout tablePage = (LinearLayout) view.findViewById(R.id.tableLayout);
        tablePage.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.center_frame, new CenterFragment(getActivity(), 3));
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