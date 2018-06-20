package com.wenjing.yinfutong.function.home.fragment;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.wenjing.yinfutong.AppContext;
import com.wenjing.yinfutong.R;
import com.wenjing.yinfutong.base.BaseFragment;
import com.wenjing.yinfutong.function.RequestCode;
import com.wenjing.yinfutong.interf.OnResponseListener;
import com.wenjing.yinfutong.model.BaseResponse;
import com.wenjing.yinfutong.model.Customer;
import com.wenjing.yinfutong.model.UsQRBean;
import com.wenjing.yinfutong.model.body.BaseRequestBody;
import com.wenjing.yinfutong.utils.ArithUtils;
import com.wenjing.yinfutong.utils.DecimalFormatUtil;
import com.wenjing.yinfutong.utils.MyObservable;
import com.wenjing.yinfutong.utils.TLog;
import com.wenjing.yinfutong.utils.Validator;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by wenjing on 2018/3/14.
 */

public class PaymentFragment extends BaseFragment {
    @BindView(R.id.tv_person)
    TextView tvPerson;
    @BindView(R.id.tv_pay_type)
    TextView tvPayType;
    @BindView(R.id.tv_confirm_payment)
    TextView tvConfirmPayment;
    @BindView(R.id.tv_pay_money)
    TextView tvPayMoney;


    private String orderNo;

    private UsQRBean dataBean;

    private double payAmount;

    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_payment;
    }

    @Override
    protected void initView(View view) {

        Bundle bundle = getArguments();
        orderNo = bundle.getString("orderNo");
        dataBean = bundle.getParcelable("DataBean");
        String name = dataBean.getName();
        String realName = dataBean.getRealName();

        tvPerson.setText(name);
        tvPayType.setText(realName);
        payAmount = dataBean.getAmount();
        String formatAmount = DecimalFormatUtil.defFormat(payAmount);
        String perThreeCommaAmount = Validator.getPerThreeCommaAmount(formatAmount);
        tvPayMoney.setText(perThreeCommaAmount);
    }


    @OnClick({R.id.tv_confirm_payment})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_confirm_payment:

                //                String money = etMoney.getText().toString().trim();
                //                if (!TextUtil.isEmpty(money) && Double.parseDouble(money) > 0) {
                //                    Bundle bundle = new Bundle();
                //                    bundle.putParcelable("DataBean", dataBean);
                //                    bundle.putString("money", money);
                //
                //                    PaymentConfirmFragment paymentConfirmFragment = new PaymentConfirmFragment();
                //                    paymentConfirmFragment.setArguments(bundle);
                //                    paymentConfirmFragment.show(getCurActivity().getSupportFragmentManager(), "paymentConfirmFragment");
                //                }

                //先本地过滤   余额是否  充足
                double usableAmount = AppContext.instance().getLoginUser().getUsableAmount();
                if(usableAmount >= payAmount){
                    getUserInfo();
                }else {
                    AppContext.showToast(getResources().getString(R.string.not_sufficient_fund));
                }
                break;
        }
    }

    private void getUserInfo() {
        double rebate = dataBean.getRebate();
        double money = dataBean.getAmount();

        //改动以后  直接跳到   输入支付密码页面
        PaymentPasswordFragment fragment = new PaymentPasswordFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("type", RequestCode.PAY_QR);
        //这个实际可以  不分出来写
        bundle.putString("money", String.valueOf(ArithUtils.sub(money, rebate)));//实际金额
        bundle.putParcelable("DataBean", dataBean);
        bundle.putString("orderNo" , orderNo);
        fragment.setArguments(bundle);
        fragment.show(getActivity().getSupportFragmentManager(), "paymentPasswordFragment");
    }


//    private void getUserInfo() {
//        int accountId;
//        if (!AppContext.instance().isLogin()) {
//            accountId = -1;
//        } else {
//            accountId = AppContext.instance().getLoginUser().getAccountId();
//        }
//
//        BaseRequestBody body = new BaseRequestBody(AppContext.getLangulage(), accountId);
//        new MyObservable<BaseResponse<Customer>>().observe(getContext(),
//                sHomeApi.getUserInfo(body),
//                new OnResponseListener<BaseResponse<Customer>>() {
//                    @Override
//                    public void onNext(BaseResponse<Customer> data) {
//                        if (data.getData() != null && data.getCode() == 0) {
//                            Customer customer = data.getData();
//                            //覆盖缓存
//                            AppContext.instance().saveLoginInfo(getContext(), customer);
//
//                            double usableAmount = customer.getUsableAmount();
//
//                            if (usableAmount >= payAmount){//余额充足
//
//                                double rebate = dataBean.getRebate();
//                                double money = dataBean.getAmount();
//
//                                //改动以后  直接跳到   输入支付密码页面
//                                PaymentPasswordFragment fragment = new PaymentPasswordFragment();
//                                Bundle bundle = new Bundle();
//                                bundle.putInt("type", RequestCode.PAY_QR);
//                                //这个实际可以  不分出来写
//                                bundle.putString("money", String.valueOf(ArithUtils.sub(money, rebate)));//实际金额
//                                bundle.putParcelable("DataBean", dataBean);
//                                fragment.setArguments(bundle);
//                                fragment.show(getActivity().getSupportFragmentManager(), "paymentPasswordFragment");
//                            }else {
//                                AppContext.showToast(getResources().getString(R.string.not_sufficient_fund));
//                            }
//                        }
//                    }
//
//                    @Override
//                    public void onError(Throwable e) {
//                        AppContext.showToast(e.toString());
//                    }
//                });
//
//    }

}
