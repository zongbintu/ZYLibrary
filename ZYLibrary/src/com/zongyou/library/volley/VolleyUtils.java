package com.zongyou.library.volley;

import android.content.Context;
import android.text.TextUtils;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request.Method;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;

import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

/**
 * Volley工具类
 *
 * @author Altas
 * @email Altas.TuTu@gmail.com
 * @date 2014年10月13日
 */
public class VolleyUtils {
	private static final String CONNECTOR = "&";
	private static final String EQUAL = "=";
	private static final String QUESTION = "?";

	private VolleyUtils() {
	}

	/**
	 * 封装Get请求， 由于Volley在Get中，直接采用url，不处理参数，在这里封装处理，避免外部手动组合
	 *
	 * @param url
	 * @param params        参数
	 * @param clazz         结果实体类型
	 * @param listener
	 * @param errorListener
	 */
	public static <T> void get(String url, Map<String, String> params,
							   final Class<T> clazz, Listener<T> listener,
							   ErrorListener errorListener) {
		ObjectRequest<T> objectRequest = new ObjectRequest<T>(Method.GET, getUrl(url, params), clazz,
				listener, errorListener);
		objectRequest.setRetryPolicy(new DefaultRetryPolicy(600000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
		RequestManager.getRequestQueue().add(objectRequest);

	}

	/**
	 * 封装Post请求， 由于Volley要求参数类型为String，在这里封装处理，避免外部手动组合
	 *
	 * @param url
	 * @param params
	 * @param clazz
	 * @param listener
	 * @param errorListener
	 */
	public static <T> void post(String url, final Map<String, String> params,
								final Class<T> clazz, Listener<T> listener,
								ErrorListener errorListener) {
		ObjectRequest<T> objectRequest =
				new ObjectRequest<T>(Method.POST, url, clazz, listener,
						errorListener) {

					@Override
					protected Map<String, String> getParams()
							throws AuthFailureError {
						return params;
					}
				};
		objectRequest.setRetryPolicy(new DefaultRetryPolicy(600000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
		RequestManager.getRequestQueue().add(objectRequest);

	}

	public static <T> void post(Context context, String url, final Map<String, String> params,
								final Class<T> clazz, Listener<T> listener,
								ErrorListener errorListener, boolean isPass) {
		ObjectRequest<T> objectRequest =
				new ObjectRequest<T>(context, Method.POST, url, clazz, listener,
						errorListener, isPass) {

					@Override
					protected Map<String, String> getParams()
							throws AuthFailureError {
						return params;
					}
				};
		objectRequest.setRetryPolicy(new DefaultRetryPolicy(600000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
		RequestManager.getRequestQueue().add(objectRequest);
	}

	/**
	 * 封装Post请求， 由于Volley要求参数类型为String，在这里封装处理，避免外部手动组合
	 *
	 * @param url
	 * @param params
	 * @param clazz
	 * @param listener
	 * @param errorListener
	 */
	public static <T> void post(String url, final Map<String, String> params,
								final Class<T> clazz, Listener<T> listener,
								ErrorListener errorListener, T t) {
		ObjectRequest<T> objectRequest =
				new ObjectRequest<T>(Method.POST, url, clazz, listener,
						errorListener, t) {

					@Override
					protected Map<String, String> getParams()
							throws AuthFailureError {
						return params;
					}
				};
		objectRequest.setRetryPolicy(new DefaultRetryPolicy(600000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
		RequestManager.getRequestQueue().add(objectRequest);
	}

	public static <T> void post(Context context, String url, final Map<String, String> params,
								final Class<T> clazz, Listener<T> listener,
								ErrorListener errorListener, T t, boolean isPass) {
		ObjectRequest<T> objectRequest =
				new ObjectRequest<T>(context, Method.POST, url, clazz, listener,
						errorListener, t) {

					@Override
					protected Map<String, String> getParams()
							throws AuthFailureError {
						return params;
					}
				};
		objectRequest.setRetryPolicy(new DefaultRetryPolicy(600000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
		RequestManager.getRequestQueue().add(objectRequest);
	}

	/**
	 * 组合参数
	 *
	 * @param params
	 * @return
	 */
	private static String getRequestBody(Map<String, String> params) {
		if (null == params || params.size() == 0)
			return null;
		else {
			Iterator<Entry<String, String>> iterator = params.entrySet()
					.iterator();
			StringBuilder bodyStringBuilder = new StringBuilder();
			while (iterator.hasNext()) {
				Entry<String, String> entry = iterator.next();
				if (0 != bodyStringBuilder.length()) {
					bodyStringBuilder.append(CONNECTOR);
				}
				bodyStringBuilder.append(entry.getKey()).append(EQUAL)
						.append(entry.getValue());
			}
			return bodyStringBuilder.toString();
		}
	}

	/**
	 * 组装url
	 *
	 * @param url
	 * @param params
	 * @return
	 */
	private static String getUrl(String url, Map<String, String> params) {
		final String pString = getRequestBody(params);
		return TextUtils.isEmpty(pString) ? url : url.concat(QUESTION).concat(
				pString);
	}
}
