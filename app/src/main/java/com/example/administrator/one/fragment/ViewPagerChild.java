package com.example.administrator.one.fragment;


import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.PopupMenu;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.one.R;
import com.example.administrator.one.bean.HomeBean;
import com.example.administrator.one.callback.DataCallback;
import com.example.administrator.one.utils.BitmapUtils;
import com.example.administrator.one.utils.HttpUtils;
import com.google.gson.Gson;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * A simple {@link Fragment} subclass.
 */
public class ViewPagerChild extends Fragment implements DataCallback {

    private String headPath = "http://v3.wufazhuce.com:8000/api/hp/detail/";
    private String index;
    private HomeBean homeBean;
    private View view;
    ImageView imageView1;
    private String LocalPath;
    String fileName;


    ImageView imageView;
    TextView hp_title;
    TextView hp_author;
    TextView hp_content;
    TextView time;
    TextView praise;
    String imagePath;


//    public ViewPagerChild() {
//        // Required empty public constructor
//    }

    public static ViewPagerChild newInstance() {
        ViewPagerChild fragment = new ViewPagerChild();

        return fragment;
    }

//    public void setData(String num) {
//        this.index = num;
//
//    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        index= bundle.getString("id");


    }

//    private Handler handler = new Handler() {
//
//        @Override
//        public void handleMessage(Message msg) {
//            super.handleMessage(msg);
//            if (msg.what == 0) {
//                String json = (String) msg.obj;
//                Gson gson = new Gson();
//                homeBean = gson.fromJson(json, HomeBean.class);
//
//                Picasso.with(getActivity()).load(homeBean.getData().getHp_img_url()).into(imageView);
//                hp_title.setText(homeBean.getData().getHp_title());
//                hp_author.setText(homeBean.getData().getHp_author());
//                hp_content.setText(homeBean.getData().getHp_content());
//                hp_title.setText(homeBean.getData().getHp_title());
//                time.setText(homeBean.getData().getHp_makettime());
//                praise.setText(String.valueOf(homeBean.getData().getPraisenum()));
//
//
//
//                imageView.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                       myDialog();
//
////                         AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
////                        View view =  LayoutInflater.from(getActivity()).inflate(R.layout.home_image_window,null);
////                        ImageView imageView2 = (ImageView) view.findViewById(R.id.imageView2);
//////                        imageView2.setTag(1);
////                        Picasso.with(getActivity()).load(homeBean.getData().getHp_img_url()).into(imageView2);
//////                        imageView2.setImageBitmap(BitmapFactory.decodeResource(get),imageView2.getId()));
////                        builder.setView(view);
////
////                        AlertDialog dialog = builder.create();
////                        dialog.show();
////
////                        Window win = dialog.getWindow();
//////                        win.getDecorView().setPadding(0, 0, 0, 0);
//////                        WindowManager.LayoutParams lp = win.getAttributes();
//////                        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
//////                        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
//////                        win.setAttributes(lp);
////
////                        win.setBackgroundDrawableResource(android.R.color.transparent);
////                        win.setWindowAnimations(R.style.home_image_window);
//
//
//                    }
//                });
//
//
//            }
//        }
//    };

    public void myDialog(){
        DisplayMetrics metrics = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(metrics);
        int w = metrics.widthPixels;
        int h = metrics.heightPixels;
        Log.e("=====","===w==="+w);
        Log.e("=====","===h==="+h);
         AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        imageView1 = new ImageView(getActivity());
//        imageView1.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,300));
        imageView1.setBackgroundColor(Color.BLUE);
        imageView1.setScaleType(ImageView.ScaleType.CENTER_CROP);
//                        没有设置主题，直接new的ImageView弹出的对话框，是在和整个window的界面间隔的白色背景对话框后面中间是贴白框边的图片。
//                        设置window背景后，
//        Picasso.with(getActivity()).load(homeBean.getData().getHp_img_url()).into(imageView1);
        Log.e("=====","===myDialog===="+LocalPath);

        imageView1.setImageBitmap(BitmapFactory.decodeFile(LocalPath));
        registerForContextMenu(imageView1);
        builder.setView(imageView1);
        AlertDialog dialog = builder.create();


        dialog.show();

        Window window = dialog.getWindow();
        WindowManager.LayoutParams lp = window.getAttributes();
        lp.width = w;
        lp.height = h/3;
        window.setAttributes(lp);
        window.setGravity(Gravity.CENTER);
        window.setWindowAnimations(R.style.home_image_window);

        window.setBackgroundDrawableResource(android.R.color.transparent);
        window.getDecorView().setPadding(0, 0, 0, 0);



//        imageView1.setOnLongClickListener(new View.OnLongClickListener() {
//            @Override
//            public boolean onLongClick(View v) {
//
//                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
//                builder.setTitle("更多操作");
//                builder.setItems(new CharSequence[]{"保存到图库", "取消"}, new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        switch (which) {
//                            case 0:
//                                //保存文件到图库
//                                BitmapUtils.insertImgStore(getActivity(), LocalPath, fileName);
//                                Toast.makeText(getActivity(),"保存路径为："+LocalPath,Toast.LENGTH_SHORT).show();
//                                break;
//                            case 1:
//                                dialog.dismiss();
//                                break;
//                        }
//                    }
//                });
//                builder.create().show();
//                return true;
//            }
//        });

    }




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_view_pager_child, container, false);
        findView(view);
        initData();

        return view;
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        menu.setHeaderTitle("更多操作");
        menu.add(0,0, Menu.NONE,"保存图片到相册");
        menu.add(0,1,Menu.NONE,"取消");
        menu.setGroupEnabled(0,true);
        menu.getItem(0).setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                Log.e("=========","================");
                //保存到系统相机
                BitmapUtils.insertImgStore(getActivity(), LocalPath, fileName);
                Log.e("=====","===保存到系统相机===="+LocalPath);
                Toast.makeText(getActivity(),"保存路径为："+LocalPath,Toast.LENGTH_SHORT).show();
                return true;
            }
        });

    }


//    @Override
//    public boolean onContextItemSelected(MenuItem item) {
//        item.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
//            @Override
//            public boolean onMenuItemClick(MenuItem item) {
//                if(item.getItemId()==0){
//                    Log.e("=========","================");
//                    //保存到系统相机
//                    BitmapUtils.insertImgStore(getActivity(), LocalPath, fileName);
//                    Log.e("=====","===保存到系统相机===="+LocalPath);
//                    Toast.makeText(getActivity(),"保存路径为："+LocalPath,Toast.LENGTH_SHORT).show();
//                }
//                return true;
//            }
//        });
//
//        Log.e("=========","=======ddd=========");
//
//
//        return super.onContextItemSelected(item);
//    }

    public void findView(View view) {
        imageView = (ImageView) view.findViewById(R.id.home_view_imageView);
        hp_title = (TextView) view.findViewById(R.id.home_view_vol);
        hp_author = (TextView) view.findViewById(R.id.home_view_author);
        hp_content = (TextView) view.findViewById(R.id.home_view_content);
        time = (TextView) view.findViewById(R.id.home_view_time);
        praise = (TextView) view.findViewById(R.id.home_view_praise);

    }

    private void initData() {

        HttpUtils httpUtils = HttpUtils.getHttpUtils();
        httpUtils.getStrByNetWork(0,0,headPath + index,null,null);
        httpUtils.setDataCallback(this);

//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                String json = HttpUtils.getStringByNetWork(headPath + index);
////                Message message = new Message();
////                message.what = 0;
////                message.obj = json;
////                handler.sendMessage(message);
//                handler.obtainMessage(0,json).sendToTarget();//post
//            }
//        }).start();
    }

    @Override
    public void setDataCallback(int id,int type, String json) {
        Gson gson = new Gson();
        homeBean = gson.fromJson(json, HomeBean.class);
        imagePath = homeBean.getData().getHp_img_url();
        fileName = BitmapUtils.getFileName(imagePath);
        Picasso.with(getActivity()).load(imagePath).transform(new Transformation() {


            @Override
            public Bitmap transform(Bitmap source) {
                //保存到本地磁盘
                String path = BitmapUtils.saveImageToSDCard(source, fileName);
                //读取图片，并进行二次采样
                Bitmap bmp = BitmapUtils.getBitmapByPath(path);
                LocalPath = BitmapUtils.saveImageToSDCard(bmp, fileName);
                Log.e("=====","===transform===="+LocalPath);
                if(bmp!=source){
                    source.recycle();
                    source=null;
                }
                return bmp;
            }

            @Override
            public String key() {
                return imagePath;
            }
        }).config(Bitmap.Config.RGB_565).memoryPolicy(MemoryPolicy.NO_STORE).into(imageView);

        hp_title.setText(homeBean.getData().getHp_title());
        hp_author.setText(homeBean.getData().getHp_author());
        hp_content.setText(homeBean.getData().getHp_content());
        hp_title.setText(homeBean.getData().getHp_title());
        time.setText(homeBean.getData().getHp_makettime());
        praise.setText(String.valueOf(homeBean.getData().getPraisenum()));



        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myDialog();

//                DisplayMetrics metrics = new DisplayMetrics();
//                getActivity().getWindowManager().getDefaultDisplay().getMetrics(metrics);
//                int w = metrics.widthPixels;
//                int h = metrics.heightPixels;
//
//                         AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
//                        View view =  LayoutInflater.from(getActivity()).inflate(R.layout.home_image_window,null);
//                        ImageView imageView = (ImageView) view.findViewById(R.id.imageView);
//                imageView.setBackgroundColor(Color.BLUE);
//                imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
////                        imageView2.setTag(1);
////                        Picasso.with(getActivity()).load(homeBean.getData().getHp_img_url()).into(imageView2);
////                        imageView2.setImageBitmap(BitmapFactory.decodeResource(get),imageView2.getId()));
//                        imageView.setImageBitmap(BitmapFactory.decodeFile(LocalPath));
//                        builder.setView(view);
//
//                        AlertDialog dialog = builder.create();
//
//                        dialog.show();
//
//                        Window win = dialog.getWindow();
//                        win.setBackgroundDrawableResource(android.R.color.holo_red_dark);
//                        win.getDecorView().setPadding(0, 0, 0, 0);
//                        WindowManager.LayoutParams lp = win.getAttributes();
////                        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
////                        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
//                lp.width=w;
//                lp.height=h;
//                        win.setAttributes(lp);
//
//                        win.setBackgroundDrawableResource(android.R.color.transparent);
//                        win.setWindowAnimations(R.style.home_image_window);


            }
        });
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
       unregisterForContextMenu(imageView1);
    }
}
