package com.itcast.googleplay.ui.widght;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

/**
 * 定义强大的流式布局类,是一个自定义的控件
 * Created by Lenovo on 2016/8/1.
 */
public class FlowLayout extends ViewGroup {

    //声明宽高并且设置宽高
    private int horizontalSpacing = 15; //字块之间的水平间距
    private int verticalSpacing = 15; //行与行之间的垂直间距

    // 可让外界进行自定义间距
    public void setHorizontalSpacing(int horizontalSpacing) {
        this.horizontalSpacing = horizontalSpacing;
    }

    public void setVerticalSpacing(int verticalSpacing) {
        this.verticalSpacing = verticalSpacing;
    }

    // 线条的集合
    private ArrayList<Line> lineList = new ArrayList<>();


    public FlowLayout(Context context) {
        super(context);
    }

    public FlowLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public FlowLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    /**
     * 分行:遍历所有的子View,判断那几个子View是在同一行中,相当于是一个座位表
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        //super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        //1.获取FlowLayout的宽度
        int width = MeasureSpec.getSize(widthMeasureSpec);
        //2.获取用于实际比较的宽度，就是除去2边的padding的宽度
        int noPaddingWidth = width - getPaddingLeft() - getPaddingRight();
        //3.遍历所有的子View，拿子View的宽和noPaddingWidth进行比较
        Line line = new Line();//准备Line对象
        for (int i = 0; i < getChildCount(); i++) {
            View childView = getChildAt(i);
            //保证能够获取到宽高,保险的起见
            childView.measure(0, 0);
            //4.如果当前line中木有子View，则不用比较直接放入line中，因为要保证每行至少有一个子View;
            if (line.getViewList().size() == 0) {
                line.addLineView(childView);
            } else if (line.getLineWidth() + horizontalSpacing + childView.getMeasuredWidth() > noPaddingWidth) {
                //5.如果当前line的宽+水平间距+子View的宽大于noPaddingWidth,则child需要换行
                //需要先存放好之前的line对象，否则会造成丢失
                lineList.add(line);
                // 创建新的line,并将childview放入到当前的行中.
                line = new Line();
                line.addLineView(childView);
            } else {
                //6.说明当前child应该放入当前Line中
                line.addLineView(childView);
            }
            //7.如果当前child是最后的子View，那么需要保存最后的line对象
            if (i == getChildCount() - 1) {
                lineList.add(line);
            }
        }
        //for循环结束了，lineList存放了所有的Line，而每个Line又记录了自己行所有的VIew;
        //计算FLowLayout需要的高度
        int height = getPaddingTop() + getPaddingBottom();  //上下的padding值
        for (int i = 0; i < lineList.size(); i++) {
            height += lineList.get(i).getLineHeight();    //每条线上的距离
        }
        height += (lineList.size() - 1) * verticalSpacing;//每条线的间距
        //设置当前控件的宽高，或者向父VIew申请宽高
        setMeasuredDimension(width, height);
    }

    /**
     * 去摆放所有的子View，让每个人真正的坐到自己的位置上
     */
    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int paddingLeft = getPaddingLeft();
        int paddingTop = getPaddingTop();
        for (int i = 0; i < lineList.size(); i++) {
            Line line = lineList.get(i);
            // 从第二行开始,每一行的top值总是比上一行的top值多了一个行高和一个top值
            if (i > 0) {
                paddingTop += verticalSpacing + lineList.get(i - 1).getLineHeight();
            }
            // 获取line中的view集合.
            ArrayList<View> viewList = line.getViewList();
            // 1,获取的是每一行的留白值
            int remainSpacing = getLineRemainSpacing(line);
            // 2,计算每一个view得到的平均值.
            int perSpacing = remainSpacing / viewList.size();
            for (int j = 0; j < viewList.size(); j++) {
                View childView = viewList.get(j);
                // 3,将得到的perSpacing值加入到每一行的上面.
                int widthSpec = MeasureSpec.makeMeasureSpec(childView.getMeasuredWidth() + perSpacing, MeasureSpec.EXACTLY);
                childView.measure(widthSpec, 0);
                if (j == 0) {
                    // 如果是每一行的第一个,那么直接的放在最左边即可.
                    childView.layout(paddingLeft, paddingTop,
                            paddingLeft + childView.getMeasuredWidth(), paddingTop + childView.getMeasuredHeight());
                } else {
                    // 如果不是第一个就要得到之前一个的right值.
                    View preView = viewList.get(j - 1);
                    // 当前view就是前一个view加上spacing即可.
                    int left = preView.getRight() + horizontalSpacing;
                    childView.layout(left, preView.getTop(), left + childView.getMeasuredWidth(), preView.getBottom());
                }
            }
        }
    }

    /**
     * 获取指定line的留白
     *
     * @param line
     */
    private int getLineRemainSpacing(Line line) {
        return getMeasuredWidth() - getPaddingLeft() - getPaddingRight() - line.getLineWidth();
    }


    /**
     * 封装每一行的数据,其中包括每一行的所有子vie和行的宽和高
     */
    public class Line {
        // 获取所有的子View
        public ArrayList<View> getViewList() {
            return viewList;
        }

        // 获取的是行宽
        public int getLineWidth() {
            return width;
        }

        // 获取的是行高
        public int getLineHeight() {
            return height;
        }

        private ArrayList<View> viewList;   // 当前行的所有子view
        private int width;      //行的宽度,就是所有子view的宽加上字块之间的间距
        private int height;     //行高

        //初始化在哪里都是可以的,上边的声明里边也是可以的
        public Line() {
            viewList = new ArrayList<View>();
        }

        /**
         * 记录子view
         *
         * @param child
         */
        public void addLineView(View child) {
            if (!viewList.contains(child)) {
                viewList.add(child);
                // 更新行的宽和高.
                if (viewList.size() == 1) {
                    width = child.getMeasuredWidth();
                } else {
                    width += child.getMeasuredWidth() + horizontalSpacing;
                }
                height = Math.max(height, child.getMeasuredHeight());
            }
        }
    }
}
