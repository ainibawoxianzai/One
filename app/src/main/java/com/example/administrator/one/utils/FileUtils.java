package com.example.administrator.one.utils;

import android.content.Context;
import android.os.Environment;

import java.io.File;

/**
 * Created by bluesky on 16/9/20.
 */
public class FileUtils {

    //根据文件名称创建文件得到文件地址
    public static String getFilePath(Context context, String name) {

        File rootFile = null;
        //if判断sd卡是否存在
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            //外部存储：sd卡上的缓存目录，跟随包名，如果程序写在数据全部清除
            ///sd根目录/Android/data/<包名>／cache/xx
            rootFile = context.getExternalCacheDir();
        } else {
            //内部存储：/data/data/<包名>／cache/xx
            rootFile = context.getCacheDir();
        }

        //创建文件的目录
        File picFile = new File(rootFile, "test");
        if (!picFile.exists()) {
            picFile.mkdirs();
        }

        //创建指定目录下的文件
        File file = new File(picFile, name);
        return file.getAbsolutePath();
    }

    //根据指定地址得到文件名称
    public static String getFileName(String url) {
        String[] s = url.split("/");
        String name = s[s.length - 1];
        return name;
    }


}
