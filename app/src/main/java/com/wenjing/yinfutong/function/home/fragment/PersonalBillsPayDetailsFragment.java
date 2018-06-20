package com.wenjing.yinfutong.function.home.fragment;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.wenjing.yinfutong.AppContext;
import com.wenjing.yinfutong.R;
import com.wenjing.yinfutong.base.BaseFragment;
import com.wenjing.yinfutong.model.PersonalBillsPayDetailsBean;
import com.wenjing.yinfutong.model.body.PersonalBillsDetailsRequestBody;
import com.wenjing.yinfutong.retrofit.compose.RxSchedulers;
import com.wenjing.yinfutong.retrofit.rxresult.RxResultHelper;
import com.wenjing.yinfutong.utils.ArithUtils;
import com.wenjing.yinfutong.utils.DecimalFormatUtil;
import com.wenjing.yinfutong.utils.TextUtil;

import butterknife.BindView;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * Created by wenjing on 2018/3/22.
 */

public class PersonalBillsPayDetailsFragment extends BaseFragment {


    @BindView(R.id.tv_practical_money)
    TextView tvPracticalMoney;
    @BindView(R.id.tv_order_money)
    TextView tvOrderMoney;
    @BindView(R.id.tv_discount_money)
    TextView tvDiscountMoney;
    @BindView(R.id.tv_company_name)
    TextView tvCompanyName;
    @BindView(R.id.tv_pay_type)
    TextView tvPayType;
    @BindView(R.id.tv_trade_time)
    TextView tvTradeTime;
    @BindView(R.id.tv_order_num)
    TextView tvOrderNum;
    @BindView(R.id.tv_payer)
    TextView tvName;

    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_personal_bills_pay_details;
    }

    @Override
    protected void initView(View view) {
        Bundle bundle = getArguments();

        String orderNo = bundle.getString("orderNo");
        final int orderType = bundle.getInt("orderType");


        if (!TextUtil.isEmpty(orderNo) && orderType > 0) {
            PersonalBillsDetailsRequestBody requestBody = new PersonalBillsDetailsRequestBody(orderNo, orderType, AppContext.getLangulage());

            showDialog();
            sHomeApi.getPersonalPayBillsDetails(requestBody)
                    .compose(RxResultHelper.<PersonalBillsPayDetailsBean>handleRespose())
                    .compose(RxSchedulers.<PersonalBillsPayDetailsBean>applyObservableAsync())
                    .subscribe(new Observer<PersonalBillsPayDetailsBean>() {
                        @Override
                        public void onSubscribe(Disposable d) {

                        }

                        @Override
                        public void onNext(PersonalBillsPayDetailsBean bean) {
                            getmTipDialog().dismiss();
                            PersonalBillsPayDetailsBean data = bean;
                            double orderAmount = data.getOrderAmount();
                            double bonusAmount = data.getBonusAmount();
                            String merchantName = data.getMerchantName();
                            int payChannel = data.getPayChannel();
                            String orderNo = data.getOrderNo();
                            String nickName = data.getNickName();
                            String orderTimeStr = data.getOrderTimeStr();
                            String money = DecimalFormatUtil.defFormat(orderAmount);
                            double practicalMoney = ArithUtils.sub(orderAmount, bonusAmount);
                            String practicalMoneyStr = DecimalFormatUtil.defFormat(practicalMoney);
                            String bonusAmountStr = DecimalFormatUtil.defFormat(bonusAmount);

                            tvName.setText(nickName);
                            tvCompanyName.setText(merchantName);
                            tvOrderMoney.setText(money);
                            tvDiscountMoney.setText(bonusAmountStr);
                            tvTradeTime.setText(orderTimeStr);
                            tvOrderNum.setText(orderNo);
                            if (orderType == 1) {
                                tvPracticalMoney.setText("-" + practicalMoneyStr);
                            }
                            if (orderType==4){
                                tvPracticalMoney.setText("+" + practicalMoneyStr);
                            }

                            switch (payChannel) {
                                case 0:
                                    tvPayType.setText(getResources().getString(R.string.balance));
                                    break;
                                case 1:
                                    tvPayType.setText(getResources().getString(R.string.wechat));
                                    break;
                                case 2:
                                    tvPayType.setText(getResources().getString(R.string.alipay));
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
