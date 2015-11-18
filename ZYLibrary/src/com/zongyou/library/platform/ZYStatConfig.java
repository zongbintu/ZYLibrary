package com.zongyou.library.platform;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import com.tencent.stat.MtaSDkException;
import com.tencent.stat.StatConfig;
import com.tencent.stat.StatService;

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
    public static void setAppKey(Context context, String appKey){
        StatConfig.setAppKey(context, appKey);
        try {
            StatService.startStatService(context, appKey,
                    com.tencent.stat.common.StatConstants.VERSION);
        } catch (MtaSDkException e) {
            e.printStackTrace();
            Log.e(TAG, "初始化统计失败: " + e.getMessage());
        }
    }

    // 设置MTA渠道（投放渠道/市场），也可以通过xml配置
    public static void setInstallChannel(Context context, String channel){
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

    public static void onPageResume(Context context){
        StatService.onResume(context);
    }

    public static void onPagePause(Context context){
        StatService.onPause(context);
    }

    public static void onPageResume(Context ctx, String pageName){
        StatService.trackBeginPage(ctx, pageName);
    }

    public static void onPagePause(Context ctx, String pageName){
        StatService.trackEndPage(ctx, pageName);
    }
}
