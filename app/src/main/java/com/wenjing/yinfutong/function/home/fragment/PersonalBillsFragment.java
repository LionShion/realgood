package com.wenjing.yinfutong.function.home.fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.gavin.com.library.PowerfulStickyDecoration;
import com.gavin.com.library.listener.PowerGroupListener;
import com.qmuiteam.qmui.widget.QMUITopBarLayout;
import com.wenjing.yinfutong.AppContext;
import com.wenjing.yinfutong.R;
import com.wenjing.yinfutong.base.BaseActivity;
import com.wenjing.yinfutong.base.BaseFragment;
import com.wenjing.yinfutong.common.UIHelper;
import com.wenjing.yinfutong.function.RequestCode;
import com.wenjing.yinfutong.function.home.adapter.PersonalBillsAdapter;
import com.wenjing.yinfutong.interf.EndLessOnScrollListener;
import com.wenjing.yinfutong.model.PersonalBillsBean;
import com.wenjing.yinfutong.model.body.PersonalBillsRequestBody;
import com.wenjing.yinfutong.retrofit.compose.RxSchedulers;
import com.wenjing.yinfutong.retrofit.rxresult.RxResultHelper;
import com.wenjing.yinfutong.utils.DecimalFormatUtil;
import com.wenjing.yinfutong.utils.DensityUtil;
import com.wenjing.yinfutong.utils.TLog;
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

public class PersonalBillsFragment extends BaseFragment {
    public static final String TAG = PersonalBillsFragment.class.getSimpleName();

    PowerfulStickyDecoration decoration;
    @BindView(R.id.rv_recyclerview)
    EmptyRecyclerView mRecyclerView;
    @BindView(R.id.iv_empty)
    ImageView ivEmpty;
    @BindView(R.id.iv_error)
    ImageView ivError;

    private ArrayList<PersonalBillsBean.ListBean> dataList = new ArrayList<>();
    private PersonalBillsAdapter mAdapter;
    //交易类型：1 - 收入，2 - 支出，3 - 全部
    private int incomeTypeInt = 3;
    //账户类型：1-个人，2-商家，3-全部
    private int payTypeInt = 3;

    private Handler handler = new DataUpdateHandler();
    private LinearLayoutManager mLinearLayoutManager;

    /**
     * 当前页面的下标
     */
    private int pageIndex = 0;
    private int totalPage;

    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_personal_bills;
    }

    @Override
    protected void initView(View view) {
        setTopBarRight();
        initData(MSG_PAGE_UPDATE);
        initRecyclerView();
        initPullLoad();
    }

    private void initPullLoad() {
        mRecyclerView.addOnScrollListener(new EndLessOnScrollListener(mLinearLayoutManager) {
            @Override
            public void onLoadMore(int currentPage) {

                TLog.e("-------", "++前--" + pageIndex + "---" + totalPage);
                if (pageIndex < totalPage) {
                    if (pageIndex == totalPage - 1) {
                        return;
                    } else {
                        handler.sendEmptyMessage(MSG_PAGE_PLUS);
                    }
                }
            }
        });


    }

    private void setTopBarRight() {
        BaseActivity activity = (BaseActivity) getCurActivity();
        QMUITopBarLayout layout = activity.getmQmuiTopBarlayout();

        Button button = layout.addRightTextButton(getResources().getString(R.string.screen), R.id.iv_right);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PersonalBillsScreenFragment billsScreenFragment = new PersonalBillsScreenFragment();
                billsScreenFragment.setTargetFragment(PersonalBillsFragment.this, RequestCode.PERSONAL_BILLS_SCREEN);
                billsScreenFragment.show(getActivity().getSupportFragmentManager(), "billsScreenFragment");

            }
        });
        button.setTextColor(getResources().getColor(R.color.black_333));
    }

    private void initData(final int what) {
        PersonalBillsRequestBody requestHomeDataBody = new PersonalBillsRequestBody(
                AppContext.instance().getLoginUser().getAccountId(),
                AppContext.getLangulage(),
                pageIndex,
                incomeTypeInt,
                payTypeInt);


        TLog.log(this , "PersonalBillsRequestBody : " + requestHomeDataBody);
        showDialog();
        sHomeApi.getPersonalBills(requestHomeDataBody)
                .compose(RxResultHelper.<PersonalBillsBean>handleRespose())
                .compose(RxSchedulers.<PersonalBillsBean>applyObservableAsync())
                .subscribe(new Observer<PersonalBillsBean>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(@NonNull PersonalBillsBean bean) {
                        getmTipDialog().dismiss();
                        totalPage = bean.getTotalPage();

                        List<PersonalBillsBean.ListBean> list = bean.getList();
                        if (list.size() == 0) {
                            dataList.clear();
                            mAdapter.notifyDataSetChanged();
                            mRecyclerView.setEmptyView(ivEmpty);
                        } else {
                            switch (what){
                                case MSG_PAGE_PLUS:

                                    break;
                                case MSG_PAGE_UPDATE:
                                    //先全部清空   第一次进来肯定没有数据
                                    dataList.clear();
                                    break;
                            }
                            dataList.addAll(list);
                            mAdapter.notifyDataSetChanged();
                            initDecoration();
                            mRecyclerView.addItemDecoration(decoration);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        getmTipDialog().dismiss();
                        AppContext.showToast(e.toString());
                        dataList.clear();
                        mRecyclerView.setEmptyView(ivError);
                        TLog.log(TAG, e.toString());
                    }

                    @Override
                    public void onComplete() {

                    }

                });

    }

    private void initDecoration() {
        if (decoration != null) {
            //如果有头就移除
            mRecyclerView.removeItemDecoration(decoration);
        }

        decoration = PowerfulStickyDecoration.Builder
                .init(new PowerGroupListener() {
                    @Override
                    public String getGroupName(int position) {
                        //获取组名，用于判断是否是同一组
                        if (dataList.size() > position) {
                            return dataList.get(position).getMonth();
                        }
                        return null;
                    }

                    @Override
                    public View getGroupView(final int position) {
                        //获取自定定义的组View
                        if (dataList.size() > position) {

                            String moneyPay = DecimalFormatUtil.defFormat(dataList.get(position).getPay());
                            String moneyGet = DecimalFormatUtil.defFormat(dataList.get(position).getIncome());
                            final View view = getCurActivity().getLayoutInflater().inflate(R.layout.city_group, null, false);
                            ((TextView) view.findViewById(R.id.tv_month)).setText(dataList.get(position).getMonth());
                            ((TextView) view.findViewById(R.id.tv_out)).setText(getResources().getString(R.string.out));
                            ((TextView) view.findViewById(R.id.tv_out_money)).setText(moneyPay);
                            ((TextView) view.findViewById(R.id.tv_in)).setText(getResources().getString(R.string.income));
                            ((TextView) view.findViewById(R.id.tv_in_money)).setText(moneyGet);

                            return view;
                        } else {
                            return null;
                        }
                    }
                })
                .setGroupHeight(DensityUtil.dip2px(getContext(), 60))   //设置高度
                .build();
    }

    private void initRecyclerView() {

        mLinearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(mLinearLayoutManager);
        mAdapter = new PersonalBillsAdapter(getContext(), dataList);

        mRecyclerView.setAdapter(mAdapter);

        mAdapter.setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, RecyclerView.ViewHolder holder, int position) {
                PersonalBillsBean.ListBean dataBean = dataList.get(position);

                Bundle bundle = new Bundle();
                bundle.putString("orderNo", dataBean.getOrderNo());
                int orderType = dataBean.getOrderType();
                bundle.putInt("orderType", orderType);

                switch (orderType) {
                    case 1:
                        UIHelper.showPersonalPayBillsDetails(getContext(), bundle);

                        break;
                    case 2:
                        UIHelper.showPersonalBillsDetails(getContext(), bundle);

                        break;
                    case 3:
                        UIHelper.showPersonalWithdrawBillsDetails(getContext(), bundle);

                        break;
                    case 4://商家账单
                        UIHelper.showMerchantBillDetails(getContext(),bundle);

                        break;
                }


            }

            @Override
            public boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, int position) {
                return false;
            }
        });
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case RequestCode.PERSONAL_BILLS_SCREEN:
                if (resultCode == Activity.RESULT_OK && data != null) {//获取从DialogFragmentB中传递的mB2A
                    Bundle bundle = data.getExtras();
                    incomeTypeInt = bundle.getInt("incomeTypeInt");
                    payTypeInt = bundle.getInt("payTypeInt");

                    if (incomeTypeInt > 0 && payTypeInt > 0) {
                        handler.sendEmptyMessage(MSG_PAGE_UPDATE);
                    }
                }
                break;
        }
    }

    public static final int MSG_PAGE_PLUS    = 1;
    public static final int MSG_PAGE_UPDATE  =2;

    private class DataUpdateHandler extends Handler{
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case MSG_PAGE_PLUS:
                    pageIndex++;
                    initData(MSG_PAGE_PLUS);
                    break;
                case MSG_PAGE_UPDATE:
                    pageIndex = 0;
                    totalPage = 0;
                    initData(MSG_PAGE_UPDATE);
                    break;
            }

        }
    }

}
