package com.zongyou.network;

import com.squareup.okhttp.Interceptor;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import com.squareup.okhttp.ResponseBody;
import com.zongyou.library.util.LogUtils;

import java.io.IOException;

import okio.Buffer;

/**
 * Retrofit 2.0 add log
 * Created by tu on 15/10/19.
 */
public class LoggingInterceptor  implements Interceptor {
    public static final String TAG = "HTTP_LOG";
    @Override public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        long t1 = System.nanoTime();
        String requestLog = String.format("Sending request %s on %s%n%s",
                request.url(), chain.connection(), request.headers());
        LogUtils.d(String.format("Sending request %s on %s%n%s",
                request.url(), chain.connection(), request.headers()));
        if(request.method().compareToIgnoreCase("post")==0){
            requestLog ="\n"+requestLog+"\n"+bodyToString(request);
        }
        LogUtils.d(TAG, "request" + "\n" + requestLog);

        Response response = chain.proceed(request);
        long t2 = System.nanoTime();

        String responseLog = String.format("Received response for %s in %.1fms%n%s",
                response.request().url(), (t2 - t1) / 1e6d, response.headers());

        String bodyString = response.body().string();

        LogUtils.d(TAG,"response"+"\n"+responseLog+"\n"+bodyString);

        return response.newBuilder()
                .body(ResponseBody.create(response.body().contentType(), bodyString))
                .build();
    }

    private static String bodyToString(final Request request) {
        try {
            final Request copy = request.newBuilder().build();
            final Buffer buffer = new Buffer();
            copy.body().writeTo(buffer);
            return buffer.readUtf8();
        } catch (final IOException e) {
            return "did not work";
        }
    }
}
