package com.zongyou.library.util;

import android.app.Activity;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;

/**
 * @author Altas
 * @email Altas.Tutu@gmail.com
 * @time Jan 5, 2015 5:20:23 PM
 */
public class UriUtils {
	public static String uri2FilePath(Activity activity, String uriSrc) {

		Uri uri = Uri.parse(uriSrc);
		String[] proj = { MediaStore.Images.Media.DATA };
		Cursor actualimagecursor = activity.managedQuery(uri, proj, null, null, null);
		int actual_image_column_index = actualimagecursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
		actualimagecursor.moveToFirst();

		return actualimagecursor.getString(actual_image_column_index);
	}

	public static Uri filePath2Uri(Activity activity, String path) {
		Cursor cursor = activity.managedQuery(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, null, null, null, MediaStore.Images.Media.DEFAULT_SORT_ORDER);
		cursor.moveToFirst();

		while (!cursor.isAfterLast()) {
			String data = cursor.getString(cursor.getColumnIndex(MediaStore.MediaColumns.DATA));
			if (path.equals(data)) {
				int ringtoneID = cursor.getInt(cursor.getColumnIndex(MediaStore.MediaColumns._ID));
				// mImageUri = Uri.withAppendedPath(mUri, ""
				// + ringtoneID);
				break;
			}
			cursor.moveToNext();
		}
		return null;
	}
}
