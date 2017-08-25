package com.itcast.googleplay.ui.fragment;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;

import com.itcast.googleplay.R;
import com.itcast.googleplay.utils.CommonUtil;

/**
 * 这个布局是所有布局中所共有的,只是可以通显示与不显示来控制
 * <p/>
 * 八.由于每个界面加载数据都有相同的逻辑(价值：￥2000)：
 * 细分状态，每种状态下显示对应的View,所以抽取类LoadingPage;
 * 1.定义3种状态常量:加载中,加载失败，加载成功;
 * 2.让LoadingPage继承FrameLayout(其他布局也是可以的)，原因有2个：
 * $$$$$a.这样就可以往LoadingPage中添加子View了，从而可以去控制子View的显示与隐藏
 * $$$$$b.这样就可以作为Fragment的View返回了，
 * 3.在构造方法中给LoadingPage添加3种状态对应的View对象;(构造方法,最先执行)
 * 4.增加showPage方法，根据mState，控制对应的View显示;
 * 5.增加loadDataAndRefreshPage()方法，去服务器请求数据，并且根据回来的数据去刷新Page;
 */
public abstract class LoadingPager extends FrameLayout {

    private static final int STATE_LOADING = 1;
    private static final int STATE_ERROE = 2;
    private static final int STATE_SUCCESS = 3;

    private int mState = STATE_LOADING;//默认


    private View loadingView;
    private View errorView;
    private View successView;

    public LoadingPager(Context context) {
        super(context);
        initLoadingPage();
    }

    public LoadingPager(Context context, AttributeSet attrs) {
        super(context, attrs);
        initLoadingPage();
    }

    public LoadingPager(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initLoadingPage();
    }

    /**
     * 应该是帧布局,而且都应该是三个布局
     */
    public void initLoadingPage() {
        /**
         * 为了尽可能的保证填充布局,使用参数再一步的设置.
         */
        LayoutParams layoutParams = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);

        if (loadingView == null) {
            loadingView = View.inflate(getContext(), R.layout.page_loading, null);
        }
        addView(loadingView, layoutParams); //进度条而已,每一个根布局中都有add方法的.
        /**
         * 增加的加载错误的布局,但是其中还是有逻辑的,需要实现
         */
        if (errorView == null) {
            errorView = View.inflate(getContext(), R.layout.page_error, null);
            Button button = (Button) errorView.findViewById(R.id.btn_reload);
            button.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View view) {
                    mState = STATE_LOADING;
                    showPage();
                    loadDataAndRefreshPage();   //需要先改页面效果再去加载,用户体验比较好
                }
            });
        }
        addView(errorView, layoutParams);
        /**
         * 添加不同的成功布局,但是需要进行判断
         */
        if (successView == null) {
            successView = createSuccessView();
        }
        if (successView == null) {
            throw new IllegalArgumentException("the method createSuccessView() can return null");
        }
        addView(successView, layoutParams);
        /**
         * 显示界面,并且去服务器请求数据，并且根据回来的数据去刷新Page
         */
        showPage();
        loadDataAndRefreshPage();


    }

    /**
     * 请求成功后的界面也是每一个都不相同的
     *
     * @return
     */
    protected abstract View createSuccessView();

    /**
     * 页面的展示,根据状态来显示
     */
    private void showPage() {

        loadingView.setVisibility(View.INVISIBLE);
        errorView.setVisibility(View.INVISIBLE);
        successView.setVisibility(View.INVISIBLE);

        switch (mState) {
            case STATE_LOADING:
                loadingView.setVisibility(View.VISIBLE);
                break;
            case STATE_ERROE:
                errorView.setVisibility(View.VISIBLE);
                break;
            case STATE_SUCCESS:
                successView.setVisibility(View.VISIBLE);
                break;
            default:

                break;
        }
    }

    /**
     * 请求数据并且刷新页面,子线程的请求主线程的刷新,是根据回来的数据显示的页面
     */
    public void loadDataAndRefreshPage() {
        new Thread(new Runnable() {            //子线程
            @Override
            public void run() {
                Object data = loadData();
                mState = data == null ? STATE_ERROE : STATE_SUCCESS;
                CommonUtil.runOnUIThread(new Runnable() {  //$$$回主线程.这一部分的逻辑必须是如此的顺序,必须先数据后刷新
                    @Override
                    public void run() {
                        showPage();     //带着改变后的状态,去刷新的界面.
                    }
                });
            }
        }).start();
    }

    /**
     * 请求数据,每一个的数据是不相同的,需要由子类来实现
     * 去服务器请求数据，由于我不关心具体的数据类型，只需判断数据是否为空来显示相应的页面,因为是父类站的比较高,是一种抽象方法
     *
     * @return
     */
    public abstract Object loadData();

}
