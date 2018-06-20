package com.wenjing.yinfutong.function.home.fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.rengwuxian.materialedittext.MaterialEditText;
import com.wenjing.yinfutong.AppConfig;
import com.wenjing.yinfutong.AppContext;
import com.wenjing.yinfutong.R;
import com.wenjing.yinfutong.base.BaseFragment;
import com.wenjing.yinfutong.constant.RequestConstant;
import com.wenjing.yinfutong.function.RequestCode;
import com.wenjing.yinfutong.interf.OnResponseListener;
import com.wenjing.yinfutong.model.BankCardBean;
import com.wenjing.yinfutong.model.BaseResponse;
import com.wenjing.yinfutong.model.Customer;
import com.wenjing.yinfutong.model.ExchangeRateBean;
import com.wenjing.yinfutong.model.body.BaseRequestBody;
import com.wenjing.yinfutong.retrofit.compose.RxSchedulers;
import com.wenjing.yinfutong.retrofit.rxresult.RxResultHelper;
import com.wenjing.yinfutong.utils.ArithUtils;
import com.wenjing.yinfutong.utils.DecimalFormatUtil;
import com.wenjing.yinfutong.utils.DialogUtils;
import com.wenjing.yinfutong.utils.MyObservable;
import com.wenjing.yinfutong.utils.TextUtil;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * Created by wenjing on 2018/3/29.
 */

public class CashOutFragment extends BaseFragment {
    @BindView(R.id.tv_symbol)
    TextView tvSymbol;
    @BindView(R.id.tv_cash_out_all)
    TextView tvCashOutAll;
    @BindView(R.id.et_money)
    MaterialEditText etMoney;
    @BindView(R.id.tv_card)
    TextView tvCard;
    @BindView(R.id.rl_choose_card)
    RelativeLayout rlChooseCard;
    @BindView(R.id.tv_actual_account)
    TextView tvActualAccount;
    @BindView(R.id.tv_confirm_cash_out)
    TextView tvConfirmCashOut;
    @BindView(R.id.tv_calculation_amount)
    TextView tvCalculation;
    @BindView(R.id.iv_up)
    ImageView ivUp;

    private String money;
    private String cardNo;
    private boolean up=false;

    private String exchangeRate;

    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_cash_out;
    }

    @Override
    protected void initView(View view) {
        initData();

        Bundle bundle = getArguments();
        money = bundle.getString("money");
        etMoney.addTextChangedListener(new TextWatcher() {
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
                    tvConfirmCashOut.setEnabled(true);
                    tvConfirmCashOut.setBackground(getResources().getDrawable(R.drawable.green_btn_pressed));

                    double money = ArithUtils.mul(Double.parseDouble(text), Double.parseDouble(exchangeRate));

                    tvActualAccount.setText(DecimalFormatUtil.defFormat(money) + "元");
                } else {
                    tvConfirmCashOut.setEnabled(false);
                    tvConfirmCashOut.setBackground(getResources().getDrawable(R.drawable.gray_btn_normal));
                    tvActualAccount.setText("0.00元");
                }
            }
        });
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

//                        tvExchangeRate.setText(sb);
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }

                });
    }


    @OnClick({R.id.iv_up,R.id.tv_cash_out_all, R.id.rl_choose_card, R.id.tv_confirm_cash_out})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_cash_out_all:
                String format = DecimalFormatUtil.defFormat(Double.parseDouble(money));
                etMoney.setText(format);
                break;
            case R.id.rl_choose_card:

                RechargePayWayFragment wayFragment = new RechargePayWayFragment();
                wayFragment.setTargetFragment(this, RequestCode.RECHARGE_ONLINE);
                wayFragment.show(getActivity().getSupportFragmentManager(), "wayFragment");


                break;
            case R.id.tv_confirm_cash_out:

                String money = etMoney.getText().toString().trim();
                String cardString = tvCard.getText().toString().trim();

                if (!TextUtil.isEmpty(cardString) && !TextUtil.isEmpty(money)) {
                    int traderPasswordFlag = AppContext.instance().getLoginUser().getTraderPasswordFlag();
                    switch (traderPasswordFlag) {
                        case RequestConstant.TRADEPSD_FLAG_NO://未设置
                            DialogUtils.showSetPaymentPsdDialog(getContext());

                            break;
                        case RequestConstant.TRADEPSD_FLAG_YES://已设置
                            getUserInfo();

                            break;
                    }

                    return;
                }
                AppContext.showToast(getResources().getString(R.string.data_no_empity));


                break;
            case R.id.iv_up:
                if (!up){
                    up=true;
                    ivUp.setImageDrawable(getResources().getDrawable(R.mipmap.iv_up));
                    tvCalculation.setVisibility(View.VISIBLE);
                }else {
                    up=false;
                    ivUp.setImageDrawable(getResources().getDrawable(R.mipmap.iv_down));
                    tvCalculation.setVisibility(View.GONE);
                }

                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RequestCode.CHOOSE_PAY_TYPE && resultCode == Activity.RESULT_OK && data != null) {
            Bundle bundle = data.getExtras();
            BankCardBean dataBean = bundle.getParcelable("dataBean");
            cardNo = dataBean.getCardNo();
            tvCard.setText(dataBean.getBankName());
        }
    }

    private void getUserInfo() {
        int accountId;
        if (!AppContext.instance().isLogin()) {
            accountId = -1;
        } else {
            accountId = AppContext.instance().getLoginUser().getAccountId();
        }

        BaseRequestBody body = new BaseRequestBody(AppContext.getLangulage(), accountId);
        new MyObservable<BaseResponse<Customer>>().observe(getContext(),
                sHomeApi.getUserInfo(body),
                new OnResponseListener<BaseResponse<Customer>>() {
                    @Override
                    public void onNext(BaseResponse<Customer> data) {

                        if (data.getData() != null && data.getCode() == 0) {
                            Customer customer = data.getData();
                            //覆盖缓存
                            AppContext.instance().saveLoginInfo(getContext(), customer);

                            double usableAmount = customer.getUsableAmount();
                            String tvMoney = etMoney.getText().toString().trim();
                            if (usableAmount>=Double.parseDouble(tvMoney)){//余额充足

                                PaymentPasswordFragment fragment = new PaymentPasswordFragment();
                                Bundle bundle = new Bundle();
                                bundle.putInt("type", RequestCode.CASH_OUT);
                                bundle.putString("money", tvMoney);
                                bundle.putString("cardNo", cardNo);
                                fragment.setArguments(bundle);
                                fragment.show(getActivity().getSupportFragmentManager(), "paymentPasswordFragment");
                            }else {
                                AppContext.showToast(getResources().getString(R.string.not_sufficient_fund));
                            }
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        AppContext.showToast(e.toString());
                    }
                });

    }


}
