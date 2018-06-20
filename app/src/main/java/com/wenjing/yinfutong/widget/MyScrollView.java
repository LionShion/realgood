package com.wenjing.yinfutong.widget;

import android.content.Context;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ScrollView;

import com.wenjing.yinfutong.interf.AlphaChangeListener;


/**
 * Created by wenjing on 2017/2/24.
 */

public class MyScrollView extends ScrollView {

    AlphaChangeListener alphaChangeListener;

    private float xLast, yLast, xDistance, yDistance;

    // ScrollView的子View， 也是ScrollView的唯一一个子View
    private View contentView;

    // 用于记录正常的布局位置
    private Rect originalRect = new Rect();

    public MyScrollView(Context context) {
        super(context);
    }

    public MyScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        if (getChildCount() > 0) {
            contentView = getChildAt(0);
        }
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);


        if (contentView == null)
            return;

        // ScrollView中的唯一子控件的位置信息, 这个位置信息在整个控件的生命周期中保持不变
        originalRect.set(contentView.getLeft(), contentView.getTop(),
                contentView.getRight(), contentView.getBottom());
    }

    /**
     * 在这里解决滑动上下滑动，左右滑动冲突
     *
     * @param ev
     * @return
     */
    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                xDistance = yDistance = 0f;
                xLast = ev.getX();
                yLast = ev.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                final float curX = ev.getX();
                final float curY = ev.getY();

                xDistance += Math.abs(curX - xLast);
                yDistance += Math.abs(curY - yLast);
                xLast = curX;
                yLast = curY;

                if (xDistance > yDistance+10) {
                    return false;   //表示向下传递事件
                }
        }

        return super.onInterceptTouchEvent(ev);
    }

    public void setAlphaChangeListener(AlphaChangeListener alphaChangeListener) {
        this.alphaChangeListener = alphaChangeListener;
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
        if (alphaChangeListener != null) {
            int scrollY = getScrollY();
            int screen_height = getContext().getResources().getDisplayMetrics().heightPixels;
            if (scrollY <= screen_height / 3f) {//0~1f,而透明度应该是1~0f
                alphaChangeListener.alphaChanging( scrollY / (screen_height / 3f));//alpha=滑出去的高度/(screen_height/3f)}
            }
        }
    }
}