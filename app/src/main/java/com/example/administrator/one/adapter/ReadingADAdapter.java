package com.example.administrator.one.adapter;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import com.example.administrator.one.callback.AdClickCallback;

import java.util.List;

/**
 * Created by Administrator on 2016/9/23.
 */
//public class ReadingADAdapter {
//}

public class ReadingADAdapter extends PagerAdapter {

    List<View> viewList;
    AdClickCallback adClickCallback;

    public ReadingADAdapter(List<View> viewList) {
        this.viewList = viewList;
    }

    public void setOnAdClickCallback(AdClickCallback adClickCallback){
        this.adClickCallback = adClickCallback;
    }

    //表示要加载的页数
    @Override
    public int getCount() {
        return viewList.size();
    }

    //该方法是检验PagerAdapter是否正常工作
    //参数2的object是该instantiateItem()返回的
    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    /**
     * 初始化ViewPager容器中的页page
     *
     * @param container:代指的ViewPager
     * @param position:根据指定创建新的View
     * @return:创建的新的Object
     */

    @Override
    public Object instantiateItem(ViewGroup container, final int position) {
        //根据指定pos位置取出View
        View view = viewList.get(position);

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (adClickCallback !=null){
                        adClickCallback.setAdClickCallback(position);

                }

            }
        });

        //把View添加到ViewGroup容器中
        container.addView(view);
        return view;
    }

    //根据指定位置把不需要的内容移除掉
    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        //super调用父类，父类直接抛出异常让自己处理
        // super.destroyItem(container, position, object);
        container.removeView(viewList.get(position));

    }
}