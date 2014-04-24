package com.example.givemefive.app;

/**
 * Created by cyy on 14-3-15.
 */

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.example.givemefive.app.adapters.MainGridViewAdapter;
import com.example.givemefive.app.adapters.NotificationListAdapter;
import com.example.givemefive.app.adapters.RoomCommentAdapter;
import com.example.givemefive.app.adapters.SomeRoomListAdapter;
import com.example.givemefive.app.adapters.SomeTimeListAdapter;
import com.example.givemefive.app.capricorn.RayMenu;
import com.example.givemefive.app.tools.PostGetJson;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

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
    private ImageButton buttonRefresh;

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

    //评论
    private List<Map<String,String>> comments;

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

    /*
    onCreateView()----------------------------------------------------------------------------------
     */

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
        initStateInfos(0);
        mainGridViewAdapter = new MainGridViewAdapter(context,stateInfos, TOTAL_ROOM, TOTAL_TIME, BEGIN_TIME);//19间琴房，7个时间段
        gridView.setNumColumns(TOTAL_ROOM+1);
        gridView.setHorizontalScrollBarEnabled(true);
        gridView.setAdapter(mainGridViewAdapter);

        //手动刷新
        buttonRefresh = (ImageButton)view.findViewById(R.id.imageButtonRefresh);
        buttonRefresh.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                initStateInfos(0);
            }
        });

        //其它控件
        textViewIntroduction = (TextView)view.findViewById(R.id.textViewIntroduction);
        textViewIntroduction.setText("这里可以放一点文字");
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
                if (buttonTomorrow.getText().equals("查看明天")){
                    buttonTomorrow.setText("查看今天");
                    initStateInfos(1);
                }else {
                    buttonTomorrow.setText("查看明天");
                    initStateInfos(0);
                }
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

        initNotifications();

        View footerView = LayoutInflater.from(context).inflate(R.layout.item_list_dialog_footer, null);
        ImageButton imageButtonLoadMore = (ImageButton)footerView.findViewById(R.id.imageButtonMore);
        imageButtonLoadMore.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                loadMoreNotifications();
            }
        });
        listViewNotices.addFooterView(footerView);

        notificationListAdapter = new NotificationListAdapter(context, R.layout.item_list_notification, notifications);
        listViewNotices.setAdapter(notificationListAdapter);

        return view;
    }

    /*
    onCreateView()----------------------------------------------------------------------------------
     */

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
                    showDialogSomeRoom(selectRoomNum);//从1开始
                }else if(selectRoomNum==0){
                    showDialogSomeTime(beginTimeClock);//从BEGIN_TIME开始
                }else {
                    showDialogBooking(context, stateInfos.get(getPosition(beginTimeClock,selectRoomNum)));
                }

            }
        });
    }

    /*
    * 数据部分！------------------------------------------------------------------------------------
    */

    //刷新，对GridView里面的数据的重新加载，生成StateInfos
    //参数date，0：今天；1：明天
    private void initStateInfos(int date){
        stateInfos = new ArrayList<StateInfo>();
        StateInfo stateInfoNull = null;
        StateInfo stateInfoTmp = null;
        for (int i=0;i<=TOTAL_ROOM;i++){//第一行
            stateInfoNull = new StateInfo();
            stateInfoNull.setTimeId(0);
            stateInfoNull.setRoomId(i);
            stateInfos.add(stateInfoNull);
        }
        for (int i=0;i<TOTAL_TIME;i++){//每一行
            stateInfoNull = new StateInfo();
            stateInfoNull.setRoomId(0);
            stateInfoNull.setTimeId(i + BEGIN_TIME);
            stateInfos.add(stateInfoNull);//第一列
            for (int j=1;j<=TOTAL_ROOM;j++){
                stateInfoTmp = new StateInfo();
                stateInfoTmp.setRoomId(j);
                stateInfoTmp.setTimeId(i + BEGIN_TIME);
                stateInfoTmp.setStateId(0);
                stateInfoTmp.setStateName("空闲");
                stateInfos.add(stateInfoTmp);
            }
        }
    }

    //初始化通知
    private void initNotifications(){
        notifications = new ArrayList<Map<String, String>>();
        Map<String, String> temp;
        List<NameValuePair> pairs = new ArrayList<NameValuePair>();
        pairs.add(new BasicNameValuePair("id","1"));
        PostGetJson postGetJson = new PostGetJson(getString(R.string.postStadiumNotices),pairs);
        String json = "";
        JSONArray jsonArray = null;
        try {
            json = postGetJson.getJsonDate();
            jsonArray = new JSONArray(json);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        if(json.equals("")||json.equals("{}")||jsonArray==null){
            return;
        }
        for(int i=0;i<jsonArray.length();i++){
            temp = new HashMap<String, String>();
            try {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                temp.put("title",jsonObject.getString("title"));
                temp.put("time",jsonObject.getString("time"));
                temp.put("content",jsonObject.getString("content"));
                notifications.add(temp);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
    //加载更多通知
    private void loadMoreNotifications(){

    }

    //初始化评论
    //参数，room：房间号
    private void initComments(int room){
        comments = new ArrayList<Map<String, String>>();
        HttpGet httpGet = new HttpGet(getString(R.string.getComments)+"?site_id="+"1"+"&room_id="+String.valueOf(room));//暂时只是1说明是琴房的数据
        HttpClient httpClient = new DefaultHttpClient();
        HttpResponse httpResponse = null;
        String json = "";
        JSONArray jsonArray = null;
        try {
            httpResponse = httpClient.execute(httpGet);
            json = EntityUtils.toString(httpResponse.getEntity());
            jsonArray = new JSONArray(json);
            for(int i=0;i<jsonArray.length();i++){
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                Map<String, String> temp = new HashMap<String, String>();
                temp.put("content",jsonObject.getString("content"));
                temp.put("time",jsonObject.getString("time"));
                temp.put("user",jsonObject.getString("user_id"));
                temp.put("id",jsonObject.getString("id"));
                comments.add(temp);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    //加载更多评论
    //参数，room：房间号
    private void loadMoreComments(int room){
    }
    /*
    * 数据部分！------------------------------------------------------------------------------------
    */

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

    //工具类
    public int getPosition(int time, int room){
        int position = (time-BEGIN_TIME+1)*(TOTAL_ROOM+1)+room;
        return position;
    }

    //Dialog
    //针对某时间某场地的预订
    private void showDialogBooking(Context context, StateInfo stateInfo){
        final Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.dialog_book_info);
        dialog.setTitle(stateInfo.getRoomId() + "号——开始时间:" + stateInfo.getTimeId() + ":00");

        TextView textView = (TextView)dialog.findViewById(R.id.textViewState);
        textView.setText("状态：" + stateInfo.getStateName());

        Button buttonBook = (Button)dialog.findViewById(R.id.buttonBookNow);
        Button buttonComment = (Button)dialog.findViewById(R.id.buttonViewComment);
        buttonBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.hide();
            }
        });
        buttonComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.hide();
            }
        });

        LinearLayout linearLayout = (LinearLayout)dialog.findViewById(R.id.layout_only_admin);

        dialog.show();
    }

    //某一个时间内所有房间的情况
    private void showDialogSomeTime(int beginTime){
        final Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.dialog_some_time);
        dialog.setTitle("时间:" + beginTime + ":00~"+String.valueOf(beginTime+1)+":00");

        ListView listViewSomeTime = (ListView)dialog.findViewById(R.id.listViewDialogSomeTime);

        List<StateInfo> stateInfoSomeTime = new ArrayList<StateInfo>();
        for (int i = 1;i<=TOTAL_ROOM;i++){
            stateInfoSomeTime.add(stateInfos.get(getPosition(beginTime,i)));
        }
        SomeTimeListAdapter someTimeListAdapter = new SomeTimeListAdapter(context, R.layout.item_list_dialog_some_time, stateInfoSomeTime);
        listViewSomeTime.setAdapter(someTimeListAdapter);

        dialog.show();
    }

    //某一个房间在两天之内的情况
    private void showDialogSomeRoom(final int roomNum){
        final Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.dialog_some_room);
        dialog.setTitle(roomNum + "号");

        ListView listViewSomeRoom = (ListView)dialog.findViewById(R.id.listViewDialogSomeRoom);
        TextView textViewResume = (TextView)dialog.findViewById(R.id.textViewRoomResume);
        ListView listViewComments = (ListView)dialog.findViewById(R.id.listViewRoomComments);
        EditText editTextComment = (EditText)dialog.findViewById(R.id.editTextComment);
        ImageButton imageButtonSubmitCmt = (ImageButton)dialog.findViewById(R.id.imageButtonSubCmt);

        //房态
        List<StateInfo> stateInfoRoom = new ArrayList<StateInfo>();
        for (int i=BEGIN_TIME;i<BEGIN_TIME+TOTAL_TIME;i++){
            stateInfoRoom.add(stateInfos.get(getPosition(i, roomNum)));
        }
        SomeRoomListAdapter someRoomListAdapter = new SomeRoomListAdapter(context, R.layout.item_list_dialog_some_room, stateInfoRoom);
        listViewSomeRoom.setAdapter(someRoomListAdapter);

        //简介
        textViewResume.setText("简介：");

        //评论
        initComments(roomNum);
        View footerView = LayoutInflater.from(context).inflate(R.layout.item_list_dialog_footer, null);
        ImageButton imageButtonLoadMore = (ImageButton)footerView.findViewById(R.id.imageButtonMore);
        imageButtonLoadMore.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                loadMoreComments(roomNum);
            }
        });
        listViewComments.addFooterView(footerView);
        RoomCommentAdapter roomCommentAdapter = new RoomCommentAdapter(context, R.layout.item_list_dialog_comment, comments);
        listViewComments.setAdapter(roomCommentAdapter);

        //发表评论
        imageButtonSubmitCmt.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        dialog.show();
    }

}