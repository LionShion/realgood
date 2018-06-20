package com.wenjing.yinfutong.function.mine.fragment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RadioGroup;

import com.wenjing.yinfutong.AppContext;
import com.wenjing.yinfutong.R;
import com.wenjing.yinfutong.base.BaseFragment;
import com.wenjing.yinfutong.constant.RequestConstant;
import com.wenjing.yinfutong.function.mine.adapter.CommonViewPagerAdapter;
import com.wenjing.yinfutong.function.mine.adapter.DepositBankAdapter;
import com.wenjing.yinfutong.interf.OnResponseListener;
import com.wenjing.yinfutong.model.BaseResponse;
import com.wenjing.yinfutong.model.DepositBankBean;
import com.wenjing.yinfutong.model.body.DepositBankRequestBody;
import com.wenjing.yinfutong.utils.MyObservable;
import com.wenjing.yinfutong.utils.TLog;
import com.wenjing.yinfutong.view.EmptyRecyclerView;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.MultiItemTypeAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by ${luoyingtao} on 2018\3\23 0023.
 */

public class DepositBankFragment extends BaseFragment {
    public static final String TAG = "DepositBankFragment";

    @BindView(R.id.rg_db_selector)
    RadioGroup rgDbSelector;
    @BindView(R.id.vp_banklist)
    ViewPager vpBank;

    private List<View> views = new ArrayList<>();

    private List<DepositBankBean.PaybankBean> notUnionBankList = new ArrayList<>();
    private CommonAdapter<DepositBankBean.PaybankBean> notUnionAdapter;
    private EmptyRecyclerView notunion_recyclerBank;
    private View notunion_view;


    private List<DepositBankBean.PaybankBean> unionBankList = new ArrayList<>();
    private CommonAdapter<DepositBankBean.PaybankBean> unionAdapter;
    private EmptyRecyclerView union_recyclerBank;
    private View union_view;


    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_depositbank;
    }

    @Override
    protected void initView(View view) {
        loadData();
        initBankVp();

        rgDbSelector.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int checkedId) {
                switch (checkedId) {
                    case R.id.rb_not_unioncard:
                        vpBank.setCurrentItem(0,true);
                        break;
                    case R.id.rb_unioncard:
                        vpBank.setCurrentItem(1,true);
                        break;
                }
            }
        });

        vpBank.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                rgDbSelector.check(rgDbSelector.getChildAt(position).getId());//
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }

    private void loadData() {
        showNotUnionPayBank();
        showUnionPayBank();
    }

    private void initBankVp() {
        views.add(getNotUnionVpItemView());
        views.add(getUnionVpItemView());
        CommonViewPagerAdapter commonViewPagerAdapter = new CommonViewPagerAdapter(views);

        vpBank.setAdapter(commonViewPagerAdapter);
        vpBank.setCurrentItem(0);
    }

    private View getNotUnionVpItemView() {
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        notunion_view = inflater.inflate(R.layout.item_vp_bank, null, false);
        notunion_recyclerBank = notunion_view.findViewById(R.id.rv_recyclerview);



        notunion_recyclerBank.setLayoutManager(new LinearLayoutManager(getContext()));
        notunion_recyclerBank.addItemDecoration(new DividerItemDecoration(getContext(), OrientationHelper.VERTICAL));

        notUnionAdapter = new DepositBankAdapter(getContext(),notUnionBankList);
        notunion_recyclerBank.setAdapter(notUnionAdapter);
        notUnionAdapter.setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, RecyclerView.ViewHolder holder, int position) {
                DepositBankBean.PaybankBean paybankBean = notUnionBankList.get(position);
                Intent intent = new Intent();
                intent.putExtra("bank", paybankBean);
                getCurActivity().setResult(Activity.RESULT_OK, intent);
                getCurActivity().finish();
            }

            @Override
            public boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, int position) {
                return false;
            }
        });
        return notunion_view;
    }

    private View getUnionVpItemView() {
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        union_view = inflater.inflate(R.layout.item_vp_bank, null, false);
        union_recyclerBank = union_view.findViewById(R.id.rv_recyclerview);

        union_recyclerBank.setLayoutManager(new LinearLayoutManager(getContext()));
        union_recyclerBank.addItemDecoration(new DividerItemDecoration(getContext(), OrientationHelper.VERTICAL));

        unionAdapter = new DepositBankAdapter(getContext(),unionBankList);
        union_recyclerBank.setAdapter(unionAdapter);
        unionAdapter.setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, RecyclerView.ViewHolder holder, int position) {
                DepositBankBean.PaybankBean paybankBean = unionBankList.get(position);
                Intent intent = new Intent();
                intent.putExtra("bank", paybankBean);
                getCurActivity().setResult(Activity.RESULT_OK, intent);
                getCurActivity().finish();
            }

            @Override
            public boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, int position) {
                return false;
            }
        });
        return union_view;
    }

    private void showNotUnionPayBank() {
        loadRecyclerDta(BankType.NotUnionPay);
    }

    private void showUnionPayBank() {
        loadRecyclerDta(BankType.Unionpay);
    }

    private void loadRecyclerDta(final BankType bankType) {
        DepositBankRequestBody body = new DepositBankRequestBody();
        body.setRegisterLang(AppContext.getLangulage());
        body.setType(bankType.getType());

        showWaitDialog();
        new MyObservable<BaseResponse<DepositBankBean>>().observe(getContext(),
                sMineApi.getDepositBankList(body),
                new OnResponseListener<BaseResponse<DepositBankBean>>() {
                    @Override
                    public void onNext(BaseResponse<DepositBankBean> data) {
                        hideWaitDialog();
                        TLog.log(TAG, "bankType : " + bankType.getType() + data.toString());

                        if (data.getCode() == 0 && data.getData() != null) {

                            switch (bankType){
                                case NotUnionPay:
                                    if(data.getData().getPaybank() !=null && data.getData().getPaybank().size() > 0){
                                        notUnionBankList.addAll(data.getData().getPaybank());
                                        notunion_recyclerBank.setAdapter(notUnionAdapter);
                                    }else {
                                        notunion_recyclerBank.setEmptyView(notunion_view.findViewById(R.id.iv_empty));
                                    }
                                    break;
                                case Unionpay:
                                    if(data.getData().getPaybank() !=null && data.getData().getPaybank().size() > 0){
                                        unionBankList.addAll(data.getData().getPaybank());
                                        union_recyclerBank.setAdapter(unionAdapter);
                                    }else {
                                        union_recyclerBank.setEmptyView(union_view.findViewById(R.id.iv_empty));
                                    }
                                    break;
                            }
                        } else {
                            AppContext.showToast(data.getMsg());
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        hideWaitDialog();
                        TLog.log(TAG, "bankType : " + bankType.getType() + e.getMessage());
                        AppContext.showToast(e.getMessage());

                        switch (bankType){
                            case NotUnionPay:
                                notunion_recyclerBank.setEmptyView(notunion_view.findViewById(R.id.iv_error));
                                break;
                            case Unionpay:
                                union_recyclerBank.setEmptyView(union_view.findViewById(R.id.iv_error));
                                break;
                        }
                    }
                });

    }

    private enum BankType {
        Unionpay(RequestConstant.DEPOSITBANK_TYPE_UNIONPAY),
        NotUnionPay(RequestConstant.DEPOSITBANK_TYPE_NOTUNIONPAY);

        private int type;

        private BankType(int type) {
            this.type = type;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }
    }

}
