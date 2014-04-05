package com.example.givemefive.app;

/**
 * Created by cyy on 14-3-15.
 */

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class RightFragment extends Fragment {

    private Context context;
    private Button bttestdatabase;
    private Button btlogin;
    private Button btregister;
    private Button buttonSetting;
    private int ISLogin;

    public RightFragment(Context con, int isLogin){
        context = con;
        ISLogin = isLogin;
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (ISLogin == 1) {
            //已经登录的情况
            View view = inflater.inflate(R.layout.right_fragment, null);

            buttonSetting = (Button) view.findViewById(R.id.buttonSetting);



            buttonSetting.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(getActivity(), SettingsActivity.class);
                    startActivity(intent);
                }
            });

            return view;
        }
        else{
            //为登录的情况，保留了testdatabase
            View view = inflater.inflate(R.layout.unlogin_rightfra, null);

            bttestdatabase = (Button) view.findViewById(R.id.testdb);
            btlogin = (Button) view.findViewById(R.id.login);



            btlogin.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
                    fragmentTransaction.replace(R.id.center_frame, new login());
                    fragmentTransaction.commit();
                    ((MainActivity) getActivity()).showLeft();
                }
            });

            bttestdatabase.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(getActivity(), test_database.class);
                    startActivity(intent);
                }
            });


            return view;
        }
    }

    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }
}