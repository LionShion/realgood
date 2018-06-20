package com.wenjing.yinfutong.function.home.fragment;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.wenjing.yinfutong.AppContext;
import com.wenjing.yinfutong.R;
import com.wenjing.yinfutong.base.BaseFragment;
import com.wenjing.yinfutong.model.PersonalBillsWithdrawDetailsBean;
import com.wenjing.yinfutong.model.body.PersonalBillsDetailsRequestBody;
import com.wenjing.yinfutong.retrofit.compose.RxSchedulers;
import com.wenjing.yinfutong.retrofit.rxresult.RxResultHelper;
import com.wenjing.yinfutong.utils.DecimalFormatUtil;
import com.wenjing.yinfutong.utils.TextUtil;

import butterknife.BindView;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * Created by wenjing on 2018/3/22.
 */

public class PersonalBillsWithdrawDetailsFragment extends BaseFragment {


    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.tv_money)
    TextView tvMoney;
    @BindView(R.id.tv_order_money)
    TextView tvOrderMoney;
    @BindView(R.id.tv_bank_name)
    TextView tvBankName;
    @BindView(R.id.tv_practical_money)
    TextView tvPracticalMoney;
    @BindView(R.id.tv_service_money)
    TextView tvServiceMoney;
    @BindView(R.id.tv_rate)
    TextView tvRate;
    @BindView(R.id.tv_trade_time)
    TextView tvTradeTime;
    @BindView(R.id.tv_order_num)
    TextView tvOrderNum;

    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_personal_bills_withdraw_details;
    }

    @Override
    protected void initView(View view) {
        Bundle bundle = getArguments();

        String orderNo =bundle.getString("orderNo");
        int orderType = bundle.getInt("orderType");

        if (!TextUtil.isEmpty(orderNo) && orderType > 0) {
            PersonalBillsDetailsRequestBody requestBody = new PersonalBillsDetailsRequestBody(orderNo, orderType, AppContext.getLangulage());

            showDialog();
            sHomeApi.getPersonalWinthdrawBillsDetails(requestBody)
                    .compose(RxResultHelper.<PersonalBillsWithdrawDetailsBean>handleRespose())
                    .compose(RxSchedulers.<PersonalBillsWithdrawDetailsBean>applyObservableAsync())
                    .subscribe(new Observer<PersonalBillsWithdrawDetailsBean>() {
                        @Override
                        public void onSubscribe(Disposable d) {

                        }

                        @Override
                        public void onNext(PersonalBillsWithdrawDetailsBean bean) {

                            getmTipDialog().dismiss();


                                PersonalBillsWithdrawDetailsBean data = bean;
                                double amount = data.getAmount();
                                double actualAmount = data.getActualAmount();
                                String bankName = data.getBankName();
                                double fee = data.getFee();
                                double rate = data.getRate();
                                String timeStr = data.getTimeStr();
                                String orderNo = data.getOrderNo();

                                String amountStr = DecimalFormatUtil.defFormat(amount);
                                String actualAmountStr = DecimalFormatUtil.defFormat(actualAmount);
                                String feeStr = DecimalFormatUtil.defFormat(fee);

                                tvName.setText(bankName);
                                tvBankName.setText(bankName);
                                tvMoney.setText("-"+amountStr);
                                tvOrderMoney.setText(amountStr);
                                tvPracticalMoney.setText(actualAmountStr);
                                tvServiceMoney.setText(feeStr);

                                StringBuilder sb=new StringBuilder();
                                sb.append("1美元=");
                                sb.append(rate);
                                sb.append("元人民币");

                                tvRate.setText(sb);

                                tvTradeTime.setText(timeStr);
                                tvOrderNum.setText(orderNo);

                            }

                        @Override
                        public void onError(Throwable e) {
                            AppContext.showToast(e.toString());

                            getmTipDialog().dismiss();
                        }

                        @Override
                        public void onComplete() {

                        }
                    });

        }


    }

}
