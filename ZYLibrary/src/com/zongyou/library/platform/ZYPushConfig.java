package com.zongyou.library.platform;

import android.content.Context;
import android.support.annotation.NonNull;

import cn.jpush.android.api.JPushInterface;

/**
 * User: ldh (394380623@qq.com)
 * Date: 2015-11-18
 * Time: 16:18
 * FIXME
 */
public class ZYPushConfig {


    public static void init(@NonNull Context context){
        JPushInterface.init(context);
    }

    public static void setDebugMode(boolean debug){
        JPushInterface.setDebugMode(debug);
    }
}
