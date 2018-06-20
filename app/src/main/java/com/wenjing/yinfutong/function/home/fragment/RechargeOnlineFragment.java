package com.wenjing.yinfutong.function.home.fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.rengwuxian.materialedittext.MaterialEditText;
import com.wenjing.yinfutong.AppConfig;
import com.wenjing.yinfutong.AppContext;
import com.wenjing.yinfutong.R;
import com.wenjing.yinfutong.base.BaseFragment;
import com.wenjing.yinfutong.common.UIHelper;
import com.wenjing.yinfutong.constant.RequestConstant;
import com.wenjing.yinfutong.function.RequestCode;
import com.wenjing.yinfutong.model.BankCardBean;
import com.wenjing.yinfutong.model.ExchangeRateBean;
import com.wenjing.yinfutong.retrofit.compose.RxSchedulers;
import com.wenjing.yinfutong.retrofit.rxresult.RxResultHelper;
import com.wenjing.yinfutong.utils.ArithUtils;
import com.wenjing.yinfutong.utils.DecimalFormatUtil;
import com.wenjing.yinfutong.utils.DialogUtils;
import com.wenjing.yinfutong.utils.TextUtil;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * Created by wenjing on 2018/3/27.
 */

public class RechargeOnlineFragment extends BaseFragment implements TextWatcher {
    @BindView(R.id.et_money)
    MaterialEditText etMoney;
    @BindView(R.id.tv_card)
    TextView tvCard;
    @BindView(R.id.rl_choose_card)
    RelativeLayout rlChooseCard;
    @BindView(R.id.tv_money)
    TextView tvMoney;
    @BindView(R.id.tv_confirm_payment)
    TextView tvConfirmPayment;
    @BindView(R.id.tv_exchange_rate)
    TextView tvExchangeRate;

    private String exchangeRate;
    private int bankId;
    private String rechargeMoney;


    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_recharge_online;
    }

    @Override
    protected void initView(View view) {

        initData();
        etMoney.addTextChangedListener(this);


    }

    private void initData() {
        sHomeApi.getExchangeRate(AppConfig.getRequestBody())
                .compose(RxResultHelper.<ExchangeRateBean>handleRespose())
                .compose(RxSchedulers.<ExchangeRateBean>applyObservableAsync())
                .subscribe(new Observer<ExchangeRateBean>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(ExchangeRateBean bean) {
                        exchangeRate = bean.getExchangeRate();
                        StringBuilder sb = new StringBuilder();
                        sb.append("当前汇率 1美元=");
                        sb.append(exchangeRate);
                        sb.append("元人民币");

                        tvExchangeRate.setText(sb);
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }

                });
    }


    @OnClick({ R.id.rl_choose_card, R.id.tv_confirm_payment})
    public void onViewClicked(View view) {

        int traderPasswordFlag = AppContext.instance().getLoginUser().getTraderPasswordFlag();
        switch (view.getId()) {
            case R.id.rl_choose_card:
                switch (traderPasswordFlag) {
                    case RequestConstant.TRADEPSD_FLAG_NO://未设置
                        UIHelper.showSetPaymentPsd(getContext());
                        break;
                    case RequestConstant.TRADEPSD_FLAG_YES://已设置
                        RechargePayWayFragment wayFragment = new RechargePayWayFragment();
                        wayFragment.setTargetFragment(this, RequestCode.RECHARGE_ONLINE);
                        wayFragment.show(getActivity().getSupportFragmentManager(), "wayFragment");
                        break;
                }

                break;
            case R.id.tv_confirm_payment:

                switch (traderPasswordFlag) {
                    case RequestConstant.TRADEPSD_FLAG_NO://未设置
                        DialogUtils.showSetPaymentPsdDialog(getContext());

                        break;
                    case RequestConstant.TRADEPSD_FLAG_YES://已设置

                        rechargeMoney = etMoney.getText().toString().trim();
                        String cardStr = tvCard.getText().toString().trim();
                        if (!TextUtil.isEmpty(rechargeMoney) && !TextUtil.isEmpty(cardStr) && !cardStr.equals(getResources().getString(R.string.choose_pay_way))) {
                            PaymentPasswordFragment paymentPasswordFragment = new PaymentPasswordFragment();
                            Bundle bundle = new Bundle();
                            bundle.putInt("type", RequestCode.RECHARGE_ONLINE);
                            bundle.putString("money", rechargeMoney);
                            bundle.putInt("bankId", bankId);
                            paymentPasswordFragment.setArguments(bundle);
                            paymentPasswordFragment.show(getActivity().getSupportFragmentManager(), "paymentPasswordFragment");

                        } else {
                            AppContext.showToast(getResources().getString(R.string.tautology));
                        }
                        break;
                }

        }
    }


    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void afterTextChanged(Editable editable) {
        String text = editable.toString();
        if (!TextUtils.isEmpty(text) && !text.equals("0")) {
            tvConfirmPayment.setEnabled(true);
            tvConfirmPayment.setBackground(getResources().getDrawable(R.drawable.green_btn_pressed));

            double money = ArithUtils.mul(Double.parseDouble(text), Double.parseDouble(exchangeRate));

            tvMoney.setText(DecimalFormatUtil.defFormat(money) + "元");
        } else {
            tvConfirmPayment.setEnabled(false);
            tvConfirmPayment.setBackground(getResources().getDrawable(R.drawable.gray_btn_normal));
            tvMoney.setText("0.00元");
        }

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RequestCode.RECHARGE_ONLINE && resultCode == Activity.RESULT_OK) {
            Bundle bundle = data.getExtras();
            BankCardBean dataBean = bundle.getParcelable("dataBean");
            bankId = dataBean.getBankId();
            tvCard.setText(dataBean.getBankName());

        }
    }

}
