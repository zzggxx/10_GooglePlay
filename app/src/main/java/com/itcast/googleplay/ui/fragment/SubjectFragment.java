package com.itcast.googleplay.ui.fragment;

import com.google.gson.reflect.TypeToken;
import com.itcast.googleplay.adapter.BaseicAdapter;
import com.itcast.googleplay.adapter.SubjectAdapter;
import com.itcast.googleplay.bean.Subject;
import com.itcast.googleplay.http.Api;
import com.itcast.googleplay.http.HttpUtils;
import com.itcast.googleplay.utils.CommonUtil;
import com.itcast.googleplay.utils.JsonUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Lenovo on 2016/7/27.
 */
public class SubjectFragment extends BasicListFragment {
    /**
     * 专题特有的一个适配器
     *
     * @return
     */
    @Override
    protected BaseicAdapter getAdapter() {
        return new SubjectAdapter(list);
    }

    /**
     * 数据请求并且进行的绑定
     *
     * @return
     */
    @Override
    public Object requestData() {
        String json = HttpUtils.get(Api.SUBJECT + list.size());
        ArrayList<Subject> subjects =
                (ArrayList<Subject>) JsonUtil.parseJsonToList(json, new TypeToken<List<Subject>>() {
                }.getType());
        if (subjects != null) {
            list.addAll(subjects);
            CommonUtil.runOnUIThread(new Runnable() {
                @Override
                public void run() {
                    baseicAdapter.notifyDataSetChanged();
                    refreshListView.onRefreshComplete();
                }
            });
        }
        return subjects;
    }
}
