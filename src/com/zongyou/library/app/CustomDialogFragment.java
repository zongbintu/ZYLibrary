package com.zongyou.library.app;

import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;

import com.zongyou.library.R;

/**
 * @author Altas
 * @email Altas.Tutfei@gmail.com
 * @time 2015-3-23 上午10:13:19
 */
public class CustomDialogFragment extends DialogFragment {

    private static CustomDialogFragment dialog;

    public CustomDialogFragment() {
        setCancelable(true);
    }

    public static void getInstance(FragmentManager fragmentManager, String tag) {
        if (dialog == null || dialog.isDetached() || !tag.equals(dialog.getTag()) || !dialog.isVisible()) {
            dialog = new CustomDialogFragment();
            dialog.show(fragmentManager, tag);
        }
    }

    public static void dismissDialog() {
        if (null != dialog)
            dialog.dismissAllowingStateLoss();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog mDialog = new Dialog(getActivity(), R.style.Transparent);
        mDialog.setContentView(R.layout.loading_dialog);
        return mDialog;
    }
}
