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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.example.givemefive.app.adapters.MainGridViewAdapter;
import com.example.givemefive.app.adapters.NotificationListAdapter;
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
    private Button buttonTomorrow;

    private Spinner spinnerSelectTime;
    private Spinner spinnerSelectRoom;
    private int beginTimeClock = 0;
    private int selectRoomNum = 0;
    private Button buttonFind;

    //旋转菜单
    private static final int[] ITEM_DRAWABLES = { R.drawable.composer_camera,
            R.drawable.composer_music,
            R.drawable.composer_place,
            R.drawable.composer_sleep,
            R.drawable.composer_thought,
            R.drawable.composer_with };

    //通知
    private TextView textViewUpPullTitle;
    private ListView listViewNotices;
    private NotificationListAdapter notificationListAdapter;
    private List<Map<String, String>> notifications;

    public CenterFragment(Context con, int centerId){
        context = con;
        CENTER_ID = centerId;
        switch (centerId){
            case 0:
                titleCurrent = context.getString(R.string.title_piano_room)+context.getString(R.string.title_end_book);
                TOTAL_ROOM = 19;
                TOTAL_TIME = 7;
                BEGIN_TIME = 15;
                break;
            case 1:
                titleCurrent = context.getString(R.string.title_badminton_new)+context.getString(R.string.title_end_book);
                TOTAL_ROOM = 16;
                TOTAL_TIME = 12;
                BEGIN_TIME = 10;
                break;
            case 2:
                titleCurrent = context.getString(R.string.title_badminton_old)+context.getString(R.string.title_end_book);
                TOTAL_ROOM = 5;
                TOTAL_TIME = 12;
                BEGIN_TIME = 8;
                break;
            case 3:
                titleCurrent = context.getString(R.string.title_table_tennis_new)+context.getString(R.string.title_end_book);
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
        textViewIntroduction.setText("横轴表示编号，纵轴表示时间");
        buttonHelp = (Button)view.findViewById(R.id.buttonHelp);
        buttonHelp.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                Dialog dialog = new Dialog(context);
                dialog.setContentView(R.layout.dialog_table_help);
                dialog.show();
            }
        });

        //查看明天的情况
        buttonTomorrow = (Button)view.findViewById(R.id.buttonTomorrow);
        buttonTomorrow.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        //输入查询
        spinnerSelectTime = (Spinner)view.findViewById(R.id.spinnerTime);
        spinnerSelectRoom = (Spinner)view.findViewById(R.id.spinnerRoom);

        String[] times = getSpinnerStringsTime();
        String[] rooms = getSpinnerStringsRoom();
        ArrayAdapter arrayAdapterTime = new ArrayAdapter(getActivity(),android.R.layout.simple_spinner_item,times);
        ArrayAdapter arrayAdapterRoom = new ArrayAdapter(getActivity(),android.R.layout.simple_spinner_item,rooms);
        spinnerSelectTime.setAdapter(arrayAdapterTime);
        spinnerSelectRoom.setAdapter(arrayAdapterRoom);

        buttonFind = (Button)view.findViewById(R.id.buttonCheck);
        initInputFindListener();

        //菜单
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

        //通知
        textViewUpPullTitle = (TextView)view.findViewById(R.id.textViewUpPanelTitle);
        textViewUpPullTitle.setText(context.getString(R.string.title_piano_room)+context.getString(R.string.title_end_notice));
        listViewNotices = (ListView)view.findViewById(R.id.listViewNotices);
        notifications = new ArrayList<Map<String, String>>();
        for (int i=0;i<10;i++){
            Map<String, String> temp = new HashMap<String, String>();
            temp.put("title","biaoti:"+i);
            temp.put("time","shijian:"+i);
            temp.put("type","leixing:"+i);
            temp.put("content","zhengwen:"+i);
            notifications.add(temp);
        }
        notificationListAdapter = new NotificationListAdapter(context, R.layout.item_list_notification, notifications);
        listViewNotices.setAdapter(notificationListAdapter);

        return view;
    }

    //输入查找
    private void initInputFindListener(){
        spinnerSelectTime.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (i!=0){
                    beginTimeClock = i+BEGIN_TIME-1;
                }else{
                    beginTimeClock = 0;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        spinnerSelectRoom.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                selectRoomNum = i;
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        buttonFind.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if (beginTimeClock==0 && selectRoomNum==0){
                    Toast.makeText(getActivity(),"请选择需要查询的项目",Toast.LENGTH_SHORT).show();
                }else if(beginTimeClock==0){

                }else if(selectRoomNum==0){

                }else {

                }

            }
        });
    }

    //spinner显示的内容
    private String[] getSpinnerStringsTime(){
        String[] strings = new String[TOTAL_TIME+1];
        strings[0] = "全部时间";
        for (int i=BEGIN_TIME;i<BEGIN_TIME+TOTAL_TIME;i++){
            strings[i-BEGIN_TIME+1] = String.valueOf(i)+":00~"+String.valueOf(i+1)+":00";
        }
        return strings;
    }
    private String[] getSpinnerStringsRoom(){
        String[] strings = new String[TOTAL_ROOM+1];
        strings[0] = "全部场地";
        for (int i=1;i<=TOTAL_ROOM;i++){
            strings[i] = String.valueOf(i);
        }
        return strings;
    }

}