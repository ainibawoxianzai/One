package com.example.administrator.one.welcome;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.administrator.one.R;

import java.util.ArrayList;
import java.util.List;

public class WelcomeActivity extends AppCompatActivity {

//    List<String> imgSet = new ArrayList<>();
//    List<List> imgSets = new ArrayList<>();
//    List<WelcomeOne> fragmentList = new ArrayList<>();
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_welcome);
//        ViewPager viewPager = (ViewPager) findViewById(R.id.welcome_view_pager);
//        FragmentManager fragmentManager = getSupportFragmentManager();
//        initData();
//        WelcomeFragmentAdapter fragmentAdapter = new WelcomeFragmentAdapter(fragmentManager);
//        viewPager.setAdapter(fragmentAdapter);
//
//    }
//
//    private void initData() {
//
//        for (int i = 1; i <95; i++) {
//            imgSet.add("R.drawable.one_guide_"+i);
//        }
//        for (int i = 0; i < 5; i++) {
//            imgSets.add(imgSet);
//        }
//        for (int i = 0; i < 5; i++) {
//            WelcomeOne wf= new WelcomeOne();
//            fragmentList.add(wf);
//        }
//    }
//
//    class WelcomeFragmentAdapter extends FragmentPagerAdapter{
//
//
//        public WelcomeFragmentAdapter(FragmentManager fm) {
//            super(fm);
//        }
//
//        @Override
//        public Fragment getItem(int position) {
//            WelcomeOne fragment = fragmentList.get(position);
//            fragment.setData(position);
//            return fragment;
//        }
//
//        @Override
//        public int getCount() {
//            return fragmentList.size();
//        }
//
//
//    }
}
