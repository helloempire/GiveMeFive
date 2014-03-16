package com.example.givemefive.app;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.View;
import android.view.Window;

import com.example.givemefive.app.view.SlidingMenu;

public class MainActivity extends FragmentActivity {
    private SlidingMenu mSlidingMenu;// 侧边栏的view
    private LeftFragment leftFragment; // 左侧边栏的碎片化view
    private RightFragment rightFragment; // 右侧边栏的碎片化view
    private SampleListFragment centerFragment;// 中间内容碎片化的view
    private FragmentTransaction ft; // 碎片化管理的事务

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 去标题栏
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(com.example.givemefive.app.R.layout.activity_main);
        Log.i("main","main 1");
        mSlidingMenu = (SlidingMenu) findViewById(R.id.slidingMenu);
        //mSlidingMenu = (SlidingMenu) findViewById(R.layout.activity_main);
        Log.i("main","main 2");
        mSlidingMenu.setLeftView(getLayoutInflater().inflate(
                com.example.givemefive.app.R.layout.left_frame, null));
        mSlidingMenu.setRightView(getLayoutInflater().inflate(
                com.example.givemefive.app.R.layout.right_frame, null));
        mSlidingMenu.setCenterView(getLayoutInflater().inflate(
                com.example.givemefive.app.R.layout.center_frame, null));
        Log.i("main","main 3");

        ft = this.getSupportFragmentManager().beginTransaction();
        leftFragment = new LeftFragment();
        rightFragment = new RightFragment();
        ft.replace(com.example.givemefive.app.R.id.left_frame, leftFragment);
        ft.replace(com.example.givemefive.app.R.id.right_frame, rightFragment);
        Log.i("main","main 4");

        centerFragment = new SampleListFragment();
        ft.replace(com.example.givemefive.app.R.id.center_frame, centerFragment);
        ft.commit();

    }

    public void llronclick(View v) {
        switch (v.getId()) {
            case com.example.givemefive.app.R.id.llr_energy_management:
                Intent intent = new Intent(this, DetailsActivity.class);
                Log.i("main","llronclick");
                startActivity(intent);
                break;

            default:
                break;
        }
    }

    public void showLeft() {
        mSlidingMenu.showLeftView();
    }

    public void showRight() {
        mSlidingMenu.showRightView();
    }

}