package com.itcast.googleplay.holder;

import android.view.View;

/**
 * $$$$$$$$$$$$$$$$$每一个界面都有相同的逻辑,holder的提取,体现了模块化的思想,MVP的思想
 * Created by Lenovo on 2016/7/28.
 */
public abstract class BaseHolder<T> {
    /**
     * ***引入的数据源
     */
    public View holderView;    //$$$$$一开始的时候就将convertView转变到ViewHolder中使用变量进行表示

    /**
     * 由构造方法传入getView中的必须参数项,由于布局是必须的所以是构造方法传入的.
     */
    public BaseHolder() {
        holderView = initHolderView();
        holderView.setTag(this);
    }

    /**
     * 不知道布局的具体体现
     *
     * @return
     */
    protected abstract View initHolderView();

    /**
     * 将适配器中的具体请求到参数设置给具体需要的地方,就说布局需中构造函数中需要的数据集合是你创造的时候必须的,所以给你
     *
     * @param t
     */
    public abstract void bindData(T t);

    /**
     * getView需要的布局,这里只是为了抽取adapter的方便而已..
     *
     * @return
     */
    public View getHolderView() {
        return holderView;
    }
}

