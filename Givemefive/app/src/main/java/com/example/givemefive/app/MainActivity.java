package com.example.givemefive.app;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.Window;

import com.example.givemefive.app.view.SlidingMenu;

//
public class MainActivity extends FragmentActivity {
    private SlidingMenu mSlidingMenu;// 侧边栏的view
    private LeftFragment leftFragment; // 左侧边栏的碎片化view
    private RightFragment rightFragment; // 右侧边栏的碎片化view
    //private SampleListFragment centerFragment;// 中间内容碎片化的view 依靠listFragement来完成左右滑动的
    private CenterFragment centerFragment;

    private FragmentTransaction ft; // 碎片化管理的事务

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 去标题栏
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(com.example.givemefive.app.R.layout.activity_main);
        Log.i("main","main 1");
        //总界面
        mSlidingMenu = (SlidingMenu) findViewById(R.id.slidingMenu);
        Log.i("main","main 2");
        //放置左右中三部分view，空白
        mSlidingMenu.setLeftView(getLayoutInflater().inflate(
                com.example.givemefive.app.R.layout.left_frame, null));
        mSlidingMenu.setRightView(getLayoutInflater().inflate(
                com.example.givemefive.app.R.layout.right_frame, null));
        mSlidingMenu.setCenterView(getLayoutInflater().inflate(
                com.example.givemefive.app.R.layout.center_frame, null));
        Log.i("main","main 3");

        //碎片化管理
        ft = this.getSupportFragmentManager().beginTransaction();
        //左侧边栏
        leftFragment = new LeftFragment();
        //右侧边栏
        rightFragment = new RightFragment();
        //替换
        ft.replace(com.example.givemefive.app.R.id.left_frame, leftFragment);
        ft.replace(com.example.givemefive.app.R.id.right_frame, rightFragment);
        Log.i("main","replace finished");

        //中间应该放个空白页面 此处目前替换为listview
        centerFragment = new CenterFragment();
        //centerFragment = new SampleListFragment();
        ft.replace(com.example.givemefive.app.R.id.center_frame, centerFragment);
        ft.commit();
    }

    /*暂时不需要的代码 cyy 13-3-18 若对这边进行删除，会导致右侧边栏点击动作闪退
    * llronclick在layout文件中
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
    */

    //展示左侧边栏
    public void showLeft() {
        mSlidingMenu.showLeftView();
    }

    //展示右侧边栏
    public void showRight() {
        mSlidingMenu.showRightView();
    }

}