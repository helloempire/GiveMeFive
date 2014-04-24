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

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AdminActivity extends Activity {

    private EditText editTextMaxDay;
    private EditText editTextResume;
    private EditText editTextRule;
    private Button buttonSave1;
    private Button buttonAddNotice;
    private ListView listViewNotifications;

    private String maxDay="";
    private String resume="";
    private String rule="";
    private List<Map<String, String>> notificationList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        editTextMaxDay = (EditText)findViewById(R.id.editTextMaxDay);
        editTextResume = (EditText)findViewById(R.id.editTextResume);
        editTextRule = (EditText)findViewById(R.id.editTextRule);
        buttonSave1 = (Button)findViewById(R.id.buttonSave1);
        buttonAddNotice = (Button)findViewById(R.id.buttonAddNotice);
        listViewNotifications = (ListView)findViewById(R.id.listViewAdminNotice);

        initBaseInfo();
        editTextMaxDay.setText(maxDay);
        editTextResume.setText(resume);
        editTextRule.setText(rule);

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

    /*
    数据部分
     */
    //初始化基本信息
    private void initBaseInfo(){
        HttpGet httpGet = new HttpGet(getString(R.string.getStadiumBaseInfo));
        HttpClient httpClient = new DefaultHttpClient();
        HttpResponse httpResponse = null;
        String json = "";
        JSONObject jsonObject = null;
        try {
            httpResponse = httpClient.execute(httpGet);
            json = EntityUtils.toString(httpResponse.getEntity());
            jsonObject = new JSONObject(json);
            maxDay = jsonObject.getString("maxday");
            resume = jsonObject.getString("introduction");
            rule = jsonObject.getString("rule");
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    //初始化通知信息
    private void initNotificationString(){
        notificationList = new ArrayList<Map<String, String>>();
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
                notificationList.add(temp);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}
