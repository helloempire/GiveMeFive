package com.example.givemefive.app.admin;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.example.givemefive.app.R;
import com.example.givemefive.app.adapters.NotificationListAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AdminActivity extends Activity {

    private EditText editTextMaxDay;
    private EditText editTextResume;
    private Button buttonSave1;
    private Button buttonAddNotice;
    private ListView listViewNotifications;

    private String maxDay;
    private String resume;
    private List<Map<String, String>> notificationList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        editTextMaxDay = (EditText)findViewById(R.id.editTextMaxDay);
        editTextResume = (EditText)findViewById(R.id.editTextResume);
        buttonSave1 = (Button)findViewById(R.id.buttonSave1);
        buttonAddNotice = (Button)findViewById(R.id.buttonAddNotice);
        listViewNotifications = (ListView)findViewById(R.id.listViewAdminNotice);

        buttonSave1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        buttonAddNotice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        //公告列表
        initNotificationString();
        NotificationListAdapter notificationListAdapter = new NotificationListAdapter(AdminActivity.this,R.layout.item_list_notification,notificationList);
        listViewNotifications.setAdapter(notificationListAdapter);

        listViewNotifications.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                final Dialog dialog = new Dialog(AdminActivity.this);
                dialog.setContentView(R.layout.dialog_admin_modify_notice);
                dialog.setTitle("修改公告");

                EditText editTextTitle = (EditText)dialog.findViewById(R.id.editTextModifyNoticeTitle);
                EditText editTextContent = (EditText)dialog.findViewById(R.id.editTextModifyNoticeContent);
                Button buttonSave = (Button)dialog.findViewById(R.id.buttonSaveNotice);
                Button buttonDelete = (Button)dialog.findViewById(R.id.buttonDeleteNotice);

                editTextTitle.setText(notificationList.get(i).get("title"));
                editTextContent.setText(notificationList.get(i).get("content"));
                buttonSave.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                    }
                });
                buttonDelete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                    }
                });

                dialog.show();
            }
        });
    }

    private void initNotificationString(){
        notificationList = new ArrayList<Map<String, String>>();
        Map<String, String> temp;
        for(int i=0;i<10;i++){
            temp = new HashMap<String, String>();
            temp.put("title","biaoti:"+i);
            temp.put("time","shijian:"+i);
            temp.put("type","leixing:"+i);
            temp.put("content","zhengwen:"+i);
            notificationList.add(temp);
        }
    }
}
