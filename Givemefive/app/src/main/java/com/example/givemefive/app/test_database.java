package com.example.givemefive.app;

import android.app.Activity;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

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

    private EditText etusername;
    private EditText etpassword;
    private TextView tvusername;
    private TextView tvpassword;
    private TextView tvtest;
    private Button get;

    private String username;
    private String password;

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
            httpPost = new HttpPost(url + "/index.php/login/appgenerallogin");

            username = etusername.getText().toString();
            password = etpassword.getText().toString();
            Log.i("testdatabase", "inputusername = " + username);
            Log.i("testdatabase","inputpassword = " + password);

            //创建一个用户，用于向服务端发送数据时，存放的实体
            NameValuePair nameValuePair1 = new BasicNameValuePair("username",username);
            NameValuePair nameValuePair2 = new BasicNameValuePair("password",password);
            List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
            nameValuePairs.add(nameValuePair1);
            nameValuePairs.add(nameValuePair2);

            Log.i("testdatabase", "sendusername = " + nameValuePair1);
            Log.i("testdatabase","sendpassword = " + nameValuePair2);

            HttpEntity requesHttpEntity = new UrlEncodedFormEntity(nameValuePairs);
            httpPost.setEntity(requesHttpEntity);
            response = httpClient.execute(httpPost);

            if(response.getStatusLine().getStatusCode() == HttpStatus.SC_OK){
                Log.i("testdatabase","HttpStatus = OK");
                entity = response.getEntity();
                StringBuffer sb = new StringBuffer();
                BufferedReader reader = new BufferedReader(new InputStreamReader(entity.getContent()));

                String s = null;

                while ((s = reader.readLine()) != null){
                    sb.append(s);
                }

                Log.i("testdatabase","S  = " + s);

                JSONObject datas= new JSONObject(sb.toString());
                Log.i("testdatabase","datas = " + datas.toString());
                String status = null;
                String response = null;
                String test = null;

                status = datas.getString("status");
                response = datas.getString("response");
                test = "login test";

                Toast.makeText(this.getApplicationContext(),
                        response,
                        Toast.LENGTH_SHORT).show();

                tvusername.setText(status);
                tvpassword.setText(response);
                tvtest.setText(test);
            }

        }catch (Exception e){
            System.out.println();
            Log.i("testdatabase","HttpStatus = fail");
        }
    }

    private void initView() {
        etusername = (EditText) findViewById(R.id.test_et_username);
        etpassword = (EditText) findViewById(R.id.test_et_password);
        tvusername = (TextView) findViewById(R.id.username);
        tvpassword = (TextView) findViewById(R.id.password);
        tvtest = (TextView) findViewById(R.id.test);
        get = (Button) findViewById(R.id.get);


    }

}






















