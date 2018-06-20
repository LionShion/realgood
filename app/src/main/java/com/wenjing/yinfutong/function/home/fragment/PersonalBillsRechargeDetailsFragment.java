package com.wenjing.yinfutong.function.home.fragment;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.wenjing.yinfutong.AppContext;
import com.wenjing.yinfutong.R;
import com.wenjing.yinfutong.base.BaseFragment;
import com.wenjing.yinfutong.model.PersonalBillsRechargeDetailsBean;
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

public class PersonalBillsRechargeDetailsFragment extends BaseFragment {

    @BindView(R.id.tv_practical_money)
    TextView tvPracticalMoney;
    @BindView(R.id.tv_order_money)
    TextView tvOrderMoney;
    @BindView(R.id.tv_pay_type)
    TextView tvPayType;
    @BindView(R.id.tv_trade_time)
    TextView tvTradeTime;
    @BindView(R.id.tv_order_num)
    TextView tvOrderNum;

    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_personal_bills_details;
    }

    @Override
    protected void initView(View view) {
        Bundle bundle = getArguments();
        String orderNo = bundle.getString("orderNo");
        int orderType = bundle.getInt("orderType");

        if (!TextUtil.isEmpty(orderNo)&& orderType>0) {
            PersonalBillsDetailsRequestBody requestBody = new PersonalBillsDetailsRequestBody(orderNo, orderType, AppContext.getLangulage());

            showDialog();
            sHomeApi.getPersonalRechargeBillsDetails(requestBody)
                    .compose(RxResultHelper.<PersonalBillsRechargeDetailsBean>handleRespose())
                   .compose(RxSchedulers.<PersonalBillsRechargeDetailsBean>applyObservableAsync())
                    .subscribe(new Observer<PersonalBillsRechargeDetailsBean>() {
                        @Override
                        public void onSubscribe(Disposable d) {

                        }

                        @Override
                        public void onNext(PersonalBillsRechargeDetailsBean bean) {

                            getmTipDialog().dismiss();


                                PersonalBillsRechargeDetailsBean data = bean;
                                int type = data.getType();
                                double amount = data.getAmount();
                                String timeStr = data.getTimeStr();
                                String orderNo = data.getOrderNo();

                                String money = DecimalFormatUtil.defFormat(amount);
                                tvPracticalMoney.setText("+"+money);
                                tvTradeTime.setText(timeStr);
                                tvOrderNum.setText(orderNo);
                                tvOrderMoney.setText(money);

                                switch (type) {
                                    case 1:
                                        tvPayType.setText(R.string.bank_card);

                                        break;
                                    case 2:
                                        tvPayType.setText(R.string.artificial_recharge);

                                        break;
                                    case 3:
                                        tvPayType.setText(R.string.distributor);

                                        break;
                                    case 4:
                                        tvPayType.setText(R.string.alipay);

                                        break;
                                    case 5:
                                        tvPayType.setText(R.string.wechat);

                                        break;
                                }


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
