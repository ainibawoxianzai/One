package com.example.administrator.one.fragment;


import android.os.Bundle;
//import android.os.Handler;
//import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.administrator.one.R;
import com.example.administrator.one.adapter.HomeFragmentPagerAdapter;
import com.example.administrator.one.callback.DataCallback;
import com.example.administrator.one.utils.HttpUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment implements DataCallback {


    private ViewPager viewPager;
    private String HOME_PATH = "http://v3.wufazhuce.com:8000/api/hp/idlist/0";
    private List<String> list = new ArrayList<>();
    private List<Fragment> fragmentList = new ArrayList<>();
    FragmentManager fm;
    HomeFragmentPagerAdapter pagerAdapter;


    //    public HomeFragment() {
//        // Required empty public constructor
//    }

    public static HomeFragment newInstance(){
        HomeFragment fragment = new HomeFragment();
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initData();

    }

//    private Handler handler = new Handler(){
//        @Override
//        public void handleMessage(Message msg) {
//            super.handleMessage(msg);
//            if(msg.what==0){
//                String json = (String) msg.obj;
//                try {
//                    JSONObject jsonObject = new JSONObject(json);
//                    String data = jsonObject.getString("data");
//                    String newString = data.substring(2, data.length() - 3);
//                    String[] subString = newString.split("\",\"");
//                    for (int i = 0; i < subString.length; i++) {
////                        String s = subString[i];
////                        Log.e("===","==="+s);
//                        list.add(subString[i]);
//                    }
//                        pagerAdapter = new MyPagerAdapter(fm, fragmentList);
//                        viewPager.setAdapter(pagerAdapter);
//                        pagerAdapter.notifyDataSetChanged();
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//            }
//        }
//    };

    private void initData() {
//        for (int i = 0; i < 10; i++) {
//            ViewPagerChild fragment = ViewPagerChild.newInstance();
//
//            fragmentList.add(fragment);
//        }

        HttpUtils httpUtils = HttpUtils.getHttpUtils();
        httpUtils.getStrByNetWork(0,0, HOME_PATH,null,null);
        httpUtils.setDataCallback(this);

//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                String json = HttpUtils.getStringByNetWork(HOME_PATH);
//                Message message = new Message();
//                message.what=0;
//                message.obj = json;
//                handler.sendMessage(message);
//            }
//        }).start();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_main_home, container, false);
        viewPager = (ViewPager) view.findViewById(R.id.home_view_pager);
         fm = getFragmentManager();
//        pagerAdapter = new MyPagerAdapter(fm, fragmentList);
//        viewPager.setAdapter(pagerAdapter);
        return view;
    }

    @Override
    public void setDataCallback(int id,int type, String json) {
        try {
            JSONObject jsonObject = new JSONObject(json);
//            String data = jsonObject.getString("data");
//            String newString = data.substring(2, data.length() - 3);
//            String[] subString = newString.split("\",\"");
//            for (int i = 0; i < subString.length; i++) {
////                        String s = subString[i];
////                        Log.e("===","==="+s);
//                list.add(subString[i]);
//            }

            JSONArray data1 = jsonObject.getJSONArray("data");
            for (int i = 0; i < data1.length(); i++) {
                String index = data1.getString(i);
                ViewPagerChild fragment = ViewPagerChild.newInstance();
                Bundle bundle = new Bundle();
                bundle.putString("id",index);
                fragment.setArguments(bundle);
                fragmentList.add(fragment);
            }
            pagerAdapter = new HomeFragmentPagerAdapter(fm, fragmentList);
            viewPager.setAdapter(pagerAdapter);
            pagerAdapter.notifyDataSetChanged();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }



}
