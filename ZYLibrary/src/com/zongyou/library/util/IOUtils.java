package com.zongyou.library.util;

import android.database.Cursor;
import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * IO工具类
 * 
 * @author Altas
 * @email Altas.TuTu@gmail.com
 * @date 2014年9月25日
 */
public class IOUtils {
	private static final int BUFFER_SIZE = 32768;

	public static void copyStream(InputStream is, OutputStream os)
			throws IOException {
		byte[] bytes = new byte[BUFFER_SIZE];
		while (true) {
			int count = is.read(bytes, 0, BUFFER_SIZE);
			if (count == -1) {
				break;
			}
			os.write(bytes, 0, count);
		}
	}

	public static void closeSilently(Closeable closeable) {
		try {
			closeable.close();
		} catch (Exception localException) {
		}
	}

	public static void closeSilently(Cursor cursor) {
		if (cursor != null)
			try {
				cursor.close();
			} catch (Throwable localThrowable) {
			}
	}
}