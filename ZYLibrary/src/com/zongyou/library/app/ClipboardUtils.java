package com.zongyou.library.app;

import android.content.ClipData;
import android.content.Context;
import android.text.ClipboardManager;
import android.util.Log;

import static android.os.Build.VERSION.SDK_INT;
import static android.os.Build.VERSION_CODES.HONEYCOMB;

/**
 * 剪切板工具类
 * 
 * @author Altas
 * @email Altas.Tutu@gmail.com
 * @time 2015-1-13 下午5:58:02
 */
public class ClipboardUtils {
	private static ClipboardManager mClipboardManager;
	private static android.content.ClipboardManager mNewCliboardManager;

	private static void instance(Context context) {
		if (SDK_INT >= HONEYCOMB) {
			if (mNewCliboardManager == null)
				mNewCliboardManager = (android.content.ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
		} else {
			if (mClipboardManager == null)
				mClipboardManager = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
		}
	}

	/**
	 * 为剪切板设置内容
	 * 
	 * @param context
	 * @param text
	 */
	public static void setText(Context context, CharSequence text) {
		if (SDK_INT >= HONEYCOMB) {
			instance(context);
			// Creates a new text clip to put on the clipboard
			ClipData clip = ClipData.newPlainText("simple text", text);

			// Set the clipboard's primary clip.
			mNewCliboardManager.setPrimaryClip(clip);
		} else {
			instance(context);
			mClipboardManager.setText(text);
		}
	}

	/**
	 * 获取剪切板的内容
	 * 
	 * @param context
	 * @return
	 */
	public static CharSequence getText(Context context) {
		StringBuilder sb = new StringBuilder();
		if (SDK_INT >= HONEYCOMB) {
			instance(context);
			if (!mNewCliboardManager.hasPrimaryClip()) {
				Log.d("ClipboardManager", "Clipboard is empty");
				return sb.toString();
			} else {
				ClipData clipData = (mNewCliboardManager).getPrimaryClip();
				int count = clipData.getItemCount();

				for (int i = 0; i < count; ++i) {

					ClipData.Item item = clipData.getItemAt(i);
					CharSequence str = item.coerceToText(context);
					Log.i("mengdd", "item : " + i + ": " + str);
					sb.append(str);
				}
			}

		} else {
			instance(context);
			sb.append(mClipboardManager.getText());
		}
		return sb.toString();
	}
}
