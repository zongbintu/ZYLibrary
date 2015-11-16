package com.zongyou.library.app;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.MotionEvent;

/**
 * @author Altas
 * @email Altas.TuTu@gmail.com
 * @date 2014年9月27日
 */
public class BaseFragmentActivity extends FragmentActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		AppManager.getAppManager().addActivity(this);
	}

	@Override
	protected void onDestroy() {
		AppManager.getAppManager().finishActivity(this);
		super.onDestroy();
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		AppUtils.hideSoftInput(this);
		return super.onTouchEvent(event);
	}

}
