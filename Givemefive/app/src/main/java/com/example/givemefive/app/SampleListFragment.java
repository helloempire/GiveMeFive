package com.example.givemefive.app;

/**
 * Created by cyy on 14-3-15.
 */

import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class SampleListFragment extends ListFragment {
    private ImageView lv_left;
    private ImageView iv_right;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        View mView = inflater.inflate(com.example.givemefive.app.R.layout.list, null);
        lv_left = (ImageView) mView.findViewById(com.example.givemefive.app.R.id.iv_left);
        iv_right = (ImageView) mView.findViewById(com.example.givemefive.app.R.id.iv_right);
        return mView;
    }

    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        /*
        Map<String, Object> item1 = new HashMap<String, Object>();
        item1.put("list_title", getString(com.example.givemefive.app.R.string.title1));
        item1.put("list_image", com.example.givemefive.app.R.drawable.p1);
        item1.put("list_contect", getString(com.example.givemefive.app.R.string.test));
        Map<String, Object> item2 = new HashMap<String, Object>();
        item2.put("list_title", getString(com.example.givemefive.app.R.string.title1));
        item2.put("list_image", com.example.givemefive.app.R.drawable.p2);
        item2.put("list_contect", getString(com.example.givemefive.app.R.string.test));
        Map<String, Object> item3 = new HashMap<String, Object>();
        item3.put("list_title", getString(com.example.givemefive.app.R.string.title1));
        item3.put("list_image", com.example.givemefive.app.R.drawable.p3);
        item3.put("list_contect", getString(com.example.givemefive.app.R.string.test));

        Map<String, Object> item4 = new HashMap<String, Object>();
        item4.put("list_title", getString(com.example.givemefive.app.R.string.title1));
        item4.put("list_image", com.example.givemefive.app.R.drawable.p4);
        item4.put("list_contect", getString(com.example.givemefive.app.R.string.test));
        Map<String, Object> item5 = new HashMap<String, Object>();
        item5.put("list_title", getString(com.example.givemefive.app.R.string.title1));
        item5.put("list_image", com.example.givemefive.app.R.drawable.p5);
        item5.put("list_contect", getString(com.example.givemefive.app.R.string.test));
        Map<String, Object> item6 = new HashMap<String, Object>();
        item6.put("list_title", getString(com.example.givemefive.app.R.string.title1));
        item6.put("list_image", com.example.givemefive.app.R.drawable.p6);
        item6.put("list_contect", getString(com.example.givemefive.app.R.string.test));
        Map<String, Object> item7 = new HashMap<String, Object>();
        item7.put("list_title", getString(com.example.givemefive.app.R.string.title1));
        item7.put("list_image", com.example.givemefive.app.R.drawable.p7);
        item7.put("list_contect", getString(com.example.givemefive.app.R.string.test));
         */

        List<Map<String, Object>> data = new ArrayList<Map<String, Object>>();
         /*
        data.add(item1);
        data.add(item2);
        data.add(item3);

        data.add(item4);
        data.add(item5);
        data.add(item6);
        data.add(item7);
        */
        String[] from = new String[] { "list_title", "list_image", "list_contect" };
        int[] to = new int[] { com.example.givemefive.app.R.id.list_title, com.example.givemefive.app.R.id.list_image,
                com.example.givemefive.app.R.id.list_contect };
        SimpleAdapter adapter = new SimpleAdapter(getActivity(), data,
                com.example.givemefive.app.R.layout.list_item, from, to);
        setListAdapter(adapter);

        lv_left.setOnClickListener(new OnClickListener() {

            public void onClick(View v) {
                ((MainActivity) getActivity()).showLeft();
            }
        });

        iv_right.setOnClickListener(new OnClickListener() {

            public void onClick(View v) {
                ((MainActivity) getActivity()).showRight();
            }
        });


    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);

        //to lijj:改这里进
        /*
        Intent intent = new Intent(getActivity(), DetailsActivity.class);
        startActivity(intent);
        */
        Log.i("list","这是第" + id + "行");
    }
}