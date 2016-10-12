package com.example.administrator.one.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.administrator.one.R;
import com.example.administrator.one.bean.ReadingListItemBean;
import com.example.administrator.one.bean.ReadingListViewBean;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class ReadingList extends Fragment {

    private List<ReadingListItemBean> listItems = new ArrayList<>();
    private ListView listView;
    View view;
    ListAdapter listAdapter;

    public static ReadingList newInstance() {
        ReadingList fragment = new ReadingList();
        return fragment;
    }
    public void setData(List<ReadingListItemBean> items){
        listItems = items;
//        listAdapter.notifyDataSetChanged();
       // initData();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_reading_list, container, false);
//        listView = (ListView) view.findViewById(R.id.reading_list_view);
        initData();
        return view;
    }

    private void initData() {
        listView = (ListView) view.findViewById(R.id.reading_list_view);
        listAdapter = new ListAdapter();
        listView.setAdapter(listAdapter);

    }

    class ListAdapter extends BaseAdapter{

        @Override
        public int getCount() {
            return listItems.size();
        }

        @Override
        public ReadingListItemBean getItem(int position) {
            return listItems.get(position);
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder viewHolder;
            if (convertView==null){
                convertView=LayoutInflater.from(getActivity()).inflate(R.layout.reading_list_item,parent,false);
                viewHolder = new ViewHolder(convertView);
            }else {
                viewHolder = (ViewHolder) convertView.getTag();
            }
            ReadingListItemBean itemBean = listItems.get(position);
            viewHolder.title.setText(itemBean.hp_title);
            viewHolder.name.setText(itemBean.user_name);
            viewHolder.word.setText(itemBean.guide_word);
            if(position==0){
                viewHolder.imageView.setImageResource(R.drawable.essay_image);
            }else if(position==1){
                viewHolder.imageView.setImageResource(R.drawable.serial_image);
            }else if(position==2){
                viewHolder.imageView.setImageResource(R.drawable.question_image);
            }

            return convertView;
        }
        class ViewHolder {
            TextView title;
            ImageView imageView;
            TextView name;
            TextView word;

            public  ViewHolder(View view) {
                 title = (TextView) view.findViewById(R.id.reading_list_item_hp_title);
                title.setTextSize(2,20);
                imageView = (ImageView) view.findViewById(R.id.reading_list_item_imageView);
                 name = (TextView) view.findViewById(R.id.reading_list_item_name);
                word = (TextView) view.findViewById(R.id.reading_list_item_guid_word);
                view.setTag(this);
            }
        }
    }
}
