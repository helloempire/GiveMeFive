package com.example.givemefive.app;

import android.app.Activity;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by cyy on 14-4-3.
 */
public class test_database extends Activity {
    private static final String url = "http://givemefive.dongyueweb.com";
    private DefaultHttpClient httpClient;
    private HttpResponse response;
    private HttpPost httpPost;
    private HttpEntity entity;

    private TextView tvusername;
    private TextView tvpassword;
    private TextView tvtest;
    private Button get;

    protected void onCreate(Bundle savedInstanceState){
        //解决线程问题
        if (android.os.Build.VERSION.SDK_INT > 7) {
            StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder()
                    .detectDiskReads()
                    .detectDiskWrites()
                    .detectNetwork()   // or .detectAll() for all detectable problems
                    .penaltyLog()
                    .build());
        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.test_database);

        initView();
        addListener();


    }

    private void addListener() {
        get.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                login();
            }
        });
    }

    public void login(){
        httpClient = new DefaultHttpClient();
        try{
            httpPost = new HttpPost(url + "/index.php");
            NameValuePair nameValuePair1 = new BasicNameValuePair("test","test");
            List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
            nameValuePairs.add(nameValuePair1);

            HttpEntity requesHttpEntity = new UrlEncodedFormEntity(nameValuePairs);
            httpPost.setEntity(requesHttpEntity);
            response = httpClient.execute(httpPost);

            if(response.getStatusLine().getStatusCode() == HttpStatus.SC_OK){
                entity = response.getEntity();
                StringBuffer sb = new StringBuffer();
                BufferedReader reader = new BufferedReader(new InputStreamReader(entity.getContent()));

                String s = null;

                while ((s = reader.readLine()) != null){
                    sb.append(s);
                }

                JSONObject datas= new JSONObject(sb.toString());
                String username = null;
                String password = null;
                String test = null;

                username = datas.getString("username");
                password = datas.getString("password");
                test = datas.getString("test");

                tvusername.setText(username);
                tvpassword.setText(password);
                tvtest.setText(test);
            }

        }catch (Exception e){
            System.out.println();
        }
    }

    private void initView() {
        tvusername = (TextView) findViewById(R.id.username);
        tvpassword = (TextView) findViewById(R.id.password);
        tvtest = (TextView) findViewById(R.id.test);
        get = (Button) findViewById(R.id.get);
    }

}






















