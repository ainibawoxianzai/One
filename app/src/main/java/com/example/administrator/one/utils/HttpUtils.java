package com.example.administrator.one.utils;

import android.app.Activity;
import android.os.Handler;

import com.example.administrator.one.callback.DataCallback;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Iterator;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Administrator on 2016/9/17.
 */
public class HttpUtils {

    public static HttpUtils httpUtils = null;
    DataCallback dataCallback;
    Handler handler =new Handler();
    public static HttpUtils getHttpUtils() {
        if (httpUtils == null) {
            httpUtils = new HttpUtils();
        }
        return httpUtils;
    }

    public void setDataCallback(DataCallback dataCallback) {
        this.dataCallback = dataCallback;
    }

    public void getStrByNetWork(final int id,final int type, String path, Map<String,String> mapBoby,Map<String,String> mapHeaders) {
        OkHttpClient client = new OkHttpClient();
        Request request = null;
        if(type==0) {
           request = new Request.Builder().url(path).build();
        }else if(type==1){
            //创建请求（请求方式）post提交请求体
            FormBody.Builder builder = new FormBody.Builder();

            //构造请求体
            Iterator<Map.Entry<String, String>> iterator = mapBoby.entrySet().iterator();
            while(iterator.hasNext()){
                Map.Entry<String, String> map = iterator.next();
                map.getKey();
                map.getValue();
                builder.add(map.getKey(),map.getValue());

            }
            FormBody body = builder.build();

            //构造请求
            Request.Builder requestBuilder = new Request.Builder();
            requestBuilder.url(path);
            Iterator<Map.Entry<String, String>> iterator1 = mapHeaders.entrySet().iterator();
            while(iterator1.hasNext()){
                Map.Entry<String, String> map = iterator1.next();
                map.getKey();
                map.getValue();
                requestBuilder.addHeader(map.getKey(),map.getValue());

            }
            requestBuilder.post(body);
            request = requestBuilder.build();

        }

        Call call = client.newCall(request);
        if (call.isExecuted()){
            call.cancel();
        }
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, final Response response) throws IOException {
                final  String str = response.body().string();
                if (dataCallback != null) {
                    handler.post(new Runnable() {
                        @Override
                        public void run() {

                            dataCallback.setDataCallback(id,type,str);
                        }
                    });


                }
            }
        });
    }

    public static String getStringByNetWork(String path) {
        try {
            URL url = new URL(path);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setConnectTimeout(5000);
            connection.setRequestMethod("GET");
            if (connection.getResponseCode() == 200) {
                InputStream inputStream = connection.getInputStream();
                int len = 0;
                byte[] buffer = new byte[1024];
                StringBuilder builder = new StringBuilder();
                while ((len = inputStream.read(buffer)) != -1) {
                    builder.append(new String(buffer,0,len));
                }
                return builder.toString();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;

    }

    public static InputStream getInputStreamByNetWork(String imgPath) {

        try {
            URL url = new URL(imgPath);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setConnectTimeout(5000);
            if (connection.getResponseCode() == 200) {
                return connection.getInputStream();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;

    }



}
