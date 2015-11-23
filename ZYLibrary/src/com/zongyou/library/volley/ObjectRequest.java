/*
 * Copyright (C) 2011 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.zongyou.library.volley;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.toolbox.HttpHeaderParser;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.zongyou.library.util.LogUtils;
import com.zongyou.library.util.base64.AESUtils;
import com.zongyou.library.util.base64.EnResult;
import com.zongyou.library.util.json.JSONHelper;
import com.zongyou.library.util.storage.PreferenceUtils;


import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;

/**
 * A request for retrieving a T type response body at a given URL that also
 * optionally sends along a JSON body in the request specified.
 *
 * @param <T> Model
 */
public class ObjectRequest<T> extends Request<T> {

	public static final String TAG = ObjectRequest.class.getSimpleName();

	private final Listener<T> mListener;
	private Class<T> mClazz;
	private T mErrorT;
	private boolean isPass;


	/**
	 * Deprecated constructor for a JsonRequest which defaults to GET unless
	 * {@link #getPostBody()} or {@link #getPostParams()} is overridden (which
	 * defaults to POST).
	 */
	public ObjectRequest(String url, String requestBody, Listener<T> listener, ErrorListener errorListener) {
		this(Method.DEPRECATED_GET_OR_POST, url, listener, errorListener);
	}

	private ObjectRequest(int method, String url, Listener<T> listener, ErrorListener errorListener) {
		super(method, url, errorListener);
		mListener = listener;
	}

	/**
	 * constructor
	 *
	 * @param method
	 * @param url
	 * @param clazz
	 * @param listener
	 * @param errorListener
	 */
	public ObjectRequest(int method, String url, Class<T> clazz, Listener<T> listener, ErrorListener errorListener) {
		super(method, url, errorListener);
		mListener = listener;
		mClazz = clazz;
	}

	private Context mContext;

	public ObjectRequest(Context context, int method, String url, Class<T> clazz, Listener<T> listener, ErrorListener errorListener, boolean isPass) {
		super(method, url, errorListener);
		mListener = listener;
		mClazz = clazz;
		mContext = context;
		this.isPass = isPass;
	}

	/**
	 * constructor
	 *
	 * @param method
	 * @param url
	 * @param clazz
	 * @param listener
	 * @param errorListener
	 */
	public ObjectRequest(Context context, int method, String url, Class<T> clazz, Listener<T> listener, ErrorListener errorListener, T errorT) {
		super(method, url, errorListener);
		mListener = listener;
		mClazz = clazz;
		mErrorT = errorT;
		mContext = context;
	}

	public ObjectRequest(int method, String url, Class<T> clazz, Listener<T> listener, ErrorListener errorListener, T errorT) {
		super(method, url, errorListener);
		mListener = listener;
		mClazz = clazz;
		mErrorT = errorT;
	}

	@Override
	protected void deliverResponse(T response) {
		mListener.onResponse(response);
	}

	@Override
	protected Response<T> parseNetworkResponse(NetworkResponse response) {
		try {
			// 将结果转换为T
			final String data = new String(response.data, HttpHeaderParser.parseCharset(response.headers));

            Log.e("parseNetworkResponse", data);
            Gson gson = new GsonBuilder().create();
            EnResult result = gson.fromJson(data, EnResult.class);
//					JSONHelper.parseObject(data, EnResult.class);
            T newResult;

            if (isPass) {
                // TODO 解密
                if (result == null) {
                    result = (EnResult) mErrorT;
                }
                if (TextUtils.isEmpty(result.data)) {
                    newResult = gson.fromJson(data, mClazz);
                } else {
                    String token = result.token;
                    if (TextUtils.isEmpty(token)) {
                        token = PreferenceUtils.getValue(mContext, "logintoken", "");
                    } else
                        PreferenceUtils.setValue(mContext, "token", result.token);
                    if (TextUtils.isEmpty(token)) {
                        token = PreferenceUtils.getValue(mContext, "token", "");
                    }

					String key = PreferenceUtils.getValue(mContext, "key", "");
					if (!TextUtils.isEmpty(result.key)) {
						PreferenceUtils.setValue(mContext, "key", result.key);
						key = result.key;
					}

					int userId = result.userId;

					if (userId == 0) {
						if (!this.getUrl().contains("app.init")) {
							userId = Integer.valueOf(PreferenceUtils.getValue(mContext, "userId", 0));
						}
					} else {
						PreferenceUtils.setValue(mContext, "userId", result.userId);
					}

                    AESUtils aesUtils = new AESUtils(token, key, userId);
                    String datas = aesUtils.decrypt(result.data);
                    LogUtils.e("data", datas);
                    if (null == mClazz || TextUtils.isEmpty(datas)) {
                        newResult = gson.fromJson(data, mClazz);
                        Response<T> responseT = Response.success(newResult, HttpHeaderParser.parseCacheHeaders(response));
                        return responseT;
                    }
                    // json parse mClass
                    result.data = null;
                    newResult = gson.fromJson(gson.toJson(result), mClazz);
                    Field f;
                    try {
                        f = mClazz.getField("data");
                        Class dataClazz = f.getType();
                        if (JSONHelper.isSingle(dataClazz)) {
                            f.set(newResult, datas);
                        } else if (JSONHelper.isCollection(dataClazz)) {
                            Class<?> c = null;
                            Type gType = f.getGenericType();
//							if (gType instanceof ParameterizedType) {
//								ParameterizedType ptype = (ParameterizedType) gType;
//								Type[] targs = ptype.getActualTypeArguments();
//								if (targs != null && targs.length > 0) {
//									Type t = targs[0];
//									c = (Class<?>) t;
//								}
//							}
                            f.set(newResult, gson.fromJson(datas, gType));
//							gson.fromJson(datas,gType);
//							f.set(newResult, JSONHelper.parseCollection(datas, ArrayList.class, c));
                        } else {
                            f.set(newResult, gson.fromJson(datas, dataClazz));
                        }

                    } catch (Exception e) {
                        LogUtils.e(e.getMessage());
                    }
                }
            } else {
                newResult = gson.fromJson(data, mClazz);
            }
            Response<T> responseT = Response.success(newResult, HttpHeaderParser.parseCacheHeaders(response));
            return responseT;
        } catch (UnsupportedEncodingException e) {
            LogUtils.e(TAG, e);
            return Response.error(new ParseError(e));
        } catch (Exception ex) {
            LogUtils.e(TAG, ex);
            return Response.error(new ParseError(ex));
        }
    }
}