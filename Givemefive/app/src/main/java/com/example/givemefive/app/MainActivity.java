package com.example.givemefive.app;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.view.Window;

import com.example.givemefive.app.view.SlidingMenu;

public class MainActivity extends FragmentActivity {
    private SlidingMenu slidingMenu;// 侧边栏的view
    private LeftFragment leftFragment; // 左侧边栏的碎片化view
    private RightFragment rightFragment; // 右侧边栏的碎片化view
    private CenterFragment centerFragment;// 中间内容碎片化的view
    private FragmentTransaction fragmentTransaction; // 碎片化管理的事务

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 去标题栏
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);

        slidingMenu = (SlidingMenu) findViewById(R.id.slidingMenu);
        slidingMenu.setLeftView(getLayoutInflater().inflate(R.layout.left_frame, null));
        slidingMenu.setRightView(getLayoutInflater().inflate(R.layout.right_frame, null));
        slidingMenu.setCenterView(getLayoutInflater().inflate(R.layout.center_frame, null));

        leftFragment = new LeftFragment(MainActivity.this);
        rightFragment = new RightFragment(MainActivity.this,0);
        centerFragment = new CenterFragment(MainActivity.this, 0);

        fragmentTransaction = this.getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.left_frame, leftFragment);
        fragmentTransaction.replace(R.id.right_frame, rightFragment);
        fragmentTransaction.replace(R.id.center_frame, centerFragment);
        fragmentTransaction.commit();

    }

    public void showLeft() {
        slidingMenu.showLeftView();
    }

    public void showRight() {
        slidingMenu.showRightView();
    }

}