package com.itcast.googleplay.ui.fragment;

import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.itcast.googleplay.R;
import com.itcast.googleplay.adapter.BaseicAdapter;
import com.itcast.googleplay.global.GooglePlayApplication;

import java.util.ArrayList;

/**
 * Created by Lenovo on 2016/7/29.
 */
public abstract class BasicListFragment<T> extends BaseFragment implements OnItemClickListener {
    protected ArrayList<T> list = new ArrayList<T>();
    protected BaseicAdapter<T> baseicAdapter;
    protected ListView listView;
    protected PullToRefreshListView refreshListView;

//    内部维护了很多得,可以看一看
//    PullToRefreshListView;
//    PullToRefreshGridView;
//    PullToRefreshHorizontalScrollView;
//    PullToRefreshScrollView;
//    PullToRefreshWebView;

    @Override
    public View getSuccessView() {
        //每一个布局都是三方的那个布局所以是不需要重新写的
        refreshListView = (PullToRefreshListView) View.inflate(
                GooglePlayApplication.context, R.layout.ptr_listview, null);    //$$$$下拉刷新的布局,看类库

        //设置刷新的模式
        setRefreshMode();

        //子类必须进行刷新界面,设置刷新的监听.
        refreshListView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener<ListView>() {
            //下拉刷新上拉加载都会执行的
            @Override
            public void onRefresh(PullToRefreshBase<ListView> refreshView) {
                //重新的加载loadingPager界面.
                loadingPager.loadDataAndRefreshPage();
            }
        });

        //都是对listView进行的重写,所以在父类进实现.
        listView = refreshListView.getRefreshableView();

        //设置是不是需要分割线和点击的效果
        setListView();

        //增加头部轮播图,自类根据自身详情而定,在适配器之前操作!
        addHeader();

        //是适配器,必须的.但是不知道怎么实现,不能new一个适配器的基类.
        baseicAdapter = getAdapter();
        listView.setAdapter(baseicAdapter);

        //增加item的点击事件,子类可以有也可以没有.
        listView.setOnItemClickListener(this);

        //返回的是包裹布局,不仅仅只是listView
        return refreshListView;
    }

    /**
     * 适配器必须的
     *
     * @return
     */
    protected abstract BaseicAdapter<T> getAdapter();

    /**
     * 轮播图
     */
    protected void addHeader() {

    }

    /**
     * 设置分割线和点击的效果,自类可以进行更改
     */
    protected void setListView() {
        listView.setDividerHeight(0);
        listView.setSelector(android.R.color.transparent);
    }

    /**
     * 设置刷新的模式,有一个模式大部分的默认,自类也时可以进行更改的
     */
    protected void setRefreshMode() {
        refreshListView.setMode(PullToRefreshBase.Mode.BOTH);
    }

    /**
     * 返回的数据,数据返回的时候应该是添加到集合中的,而且必须利用数据去更新界面.
     *
     * @return
     */
    @Override
    public Object requestData() {
        return null;
    }

    /**
     * 判断是不是从头开始的加载
     */
    protected void checkPullFromStart() {
        if (refreshListView.getCurrentMode() == PullToRefreshBase.Mode.PULL_FROM_START) {
            list.clear();   //从零又开始加载.应该是数据回来之后判断有数据之后进行清空.
        }
    }

    /**
     * ****抽象类实现了接口可以不重写抽象的方法,已经继承了,但是抽象类的子类这时候就出现了问题,是要进行重写的了
     * ****若是空实现了,子类就可以选择性的是不是进行重写了.
     *
     * @param adapterView
     * @param view
     * @param i
     * @param l
     */
    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

    }
}
