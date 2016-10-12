package com.example.administrator.one.cache;

import android.graphics.Bitmap;
import android.support.v4.util.LruCache;
import android.util.Log;

/**
 * Created by bluesky on 16/9/20.
 * 备注：一般情况下一个程序中只有一个缓存类的的对象
 * 一级内存存储LruCache
 *
 * lruCache:
 *
 * 安卓系统给每个程序分配的空间一般为64M;
 * 获取运行内存大小
 *
 *
 * 源代码中查看get(),put(),trimToSize()
 */
public class LruCacheUtils {
    public static LruCacheUtils lruCacheUtils;

    //声明内存缓存类的对象
    LruCache<String, Bitmap> lruCache;

    //单例模式
    public static LruCacheUtils getLruCacheUtils() {
        if (lruCacheUtils == null) {
            lruCacheUtils = new LruCacheUtils();
        }

        return lruCacheUtils;
    }
    //在无参构造方法中创建内存缓存类的对象
    public LruCacheUtils() {
        //系统分配的运行最大内存的1/8用来存储图片
        int maxSize = (int) Runtime.getRuntime().maxMemory()/8;
        Log.e("=====","====maxMemory==="+Runtime.getRuntime().maxMemory());
        //重写sizeOf()方法的目的是计算每个图的大小进行累加，最终累加的大小和maxSize进行比较，
        //       如果超过maxSize,执行lru算法回收不需要的数据；
        lruCache = new LruCache<String, Bitmap>(maxSize){
            @Override
            protected int sizeOf(String key, Bitmap value) {
                //计算每个图片的大小(总的字节数)
                //通过计算每一行的字节数乘以它的行数就是总的字节数:value.getRowBytes()*value.getHeight();
                return value.getByteCount();
            }
        };
    }


    //存放到内存缓存中
    public void put(String key ,Bitmap bmp){
        Log.e("=====","====put===");
        lruCache.put(key,bmp);

    }

    //从内存缓存查找数据
    public Bitmap get(String key){
        Log.e("=====","====get===");
        return  lruCache.get(key);

    }
}
