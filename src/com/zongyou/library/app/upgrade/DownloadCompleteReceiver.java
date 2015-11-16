package com.zongyou.library.app.upgrade;

import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;

import com.zongyou.library.app.IntentUtils;

/**
 * DownloadCompleteReceiver
 * @author Altas
 * @email Altas.Tutu@gmail.com
 * @time 2015-4-13 下午3:13:09
 */
public class DownloadCompleteReceiver extends BroadcastReceiver {
	private long mDownloadId;
	public DownloadCompleteReceiver(long downloadId){
		this.mDownloadId=downloadId;
	}
	@Override
	public void onReceive(Context context, Intent intent) {
		if (intent.getAction().equals(DownloadManager.ACTION_DOWNLOAD_COMPLETE)) {
			if (mDownloadId == intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, -1)) {
				DownloadManager mDownloadManager = (DownloadManager) context.getSystemService(Context.DOWNLOAD_SERVICE);
				DownloadManager.Query query = new DownloadManager.Query();
				query.setFilterById(mDownloadId);
				Cursor cursor = mDownloadManager.query(query);
				if (cursor.moveToFirst()) {
					int columnIndex = cursor.getColumnIndex(DownloadManager.COLUMN_STATUS);
					if (DownloadManager.STATUS_SUCCESSFUL == cursor.getInt(columnIndex)) {
						final String filename = cursor.getString(cursor.getColumnIndex(DownloadManager.COLUMN_LOCAL_URI));
						IntentUtils.installAPK(context.getApplicationContext(), Uri.parse(filename).getPath());
					}
				}
			}
		}
	}
}
