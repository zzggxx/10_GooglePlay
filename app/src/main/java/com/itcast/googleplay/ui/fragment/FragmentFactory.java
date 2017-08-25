package com.itcast.googleplay.ui.fragment;


import android.support.v4.app.Fragment;

/**
 * 根据位置创建不同的fragment,其实就是代码的简洁性
 * Created by Lenovo on 2016/7/27.
 */
public class FragmentFactory {
    public static Fragment create(int position) {
        Fragment fragment = null;
        switch (position) {
            case 0:
                fragment = new HomeFragment();
                break;
            case 1:
                fragment = new AppFragment();
                break;
            case 2:
                fragment = new GameFragment();
                break;
            case 3:
                fragment = new SubjectFragment();
                break;
            case 4:
                fragment = new RecommendFragment();
                break;
            case 5:
                fragment = new CategoryFragment();
                break;
            case 6:
                fragment = new HotFragment();
                break;
            default:

                break;
        }
        return fragment;
    }
}
