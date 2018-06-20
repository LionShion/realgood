package com.wenjing.yinfutong.function.mine.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;

import com.qmuiteam.qmui.alpha.QMUIAlphaImageButton;
import com.qmuiteam.qmui.widget.QMUITopBarLayout;
import com.wenjing.yinfutong.AppContext;
import com.wenjing.yinfutong.R;
import com.wenjing.yinfutong.base.BaseActivity;
import com.wenjing.yinfutong.base.BaseFragment;
import com.wenjing.yinfutong.common.UIHelper;
import com.wenjing.yinfutong.constant.RequestConstant;
import com.wenjing.yinfutong.function.RequestCode;
import com.wenjing.yinfutong.interf.OnResponseListener;
import com.wenjing.yinfutong.model.BankCardBean;
import com.wenjing.yinfutong.model.BaseResponse;
import com.wenjing.yinfutong.model.Customer;
import com.wenjing.yinfutong.model.MerchantFundBean;
import com.wenjing.yinfutong.model.body.BaseRequestBody;
import com.wenjing.yinfutong.model.body.MerchantFundRequestBody;
import com.wenjing.yinfutong.utils.DecimalFormatUtil;
import com.wenjing.yinfutong.utils.MyObservable;
import com.wenjing.yinfutong.utils.TextUtil;
import com.wenjing.yinfutong.utils.Validator;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by ${luoyingtao} on 2018\3\16 0016.
 */

public class MerchantServiceFragment extends BaseFragment {

    @BindView(R.id.tv_today_income)
    TextView tvTodayIncome;
    @BindView(R.id.today_trade_num)
    TextView todayTradeNum;
    @BindView(R.id.today_customer_num)
    TextView todayCustomerNum;
    @BindView(R.id.ms_balance_num)
    TextView msBalanceNum;

    private double balance = 0.00;

    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_merchantservice;
    }

    @Override
    protected void initView(View view) {
        setToolbarRight();
    }

    private void setToolbarRight() {
        BaseActivity activity = (BaseActivity) getCurActivity();
        QMUITopBarLayout qmuiTopBarLayout = activity.getmQmuiTopBarlayout();

        activity.setTitleColor(getResources().getColor(R.color.white));
        activity.getStatusBarHelper().setStatusBarDarkMode(getCurActivity());

        final QMUIAlphaImageButton imageBtn = qmuiTopBarLayout.addLeftBackImageButton();
        imageBtn.setImageResource(R.mipmap.ic_light_back);
        imageBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getCurActivity().onBackPressed();
                InputMethodManager imm = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(imageBtn.getWindowToken(), 0);
            }
        });

        qmuiTopBarLayout.setBackgroundColor(getResources().getColor(R.color.green_00bc9c));
    }


    @Override
    public void onResume() {
        super.onResume();
        loadData();
    }

    private void loadData() {
        Customer customer = AppContext.instance().getLoginUser();

        MerchantFundRequestBody body = new MerchantFundRequestBody();
        body.setMerchantId(customer.getMerchantId());
        body.setRegisterLang(AppContext.getLangulage());

        new MyObservable<BaseResponse<MerchantFundBean>>().observe(getContext(),
                sMineApi.getMerchantFund(body),
                new OnResponseListener<BaseResponse<MerchantFundBean>>() {
                    @Override
                    public void onNext(BaseResponse<MerchantFundBean> data) {
                        if (data.getCode() == 0 && data.getData() != null) {
                            MerchantFundBean bean = data.getData();
                            String count = bean.getCount();
                            int custCount = bean.getCustCount();

                            tvTodayIncome.setText(DecimalFormatUtil.defFormat(bean.getIncome()));
                            todayTradeNum.setText(count);
                            todayCustomerNum.setText(String.valueOf(custCount));

                            balance = bean.getReceiveAmount();
                            String format = DecimalFormatUtil.defFormat(balance);
                            String formatAmount = Validator.getPerThreeCommaAmount(format);
                            msBalanceNum.setText(TextUtil.getSpannableBalance(formatAmount));
                        }
                    }

                    @Override
                    public void onError(Throwable e) {

                    }
                });

    }

    @OnClick({R.id.btn_cashout, R.id.rb_accountCheck, R.id.rd_paymentCode, R.id.rd_bill, R.id.rb_voicePrompt, R.id.rb_commission, R.id.rb_expectMore})
    public void onViewClicked(View view) {
        final Bundle bundle = new Bundle();
        switch (view.getId()) {
            case R.id.rb_accountCheck:
                bundle.putParcelable("listBean", null);
                bundle.putInt("type", RequestCode.DAY_TYPE);
                bundle.putInt("buttonType", 1);
                UIHelper.showMerchantBillsList(getContext(), bundle);

                break;
            case R.id.btn_cashout:
                //余额不足
                if(balance <= 0){
                    AppContext.showToast(R.string.not_sufficient_fund);
                    return;
                }

                //为设置支付密码
                Customer customer = AppContext.instance().getLoginUser();
                int traderPasswordFlag = customer.getTraderPasswordFlag();
                if(traderPasswordFlag == RequestConstant.TRADEPSD_FLAG_NO){
                    UIHelper.showSetPaymentPsd(getContext());
                    return;
                }

                //未绑卡
                BaseRequestBody body = new BaseRequestBody(AppContext.getLangulage(),AppContext.instance().getLoginUser().getAccountId());

                new MyObservable<BaseResponse<List<BankCardBean>>>().observe(getContext(),
                        sMineApi.getCustomerBankList(body),
                        new OnResponseListener<BaseResponse<List<BankCardBean>>>() {
                            @Override
                            public void onNext(BaseResponse<List<BankCardBean>> data) {
                                if(data.getCode() == 0 && data.getData() != null && data.getData().size() > 0){
                                    bundle.putString("money", String.valueOf(balance));
                                    UIHelper.showCashOut(getContext(), bundle);
                                }else {
                                    UIHelper.showBindBankCard(getContext());
                                }
                            }

                            @Override
                            public void onError(Throwable e) {

                            }
                        });
                break;
            case R.id.rd_paymentCode:
                UIHelper.showGathering(getContext());
                break;
            case R.id.rd_bill:
                UIHelper.showHistoryBill(getContext());
                break;
            case R.id.rb_voicePrompt:
                voicePrompt();
                break;
            case R.id.rb_commission:

                break;
            case R.id.rb_expectMore:

                break;
        }
    }

    private void voicePrompt() {
        UIHelper.showVoicePrompt(getContext());
    }

}
