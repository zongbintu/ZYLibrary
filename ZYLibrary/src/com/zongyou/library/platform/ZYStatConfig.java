package com.zongyou.library.platform;

import android.content.Context;
import android.support.annotation.NonNull;
import android.text.TextUtils;

import com.tencent.stat.MtaSDkException;
import com.tencent.stat.StatConfig;
import com.tencent.stat.StatService;
import com.zongyou.library.util.LogUtils;

/**
 * User: ldh (394380623@qq.com)
 * Date: 2015-11-18
 * Time: 16:17
 * FIXME
 */
public class ZYStatConfig {
    protected static final String TAG = "ZYStatConfig";
    protected static final String DEFAULT_CHANNEL = "FanWe";
    // 设置MTA appkey，也可以通过xml配置
    public static void setAppKey(@NonNull Context context, @NonNull String appKey){
        StatConfig.setAppKey(context, appKey);
        try {
            StatService.startStatService(context, appKey,
                    com.tencent.stat.common.StatConstants.VERSION);
        } catch (MtaSDkException e) {
            e.printStackTrace();
            LogUtils.d(TAG, "初始化统计失败: " + e.getMessage());
        }
    }

    // 设置MTA渠道（投放渠道/市场），也可以通过xml配置
    public static void setInstallChannel(@NonNull Context context, String channel){
        if(TextUtils.isEmpty(channel)) {
            StatConfig.setInstallChannel(context, DEFAULT_CHANNEL);
        }else{
            StatConfig.setInstallChannel(context, channel);
        }
    }

    // 是否捕获Java异常，默认true。
    public static void setAutoExceptionCaught(boolean caught){
        StatConfig.setAutoExceptionCaught(caught);
    }

    public static void setDebugMode(boolean debug){
        StatConfig.setDebugEnable(debug);
    }

    public static void onPageResume(@NonNull Context context){
        StatService.onResume(context);
    }

    public static void onPagePause(@NonNull Context context){
        StatService.onPause(context);
    }

    public static void onPageResume(@NonNull Context ctx, String pageName){
        StatService.trackBeginPage(ctx, pageName);
    }

    public static void onPagePause(@NonNull Context ctx, String pageName){
        StatService.trackEndPage(ctx, pageName);
    }
}
