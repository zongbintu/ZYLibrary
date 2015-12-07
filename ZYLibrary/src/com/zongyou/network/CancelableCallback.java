package com.zongyou.network;

import java.util.ArrayList;
import java.util.List;

import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

/**
 * Created by tu on 15/10/30.
 */
public abstract class CancelableCallback<T> implements Callback<T> {
    private static List<CancelableCallback> mList = new ArrayList<>();
    private boolean isCanceled = false;
    private Object mTag = null;

    public static void cancelAll() {
        List<CancelableCallback> list =new ArrayList<>();
        list.addAll(mList);
        for (CancelableCallback callback : list) {
            callback.cancel();
        }
    }

    public static void cancel(Object tag) {
        if (tag != null)
            for (CancelableCallback callback : mList) {
                if (tag.equals(callback.mTag))
                    callback.cancel();
            }
    }

    public CancelableCallback() {
        mList.add(this);
    }

    public CancelableCallback(Object tag) {
        mTag = tag;
        mList.add(this);
    }

    public void cancel() {
        isCanceled = true;
        mList.remove(this);
    }

    @Override
    public void onResponse(Response<T> response, Retrofit retrofit) {
        if (!isCanceled)
            onSuccess(response);
        mList.remove(this);
    }

    @Override
    public void onFailure(Throwable t) {
        if (!isCanceled)
            onError(t);
        mList.remove(this);
    }

    public abstract void onSuccess(Response<T> response);

    public abstract void onError(Throwable t);
}
