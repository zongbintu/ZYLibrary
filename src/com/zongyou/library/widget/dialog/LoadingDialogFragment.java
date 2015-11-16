package com.zongyou.library.widget.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;

/**
 * DialogFragment
 * 
 * @author Altas
 * @email Altas.TuTu@gmail.com
 * @date 2014年9月29日
 */
public class LoadingDialogFragment extends DialogFragment {

	private static LoadingDialogFragment mDialogFragment;

	public static LoadingDialogFragment newInstance(int msg) {
		mDialogFragment = null == mDialogFragment ? new LoadingDialogFragment()
				: mDialogFragment;
		// Supply msg input as an argument.
		Bundle args = new Bundle();
		args.putInt("msg", msg);
		mDialogFragment.setArguments(args);
		return mDialogFragment;
	}

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		// Verify that the host activity implements the callback interface
		/*
		 * try { // Instantiate the LoadingDialogListener so we can send events
		 * to // the host mListener = (LoadingDialogListener) activity; } catch
		 * (ClassCastException e) { // The activity doesn't implement the
		 * interface, throw exception throw new
		 * ClassCastException(activity.toString() +
		 * " must implement LoadingDialogListener"); }
		 */
	}

	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		// 加载中
		Dialog dialog = LightProgressDialog.create(getActivity(),
				getString(getArguments().getInt("msg")));
		dialog.setCancelable(true);

		return dialog;
	}

	public static void show(int msgRes, FragmentManager fragmentManager,
			String tag) {
		// 避免重复创建fragment实例
		if (null != mDialogFragment && tag.equals(mDialogFragment.getTag())) {
			Bundle args = new Bundle();
			args.putInt("msg", msgRes);
			mDialogFragment.setArguments(args);
		}
		mDialogFragment = LoadingDialogFragment.newInstance(msgRes);
		mDialogFragment.show(fragmentManager, tag);
	}

	public static void dismissDialog() {
		if (null != mDialogFragment)
			mDialogFragment.dismissAllowingStateLoss();
	}

}
