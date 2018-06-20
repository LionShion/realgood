package com.wenjing.yinfutong.function.home.fragment;

import android.view.View;
import android.widget.TextView;

import com.qmuiteam.qmui.widget.QMUITopBarLayout;
import com.wenjing.yinfutong.AppContext;
import com.wenjing.yinfutong.R;
import com.wenjing.yinfutong.base.BaseActivity;
import com.wenjing.yinfutong.base.BaseFragment;
import com.wenjing.yinfutong.common.UIHelper;
import com.wenjing.yinfutong.model.BankCardBean;
import com.wenjing.yinfutong.model.BaseResponse;
import com.wenjing.yinfutong.model.Customer;
import com.wenjing.yinfutong.model.body.BaseRequestBody;
import com.wenjing.yinfutong.retrofit.compose.RxSchedulers;
import com.wenjing.yinfutong.retrofit.rxresult.RxResultHelper;
import com.wenjing.yinfutong.utils.DecimalFormatUtil;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * Created by wenjing on 2018/3/15.
 */

public class AccountBanalceFragment extends BaseFragment {

    @BindView(R.id.tv_account_balance)
    TextView tvAccountBalance;
    @BindView(R.id.tv_recharge)
    TextView tvRecharge;
    @BindView(R.id.tv_cash_out)
    TextView tvCashOut;
    private int size = -1;

    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_account_banlace;
    }

    @Override
    protected void initView(View view) {
        setToolbarLayout();
    }

    @Override
    public void onResume() {
        super.onResume();
        getBankListData();
        getUserInfo();
    }

    private void setToolbarLayout() {
        BaseActivity activity = (BaseActivity) getCurActivity();
        QMUITopBarLayout layout = activity.getmQmuiTopBarlayout();
        layout.setBackgroundColor(getResources().getColor(R.color.green_00bc9c));
        layout.removeAllLeftViews();
        layout.addLeftImageButton(R.mipmap.icon_back, -1)
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        getCurActivity().finish();
                    }
                });
        activity.setTitleColor(getResources().getColor(R.color.white));
        activity.getStatusBarHelper().setStatusBarDarkMode(getCurActivity());

    }


    private void getUserInfo() {
        BaseRequestBody body = new BaseRequestBody(AppContext.getLangulage(), AppContext.instance().getLoginUser().getAccountId());
        sHomeApi.getUserInfo(body)
                .compose(RxResultHelper.<Customer>handleRespose())
                .compose(RxSchedulers.<Customer>applyObservableAsync())
                .subscribe(new Observer<Customer>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Customer value) {
                        Customer customer = value;
                        double usableAmount = customer.getUsableAmount();
                        String format = DecimalFormatUtil.defFormat(usableAmount);
//                        String formatAmount = Validator.getPerThreeCommaAmount(format);
                        tvAccountBalance.setText(format);

                        //覆盖缓存
                        AppContext.instance().saveLoginInfo(getContext(), customer);

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });

    }


    @OnClick({R.id.tv_recharge, R.id.tv_cash_out})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_recharge:
                if (AppContext.instance().isLogin()) {
                    int traderPasswordFlag = AppContext.instance().getLoginUser().getTraderPasswordFlag();
                    switch (traderPasswordFlag) {
                        case 0:
                            UIHelper.showSetPaymentPsd(getContext());
                            break;
                        case 1:
                            if (size > 0) {
                                UIHelper.showRechargeOnline(getContext());
                            } else if(size == 0){
                                UIHelper.showBindBankCard(getContext());
                            }
                            break;
                    }
                }
                break;
         /*   case R.id.tv_cash_out:
                String money = tvAccountBalance.getText().toString().trim();
                Bundle bundle = new Bundle();
                bundle.putString("money", money);
                UIHelper.showCashOut(getContext(), bundle);
                break;*/
        }
    }

    private void getBankListData() {
        BaseRequestBody body = new BaseRequestBody(AppContext.getLangulage(), AppContext.instance().getLoginUser().getAccountId());

        sMineApi.getCustomerBankList(body)
                .compose(RxSchedulers.<BaseResponse<List<BankCardBean>>>applyObservableAsync())
                .subscribe(new Observer<BaseResponse<List<BankCardBean>>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(BaseResponse<List<BankCardBean>> bean) {
                        if (bean.getCode() == 0 && bean.getData() != null) {
                            size = bean.getData().size();
                            return;
                        }
                        AppContext.showToast(bean.getMsg());

                    }

                    @Override
                    public void onError(Throwable e) {
                        AppContext.showToast(e.toString());
                    }

                    @Override
                    public void onComplete() {

                    }
                });


    }
}
