package com.zongyou.library.app;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

/**
 * Fragment管理工具类
 *
 * @author Altas
 * @email Altas.Tu@qq.com
 * @date 2014-3-12 下午6:45:16
 */
public class FragmentUtil {
    private static boolean mIsCanEixt = false;

    /**
     * Fragment跳转
     *
     * @param fm
     * @param fragmentClass
     * @param args
     */
    public static void turnToFragment(FragmentManager fm, int containerId,
                                      Class<? extends Fragment> fragmentClass, Bundle args) {
        final String tag = fragmentClass.getName();
        mIsCanEixt = false;
        Fragment fragment = fm.findFragmentByTag(tag);
        boolean isFragmentExist = true;
        if (fragment == null) {
            try {
                isFragmentExist = false;
                fragment = fragmentClass.newInstance();
                fragment.setArguments(new Bundle());
            } catch (java.lang.InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        if (fragment.isAdded()) {
            return;
        }
        if (args != null && !args.isEmpty()) {
            fragment.getArguments().putAll(args);
        }
        FragmentTransaction ft = fm.beginTransaction();
        ft.setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out,
                android.R.anim.fade_in, android.R.anim.fade_out);
        if (isFragmentExist) {
            ft.replace(containerId, fragment);
        } else {
            ft.replace(containerId, fragment, tag);
        }

        ft.addToBackStack(tag);
        ft.commitAllowingStateLoss();
    }

    /**
     * Fragment跳转
     *
     * @param fm
     * @param fragmentClass
     * @param args
     */
    @Deprecated
    public static void turnToFragment(FragmentManager fm, int containerId,
                                      Class<? extends Fragment> fragmentClass, Bundle args,
                                      boolean backAble) {
        final String tag = fragmentClass.getName();
        mIsCanEixt = false;
        Fragment fragment = fm.findFragmentByTag(tag);
        boolean isFragmentExist = true;
        if (fragment == null) {
            try {
                isFragmentExist = false;
                fragment = fragmentClass.newInstance();
                fragment.setArguments(new Bundle());
            } catch (java.lang.InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        if (fragment.isAdded()) {
            return;
        }
        if (args != null && !args.isEmpty()) {
            fragment.getArguments().putAll(args);
        }
        FragmentTransaction ft = fm.beginTransaction();
        ft.setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out,
                android.R.anim.fade_in, android.R.anim.fade_out);
        if (isFragmentExist) {
            ft.replace(containerId, fragment);
        } else {
            ft.replace(containerId, fragment, tag);
        }
        if (backAble) {
            ft.addToBackStack(tag);
        }
        ft.commitAllowingStateLoss();
    }

    /**
     * Fragment跳转
     *
     * @param fm
     * @param containerId
     * @param fragment
     * @param backAble
     */
    public static void turnToFragment(FragmentManager fm, int containerId,
                                      Fragment fragment,
                                      boolean backAble) {
        final String tag = fragment.getClass().getName();
        mIsCanEixt = false;
        Fragment tempFragment = fm.findFragmentByTag(tag);
        boolean isFragmentExist = true;
        if (tempFragment == null) {
                isFragmentExist = false;
        }
        if (fragment.isAdded()) {
            return;
        }
        FragmentTransaction ft = fm.beginTransaction();
        ft.setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out,
                android.R.anim.fade_in, android.R.anim.fade_out);
        if (isFragmentExist) {
            ft.replace(containerId, fragment);
        } else {
            ft.replace(containerId, fragment, tag);
        }
        if (backAble) {
            ft.addToBackStack(tag);
        }
        ft.commitAllowingStateLoss();
    }


    /**
     * 按tag返回fragment Pop the last fragment transition from the manager's
     * fragment back stack. If there is nothing to pop, false is returned. This
     * function is asynchronous -- it enqueues the request to pop, but the
     * action will not be performed until the application returns to its event
     * loop.
     *
     * @param fragmentManager
     * @param name            If non-null, this is the name of a previous back state to look
     *                        for; if found, all states up to that state will be popped. The
     *                        POP_BACK_STACK_INCLUSIVE flag can be used to control whether
     *                        the named state itself is popped. If null, only the top state
     *                        is popped.
     */
    public static void popBackStackImmediate(FragmentManager fragmentManager,
                                             String name) {
        fragmentManager.popBackStackImmediate(name,
                FragmentManager.POP_BACK_STACK_INCLUSIVE);
    }
}