package com.example.givemefive.app;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
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
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

//import com.database.database.DatabaseAdapter;
/**
 * The main application activity which serves as a login page. 
 * @author Andrei
 *
 */
public class login extends Fragment {

    public static final String MY_PREFS = "SharedPreferences";
    private static final String url = "http://givemefive.dongyueweb.com";
    private DefaultHttpClient httpClient;
    private HttpResponse response;
    private HttpPost httpPost;
    private HttpEntity entity;
    private EditText theUsername;
    private EditText thePassword;
    private Button loginButton;
    private Button registerButton;
    private Button clearButton;
    private Button exitButton;
    private	CheckBox rememberDetails;
    private RightFragment rightFragment;


    /** Called when the activity is first created. */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (android.os.Build.VERSION.SDK_INT > 7) {
            StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder()
                    .detectDiskReads()
                    .detectDiskWrites()
                    .detectNetwork()   // or .detectAll() for all detectable problems
                    .penaltyLog()
                    .build());
        }

        super.onCreate(savedInstanceState);

        SharedPreferences mySharedPreferences = this.getActivity().getSharedPreferences(MY_PREFS, 0);
        Editor editor = mySharedPreferences.edit();
        editor.putLong("uid", 0);
        editor.commit();

        View view = inflater.inflate(R.layout.login, null);

        initControls(view);
        return view;
    }




    private void initControls(View view) {
        //Set the activity layout.

        theUsername = (EditText) view.findViewById(R.id.Username);
        thePassword = (EditText)  view.findViewById(R.id.Password);
        loginButton = (Button)  view.findViewById(R.id.Login);
        registerButton = (Button)  view.findViewById(R.id.Register);
        //clearButton = (Button) findViewById(R.id.Clear);
        exitButton = (Button)  view.findViewById(R.id.Exit);
        rememberDetails = (CheckBox)  view.findViewById(R.id.RememberMe);

        //Create touch listeners for all buttons.
        loginButton.setOnClickListener(new Button.OnClickListener(){
            public void onClick (View v){
                LogMeIn(v);
            }
        });

        registerButton.setOnClickListener(new Button.OnClickListener(){
            public void onClick (View v){
                Register(v);
            }
        });

//        clearButton.setOnClickListener(new Button.OnClickListener(){
//            public void onClick (View v){
//                ClearForm();
//            }
//        });

        exitButton.setOnClickListener(new Button.OnClickListener(){
            public void onClick (View v){
                rightFragment = new RightFragment(getActivity(),1);
                rightFragment.getActivity();
                Exit();
            }
        });
        //Create remember password check box listener.
        rememberDetails.setOnClickListener(new CheckBox.OnClickListener(){
            public void onClick (View v){
                RememberMe();
            }
        });

        //Handle remember password preferences.
        SharedPreferences prefs = this.getActivity().getSharedPreferences(MY_PREFS, 0);
        String thisUsername = prefs.getString("username", "");
        String thisPassword = prefs.getString("password", "");
        boolean thisRemember = prefs.getBoolean("remember", false);
        if(thisRemember) {
            theUsername.setText(thisUsername);
            thePassword.setText(thisPassword);
            rememberDetails.setChecked(thisRemember);
        }

    }

    /**
     * Deals with Exit option - exits the application.
     */
    private void Exit()
    {
        FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.center_frame, new CenterFragment(getActivity(), 0));
        fragmentTransaction.commit();
        ((MainActivity) getActivity()).showLeft();
    }

    /**
     * Clears the login form.
     */
    private void ClearForm() {
        saveLoggedInUId(0,"","");
        theUsername.setText("");
        thePassword.setText("");
    }

    /**
     * Handles the remember password option.
     */
    private void RememberMe() {
        boolean thisRemember = rememberDetails.isChecked();
        SharedPreferences prefs = this.getActivity().getSharedPreferences(MY_PREFS, 0);
        Editor editor = prefs.edit();
        editor.putBoolean("remember", thisRemember);
        editor.commit();
    }

    /**
     * This method handles the user login process.  
     * @param v
     */
    private void LogMeIn(View v) {
        httpClient = new DefaultHttpClient();
        try {
            httpPost = new HttpPost(url + "/index.php/login/appgenerallogin");
            //Get the username and password
            String thisUsername = theUsername.getText().toString();
            String thisPassword = thePassword.getText().toString();

            //Assign the hash to the password
            //thisPassword = md5(thisPassword);

            Toast.makeText(this.getActivity().getApplicationContext(),
                    thisPassword,
                    Toast.LENGTH_SHORT).show();

            //创建一个用户，用于向服务端发送数据时，存放的实体
            NameValuePair nameValuePair1 = new BasicNameValuePair("username", thisUsername);
            NameValuePair nameValuePair2 = new BasicNameValuePair("password", thisPassword);
            List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
            nameValuePairs.add(nameValuePair1);
            nameValuePairs.add(nameValuePair2);

            HttpEntity requesHttpEntity = new UrlEncodedFormEntity(nameValuePairs);
            httpPost.setEntity(requesHttpEntity);
            response = httpClient.execute(httpPost);

            if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                Log.i("testdatabase", "HttpStatus = OK");
                entity = response.getEntity();
                StringBuffer sb = new StringBuffer();
                BufferedReader reader = new BufferedReader(new InputStreamReader(entity.getContent()));

                String s = null;

                while ((s = reader.readLine()) != null) {
                    sb.append(s);
                }

                Log.i("testdatabase", "S  = " + s);

                JSONObject datas = new JSONObject(sb.toString());
                Log.i("testdatabase", "datas = " + datas.toString());
                String status = null;
                String response = null;
                String test = null;

                status = datas.getString("status");
                response = datas.getString("response");
                test = "login test";


                Toast.makeText(this.getActivity().getApplicationContext(),
                        status + '\n' + response + '\n' + test,
                        Toast.LENGTH_SHORT).show();

                if (status == "true") {

                    Toast.makeText(this.getActivity().getApplicationContext(),
                            "登录成功",
                            Toast.LENGTH_SHORT).show();

                    FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
                    fragmentTransaction.replace(R.id.right_frame, new RightFragment(getActivity(), 1));
                    fragmentTransaction.commit();
                }
                else{
                    Toast.makeText(this.getActivity().getApplicationContext(),
                            "用户名不存在或密码错误",
                            Toast.LENGTH_SHORT).show();
                }
            }
        } catch (Exception e) {
            Toast.makeText(this.getActivity().getApplicationContext(),
                    "服务器不可用",
                    Toast.LENGTH_SHORT).show();
            System.out.println();
            Log.i("testdatabase", "HttpStatus = fail");
        }
    }


    /**
     * Open the Registration activity.
     * @param v
     */
    private void Register(View v)
    {
        Intent i = new Intent(v.getContext(), Register.class);
        startActivity(i);
    }

    private void saveLoggedInUId(long id, String username, String password) {
        SharedPreferences settings = this.getActivity().getSharedPreferences(MY_PREFS, 0);
        Editor myEditor = settings.edit();
        myEditor.putLong("uid", id);
        myEditor.putString("username", username);
        myEditor.putString("password", password);
        boolean rememberThis = rememberDetails.isChecked();
        myEditor.putBoolean("rememberThis", rememberThis);
        myEditor.commit();
    }

    /**
     * Deals with the password encryption.
     * @param s The password.
     * @return
     */
    private String md5(String s) {
        try {
            MessageDigest digest = java.security.MessageDigest.getInstance("MD5");
            digest.update(s.getBytes());
            byte messageDigest[] = digest.digest();

            StringBuffer hexString = new StringBuffer();
            for (int i=0; i<messageDigest.length; i++)
                hexString.append(Integer.toHexString(0xFF & messageDigest[i]));

            return hexString.toString();
        }

        catch (NoSuchAlgorithmException e) {
            return s;
        }
    }
}