package com.itcast.googleplay.adapter;

import com.itcast.googleplay.holder.BaseHolder;
import com.itcast.googleplay.holder.CategoryInfoHolder;
import com.itcast.googleplay.holder.CategoryTitleHolder;

import java.util.ArrayList;

/**
 * Created by Lenovo on 2016/7/29.
 */
public class CategoryAdapter extends BaseicAdapter<Object> {

    public CategoryAdapter(ArrayList<Object> list) {
        super(list);
    }


    /**
     * ******************************适配器是不一样的,需要自己添加重新实现的方法
     */
    public final int ITEM_TITLE = 0;
    public final int ITEM_INFO = 1;

    /**
     * 一定要注意不是getitemtypecount();
     *
     * @return
     */
    @Override
    public int getViewTypeCount() {
        return 2;
    }

    @Override
    public int getItemViewType(int position) {
        Object o = list.get(position);
        if (o instanceof String) {
            return ITEM_TITLE;
        } else {
            return ITEM_INFO;
        }
    }

    @Override
    public BaseHolder<Object> getHolder(int position) {
        BaseHolder<Object> holder = null;
        int type = getItemViewType(position);
        if (type == ITEM_TITLE) {
            //**************但是由于holder封装了加载布局和绑定数据的操作，所以现在只需要根据不同的条目类型
            holder = new CategoryTitleHolder();
        } else {
            holder = new CategoryInfoHolder();
        }
        return holder;
    }
}
