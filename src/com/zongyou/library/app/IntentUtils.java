package com.zongyou.library.app;


import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.text.TextUtils;

import com.zongyou.library.R;
import com.zongyou.library.util.RegexUtils;
import com.zongyou.library.util.ToastUtils;

import java.io.File;

/**
 * 调用系统APP功能
 *
 * @author Altas
 * @email Altas.Tutu@gmail.com
 * @time 2015-1-13 下午5:11:58
 */
public class IntentUtils {
    /**
     * 拨打电话,需要添加"android.permission.CALL_PHONE"权限
     *
     * @param context
     * @param num
     */
    public static void call(Context context, String num) {
        Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + num));
        context.startActivity(intent);
    }

    /**
     * 调用拨号键盘
     *
     * @param context
     * @param num
     */
    public static void dial(Context context, String num) throws Exception {
        if (!RegexUtils.match(RegexUtils.POSITIVE_INTEGER, num))
            throw new Exception("is not tel number");
        Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + num));
        context.startActivity(intent);
    }

    /**
     * 调用发送短信
     *
     * @param context
     * @param num
     * @param sms_body
     */
    public static void sendto(Context context, String num, String sms_body) {
        Intent intent = new Intent(Intent.ACTION_SENDTO, Uri.parse("smsto:" + num));
        if (!TextUtils.isEmpty(sms_body))
            intent.putExtra("sms_body", sms_body);
        context.startActivity(intent);
    }

    /**
     * 安装APK
     *
     * @param context
     * @param fileName
     */
    public static void installAPK(Context context, String fileName) {
        File apkfile = new File(fileName);
        if (!apkfile.exists()) {
            return;
        }
        Intent i = new Intent(Intent.ACTION_VIEW);
        i.setDataAndType(Uri.parse("file://" + apkfile.toString()), "application/vnd.android.package-archive");
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(i);
    }

    /**
     * 打开地图
     * @param context
     * @param uri geo:" + point.y + "," + point.x + "?q=" + point.address
     */
    public static void openSysMap(Context context,Uri uri){
        try {
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            context.startActivity(intent);
        } catch (Exception e) {
            ToastUtils.show(context, "请先安装地图APP");
        }
    }
}
