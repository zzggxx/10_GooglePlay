package com.itcast.googleplay.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.itcast.googleplay.utils.CommonUtil;

/**
 * *************** 这是最大基类,是所有的fragment的父类,包括baselistFragment.$$$$其实所有的基类就是数据和布局!!!!!!!!
 * 注意使用的是v4的包
 * Created by Lenovo on 2016/7/27.
 */
public abstract class BaseFragment extends Fragment {
    //$$$$引入每一个界面都需要的加载页面loadingPager,具体数据和页面由子类实现
    public LoadingPager loadingPager;

    //实现布局
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (loadingPager == null) {
            loadingPager = new LoadingPager(getActivity()) {
                // 一下两个方法继续抽象着使用即可.
                @Override
                protected View createSuccessView() {    //*****获取布局和数据,加载页面逻辑实现的,这里是实现.
                    return getSuccessView();
                }

                @Override
                public Object loadData() {
                    return requestData();
                }
            };
        } else {
            // TODO: 2017/1/16 注意移除父类的方法.
            /**
             这是解决了点击三个之外页面,回到重新加载的bug,应该是使用第一次的,记录了用户浏览位置的loadingpager的!!!!
             ViewPager的子View是不能添加两次,但是5.0之后已经是去掉了外层的包裹布局,就是已经取消了
             *
             */
            CommonUtil.removeSelfFromParent(loadingPager);//直接的移除还是使用上次已经创建好的loadingpager
        }
        return loadingPager;    //所有的布局和数据都是由loadingpager引出来的
    }

    /**
     * 获取子类的布局
     *
     * @return
     */
    public abstract View getSuccessView();

    /**
     * 子类布局文件的数据
     *
     * @return
     */
    public abstract Object requestData();

}
