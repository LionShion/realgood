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
import com.wenjing.yinfutong.function.home.adapter.TradeMessageAdapter;
import com.wenjing.yinfutong.interf.EndLessOnScrollListener;
import com.wenjing.yinfutong.model.TradeMessageBean;
import com.wenjing.yinfutong.model.body.TradeMessageRequestBody;
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

public class TradeInformationFragment extends BaseFragment {
    @BindView(R.id.rv_recyclerView)
    EmptyRecyclerView mRecyclerView;
    @BindView(R.id.iv_empty)
    ImageView ivEmpty;
    @BindView(R.id.iv_error)
    ImageView ivError;

    private ArrayList<TradeMessageBean.MessageBean> infoList = new ArrayList<>();
    private TradeMessageAdapter mAdapter;

    private Handler handler = new Handler();
    private LinearLayoutManager mLinearLayoutManager;

    /**
     * 当前页面的下标
     */
    private int pageIndex = 0;
    private int totalPage;


    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_trade_information;
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

        TradeMessageRequestBody body = new TradeMessageRequestBody(AppContext.getLangulage(), pageIndex);

        showDialog();
        sHomeApi.getTradeMessage(body)
                .compose(RxResultHelper.<TradeMessageBean>handleRespose())
                .compose(RxSchedulers.<TradeMessageBean>applyObservableAsync())
                .subscribe(new Observer<TradeMessageBean>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(TradeMessageBean bean) {
                        getmTipDialog().dismiss();
                        totalPage = bean.getTotalPage();
                        List<TradeMessageBean.MessageBean> message = bean.getMessage();
                        for (TradeMessageBean.MessageBean messageBean : message) {
                            infoList.add(messageBean);
                        }
                        mRecyclerView.setAdapter(mAdapter);

                    }

                    @Override
                    public void onError(Throwable e) {
                        getmTipDialog().dismiss();
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
        mAdapter = new TradeMessageAdapter(getContext(), infoList);
        mRecyclerView.setAdapter(mAdapter);

        mAdapter.setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, RecyclerView.ViewHolder holder, int position) {
                TradeMessageBean.MessageBean messageBean = mAdapter.getDatas().get(position);
                int category = messageBean.getCategory();
                String orderNo = messageBean.getOrderNo();

                Bundle bundle = new Bundle();
                bundle.putString("orderNo", orderNo);

                switch (category) {
                    case 1://支付
                        bundle.putInt("orderType", category);

                        UIHelper.showPersonalPayBillsDetails(getContext(), bundle);

                        break;
                    case 2://充值
                        bundle.putInt("orderType", category);
                        UIHelper.showPersonalBillsDetails(getContext(), bundle);

                        break;
                    case 3://提现
                        bundle.putInt("orderType", category);
                        UIHelper.showPersonalWithdrawBillsDetails(getContext(), bundle);

                        break;
                    case 4:
                        bundle.putInt("orderType", category);
                        UIHelper.showMerchantBillDetails(getContext(), bundle);



                        break;
                }

            }

            @Override
            public boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, int position) {
                return false;
            }
        });


    }

}
