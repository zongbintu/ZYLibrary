package com.zongyou.library.volley.images;

import java.io.File;
import java.io.FileOutputStream;
import java.util.HashMap;
import java.util.Iterator;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.util.Log;

import com.android.volley.toolbox.ImageLoader.ImageCache;
import com.zongyou.library.util.LogUtils;
import com.zongyou.library.util.MD5Utils;

/**
 * 缓存图片 TODO 缓存策略定义，硬盘缓存大小
 * 
 * @author Altas
 * @email Altas.TuTu@gmail.com
 * @date 2014年10月16日
 */
public class LruCache implements ImageCache {

	public static final String TAG = LruCache.class.getSimpleName();

	/** 单例 */
	private static LruCache cache;
	/** 内存缓存 */
	private HashMap<String, Bitmap> memory;
	/** 缓存目录 */
	private String cacheDir;

	/** 获取单例 */
	public static LruCache getInstance(Context context) {
		if (null == cache) {
			cache = new LruCache(context);
		}
		return cache;
	}

	private LruCache(Context context) {
		memory = new HashMap<String, Bitmap>();
		cacheDir = context.getCacheDir().toString() + File.separator;
	}

	@Override
	public Bitmap getBitmap(String url) {
		// 获取图片
		try {
			String key = MD5Utils.md5(url);
			if (cache.memory.containsKey(key)) {
				return cache.memory.get(key);
			} else {
				File file = new File(cache.cacheDir + key);
				if (file.exists()) {
					Bitmap bitmap = BitmapFactory.decodeFile(file.toString());
					return bitmap;
				}
			}
		} catch (Exception e) {
			Log.d("halfman", e.getMessage());
		}
		return null;
	}

	@Override
	public void putBitmap(String url, Bitmap bitmap) {
		// 尺寸超过10时，清理缓存并放入内存
		if (cache.memory.size() == 10) {
			Iterator<String> it = cache.memory.keySet().iterator();
			while (it.hasNext()) {
				try {
					String key = it.next();
					Bitmap temp = cache.memory.get(key);
					File file = new File(cache.cacheDir + key);
					FileOutputStream os;
					os = new FileOutputStream(file, false);
//					if(bitmap.hasAlpha())
					temp.compress(CompressFormat.PNG, 100, os);
//					else
//						temp.compress(CompressFormat.JPEG, 100, os);
					os.flush();
					os.close();
				} catch (Exception e) {
					LogUtils.e(TAG, e);
				}
			}
			cache.memory.clear();
		}
		// 放入图片到内存
		cache.memory.put(MD5Utils.md5(url), bitmap);
	}
}