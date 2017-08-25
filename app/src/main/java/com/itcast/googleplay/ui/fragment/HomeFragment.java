package com.itcast.googleplay.ui.fragment;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import com.itcast.googleplay.AppDetailActivity;
import com.itcast.googleplay.R;
import com.itcast.googleplay.adapter.BaseicAdapter;
import com.itcast.googleplay.adapter.HomeAdapter;
import com.itcast.googleplay.adapter.HomeHeaderAdapter;
import com.itcast.googleplay.bean.AppInfo;
import com.itcast.googleplay.bean.Home;
import com.itcast.googleplay.global.GooglePlayApplication;
import com.itcast.googleplay.http.Api;
import com.itcast.googleplay.http.HttpUtils;
import com.itcast.googleplay.utils.CommonUtil;
import com.itcast.googleplay.utils.JsonUtil;

/**
 * 先extends fragment最后由几个相同的界面(包含有listView的)进行抽取到BasicListFragment
 * Created by Lenovo on 2016/7/27.
 */
public class HomeFragment extends BasicListFragment<AppInfo> {


    private ViewPager mViewPager;
    /**
     * 自己给自己发送消息
     */
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            //super.handleMessage(msg);
            mViewPager.setCurrentItem(mViewPager.getCurrentItem() + 1);
            handler.sendEmptyMessageDelayed(0, 2000);
        }
    };

    /**
     * 实现父类的适配器方法
     *
     * @return
     */
    @Override
    protected BaseicAdapter getAdapter() {
        return new HomeAdapter(list);
    }

    /**
     * $$$$$$增加头布局,就是轮播图,一般最好是加上注解,防止出现不可预估的问题
     */
    @Override
    public void addHeader() {
        View headerView = View.inflate(GooglePlayApplication.context, R.layout.layout_home_heder, null);
        mViewPager = (ViewPager) headerView.findViewById(R.id.viewpager);

        //$$$$屏幕的适配,由图片的高宽比得出比例关系,设置参数.
        int width = getActivity().getWindowManager().getDefaultDisplay().getWidth();//获取屏幕的宽度
        float height = width / 2.65f;//这是根据图片的宽高比得到的比例值
        ViewGroup.LayoutParams layoutParams = mViewPager.getLayoutParams();//不知道使用什么就使用viewgroup
        layoutParams.height = (int) height;
        mViewPager.setLayoutParams(layoutParams);

        listView.addHeaderView(headerView);     //增加的是整个布局并不是单个viewPager
    }

    @Override
    public Object requestData() {
        //**只有是在第一页才会有图片数据的,后边是没有的
        checkPullFromStart();
        //***size就是从多少处开始加载的
        String json = HttpUtils.get(Api.HOME + list.size());
        //LogUtil.e("数据你好", json);    服务器问题,数据请求在这里打印出来就是},是有问题的.使用e级别是为了好辨认.
        /**
         *%%%%看接口文档,最佳的做法就是将返回数据打印出来,并且和接口的文档进行比对然后再解析数据,不对的话及时找后台的人员
         * 最好的就是打印出来之后自己写bean对象,可以生成之后进行精简,找出自己需要的东西
         * bean对象打头可能是对象也可能是集合.在解析的时候注意[]是集合,而{}是对象(可以继续创建内部类).没加""可不是String
         */
        final Home home = JsonUtil.parseJsonToBean(json, Home.class);

        if (home != null) {
            //直接的添加
            list.addAll(home.getList());

            CommonUtil.runOnUIThread(new Runnable() {
                @Override
                public void run() {

                    //模拟访问网络的时长
                    try {
                        Thread.sleep(1500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    //***首页中第一次加载成功有数据之后才能设置适配器的
                    if (home.getPicture() != null && home.getPicture().size() > 0) {
                        mViewPager.setAdapter(new HomeHeaderAdapter(home.getPicture()));
                        mViewPager.setCurrentItem(home.getPicture().size() * 10 * 1000 / 2);
                    }
                    //***设置适配器,集合数据源的改变都应该是通知适配器
                    baseicAdapter.notifyDataSetChanged();
                    //***调用杀心完成的方法
                    refreshListView.onRefreshComplete();
                }
            });
        }
        //返回对象,才是请求到的数据
        return home;
    }

    /**
     * 创建的时候进行发送消息
     *
     * @param
     */
    @Override
    public void onStart() {
        super.onStart();
        handler.sendEmptyMessageDelayed(0, 2000);
    }


    /**
     * 页面不可见的时候你就停止,回来的时候还是停留在那个地方的
     */
    @Override
    public void onStop() {
        super.onStop();
        handler.removeMessages(0);
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
        //super.onItemClick(adapterView, view, i, l);
        Intent intent = new Intent(getActivity(), AppDetailActivity.class);
        intent.putExtra("packageName", list.get(position - 2).getPackageName());
        startActivity(intent);
    }
}
