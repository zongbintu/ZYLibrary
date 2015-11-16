package com.zongyou.library.app.upgrade;

import java.io.File;

import android.app.DownloadManager;
import android.content.Context;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;

/**
 * 版本升级
 * 
 * @author Altas
 * @email Altas.Tutu@gmail.com
 * @time 2015-3-2 下午2:55:04
 */
public class UpgradeManager {
	/**
	 * 版本升级，需要注册DownloadCompleteReceiver registerReceiver(receiver, new
	 * IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE));
	 * 
	 * @param context
	 * @param uri
	 * @param title
	 * @param description
	 */
	public static long updgrade(Context context, Uri uri, String title, String description) {
		DownloadManager downloadManager = (DownloadManager) context.getSystemService(Context.DOWNLOAD_SERVICE);
		DownloadManager.Request request = new DownloadManager.Request(uri);
		request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, uri.toString().substring(uri.toString().lastIndexOf("/") + 1));
		File folder = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
		if (!folder.exists() || !folder.isDirectory()) {
			folder.mkdirs();
		}
		request.setTitle(title);
		request.setDescription(description);
		if (Build.VERSION_CODES.HONEYCOMB <= Build.VERSION.SDK_INT)
			request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
		request.setMimeType("application/vnd.android.package-archive");
		return downloadManager.enqueue(request);
	}

}
