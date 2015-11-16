package com.zongyou.library.util;

import android.content.Context;
import android.content.res.Configuration;
import android.graphics.PixelFormat;
import android.os.Handler;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import com.zongyou.library.R;

/**
 * show(),不开弹窗无法显示
 * showToast()，采用windowManager的方式，但是在红米下报错
 * Created by atlas on 15/4/25.
 * Email:atlas.tufei@gmail.com
 */
public class ToastUtils {
    private static final String TAG = ToastUtils.class.getSimpleName();
    private static TextView tv;
    public static final int LENGTH_LONG = 3500; // 3.5 seconds
    public static final int LENGTH_SHORT = 2000; // 2 seconds

    private static View mNextView;
    private static int mGravity, mX, mY;
    private static final WindowManager.LayoutParams mParams = new WindowManager.LayoutParams();
    private static WindowManager mWM;
    private static Handler mHanlder = new Handler();
    private static Toast mToast;

    /**
     * init
     * @param context
     */
    private static void init(Context context) {
        mY = context.getResources().getDimensionPixelSize(
                R.dimen.toast_y_offset);
        mGravity = context.getResources().getInteger(
                R.integer.config_toastDefaultGravity);
        LayoutInflater inflate = (LayoutInflater)
                context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mNextView = inflate.inflate(R.layout.transient_notification, null);
        TextView tv = (TextView) mNextView.findViewById(android.R.id.message);

        mParams.height = WindowManager.LayoutParams.WRAP_CONTENT;
        mParams.width = WindowManager.LayoutParams.WRAP_CONTENT;
        mParams.format = PixelFormat.TRANSLUCENT;
        mParams.windowAnimations = R.style.Animation_Toast;
        mParams.type = WindowManager.LayoutParams.TYPE_TOAST;
        mParams.setTitle("Toast");
        mParams.flags = WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON
                | WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
                | WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE;


        mWM = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        // We can resolve the Gravity here by using the Locale for getting
        // the layout direction
        final Configuration config = mNextView.getContext().getResources().getConfiguration();
//        final int gravity = Gravity.getAbsoluteGravity(mGravity, config.getLayoutDirection());
        mParams.gravity = Gravity.BOTTOM;
//        if ((gravity & Gravity.HORIZONTAL_GRAVITY_MASK) == Gravity.FILL_HORIZONTAL) {
//            mParams.horizontalWeight = 1.0f;
//        }
//        if ((gravity & Gravity.VERTICAL_GRAVITY_MASK) == Gravity.FILL_VERTICAL) {
//            mParams.verticalWeight = 1.0f;
//        }
        mParams.x = mX;
        mParams.y = mY;
//        mParams.verticalMargin = mVerticalMargin;
//        mParams.horizontalMargin = mHorizontalMargin;
        mParams.packageName = context.getPackageName();
    }

    /**
     * Show the view for the specified duration.
     * @param context
     * @param text
     * @param duration
     */
    private static void showToast(final Context context, final CharSequence text, int duration) {
        if (context == null) {
        	return;
            //throw new RuntimeException("context is null");
        }

        if (mWM == null || mNextView == null) {
            init(context);
        }
        mHanlder.removeCallbacks(cancelRunable);
        mHanlder.post(new Runnable() {
            @Override
            public void run() {
                ((TextView) mNextView.findViewById(android.R.id.message)).setText(text);
                LogUtils.e("has parent "+(mNextView.getParent() == null));
                if (mNextView.getParent() != null){
                	LogUtils.e("parent "+mNextView.getParent().toString());
                    mWM.removeView(mNextView);
                    
                }
                mWM.addView(mNextView, mParams);
            }
        });
        mHanlder.postDelayed(cancelRunable, duration);
    }
    private static void showToast(final Context context, final int stringid) {
    	showToast(context,context.getString(stringid),LENGTH_SHORT);
    }
    
    private static void showToast(final Context context, final CharSequence text) {
    	showToast(context,text,LENGTH_SHORT);
    }

    private static Runnable cancelRunable = new Runnable() {
        @Override
        public void run() {
            cancel();
        }
    };

    /**
     * cancel toast
     */
    public static void cancel() {
        if (mNextView != null && mNextView.getParent() != null)
            mWM.removeViewImmediate(mNextView);
    }
    public static void show(Context context, CharSequence msg, int duration) {
		if (mToast == null) {
			mToast = Toast.makeText(context, msg, duration);
		} else {
			mToast.setText(msg);
		}
		mToast.show();
	}

	public static void show(Context context, CharSequence msg) {
		show(context, msg, Toast.LENGTH_SHORT);
	}

	public static void show(Context context, int msgId) {
		show(context, context.getString(msgId), Toast.LENGTH_SHORT);
	}

}
