package com.wenjing.yinfutong.view;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;

import com.wenjing.yinfutong.R;
import com.zhy.adapter.recyclerview.wrapper.LoadMoreWrapper;

/**
 * Created by ${luoyingtao} on 2018\4\13 0013.
 */

public class LoadRecyclerView extends FrameLayout{

    private RelativeLayout emptyView,errorView;
    private RecyclerView recyclerView;
    private boolean move;
    private int mIndex;
    private RecyclerView.LayoutManager layoutManager;

    private Context mContext;

    public LoadRecyclerView(@NonNull Context context) {
        super(context);
        initView(context);
    }

    public LoadRecyclerView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    public LoadRecyclerView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    private void initView(Context context){
        mContext=context;
        LayoutInflater.from(mContext).inflate(R.layout.layout_load_recycler,this);
        emptyView = (RelativeLayout)findViewById(R.id.layout_empty);
        errorView = (RelativeLayout)findViewById(R.id.layout_error);
        recyclerView = (RecyclerView)findViewById(R.id.load_recycler);
    }


    public void init(RecyclerView.LayoutManager layoutManager){
        recyclerView.setLayoutManager(layoutManager);

        this.layoutManager = layoutManager;
        recyclerView.addOnScrollListener(new RecyclerViewListener());
    }

    public void showEmpty(OnClickListener onEmptyClick){
        recyclerView.setVisibility(GONE);
        emptyView.setVisibility(VISIBLE);
        errorView.setVisibility(GONE);
        if(onEmptyClick!=null){
            emptyView.setOnClickListener(onEmptyClick);
        }
    }

    public void showError(OnClickListener onErrorClick){
        recyclerView.setVisibility(GONE);
        emptyView.setVisibility(GONE);
        errorView.setVisibility(VISIBLE);
        if(onErrorClick!=null){
            errorView.setOnClickListener(onErrorClick);
        }
    }

    public void showData(){
        recyclerView.setVisibility(VISIBLE);
        emptyView.setVisibility(GONE);
        errorView.setVisibility(GONE);
    }

    public void setEmptyView(View view){
        emptyView.removeAllViews();
        emptyView.addView(view);
    }

    public void setAdapter(RecyclerView.Adapter adapter){
        recyclerView.setAdapter(adapter);
    }

    public void addItemDecoration(RecyclerView.ItemDecoration itemDecoration){
        recyclerView.addItemDecoration(itemDecoration);
    }

    public void moveToPosition(int n) {
        mIndex=n;
        LinearLayoutManager  mLinearLayoutManager=(LinearLayoutManager)layoutManager;
        int firstItem = mLinearLayoutManager.findFirstVisibleItemPosition();
        int lastItem = mLinearLayoutManager.findLastVisibleItemPosition();
        //然后区分情况
        if (n <= firstItem ){
            //当要置顶的项在当前显示的第一个项的前面时
            recyclerView.scrollToPosition(n);
        }else if ( n <= lastItem ){
            //当要置顶的项已经在屏幕上显示时
            int top = recyclerView.getChildAt(n - firstItem).getTop();
            recyclerView.scrollBy(0, top);
        }else{
            //当要置顶的项在当前显示的最后一项的后面时
            recyclerView.scrollToPosition(n);
            //这里这个变量是用在RecyclerView滚动监听里面的
            move = true;
        }

    }

    class RecyclerViewListener extends RecyclerView.OnScrollListener{
        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);
            //在这里进行第二次滚动（最后的100米！）
            if (move ){
                move = false;
                //获取要置顶的项在当前屏幕的位置，mIndex是记录的要置顶项在RecyclerView中的位置
                LinearLayoutManager mLinearLayoutManager=(LinearLayoutManager)layoutManager;
                int n = mIndex - mLinearLayoutManager.findFirstVisibleItemPosition();
                if ( 0 <= n && n < recyclerView.getChildCount()){
                    //获取要置顶的项顶部离RecyclerView顶部的距离
                    int top = recyclerView.getChildAt(n).getTop();
                    //最后的移动
                    recyclerView.scrollBy(0, top);
                }
            }
        }
    }

}
