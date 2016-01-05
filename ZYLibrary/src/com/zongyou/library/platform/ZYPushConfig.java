package com.zongyou.library.platform;

import android.app.Notification;
import android.content.Context;
import android.support.annotation.NonNull;

import com.zongyou.library.util.LogUtils;

import java.util.Set;

import cn.jpush.android.api.BasicPushNotificationBuilder;
import cn.jpush.android.api.JPushInterface;
import cn.jpush.android.api.TagAliasCallback;

/**
 * User: ldh (394380623@qq.com)
 * Date: 2015-11-18
 * Time: 16:18
 * FIXME
 */
public class ZYPushConfig {

    private final static String TAG= "ZYPushConfig";
    private static int sRetryTimes = 0;

    public static void init(@NonNull Context context) {
        JPushInterface.init(context);
    }

    /**
     * 推送消息最多保留条数
     * @param context
     * @param num
     */
    public static void setLatestNotificationNumber(@NonNull Context context, int num){
        JPushInterface.setLatestNotificationNumber(context, num);
    }


    public static void setDebugMode(boolean debug) {
        JPushInterface.setDebugMode(debug);
    }

    /**
     * 初始化标签
     * @param context
     * @param list
     */
    public static void initTags(@NonNull Context context, @NonNull Set<String> list) {


        JPushInterface.setTags(context, list,
                new TagAliasCallback() {

                    @Override
                    public void gotResult(int arg0, String arg1,
                                          Set<String> arg2) {

                    }
                });
    }

    /**
     * 初始化别名
     * @param context
     * @param alias
     */
    public static void initAlias(@NonNull final Context context, @NonNull final String alias) {

        JPushInterface.setAlias(context, alias,
                new TagAliasCallback() {

                    @Override
                    public void gotResult(int arg0, String arg1,
                                          Set<String> arg2) {
                        if (arg0 == 6002 && sRetryTimes < 3) {
                            initAlias(context, alias);
                            sRetryTimes++;

                            LogUtils.d(TAG, "初始化推送别名，重试：" + sRetryTimes);
                        } else {
                            sRetryTimes = 0;
                            LogUtils.d(TAG, "初始化推送别名：成功");
                        }
                    }
                });
    }

    /**
     * 清空推送消息
     * @param context
     */
    public static void clearAllNotifications(@NonNull Context context) {
        JPushInterface.clearAllNotifications(context);
    }

    /**
     * 设置默认收到推送是否需要声音和振动
     * @param context
     * @param sound
     * @param vibrate
     */
    public static void setDefaultNotificationSound(@NonNull Context context, boolean sound, boolean vibrate){
        BasicPushNotificationBuilder builder = new BasicPushNotificationBuilder(context);
        builder.notificationFlags = Notification.FLAG_AUTO_CANCEL;  //设置为自动消失
        int notificationDefaults = Notification.DEFAULT_LIGHTS;
        if(sound){
            notificationDefaults |= Notification.DEFAULT_SOUND;
        }
        if(vibrate){
            notificationDefaults |= Notification.DEFAULT_VIBRATE;
        }
        builder.notificationDefaults = notificationDefaults;
        JPushInterface.setDefaultPushNotificationBuilder(builder);
    }


    public static void stopPush(@NonNull Context context){
        JPushInterface.stopPush(context);
    }

    public static void resumePush(@NonNull Context context){
        JPushInterface.resumePush(context);
    }

    public static boolean isPushStopped(@NonNull Context context){
        return JPushInterface.isPushStopped(context);
    }
}
