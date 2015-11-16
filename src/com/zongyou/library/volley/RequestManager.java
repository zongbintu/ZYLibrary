package com.zongyou.library.volley;

import android.content.Context;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;
import com.zongyou.library.volley.images.LruCache;

/**
 * @author Altas
 * @email Altas.TuTu@gmail.com
 * @date 2014年10月16日
 */
public class RequestManager {
	/**
	 * the queue :-)
	 */
	private static RequestQueue mRequestQueue;
	private static ImageLoader mImageLoader;

	/**
	 * Nothing to see here.
	 */
	private RequestManager() {
		// no instances
	}

	/**
	 * @param context
	 *            application context
	 */
	public static void init(Context context) {
		mRequestQueue = Volley.newRequestQueue(context);
		mImageLoader = new ImageLoader(mRequestQueue,
				LruCache.getInstance(context));
	}

	/**
	 * @return instance of the queue
	 * @throws IllegalStatException
	 *             if init has not yet been called
	 */
	public static RequestQueue getRequestQueue() {
		if (mRequestQueue != null) {
			return mRequestQueue;
		} else {
			throw new IllegalStateException("Not initialized");
		}
	}

	/**
	 * @return instance of the imageloader
	 * @throws IllegalStatException
	 *             if init has not yet been called
	 */
	public static ImageLoader getImageLoader() {
		if (mImageLoader != null) {
			return mImageLoader;
		} else {
			throw new IllegalStateException("Not initialized");
		}
	}
}
