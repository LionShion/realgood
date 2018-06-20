package com.wenjing.yinfutong.function.home.fragment;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.TextView;

import com.rengwuxian.materialedittext.MaterialEditText;
import com.wenjing.yinfutong.AppContext;
import com.wenjing.yinfutong.R;
import com.wenjing.yinfutong.base.BaseFragment;
import com.wenjing.yinfutong.common.UIHelper;
import com.wenjing.yinfutong.constant.RequestConstant;
import com.wenjing.yinfutong.model.QRCodeResultBean;
import com.wenjing.yinfutong.model.RechargeOfflineToSignOrderNo;
import com.wenjing.yinfutong.model.RechargeOnlineBean;
import com.wenjing.yinfutong.model.body.RechargeOfflineRequestBody;
import com.wenjing.yinfutong.retrofit.compose.RxSchedulers;
import com.wenjing.yinfutong.retrofit.rxresult.RxResultHelper;
import com.wenjing.yinfutong.utils.MD5Util;
import com.wenjing.yinfutong.utils.TLog;
import com.wenjing.yinfutong.utils.TextUtil;
import com.wenjing.yinfutong.utils.sign.SignHelper;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * Created by wenjing on 2018/3/19.
 */

public class RechargeFragment extends BaseFragment {
    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.tv_type)
    TextView tvType;
    @BindView(R.id.et_money)
    MaterialEditText etMoney;
    @BindView(R.id.tv_confirm_payment)
    TextView tvConfirmPayment;
    private QRCodeResultBean dataBean;


    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_recharge;
    }

    @Override
    protected void initView(View view) {

        tvConfirmPayment.setEnabled(false);

        Bundle bundle = getArguments();
        dataBean = bundle.getParcelable("DataBean");
        String name = dataBean.getName();
        String realName = dataBean.getRealName();

        tvName.setText(name);
        tvType.setText(realName);

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
                if (!TextUtil.isEmpty(text) && !text.equals("0")) {
                    tvConfirmPayment.setEnabled(true);
                    tvConfirmPayment.setBackground(getResources().getDrawable(R.drawable.green_btn_pressed));
                } else {
                    tvConfirmPayment.setEnabled(false);
                    tvConfirmPayment.setBackground(getResources().getDrawable(R.drawable.btn_background_grey));
                }
            }
        });


    }


    @OnClick({R.id.tv_confirm_payment})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_confirm_payment:
                String money = etMoney.getText().toString().trim();
                if (!TextUtil.isEmpty(money) && Double.parseDouble(money) > 0) {

                    int accountId = AppContext.instance().getLoginUser().getAccountId();

                    toRechargeOffline(accountId,money);

                }
                break;
        }
    }

    private void toRechargeOffline(int accountId, final String money) {
        RechargeOfflineToSignOrderNo toSignOrderNo = new RechargeOfflineToSignOrderNo(accountId, dataBean.getId(), Double.parseDouble(money));
        TLog.log(this, toSignOrderNo.toString());

        String signContent = SignHelper.sortParamForSignCard(toSignOrderNo);
        String sign = MD5Util.getInstance().sign(signContent, RequestConstant.PRIVATE_KEY);

        RechargeOfflineRequestBody body = new RechargeOfflineRequestBody(accountId, dataBean.getId(), Double.parseDouble(money), dataBean.getDesc(), AppContext.getLangulage(), sign);
        TLog.log(this, body.toString());

        sHomeApi.getRechargeOffline(body)
                .compose(RxResultHelper.<RechargeOnlineBean>handleRespose())
                .compose(RxSchedulers.<RechargeOnlineBean>applyObservableAsync())
                .subscribe(new Observer<RechargeOnlineBean>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(RechargeOnlineBean bean) {
                        Bundle bundle = new Bundle();
//                        bundle.putString("money", money);
//                        UIHelper.showRechargeSucc(getContext(), bundle);
                        //分销商   id
                        bundle.putInt("distributorId" , dataBean.getId());
                        //走   待充值  页面
                        UIHelper.showToRecharge(getContext() , bundle);
                        getActivity().finish();

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
