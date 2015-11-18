package com.zongyou.library.platform;

import android.content.Context;
import android.support.annotation.NonNull;
import android.text.TextUtils;

import com.zongyou.library.util.storage.PreferenceUtils;

import java.util.HashSet;
import java.util.Set;

import cn.jpush.android.api.JPushInterface;
import cn.jpush.android.api.TagAliasCallback;

/**
 * User: ldh (394380623@qq.com)
 * Date: 2015-11-18
 * Time: 16:18
 * FIXME
 */
public class ZYPushConfig {

    private static int num = 0;

    public static void init(@NonNull Context context) {
        JPushInterface.init(context);
    }

    public static void setDebugMode(boolean debug) {
        JPushInterface.setDebugMode(debug);
    }

    /**
     * 标签
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
     * 别名
     */
    public static void initAlias(@NonNull final Context context, @NonNull final String alias) {

        JPushInterface.setAlias(context, alias,
                new TagAliasCallback() {

                    @Override
                    public void gotResult(int arg0, String arg1,
                                          Set<String> arg2) {
                        if (arg0 == 6002 && num < 3) {
                            initAlias(context, alias);
                            num++;
                        } else {
                            num = 0;
                        }
                    }
                });
    }

    public static void clearAllNotifications(Context context) {
        JPushInterface.clearAllNotifications(context);
    }
}
