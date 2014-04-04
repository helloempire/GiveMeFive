package com.example.givemefive.app;

/**
 * Created by cyy on 14-3-15.
 */

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
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

    public RightFragment(Context con){
        context=con;
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.right_fragment, null);

        bttestdatabase = (Button)view.findViewById(R.id.test_database);
        btlogin = (Button)view.findViewById(R.id.login);
        btregister = (Button)view.findViewById(R.id.register);
        buttonSetting = (Button)view.findViewById(R.id.buttonSetting);

        btlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(),login.class);
                startActivity(intent);
            }
        });

        btregister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(),Register.class);
                startActivity(intent);
            }
        });

        bttestdatabase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(),test_database.class);
                startActivity(intent);
            }
        });

        buttonSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(),SettingsActivity.class);
                startActivity(intent);
            }
        });
        
        return view;
    }

    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }
}