package com.zongyou.library.util.storage;

import java.io.File;

import com.zongyou.library.util.FileUtils;

import android.content.Context;
import android.os.Build;
import android.os.Environment;

/**
 * 存储工具类
 * 
 * @author Altas
 * @email Altas.TuTu@gmail.com
 * @date 2014年9月25日
 */
public class StorageUtils {
	// 内部存储
	public static final int MODE_INTERNAL = 0;
	// 外部存储
	public static final int MODE_EXTERNAL = 1;

	/**
	 * 缓存对象 将对象写成对象文件到应用缓存中
	 * 
	 * @param context
	 * @param dataObject
	 * @param mode
	 *            MODE_INTERNAL MODE_EXTERNAL
	 * @return
	 */
	public static boolean storageCacheObject(Context context,
			Object dataObject, int mode) {
		StringBuilder fileName = new StringBuilder();
		final String cacheFileName = dataObject.getClass().getSimpleName();
		switch (mode) {
		case MODE_INTERNAL:
			fileName.append(context.getCacheDir().getPath());
			break;
		case MODE_EXTERNAL:
			if (!android.os.Environment.getExternalStorageState().equals(
					android.os.Environment.MEDIA_MOUNTED)) {
				return false;
			}
			if (Build.VERSION.SDK_INT < Build.VERSION_CODES.FROYO)
				fileName.append(
						Environment.getExternalStorageDirectory().getPath())
						.append("/Android/data/")
						.append(context.getApplicationContext()
								.getPackageName()).append("/cache");
			else
				/*
				 * if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT)
				 * fileName.append( context.getExternalCacheDir().getPath());
				 * else
				 */
				// files目录
				fileName.append(context.getExternalFilesDir(null));
			break;
		}
		fileName.append("/").append(cacheFileName);
		return FileUtils.writeObjectFile(new File(fileName.toString()),
				dataObject);
	}

	/**
	 * 获取缓存文件目录
	 * 
	 * @param context
	 * @param mode
	 * @return
	 */
	public static String storageCacheDir(Context context, int mode) {
		StringBuilder fileName = new StringBuilder();
		switch (mode) {
		case MODE_INTERNAL:
			fileName.append(context.getCacheDir().getPath());
			break;
		case MODE_EXTERNAL:
			if (!android.os.Environment.getExternalStorageState().equals(
					android.os.Environment.MEDIA_MOUNTED)) {
				return null;
			}
			if (Build.VERSION.SDK_INT < Build.VERSION_CODES.FROYO)
				fileName.append(
						Environment.getExternalStorageDirectory().getPath())
						.append("/Android/data/")
						.append(context.getApplicationContext()
								.getPackageName()).append("/cache");
			else
				// files目录
				fileName.append(context.getExternalFilesDir(null));
			break;
		}
		return fileName.append("/").toString();
	}
}
