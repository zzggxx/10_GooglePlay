package com.itcast.googleplay.ui.fragment;

import android.view.View;
import android.widget.ListView;

import com.google.gson.reflect.TypeToken;
import com.itcast.googleplay.R;
import com.itcast.googleplay.adapter.CategoryAdapter;
import com.itcast.googleplay.bean.Category;
import com.itcast.googleplay.bean.CategoryInfo;
import com.itcast.googleplay.http.Api;
import com.itcast.googleplay.http.HttpUtils;
import com.itcast.googleplay.utils.CommonUtil;
import com.itcast.googleplay.utils.JsonUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Lenovo on 2016/7/27.
 */
public class CategoryFragment extends BaseFragment {

    private ArrayList<Object> list = new ArrayList<>();

    private ListView listView;

    @Override
    public View getSuccessView() {
        listView = (ListView) View.inflate(getActivity(), R.layout.listview, null);

        return listView;
    }

    @Override
    public Object requestData() {

        String json = HttpUtils.get(Api.CATEGORY);

        ArrayList<Category> categories =
                (ArrayList<Category>) JsonUtil.parseJsonToList(json, new TypeToken<List<Category>>() {
                }.getType());

        if (categories != null) {
            for (Category category : categories) {
                list.add(category.getTitle());
                ArrayList<CategoryInfo> infos = category.getInfos();
                list.addAll(infos);
            }
        }
        //**刷新UI
        CommonUtil.runOnUIThread(new Runnable() {
            @Override
            public void run() {
                listView.setAdapter(new CategoryAdapter(list));
            }
        });

        return list;
    }
}
