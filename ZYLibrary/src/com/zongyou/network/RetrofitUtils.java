package com.zongyou.network;

import android.content.Context;

import com.squareup.okhttp.Interceptor;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.zongyou.library.util.LogUtils;
import com.zongyou.library.util.storage.PreferenceUtils;

import java.io.IOException;
import java.lang.reflect.Field;

import retrofit.GsonConverterFactory;
import retrofit.Retrofit;

/**
 * Created by Tu on 15/8/11.
 */
public class RetrofitUtils {
    private static Retrofit singleton;
    private static Class sClazz;

    public static <T> T createApi(Context context, Class<T> clazz) {
        if (singleton == null || !clazz.equals(sClazz)) {
            synchronized (RetrofitUtils.class) {
                if (singleton == null || !clazz.equals(sClazz)) {

                    try {
                        Field api = clazz.getField("API");
                        OkHttpClient httpClient = new OkHttpClient();
                        httpClient.interceptors().add(new LoggingInterceptor());
                        final String token = PreferenceUtils.getValue(context, "logintoken", "");
                        httpClient.networkInterceptors().add(new Interceptor() {
                            @Override
                            public com.squareup.okhttp.Response intercept(Chain chain) throws IOException {

                                Request request = chain.request().newBuilder().addHeader("Authorization", token).addHeader("Content-Type", "application/x-www-form-urlencoded").addHeader("User-Agent", "Android").build();
                                return chain.proceed(request);
                            }
                        });
                        singleton = new Retrofit.Builder().baseUrl((String) api.get(clazz)).addConverterFactory(GsonConverterFactory.create()).client(httpClient).build();
                    } catch (NoSuchFieldException e) {
                        LogUtils.e("api url error", e);
                    } catch (IllegalAccessException e) {
                        LogUtils.e("api url error", e);
                    }
                    sClazz = clazz;
                }
            }
        }
        return singleton.create(clazz);
    }
}
