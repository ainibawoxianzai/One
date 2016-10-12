package com.example.administrator.one.adapter;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.administrator.one.fragment.ViewPagerChild;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/9/23.
 */
//public class HomeFragmentPagerAdapter {
//}
public class HomeFragmentPagerAdapter extends FragmentPagerAdapter {

    //数据集合
    List<Fragment> fragmentList;
    List<String> list = new ArrayList<>();

    public HomeFragmentPagerAdapter(FragmentManager fm, List<Fragment> fragmentList) {
        super(fm);
        this.fragmentList = fragmentList;
    }
    //根据指定位置获取当前的fragment对象
    @Override
    public Fragment getItem(int position) {

        ViewPagerChild fragment = (ViewPagerChild) fragmentList.get(position);
//        Bundle bundle = fragment.getArguments();
//        String id = bundle.getString("id");
//        fragment.setData(id);

        return fragment;
    }

    //要加载的fragment的个数
    @Override
    public int getCount() {
        return fragmentList.size();
    }
}