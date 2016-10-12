package com.example.administrator.one.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.example.administrator.one.R;
import com.example.administrator.one.activity.MainActivity;
import com.example.administrator.one.activity.ReadingADDetailActivity;
import com.example.administrator.one.adapter.ReadingADAdapter;
import com.example.administrator.one.bean.ReadingListItemBean;
import com.example.administrator.one.bean.ReadingListViewBean;
import com.example.administrator.one.bean.ReadingViewPagerBean;
import com.example.administrator.one.callback.AdClickCallback;
import com.example.administrator.one.callback.DataCallback;
import com.example.administrator.one.utils.HttpUtils;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class ReadFragment extends Fragment implements DataCallback,AdClickCallback{


    private ViewPager viewPager;
    private ViewPager viewPager2;
    public static final String READING_AD_PATH = "http://v3.wufazhuce.com:8000/api/reading/carousel/?";
    public static final String READING_CONTENT_PATH = "http://v3.wufazhuce.com:8000/api/reading/index/?";
    private List<String> listImgs = new ArrayList<>();
    List<ReadingViewPagerBean.DataBean> data;

    private List<ReadingList> fragmentList = new ArrayList<>();
    private List<ReadingListViewBean.DataBean.EssayBean> essay;
    private List<ReadingListViewBean.DataBean.SerialBean> serial;
    private List<ReadingListViewBean.DataBean.QuestionBean> question;

    private FragmentManager fragmentManager;
    private RelativeLayout container;

//    public ReadFragment() {
//        // Required empty public constructor
//    }

    public static ReadFragment newInstance(){
        ReadFragment fragment = new ReadFragment();
        return fragment;
    }

    LinearLayout layout_point_container;
    Handler handler =new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            //接收消息加载下一页
            if (msg!=null){
               if(msg.what==1){
                    //先取出当前页的位置
                    int pos = viewPager.getCurrentItem();
                    viewPager.setCurrentItem((pos+1)% listImgs.size(),false);
                }
            }
        }
    };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_main_read, container, false);

        findView(view);
        initData();
        return view ;
    }

    private void findView(View view) {
        viewPager = (ViewPager) view.findViewById(R.id.reading_top_view_pager);
        viewPager2 = (ViewPager) view.findViewById(R.id.reading_bottom_view_pager);
        layout_point_container = (LinearLayout) view.findViewById(R.id.ponit_container);
        fragmentManager = getFragmentManager();
    }

    private void initData() {

        for (int i = 0; i < 10; i++) {
            ReadingList fragment = ReadingList.newInstance();
            fragmentList.add(fragment);

        }

        HttpUtils httpUtils = HttpUtils.getHttpUtils();
        httpUtils.setDataCallback(this);
        httpUtils.getStrByNetWork(0,0,READING_AD_PATH,null,null);
        httpUtils.getStrByNetWork(1,0,READING_CONTENT_PATH,null,null);
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                String json = HttpUtils.getStringByNetWork(READING_AD_PATH);
//                Message message = new Message();
//                message.what = 0;
//                message.obj = json;
//                handler.sendMessage(message);
//
//
//
//            }
//        }).start();
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                String json2 = HttpUtils.getStringByNetWork(READING_CONTENT_PATH);
//                Message message2 = new Message();
//                message2.what =2;
//                message2.obj = json2;
//                handler.sendMessage(message2);
//            }
//        }).start();
    }

    public List<View> initAd(){

        List<View> viewList = new ArrayList<>();

        for (int i = 0; i < listImgs.size(); i++) {
            //显示图片
            ImageView img = new ImageView(getActivity());
            //下载图片并显示
            Picasso.with(getActivity()).load(listImgs.get(i))
                    .placeholder(R.mipmap.ic_launcher)
                    .into(img);

            img.setScaleType(ImageView.ScaleType.CENTER_CROP);
            viewList.add(img);

            //显示圆点
            ImageView img_point = new ImageView(getActivity());
            //设置每个imageView的id
            img_point.setId(i);
            //img_point.setOnClickListener(this);
            //设置圆点之间的距离
            img_point.setPadding(10, 0, 10, 0);
            //设置圆点内容显示
            //默认选中第一个
            if (i == 0) {
                //选中
                img_point.setImageResource(R.drawable.reading_view_pager_point_select);
            } else {
                //不选中
                img_point.setImageResource(R.drawable.reading_view_pager_point_default);
            }
            //添加圆点到容器中并显示
            layout_point_container.addView(img_point);

        }
        return  viewList;

    }

    @Override
    public void setDataCallback(int id, int type, String json) {
        if (id==0&&json!=null){

            Gson gson = new Gson();
            ReadingViewPagerBean viewPagerBean = gson.fromJson(json, ReadingViewPagerBean.class);
             data = viewPagerBean.getData();
            for (int i = 0; i < data.size(); i++) {
                String cover = data.get(i).getCover();

                listImgs.add(cover);

            }

            //initAd初始化广告数据
            List<View> viewList = initAd();
            ReadingADAdapter myAdapter = new ReadingADAdapter(viewList);
            myAdapter.setOnAdClickCallback(this);
            //填充页面
            viewPager.setAdapter(myAdapter);
            new Thread(new Runnable() {
                @Override
                public void run() {
                    while (true){
                        try {
                            Thread.sleep(4000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        //发消息显示下一页
                        handler.sendEmptyMessage(1);
                    }

                }
            }).start();

            viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                @Override
                public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                    //页面滚动过程

                }

                @Override
                public void onPageSelected(int position) {

                    //选中那个页面
                    int count = layout_point_container.getChildCount();
                    for (int i = 0; i < count; i++) {
                        ImageView img = (ImageView) layout_point_container.getChildAt(i);
                        if (i == position) {
                            //表示选中
                            img.setImageResource(R.drawable.reading_view_pager_point_select);
                        } else {
                            //未选中
                            img.setImageResource(R.drawable.reading_view_pager_point_default);
                        }

                    }

                }

                @Override
                public void onPageScrollStateChanged(int state) {
                    //表示滚动状态

                }
            });


        }else if(json!=null&&id==1){

            Gson gson = new Gson();
            ReadingListViewBean listViewBean = gson.fromJson(json, ReadingListViewBean.class);
            essay = listViewBean.getData().getEssay();
            serial = listViewBean.getData().getSerial();
            question = listViewBean.getData().getQuestion();
//                    try {
//                        JSONObject jsonObject = new JSONObject(json);
//                        JSONObject data = jsonObject.getJSONObject("data");
//                        JSONArray essay = data.getJSONArray("essay");

            MyPagerAdapter myPagerAdapter = new MyPagerAdapter(fragmentManager, fragmentList,json);
            viewPager2.setAdapter(myPagerAdapter);
            myPagerAdapter.notifyDataSetChanged();
//                    } catch (JSONException e) {
//                        e.printStackTrace();
//                    }

        }
    }

    @Override
    public void setAdClickCallback(int position) {
        String id = data.get(position).getId();

        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.setCustomAnimations(R.anim.reading_ad_detail_enter,R.anim.reading_ad_detail_exit,R.anim.reading_ad_detail_enter,R.anim.reading_ad_detail_exit);
        transaction.add(R.id.reading_container,new ReadingAdDetail());
        transaction.addToBackStack(null);
        transaction.commit();


//        Intent intent = new Intent(getActivity(), ReadingADDetailActivity.class);
//        intent.putExtra("id",id);
//        startActivity(intent);
//        getActivity().overridePendingTransition(R.anim.reading_ad_detail_enter,R.anim.reading_ad_old_activity_out);


    }


    class MyPagerAdapter extends FragmentPagerAdapter {

        //数据集合
        List<ReadingList> fragmentList;
        String json = "";

        public MyPagerAdapter(FragmentManager fm, List<ReadingList> fragmentList,String json) {
            super(fm);
            this.fragmentList = fragmentList;
        }
        //根据指定位置获取当前的fragment对象
        @Override
        public Fragment getItem(int position) {
            List<ReadingListItemBean> items = new ArrayList<>();
            ReadingList fragment =  fragmentList.get(position);

            ReadingListViewBean.DataBean.EssayBean essayBean = essay.get(position);
            String title = essayBean.getHp_title();
            List<ReadingListViewBean.DataBean.EssayBean.AuthorBean> author = essayBean.getAuthor();
            String name = author.get(0).getUser_name();
            String word = essayBean.getGuide_word();
            items.add(new ReadingListItemBean(title, name, word));
            ReadingListViewBean.DataBean.SerialBean serialBean = serial.get(position);
            String title2 = serialBean.getTitle();
            ReadingListViewBean.DataBean.SerialBean.AuthorBean author2 = serialBean.getAuthor();
            String name2 = author2.getUser_name();
            String word2 = serialBean.getExcerpt();
            items.add(new ReadingListItemBean(title2, name2, word2));
            ReadingListViewBean.DataBean.QuestionBean questionBean = question.get(position);
            String title3 = questionBean.getQuestion_title();
            String name3 = questionBean.getAnswer_title();
            String word3 = questionBean.getAnswer_content();
            items.add(new ReadingListItemBean(title3, name3, word3));
//            try {
//                JSONObject jsonObject = new JSONObject(json);
//                JSONObject data = jsonObject.getJSONObject("data");
//                JSONArray essay = data.getJSONArray("essay");
//                JSONObject essayItem = (JSONObject) essay.get(position);
//                String title = essayItem.getString("Hp_title");
//                String word = essayItem.getString("guide_word");
//                String name = essayItem.getJSONArray("author").getJSONObject(0).getString("user_name");
//                items.add(new ReadingListItemBean(title,name,word));
//
//                JSONArray serial = data.getJSONArray("serial");
//                JSONObject serialItem = (JSONObject) serial.get(position);
//                String title2 = serialItem.getString("Hp_title");
//                String word2 = serialItem.getString("guide_word");
//                String name2 = serialItem.getJSONArray("author").getJSONObject(0).getString("user_name");
//                items.add(new ReadingListItemBean(title2,name2,word2));
//
//                JSONArray question = data.getJSONArray("question");
//                JSONObject questionItem = (JSONObject) question.get(position);
//                String title3 = questionItem.getString("Hp_title");
//                String word3 = questionItem.getString("guide_word");
//                String name3 = questionItem.getJSONArray("author").getJSONObject(0).getString("user_name");
//                items.add(new ReadingListItemBean(title3,name3,word3));
//
//            } catch (JSONException e) {
//                e.printStackTrace();
//            }

            fragment.setData(items);
            return fragment;
        }

        //要加载的fragment的个数
        @Override
        public int getCount() {
            return fragmentList.size();
        }
    }

//    @Override
//    public void onDestroyView() {
//        super.onDestroyView();
//
//    }
}
