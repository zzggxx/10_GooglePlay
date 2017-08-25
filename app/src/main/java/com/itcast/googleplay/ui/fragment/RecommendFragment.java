package com.itcast.googleplay.ui.fragment;

import android.graphics.Color;
import android.view.View;
import android.widget.TextView;

import com.google.gson.reflect.TypeToken;
import com.itcast.googleplay.http.Api;
import com.itcast.googleplay.http.HttpUtils;
import com.itcast.googleplay.ui.widght.randomlayout.StellarMap;
import com.itcast.googleplay.utils.CommonUtil;
import com.itcast.googleplay.utils.DensityUtil;
import com.itcast.googleplay.utils.JsonUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by Lenovo on 2016/7/27.
 */
public class RecommendFragment extends BaseFragment {
    private ArrayList<String> list;
    private StellarMap stellarMap;

    @Override
    public View getSuccessView() {
        //应用的是第三方的类文件
        stellarMap = new StellarMap(getActivity());
        //内间距,就是边上文字距离屏幕的距离
        int innerpadding = DensityUtil.dip2px(getActivity(), 15);
        stellarMap.setInnerPadding(innerpadding, innerpadding, innerpadding, innerpadding);

        return stellarMap;
    }

    @Override
    public Object requestData() {
        /**
         * 请求的数据类型是String类型,所以是泛型直接的写String
         */
        String json = HttpUtils.get(Api.RECOMMEND);
        list = (ArrayList<String>) JsonUtil.parseJsonToList(
                json, new TypeToken<List<String>>() {
                }.getType());
        if (list != null) {
            CommonUtil.runOnUIThread(new Runnable() {
                @Override
                public void run() {
                    stellarMap.setAdapter(new stellarMapAdapter());
                    stellarMap.setGroup(0, true);
                    stellarMap.setRegularity(12, 12);
                }
            });
        }
        return list;
    }

    /**
     * 三方控件的适配器,也是仿照listView的方式.
     */
    private class stellarMapAdapter implements StellarMap.Adapter {
        @Override
        public int getGroupCount() {
            return list.size() / getCount(0);
        }

        @Override
        public int getCount(int group) {
            return 11;
        }

        @Override
        public View getView(int group, int position, View convertView) {
            TextView textView = new TextView(getActivity());
            //设置文字
            textView.setText(list.get(group * getCount(group) + position));
            //设置大小
            Random random = new Random();
            textView.setTextSize(random.nextInt(10) + 14);
            //设置颜色
            int red = random.nextInt(180);
            int green = random.nextInt(180);
            int blue = random.nextInt(180);
            int rgb = Color.rgb(red, green, blue);
            textView.setTextColor(rgb);
            //textView.setTextColor(Color.parseColor("#333"));  //***使用颜色的编译

            return textView;
        }

        //源码中并没有用到的
        @Override
        public int getNextGroupOnPan(int group, float degree) {
            return 0;
        }

        //下一个播放的界面
        @Override
        public int getNextGroupOnZoom(int group, boolean isZoomIn) {
            return (group + 1) % getGroupCount();
        }
    }
}
