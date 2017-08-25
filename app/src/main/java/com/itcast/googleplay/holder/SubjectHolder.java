package com.itcast.googleplay.holder;

import android.view.View;
import android.widget.TextView;

import com.itcast.googleplay.R;
import com.itcast.googleplay.bean.Subject;
import com.itcast.googleplay.global.GooglePlayApplication;
import com.itcast.googleplay.http.Api;
import com.itcast.googleplay.ui.widght.RatioImageView;

import it.sephiroth.android.library.picasso.Picasso;

/**
 * holder在这里边就是界面和数据的展现.
 * Created by Lenovo on 2016/7/29.
 */
public class SubjectHolder extends BaseHolder<Subject> {

    private RatioImageView imageView1;
    private TextView textView;

    @Override
    protected View initHolderView() {
        View view = View.inflate(GooglePlayApplication.context, R.layout.adapter_subject, null);
        imageView1 = (RatioImageView) view.findViewById(R.id.iv_image);
        textView = (TextView) view.findViewById(R.id.tv_des);
        return view;
    }

    @Override
    public void bindData(Subject subject) {

        textView.setText(subject.getDes());

        Picasso.with(GooglePlayApplication.context).
                load(Api.IMAGE_PREFIX + subject.getUrl()).
//                centerCrop().
//                resize(200, 200).
        into(imageView1);
//        imageView.setImageResource(R.drawable.ic_launcher);

    }

}