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
public class GameFragment extends BasicListFragment {
    @Override
    protected BaseicAdapter<AppInfo> getAdapter() {
        return new HomeAdapter(list);
    }

    @Override
    public Object requestData() {
        String json = HttpUtils.get(Api.GAME + list.size());
        ArrayList<AppInfo> appInfos =
                (ArrayList<AppInfo>) JsonUtil.parseJsonToList(json, new TypeToken<List<AppInfo>>() {
                }.getType());
        if (appInfos != null) {
            list.addAll(appInfos);
            CommonUtil.runOnUIThread(new Runnable() {
                @Override
                public void run() {
                    baseicAdapter.notifyDataSetChanged();
                    refreshListView.onRefreshComplete();
                }
            });
        }
        return appInfos;
    }
}
