package com.wenjing.yinfutong.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.annotation.DrawableRes;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.wenjing.yinfutong.R;


/**
 * Created by Administrator on 2017/9/20 0020.
 */

public class LinearItemDecoration extends RecyclerView.ItemDecoration{
    private static final int [] ATTRS = new int[]{android.R.attr.listDivider};

    private static final int Horizontal_List = LinearLayoutManager.HORIZONTAL;
    private static final int Vertical_List = LinearLayoutManager.VERTICAL;

    private Drawable mDivider;

    private int mOrientation;


    private int mDividerHeight = 2;//分割线高度,默认为1px
    private Paint mPaint;

    public LinearItemDecoration(Context context,int orientation) {
        TypedArray typedArray = context.obtainStyledAttributes(ATTRS);
        mDivider = typedArray.getDrawable(0);
        typedArray.recycle();//用完回收
        setOrientation(orientation);
        initPaint(context);
    }

    public LinearItemDecoration(Context context,int orientation,@DrawableRes int dividerId) {
        mDivider = ContextCompat.getDrawable(context,dividerId);
        mDividerHeight = mDivider.getIntrinsicHeight();
        setOrientation(orientation);
        initPaint(context);
    }

    private void initPaint(Context context) {
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setColor(ContextCompat.getColor(context, R.color.all_bg));
        mPaint.setStyle(Paint.Style.FILL);
    }

    public void setOrientation(int orientation) {
        if(orientation != Horizontal_List && orientation != Vertical_List){
            throw new IllegalArgumentException("invalid orientation");
        }
        this.mOrientation = mOrientation;
    }


    @Override
    public void onDraw(Canvas c, RecyclerView parent) {
        super.onDraw(c, parent);
        if(mOrientation == Vertical_List){
            drawHorizontal(c,parent);//画横线
        }else {
            drawVertical(c,parent);//画纵线
        }
    }

    private void drawVertical(Canvas c, RecyclerView parent) {
        int left = parent.getPaddingLeft();
        int right = parent.getWidth() - parent.getPaddingRight();

        int childCount = parent.getChildCount();

        for(int i=0 ; i<childCount ; i++){
            View child = parent.getChildAt(i);
            RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child.getLayoutParams();
            int top = child.getBottom() + params.bottomMargin;
            int bottom = top + mDividerHeight;

            mDivider.setBounds(left,top,right,bottom);
            mDivider.draw(c);

            if(mPaint != null){
                c.drawRect(left,top,right,bottom,mPaint);
            }
        }
    }

    /**
     *
     * @param c
     * @param parent
     */
    private void drawHorizontal(Canvas c, RecyclerView parent) {
        int top = parent.getPaddingTop();
        int bottom = parent.getHeight() - parent.getPaddingBottom();

        int childCount = parent.getChildCount();
        for(int i = 0 ; i<childCount ; i++){
            View child = parent.getChildAt(i);
            RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child.getLayoutParams();

            int left = child.getRight() + params.rightMargin;
            int right = left + mDividerHeight;
            mDivider.setBounds(left,top,right,bottom);
            mDivider.draw(c);

            if(mPaint != null){
                c.drawRect(left,top,right,bottom,mPaint);
            }
        }
    }

//    @Override
//    public void getItemOffsets(Rect outRect, int itemPosition, RecyclerView parent) {
//        super.getItemOffsets(outRect, itemPosition, parent);
//        if(mOrientation == Vertical_List){
//            outRect.set(0,0,0,mDivider.getIntrinsicHeight());
//        }else{
//            outRect.set(0,0,mDivider.getIntrinsicWidth(),0);
//        }
//    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
        outRect.set(0,0,0,mDividerHeight);
    }
}
