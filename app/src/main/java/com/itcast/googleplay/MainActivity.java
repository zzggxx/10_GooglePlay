package com.itcast.googleplay;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.itcast.googleplay.adapter.MainPagerAdapter;
import com.itcast.googleplay.ui.widght.PagerSlidingTab;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class MainActivity extends AppCompatActivity {

    @InjectView(R.id.slidingTab)
    PagerSlidingTab slidingTab;
    @InjectView(R.id.viewPager)
    ViewPager viewPager;
    @InjectView(R.id.drawerLayout)
    DrawerLayout drawerLayout;
    @InjectView(R.id.toolbar)
    Toolbar toolbar;

    private ActionBarDrawerToggle drawerToggle;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.inject(this);   //一加载布局就使用注入的,避免使用的时候空指针,若是反射得到的View,应该是两个参数this,view
        // TODO: 2016/9/22 逻辑更加的清楚
        initView();
        setToolBar();
    }


    /**
     * 初始化布局
     */
    private void initView() {
        //设置适配器之后进行绑定
        MainPagerAdapter adapter = new MainPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(adapter);
        slidingTab.setViewPager(viewPager); //别忘记了获取title

//        $$$$缓存页数,就是说点到后边的页面(ViewPager缓存是两个或者三个)再回来的时候回重新的加载,但是该方法有弊有利
//        解决方法就是直接的加载原来的loadingpager,就是第一次已经创建好了的loadingpager,但是有一个重复的问题,注意处理5.0之后
//        已经没有那个重复添加的问题了
//        利：非常方便的帮助我们缓存所有的page
//        弊：会预加载所有的页数，会过多消耗用户流量，同时很可能造成卡顿
//        viewPager.setOffscreenPageLimit(adapter.getCount());
    }

    /**
     * $$$$初始化ToolBar,模板型代码.以后使用这一个
     */
    private void setToolBar() {

        //$$$属性应该是在xml文件中写
        toolbar.setLogo(R.drawable.ic_launcher);
        toolbar.setTitle(R.string.app_name);
        toolbar.setTitleTextColor(Color.RED);

        //代替actionBar
        setSupportActionBar(toolbar);
        getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);
        drawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar,
                android.support.v7.appcompat.R.string.abc_action_bar_home_description,
                android.support.v7.appcompat.R.string.abc_action_bar_home_description_format);
        //滑动和汉堡包同步变化
        drawerToggle.syncState();
        drawerLayout.addDrawerListener(drawerToggle);

    }
}
