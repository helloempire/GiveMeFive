package com.example.givemefive.app;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.database.database.DatabaseAdapter;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
/**
 * The main application activity which serves as a login page. 
 * @author Andrei
 *
 */
public class login extends Fragment {

    public static final String MY_PREFS = "SharedPreferences";
    private DatabaseAdapter dbHelper;
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
        super.onCreate(savedInstanceState);

        SharedPreferences mySharedPreferences = this.getActivity().getSharedPreferences(MY_PREFS, 0);
        Editor editor = mySharedPreferences.edit();
        editor.putLong("uid", 0);
        editor.commit();

        dbHelper = new DatabaseAdapter(this.getActivity());
        dbHelper.open();

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
        //Get the username and password
        String thisUsername = theUsername.getText().toString();
        String thisPassword = thePassword.getText().toString();

        //Assign the hash to the password
        thisPassword = md5(thisPassword);

        // Check the existing user name and password database
        Cursor theUser = dbHelper.fetchUser(thisUsername, thisPassword);
        if (theUser != null) {
            getActivity().startManagingCursor(theUser);
            if (theUser.getCount() > 0) {
                saveLoggedInUId(theUser.getLong(theUser.getColumnIndex(DatabaseAdapter.COL_ID)), thisUsername, thePassword.getText().toString());
                getActivity().stopManagingCursor(theUser);
                theUser.close();
                FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.right_frame, new RightFragment(getActivity(),1));
                fragmentTransaction.commit();
                ((MainActivity) getActivity()).showLeft();
            }

            //Returns appropriate message if no match is made
            else {
                Toast.makeText(this.getActivity().getApplicationContext(),
                        "You have entered an incorrect username or password.",
                        Toast.LENGTH_SHORT).show();
                saveLoggedInUId(0, "", "");
            }
            this.getActivity().stopManagingCursor(theUser);
            theUser.close();
        }

        else {
            Toast.makeText(this.getActivity().getApplicationContext(),
                    "Database query error",
                    Toast.LENGTH_SHORT).show();
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