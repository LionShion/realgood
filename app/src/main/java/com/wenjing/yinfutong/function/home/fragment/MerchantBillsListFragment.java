package com.wenjing.yinfutong.function.home.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.qmuiteam.qmui.alpha.QMUIAlphaImageButton;
import com.qmuiteam.qmui.widget.QMUITopBarLayout;
import com.wenjing.yinfutong.AppContext;
import com.wenjing.yinfutong.R;
import com.wenjing.yinfutong.base.BaseActivity;
import com.wenjing.yinfutong.base.BaseFragment;
import com.wenjing.yinfutong.common.UIHelper;
import com.wenjing.yinfutong.function.home.adapter.MerchantBillsListAdapter;
import com.wenjing.yinfutong.interf.EndLessOnScrollListener;
import com.wenjing.yinfutong.model.HistoryBillsBean;
import com.wenjing.yinfutong.model.MerchantBillsListBean;
import com.wenjing.yinfutong.model.body.MerchantBillsListRequestBody;
import com.wenjing.yinfutong.retrofit.compose.RxSchedulers;
import com.wenjing.yinfutong.retrofit.rxresult.RxResultHelper;
import com.wenjing.yinfutong.utils.DecimalFormatUtil;
import com.wenjing.yinfutong.view.EmptyRecyclerView;
import com.zhy.adapter.recyclerview.MultiItemTypeAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * Created by wenjing on 2018/3/27.
 */

public class MerchantBillsListFragment extends BaseFragment {

    @BindView(R.id.tv_money)
    TextView tvMoney;
    @BindView(R.id.ll_background)
    LinearLayout llBackground;
    @BindView(R.id.tv_limit)
    TextView tvLimit;
    @BindView(R.id.rv_recyclerview)
    EmptyRecyclerView mRecyclerView;
    @BindView(R.id.iv_error)
    ImageView ivError;
    @BindView(R.id.iv_empty)
    ImageView ivEmpty;

    private MerchantBillsListAdapter mAdapter;
    private List<MerchantBillsListBean.ListBean> infoList = new ArrayList<>();
    private HistoryBillsBean.ListBean listBean;
    /**
     * 当前页面的下标
     */
    private int pageIndex = 0;
    private int totalPage;
    private LinearLayoutManager mLinearLayoutManager;
    private Handler handler = new Handler();
    private int type, buttonType;


    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_merchant_bills_list;
    }

    @Override
    protected void initView(View view) {
        getBundle();
        setTopBarRight();
        initData();
        initRecyclerView();
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


    private void getBundle() {
        Bundle bundle = getArguments();
        listBean = bundle.getParcelable("listBean");
        type = bundle.getInt("type");
        buttonType = bundle.getInt("buttonType");
    }

    private void setTopBarRight() {
        BaseActivity activity = (BaseActivity) getCurActivity();
        if (listBean != null) {
            switch (type) {
                case 1:
                    activity.setActionBarTitle(listBean.getDate());
                    break;
                case 2:
                    activity.setActionBarTitle(listBean.getMonth());
                    break;
            }
        } else {
            activity.setActionBarTitle(getResources().getString(R.string.today_bill));
        }
        QMUITopBarLayout layout = activity.getmQmuiTopBarlayout();
        layout.removeAllLeftViews();
        layout.addLeftImageButton(R.mipmap.icon_back, -1)
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        getCurActivity().finish();
                    }
                });

        setRightTextButton(activity);

        activity.setTitleColor(getResources().getColor(R.color.white));
        activity.getStatusBarHelper().setStatusBarDarkMode(getCurActivity());
    }

    private void setRightTextButton(BaseActivity activity) {


        QMUITopBarLayout layout = activity.getmQmuiTopBarlayout();
        layout.setBackgroundColor(getResources().getColor(R.color.green_00bc9c));

        if (buttonType == 0) {
            QMUIAlphaImageButton imageButton = layout.addRightImageButton(R.mipmap.service_white, -1);
            imageButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    AppContext.showToast(getResources().getString(R.string.customer_service));
                }
            });


        } else {
            Button button = layout.addRightTextButton(R.string.history_bill, -1);
            button.setTextColor(getResources().getColor(R.color.white));
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    UIHelper.showHistoryBill(getContext());

                }
            });
        }

    }

    private void initData() {
        String date;
        String month;
        if (listBean == null) {
            date = "";
            month = "";
        } else {
            date = listBean.getDate();
            month = listBean.getMonth();
        }


        int merchantId = AppContext.instance().getLoginUser().getMerchantId();
        MerchantBillsListRequestBody body = new MerchantBillsListRequestBody(date, month, String.valueOf(type), merchantId, AppContext.getLangulage(), pageIndex);

        showDialog();
        sHomeApi.getMerchantBillsList(body)
                .compose(RxResultHelper.<MerchantBillsListBean>handleRespose())
                .compose(RxSchedulers.<MerchantBillsListBean>applyObservableAsync())
                .subscribe(new Observer<MerchantBillsListBean>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(@NonNull MerchantBillsListBean bean) {
                        getmTipDialog().dismiss();
                        MerchantBillsListBean data = bean;
                        int count = data.getCount();
                        totalPage = data.getTotalPage();
                        int sumAmount = data.getSumAmount();
                        tvLimit.setText(String.valueOf(count));

                        String money = DecimalFormatUtil.defFormat(sumAmount);
                        tvMoney.setText(money);

                        List<MerchantBillsListBean.ListBean> list = data.getList();
                        if (list.size() == 0) {
                            mRecyclerView.setEmptyView(ivEmpty);

                        } else {
                            for (MerchantBillsListBean.ListBean listBean : list) {
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
        mAdapter = new MerchantBillsListAdapter(getContext(), infoList);

        mRecyclerView.setAdapter(mAdapter);

        mAdapter.setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, RecyclerView.ViewHolder holder, int position) {
                Bundle bundle = new Bundle();
                MerchantBillsListBean.ListBean listBean = infoList.get(position);
                String orderNo = listBean.getOrderNo();
                int orderType = listBean.getOrderType();
                bundle.putString("orderNo", orderNo);
                bundle.putInt("orderType", orderType);
                UIHelper.showMerchantBillDetails(getContext(), bundle);
            }

            @Override
            public boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, int position) {
                return false;
            }
        });
    }


}
