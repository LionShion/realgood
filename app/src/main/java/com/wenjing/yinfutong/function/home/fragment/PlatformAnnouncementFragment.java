package com.wenjing.yinfutong.function.home.fragment;

import android.os.Handler;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.wenjing.yinfutong.AppContext;
import com.wenjing.yinfutong.R;
import com.wenjing.yinfutong.base.BaseFragment;
import com.wenjing.yinfutong.common.UIHelper;
import com.wenjing.yinfutong.function.home.adapter.PlatformAnnouncementAdapter;
import com.wenjing.yinfutong.interf.EndLessOnScrollListener;
import com.wenjing.yinfutong.model.NoticePageBean;
import com.wenjing.yinfutong.model.body.NoticePageRequestBody;
import com.wenjing.yinfutong.retrofit.compose.RxSchedulers;
import com.wenjing.yinfutong.retrofit.rxresult.RxResultHelper;
import com.wenjing.yinfutong.view.EmptyRecyclerView;
import com.zhy.adapter.recyclerview.MultiItemTypeAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * Created by wenjing on 2018/3/20.
 */

public class PlatformAnnouncementFragment extends BaseFragment {

    @BindView(R.id.rv_recyclerView)
    EmptyRecyclerView mRecyclerView;
    @BindView(R.id.iv_empty)
    ImageView ivEmpty;
    @BindView(R.id.iv_error)
    ImageView ivError;

    private ArrayList<NoticePageBean.ListBean> infoList = new ArrayList<>();
    private PlatformAnnouncementAdapter mAdapter;

    private Handler handler = new Handler();
    private LinearLayoutManager mLinearLayoutManager;

    /**
     * 当前页面的下标
     */
    private int pageIndex = 0;
    private int totalPage;

    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_platform_announcement;
    }

    @Override
    protected void initView(View view) {
        initRecyclerView();
        initData();
        initPullLoad();
    }

    private void initPullLoad() {

        mRecyclerView.addOnScrollListener(new EndLessOnScrollListener(mLinearLayoutManager) {
            @Override
            public void onLoadMore(int currentPage) {


                if (pageIndex < totalPage) {
                    if (pageIndex == totalPage - 1) {
                        return;
                    } else {
                        handler.postDelayed(new Runnable() {
                            public void run() {
                                pageIndex++;
                                //请求网络
                                initData();
                                mAdapter.notifyDataSetChanged();
                            }
                        }, 500);

                    }

                }
            }
        });
    }

    private void initData() {
        int roleFlag = AppContext.instance().getLoginUser().getRoleFlag();

        NoticePageRequestBody body = new NoticePageRequestBody(pageIndex, roleFlag, AppContext.getLangulage());
        sHomeApi.getNoticePage(body)
                .compose(RxResultHelper.<NoticePageBean>handleRespose())
                .compose(RxSchedulers.<NoticePageBean>applyObservableAsync())
                .subscribe(new Observer<NoticePageBean>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(NoticePageBean bean) {
                        totalPage = bean.getTotalPage();
                        List<NoticePageBean.ListBean> list = bean.getList();
                        for (NoticePageBean.ListBean listBean : list) {
                            infoList.add(listBean);
                        }
                        mRecyclerView.setAdapter(mAdapter);

                    }

                    @Override
                    public void onError(Throwable e) {
                        AppContext.showToast(e.toString());

                    }

                    @Override
                    public void onComplete() {

                    }

                });


    }

    private void initRecyclerView() {
        mLinearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(mLinearLayoutManager);
        mAdapter = new PlatformAnnouncementAdapter(getContext(), infoList);
        mRecyclerView.setAdapter(mAdapter);

        mAdapter.setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, RecyclerView.ViewHolder holder, int position) {
                NoticePageBean.ListBean noticeBean = mAdapter.getDatas().get(position);
                String linkUrl = noticeBean.getLinkUrl();
                String title = noticeBean.getTitle();

                UIHelper.showSingleWebView(getContext(), linkUrl, title);
            }

            @Override
            public boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, int position) {
                return false;
            }
        });


    }

}
