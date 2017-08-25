package com.itcast.googleplay.ui.fragment;

import com.google.gson.reflect.TypeToken;
import com.itcast.googleplay.adapter.BaseicAdapter;
import com.itcast.googleplay.adapter.HomeAdapter;
import com.itcast.googleplay.bean.AppInfo;
import com.itcast.googleplay.http.Api;
import com.itcast.googleplay.http.HttpUtils;
import com.itcast.googleplay.utils.CommonUtil;
import com.itcast.googleplay.utils.JsonUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Lenovo on 2016/7/27.
 */
public class AppFragment extends BasicListFragment<AppInfo> {

    @Override
    protected BaseicAdapter<AppInfo> getAdapter() {
        return new HomeAdapter(list);       //一样的布局是不需要重新的创建的
    }

    @Override
    public Object requestData() {
        String json = HttpUtils.get(Api.APP + list.size());
        ArrayList<AppInfo> appInfos =
                (ArrayList<AppInfo>) JsonUtil.parseJsonToList(json, new TypeToken<List<AppInfo>>() {
                }.getType());
        if (appInfos != null) {
            list.addAll(appInfos);          //集合添加数据
            CommonUtil.runOnUIThread(new Runnable() {
                @Override
                public void run() {
                    baseicAdapter.notifyDataSetChanged();   //适配器的通知更新
                    refreshListView.onRefreshComplete();    //完成的时候,不能少,若是没有的话,刷新的界面是不能缩回去的
                }
            });
        }
        return appInfos;    //数据的返回.
    }
}
