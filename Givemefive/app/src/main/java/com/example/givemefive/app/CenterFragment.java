package com.example.givemefive.app;

/**
 * Created by cyy on 14-3-15.
 */

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import com.example.givemefive.app.capricorn.RayMenu;

public class CenterFragment extends Fragment {

    private int CENTER_ID;
    private int TOTAL_ROOM;
    private int TOTAL_TIME;
    private int BEGIN_TIME;

    private Context context;

    private ImageView lv_left;
    private ImageView iv_right;
    private TextView textViewTitle;
    private String titleCurrent;

    private GridView gridView;
    private MainGridViewAdapter mainGridViewAdapter;
    private List<StateInfo> stateInfos;

    private Button buttonHelp;
    private TextView textViewIntroduction;

    private Spinner spinnerSelectTime;
    private Spinner spinnerSelectRoom;
    private Button buttonFind;

    //旋转菜单
    private static final int[] ITEM_DRAWABLES = { R.drawable.composer_camera,
            R.drawable.composer_music,
            R.drawable.composer_place,
            R.drawable.composer_sleep,
            R.drawable.composer_thought,
            R.drawable.composer_with };

    public CenterFragment(Context con, int centerId){
        context = con;
        CENTER_ID = centerId;
        switch (centerId){
            case 0:
                titleCurrent = context.getString(R.string.title_piano_room);
                TOTAL_ROOM = 19;
                TOTAL_TIME = 7;
                BEGIN_TIME = 15;
                break;
            case 1:
                titleCurrent = context.getString(R.string.title_badminton_new);
                TOTAL_ROOM = 16;
                TOTAL_TIME = 12;
                BEGIN_TIME = 10;
                break;
            case 2:
                titleCurrent = context.getString(R.string.title_badminton_old);
                TOTAL_ROOM = 5;
                TOTAL_TIME = 12;
                BEGIN_TIME = 8;
                break;
            case 3:
                titleCurrent = context.getString(R.string.title_table_tennis_new);
                TOTAL_ROOM = 10;
                TOTAL_TIME = 3;
                BEGIN_TIME = 10;
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

        //情况表
        gridView = (GridView)view.findViewById(R.id.gridViewTable);
        registerForContextMenu(gridView);
        stateInfos = new ArrayList<StateInfo>();
        StateInfo stateInfo1 = new StateInfo();
        for (int i=0;i<(TOTAL_ROOM+1)*(TOTAL_TIME+1);i++){
            stateInfos.add(stateInfo1);
        }
        mainGridViewAdapter = new MainGridViewAdapter(context,stateInfos, TOTAL_ROOM, TOTAL_TIME, BEGIN_TIME);//19间琴房，7个时间段
        gridView.setNumColumns(TOTAL_ROOM+1);
        gridView.setHorizontalScrollBarEnabled(true);
        gridView.setAdapter(mainGridViewAdapter);

        //其它控件
        textViewIntroduction = (TextView)view.findViewById(R.id.textViewIntroduction);
        textViewIntroduction.setText("横轴表示房间号，纵轴表示时间");
        buttonHelp = (Button)view.findViewById(R.id.buttonHelp);
        buttonHelp.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                Dialog dialog = new Dialog(context);
                dialog.setContentView(R.layout.dialog_table_help);
                dialog.show();
            }
        });

        spinnerSelectTime = (Spinner)view.findViewById(R.id.spinnerTime);
        spinnerSelectRoom = (Spinner)view.findViewById(R.id.spinnerRoom);

        buttonFind = (Button)view.findViewById(R.id.buttonCheck);

        RayMenu rayMenu = (RayMenu) view.findViewById(R.id.ray_menu);
        final int itemCount = ITEM_DRAWABLES.length;
        for (int i = 0; i < itemCount; i++) {
            ImageView item = new ImageView(context);
            item.setImageResource(ITEM_DRAWABLES[i]);

            final int position = i;
            rayMenu.addItem(item, new OnClickListener() {

                @Override
                public void onClick(View v) {
                    Toast.makeText(context, "position:" + position, Toast.LENGTH_SHORT).show();
                }
            });// Add a menu item
        }


        return view;
    }

}