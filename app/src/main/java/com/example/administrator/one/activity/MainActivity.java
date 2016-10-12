package com.example.administrator.one.activity;

import android.graphics.Color;
import android.os.Build;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.example.administrator.one.R;
import com.example.administrator.one.fragment.HomeFragment;
import com.example.administrator.one.fragment.MovieFragment;
import com.example.administrator.one.fragment.MusicFragment;
import com.example.administrator.one.fragment.ReadFragment;

public class MainActivity extends AppCompatActivity {

    private TextView mTital;
    private HomeFragment homeFragment;
    private ReadFragment readFragment;
    private MusicFragment musicFragment;
    private FragmentManager fragmentManager;
    private Fragment curFragment;
    private MovieFragment movieFragment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        if (Build.VERSION.SDK_INT >= 21) {
//            getWindow().setStatusBarColor(getResources().getColor(R.color.black));
//            //设置整个背景的颜色
//            getWindow().setBackgroundDrawableResource(R.color.white);
//        }
//
//        Toolbar toolbar = (Toolbar) findViewById(R.id.tool_bar);
//        View view = LayoutInflater.from(this).inflate(R.layout.main_toolbar, toolbar);
//        mTotal = (TextView) view.findViewById(R.id.main_bar_center_text);
//        if (Build.VERSION.SDK_INT >= 16) {
////            mTotal.setBackground(R.drawable.);
//        }

        mTital = (TextView) findViewById(R.id.main_bar_center_text);
        if(Build.VERSION.SDK_INT>=16) {
            mTital.setBackground(getResources().getDrawable(R.drawable.nav_title));
        }

        fragmentManager = getSupportFragmentManager();
        initFragmet();
        initView();

    }

    private void initView() {
        RadioGroup radioGroup = (RadioGroup) findViewById(R.id.main_bottom_radio_group);
        radioGroup.check(R.id.main_bottom_home_rb);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if(Build.VERSION.SDK_INT>=16) {
                    mTital.setBackground(getResources().getDrawable(R.color.white));
                    mTital.setText("");
                }
                switch (checkedId){

                    case R.id.main_bottom_home_rb:
                        controlFragemnt(homeFragment);
                        if(Build.VERSION.SDK_INT>=16) {
                            mTital.setBackground(getResources().getDrawable(R.drawable.nav_title));
                        }
                        break;
                    case R.id.main_bottom_read_rb:
                        controlFragemnt(readFragment);
                        if(Build.VERSION.SDK_INT>=16) {
                            mTital.setText("阅读");
                            mTital.setTextSize(1,20);
                        }
                        break;
                    case R.id.main_bottom_music_rb:
                        controlFragemnt(musicFragment);
                        if(Build.VERSION.SDK_INT>=16) {
                            mTital.setText("音乐");
                            mTital.setTextSize(1,20);
                        }
                        break;
                    case R.id.main_bottom_movie_rb:
                        controlFragemnt(movieFragment);
                        if(Build.VERSION.SDK_INT>=16) {
                            mTital.setText("电影");
                            mTital.setTextSize(1,20);
                        }
                        break;
                }
            }
        });
    }

    private void controlFragemnt(Fragment fragment) {
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        if(curFragment!=null&&curFragment.isAdded()){
            fragmentTransaction.hide(curFragment);
        }
        if(!fragment.isAdded()){
            fragmentTransaction.add(R.id.main_fragment_layout,fragment);
        }
        fragmentTransaction.show(fragment);
        fragmentTransaction.commit();
        curFragment = fragment;

    }

    private void initFragmet() {
        homeFragment = HomeFragment.newInstance();
        readFragment = ReadFragment.newInstance();
        musicFragment = MusicFragment.newInstance();
        movieFragment = MovieFragment.newInstance();
        controlFragemnt(homeFragment);
    }


}
