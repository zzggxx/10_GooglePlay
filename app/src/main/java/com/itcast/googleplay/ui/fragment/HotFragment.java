package com.itcast.googleplay.ui.fragment;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.view.Gravity;
import android.view.View;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.reflect.TypeToken;
import com.itcast.googleplay.http.Api;
import com.itcast.googleplay.http.HttpUtils;
import com.itcast.googleplay.ui.widght.FlowLayout;
import com.itcast.googleplay.utils.ColorUtil;
import com.itcast.googleplay.utils.CommonUtil;
import com.itcast.googleplay.utils.DensityUtil;
import com.itcast.googleplay.utils.DrawableUtil;
import com.itcast.googleplay.utils.JsonUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Lenovo on 2016/7/27.
 */
public class HotFragment extends BaseFragment {
    private ScrollView scrollView;
    private FlowLayout flowLayout;
    private int vPadding, hPadding;

    @Override
    public View getSuccessView() {
        scrollView = new ScrollView(getActivity());
        flowLayout = new FlowLayout(getActivity());

        vPadding = DensityUtil.dip2px(getActivity(), 6);
        hPadding = DensityUtil.dip2px(getActivity(), 9);

        int padding = DensityUtil.dip2px(getActivity(), 15);
        flowLayout.setPadding(padding, padding, padding, padding);

        flowLayout.setHorizontalSpacing(padding);
        flowLayout.setVerticalSpacing(padding);

        scrollView.addView(flowLayout);

        return scrollView;
    }

    @Override
    public Object requestData() {
        String json = HttpUtils.get(Api.HOT);
        final ArrayList<String> list = (ArrayList<String>) JsonUtil.parseJsonToList(json, new TypeToken<List<String>>() {
        }.getType());


        if (list != null) {
            CommonUtil.runOnUIThread(new Runnable() {
                @Override
                public void run() {
                    for (int i = 0; i < list.size(); i++) {
                        final TextView textView = new TextView(getActivity());
                        textView.setTextSize(16);
                        textView.setGravity(Gravity.CENTER);
                        textView.setText(list.get(i));
                        textView.setTextColor(Color.WHITE);
                        textView.setPadding(hPadding, vPadding, hPadding, vPadding);

                        Drawable pressed = DrawableUtil.generateDrawable(ColorUtil.randomColor(), hPadding);
                        Drawable normal = DrawableUtil.generateDrawable(ColorUtil.randomColor(), hPadding);
                        textView.setBackgroundDrawable(DrawableUtil.generateSelector(pressed, normal));

                        flowLayout.addView(textView);

                        textView.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                Toast.makeText(getActivity(), textView.getText().toString(), Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                }
            });
        }

        return list;
    }
}
