package com.itcast.googleplay.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.itcast.googleplay.R;
import com.itcast.googleplay.ui.fragment.FragmentFactory;
import com.itcast.googleplay.utils.CommonUtil;

/**
 * $$$添加的是Fragment当然使用的是FragmentPagerAdapter,注意和其他几个的区别
 * 但是子View是继承的fragment,
 * <p>
 * Created by Lenovo on 2016/7/27.
 */
public class MainPagerAdapter extends FragmentPagerAdapter {

    private String[] tabs;

    /**
     * 一创建便有了数据
     * $$$获得R文件中的字符串和数组
     * R.array.XXXX
     * R.String.XXXX
     *
     * @param fm
     */
    public MainPagerAdapter(FragmentManager fm) {
        super(fm);
//        需要使用context获取,getResources()拿到资源文件.
//        tabs = GooglePlayApplication.context.getResources().getStringArray(R.array.tab_names);
        tabs = CommonUtil.getStringArray(R.array.tab_names);
    }

    /**
     * $$$$单例模式的工厂类,比较很方便,也是高大上
     *
     * @param position
     * @return
     */
    @Override
    public Fragment getItem(int position) {
        return FragmentFactory.create(position);
    }

    @Override
    public int getCount() {
        return tabs.length;
    }

    /**
     * $$$$$使用indicator的时候必须使用此方法,千万要注意!
     * 注意有时候需要进行取余操作.
     *
     * @param position
     * @return
     */
    @Override
    public CharSequence getPageTitle(int position) {
        return tabs[position];
    }
}
