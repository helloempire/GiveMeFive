package com.example.givemefive.app;

import android.app.Activity;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
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
import java.util.ArrayList;
import java.util.List;

/**
 * Handles the user registration activity.
 * 
 */
public class Register extends Activity {
    private static final String url = "http://givemefive.dongyueweb.com";
    private DefaultHttpClient httpClient;
    private HttpResponse response;
    private HttpPost httpPost;
    private HttpEntity entity;

	private EditText newUsername;
	private EditText newPassword;
	private EditText newConfiPass;
    private EditText newStudentID;
    private EditText newEmail;
    private Button registerButton;
    private Button backButton;


	
	 @Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	        
        SharedPreferences settings = getSharedPreferences(RightFragment.MY_PREFS, 0);
        Editor editor = settings.edit();
        editor.putLong("uid", 0);
        editor.commit();

        setContentView(R.layout.register);
        initControls();
	    }
	 
	 /**
	  * Handles interface controls.
	 */
	 private void initControls()
	    {
		 	newUsername = (EditText) findViewById(R.id.nUsername);
		 	newPassword = (EditText) findViewById(R.id.nPassword);
		 	newConfiPass = (EditText) findViewById(R.id.nConfiPass);
            newStudentID = (EditText) findViewById(R.id.nStuID);
            newEmail = (EditText) findViewById(R.id.nEmail);
		 	registerButton = (Button) findViewById(R.id.nRegister);
            backButton = (Button) findViewById(R.id.Back);

		 	registerButton.setOnClickListener(new Button.OnClickListener() { 
		 		public void onClick (View v){ 
		 			RegisterMe(v); }});

            backButton.setOnClickListener(new Button.OnClickListener() {
                public void onClick (View v){
                    BackToLogin(v); }});


	    }

    private void BackToLogin(View v)
    {
        finish();
    }

	  /**
	   * Handles the registration process.
	   * @param v
	   */
	 private void RegisterMe(View v)
	    {
		 	//Get user details. 
	    	String username = newUsername.getText().toString();
	    	String password = newPassword.getText().toString();
	    	String confirmpassword = newConfiPass.getText().toString();
            String stuID = newStudentID.getText().toString();
            String email = newEmail.getText().toString();
	    	
	    	//Check if all fields have been completed.
	    	if (username.equals("") || password.equals("") || confirmpassword.equals("") || stuID.equals("") || email.equals("")){
	    		Toast.makeText(getApplicationContext(), 
	    				"请完成表格后确认",
				          Toast.LENGTH_SHORT).show();
	  		return;
	    	}
	    	
	    	//Check password match. 
	    	if (!password.equals(confirmpassword)) {
	    		Toast.makeText(getApplicationContext(), 
	    				"密码不匹配",
				          	Toast.LENGTH_SHORT).show();
	    					newPassword.setText("");
	    					newConfiPass.setText("");
	    		return;
	    	}

//            Toast.makeText(this.getApplicationContext(),
//                    username + password + stuID,
//                    Toast.LENGTH_SHORT).show();

            httpClient = new DefaultHttpClient();
            try{
                //httpPost = new  HttpPost(url + "/index.php/user/appgenerallogin");
                httpPost = new HttpPost(url + "/index.php/user/create_memeber");

                List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();

                JSONObject jsonObject = new JSONObject();
                jsonObject.put("username",username);
                jsonObject.put("password",password);
                jsonObject.put("studentnumber",stuID);

                nameValuePairs.add(new BasicNameValuePair("signinfo",jsonObject.toString()));

                Log.i("testdatabase", "inputusername = " + username);
                Log.i("testdatabase","inputpassword = " + password);


                Toast.makeText(this.getApplicationContext(),
                        nameValuePairs.toString(),
                        Toast.LENGTH_SHORT).show();


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
                    //test = "create member test";
                    test = "login test";
                    if (status == "true") {

                        Toast.makeText(this.getApplicationContext(),
                                "注册成功",
                                Toast.LENGTH_SHORT).show();

                    }
                    else{
                        Toast.makeText(this.getApplicationContext(),
                                "注册失败",
                                Toast.LENGTH_SHORT).show();
                        saveLoggedInUId(0, "", "");
                    }
                    Toast.makeText(this.getApplicationContext(),
                            response,
                            Toast.LENGTH_SHORT).show();

                }

            }catch (Exception e){
                System.out.println();
                Log.i("testdatabase","HttpStatus = fail");
            }
        }



	 
	 private void saveLoggedInUId(long id, String username, String password) {
			SharedPreferences settings = getSharedPreferences(RightFragment.MY_PREFS, 0);
			Editor editor = settings.edit();
			editor.putLong("uid", id);
			editor.putString("username", username);
			editor.putString("password", password);
			editor.commit();
	}
}
