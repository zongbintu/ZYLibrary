package com.zongyou.library.app;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.zongyou.library.platform.ZYStatConfig;
import com.zongyou.library.util.LogUtils;
import com.zongyou.library.widget.util.ViewFinder;

/**
 * Fragment基类
 *
 * @author Altas
 * @version 1.0 2014-02-18 14:16:39
 * @update initAble 赋值修改
 */
public abstract class BaseFragment extends Fragment {
    public Activity mFragmentActivity;
    protected View mRootView;// 缓存Fragment view
    // 是否可以初始化
    protected boolean initAble = true;
    protected ViewFinder mViewFinder;

    private String mFragmentTag; // 页面统计TAG

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        initAble = mRootView == null;
        if (initAble)
            mRootView = inflateView(inflater, container);
        mViewFinder = new ViewFinder(mRootView);
        parentIsAdded();
        if (initAble)
            initView();
        return mRootView;
    }

    // inflate view
    protected abstract View inflateView(LayoutInflater inflater,
                                        ViewGroup container);

    // init view
    protected abstract void initView();

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mFragmentActivity = activity;
    }

    protected void parentIsAdded() {
        // 缓存的rootView需要判断是否已经被加过parent，
        // 如果有parent需要从parent删除，要不然会发生这个rootview已经有parent的错误。
        if (null != mRootView) {
            ViewGroup parent = (ViewGroup) mRootView.getParent();
            if (parent != null) {
                parent.removeView(mRootView);
                //initAble=false;
            }

        }
    }

    public void close() {
        if (getFragmentManager().getBackStackEntryCount() > 0) {
            getFragmentManager().popBackStack();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mFragmentActivity = null;
    }

    protected void setViewVisible(int layoutId, int visibility) {
        mViewFinder.find(layoutId).setVisibility(visibility);
    }

    protected TextView setViewText(int layoutId, CharSequence text) {
        TextView tv = mViewFinder.find(layoutId);
        tv.setText(text);
        return tv;
    }

    protected void setViewClickListener(int layoutId, View.OnClickListener listener) {
        mViewFinder.find(layoutId).setOnClickListener(listener);
    }


    public void setPageTag(String tag) {
        mFragmentTag = tag;
    }

    public String getPageTag() {
        if (TextUtils.isEmpty(mFragmentTag) && ZYStatConfig.isNeedStat()) {
            LogUtils.e("STAT_TAG", "NET SEET TAG ====>>: " + this.getClass().getName());
        }
        return mFragmentTag;
    }

    @Override
    public void onResume() {
        super.onResume();
        ZYStatConfig.onPageResume(getContext(), getPageTag());
    }

    @Override
    public void onPause() {
        super.onPause();
        ZYStatConfig.onPagePause(getContext(), getPageTag());
    }


}