package com.example.administrator.one.cache;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.FileOutputStream;

/**
 * Created by bluesky on 16/9/20.
 */
public class DiskCacheUtils {

    //把图片写入到本地
    public static  void putBmpToDisk(Bitmap bmp, String filePath){
        try {
            FileOutputStream outputStream =new FileOutputStream(filePath);
            //把图片压缩到outputStream
            bmp.compress(Bitmap.CompressFormat.PNG,100,outputStream);
            //把缓冲区所有的写入到outputStream
            outputStream.flush();
            //流不关闭容器造成内存泄漏，内存泄漏容易造成内存溢出
            outputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //从本地文件中查找图片
    public static Bitmap getBmpFromDisk(String filePath){
        Bitmap bmp = BitmapFactory.decodeFile(filePath);
        return  bmp;
    }
}
