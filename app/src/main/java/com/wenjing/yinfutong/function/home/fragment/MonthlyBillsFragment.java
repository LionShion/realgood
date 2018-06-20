package com.wenjing.yinfutong.function.home.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.wenjing.yinfutong.AppContext;
import com.wenjing.yinfutong.R;
import com.wenjing.yinfutong.base.BaseFragment;
import com.wenjing.yinfutong.common.UIHelper;
import com.wenjing.yinfutong.function.RequestCode;
import com.wenjing.yinfutong.function.home.adapter.HistoryBillsAdapter;
import com.wenjing.yinfutong.interf.EndLessOnScrollListener;
import com.wenjing.yinfutong.model.HistoryBillsBean;
import com.wenjing.yinfutong.model.body.MerchantBillsRequestBody;
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
 * Created by wenjing on 2018/3/21.
 */

public class MonthlyBillsFragment extends BaseFragment {

    @BindView(R.id.rv_recyclerview)
    EmptyRecyclerView mRecyclerView;
    @BindView(R.id.iv_error)
    ImageView ivError;
    @BindView(R.id.iv_empty)
    ImageView ivEmpty;


    private List<HistoryBillsBean.ListBean> infoList = new ArrayList<>();
    private HistoryBillsAdapter mAdapter;

    /**
     * 当前页面的下标
     */
    private int pageIndex = 0;
    private int totalPage;
    private Handler handler = new Handler();
    private int type;
    private LinearLayoutManager mLinearLayoutManager;


    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_monthly_bills;
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
        int merchantId = AppContext.instance().getLoginUser().getMerchantId();
        MerchantBillsRequestBody body = new MerchantBillsRequestBody(merchantId, RequestCode.REGISTER_LANGUAGE, pageIndex);

        showDialog();
        sHomeApi.getMonthBills(body)
                .compose(RxResultHelper.<HistoryBillsBean>handleRespose())
                .compose(RxSchedulers.<HistoryBillsBean>applyObservableAsync())
                .subscribe(new Observer<HistoryBillsBean>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(HistoryBillsBean bean) {
                        getmTipDialog().dismiss();
                        List<HistoryBillsBean.ListBean> list = bean.getList();
                        HistoryBillsBean data = bean;
                        type = data.getType();
                        totalPage = bean.getTotalPage();

                        if (list.size() <= 0) {

                            mRecyclerView.setEmptyView(ivEmpty);
                        } else {

                            for (HistoryBillsBean.ListBean listBean : list) {
                                infoList.add(listBean);
                            }
                            mRecyclerView.setAdapter(mAdapter);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        getmTipDialog().dismiss();
                        AppContext.showToast(e.toString());
                        mRecyclerView.setEmptyView(ivError);
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    private void initRecyclerView() {

        mLinearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(mLinearLayoutManager);
        mAdapter = new HistoryBillsAdapter(getContext(), infoList);
        mRecyclerView.setAdapter(mAdapter);


        mAdapter.setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, RecyclerView.ViewHolder holder, int position) {

                HistoryBillsBean.ListBean listBean = mAdapter.getDatas().get(position);
                Bundle bundle = new Bundle();
                bundle.putParcelable("listBean", listBean);
                bundle.putInt("type", type);
                bundle.putInt("buttonType", 0);

                UIHelper.showMerchantBillsList(getContext(), bundle);


            }

            @Override
            public boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, int position) {
                return false;
            }
        });

    }

}
